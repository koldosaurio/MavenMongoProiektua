package Controller;




//import Model.Landareak;
import Model.Landareak;
import com.mongodb.client.MongoCollection;
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
public class Methods {
    //Insert bat egiteko
    public static void landareaGehitu(Landareak land, MongoCollection taula) {

        /* Objectu berria sortu */
        Document docObj = new Document("Izena", land.getName())
                .append("Deskribapena", land.getDescription())
                .append("Kolorea", land.getColor())
                .append("Tamaina", Float.parseFloat(land.getSize().replace(land.getSize().substring(land.getSize().length() - 1, land.getSize().length()), "")))
                .append("Loreak", land.getFlowers())
                .append("IzenZientifikoa", land.getCName());
        
        /* Taulan, pelikula berriaren datuak gorde */
        taula.insertOne(docObj);
    }
}
