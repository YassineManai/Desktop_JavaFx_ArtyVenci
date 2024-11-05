package controllers.Kenza_Controllers;


import com.google.protobuf.StringValue;
import entities.Auction;
import entities.Auction_participant;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;
import services.ServiceAuction;
import services.ServiceParticipant;


import javax.xml.crypto.Data;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class UnEncherController {
    int id_user ;
    @FXML
    private Label IDAuction;
    ServiceAuction serviceAuction = new ServiceAuction();
    ServiceParticipant serviceParticipant = new ServiceParticipant();

    @FXML
    private Label dateC;

    @FXML
    private Label dateL;

    @FXML
    private HBox hbox;
    @FXML
    private ImageView loveFX;

    @FXML
    private ImageView imageEncher;

    @FXML
    private Label prix_initial;

    @FXML
    private ImageView statu;

    @FXML
    private Label titreEncher;

    private Auction auc;
    String imagePath1;



    private boolean isFavorite = false;

    public void initData(Auction auction) {
        this.auc = auction;
        titreEncher.setText(auction.getNom());
        prix_initial.setText(String.valueOf(auction.getPrix_initial())+"DT");
        dateL.setText(String.valueOf(auction.getDate_lancement()));
        dateC.setText(String.valueOf(auction.getDate_cloture()));
        try{
            String imagepath = loadImageFromDatabase(auc.getId_produit());
            System.out.println(imagepath);
            Image image = new Image(imagepath);

            imageEncher.setImage(image);
        }catch(Exception e ){
            e.printStackTrace();
        }


        IDAuction.setText(String.valueOf(auction.getId()));

        byte[] imageStau = new byte[0];
        int verif = serviceAuction.getSituation(auction);
        if(verif == -1){
            imagePath1 = "C:\\Users\\Hei\\OneDrive\\Documents\\GitHub\\Sprint-Java\\src\\main\\resources\\images\\bientot-disponible.jpg";
        }else if (verif == 0 ){
            imagePath1 = "C:\\Users\\Hei\\OneDrive\\Documents\\GitHub\\Sprint-Java\\src\\main\\resources\\images\\habitent.jpg";
        }else if(verif == 1){
            imagePath1 = "C:\\Users\\Hei\\OneDrive\\Documents\\GitHub\\Sprint-Java\\src\\main\\resources\\images\\vendu.jpg";
        }else{
            System.out.println("there is a probleme with the statu");
        }

        javafx.scene.image.Image image1 = new Image(new File(imagePath1).toURI().toString());
        statu.setImage(image1);
        if(serviceAuction.getSituation(auc) == -1 || serviceAuction.getSituation(auc) == 0){
            //image favori
            byte[] imageFavori = new byte[0];
            int verifFavori = serviceParticipant.getEtatFavori(auc.getId() , id_user);
            if(verifFavori == 0){
                loveFX.setImage(new Image(new File("C:\\Users\\Hei\\OneDrive\\Documents\\GitHub\\Sprint-Java\\src\\main\\resources\\images\\galb.jpg").toURI().toString()));
            }else if (verifFavori == 1){
                isFavorite = true;
                loveFX.setImage(new Image(new File("C:\\Users\\Hei\\OneDrive\\Documents\\GitHub\\Sprint-Java\\src\\main\\resources\\images\\prefere.png").toURI().toString()));
            }
        }


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
        if(verif == 1){
            System.out.println(getIdGagnant(auc));
            if (id_user != getIdGagnant(auc)) {
                try{
                    Parent root = loadEnchere();
                    imageEncher.getScene().setRoot(root);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else {
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
        }else{
            try{
                Parent root = loadEnchere();
                imageEncher.getScene().setRoot(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            loveFX.setImage(new Image(new File("C:\\Users\\Hei\\OneDrive\\Documents\\GitHub\\Sprint-Java\\src\\main\\resources\\images\\galb.jpg").toURI().toString()));
            serviceParticipant.deleteFavori(auc.getId(),id_user);
            isFavorite = false;
        } else {
            loveFX.setImage(new Image(new File("C:\\Users\\Hei\\OneDrive\\Documents\\GitHub\\Sprint-Java\\src\\main\\resources\\images\\prefere.png").toURI().toString()));
            serviceParticipant.addFavori(auc.getId(),id_user);
            isFavorite = true;
        }
    }
    public void setIdArtist(int idArtist) {
        this.id_user=idArtist;
    }

    void PayFlouci(MouseEvent event) throws JSONException {
        double montant = 0;
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
        try {
            Desktop.getDesktop().browse(link);
            System.out.println("t7al");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getIdGagnant(Auction auc){
        return serviceParticipant.getIdGagnat(auc);
    }
}
