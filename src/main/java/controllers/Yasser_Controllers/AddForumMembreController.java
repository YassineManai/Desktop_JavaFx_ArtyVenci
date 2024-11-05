package controllers.Yasser_Controllers;

import controllers.Yassine_Controllers.LoginController;
import entities.ForumEntity;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.ServiceForumF;

import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.control.TextArea;

public class AddForumMembreController {
    @FXML
    private Button addbutt_id;

    @FXML
    private TextArea desc_text_field;


    User CurrentUser = LoginController.userlogged;

    @FXML
    private TextField title_text_field;

    ServiceForumF SF = new ServiceForumF();

    //ADDING THE FORUM LOGIC
    @FXML
    void Add_Forum(ActionEvent event) {
        try{
            if(!title_text_field.getText().isEmpty())
            {
                if(!desc_text_field.getText().isEmpty())
                {
                    DoAddTheForum();
                }
                else{
                    throw new Exception("Description is Empty !");
                }
            }else {
                throw new Exception("Title is Empty !");
            }
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    void DoAddTheForum()
    {
        try {
            SF.ajouter(new ForumEntity(title_text_field.getText(),desc_text_field.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Forum Ajouté");
            alert.showAndWait();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/ForumPages/Member/AfficherForumMembre.fxml"));
            Parent loginSuccessRoot = loader.load();
            Scene scene = exitButt_id.getScene();
            scene.setRoot(loginSuccessRoot);


        } catch (IOException | SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    //EXITING LOGIC
    @FXML
    private Button exitButt_id;
    @FXML
    void ExitAddPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/ForumPages/Member/AfficherForumMembre.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = exitButt_id.getScene();
        scene.setRoot(loginSuccessRoot);


    }


}
