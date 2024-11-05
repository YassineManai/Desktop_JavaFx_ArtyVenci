package controllers.Asma_Controllers;

import controllers.Yassine_Controllers.LoginController;
import entities.Disscussion;
import entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.ServiceDisscussion;
import services.ServiceMessage;
import services.ServiceUser;

import java.io.File;
import java.sql.SQLException;

public class DiscussionController {

    @FXML
    public Button Afficherdis;

    @FXML
    public HBox box;

    @FXML
    public HBox box1;

    @FXML
    public Label content;

    @FXML
    public ImageView img;

    @FXML
    public Label receiver;

    @FXML
    public VBox vbox;
    User CurrentUser = LoginController.userlogged;


    ServiceDisscussion serviceDisscussion = new ServiceDisscussion();
    ServiceUser serviceUser = new ServiceUser();
    ServiceMessage serviceMessage = new ServiceMessage();

    private Disscussion dis;

    public DiscussionController() {
    }

    public void setData(Disscussion disscussion) throws SQLException {



        // Assurez-vous que receiver et content sont initialisés
        if (receiver != null && content != null) {

            if (CurrentUser.getId_User() == disscussion.getIdSender()){
                receiver.setText(serviceUser.GetUserById(disscussion.getIdReceiver()).getUsername());
                User userDiscusion = serviceUser.GetUserById(disscussion.getIdReceiver());
                String imagepath = userDiscusion.getImageURL() ;
                if (!imagepath.startsWith("file:/")) {
                    imagepath = "file:/" + imagepath;
                }
                System.out.println(imagepath);
                Image image = new Image(imagepath);
                img.setImage(image);

            }
            else
            {
                receiver.setText(serviceUser.GetUserById(disscussion.getIdSender()).getUsername());
                User userDiscusion = serviceUser.GetUserById(disscussion.getIdSender());
                String imagepath = userDiscusion.getImageURL() ;
                if (!imagepath.startsWith("file:/")) {
                    imagepath = "file:/" + imagepath;
                }
                System.out.println(imagepath);
                Image image = new Image(imagepath);
                img.setImage(image);
            }
//            content.setText(serviceMessage.getContentById(disscussion.getIdDis()).getContent());
            content.setText("View Message");
        } else {
            System.out.println("Label receiver ou content n'est pas initialisé.");
        }

        dis = disscussion;
    }

}
