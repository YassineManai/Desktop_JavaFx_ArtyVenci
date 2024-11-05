package controllers.Asma_Controllers;

import entities.Message;
import entities.User;
import javafx.geometry.Pos;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import services.ServiceDisscussion;
import services.ServiceMessage;
import services.ServiceUser;

import java.sql.SQLException;

public class MessageController {

    //déclaration des services
    ServiceDisscussion serviceDisscussion = new ServiceDisscussion() ;
    ServiceUser serviceUser = new ServiceUser() ;
    ServiceMessage serviceMessage = new ServiceMessage() ;

    //Constructeur de controller
    public MessageController() {
    }

    @FXML
    private Label heure;

    @FXML
    private Label messagee;

    @FXML
    private VBox vbox;

    @FXML
    private Label sender;
    @FXML
    private ImageView img;

    public void initializeMessage(Message message, boolean isCurrentUser) {
        // Initialize the content of the message label
        messagee.setText(message.getContent());
        heure.setText(message.getTime().toString());

        sender.setText("   "+serviceUser.GetUserById(message.getIdSender()).getUsername());
        System.out.println(message.getIdSender());
        System.out.println(message.getIdDis());













        // Apply styling based on whether the message belongs to the current user
        if (isCurrentUser) {
            messagee.setStyle("-fx-background-color: #DCF8C6; -fx-background-radius: 10; -fx-padding: 5px;");
            messagee.setAlignment(Pos.CENTER_RIGHT);
            

        } else {
            messagee.setStyle("-fx-background-color: #E6E6E6; -fx-background-radius: 10; -fx-padding: 5px;");
            messagee.setAlignment(Pos.CENTER_LEFT);
            User userDiscusion = serviceUser.GetUserById(message.getIdSender());
            String imagepath = userDiscusion.getImageURL() ;
            System.out.println(imagepath);
            if (!imagepath.startsWith("file:/")) {
                imagepath = "file:/" + imagepath;
            }
            Image image = new Image(imagepath);
            img.setImage(image);
        }
    }


}
