package View;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Controller.Methods;
import Model.Landareak;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.bson.Document;

/**
 *
 * @author DM3-2-15
 */
public class Koldosaurus_app extends Application {

    private final TableView<Landareak> table = new TableView<>();
    ObservableList<Landareak> data = null;
    final HBox hb = new HBox();

    MongoClient mongoKon = new MongoClient("localhost", 27017); // datu basera konektatu
    MongoDatabase db = mongoKon.getDatabase("landareak"); // datu basea
    MongoCollection<Document> taula = db.getCollection("landareak"); // taula

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(new Group());
        stage.setTitle("Data Table");
        stage.setWidth(900);
        stage.setHeight(600);
        final Label label = new Label("Landareak");
        label.setFont(new Font("Arial", 20));

        final Label lab = new Label("");
        lab.setTextFill(Color.web("#ff0000"));
        lab.setFont(new Font("Arial", 20));

        table.setEditable(false);

        TableColumn<Landareak, String> NameCol
                = new TableColumn<>("Name");
        NameCol.setMinWidth(100);
        NameCol.setMaxWidth(150);
        NameCol.setCellValueFactory(
                new PropertyValueFactory<>("Name"));
        NameCol.setCellFactory(TextFieldTableCell.<Landareak>forTableColumn());

        TableColumn<Landareak, String> Description
                = new TableColumn<>("Description");
        Description.setMinWidth(230);
        Description.setMaxWidth(280);
        Description.setCellValueFactory(
                new PropertyValueFactory<>("description"));
        Description.setCellFactory(TextFieldTableCell.<Landareak>forTableColumn());

        TableColumn<Landareak, String> Height = new TableColumn<>("Height");
        Height.setMinWidth(100);
        Height.setMaxWidth(150);
        Height.setCellValueFactory(
                new PropertyValueFactory<>("size"));
        Height.setCellFactory(TextFieldTableCell.<Landareak>forTableColumn());

        //Ez dut lortzen kolorea azaltzea, ez dakit zergatik ez duen funtzionatzen
        TableColumn<Landareak, String> Color = new TableColumn<>("Color");
        Color.setMinWidth(150);
        Color.setMaxWidth(190);
        Color.setCellValueFactory(
                new PropertyValueFactory<>("Color"));
        Color.setCellFactory(TextFieldTableCell.<Landareak>forTableColumn());

        TableColumn<Landareak, String> Flower
                = new TableColumn<>("Flowers");
        Flower.setMinWidth(100);
        Flower.setMaxWidth(150);

        Flower.setCellValueFactory(
                new PropertyValueFactory<>("flowers"));
        Flower.setCellFactory(TextFieldTableCell.<Landareak>forTableColumn());

        TableColumn<Landareak, String> CName
                = new TableColumn<>("Cientific Name");
        CName.setMinWidth(180);
        CName.setCellValueFactory(
                new PropertyValueFactory<>("CName"));
        CName.setCellFactory(TextFieldTableCell.<Landareak>forTableColumn());

        //Datuak kargatu
        data = Methods.datuak(taula);
        Methods.datuak(taula);

        table.setItems(data);
        table.getColumns().addAll(NameCol, Description, Height, Color, Flower, CName);

        final TextField addName = new TextField();
        addName.setPromptText("Izena");
        addName.setMaxWidth(NameCol.getPrefWidth());

        final TextField addDescription = new TextField();
        addDescription.setMaxWidth(Description.getPrefWidth());
        addDescription.setPromptText("Description");

        final TextField addColor = new TextField();
        addColor.setMaxWidth(Color.getPrefWidth());
        addColor.setPromptText("Color");

        final TextField addAvHeight = new TextField();
        addAvHeight.setMaxWidth(Height.getPrefWidth());
        addAvHeight.setPromptText("Average Height");

        final TextField addCName = new TextField();
        addCName.setMaxWidth(150);
        addCName.setPromptText("Cientific name");

        final RadioButton Flowers = new RadioButton();
        Flowers.setMaxWidth(Color.getPrefWidth());
        Flowers.setText("Flowers");

        final Button addButton = new Button("Add");
        addButton.setOnAction((ActionEvent e) -> {
            //objetu berria sortu
            if (addName.getText().equals("") || addDescription.getText().equals("") || addColor.getText().equals("") || addAvHeight.getText().equals("") || addCName.getText().equals("") || Flowers.getText().equals("")) {
                lab.setText("You should fill all the fields");
            } else {
                try {
                    Integer.parseInt(addAvHeight.getText());
                    Boolean flow;
                    if (Flowers.isSelected()) {
                        flow = true;
                    } else {
                        flow = false;
                    }
                    Landareak p = new Landareak(
                            addName.getText(),
                            addDescription.getText(),
                            addColor.getText(),
                            addAvHeight.getText(),
                            flow,
                            addCName.getText()
                    );

                    //Datu basera gehitu
                    Methods.landareaGehitu(p, taula);

                    addName.clear();
                    addDescription.clear();
                    addColor.clear();
                    addAvHeight.clear();
                    addCName.clear();
                    Flowers.setSelected(false);
                    lab.setText("");
                    //datuak kargatu berriz
                    data = Methods.datuak(taula);

                    table.setItems(data);
                } catch (NumberFormatException nf) {
                    lab.setText("Average height should be a number");
                }

            }
        });

        final Button removeButton = new Button("Delete selected");
        removeButton.setOnAction((ActionEvent e) -> {
            Landareak landare = table.getSelectionModel().getSelectedItem();

            //Hemen ezabatu objetua
            Methods.ezabatu(taula, landare.getCName());

            data.remove(landare);
            table.setItems(data);
        });

        hb.getChildren().addAll(addName, addDescription, addAvHeight, addColor, Flowers, addCName, addButton, removeButton);
        hb.setSpacing(3);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, lab, table, hb);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        stage.setOnCloseRequest((WindowEvent event) -> {
            try {
                try {
                    mongoKon.close();
                } catch (NullPointerException er) {
                }
            } catch (Exception ex) {
                Logger.getLogger(Koldosaurus_app.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        stage.setScene(scene);

        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
