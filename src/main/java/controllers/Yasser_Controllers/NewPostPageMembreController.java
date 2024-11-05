package controllers.Yasser_Controllers;

import controllers.Yassine_Controllers.LoginController;
import entities.ForumEntity;
import entities.PostEntity;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.ServicePostF;

import java.io.IOException;
import java.sql.SQLException;

public class NewPostPageMembreController {
    @FXML
    private TextField post_desc;

    @FXML
    private TextField post_title;

    private ForumEntity forum;

    User CurrentUser = LoginController.userlogged;
    @FXML
    void AfficherPostes(ActionEvent event)  throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/ForumPages/Member/AfficherForumMembre.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = post_title.getScene();
        scene.setRoot(loginSuccessRoot);

    }

    @FXML
    void AjouterPoste(ActionEvent event) {
        try {
            ServicePostF sp=new ServicePostF();
            sp.ajouter(new PostEntity(forum.getId_forum(), CurrentUser.getId_User(), post_title.getText(),post_desc.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Forum Ajouté");
            alert.showAndWait();


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/ForumPages/Member/AfficherForumMembre.fxml"));
            Parent loginSuccessRoot = loader.load();
            Scene scene = post_title.getScene();
            scene.setRoot(loginSuccessRoot);


//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ForumPages/Member/AddPostMembre.fxml"));
//            Parent loginSuccessRoot = loader.load();
//            Scene scene = post_title.getScene();
//            AddPostMembreController addPostMembreControllerController = new AddPostMembreController();
//            loader.setController(addPostMembreControllerController);
//            Parent loginSuccessRoot = loader.load();
//            Scene scene = post_title.getScene();
//            scene.setRoot(loginSuccessRoot);

//            addPostMembreControllerController.setData(forum,userlogged);


        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setData(ForumEntity currentForum ) {

        this.forum = currentForum;

    }


}
