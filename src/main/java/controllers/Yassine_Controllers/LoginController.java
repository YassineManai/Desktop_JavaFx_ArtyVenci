package controllers.Yassine_Controllers;

import controllers.Admin.User.AdminController;
import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ServiceUser;

import java.io.IOException;
import java.util.prefs.Preferences;

public class LoginController {
    public Label resetPassword;
    public ImageView ResterConecté;
    @FXML
    private PasswordField id_Password;

    @FXML
    private TextField id_Username;
    public static User userlogged;

    ServiceUser serviceUser = new ServiceUser() ;
    Preferences preferences = Preferences.userNodeForPackage(LoginController.class);
    public void GoBackClick(javafx.scene.input.MouseEvent mouseEvent) throws IOException {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/Home.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = id_Password.getScene();
        scene.setRoot(loginSuccessRoot);
        System.out.println(userlogged);
        HomeController homeController = loader.getController();
        homeController.initData(userlogged);
    }

    public void initialize() throws IOException {
        ResterConecté.setVisible(false);

        String savedUsername = preferences.get("username", null);
        String savedPassword = preferences.get("Password", null);

    }

    public void Login(javafx.event.ActionEvent actionEvent) throws IOException {
        String username = id_Username.getText();
        String password = id_Password.getText();

        try {
            // Authenticate the user
            User user = serviceUser.GetUser(username, password);
           userlogged = user ;
            System.out.println(user);
            if (user != null) {
                FXMLLoader loader;
                Parent root;

                if (user.getRole().equals("Admin")) {
                    loader = new FXMLLoader(getClass().getResource("/Admin_Interface/AdminUI.fxml"));
                    root = loader.load();
                    AdminController adminController = loader.getController();
                    adminController.initData(user);
                } else {
                    loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/Home.fxml"));
                    root = loader.load();
                    HomeController homeController = loader.getController();
                    homeController.initData(user);

                }

                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                // Close the login scene
                stage.close();

                // Set the product scene to the stage
                stage.setScene(scene);
                stage.show();
            } else {
                // Show an error message for invalid username or password
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Invalid Username or Password");
                alert.showAndWait();
            }
        } catch (Exception e) {
            // Handle any exceptions
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void MoveToSignup(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/SignUp_User.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = id_Password.getScene(); // Get the scene from any node in the current scene
        scene.setRoot(loginSuccessRoot); // Set the root of the current scene to the LoginSuccess scene
    }

    public void resetPassword(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/Reset_Account.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = id_Password.getScene(); // Get the scene from any node in the current scene
        scene.setRoot(loginSuccessRoot); // Set the root of the current scene to the LoginSuccess scene
    }

    public void ResterConecteButton(MouseEvent mouseEvent) {
        ResterConecté.setVisible(true);

        preferences.put("username", id_Username.getText());
        preferences.put("Password", id_Password.getText());

    }
}



