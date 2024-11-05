package controllers.Asma_Controllers;

import controllers.Ali_Controllers.ProductController;
import controllers.Montaha_Controllers.AfficherEventView;
import controllers.Yassine_Controllers.HomeController;
import controllers.Yassine_Controllers.LoginController;
import controllers.Yassine_Controllers.LoginSuccess;
import entities.Disscussion;
import entities.Message;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import services.ServiceDisscussion;
import services.ServiceMessage;
import services.ServiceUser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class MessagesController implements Initializable {
    public ImageView bell;
    public ImageView usericon;
    public Label nav_name;
    public Button inscrire;
    public ImageView logouticon;
    Preferences preferences = Preferences.userNodeForPackage(LoginController.class);
    @FXML
    public Button signal;

    @FXML
    public Button retour;

    @FXML
    public VBox Msg;

    @FXML
    public TextField msg;

    @FXML
    public ScrollPane messages;

    //taw mba3d
    @FXML
    public Button notification;

    //déclarations des services
    public ServiceMessage serviceMessage = new ServiceMessage();
    public ServiceDisscussion serviceDisscussion = new ServiceDisscussion();
    public ServiceUser serviceUser = new ServiceUser();

    //autres variables
    public Disscussion discussion;
//    public User current_user = new User() ;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherMessages() ;
        nav_name.setText(CurrentUser.getUsername());
        inscrire.setVisible(false);
        bell.setVisible(true);
        usericon.setVisible(true);
        logouticon.setVisible(true);
    }

    //Afficher les Messages
    public void setData(Disscussion discussion) {
        this.discussion = discussion;
        System.out.println(this.discussion);
    }

//
//public void afficherMessages() {
//    try {
//        // Chargez et affichez les messages de la discussion sélectionnée
//        if (discussion != null) {
//            int discussionId = discussion.getIdDis();
//            ObservableList<Message> messages = FXCollections.observableList(serviceMessage.afficheridDis(discussionId));
//            for (Message mesg : messages) {
//                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/message.fxml"));
//                VBox cardBox = fxmlLoader.load();
//                MessageController msgController = fxmlLoader.getController();
//
//                // Déterminez si le message appartient à l'utilisateur actuel ou non
//                boolean isCurrentUser = (mesg.getIdSender()==currentUserId); // À adapter selon votre logique
//
//                // Passez le booléen à la méthode setData()
//                msgController.setData(mesg, isCurrentUser);
//
//                Msg.getChildren().add(cardBox);
//            }
//        }
////            else {
////                showAlert(Alert.AlertType.ERROR, "Error", "Discussion not found");
////            }
//    } catch (SQLException | IOException e) {
//        showAlert(Alert.AlertType.ERROR, "Eror", "Error while charging messages: " + e.getMessage());
//    }
//}

//    public void afficherMessages() {
//        try {
//            // Chargez et affichez les messages de la discussion sélectionnée
//            if (discussion != null) {
//                int discussionId = discussion.getIdDis();
//                ObservableList<Message> messages = FXCollections.observableList(serviceMessage.afficheridDis(discussionId));
//                for (Message mesg : messages) {
//                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/message.fxml"));
//                    VBox cardBox = fxmlLoader.load();
//                    MessageController msgController = fxmlLoader.getController();
//
//                    // Déterminez si le message appartient à l'utilisateur actuel ou non
//                    boolean isCurrentUser = (mesg.getIdSender() == currentUserId); // À adapter selon votre logique
//
//                    // Passez le booléen à la méthode setData()
//                    msgController.setData(mesg, isCurrentUser);
//
//                    Msg.getChildren().add(cardBox);
//                }
//            }
//        } catch (SQLException | IOException e) {
//            showAlert(Alert.AlertType.ERROR, "Error", "Error while charging messages: " + e.getMessage());
//        }
//    }

