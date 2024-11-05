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

public class EditForumAdminController {
    @FXML
    private TextField desc_text_field;

    ServiceForumF SF = new ServiceForumF();

    @FXML
    private TextField title_text_field;


    ///EXITING LOGIC
    @FXML
    private Button exitbutt_id;
    @FXML
    void ExitEditPage(ActionEvent event) {
        try {
            Parent root= FXMLLoader.load(getClass().getResource("/Interfaces/ForumPages/Admin/GestionForumAdmin.fxml"));
            exitbutt_id.getScene().setRoot(root);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    /// SAVING LOGIC
    @FXML
    private Button save_butt_id;
    private ForumEntity oldForum;
    private ForumEntity newForum;
    @FXML
    void SaveEdit(ActionEvent event) {
        try{
            if(!title_text_field.getText().isEmpty())
            {
                if(!desc_text_field.getText().isEmpty())
                {
                    DoSave();
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

    void DoSave()
    {
        try {
            newForum = new ForumEntity(oldForum.getId_forum(), title_text_field.getText(),desc_text_field.getText(), oldForum.getReplies_num(),oldForum.getDate());
            SF.modifier(newForum);
            Parent root= FXMLLoader.load(getClass().getResource("/Interfaces/ForumPages/Admin/GestionForumAdmin.fxml"));
            save_butt_id.getScene().setRoot(root);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setData(ForumEntity forum)
    {
        this.desc_text_field.setText(forum.getDescription());
        this.title_text_field.setText(forum.getTitle());
        this.oldForum = forum;
    }

}
