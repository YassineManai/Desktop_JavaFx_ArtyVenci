package controllers.Kenza_Controllers;

import controllers.Ali_Controllers.ProductController;
import controllers.Montaha_Controllers.AfficherEventView;
import controllers.Yassine_Controllers.HomeController;
import controllers.Yassine_Controllers.LoginController;
import controllers.Yassine_Controllers.LoginSuccess;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;

import entities.Auction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import services.ServiceAuction;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.time.LocalDate;
import java.util.prefs.Preferences;

public class AjouterAuctionController implements Initializable {

    public ImageView bell;
    public ImageView usericon;
    public Label nav_name;
    public Button inscrire;
    public ImageView logouticon;
    ServiceAuction serviceAuction = new ServiceAuction();
    Preferences preferences = Preferences.userNodeForPackage(LoginController.class);
    User CurrentUser = LoginController.userlogged;

    @FXML
    private TextField tf_nomAuction;
    @FXML
    private ChoiceBox<String> tf_produit;
    @FXML
    private Spinner<Integer> tf_prix_initial ;
    @FXML
    private DatePicker tf_date;

    @FXML
    private DatePicker tf_dateC;

    int id_Artist = CurrentUser.getId_User();



    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> productNames = loadProductValuesFromDatabase(id_Artist);

        ObservableList<String> products = FXCollections.observableArrayList(productNames);
        tf_produit.setItems(products);

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory
                .IntegerSpinnerValueFactory(1, 1000, 1);
        tf_prix_initial.setValueFactory(valueFactory);

        nav_name.setText(CurrentUser.getUsername());
        inscrire.setVisible(false);
        bell.setVisible(true);
        usericon.setVisible(true);
        logouticon.setVisible(true);

    }



    private List<String> loadProductValuesFromDatabase(int userId) {
        try {
            ServiceAuction serviceAuction = new ServiceAuction();
            List<String> productNames = serviceAuction.loadProductValuesFromDatabase(userId);

            return productNames;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    @FXML
    void AjouterAuction() {
        LocalDate dateLancement = tf_date.getValue();
        LocalDate dateCloture = tf_dateC.getValue();
        try {
            String selectedProductName = tf_produit.getValue();
            List<String> productNames = loadProductValuesFromDatabase(id_Artist);

            if (selectedProductName != null && productNames.contains(selectedProductName)) {
                int productId = serviceAuction.getProductID(selectedProductName);

                if (verifierDate(tf_date, tf_dateC)) {
                    serviceAuction.ajouter_by_artist(new Auction(tf_nomAuction.getText(), dateCloture, dateLancement, Integer.parseInt(tf_prix_initial.getValue().toString()), productId), id_Artist);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Enchère ajoutée");
                    alert.showAndWait();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/AuctionPages/artistEnchers.fxml"));
                    Parent loginSuccessRoot = loader.load();
                    Scene scene = tf_nomAuction.getScene();
                    scene.setRoot(loginSuccessRoot);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Date non valide !");
                    alert.setContentText("La date de l'enchère n'est pas valide.");
                    alert.showAndWait();
                }
            } else {
                System.out.println("Selected product not found.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Selected product not found.");
                alert.showAndWait();
            }
        } catch (SQLException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    public boolean verifierDate(DatePicker tfDate, DatePicker tfDateC) {
        LocalDate dateDebut = tfDate.getValue();
        LocalDate dateFin = tfDateC.getValue();

        LocalDate dateActuelle = LocalDate.now();

        if (dateDebut != null && (dateDebut.isAfter(dateActuelle) ||dateActuelle.isEqual(dateDebut))) {
            if (dateFin != null && dateFin.isAfter(dateDebut)) {
                return true;
            }
        }
        return false;
    }


    @FXML
    public void retourner(javafx.scene.input.MouseEvent mouseEvent) throws IOException{
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/AuctionPages/artistEnchers.fxml"));
                Parent loginSuccessRoot = loader.load();
                Scene scene = tf_nomAuction.getScene();
                scene.setRoot(loginSuccessRoot);

            }catch (IOException e){
                System.out.println(e.getMessage());
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
