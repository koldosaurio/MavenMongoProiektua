
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DM3-2-15
 */
public class ProbaPito {
    public static void main(String[]args){
        Landareak land=new Landareak("kk","kk","kk","12",true,"kk");
        landareaGehitu(land);
    }
    //Insert bat egiteko
    public static void landareaGehitu(Landareak land) {
        MongoClient mongoKon = new MongoClient( "localhost" , 27017 ); // datu basera konektatu
        MongoDatabase db = mongoKon.getDatabase("landareak"); // datu basea
        MongoCollection<Document> taula = db.getCollection("landareak"); // taula

        /* Objectu berria sortu */
        Document docObj = new Document("Izena", land.getName())
                .append("Deskribapena", land.getDescription())
                .append("Kolorea", land.getColor())
                .append("Tamaina", Float.parseFloat(land.getSize().replace(land.getSize().substring(land.getSize().length() - 1, land.getSize().length()), "")))
                .append("Loreak", land.getFlowers())
                .append("IzenZientifikoa", land.getCName());
        
        /* Taulan, pelikula berriaren datuak gorde */
        taula.insertOne(docObj);
        
        mongoKon.close(); // konexioa itxi
    }
}
