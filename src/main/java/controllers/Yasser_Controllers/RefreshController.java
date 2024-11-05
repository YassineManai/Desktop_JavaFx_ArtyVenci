package controllers.Yasser_Controllers;

import entities.ForumEntity;
import entities.PostEntity;
import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;


import java.io.IOException;

public class RefreshController {

    @FXML
    private Label loadinglabele;
    private User userlogged;

    private ForumEntity f;

//    public void initialize()
//    {
//        loadinglabele = new Label();
//    }

    public void setForumData(ForumEntity f, Scene s )
    {



        this.loadinglabele.setText(f.getTitle());
//        System.out.println(f);
        this.f = f;
        try{
            Parent root = loadRootLayoutForForum(f);
            s.setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Parent loadRootLayoutForForum(ForumEntity f) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/ForumPages/Member/AddPostMembre.fxml"));
        AddPostMembreController controller = new AddPostMembreController();
        loader.setController(controller);
        controller.setData(f); // Add data to the controller
        Parent root = loader.load();
        return root;
    }

    public void setPostData(PostEntity p)
    {
        //Posts
    }

}
