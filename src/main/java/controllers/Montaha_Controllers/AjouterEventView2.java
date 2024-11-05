package controllers.Montaha_Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import entities.Event;
import services.ServiceEvent;

import java.sql.Date;

public class AjouterEventView2 {
    @FXML
    private AnchorPane anchore;

    @FXML
    private GridPane grid;
    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfplace;
    @FXML
    private DatePicker dpdate;
    @FXML
    private TextField tfprix;
    @FXML
    private TextField tfimage;
    ServiceEvent serviceEvent=new ServiceEvent();
    int idModifier=0;
    @FXML
    void ajouterEvenement(ActionEvent event) {
        Event e=new Event();
        e.setE_Name(tfnom.getText());
        e.setPlace(tfplace.getText());
        e.setImage(tfimage.getText());
        e.setTicket_Price(Double.parseDouble(tfprix.getText()));
        e.setE_Date(Date.valueOf(dpdate.getValue()));
        serviceEvent.ajouter(e);

    }
    @FXML
    void uploadImage(ActionEvent event) {

    }
}
