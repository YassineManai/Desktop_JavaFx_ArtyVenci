package controllers.Admin.Product;

import entities.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import services.*;


import java.io.IOException;
import java.sql.SQLException;

public class AdminProduit {

    @FXML
    private Label Num_Arts;

    @FXML
    private Label Num_Auction;

    @FXML
    private Label Num_Events;

    @FXML
    private Label Num_Messages;

    @FXML
    private Label Num_Forum;

    @FXML
    private Label Num_Users;

    @FXML
    private ImageView logout;
    @FXML
    private TableView<Product> tv_product;
    @FXML
    private TableColumn<Product, String> date;

    @FXML
    private TableColumn<Product, String> desc;

    @FXML
    private TableColumn<Product, Integer> fsale;

    @FXML
    private TableColumn<Product, Integer> id_prod;

    @FXML
    private TableColumn<Product, String> imgurl;

    @FXML
    private TableColumn<Product, Double> price;

    @FXML
    private TableColumn<Product, String> title;

    @FXML
    private TableColumn<Product, Integer> user_id;


    ServiceProduct p = new ServiceProduct();
    ServiceUser serviceUser = new ServiceUser();
    ServiceForumF serviceForum = new ServiceForumF();
    ServiceAuction serviceAuction = new ServiceAuction();
    ServiceDisscussion serviceDisscussion = new ServiceDisscussion();
    ServiceEvent serviceEvent = new ServiceEvent();

//    void initialized(){
//        try {
//            ObservableList<Product> observableList= FXCollections.observableList(p.afficher());
//            tv_product.setItems(observableList);
//            id_prod.setCellValueFactory(new PropertyValueFactory<>("Id_Product"));
//            user_id.setCellValueFactory(new PropertyValueFactory<>("Id_User"));
//            title.setCellValueFactory(new PropertyValueFactory<>("Title"));
//            desc.setCellValueFactory(new PropertyValueFactory<>("Description"));
//            fsale.setCellValueFactory(new PropertyValueFactory<>("ForSale"));
//            price.setCellValueFactory(new PropertyValueFactory<>("Price"));
//            date.setCellValueFactory(new PropertyValueFactory<>("CreationDate"));
//            imgurl.setCellValueFactory(new PropertyValueFactory<>("ProductImage"));
//            System.out.println("impoted");
//            System.out.println(observableList);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
public void initialize() throws SQLException {
    Num_Forum.setText(""+serviceForum.CountForums());
    Num_Arts.setText(String.valueOf(p.CountProduct()));
    Num_Users.setText(String.valueOf(serviceUser.CountUsers()));
    Num_Auction.setText(String.valueOf(serviceAuction.CountAuction()));
    Num_Messages.setText(String.valueOf(serviceDisscussion.CountDisc()));
    Num_Events.setText(String.valueOf(serviceEvent.CountMesg()));
    System.out.println("Welcome Produit");



    try {
        ObservableList<Product> observableList= FXCollections.observableList(p.afficher());
        tv_product.setItems(observableList);
        id_prod.setCellValueFactory(new PropertyValueFactory<>("Id_Product"));
        user_id.setCellValueFactory(new PropertyValueFactory<>("Id_User"));
        title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        desc.setCellValueFactory(new PropertyValueFactory<>("Description"));
        fsale.setCellValueFactory(new PropertyValueFactory<>("ForSale"));
        price.setCellValueFactory(new PropertyValueFactory<>("Price"));
        date.setCellValueFactory(new PropertyValueFactory<>("CreationDate"));
        imgurl.setCellValueFactory(new PropertyValueFactory<>("ProductImage"));
        System.out.println("impoted");
        System.out.println(observableList);
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}
    @FXML
    void Delete_Prod(ActionEvent event) throws SQLException {
        Product selectedItem = tv_product.getSelectionModel().getSelectedItem();
        if(selectedItem!=null){
            try {
                p.supprimer(selectedItem);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            initialize();
        }
    }

    @FXML
    void Refrech_Btn(ActionEvent event) {
        //initialized();
        int numberOfRows = tv_product.getItems().size();
        Num_Arts.setText(String.valueOf(numberOfRows));
    }








    @FXML
    void Go_To_Auction(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin_Interface/Admin_Auction.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = Num_Arts.getScene();
        scene.setRoot(loginSuccessRoot);

    }

    @FXML
    void Go_To_Event(MouseEvent event)throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin_Interface/Admin_Event.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = Num_Arts.getScene();
        scene.setRoot(loginSuccessRoot);

    }

    @FXML
    void Go_To_Forum(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin_Interface/Admin_Forum.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = Num_Arts.getScene();
        scene.setRoot(loginSuccessRoot);
    }

    @FXML
    void Go_To_Messages(MouseEvent event)throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin_Interface/Admin_Message.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = Num_Arts.getScene();
        scene.setRoot(loginSuccessRoot);

    }

    @FXML
    void Go_To_Product(MouseEvent event) throws IOException, SQLException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin_Interface/Admin_Produit.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = Num_Arts.getScene();
        scene.setRoot(loginSuccessRoot);
        initialize();
    }

    @FXML
    void Go_To_User(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin_Interface/AdminUI.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = Num_Arts.getScene();
        scene.setRoot(loginSuccessRoot);

    }

    @FXML
    void logout(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/Login_User.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = Num_Arts.getScene();
        scene.setRoot(loginSuccessRoot);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText(null);
        alert.show();}

}
