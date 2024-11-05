package controllers.Admin.Auction;
import controllers.Kenza_Controllers.EncherDetailController;
import entities.Auction;
import entities.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import services.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class AdminAuction {

    @FXML
    private TableView<Auction> tv_auction;
    @FXML
    private TableColumn<Auction, LocalDate> fxDateD;

    @FXML
    private TableColumn<Auction, LocalDate> fxDateF;

    @FXML
    private TableColumn<Auction, String> fxNom;

    @FXML
    private TableColumn<Auction, Integer> fxPrixFinal;

    @FXML
    private TableColumn<Auction, Integer> fxPrixInitial;


    @FXML
    private Label Num_Arts;

    @FXML
    private Label Num_Auction;

    @FXML
    private Label Num_Events;

    @FXML
    private Label Num_Messages;

    @FXML
    private Label Num_Forum;

    @FXML
    private Label Num_Users;

    @FXML
    private ImageView logout;

    ServiceProduct p = new ServiceProduct();
    ServiceUser serviceUser = new ServiceUser();
    ServiceForumF serviceForum = new ServiceForumF();
    ServiceAuction serviceAuction = new ServiceAuction();
    ServiceDisscussion serviceDisscussion = new ServiceDisscussion();
    ServiceEvent serviceEvent = new ServiceEvent();

    public void initialize() throws SQLException {
        Num_Forum.setText(""+serviceForum.CountForums());
        Num_Arts.setText(String.valueOf(p.CountProduct()));
        Num_Users.setText(String.valueOf(serviceUser.CountUsers()));
        Num_Auction.setText(String.valueOf(serviceAuction.CountAuction()));
        Num_Messages.setText(String.valueOf(serviceDisscussion.CountDisc()));
        Num_Events.setText(String.valueOf(serviceEvent.CountMesg()));



        System.out.println("Welcome Auction");
        try {
            ObservableList<Auction> observableList= FXCollections.observableList(serviceAuction.afficher());
            tv_auction.setItems(observableList);
            fxNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            fxDateD.setCellValueFactory(new PropertyValueFactory<>("date_lancement"));
            fxDateF.setCellValueFactory(new PropertyValueFactory<>("date_cloture"));
            fxPrixInitial.setCellValueFactory(new PropertyValueFactory<>("prix_initial"));
            fxPrixFinal.setCellValueFactory(new PropertyValueFactory<>("prix_final"));

            System.out.println("impoted");
            System.out.println(observableList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private Parent loadEnchre() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/AuctionPages/EncherDetail.fxml"));
        EncherDetailController controller = new EncherDetailController();
        loader.setController(controller);
        controller.initData(tv_auction.getSelectionModel().getSelectedItem());
        Parent root = loader.load();
        return root;
    }
    @FXML
    void Go_To_Auction(MouseEvent event) {

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

    public void AfficherEncher(ActionEvent actionEvent) {
        try{
            Parent root = loadEnchre();
            fxPrixFinal.getTableView().getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void supprimerButton(ActionEvent actionEvent) throws SQLException {
        Auction selectedItem = tv_auction.getSelectionModel().getSelectedItem();
        if(selectedItem!=null){
            try {
                serviceAuction.supprimer(selectedItem);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            initialize();
        }
    }
}
