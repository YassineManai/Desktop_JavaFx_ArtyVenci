package controllers.Kenza_Controllers;


import entities.Auction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.json.JSONException;
import org.json.JSONObject;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import okhttp3.OkHttpClient;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import java.net.URISyntaxException;
import java.net.URI;
import java.awt.Desktop;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import services.ServiceAuction;
import services.ServiceParticipant;

import java.awt.event.ActionEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


public class EnchereContainerController {

    int id_user;
    ServiceAuction serviceAuction = new ServiceAuction();
    ServiceParticipant serviceParticipant = new ServiceParticipant();
    @FXML
    private ImageView tfFavori;
    @FXML
    private Label prixInitialFx;
    @FXML
    private VBox vbox;
    @FXML
    private ImageView imageE;

    @FXML
    private Label titre;

    Auction auc;
    @FXML
    private ImageView tfStatu;
    String imagePath;

    private boolean isFavorite = false;

    public void initData(Auction auction) {
        this.auc=auction;
        titre.setText(auction.getNom());
        prixInitialFx.setText("Estimée a partir de "+String.valueOf(auction.getPrix_initial())+"DT");
        //image produit
        try {

            String imagepath = loadImageFromDatabase(auc.getId_produit());
            System.out.println(imagepath);
            Image image = new Image(imagepath);
            imageE.setImage(image);
        }catch  (Exception e ){
            e.printStackTrace();
        }


        //image favori
        byte[] imageFavori = new byte[0];
        int verifFavori = serviceParticipant.getEtatFavori(auc.getId() , id_user);
        if(serviceAuction.getSituation(auc) == -1 || serviceAuction.getSituation(auc) == 0){
            if(verifFavori == 0){
                tfFavori.setImage(new Image(new File("C:\\Users\\Hei\\OneDrive\\Documents\\GitHub\\Sprint-Java\\src\\main\\resources\\images\\galb.jpg").toURI().toString()));
            }else if (verifFavori == 1){
                isFavorite = true;
                tfFavori.setImage(new Image(new File("C:\\Users\\Hei\\OneDrive\\Documents\\GitHub\\Sprint-Java\\src\\main\\resources\\images\\prefere.png").toURI().toString()));
            }
        }

        //image Etat
        byte[] imageStau = new byte[0];
        int verif = serviceAuction.getSituation(auction);
        if(verif == -1){
            imagePath = "C:\\Users\\Hei\\OneDrive\\Documents\\GitHub\\Sprint-Java\\src\\main\\resources\\images\\bientot-disponible.jpg";
        }else if (verif == 0 ){
            imagePath = "C:\\Users\\Hei\\OneDrive\\Documents\\GitHub\\Sprint-Java\\src\\main\\resources\\images\\habitent.jpg";
        }else if(verif == 1){
            imagePath = "C:\\Users\\Hei\\OneDrive\\Documents\\GitHub\\Sprint-Java\\src\\main\\resources\\images\\vendu.jpg";
        }else{
            System.out.println("there is a probleme with the statu");
        }

        javafx.scene.image.Image image1 = new Image(new File(imagePath).toURI().toString());
        tfStatu.setImage(image1);

        vbox.setStyle("-fx-background-color: #E5F1FA;"+
                " -fx-border-radius: 15; ");


    }


    //hedhy traja3 taswiret l produit mtaa auction
        private String loadImageFromDatabase(int id_produit) {
            String imageData = null;
            imageData = serviceAuction.loadImageFromDatabase(id_produit);
            if (!imageData.startsWith("file:/")) {
                imageData = "file:/" + imageData;
            }
        return imageData;
    }

    @FXML
    void AfficherEncher(MouseEvent event) throws IOException {
        int verif = serviceAuction.getSituation(auc);

        if (verif == 1) {
            if (id_user != getIdGagnant(auc)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Cet enchère est Bien Terminé !");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Bravo VOUS AVEZ GAGNE !");
                alert.setContentText("Bravo VOUS AVEZ GAGNE !!");

                ButtonType detailsButton = new ButtonType("Commencer le payement.", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().add(detailsButton);

                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType == detailsButton) {
                        try {
                            PayFlouci(event);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } else {
            try {
                Parent root = loadEnchere();
                imageE.getScene().setRoot(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void PayFlouci(MouseEvent event) throws JSONException {
        Double montant = null;
        try {
            montant = serviceAuction.getPrixFinal(auc);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("app_token", "aebe6188-4587-4ff0-9b1c-a26c7898ee73");
        jsonBody.put("app_secret", "83c2f9f3-0fdc-4dec-9bf2-e642c1cce53d");
        jsonBody.put("accept_card", true); // Use boolean value, not string
        jsonBody.put("amount", (long) (montant * 1000));
        jsonBody.put("success_link", "https://ruperhat.com/wp-content/uploads/2020/06/Paymentsuccessful21.png");
        jsonBody.put("fail_link", "https://hypixel.net/attachments/1690923493412-png.3230490/");
        jsonBody.put("session_timeout_secs", 1200);
        jsonBody.put("developer_tracking_id", "df9dd458-65ed-4d8b-b354-302077358ef2");


        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, jsonBody.toString());
        Request request = new Request.Builder()
                .url("https://developers.flouci.com/api/generate_payment")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                System.out.println("Payment generated successfully");
                String responseBody = response.body().string();
                handleResponse(responseBody);
                delete(auc.getId());

            } else {
                System.out.println("Error generating payment: " + response.code());
                System.out.println(response.body().string());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void delete(int id_auction){
        try {
            serviceAuction.supprimer_auction(id_auction);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void handleResponse(String responseBody) throws JSONException {
        JSONObject jsonResponse = new JSONObject(responseBody);
        JSONObject result = jsonResponse.getJSONObject("result");
        String linkString = result.getString("link");
        URI link = null;
        try {
            link = new URI(linkString);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        //open browser
        try {
            Desktop.getDesktop().browse(link);
            System.out.println("t7al");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private Parent loadEnchere() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/AuctionPages/EncherDetail.fxml"));
        EncherDetailController controller = new EncherDetailController();
        loader.setController(controller);
        controller.setIDUser(id_user);
        controller.initData(auc);
        Parent root = loader.load();
        return root;
    }

    @FXML
    void changerFavori(MouseEvent event) {
        if (isFavorite) {
            // L'utilisateur a déjà marqué l'enchère comme favorite, changez à l'image non favorite
            tfFavori.setImage(new Image(new File("C:\\Users\\Hei\\OneDrive\\Documents\\GitHub\\Sprint-Java\\src\\main\\resources\\images\\galb.jpg").toURI().toString()));
            serviceParticipant.deleteFavori(auc.getId(),id_user);
            isFavorite = false;
        } else {
            // L'utilisateur n'a pas encore marqué l'enchère comme favorite, changez à l'image favorite
            tfFavori.setImage(new Image(new File("C:\\Users\\Hei\\OneDrive\\Documents\\GitHub\\Sprint-Java\\src\\main\\resources\\images\\prefere.png").toURI().toString()));
            serviceParticipant.addFavori(auc.getId(),id_user);
            isFavorite = true;
        }
    }

    public void setIdArtist(int idArtist) {
        this.id_user=idArtist;
    }

    public int getIdGagnant(Auction auc){
        return serviceParticipant.getIdGagnat(auc);
    }
}
