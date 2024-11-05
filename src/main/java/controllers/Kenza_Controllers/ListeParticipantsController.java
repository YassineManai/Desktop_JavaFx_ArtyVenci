package controllers.Kenza_Controllers;


import entities.Auction;
import entities.Auction_participant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.ServiceParticipant;

import javax.print.attribute.DateTimeSyntax;
import java.sql.SQLException;

public class ListeParticipantsController {

    ServiceParticipant serviceParticipant= new ServiceParticipant();
    @FXML
    private TableColumn<Auction_participant, String> NomParticipant;

    @FXML
    private TableColumn<Auction_participant, Float> prix;

    @FXML
    private TableColumn<Auction_participant, DateTimeSyntax> temps;
    @FXML
    private TableView<Auction_participant> tv_Participant;

    public void initialize(int id_auction) throws SQLException {

        try {
            ObservableList<Auction_participant> observableList = FXCollections.observableList(serviceParticipant.afficher());
            tv_Participant.setItems(observableList);
            NomParticipant.setCellValueFactory(new PropertyValueFactory<>("Username"));
            prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
            temps.setCellValueFactory(new PropertyValueFactory<>("date"));
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



}
