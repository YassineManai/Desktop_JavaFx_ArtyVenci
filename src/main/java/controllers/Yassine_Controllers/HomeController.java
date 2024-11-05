package controllers.Yassine_Controllers;
import controllers.Ali_Controllers.ProductController;
import controllers.Kenza_Controllers.ArtistEnchersController;
import controllers.Kenza_Controllers.EnchersController;
import controllers.Yasser_Controllers.AfficherForumMembreController;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import controllers.Montaha_Controllers.AfficherEventView;
import services.ServiceUser;

import java.io.IOException;
import java.util.prefs.Preferences;

public class HomeController {
    public ImageView logouticon;
    @FXML
    private ImageView bell;
    @FXML
    private ImageView usericon;

    @FXML
    private Button inscrire;
    private User userlogged;
    @FXML
    private Label nav_name;
    Preferences preferences = Preferences.userNodeForPackage(LoginController.class);
    String savedUsername = preferences.get("username", null);
    String savedPassword = preferences.get("Password", null);

    ServiceUser serviceUser = new ServiceUser();
    public void Go_To_Home(ActionEvent actionEvent) {
    }
    public boolean isUserlogged(){
        String savedUsername = preferences.get("username", null);
        String savedPassword = preferences.get("Password", null);
        if (savedUsername==null & savedPassword==null){
            return false;
        }
        else
            return true;
    }
    public void Go_To_Product(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/ProductPages/Product.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
        ProductController productController = loader.getController();
        productController.initUser(userlogged);
    }

    public void Go_To_Auction(ActionEvent actionEvent) throws IOException {
        if (userlogged != null){
              if (userlogged.getRole().equals("Member")){
                  FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/AuctionPages/Enchers.fxml"));
                  Parent loginSuccessRoot = loader.load();
                  Scene scene = nav_name.getScene();
                  scene.setRoot(loginSuccessRoot);

              }
              else if
                  (userlogged.getRole().equals("Artist")){
                      FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/AuctionPages/artistEnchers.fxml"));
                      Parent loginSuccessRoot = loader.load();
                      Scene scene = nav_name.getScene();
                      scene.setRoot(loginSuccessRoot);



              }}
        else {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/AuctionPages/Enchers.fxml"));
            Parent loginSuccessRoot = loader.load();
            Scene scene = nav_name.getScene();
            scene.setRoot(loginSuccessRoot);

        }

    }

    public void Go_To_Forum(ActionEvent actionEvent)  throws IOException {

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
        afficherEventView.initData(userlogged);




    }

    public void Go_To_Message(ActionEvent actionEvent) throws IOException {
if (userlogged != null) {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/MessageInterface/Homepage.fxml"));
    Parent loginSuccessRoot = loader.load();
    Scene scene = nav_name.getScene();
    scene.setRoot(loginSuccessRoot);
}
else {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText("Inscrire dans ArtyVency" );
    alert.showAndWait();
}


    }
    public void initData(User user) throws IOException {

        if (user != null) {

            inscrire.setVisible(false);
            bell.setVisible(true);
            usericon.setVisible(true);
            logouticon.setVisible(true);
            nav_name.setText(user.getUsername());
             userlogged = new User();
             userlogged.setGender(user.getGender());
             userlogged.setDOB(user.getDOB());
             userlogged.setPhone(user.getPhone());
             userlogged.setAdress(user.getAdress());
             userlogged.setUsername(user.getUsername());
             userlogged.setEmail(user.getEmail());
             userlogged.setFirstName(user.getFirstName());
             userlogged.setPassword(user.getPassword());
             userlogged.setLastName(user.getLastName());
             userlogged.setId_User(user.getId_User());
             userlogged.setRole(user.getRole());
             userlogged.setImageURL(user.getImageURL());
        } else {
            System.out.println("user  null");



        }
    }
public void initialize() throws IOException {
    if (isUserlogged()) {
        inscrire.setVisible(false);
        bell.setVisible(true);
        usericon.setVisible(true);
        logouticon.setVisible(true);
        User usersaved = serviceUser.GetUser(savedUsername, savedPassword);
        LoginController.userlogged = usersaved;
        System.out.println(usersaved);
        initData(usersaved);
        System.out.println("User Already logged ");
    } else {

        inscrire.setVisible(true);
        bell.setVisible(false);
        usericon.setVisible(false);
        logouticon.setVisible(false);
        System.out.println("User NOT logged ");
    }

}


    public void ProfileVisit(MouseEvent mouseEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/LoginSuccess.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);

        LoginSuccess loginSuccess = loader.getController();
        loginSuccess.initData(userlogged);
    }

    public void Logout(MouseEvent mouseEvent) throws IOException {
        preferences.remove("username");
        preferences.remove("Password");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/Login_User.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText(null);
        alert.show();
    }

    @FXML
    void sinscrire(ActionEvent event) throws IOException {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/Login_User.fxml"));
            Parent loginSuccessRoot = loader.load();
            Scene scene = nav_name.getScene();
            scene.setRoot(loginSuccessRoot);


    }}




