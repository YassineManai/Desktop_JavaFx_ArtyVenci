package controllers.Ali_Controllers;

import controllers.Yassine_Controllers.LoginController;
import entities.Product;
import entities.ProductOrder;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import services.MyListner;
import services.ServiceProduct;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Alert;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class MyCardController {
    ServiceProduct s = new ServiceProduct();

    @FXML
    private Label my_date;

    @FXML
    private Label my_desc;

    @FXML
    private Label my_fsale;

    @FXML
    private Label my_id;

    @FXML
    private ImageView my_image;

    @FXML
    private Label my_price;

    @FXML
    private Label my_title;

    private String imagePath;

    private MyListner myListner;
    User CurrentUser = LoginController.userlogged;


    public void setData(Product product,MyListner myListner){
        this.myListner=myListner;
        this.imagePath = product.getProductImage();
        if (!imagePath.startsWith("file:/")) {
            imagePath = "file:/" + imagePath;
        }
        Image image = new Image(imagePath);


        my_image.setImage(image);
        my_title.setText(product.getTitle());
        my_desc.setText(product.getDescription());
        my_fsale.setText(String.valueOf(product.getForSale()));
        my_price.setText(String.valueOf(product.getPrice()));
        my_date.setText(product.getCreationDate());
        my_id.setText(String.valueOf(product.getId_Product()));
    }
    @FXML
    void delete_item(ActionEvent event) {
        // Create a confirmation alert
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Vouler vous supprimer cette article?");

        // Customize the button types
        ButtonType buttonTypeYes = new ButtonType("Oui", ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("Non", ButtonData.NO);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeYes) {
                // User chose YES, proceed with deletion
                int idp = Integer.parseInt(my_id.getText());
                String title = my_title.getText();
                String desc = my_desc.getText();
                String date = my_date.getText();
                int fsale = Integer.parseInt(my_fsale.getText());
                Double price = Double.valueOf(my_price.getText());
                String image = this.imagePath;
                try {
                    s.supprimer(new Product(idp, CurrentUser.getId_User(), fsale, price, title, desc, date, image));
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/ProductPages/Product.fxml"));
                    Parent loginSuccessRoot = loader.load();
                    Scene scene = my_price.getScene();
                    scene.setRoot(loginSuccessRoot);
                    ProductController productController = loader.getController();
                    productController.initUser(CurrentUser);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    void edit_item(ActionEvent event) {
        int idp = Integer.parseInt(my_id.getText());
        String title = my_title.getText();
        String desc = my_desc.getText();
        String date = my_date.getText();
        int fsale = Integer.parseInt(my_fsale.getText());
        Double price = Double.valueOf(my_price.getText());


        String image = this.imagePath;
        image = image.replace("/", "\\");
        System.out.println("editpicture "+image);
        myListner.onClick(idp,title,desc,date,fsale,price,image);

    }
}
