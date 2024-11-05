package controllers.Yasser_Controllers;

import entities.PostEntity;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import services.ServiceForumF;
import services.ServicePostF;
import services.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;


public class PostTemplateMembreController {

    @FXML
    private Label Like_num_id;

    @FXML
    private Label post_label_id;

    @FXML
    private Label timestamp_label_id;

    @FXML
    private Label title_label_id;

    @FXML
    private Label user_label_id;
    private  int likes;

    private User userlogged;
    private PostEntity post;
    ServiceUser serviceUser = new ServiceUser();


    ServiceForumF SF = new ServiceForumF();
    public void setData(PostEntity postEntity )
    {





        title_label_id.setText(postEntity.getTitle());
        post_label_id.setText(postEntity.getText());
        Like_num_id.setText(""+postEntity.getLike_num());
        timestamp_label_id.setText(""+postEntity.getTime());
        this.post = postEntity;
        user_label_id.setText(serviceUser.GetUserById(post.getId_user()).getUsername());
    }
    @FXML
    private Button like_butt_id;
    @FXML
    void LikeAction(ActionEvent event) throws SQLException {
        likes = post.getLike_num() + 1;
        ServicePostF sp = new ServicePostF();
        PostEntity newPost = new PostEntity(post.getId_post(),post.getId_forum(),post.getId_user(),post.getText(),post.getTitle(),likes,post.getTime(),post.getDate());
        sp.modifier(newPost);
        Like_num_id.setText(""+post.getLike_num());
        reloadPage();
    }


//    void reloadPage()
//    {
////        Parent root= post_label_id.getScene().getRoot();
////        post_label_id.getScene().setRoot(root);
//
//        try{
//            Parent root = loadRootLayoutForForum();
//            post_label_id.getScene().setRoot(root);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//    private Parent loadRootLayoutForForum() throws Exception {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ForumPages/Member/AddPostMembre.fxml"));
//        AddPostMembreController controller = new AddPostMembreController();
//        loader.setController(controller);
//        controller.setData(SF.getbyid(post.getId_forum())); // Add data to the controller
//        Parent root = loader.load();
//        return root;
//    }

    void reloadPage()
    {
//        Parent root= post_label_id.getScene().getRoot();
//        post_label_id.getScene().setRoot(root);

        try{
            Parent root = loadRootLayoutForForum();
//            post_label_id.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    private Parent loadRootLayoutForForum() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/ForumPages/Member/RefreshPage.fxml"));
        RefreshController controller = new RefreshController();
        loader.setController(controller);
        Parent root = loader.load();
        RefreshController initedCont = loader.getController();
        initedCont.setForumData(SF.getbyid(post.getId_forum()),post_label_id.getScene()); // Add data to the controller

        return root;
    }
}
