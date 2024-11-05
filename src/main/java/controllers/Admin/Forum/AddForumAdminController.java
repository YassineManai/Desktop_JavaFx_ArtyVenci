package controllers.Admin.Forum;

import entities.ForumEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.ServiceForumF;

import java.io.IOException;
import java.sql.SQLException;

public class AddForumAdminController {
    @FXML
    private Button addbutt_id;

    @FXML
    private TextField desc_text_field;



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
            Parent root= FXMLLoader.load(getClass().getResource("/Interfaces/ForumPages/Admin/GestionForumAdmin.fxml"));
            exitButt_id.getScene().setRoot(root);
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
    void ExitAddPage(ActionEvent event) {
        try {
            Parent root= FXMLLoader.load(getClass().getResource("/Interfaces/ForumPages/Admin/GestionForumAdmin.fxml"));
            exitButt_id.getScene().setRoot(root);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
