package controllers.Montaha_Controllers;

import controllers.Ali_Controllers.ProductController;
import controllers.Kenza_Controllers.ArtistEnchersController;
import controllers.Kenza_Controllers.EnchersController;
import controllers.Yasser_Controllers.AfficherForumMembreController;
import controllers.Yassine_Controllers.HomeController;
import controllers.Yassine_Controllers.LoginController;
import controllers.Yassine_Controllers.LoginSuccess;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import test.MainFX;
import entities.Event;
import services.EventChangeListener;
import services.ServiceEvent;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.prefs.Preferences;

public class AfficherEventView implements EventChangeListener<Event> {

    public ImageView usericon;
    public Button inscrire;
    public ImageView bell;
    public ImageView logouticon;
    public Label nav_name;
    @FXML
    private AnchorPane anchore;
      int userid;
    @FXML
    private GridPane grid;
    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfplace;
    @FXML
    private DatePicker dpdate;
    @FXML
    private TextField tfprix;
    @FXML
    private TextField tfimage;
    private User userlogged;
    ServiceEvent serviceEvent=new ServiceEvent();
    int idModifier=0;
    Preferences preferences = Preferences.userNodeForPackage(LoginController.class);
    User CurrentUser = LoginController.userlogged;
    @FXML
    private TextField tfrecherche;


    @FXML
    public void initialize(){
        if (CurrentUser != null) {
            nav_name.setText(CurrentUser.getUsername());
            inscrire.setVisible(false);
            bell.setVisible(true);
            usericon.setVisible(true);
            logouticon.setVisible(true);
        }
        else {
            inscrire.setVisible(true);
            bell.setVisible(false);
            usericon.setVisible(false);
            logouticon.setVisible(false);
        }

        recherche_avance();
    }


    public void refresh(List<Event> events){
        grid.getChildren().clear();
        int column=0;
        int row=1;
        for(int i=0;i<events.size();i++){

            FXMLLoader card = new FXMLLoader(MainFX.class.getResource("/Interfaces/EventPages/afficher-one-event-view-2.fxml"));
            try {

                AnchorPane anchorPane=card.load();
                AfficherOneEventView item=card.getController();
                item.remplireData(events.get(i));
                item.setEventChangeListener(this);
                if(column==3){
                    column=0;
                    row++;
                }
                grid.add(anchorPane,column++,row);

                GridPane.setMargin(anchorPane,new Insets(10));
            }catch (IOException e){
                System.out.println("Erreur:"+e.getMessage());
            }



        }
    }
    @FXML
    void ajouterEvenement(ActionEvent event) {
        Event e=new Event();
        e.setE_Name(tfnom.getText());
        e.setPlace(tfplace.getText());
        e.setImage(tfimage.getText());
        e.setTicket_Price(Double.parseDouble(tfprix.getText()));
        e.setE_Date(Date.valueOf(dpdate.getValue()));
        serviceEvent.ajouter(e);
        refresh(serviceEvent.afficher());
    }
    @FXML
    void uploadImage(ActionEvent event) {

    }
    @FXML
    void modifierEvent(ActionEvent event) {
        if(idModifier!=0){
            Event e=new Event();
            e.setId_Event(idModifier);
            e.setE_Name(tfnom.getText());
            e.setPlace(tfplace.getText());
            e.setImage(tfimage.getText());
            e.setTicket_Price(Double.parseDouble(tfprix.getText()));
            e.setE_Date(Date.valueOf(dpdate.getValue()));
            serviceEvent.modifier(e);
            refresh(serviceEvent.afficher());
        }

    }

    @Override
    public void onSupprimerClicked() {
        refresh(serviceEvent.afficher());
    }

    @Override
    public void onModifierClicked(Event e) {
        idModifier=e.getId_Event();
        tfnom.setText(e.getE_Name());
        tfimage.setText(e.getImage());
        tfplace.setText(e.getPlace());
        tfprix.setText(e.getTicket_Price()+"");

        LocalDate localDate=e.getE_Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        dpdate.setValue(localDate);
    }
    public void recherche_avance(){
        refresh(serviceEvent.afficher());
        ObservableList<Event> events= FXCollections.observableArrayList(serviceEvent.afficher());
        FilteredList<Event> filteredList=new FilteredList<>(events,e->true);
        tfrecherche.textProperty().addListener(((observableValue, oldValue, newValue) ->{
            filteredList.setPredicate(
                    e->{
                        if(newValue==null || newValue.isEmpty()){
                            return true;
                        }
                        if(e.getPlace().toLowerCase().contains(newValue.toLowerCase())){
                            return true;
                        } else if (e.getE_Name().toLowerCase().contains(newValue.toLowerCase())) {
                            return true;
                        }else if (String.valueOf(e.getTicket_Price()).contains(newValue.toLowerCase())) {
                            return true;
                        }
                        else if (String.valueOf(e.getE_Date()).contains(newValue.toLowerCase())) {
                            return true;
                        }
                        else{
                            return false;
                        }
                    }
            );
            refresh(filteredList);
        } ));
    }

    public void initData(User user) {

    }
    public void Go_To_Home(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/Home.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
        System.out.println(userlogged);

    }
    public void Go_To_Product(ActionEvent actionEvent) throws IOException {

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

    public void Go_To_Forum(ActionEvent actionEvent)  throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/ForumPages/Member/AfficherForumMembre.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);


    }

    public void Go_To_Event(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/EventPages/afficher-event-view.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
        AfficherEventView afficherEventView = loader.getController();
        afficherEventView.initData(userlogged);




    }

    public void Go_To_Message(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/MessageInterface/Homepage.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
    }
    public void ProfileVisit(MouseEvent mouseEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/LoginSuccess.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);

        LoginSuccess loginSuccess = loader.getController();
        loginSuccess.initData(userlogged);
    }

    public void Logout(MouseEvent mouseEvent) throws IOException {
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

    @FXML
    void sinscrire(ActionEvent event) throws IOException {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/Login_User.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);


    }
}
