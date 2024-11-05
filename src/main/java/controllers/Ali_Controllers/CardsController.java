package controllers.Ali_Controllers;

import entities.Product;
import entities.ProductOrder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import services.ServiceOrder;

import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class CardsController {
    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String formattedDate = currentDate.format(formatter);
    ServiceOrder o = new ServiceOrder();

    @FXML
    private Label Date;

    @FXML
    private Label Desc;

    @FXML
    private Label Fsale;

    @FXML
    private Label Price;

    @FXML
    private ImageView ProductImage;

    @FXML
    private Label Title;

    @FXML
    private HBox box;

    @FXML
    private Button OrderButton;

    @FXML
    private Label IdCard;
    private String imagePath;
    private String imagePI ;


    public void setData(Product product){
        this.imagePath = product.getProductImage();
        if (!imagePath.startsWith("file:/")) {
            imagePath = "file:/" + imagePath;
        }



        Image image = new Image(imagePath);
        ProductImage.setImage(image);
        Title.setText(product.getTitle());
        Desc.setText(product.getDescription());
        if (product.getForSale()!=0) {
            //Fsale.setText(String.valueOf(product.getForSale()));
            Fsale.setText("This Item Is For Sale");
            Price.setText(String.valueOf(product.getPrice()));
        }else {
            Fsale.setVisible(false);
            Price.setVisible(false);
            OrderButton.setVisible(false);
        }
        Date.setText(product.getCreationDate());
        IdCard.setText(String.valueOf(product.getId_Product()));
    }


    public void AddOrderBtn(javafx.event.ActionEvent actionEvent) {
        int prod_id = Integer.parseInt(IdCard.getText());
        double price = Double.parseDouble(Price.getText());
        String title = Title.getText();
        String date = formattedDate;

        String image = imagePath;
        System.out.println("button"+image);
            try {

                ProductOrder productorder = new ProductOrder();
                productorder.setId_Product(prod_id);
                productorder.setPrice(price);
                productorder.setTitle(title);
                productorder.setOrderDate(date);
                productorder.setProd_img(image);
                o.ajouter(productorder);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

    }
}
