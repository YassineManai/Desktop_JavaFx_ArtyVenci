package controllers.Kenza_Controllers;


import controllers.Ali_Controllers.ProductController;
import controllers.Montaha_Controllers.AfficherEventView;
import controllers.Yassine_Controllers.HomeController;
import controllers.Yassine_Controllers.LoginController;
import controllers.Yassine_Controllers.LoginSuccess;
import entities.Auction;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import services.ServiceAuction;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class ModifierController{

    public ImageView bell;
    public ImageView usericon;
    public Label nav_name;
    public Button inscrire;
    public ImageView logouticon;
    ServiceAuction serviceAuction = new ServiceAuction();
    Preferences preferences = Preferences.userNodeForPackage(LoginController.class);

    @FXML
    private Label nomProduit;

    @FXML
    private TextField tf_nomAuction;
    @FXML
    private TextField fxprix_inital;
    @FXML
    private DatePicker tf_date;

    @FXML
    private DatePicker tf_dateC;
    @FXML
    private TextField id_auction;
    User CurrentUser = LoginController.userlogged;

    @FXML
    void ModifierAuction() {
        int id_enchere = Integer.parseInt(id_auction.getText());
        String nom_auction = tf_nomAuction.getText();
        String nom_produit = nomProduit.getText();
        int prix_initial = Integer.parseInt(fxprix_inital.getText());
        LocalDate date_lancement = tf_date.getValue();
        LocalDate date_cloture = tf_dateC.getValue();

        Auction editAuction = new Auction();
        editAuction.setId(id_enchere);
        editAuction.setNom(nom_auction);
        editAuction.setPrix_initial(prix_initial);
        editAuction.setDate_lancement(date_lancement);
        editAuction.setDate_cloture(date_cloture);

        try {
            serviceAuction.modifier(editAuction);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Edit User");
            alert.setHeaderText(null);
            alert.setContentText("Auction details updated successfully");
            alert.showAndWait();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/AuctionPages/artistEnchers.fxml"));
            Parent loginSuccessRoot = loader.load();
            Scene scene = tf_nomAuction.getScene();
            scene.setRoot(loginSuccessRoot);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error updating user details: " + e.getMessage());
            alert.showAndWait();
        }

    }

    public void initData(Auction auction) {
        nav_name.setText(CurrentUser.getUsername());
        inscrire.setVisible(false);
        bell.setVisible(true);
        usericon.setVisible(true);
        logouticon.setVisible(true);
        tf_nomAuction.setText(auction.getNom());
        try {
            nomProduit.setText(serviceAuction.loadProductFromDatabase(auction.getId()));
            fxprix_inital.setText(String.valueOf(auction.getPrix_initial()));
            tf_date.setValue(LocalDate.parse(String.valueOf(auction.getDate_lancement())));
            tf_dateC.setValue(LocalDate.parse(String.valueOf(auction.getDate_cloture())));
            id_auction.setText(String.valueOf(auction.getId()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void Go_To_Home(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/Home.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);

        HomeController homeController = loader.getController();
        homeController.initData(CurrentUser);
    }

    public void Go_To_Product(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/ProductPages/Product.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
        ProductController productController = loader.getController();
        productController.initUser(CurrentUser);
    }

    public void Go_To_Auction(ActionEvent actionEvent) {
    }

    public void Go_To_Forum(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/ForumPages/Member/AfficherForumMembre.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
    }

    public void Go_To_Event(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/EventPages/afficher-event-view.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
        AfficherEventView afficherEventView = loader.getController();
        afficherEventView.initData(CurrentUser);
    }

    public void Go_To_Message(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/MessageInterface/Homepage.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
    }

    public void ProfileVisit(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/LoginSuccess.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);

        LoginSuccess loginSuccess = loader.getController();
        loginSuccess.initData(CurrentUser);
    }

    public void sinscrire(ActionEvent actionEvent) {

    }

    public void Logout(MouseEvent mouseEvent) throws IOException {
        preferences.remove("username");
        preferences.remove("Password");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/Login_User.fxml"));
        Parent loginSuccessRoot = loader.load();

        LoginController.userlogged = null;
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText(null);
        alert.show();
    }
}
