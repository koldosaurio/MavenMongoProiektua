package Controller;

//import Model.Landareak;
import Model.Landareak;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.eq;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bson.Document;
import org.bson.conversions.Bson;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author DM3-2-15
 */
public class Methods {

    //Insert bat egiteko
    public static void landareaGehitu(Landareak land, MongoCollection taula) {

        /* Objectu berria sortu */
        Document docObj = new Document("Izena", land.getName())
                .append("Deskribapena", land.getDescription())
                .append("Kolorea", land.getColor())
                .append("Tamaina", Integer.parseInt(land.getSize().replace(land.getSize().substring(land.getSize().length() - 1, land.getSize().length()), "")))
                .append("Loreak", land.getFlowers())
                .append("IzenZientifikoa", land.getCName());

        /* Taulan, pelikula berriaren datuak gorde */
        taula.insertOne(docObj);
    }
    
    public static ObservableList<Landareak> datuak(MongoCollection<Document> taula) {       
        ObservableList<Landareak> peliGuzt = FXCollections.observableArrayList(); // pelikula guztien ObservableList-a
        
        try {
            FindIterable<Document> fi = taula.find();
            fi.forEach(new Block<Document>() {
                @Override
                public void apply(final Document document) {
                    Boolean bol=true;
                    if(!document.getString("Loreak").equals("yes")){
                        bol=false;
                    }
                    Landareak peli = new Landareak(document.getString("Izena"), document.getString("Deskribapena"), 
                            document.getString("kolorea"), document.getInteger("Tamaina").toString(),
                            bol, document.getString("IzenZientifikoa"));
                    peliGuzt.add(peli);
                }
            });
        }
        catch(Exception ex) {
            System.err.println("Errorea egon da!\nERROREAREN XEHETASUNAK: Mezua \n" + ex.getMessage());
        }
        return peliGuzt; // observableList-a datuekin bueltatu
    }
    public static void ezabatu(MongoCollection taula,String param){
        Bson eq = null;
        taula.deleteOne(eq("IzenZientifikoa",param));
    }
}
