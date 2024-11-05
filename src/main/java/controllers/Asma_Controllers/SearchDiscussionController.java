package controllers.Asma_Controllers;

import controllers.Ali_Controllers.ProductController;
import controllers.Montaha_Controllers.AfficherEventView;
import controllers.Yassine_Controllers.HomeController;
import controllers.Yassine_Controllers.LoginController;
import controllers.Yassine_Controllers.LoginSuccess;
import entities.Disscussion;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.ServiceDisscussion;
import services.ServiceMessage;
import services.ServiceUser;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class SearchDiscussionController {
    public ImageView bell;
    public ImageView usericon;
    public Label nav_name;
    public Button inscrire;
    public ImageView logouticon;
    Preferences preferences = Preferences.userNodeForPackage(LoginController.class);
    @FXML
    private Button chercher;

    @FXML
    private VBox discussionstrouvees;

    @FXML
    private ScrollPane discussionstrouves;

    @FXML
    private TextField username;

    //Services
    private ServiceMessage serviceMessage = new ServiceMessage();
    private ServiceDisscussion serviceDisscussion = new ServiceDisscussion();
    private ServiceUser serviceUser = new ServiceUser();

    // Déclarations des autres variables
    private User receiver = new User();

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


    public void initialize() {

        nav_name.setText(CurrentUser.getUsername());
        inscrire.setVisible(false);
        bell.setVisible(true);
        usericon.setVisible(true);
        logouticon.setVisible(true);
    }

    @FXML
    void afficherDiscussionstrouvees(MouseEvent event) {
            try {
                ObservableList<Disscussion> discussions = FXCollections.observableList(chercherDiscussion());
                if (discussions.isEmpty()) {
                    showAlert(Alert.AlertType.INFORMATION, "Discussion not found", "Discussion not found ");
                } else {
                    for (Disscussion discussion : discussions) {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Interfaces/MessageInterface/discussion.fxml"));
                        HBox cardBox = fxmlLoader.load();
                        DiscussionController discController = fxmlLoader.getController();
                        discController.setData(discussion);
                        discussionstrouvees.getChildren().add(cardBox);
                        // Ajoutez un gestionnaire d'événements pour chaque HBox
                        cardBox.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/MessageInterface/messages.fxml"));
                                    Parent root = loader.load();
                                    MessagesController messagesController = loader.getController();
                                    messagesController.setData(discussion); // Définissez les données de discussion dans MessagesController
                                    messagesController.afficherMessages();

                                    // Récupérez la scène actuelle et remplacez sa racine par la nouvelle page chargée
                                    Scene scene = discussionstrouvees.getScene(); // Obtenez la scène actuelle
                                    scene.setRoot(root); // Remplacez la racine de la scène par la nouvelle page chargée

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            } catch (SQLException | IOException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Error while deleting the discussion: " + e.getMessage());
            }
    }

    @FXML
    public List<Disscussion> chercherDiscussion () throws SQLException {
        discussionstrouvees.getChildren().clear();
        List<Disscussion> disTrouvees = new ArrayList<>();
        String userName = username.getText().trim();
        if (!userName.isEmpty()) {
            receiver = serviceUser.GetUserFromUsername(userName);
            if (receiver != null) {
                try {
                    Disscussion d = serviceDisscussion.getDiscussionByReceiverId(receiver.getId_User() , CurrentUser.getId_User());
                    if (d != null) {
                        disTrouvees.add(d);
//                        System.out.println(disTrouvees);
                        return disTrouvees;
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error", "Discussion not found");
                    }
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Error: " + e.getMessage());
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "User not found");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "write the username");
        }
        System.out.println(disTrouvees);
        return disTrouvees;
    }

private void showAlert (Alert.AlertType alertType, String title, String content){
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
