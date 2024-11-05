package controllers.Admin.User;

import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import services.*;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class AdminController {


    public javafx.scene.image.ImageView icon1;
    public javafx.scene.image.ImageView icon2;
    public javafx.scene.image.ImageView icon3;
    public javafx.scene.image.ImageView icon4;
    public javafx.scene.image.ImageView icon5;
    public javafx.scene.image.ImageView icon6;
    public Rectangle back;
    public javafx.scene.image.ImageView profile_pic;
    public javafx.scene.image.ImageView artyvenci;
    public ImageView ViewUser;
    public Button ViewProfile;
    public ImageView closepic;
    public Rectangle back1;
    public ImageView suppView;
    public Label label21;
    public Button Annuler;
    public Button Confirmé;
    public TextField E_Email;
    public TextField E_Tel;
    public TextField E_Adress;
    public DatePicker E_Date;

    public TextField E_Nom;
    public TextField E_Prénon;
    public TextField E_utilisateur;
    public Button AnnulerEdit;
    public Button ConfirméEdit;
    public ComboBox E_Sexe;
    public ComboBox E_Role;
    public ImageView recherche;
    public TextField recherchenom;
    @FXML
    private TableColumn<User, String>  id_image;
    @FXML
    private TableColumn<User, String> id_adress;

    @FXML
    private TableColumn<User, DatePicker> id_dob;

    @FXML
    private TableColumn<User, String> id_email;

    @FXML
    private TableColumn<User, String> id_firstname;

    @FXML
    private TableColumn<User, String> id_gender;

    @FXML
    private TableColumn<User, String> id_lastname;

    @FXML
    private TableColumn<User, Integer> id_phone;

    @FXML
    private TableColumn<User, String> id_role;

    @FXML
    private TableColumn<User, String> id_username;
    @FXML
    private TableView<User> tv_users;

    @FXML
    private ComboBox<String> id_Choice;



    @FXML
    private Label Adress;

    @FXML
    private Button Editbutton;

    @FXML
    private Label FirstLastname;



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
    private Label Role;




    @FXML
    private Label date;

    @FXML
    private Button deleteButton;

    @FXML
    private Label email1;


    @FXML
    private Label iduser;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private Label label4;

    @FXML
    private Label label5;

    @FXML
    private Label label6;


    @FXML
    private Separator separator;

    @FXML
    private Label sexe;

    @FXML
    private Label telephone;

    @FXML
    private Label username1;


    ServiceProduct p = new ServiceProduct();
     ServiceUser serviceUser = new ServiceUser();
     ServiceForumF serviceForum = new ServiceForumF();
     ServiceAuction serviceAuction = new ServiceAuction();
     ServiceDisscussion serviceDisscussion = new ServiceDisscussion();
     ServiceEvent serviceEvent = new ServiceEvent();

    public void initialize() throws SQLException {

        Num_Forum.setText(""+serviceForum.CountForums());
        Num_Arts.setText(String.valueOf(p.CountProduct()));
        Num_Users.setText(String.valueOf(serviceUser.CountUsers()));
        Num_Auction.setText(String.valueOf(serviceAuction.CountAuction()));
        Num_Messages.setText(String.valueOf(serviceDisscussion.CountDisc()));
        Num_Events.setText(String.valueOf(serviceEvent.CountMesg()));





        label1.setVisible(false);
        label2.setVisible(false);
        label3.setVisible(false);
        label4.setVisible(false);
        label5.setVisible(false);
        label6.setVisible(false);
        icon1.setVisible(false);
        icon2.setVisible(false);
        icon3.setVisible(false);
        icon4.setVisible(false);
        icon5.setVisible(false);
        icon6.setVisible(false);
        back.setVisible(false);
        separator.setVisible(false);
        Editbutton.setVisible(false);
        deleteButton.setVisible(false);
        profile_pic.setVisible(false);
        FirstLastname.setVisible(false);
        username1.setVisible(false);
        email1.setVisible(false);
        telephone.setVisible(false);
        Adress.setVisible(false);
        date.setVisible(false);
        sexe.setVisible(false);
        artyvenci.setVisible(false);
        iduser.setVisible(false);
        Role.setVisible(false);
        ViewUser.setVisible(false);
        closepic.setVisible(false);
        label21.setVisible(false);
        back1.setVisible(false);
        Confirmé.setVisible(false);
        Annuler.setVisible(false);
        suppView.setVisible(false);
        E_Nom.setVisible(false);
        E_Date.setVisible(false);
        E_Adress.setVisible(false);
        E_Role.setVisible(false);
        E_Tel.setVisible(false);
        E_Prénon.setVisible(false);
        E_utilisateur.setVisible(false);
        E_Sexe.setVisible(false);
        E_Email.setVisible(false);
        ConfirméEdit.setVisible(false);
        AnnulerEdit.setVisible(false);



        initializeComboBoxContent();
        initializeComboBoxContentEdit();
        Num_Users.setText(String.valueOf(serviceUser.CountUsers()));


        id_Choice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if ("Artist".equals(newValue)) {
                try {
                    ObservableList<User> observableList = FXCollections.observableList(serviceUser.DISPLAYARTIST());
                    tv_users.setItems(observableList);

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            else  if ("Member".equals(newValue)) {
                try {
                    ObservableList<User> observableList = FXCollections.observableList(serviceUser.DISPLAYMember());
                    tv_users.setItems(observableList);

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        try {
            ObservableList<User> observableList = FXCollections.observableList(serviceUser.DISPLAY());
            tv_users.setItems(observableList);
            id_username.setCellValueFactory(new PropertyValueFactory<>("Username"));
            id_email.setCellValueFactory(new PropertyValueFactory<>("Email"));
            id_role.setCellValueFactory(new PropertyValueFactory<>("Role"));
            id_firstname.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
            id_lastname.setCellValueFactory(new PropertyValueFactory<>("LastName"));
            id_adress.setCellValueFactory(new PropertyValueFactory<>("Adress"));
            id_phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
            id_gender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
            id_dob.setCellValueFactory(new PropertyValueFactory<>("DOB"));
            id_image.setCellValueFactory(new PropertyValueFactory<>("ImageURL"));
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void initializeComboBoxContent() {
        // Initialize  choices
        id_Choice.getItems().addAll("Member", "Artist"); // Example Role choices

    }

    public void logout(MouseEvent mouseEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/Login_User.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = id_Choice.getScene();
        scene.setRoot(loginSuccessRoot);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText(null);
        alert.show();

    }

    public void Delete(ActionEvent actionEvent) {


        label21.setVisible(true);
        back1.setVisible(true);
        Confirmé.setVisible(true);
        Annuler.setVisible(true);
        suppView.setVisible(true);


    }

    public void Edit(ActionEvent actionEvent) {



        FirstLastname.setVisible(false);
        username1.setVisible(false);
        email1.setVisible(false);
        telephone.setVisible(false);
        Adress.setVisible(false);
        date.setVisible(false);
        sexe.setVisible(false);
        Role.setVisible(false);
        E_Nom.setVisible(true);
        E_Date.setVisible(true);
        E_Adress.setVisible(true);
        E_Role.setVisible(true);
        E_Tel.setVisible(true);
        E_Prénon.setVisible(true);
        E_utilisateur.setVisible(true);
        E_Sexe.setVisible(true);
        E_Email.setVisible(true);
        Editbutton.setVisible(false);
        deleteButton.setVisible(false);
        ConfirméEdit.setVisible(true);
        AnnulerEdit.setVisible(true);
        icon6.setVisible(false);


        User selectedItem = tv_users.getSelectionModel().getSelectedItem();


            E_Prénon.setText(selectedItem.getLastName());
            E_Nom.setText(selectedItem.getFirstName());
            E_utilisateur.setText(selectedItem.getUsername());
            E_Email.setText(selectedItem.getEmail());
            E_Role.setValue(selectedItem.getRole());
            E_Tel.setText(String.valueOf(selectedItem.getPhone()));
            E_Adress.setText(selectedItem.getAdress());
            E_Date.setValue(LocalDate.parse(selectedItem.getDOB()));
            E_Sexe.setValue(selectedItem.getGender());










    }

    public void initData(User user) {
    }

    @FXML
    void AddImage(MouseEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());

            Circle clip = new Circle(profile_pic.getFitWidth() / 2, profile_pic.getFitHeight() / 2, Math.min(profile_pic.getFitWidth(), profile_pic.getFitHeight()) / 2);
            profile_pic.setClip(clip);
            profile_pic.setImage(image);

        }

    }
    private void initializeComboBoxContentEdit() {
        // Initialize  choices
        E_Role.getItems().addAll("Member", "Artist"); // Example Role choices
        E_Sexe.getItems().addAll("Homme", "Femme", "Autre"); // Example gender choices

    }
    public void ViewProfile(ActionEvent actionEvent) {
        label1.setVisible(true);
        label2.setVisible(true);
        label3.setVisible(true);
        label4.setVisible(true);
        label5.setVisible(true);
        label6.setVisible(true);
        icon1.setVisible(true);
        icon2.setVisible(true);
        icon3.setVisible(true);
        icon4.setVisible(true);
        icon5.setVisible(true);
        icon6.setVisible(true);
        back.setVisible(true);
        separator.setVisible(true);
        Editbutton.setVisible(true);
        deleteButton.setVisible(true);
        profile_pic.setVisible(true);
        FirstLastname.setVisible(true);
        username1.setVisible(true);
        email1.setVisible(true);
        telephone.setVisible(true);
        Adress.setVisible(true);
        date.setVisible(true);
        sexe.setVisible(true);
        artyvenci.setVisible(true);
        iduser.setVisible(true);
        Role.setVisible(true);
        ViewUser.setVisible(true);
        closepic.setVisible(true);


        User selectedItem = tv_users.getSelectionModel().getSelectedItem();
         FirstLastname.setText(selectedItem.getFirstName() + " " + selectedItem.getLastName());
         username1.setText(selectedItem.getUsername());
         email1.setText(selectedItem.getEmail());
         Role.setText(selectedItem.getRole());
         iduser.setText(String.valueOf((selectedItem.getId_User())));
         telephone.setText(String.valueOf(selectedItem.getPhone()));
         Adress.setText(selectedItem.getAdress());
         date.setText(selectedItem.getDOB());
         sexe.setText(selectedItem.getGender());

        String filePath = selectedItem.getImageURL();

        if (filePath != null){
            if (!filePath.startsWith("file:/")) {
                filePath = "file:/" + filePath;
            }

            javafx.scene.image.Image image = new Image(filePath);
            Circle clip = new Circle(profile_pic.getFitWidth() / 2, profile_pic.getFitHeight() / 2, Math.min(profile_pic.getFitWidth(), profile_pic.getFitHeight()) / 2);
            profile_pic.setClip(clip);
            profile_pic.setImage(image);
        }
        else {
            profile_pic.setImage(new Image("file:D:\\Heikun\\ESPRIT 2023-2024\\JAVA\\TD Revision\\ArtyVenci\\src\\main\\resources\\images\\Avatar.png"));
        }


    }

    public void CloseView(MouseEvent mouseEvent) {

        label1.setVisible(false);
        label2.setVisible(false);
        label3.setVisible(false);
        label4.setVisible(false);
        label5.setVisible(false);
        label6.setVisible(false);
        icon1.setVisible(false);
        icon2.setVisible(false);
        icon3.setVisible(false);
        icon4.setVisible(false);
        icon5.setVisible(false);
        icon6.setVisible(false);
        back.setVisible(false);
        separator.setVisible(false);
        Editbutton.setVisible(false);
        deleteButton.setVisible(false);
        profile_pic.setVisible(false);
        FirstLastname.setVisible(false);
        username1.setVisible(false);
        email1.setVisible(false);
        telephone.setVisible(false);
        Adress.setVisible(false);
        date.setVisible(false);
        sexe.setVisible(false);
        artyvenci.setVisible(false);
        iduser.setVisible(false);
        Role.setVisible(false);
        ViewUser.setVisible(false);
        closepic.setVisible(false);
        E_Nom.setVisible(false);
        E_Date.setVisible(false);
        E_Adress.setVisible(false);
        E_Role.setVisible(false);
        E_Tel.setVisible(false);
        E_Prénon.setVisible(false);
        E_utilisateur.setVisible(false);
        E_Sexe.setVisible(false);
        E_Email.setVisible(false);
        ConfirméEdit.setVisible(false);
        AnnulerEdit.setVisible(false);


    }

    public void ConfirmButton(ActionEvent actionEvent) {
        User selectedItem = tv_users.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            int User_id = selectedItem.getId_User();
            try {
                // Delete the item from the database
                serviceUser.DELETEUser(User_id);

                // Remove the item from the ObservableList
                ObservableList<User> userList = tv_users.getItems();
                userList.remove(selectedItem);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        label1.setVisible(false);
        label2.setVisible(false);
        label3.setVisible(false);
        label4.setVisible(false);
        label5.setVisible(false);
        label6.setVisible(false);
        icon1.setVisible(false);
        icon2.setVisible(false);
        icon3.setVisible(false);
        icon4.setVisible(false);
        icon5.setVisible(false);
        icon6.setVisible(false);
        back.setVisible(false);
        separator.setVisible(false);
        Editbutton.setVisible(false);
        deleteButton.setVisible(false);
        profile_pic.setVisible(false);
        FirstLastname.setVisible(false);
        username1.setVisible(false);
        email1.setVisible(false);
        telephone.setVisible(false);
        Adress.setVisible(false);
        date.setVisible(false);
        sexe.setVisible(false);
        artyvenci.setVisible(false);
        iduser.setVisible(false);
        Role.setVisible(false);
        ViewUser.setVisible(false);
        closepic.setVisible(false);
        label21.setVisible(false);
        back1.setVisible(false);
        Confirmé.setVisible(false);
        Annuler.setVisible(false);
        suppView.setVisible(false);


    }

    public void AnnulerButton(ActionEvent actionEvent) {

        label21.setVisible(false);
        back1.setVisible(false);
        Confirmé.setVisible(false);
        Annuler.setVisible(false);
        suppView.setVisible(false);


    }

    public void AnnulerEditButton(ActionEvent actionEvent) {

        FirstLastname.setVisible(true);
        username1.setVisible(true);
        email1.setVisible(true);
        telephone.setVisible(true);
        Adress.setVisible(true);
        date.setVisible(true);
        sexe.setVisible(true);
        Role.setVisible(true);
        E_Nom.setVisible(false);
        E_Date.setVisible(false);
        E_Adress.setVisible(false);
        E_Role.setVisible(false);
        E_Tel.setVisible(false);
        E_Prénon.setVisible(false);
        E_utilisateur.setVisible(false);
        E_Sexe.setVisible(false);
        E_Email.setVisible(false);
        Editbutton.setVisible(true);
        deleteButton.setVisible(true);
        ConfirméEdit.setVisible(false);
        AnnulerEdit.setVisible(false);
        icon6.setVisible(true);
    }

    public void ConfirméEditButton(ActionEvent actionEvent) {





        User selectedItem = tv_users.getSelectionModel().getSelectedItem();


        int userId = Integer.parseInt(iduser.getText());
        String firstName = E_Nom.getText();
        String lastName = E_Prénon.getText();
        String address = E_Adress.getText();
        int phone = Integer.parseInt(E_Tel.getText());
        LocalDate dob = E_Date.getValue();
        String email = E_Email.getText();
        String username = E_utilisateur.getText();
        String password = selectedItem.getPassword();
        String role = (String) E_Role.getValue();
        String gender = (String) E_Sexe.getValue();
        String ImageURL = "";
        Image image = profile_pic.getImage();
        if (image != null) {
            String imageUrl = image.getUrl();
            ImageURL = imageUrl;
//            if (imageUrl.startsWith("file:/")) {
//                ImageURL = imageUrl.substring("file:/".length());
//            }
        }

        // Create a new User object with the edited values
        User editedUser = new User(); // Initialize a new User object
        editedUser.setId_User(userId); // Assuming userId is accessible in this method
        editedUser.setUsername(username);
        editedUser.setEmail(email);
        editedUser.setPassword(password);
        editedUser.setRole(role);
        editedUser.setFirstName(firstName);
        editedUser.setLastName(lastName);
        editedUser.setAdress(address);
        editedUser.setPhone(phone);
        editedUser.setDOB(dob.toString());
         editedUser.setGender(gender);
         editedUser.setImageURL(ImageURL);

        // Initialize ServiceUser
        ServiceUser serviceUser = new ServiceUser();

        try {
            // Update the user details using the ServiceUser
            serviceUser.UPDATE(editedUser);

            // Show a confirmation message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Edit User");
            alert.setHeaderText(null);
            alert.setContentText("User details updated successfully");
            alert.showAndWait();


        } catch (SQLException e) {
            // Handle SQL exception
            e.printStackTrace();
            // Show an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error updating user details: " + e.getMessage());
            alert.showAndWait();
        }

        try {
            tv_users.getItems().clear();
            ObservableList<User> observableList = FXCollections.observableList(serviceUser.DISPLAY());
            tv_users.setItems(observableList);
            id_username.setCellValueFactory(new PropertyValueFactory<>("Username"));
            id_email.setCellValueFactory(new PropertyValueFactory<>("Email"));
            id_role.setCellValueFactory(new PropertyValueFactory<>("Role"));
            id_firstname.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
            id_lastname.setCellValueFactory(new PropertyValueFactory<>("LastName"));
            id_adress.setCellValueFactory(new PropertyValueFactory<>("Adress"));
            id_phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
            id_gender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
            id_dob.setCellValueFactory(new PropertyValueFactory<>("DOB"));
            id_image.setCellValueFactory(new PropertyValueFactory<>("ImageURL"));
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        label1.setVisible(false);
        label2.setVisible(false);
        label3.setVisible(false);
        label4.setVisible(false);
        label5.setVisible(false);
        label6.setVisible(false);
        icon1.setVisible(false);
        icon2.setVisible(false);
        icon3.setVisible(false);
        icon4.setVisible(false);
        icon5.setVisible(false);
        icon6.setVisible(false);
        back.setVisible(false);
        separator.setVisible(false);
        Editbutton.setVisible(false);
        deleteButton.setVisible(false);
        profile_pic.setVisible(false);
        FirstLastname.setVisible(false);
        username1.setVisible(false);
        email1.setVisible(false);
        telephone.setVisible(false);
        Adress.setVisible(false);
        date.setVisible(false);
        sexe.setVisible(false);
        artyvenci.setVisible(false);
        iduser.setVisible(false);
        Role.setVisible(false);
        ViewUser.setVisible(false);
        closepic.setVisible(false);
        E_Nom.setVisible(false);
        E_Date.setVisible(false);
        E_Adress.setVisible(false);
        E_Role.setVisible(false);
        E_Tel.setVisible(false);
        E_Prénon.setVisible(false);
        E_utilisateur.setVisible(false);
        E_Sexe.setVisible(false);
        E_Email.setVisible(false);
        ConfirméEdit.setVisible(false);
        AnnulerEdit.setVisible(false);

    }

    public void rechercheuser(MouseEvent mouseEvent) {
        try {
            tv_users.getItems().clear();
            String searchText = recherchenom.getText().trim();
            ObservableList<User> observableList = FXCollections.observableList(serviceUser.DISPLAY());

            List<User> filteredList = observableList.stream()
                    .filter(e -> e.getUsername().toLowerCase().contains(searchText.toLowerCase()))
                    .collect(Collectors.toList());

            ObservableList<User> filteredObservableList = FXCollections.observableList(filteredList);
            tv_users.setItems(filteredObservableList);
            id_username.setCellValueFactory(new PropertyValueFactory<>("Username"));
            id_email.setCellValueFactory(new PropertyValueFactory<>("Email"));
            id_role.setCellValueFactory(new PropertyValueFactory<>("Role"));
            id_firstname.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
            id_lastname.setCellValueFactory(new PropertyValueFactory<>("LastName"));
            id_adress.setCellValueFactory(new PropertyValueFactory<>("Adress"));
            id_phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
            id_gender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
            id_dob.setCellValueFactory(new PropertyValueFactory<>("DOB"));
            id_image.setCellValueFactory(new PropertyValueFactory<>("ImageURL"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/EventPages/ajouter-event-view.fxml"));
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
    void Go_To_Messages(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin_Interface/Admin_Message.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = Num_Arts.getScene();
        scene.setRoot(loginSuccessRoot);
    }

    @FXML
    void Go_To_Product(MouseEvent event)throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin_Interface/Admin_Produit.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = Num_Arts.getScene();
        scene.setRoot(loginSuccessRoot);
    }

    @FXML
    void Go_To_User(MouseEvent event) {

    }
}