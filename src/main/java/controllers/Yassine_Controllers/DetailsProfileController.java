package controllers.Yassine_Controllers;

import controllers.Ali_Controllers.ProductController;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import services.ServiceUser;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class DetailsProfileController {

    public ImageView bell;

    public Label nav_name;

    public ImageView logouticon;
    public ImageView usericon;

    User CurrentUser = LoginController.userlogged;
    public ImageView profile_pic;
    public Button modiermdp;
    public Button Anuller_mdp;
    public Label label6;
    public Label label5;
    public Label label4;
    public Label label3;
    public Label label2;
    public PasswordField id_newmdp;
    public Label label1;
    public ImageView mdp_change;
    public Rectangle back;
    @FXML
    private TextField id_Nom;

    @FXML
    private TextField id_adress;

    @FXML
    private PasswordField id_confirm;

    @FXML
    private DatePicker id_date;

    @FXML
    private Label id_email;

    @FXML
    private PasswordField id_mdp;

    @FXML
    private TextField id_prenom;

    @FXML
    private TextField id_tele;

    @FXML
    private TextField id_user;




    @FXML
    private Label statue_name;
    private User userlogged;
    private String Current_Password;



    private String id_Utlisateur;
    private String Role;
    private String Gender ;



    public void initialize(){

        label1.setVisible(false);
        label2.setVisible(false);
        label3.setVisible(false);
        label4.setVisible(false);
        label5.setVisible(false);
        label6.setVisible(false);
        Anuller_mdp.setVisible(false);
        modiermdp.setVisible(false);
        mdp_change.setVisible(false);
        back.setVisible(false);
        id_newmdp.setVisible(false);
        id_mdp.setVisible(false);
        id_confirm.setVisible(false);

        nav_name.setText(CurrentUser.getUsername());

        bell.setVisible(true);
        usericon.setVisible(true);
        logouticon.setVisible(true);


    }

    @FXML
    void Annuler_Edit(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/Profile_propos.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
        ProfileController profileController = loader.getController();
        profileController.initData(userlogged);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Annuler la Modification");
        alert.setHeaderText(null);
        alert.show();
    }
    User editedUser = new User();
    @FXML
    void Confirme_Edit(ActionEvent event) {
        // Get the edited values from the text fields
        int userId = Integer.parseInt(id_user.getText());
        String firstName = id_Nom.getText();
        String lastName = id_prenom.getText();
        String address = id_adress.getText();

        LocalDate dob = id_date.getValue();
        String email = id_email.getText();
        String username = id_Utlisateur;

        String role = Role;
        String gender = Gender;
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
        // Initialize a new User object
        editedUser.setId_User(userId); // Assuming userId is accessible in this method
        editedUser.setUsername(username);
        editedUser.setEmail(email);
        if (editedUser.getPassword()== null){
            editedUser.setPassword(Current_Password);
        }
        editedUser.setRole(role);
        editedUser.setFirstName(firstName);
        editedUser.setLastName(lastName);
        editedUser.setAdress(address);


        String text = id_tele.getText();
        int phone;
        try {
            phone = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            showAlert("Error", "Enter a valid phone number");
            return;
        }
        if (text.length() != 8) {
            showAlert("Error", "Enter a 6-digit phone number");
            return;
        }





        editedUser.setPhone(phone);
        editedUser.setDOB(dob.toString());
        editedUser.setGender(gender);
        editedUser.setImageURL(ImageURL);
        System.out.println(editedUser);




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

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/Profile_propos.fxml"));
            Parent loginSuccessRoot = loader.load();
            Scene scene = id_user.getScene();
            scene.setRoot(loginSuccessRoot);
            ProfileController profileController = loader.getController();
            profileController.initData(editedUser);
        } catch (SQLException | IOException e) {
            // Handle SQL exception
            e.printStackTrace();
            // Show an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error updating user details: " + e.getMessage());
            alert.showAndWait();
        }


    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    void Details(ActionEvent event) {

    }

    @FXML
    void Gallerie(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Annuler la Modification" );
        alert.showAndWait();
    }

    @FXML
    void Post_Blog(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Annuler la Modification" );
        alert.showAndWait();
    }

    public void initData(User user) {
        statue_name.setText(user.getFirstName() + " " + user.getLastName());

        id_Nom.setText(user.getFirstName());
        id_prenom.setText(user.getLastName());
        id_adress.setText(user.getAdress());
        id_tele.setText(String.valueOf(user.getPhone()));
        id_date.setValue(LocalDate.parse(user.getDOB())); // Assuming getDOB() returns a String in "yyyy-MM-dd" format
        id_email.setText(user.getEmail());
        Current_Password = user.getPassword();
        id_user.setText(String.valueOf(user.getId_User()));
         id_Utlisateur = user.getUsername();
         Role = user.getRole();
        Gender= user.getGender();




        String filePath = user.getImageURL();
        if (!filePath.startsWith("file:/")) {
            filePath = "file:/" + filePath;
        }

        Image image = new Image(filePath);
        Circle clip = new Circle(profile_pic.getFitWidth() / 2, profile_pic.getFitHeight() / 2, Math.min(profile_pic.getFitWidth(), profile_pic.getFitHeight()) / 2);
        profile_pic.setClip(clip);
        profile_pic.setImage(image);



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

    public void AddImage(MouseEvent mouseEvent) {

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

    public void ModifierMdp(ActionEvent actionEvent) {

        String userpassword = id_mdp.getText();
        String usernewpassword = id_newmdp.getText();
        String Confirmuserpass = id_confirm.getText();

        if (!usernewpassword.equals(Confirmuserpass)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("confirmé Votre mot de passe" );
            alert.showAndWait();
        }
        else if (!userpassword.equals(Current_Password) ) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Votre mot de passe actual est Faux" );
            alert.showAndWait();
        }
        else {
            editedUser.setPassword(usernewpassword);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Mot de passe Changé! Confirmé" );
            alert.showAndWait();
            label1.setVisible(false);
            label2.setVisible(false);
            label3.setVisible(false);
            label4.setVisible(false);
            label5.setVisible(false);
            label6.setVisible(false);
            Anuller_mdp.setVisible(false);
            modiermdp.setVisible(false);
            mdp_change.setVisible(false);
            back.setVisible(false);
            id_newmdp.setVisible(false);
            id_mdp.setVisible(false);
            id_confirm.setVisible(false);
            id_mdp.clear();
            id_newmdp.clear();
            id_confirm.clear();

        }
        System.out.println(usernewpassword);




    }

    public void Anuller_mdp(ActionEvent actionEvent) {
        label1.setVisible(false);
        label2.setVisible(false);
        label3.setVisible(false);
        label4.setVisible(false);
        label5.setVisible(false);
        label6.setVisible(false);
        Anuller_mdp.setVisible(false);
        modiermdp.setVisible(false);
        mdp_change.setVisible(false);
        back.setVisible(false);
        id_newmdp.setVisible(false);
        id_mdp.setVisible(false);
        id_confirm.setVisible(false);
        id_mdp.clear();
        id_newmdp.clear();
        id_confirm.clear();
        editedUser.setPassword(Current_Password);



    }

    public void Change_mdp(ActionEvent actionEvent) {
        label1.setVisible(true);
        label2.setVisible(true);
        label3.setVisible(true);
        label4.setVisible(true);
        label5.setVisible(true);
        label6.setVisible(true);
        Anuller_mdp.setVisible(true);
        modiermdp.setVisible(true);
        mdp_change.setVisible(true);
        back.setVisible(true);
        id_newmdp.setVisible(true);
        id_mdp.setVisible(true);
        id_confirm.setVisible(true);

    }

    public void Go_To_Home(ActionEvent actionEvent) {
    }

    public void Go_To_Product(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/ProductPages/Product.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = nav_name.getScene();
        scene.setRoot(loginSuccessRoot);
        ProductController productController = loader.getController();
        productController.initUser(userlogged);
    }

    public void Go_To_Auction(ActionEvent actionEvent) {
    }

    public void Go_To_Forum(ActionEvent actionEvent) {
    }

    public void Go_To_Event(ActionEvent actionEvent) {
    }

    public void Go_To_Message(ActionEvent actionEvent) {
    }

    public void ProfileVisit(MouseEvent mouseEvent) {
    }

    public void Logout(MouseEvent mouseEvent) {
    }

    public void sinscrire(ActionEvent actionEvent) {
    }
}