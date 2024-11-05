package controllers.Kenza_Controllers;


import entities.Auction_participant;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;  // Add this import statement
import services.ServiceParticipant;


import java.awt.*;

public class ParticipantController {

    ServiceParticipant serviceParticipant = new ServiceParticipant();

    @FXML
    private HBox hbox;

    @FXML
    private Label date;

    @FXML
    private Label nom_participant;

    @FXML
    private Label prix_participant;


    public void initData(Auction_participant participant) {
        System.out.println(participant.getId_participant());
        System.out.println(participant.getName());
        nom_participant.setText(participant.getName());
        prix_participant.setText(String.valueOf(participant.getPrix()));
        date.setText(String.valueOf(participant.getDate()));
    }
}
