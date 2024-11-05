package controllers.Asma_Controllers;

import controllers.Ali_Controllers.ProductController;
import controllers.Montaha_Controllers.AfficherEventView;
import controllers.Yassine_Controllers.HomeController;
import controllers.Yassine_Controllers.LoginController;
import controllers.Yassine_Controllers.LoginSuccess;
import entities.Disscussion;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ServiceDisscussion;

import java.io.IOException;
import java.sql.SQLException;
import java.util.prefs.Preferences;

public class SignalerMessageController {
    public ImageView bell;
    public ImageView usericon;
    public Label nav_name;
    public Button inscrire;
    public ImageView logouticon;
    Preferences preferences = Preferences.userNodeForPackage(LoginController.class);
    @FXML
    private RadioButton Contenu_inapproprie;

    @FXML
    private RadioButton Contenu_offensant;

    @FXML
    private RadioButton Faux_contenu;

    @FXML
    private RadioButton harcelement;

    @FXML
    private Button signaler;

    @FXML
    private RadioButton spam;

    //autres variables
    public Disscussion discussion;
    String raison ;
    User CurrentUser = LoginController.userlogged;

    @FXML
    private Button returnbtn;

    @FXML
    void returnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/MessageInterface/HomePage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) returnbtn.getScene().getWindow(); // Get the current stage
            stage.setScene(new Scene(root));
            stage.setTitle("Home Page");
            HomepageController homepageController = loader.getController();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Erreur", "Error while Navigating to HomePage: " + e.getMessage());
        }
    }

    public void setData(Disscussion discussion) {
        this.discussion = discussion;
        nav_name.setText(CurrentUser.getUsername());
        inscrire.setVisible(false);
        bell.setVisible(true);
        usericon.setVisible(true);
        logouticon.setVisible(true);
    }

//    @FXML
//    void Signaler() throws IOException, SQLException {
//
//        if (discussion != null) {
//            int discussionId = discussion.getIdDis(); // Supposons que getId() renvoie l'ID de la discussion
//
//
//            RadioButton selectedRadioButton = null;
//
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Avertissement");
//
//            // Vérifie quel bouton radio est sélectionné
//            if (Contenu_inapproprie.isSelected()) {
//                selectedRadioButton = Contenu_inapproprie;
//                raison = "Contenu_inapproprie";
//            } else if (Contenu_offensant.isSelected()) {
//                selectedRadioButton = Contenu_offensant;
//                raison = "Contenu_offensant";
//            } else if (Faux_contenu.isSelected()) {
//                selectedRadioButton = Faux_contenu;
//                raison = "Faux_contenu";
//            } else if (harcelement.isSelected()) {
//                selectedRadioButton = harcelement;
//                raison = "harcelement";
//            } else if (spam.isSelected()) {
//                selectedRadioButton = spam;
//                raison = "spam";
//            }
//
//
//            ServiceDisscussion sd = new ServiceDisscussion();
//            sd.signal(sd.getDiscussionById(discussionId), raison);
//
//            if (selectedRadioButton == null) {
//                // Aucun bouton radio sélectionné
//                alert.setContentText("Veuillez sélectionner une raison pour signaler le message.");
//            } else {
//                // Bouton radio sélectionné, affichage de l'alerte avec la raison
//                alert.setContentText("Une information sera envoyée à l'administrateur pour la raison suivante :\n"
//                        + selectedRadioButton.getText());
//            }
//            alert.showAndWait();
//
////        else {
////            showAlert(Alert.AlertType.ERROR, "Erreur", "Discussion non sélectionnée");
////        }
//
////        try {
////            FXMLLoader loader = new FXMLLoader(getClass().getResource("/messages.fxml"));
////            Parent root = loader.load();
////            MessagesController messagesController = loader.getController();
////            messagesController.setData(discussion); // Définissez les données de discussion dans MessagesController
////            messagesController.afficherMessages();
////
////            // Récupérez la scène actuelle et remplacez sa racine par la nouvelle page chargée
////            Scene scene = signaler.getScene(); // Obtenez la scène actuelle
////            scene.setRoot(root); // Remplacez la racine de la scène par la nouvelle page chargée
////
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//        }
//    }

    @FXML
    void Signaler() throws IOException, SQLException {
        System.out.println("Signaler method invoked");
        if (discussion != null) {
            System.out.println("Discussion is not null");
            int discussionId = discussion.getIdDis(); // Assuming getId() returns the ID of the discussion

            RadioButton selectedRadioButton = null;

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Avertissement");

            // Check which radio button is selected
            if (Contenu_inapproprie.isSelected()) {
                selectedRadioButton = Contenu_inapproprie;
                raison = "Contenu_inapproprie";
            } else if (Contenu_offensant.isSelected()) {
                selectedRadioButton = Contenu_offensant;
                raison = "Contenu_offensant";
            } else if (Faux_contenu.isSelected()) {
                selectedRadioButton = Faux_contenu;
                raison = "Faux_contenu";
            } else if (harcelement.isSelected()) {
                selectedRadioButton = harcelement;
                raison = "harcelement";
            } else if (spam.isSelected()) {
                selectedRadioButton = spam;
                raison = "spam";
            }

            ServiceDisscussion sd = new ServiceDisscussion();
            sd.signal(sd.getDiscussionById(discussionId), raison);

            if (selectedRadioButton == null) {
                // No radio button selected
                System.out.println("No radio button selected");
                alert.setContentText("Veuillez sélectionner une raison pour signaler le message.");
            } else {
                // Radio button selected, display the alert
                System.out.println("Radio button selected: " + selectedRadioButton.getText());
                alert.setContentText("Une information sera envoyée à l'administrateur pour la raison suivante :\n"
                        + selectedRadioButton.getText());
            }
            alert.showAndWait();
        } else {
            // Discussion is null
            System.out.println("Discussion is null");
            showAlert(Alert.AlertType.ERROR, "Erreur", "Discussion non sélectionnée");
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/MessageInterface/messages.fxml"));
            Parent root = loader.load();
            MessagesController messagesController = loader.getController();
            messagesController.setData(discussion); // Définissez les données de discussion dans MessagesController
            messagesController.afficherMessages();

            // Récupérez la scène actuelle et remplacez sa racine par la nouvelle page chargée
           Scene scene = signaler.getScene(); // Obtenez la scène actuelle
            scene.setRoot(root); // Remplacez la racine de la scène par la nouvelle page chargée

        } catch (IOException e) {
            e.printStackTrace();
       }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
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

    public void Go_To_Auction(ActionEvent actionEvent) throws IOException {

        if (CurrentUser.getRole().equals("Member")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/AuctionPages/Enchers.fxml"));
            Parent loginSuccessRoot = loader.load();
            Scene scene = nav_name.getScene();
            scene.setRoot(loginSuccessRoot);

        }
        else if
        (CurrentUser.getRole().equals("Artist")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/AuctionPages/artistEnchers.fxml"));
            Parent loginSuccessRoot = loader.load();
            Scene scene = nav_name.getScene();
            scene.setRoot(loginSuccessRoot);
        }

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
