package controllers.Yasser_Controllers;

import controllers.Montaha_Controllers.AfficherEventView;
import controllers.Yassine_Controllers.HomeController;
import controllers.Yassine_Controllers.LoginController;
import controllers.Ali_Controllers.ProductController;
import controllers.Yassine_Controllers.LoginSuccess;
import entities.ForumEntity;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.ServiceForumF;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

public class AfficherForumMembreController {

    public ImageView bell;
    public ImageView usericon;
    public Label nav_name;
    public Button inscrire;
    public ImageView logouticon;
    ServiceForumF SF = new ServiceForumF();
    @FXML
    private Button searchButt_id;

    @FXML
    private TextField searchbar_id;
    @FXML
    private ImageView Lojo;
    Preferences preferences = Preferences.userNodeForPackage(LoginController.class);
    User CurrentUser = LoginController.userlogged;
    @FXML
    void SearchForForum(ActionEvent event) {
            try {
                idVbox.getChildren().clear(); // Clear previous content

                String searchText = searchbar_id.getText(); // Assuming id_search is the TextField where the user enters the search text

                ObservableList<ForumEntity> observableList = FXCollections.observableList(SF.afficher());

                // Filter the list based on the search text
                List<ForumEntity> filteredList = observableList.stream()
                        .filter(e -> e.getTitle().toLowerCase().contains(searchText.toLowerCase()))
                        .collect(Collectors.toList());

                // Load and display filtered data
                for (ForumEntity f : filteredList) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Interfaces/ForumPages/Member/ForumTemplateMembre.fxml"));
                    HBox cardBox = fxmlLoader.load();
                    ForumTemplateMembreController cardController = fxmlLoader.getController();
                    cardController.setData(f);
                    idVbox.getChildren().add(cardBox);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

        @FXML
    private VBox idVbox;




    @FXML
   public void initialize(){
        if(CurrentUser == null){
            inscrire.setVisible(true);
            bell.setVisible(false);
            usericon.setVisible(false);
            logouticon.setVisible(false);
            System.out.println("el user mafamech");


            try{
                ObservableList<ForumEntity> observableList = FXCollections.observableList(SF.afficher());
                for (int i = 0; i < observableList.size(); i++) {
                    FXMLLoader fxmlLoader= new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/Interfaces/ForumPages/Member/ForumTemplateMembre.fxml"));
                    HBox cardBox = fxmlLoader.load();
                    ForumTemplateMembreController cardController = fxmlLoader.getController();
                    cardController.setData(observableList.get(i));

                    idVbox.getChildren().add(cardBox);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            searchButt_id.setGraphic(Lojo);


        }



        else {


            nav_name.setText(CurrentUser.getUsername());
            inscrire.setVisible(false);
            bell.setVisible(true);
            usericon.setVisible(true);
            logouticon.setVisible(true);


            try {
                ObservableList<ForumEntity> observableList = FXCollections.observableList(SF.afficher());
                for (int i = 0; i < observableList.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/Interfaces/ForumPages/Member/ForumTemplateMembre.fxml"));
                    HBox cardBox = fxmlLoader.load();
                    ForumTemplateMembreController cardController = fxmlLoader.getController();
                    cardController.setData(observableList.get(i));
                    //   cardController.initData(userlogged);
                    idVbox.getChildren().add(cardBox);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            searchButt_id.setGraphic(Lojo);
        }


    }





    @FXML
    private Button forumPage_id;
    @FXML
    void GotoforumPage(ActionEvent event) {
        try {
            Parent root= FXMLLoader.load(getClass().getResource("/Interfaces/ForumPages/Member/AfficherForumMembre.fxml"));
            forumPage_id.getScene().setRoot(root);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    //Creating Forums
    @FXML
    private Button Create_forum_butt_id;

    @FXML
    void CreateForum(ActionEvent event) throws IOException {
        if (CurrentUser != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/ForumPages/Member/AddForumMembre.fxml"));
            Parent loginSuccessRoot = loader.load();
            Scene scene = nav_name.getScene();
            scene.setRoot(loginSuccessRoot);
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/Login_User.fxml"));
            Parent loginSuccessRoot = loader.load();
            Scene scene = nav_name.getScene();
            scene.setRoot(loginSuccessRoot);
        }



    }

    private Parent loadRootLayoutForForum() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/ForumPages/Member/AddForumMembre.fxml"));

        Parent root = loader.load();
        return root;
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

    public void sinscrire(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/Login_User.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
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
