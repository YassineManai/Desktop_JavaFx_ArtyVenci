package controllers.Yassine_Controllers;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import services.ServiceUser;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class SignUpController  {


    public javafx.scene.image.ImageView id_phnepic;
    public ImageView id_userpic2;
    public ImageView id_emailpic;
    public ImageView mdppic;
    public ImageView id_mdppic;
    public ImageView id_Profile_Pic;
    public ImageView id_location_pic;
    public ImageView id_userpicu2;
    public ImageView id_userpicu1;
    public Button sincrire;
    public Button suivant;
    public Button suivant2;
    public ImageView goback1;
    public ImageView goback2;
    public Button oui;
    public Button Non;
    public SubScene subscene;
    public Label anulller_message;
    ServiceUser serviceUser = new ServiceUser();


    @FXML
    private Label adress_label;

    @FXML
    private Label date_label;

    @FXML
    private Label email_label;


    @FXML
    private Label mdp_label;



    @FXML
    private Label nom_label;





    @FXML
    private Label prenom_label;

    @FXML
    private Label role_label;

    @FXML
    private Label sexe_label;

    @FXML
    private Label telelabel;



    @FXML
    private Label utilisateur_laber;

    @FXML
    private TextField id_Username;
    @FXML
    private TextField id_Email;
    @FXML
    private PasswordField id_Password;
    @FXML
    private ComboBox<String> id_Role;
    @FXML
    private TextField id_Firstname;
    @FXML
    private TextField id_Lastname;
    @FXML
    private TextField id_Adress;
    @FXML
    private DatePicker id_Dob;
    @FXML
    private ComboBox<String> id_Gender;
    @FXML
    private TextField id_Phone;
    @FXML
    private PasswordField id_CPassword;
    @FXML
    private Label profilemessage;




    public void initialize() {

        initializeComboBoxContent();

       adress_label.setVisible(false);
       date_label.setVisible(false);
       email_label.setVisible(true);
       mdp_label.setVisible(true);
       nom_label.setVisible(false);
       prenom_label.setVisible(false);
       role_label.setVisible(true);
       sexe_label.setVisible(false);
       telelabel.setVisible(false);
       utilisateur_laber.setVisible(true);
       profilemessage.setVisible(false);


       id_CPassword.setVisible(true);
       id_Gender.setVisible(false);
       id_Password.setVisible(true);
       id_CPassword.setVisible(true);
       id_Role.setVisible(true);
       id_Dob.setVisible(false);
       id_Firstname.setVisible(false);
       id_Lastname.setVisible(false);
       id_Phone.setVisible(false);
       id_Username.setVisible(true);
       id_Email.setVisible(true);


       sincrire.setVisible(false);
       suivant.setVisible(true);
       suivant2.setVisible(false);
    

       id_phnepic.setVisible(false);
       id_userpic2.setVisible(true);
       id_userpicu1.setVisible(false);
       id_userpicu2.setVisible(false);
       id_emailpic.setVisible(true);
       id_location_pic.setVisible(false);
       id_Profile_Pic.setVisible(false);
       id_mdppic.setVisible(true);
       mdppic.setVisible(true);
       goback1.setVisible(false);
       goback2.setVisible(false);


        subscene.setVisible(false);
        oui.setVisible(false);
        Non.setVisible(false);
        anulller_message.setVisible(false);

    }
    public void SignUp(javafx.event.ActionEvent actionEvent) {
        String username = id_Username.getText();
        String email = id_Email.getText();
        String password = id_Password.getText();
        String role = id_Role.getValue();
        String firstName = id_Firstname.getText();
        String lastName = id_Lastname.getText();
        String address = id_Adress.getText();
        String gender = id_Gender.getValue();
        String phoneText = id_Phone.getText();
        LocalDate dob = id_Dob.getValue();

        int phone = Integer.parseInt(phoneText);
        String ImageURL = "";
        Image image = id_Profile_Pic.getImage();
        if (image != null) {
            String imageUrl = image.getUrl();
            ImageURL = imageUrl;
//            if (imageUrl.startsWith("file:/")) {
//                ImageURL = imageUrl.substring("file:/".length());
//            }
        }



        // Now you can proceed with adding the user
        try {
            serviceUser.ADD(new User(username, email, password, role, firstName, lastName, address, gender, phone, dob,ImageURL));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bienvenue");
            alert.setContentText("Bienvenue Dans ArtyVenci");
            alert.showAndWait();
            // Redirect to login page
            redirectToLogin();
        } catch (SQLException | IOException e) {
            showAlert("Error", e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    private void redirectToLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/Login_User.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = id_Password.getScene(); // Get the scene from any node in the current scene
        scene.setRoot(loginSuccessRoot);
    }

    private void initializeComboBoxContent() {
            // Initialize  choices
            id_Role.getItems().addAll("Member", "Artist"); // Example Role choices
            id_Gender.getItems().addAll("Homme", "Femme", "Autre"); // Example gender choices
    }

    public void GoBackClick(MouseEvent mouseEvent) throws IOException {
      subscene.setVisible(true);
      oui.setVisible(true);
      Non.setVisible(true);
        anulller_message.setVisible(true);
    }

    public void Suivanttoprofile(ActionEvent actionEvent) {


        String firstName = id_Firstname.getText();
        String lastName = id_Lastname.getText();
        String address = id_Adress.getText();
        String gender = id_Gender.getValue();
        String phoneText = id_Phone.getText();
        LocalDate dob = id_Dob.getValue();


        if (firstName.isEmpty() || lastName.isEmpty() || address.isEmpty() || gender == null  || phoneText.isEmpty() || dob == null ) {
            showAlert("Error", "Please fill in all the fields.");
            return;
        }
        String text = id_Phone.getText();
        if (text.length() != 8) {
            showAlert("Error", "Enter a 6-digit phone number");
            return;
        }

        int phone;
        try {
            phone = Integer.parseInt(phoneText);
        } catch (NumberFormatException e) {
            showAlert("Error", "Enter a valid phone number");
            return;
        }

        adress_label.setVisible(false);
        date_label.setVisible(false);
        nom_label.setVisible(false);
        prenom_label.setVisible(false);
        sexe_label.setVisible(false);
        telelabel.setVisible(false);
        id_Gender.setVisible(false);
        id_Dob.setVisible(false);
        id_Firstname.setVisible(false);
        id_Lastname.setVisible(false);
        id_Phone.setVisible(false);
        id_Adress.setVisible(false);
        suivant2.setVisible(false);
        id_phnepic.setVisible(false);
        id_userpicu1.setVisible(false);
        id_userpicu2.setVisible(false);
        id_location_pic.setVisible(false);
        id_Profile_Pic.setVisible(true);
        profilemessage.setVisible(true);
        sincrire.setVisible(true);
        goback1.setVisible(false);
        goback2.setVisible(true);




    }

    public void Suivant(ActionEvent actionEvent) {
        String email = id_Email.getText();
        String password = id_Password.getText();
        String role = id_Role.getValue();
        String Cpassword = id_CPassword.getText();
        String username = id_Username.getText();


        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || role == null  || Cpassword == null) {
            showAlert("Error", "Please fill in all the fields.");
            return;
        }
        else if (!password.equals(Cpassword)) {
            showAlert("Error", "les 2 password son't faux");
            return;
        }
        if (!isValidEmail(email)) {
            showAlert("Error", "Entrer un Adress Email Valide");
            return;
        }



        adress_label.setVisible(true);
        date_label.setVisible(true);
        email_label.setVisible(false);
        mdp_label.setVisible(false);
        nom_label.setVisible(true);
        prenom_label.setVisible(true);
        role_label.setVisible(false);
        sexe_label.setVisible(true);
        telelabel.setVisible(true);
        utilisateur_laber.setVisible(false);
        goback1.setVisible(true);




        id_CPassword.setVisible(false);
        id_Gender.setVisible(true);
        id_Password.setVisible(false);
        id_CPassword.setVisible(false);
        id_Role.setVisible(false);
        id_Dob.setVisible(true);
        id_Firstname.setVisible(true);
        id_Lastname.setVisible(true);
        id_Phone.setVisible(true);
        id_Username.setVisible(false);
        id_Email.setVisible(false);
        id_Adress.setVisible(true);



        suivant.setVisible(false);
        suivant2.setVisible(true);


        id_phnepic.setVisible(true);
        id_userpic2.setVisible(false);
        id_userpicu1.setVisible(true);
        id_userpicu2.setVisible(true);
        id_emailpic.setVisible(false);
        id_location_pic.setVisible(true);
        id_Profile_Pic.setVisible(false);
        id_mdppic.setVisible(false);
        mdppic.setVisible(false);





    }

    public void Addimage(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());

            Circle clip = new Circle(id_Profile_Pic.getFitWidth() / 2, id_Profile_Pic.getFitHeight() / 2, Math.min(id_Profile_Pic.getFitWidth(), id_Profile_Pic.getFitHeight()) / 2);
            id_Profile_Pic.setClip(clip);
            id_Profile_Pic.setImage(image);

        }
    }

    public void goback1(MouseEvent mouseEvent) {

        adress_label.setVisible(false);
        date_label.setVisible(false);
        email_label.setVisible(true);
        mdp_label.setVisible(true);
        nom_label.setVisible(false);
        prenom_label.setVisible(false);
        role_label.setVisible(true);
        sexe_label.setVisible(false);
        telelabel.setVisible(false);
        utilisateur_laber.setVisible(true);
        profilemessage.setVisible(false);


        id_CPassword.setVisible(true);
        id_Gender.setVisible(false);
        id_Password.setVisible(true);
        id_CPassword.setVisible(true);
        id_Role.setVisible(true);
        id_Dob.setVisible(false);
        id_Firstname.setVisible(false);
        id_Lastname.setVisible(false);
        id_Phone.setVisible(false);
        id_Username.setVisible(true);
        id_Email.setVisible(true);
        id_Adress.setVisible(false);


        sincrire.setVisible(false);
        suivant.setVisible(true);
        suivant2.setVisible(false);


        id_phnepic.setVisible(false);
        id_userpic2.setVisible(true);
        id_userpicu1.setVisible(false);
        id_userpicu2.setVisible(false);
        id_emailpic.setVisible(true);
        id_location_pic.setVisible(false);
        id_Profile_Pic.setVisible(false);
        id_mdppic.setVisible(true);
        mdppic.setVisible(true);

    }

    public void goback2(MouseEvent mouseEvent) {
        adress_label.setVisible(true);
        date_label.setVisible(true);
        email_label.setVisible(false);
        mdp_label.setVisible(false);
        nom_label.setVisible(true);
        prenom_label.setVisible(true);
        role_label.setVisible(false);
        sexe_label.setVisible(true);
        telelabel.setVisible(true);
        utilisateur_laber.setVisible(false);
        profilemessage.setVisible(false);
        sincrire.setVisible(false);

goback2.setVisible(false);
goback1.setVisible(true);

        id_CPassword.setVisible(false);
        id_Gender.setVisible(true);
        id_Password.setVisible(false);
        id_CPassword.setVisible(false);
        id_Role.setVisible(false);
        id_Dob.setVisible(true);
        id_Firstname.setVisible(true);
        id_Lastname.setVisible(true);
        id_Phone.setVisible(true);
        id_Username.setVisible(false);
        id_Email.setVisible(false);
        id_Adress.setVisible(true);



        suivant.setVisible(false);
        suivant2.setVisible(true);


        id_phnepic.setVisible(true);
        id_userpic2.setVisible(false);
        id_userpicu1.setVisible(true);
        id_userpicu2.setVisible(true);
        id_emailpic.setVisible(false);
        id_location_pic.setVisible(true);
        id_Profile_Pic.setVisible(false);
        id_mdppic.setVisible(false);
        mdppic.setVisible(false);

    }

    public void oui(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/Login_User.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = id_Password.getScene(); // Get the scene from any node in the current scene
        scene.setRoot(loginSuccessRoot); // Set the root of the current scene to the LoginSuccess scene
    }

    public void Non(ActionEvent actionEvent) {
        subscene.setVisible(false);
        oui.setVisible(false);
        Non.setVisible(false);
        anulller_message.setVisible(false);
    }
}
