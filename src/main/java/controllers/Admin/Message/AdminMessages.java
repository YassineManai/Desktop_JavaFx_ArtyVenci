package controllers.Admin.Message;

import entities.Auction;
import entities.Disscussion;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
public class AdminMessages {


    @FXML
    private TableView<Disscussion> tv_discussions;


    @FXML
    private Label Num_Arts;

    @FXML
    private Label Num_Auction;

    @FXML
    private Label Num_Events;

    @FXML
    private TableColumn<?, ?> raison_de_signalement;

    @FXML
    private TableColumn<?, ?> receiver;
//
//    @FXML
//    private TableColumn<?, ?> sender;

    @FXML
    private Label Num_Forum;

    @FXML
    private Button delete;

    @FXML
    private Label Num_Messages ;

    @FXML
    private Label Num_Users;

    @FXML
    private ImageView logout;

    ServiceUser serviceUser = new ServiceUser();
    ServiceDisscussion serviceDisscussion = new ServiceDisscussion();
    ServiceProduct p = new ServiceProduct();

    ServiceForumF serviceForum = new ServiceForumF();
    ServiceAuction serviceAuction = new ServiceAuction();

    ServiceEvent serviceEvent = new ServiceEvent();
    public void initialize() {

        try {
            ObservableList<Disscussion> observableList = FXCollections.observableArrayList(serviceDisscussion.afficherSig());
            tv_discussions.setItems(observableList);
            Num_Forum.setText(""+serviceForum.CountForums());
            Num_Arts.setText(String.valueOf(p.CountProduct()));
            Num_Users.setText(String.valueOf(serviceUser.CountUsers()));
            Num_Auction.setText(String.valueOf(serviceAuction.CountAuction()));
            Num_Messages.setText(String.valueOf(serviceDisscussion.CountDisc()));
            Num_Events.setText(String.valueOf(serviceEvent.CountMesg()));

            // Adding a new column for sender username
            TableColumn<Disscussion, String> senderUsernameColumn = new TableColumn<>("Sender Username");
            senderUsernameColumn.setCellValueFactory(cellData -> {
                int senderId = cellData.getValue().getIdSender();
//                System.out.println("senderId: " + senderId);
                String username = serviceUser.GetUserById(senderId).getUsername();
//                System.out.println("username: " + username);
                return new SimpleStringProperty(username);
            });
            tv_discussions.getColumns().add(senderUsernameColumn);


            // Adding a new column for sender username
            TableColumn<Disscussion, String> ReceiverUsernameColumn = new TableColumn<>("Receiver Username");
            ReceiverUsernameColumn.setCellValueFactory(cellData -> {
                int receiverId = cellData.getValue().getIdReceiver();
//                System.out.println("receiverId: " + receiverId);
//                String username = serviceUser.GetUserById(receiverId).getUsername();
                String username = serviceUser.GetUserById(cellData.getValue().getIdReceiver()).getUsername();
//                System.out.println("username: " + username);
                return new SimpleStringProperty(username);
            });
            tv_discussions.getColumns().add(ReceiverUsernameColumn);

            raison_de_signalement.setCellValueFactory(new PropertyValueFactory<>("Signal"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void annulerSignalButton(ActionEvent actionEvent) throws SQLException {
        Disscussion selectedItem = tv_discussions.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                serviceDisscussion.annulerSignal(selectedItem);
                initialize(); // Actualiser la liste après la suppression
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void DeleteButton(ActionEvent event) {
        Disscussion selectedItem = tv_discussions.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                serviceDisscussion.supprimer(selectedItem);
                initialize(); // Actualiser la liste après la suppression
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void Go_To_Auction(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin_Interface/Admin_Auction.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = Num_Arts.getScene();
        scene.setRoot(loginSuccessRoot);

    }

    @FXML
    void Go_To_Event(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin_Interface/Admin_Event.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = Num_Arts.getScene();
        scene.setRoot(loginSuccessRoot);
    }



    @FXML
    void Go_To_Forum(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin_Interface/Admin_Forum.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = Num_Arts.getScene();
        scene.setRoot(loginSuccessRoot);

    }

    @FXML
    void Go_To_Messages(MouseEvent event){

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