//    public void afficherMessages() {
//        try {
//            if (discussion != null) {
//                int discussionId = discussion.getIdDis();
//                ObservableList<Message> messages = FXCollections.observableList(serviceMessage.afficheridDis(discussionId));
//                for (Message mesg : messages) {
//                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/message.fxml"));
//                    VBox cardBox = fxmlLoader.load();
//                    MessageController msgController = fxmlLoader.getController();
//
//                    // Détermine si le message appartient à l'utilisateur actuel
//                    boolean isCurrentUser = (mesg.getIdSender() == currentUserId);
//
//                    // Initialise le contenu du message
//                    msgController.initializeMessage(mesg, isCurrentUser);
//
//                    Msg.getChildren().add(cardBox);
//                }
//            }
//        } catch (SQLException | IOException e) {
//            showAlert(Alert.AlertType.ERROR, "Error", "Error while charging messages: " + e.getMessage());
//        }
//    }
//.

    public void afficherMessages() {
        try {
            if (discussion != null) {
                int discussionId = discussion.getIdDis();
                ObservableList<Message> messages = FXCollections.observableList(serviceMessage.afficheridDis(discussionId));

                // Clear existing messages before loading new ones
                Msg.getChildren().clear();

                for (Message message : messages) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Interfaces/MessageInterface/message.fxml"));
                    VBox cardBox = fxmlLoader.load();
                    MessageController msgController = fxmlLoader.getController();
                    System.out.println(message);

                    // Determine if the message belongs to the current user
                    boolean isCurrentUser = (message.getIdSender() == CurrentUser.getId_User());

                    // Initialize the message content and style
                    msgController.initializeMessage(message, isCurrentUser);

                    // Add the message component to the VBox
                    Msg.getChildren().add(cardBox);
                }
            }
        } catch (SQLException | IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error while loading messages: " + e.getMessage());
        }
    }








    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void EnvoyerMsg(ActionEvent event) {
        try {
//            Disscussion selecteddis = new Disscussion() ;
            String messageContent = msg.getText().trim();
            if (!messageContent.isEmpty()) {
//                serviceMessage.ajouter(new Message(sender.getId_User(), messageContent, null));
                DiscussionController discController = new DiscussionController();
                System.out.println(discussion);
                discController.setData(discussion);
                serviceMessage.ajouter(new Message(CurrentUser.getId_User(), discussion.getIdDis(), messageContent, null));
//            showAlert(Alert.AlertType.INFORMATION, "Succès d'envoi", "Message envoyé");
                msg.clear();
                // Notifications
                try {
                    InputStream is = getClass().getResourceAsStream("/images/bell.png");
                    Image image = new Image(is);
                    Notifications notifications = Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("New Message");
                    notifications.title("New Message in ArtyVenci");
                    notifications.hideAfter(Duration.seconds(5));
                    notifications.show();
                } catch (Exception e) {
                    e.printStackTrace();
                    // Handle the exception appropriately, such as showing an error message
                }


            } else {
                showAlert(Alert.AlertType.WARNING, "Message vide", "Le contenu du message est vide.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de l'envoi du message: " + e.getMessage());
            System.out.println(e.getMessage());
        }


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/MessageInterface/messages.fxml"));
            Parent root = loader.load();
            MessagesController messagesController = loader.getController();
            messagesController.setData(discussion); // Set discussion data in MessagesController
            messagesController.afficherMessages();

            // Get the current stage and set its scene to the newly loaded page
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    @FXML
    public void signalerMessage(ActionEvent event) throws SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/MessageInterface/SignalerMessage.fxml"));
            Parent root = loader.load();
            SignalerMessageController signalerMessageController = loader.getController();
            signalerMessageController.setData(discussion); // Set the discussion object
            Stage stage = (Stage) returnbtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Signal");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Error", "Error while navigating to signal page : " + e.getMessage());
        }
    }

    @FXML
    public void deleteDiscussion(ActionEvent event) throws SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/MessageInterface/deleteDiscussion.fxml"));
            Parent root = loader.load();
            DeleteDiscussionController deleteDiscussionController = loader.getController();
            deleteDiscussionController.setData(discussion); // Set the discussion object
            Stage stage = (Stage) returnbtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Delete");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Error", "Error while navigating to signal page : " + e.getMessage());
        }
    }

//    @FXML
//    public void signalerMessage(ActionEvent event) throws SQLException {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignalerMessage.fxml"));
//            Parent root = loader.load();
//            Stage stage = (Stage) returnbtn.getScene().getWindow();
//            stage.setScene(new Scene(root));
//            stage.setTitle("Signal");
//            SignalerMessageController signalerMessageController = loader.getController();
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//            showAlert(Alert.AlertType.ERROR, "Error", "Error while navigating to signal page : " + e.getMessage());
//        }


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

