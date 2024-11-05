package controllers.Yassine_Controllers;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import services.ServiceUser;

import java.io.IOException;
import java.security.SecureRandom;
import java.sql.SQLException;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class ResetAccountController {

    public Label label20;
    public Label AutreMethode;
    public Button EnvoyerSMS;
    public TextField id_phone;
    public Label labelS2;
    public Label labelS1;
    public Label labelS4;
    public Label labelS5;
    public ImageView iconS1;
    public Label labelS3;
    @FXML
    private Label O_Utilisateur;

    @FXML
    private Label O_motpasse;

    @FXML
    private ImageView Retour;

    @FXML
    private ImageView Retour1;

    @FXML
    private Button envoyer_email1;

    @FXML
    private Button envoyer_email2;

    @FXML
    private TextField id_Username;

    @FXML
    private TextField id_email;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private Label label5;

    @FXML
    private Label labelv1;

    @FXML
    private Label labelv2;

    @FXML
    private Label labelv3;

    @FXML
    private Label labelv4;

    @FXML
    private Label labelv5;

ServiceUser serviceUser = new ServiceUser();
    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%&*()-+";

    private static final String PASSWORD_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + NUMBER + SPECIAL_CHARACTERS;
    private static SecureRandom random = new SecureRandom();
    public void initialize(){
        labelv1.setVisible(false);
        labelv2.setVisible(false);
        labelv3.setVisible(false);
        labelv4.setVisible(false);
        labelv5.setVisible(false);
        envoyer_email2.setVisible(false);
        Retour1.setVisible(false);
        id_email.setVisible(false);
        O_Utilisateur.setVisible(true);
        label1.setVisible(true);
        label2.setVisible(true);
        label3.setVisible(true);
        label5.setVisible(true);
        envoyer_email1.setVisible(true);
        Retour.setVisible(true);
        id_Username.setVisible(true);
        O_motpasse.setVisible(false);
        label20.setVisible(true);



        EnvoyerSMS.setVisible(false);
                id_phone.setVisible(false);
        labelS2.setVisible(false);
                labelS1.setVisible(false);

        labelS4.setVisible(false);
                labelS5.setVisible(false);
        iconS1.setVisible(false);
                labelS3.setVisible(false);
                AutreMethode.setVisible(true);
    }
    public static String generateRandomPassword(int length) {
        if (length < 1) throw new IllegalArgumentException();

        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(PASSWORD_ALLOW_BASE.length());
            password.append(PASSWORD_ALLOW_BASE.charAt(randomIndex));
        }
        return password.toString();
    }

    @FXML
    void Envoyer_Email(ActionEvent event) throws Exception {
        String username = id_Username.getText();
        User user = serviceUser.GetUserFromUsername(username);
        if (user != null){
            String password = generateRandomPassword(12);
            user.setPassword(password);
            serviceUser.UPDATE(user);

            JavaMailutil.SendMail(user.getEmail(),password);



            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Email Envoyer Avec Succes");
            alert.setHeaderText(null);
            alert.setContentText("Vérifier votre Email");
            alert.showAndWait();
            // Redirect to login page
            redirectToLogin();
        }else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Username saisie est incorrect!");
            alert.showAndWait();

        }

    }

    @FXML
    void Envoyer_Email2(ActionEvent event) throws Exception {
        String Email = id_email.getText();
        User user = serviceUser.GetUserFromEmail(Email);
        if (user != null){
            String password = generateRandomPassword(12);
            user.setPassword(password);
            serviceUser.UPDATE(user);

            JavaMailutil.SendMail2(user.getEmail(),password, user.getUsername());



            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Email Envoyer Avec Succes");
            alert.setHeaderText(null);
            alert.setContentText("Vérifier votre Email");
            alert.showAndWait();
            // Redirect to login page
            redirectToLogin();
        }else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Email saisie est incorrect!");
            alert.showAndWait();

        }

    }

    @FXML
    void GoBackClick(MouseEvent event) {

    }

    @FXML
    void Retour(MouseEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/Login_User.fxml"));
            Parent loginSuccessRoot = loader.load();
            Scene scene = id_Username.getScene(); // Get the scene from any node in the current scene
            scene.setRoot(loginSuccessRoot); // Set the root of the current scene to the LoginSuccess scene
    }

    @FXML
    void Retour1(MouseEvent event) {
        labelv1.setVisible(false);
        labelv2.setVisible(false);
        labelv3.setVisible(false);
        labelv4.setVisible(false);
        labelv5.setVisible(false);
        envoyer_email2.setVisible(false);
        Retour1.setVisible(false);
        id_email.setVisible(false);
        O_Utilisateur.setVisible(true);
        label1.setVisible(true);
        label2.setVisible(true);
        label3.setVisible(true);
        label5.setVisible(true);
        envoyer_email1.setVisible(true);
        Retour.setVisible(true);
        id_Username.setVisible(true);
        O_motpasse.setVisible(false);
        label20.setVisible(true);

        EnvoyerSMS.setVisible(false);
        id_phone.setVisible(false);
        labelS2.setVisible(false);
        labelS1.setVisible(false);

        labelS4.setVisible(false);
        labelS5.setVisible(false);
        iconS1.setVisible(false);
        labelS3.setVisible(false);
        AutreMethode.setVisible(true);
    }

    @FXML
    void oublienUtilisateur(MouseEvent event) {

        labelv1.setVisible(true);
        labelv2.setVisible(true);
        labelv3.setVisible(true);
        labelv4.setVisible(true);
        labelv5.setVisible(true);
        envoyer_email2.setVisible(true);
        Retour1.setVisible(true);
        id_email.setVisible(true);
        O_Utilisateur.setVisible(false);
        label1.setVisible(false);
        label2.setVisible(false);
        label3.setVisible(false);
        label5.setVisible(false);
        envoyer_email1.setVisible(false);
        Retour.setVisible(false);
        id_Username.setVisible(false);
        O_motpasse.setVisible(true);
        label20.setVisible(false);

        EnvoyerSMS.setVisible(false);
        id_phone.setVisible(false);
        labelS2.setVisible(false);
        labelS1.setVisible(false);

        labelS4.setVisible(false);
        labelS5.setVisible(false);
        iconS1.setVisible(false);
        labelS3.setVisible(false);
        AutreMethode.setVisible(true);

    }

    public void forgetpassword(MouseEvent mouseEvent) {


        labelv1.setVisible(false);
        labelv2.setVisible(false);
        labelv3.setVisible(false);
        labelv4.setVisible(false);
        labelv5.setVisible(false);
        envoyer_email2.setVisible(false);
        Retour1.setVisible(false);
        id_email.setVisible(false);
        O_Utilisateur.setVisible(true);
        label1.setVisible(true);
        label2.setVisible(true);
        label3.setVisible(true);
        label5.setVisible(true);
        envoyer_email1.setVisible(true);
        Retour.setVisible(true);
        id_Username.setVisible(true);
        O_motpasse.setVisible(false);
        label20.setVisible(true);

        EnvoyerSMS.setVisible(false);
        id_phone.setVisible(false);
        labelS2.setVisible(false);
        labelS1.setVisible(false);

        labelS4.setVisible(false);
        labelS5.setVisible(false);
        iconS1.setVisible(false);
        labelS3.setVisible(false);
        AutreMethode.setVisible(true);

    }

    public void AutreMethode(MouseEvent mouseEvent) {

        labelv1.setVisible(true);
        labelv2.setVisible(false);
        labelv3.setVisible(false);
        labelv4.setVisible(false);
        labelv5.setVisible(false);
        envoyer_email2.setVisible(false);
        Retour1.setVisible(true);
        id_email.setVisible(false);
        O_Utilisateur.setVisible(false);
        label1.setVisible(false);
        label2.setVisible(false);
        label3.setVisible(false);
        label5.setVisible(false);
        envoyer_email1.setVisible(false);
        Retour.setVisible(false);
        id_Username.setVisible(false);
        O_motpasse.setVisible(false);
        label20.setVisible(false);

        EnvoyerSMS.setVisible(true);
        id_phone.setVisible(true);
        labelS2.setVisible(true);
        labelS1.setVisible(true);

        labelS4.setVisible(true);
        labelS5.setVisible(true);
        iconS1.setVisible(true);
        labelS3.setVisible(true);
        AutreMethode.setVisible(false);


    }

    private void redirectToLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/Login_User.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = id_Username.getScene(); // Get the scene from any node in the current scene
        scene.setRoot(loginSuccessRoot);
    }




    public void EnvoyerSMS(ActionEvent actionEvent) throws SQLException, IOException {

        int phone;
        try {
            phone = Integer.parseInt(id_phone.getText());
        } catch (NumberFormatException e) {
            showAlert("Error", "Enter a valid phone number");
            return;
        }
        String phonereciver = id_phone.getText();
        if (phonereciver.length() != 8) {
            showAlert("Error", "Enter a 6-digit phone number");
            return;
        }
        User user = serviceUser.GetUserFromPhone(phone);
        if (user != null) {
            String password = generateRandomPassword(12);
            user.setPassword(password);
            serviceUser.UPDATE(user);

     Twilio.init("","");



            Message message = Message.creator(
                            new com.twilio.type.PhoneNumber("Number"),
                            new com.twilio.type.PhoneNumber("Number"),
                            "Bonjour,\n\n" +
                                    "Voici les informations de récupération de votre compte :\n\n" +
                                    "Nom d'utilisateur : " + user.getUsername() + "\n" +
                                    "Mot de passe : " + user.getPassword() + "\n\n" +
                                    "Si vous n'avez pas demandé cette récupération ou si vous avez des préoccupations, veuillez contacter immédiatement notre équipe d'assistance.\n\n" +
                                    "Merci,\n" +
                                    "[ArtyVenci]")
                    .create();

            System.out.println(message.getSid());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message Envoyer Avec Succes");
            alert.setHeaderText(null);
            alert.setContentText("Vérifier votre téléphone");
            alert.showAndWait();
            // Redirect to login page
            redirectToLogin();
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Vérifier votre numéro de téléphone");
            alert.showAndWait();

        }


    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
