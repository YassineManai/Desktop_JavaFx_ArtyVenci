package controllers.Admin.Event;
import entities.Auction;
import entities.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import services.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class Admin_Event {
    @FXML
    private Button Ajouter_E;

    @FXML
    private Button Ajouter_W;

    @FXML
    private Label Num_Arts;

    @FXML
    private Label Num_Auction;

    @FXML
    private Label Num_Events;

    @FXML
    private Label Num_Forum;

    @FXML
    private Label Num_Messages;

    @FXML
    private Label Num_Users;

    @FXML
    private Button Supprimer;

    @FXML
    private ImageView logout;

    @FXML
    private TableColumn<Event, String> tv_Lieu;

    @FXML
    private TableColumn<Event, Date> tv_date;

    @FXML
    private TableView<Event> tv_event;

    @FXML
    private TableColumn<Event, String> tv_nomE;

    @FXML
    private TableColumn<Event,Double> tv_prix;

    ServiceUser serviceUser = new ServiceUser();
    ServiceForumF serviceForum = new ServiceForumF();
    ServiceAuction serviceAuction = new ServiceAuction();
    ServiceProduct p = new ServiceProduct();
    ServiceEvent serviceEvent = new ServiceEvent();
    public void initialize() throws SQLException {
        Num_Users.setText(String.valueOf(serviceUser.CountUsers()));
        Num_Arts.setText(String.valueOf(p.CountProduct()));
        Num_Forum.setText(""+serviceForum.CountForums());
        Num_Auction.setText(String.valueOf(serviceAuction.CountAuction()));
        System.out.println("Welcome Event");

        ObservableList<Event> observableList= FXCollections.observableList(serviceEvent.afficher());
        tv_event.setItems(observableList);
        tv_nomE.setCellValueFactory(new PropertyValueFactory<>("E_Name"));
        tv_Lieu.setCellValueFactory(new PropertyValueFactory<>("Place"));
        tv_date.setCellValueFactory(new PropertyValueFactory<>("E_Date"));
        tv_prix.setCellValueFactory(new PropertyValueFactory<>("Ticket_Price"));

        System.out.println("impoted");
        System.out.println(observableList);

    }

    @FXML
    void Go_To_Auction(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin_Interface/Admin_Auction.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = Num_Arts.getScene();
        scene.setRoot(loginSuccessRoot);

    }

    @FXML
    void Go_To_Event(MouseEvent event) {
    }



    @FXML
    void Go_To_Forum(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin_Interface/Admin_Forum.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = Num_Arts.getScene();
        scene.setRoot(loginSuccessRoot);

    }

    @FXML
    void Go_To_Messages(MouseEvent event)throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin_Interface/Admin_Message.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = Num_Arts.getScene();
        scene.setRoot(loginSuccessRoot);

    }

    @FXML
    void Go_To_Product(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin_Interface/Admin_Produit.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = Num_Arts.getScene();
        scene.setRoot(loginSuccessRoot);
    }

    @FXML
    void Go_To_User(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin_Interface/AdminUI.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = Num_Arts.getScene();
        scene.setRoot(loginSuccessRoot);

    }

    @FXML
    void logout(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/Login_User.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = Num_Arts.getScene();
        scene.setRoot(loginSuccessRoot);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText(null);
        alert.show();}

}
