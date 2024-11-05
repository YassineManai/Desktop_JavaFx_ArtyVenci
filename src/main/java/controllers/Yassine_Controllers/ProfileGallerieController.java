package controllers.Yassine_Controllers;

import controllers.Ali_Controllers.MyCardController;
import controllers.Ali_Controllers.ProductController;
import controllers.Kenza_Controllers.ArtistEnchersController;
import controllers.Kenza_Controllers.EnchersController;
import controllers.Yasser_Controllers.AfficherForumMembreController;
import entities.Product;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import services.MyListner;
import services.ServiceOrder;
import services.ServiceProduct;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class ProfileGallerieController {




    @FXML
    private Label nav_name;
    int userid;

    Preferences preferences = Preferences.userNodeForPackage(ProfileGallerieController.class);
    String savedUsername = preferences.get("username", null);
    String savedPassword = preferences.get("Password", null);








    public javafx.scene.image.ImageView logouticon;



    @FXML
    private javafx.scene.image.ImageView bell;

    public javafx.scene.image.ImageView usericon;


    private User userlogged;



    double price,price_ed;
    @FXML
    private Pane add_panel;
    @FXML
    private TextArea pr_desc_ed;
    @FXML
    private RadioButton pr_oui_ed;
    @FXML
    private TextField pr_price_ed;

    @FXML
    private javafx.scene.image.ImageView product_image_ed;
    @FXML
    private TextField pr_date_ed;
    @FXML
    private TextField pr_id_ed;
    @FXML
    private Pane Edit_panel;

    @FXML
    private TextArea pr_desc;

    @FXML
    private RadioButton pr_oui;

    @FXML
    private TextField pr_price;

    @FXML
    private javafx.scene.image.ImageView product_image;
    @FXML
    private ScrollPane Affichage_panel;
    @FXML
    private HBox PanierCard;
    @FXML
    private ScrollPane afficher_panier;

    @FXML
    private ChoiceBox<String> pr_title;
    @FXML
    private ChoiceBox<String> pr_title_ed;

    @FXML
    private ChoiceBox<String> FilterBox;
    private List<Product> fa;
    @FXML
    private CheckBox box_fsale;

    @FXML
    private CheckBox box_nosale;
    @FXML
    private ScrollPane afficher_ma_list;

    @FXML
    private HBox ma_list_box;
    @FXML
    private HBox CardLayout;
    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String formattedDate = currentDate.format(formatter);
    ServiceProduct s = new ServiceProduct();
    ServiceOrder o = new ServiceOrder();





    public void initData(User user) {
        statue_name.setText(user.getFirstName() + " " + user.getLastName());
        nav_name.setText(user.getUsername());

        userid = user.getId_User();


        System.out.println("d5al lil 1");
        afficher_ma_list.setVisible(true);
        Edit_panel.setVisible(false);


        refreshMyCards();


        userlogged = new User();
        userlogged.setGender(user.getGender());
        userlogged.setDOB(user.getDOB());
        userlogged.setPhone(user.getPhone());
        userlogged.setAdress(user.getAdress());
        userlogged.setUsername(user.getUsername());
        userlogged.setEmail(user.getEmail());
        userlogged.setFirstName(user.getFirstName());
        userlogged.setPassword(user.getPassword());
        userlogged.setLastName(user.getLastName());
        userlogged.setId_User(user.getId_User());
        userlogged.setRole(user.getRole());
        userlogged.setImageURL(user.getImageURL());


    }






    private List<Product> fo(){
        try {
            return s.maList(userid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    int forsalevalue(){
        if (pr_oui.isSelected()){
            return 1;
        }
        else return 0;
    }

    void ini1(){
        pr_title_ed.getItems().addAll("peinture","sculpture","photography");


    }




    public void initialized(){
        System.out.println("d5al lil 2");
        fa =new ArrayList<>(fo());
        System.out.println("the size of data "+fa.size());
        try {
            VBox mainVBox = new VBox();
            ma_list_box.getChildren().add(mainVBox);

            HBox currentHBox = new HBox();
            currentHBox.setSpacing(50);

            for (int i = 0; i < fa.size(); i++) {
                if (i > 0 && i % 3 == 0) {
                    mainVBox.getChildren().add(currentHBox);
                    currentHBox = new HBox();
                    currentHBox.setSpacing(50);
                }
                MyListner myListner= new MyListner() {
                    @Override
                    public void onClick(int value,String value1,String value2,String value3,int value4,Double value5,String value6) {
                        ini1();
                        Edit_panel.setVisible(true);
                        afficher_ma_list.setVisible(false);

                        pr_id_ed.setText(String.valueOf(value));
                        pr_title_ed.setValue(value1);
                        pr_desc_ed.setText(value2);
                        pr_date_ed.setText(String.valueOf(value3));
                        if (value4!=0){
                            pr_oui_ed.setSelected(true);
                        }else {
                            pr_oui_ed.setSelected(false);
                        }
                        pr_price_ed.setText(String.valueOf(value5));

                        String imagePath = value6;
                        System.out.println(imagePath);
                        Image image = new Image(imagePath);
                        product_image_ed.setImage(image);
                    }
                };
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Interfaces/ProductPages/MyCard.fxml"));
                HBox cardBox = fxmlLoader.load();
                MyCardController myCardController = fxmlLoader.getController();
                myCardController.setData(fa.get(i),myListner);
                currentHBox.getChildren().add(cardBox);
            }

            mainVBox.getChildren().add(currentHBox);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private List<Product> recentlyAdded1(){
        try {
            return s.saleList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private List<Product> recentlyAdded;
    private List<Product> recentlyAdded2(){
        try {
            return s.NosaleList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private List<Product> recentlyAdded3(){
        try {
            return s.FilterShow(FilterBox.getValue());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
















    void clear(){
        pr_title.setValue("");
        pr_desc.setText("");
        pr_price.setText("");
        pr_oui.setSelected(false);
        product_image.setImage(new Image("file:D:\\Heikun\\ESPRIT 2023-2024\\JAVA\\TD Revision\\Sprint-Java-DEV\\src\\main\\resources\\images\\img.png"));
    }





    @FXML
    void CancelEditProduct(ActionEvent event) {
        afficher_ma_list.setVisible(true);



        Edit_panel.setVisible(false);

        refreshMyCards();
    }

    @FXML
    void EditProduct(ActionEvent event) {
        try{
            price_ed = Double.parseDouble(pr_price_ed.getText());
        }catch(NumberFormatException e){
            System.out.println("invalid integer input");
        }
        int id = Integer.parseInt(pr_id_ed.getText());
        String title = pr_title_ed.getValue();
        String desc = pr_desc_ed.getText();
        String date = pr_date_ed.getText();
        Double price = Double.valueOf(pr_price_ed.getText());
        String imagePath = "";
        Image image = product_image_ed.getImage();
        if (image != null) {

            String imageUrl = image.getUrl();
            imagePath = imageUrl;
//            if (imageUrl.startsWith("file:/")) {
//                imagePath = imageUrl.substring("file:/".length());
//            }
        }
        try {
            s.modifier(new Product(id,userid,forsalevalue_ed(),price,title,desc,date,imagePath));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        afficher_ma_list.setVisible(true);
        add_panel.setVisible(false);
        Affichage_panel.setVisible(false);
        box_fsale.setVisible(false);
        box_nosale.setVisible(false);
        afficher_panier.setVisible(false);
        Edit_panel.setVisible(false);
        FilterBox.setVisible(false);

        refreshMyCards();

    }

    int forsalevalue_ed(){
        if (pr_oui_ed.isSelected()){
            return 1;
        }
        else return 0;
    }

    @FXML
    void MagPage(MouseEvent event) {
        Affichage_panel.setVisible(true);
        afficher_panier.setVisible(false);
        afficher_ma_list.setVisible(false);
        Edit_panel.setVisible(false);
        box_fsale.setVisible(true);
        box_nosale.setVisible(true);
        FilterBox.setVisible(true);

    }

    @FXML
    void ResetAddProduct(ActionEvent event) {
        clear();
    }




    @FXML
    void add_image_dialog_ed(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            product_image_ed.setImage(image);

        }
    }





    public void refreshMyCards() {
        fa = new ArrayList<>(fo());
        ma_list_box.getChildren().clear();
        initialized();
    }






    @FXML
    private Label statue_name;

    @FXML
    void Details(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/Profile_propos.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
        ProfileController profileController = loader.getController();
        profileController.initData(userlogged);

    }

    @FXML
    void Gallerie(ActionEvent event) {
    }


    @FXML
    void Post_Blog(ActionEvent event)  throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/LoginSuccess.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
        LoginSuccess loginSuccess = loader.getController();
        loginSuccess.initData(userlogged);
    }

    public void Go_To_Home(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/Home.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
        System.out.println(userlogged);

    }

    public void Go_To_Product(ActionEvent actionEvent)  throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/ProductPages/Product.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
        ProductController productController = loader.getController();
        productController.initUser(userlogged);
    }

    public void Go_To_Auction(ActionEvent actionEvent) throws IOException {
        if (userlogged != null){
            if (userlogged.getRole().equals("Member")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/AuctionPages/Enchers.fxml"));
                Parent loginSuccessRoot = loader.load();
                Scene scene = nav_name.getScene();
                scene.setRoot(loginSuccessRoot);

            }
            else if
            (userlogged.getRole().equals("Artist")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/AuctionPages/artistEnchers.fxml"));
                Parent loginSuccessRoot = loader.load();
                Scene scene = nav_name.getScene();
                scene.setRoot(loginSuccessRoot);



            }}

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/AuctionPages/Enchers.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);


    }
    public void Go_To_Forum(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/ForumPages/Member/AfficherForumMembre.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);


    }

    public void Go_To_Event(ActionEvent actionEvent) {
    }

    public void Go_To_Message(ActionEvent actionEvent) {
    }

    public void ProfileVisit(MouseEvent mouseEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/LoginSuccess.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);

        LoginSuccess loginSuccess = loader.getController();
        loginSuccess.initData(userlogged);
    }

    public void sinscrire(ActionEvent actionEvent) throws IOException {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/Login_User.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
    }

    public void Logout(MouseEvent mouseEvent)throws IOException {
        preferences.remove("username");
        preferences.remove("Password");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/Login_User.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText(null);
        alert.show();
    }


}