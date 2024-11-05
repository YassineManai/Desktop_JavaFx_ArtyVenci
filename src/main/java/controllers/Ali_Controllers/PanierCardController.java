package controllers.Ali_Controllers;

import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import entities.ProductOrder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONException;
import services.ServiceOrder;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import okhttp3.OkHttpClient;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

import org.json.JSONObject;


import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import java.net.URISyntaxException;
import java.net.URI;
import java.awt.Desktop;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


public class PanierCardController {

    @FXML
    private Label order_date;

    @FXML
    private Label order_id;

    @FXML
    private ImageView order_image;

    @FXML
    private Label order_price;

    @FXML
    private Label order_title;

    @FXML
    private Label prod_id;
    ServiceOrder o = new ServiceOrder();



    private String imagePath;
    public void setData(ProductOrder productOrder){





        imagePath = productOrder.getProd_img();

        if (!imagePath.startsWith("file:/")) {
            imagePath = "file:/" + imagePath;
        }

        System.out.println("card"+imagePath);
        Image image = new Image(imagePath);
        order_image.setImage(image);
        order_title.setText(productOrder.getTitle());
        order_date.setText(productOrder.getOrderDate());
        order_id.setText(String.valueOf(productOrder.getId_Order()));
        prod_id.setText(String.valueOf(productOrder.getId_Product()));
        order_price.setText(String.valueOf(productOrder.getPrice()));
    }
    @FXML
    void cancel_order_btn(ActionEvent event) {
        delete();
    }

    void delete(){
        int id_prod = Integer.parseInt(prod_id.getText());
        int id_order = Integer.parseInt(order_id.getText());
        String title = order_title.getText();
        String date = order_date.getText();
        Double price = Double.valueOf(order_price.getText());
        String image = this.imagePath;
        try {
            o.supprimer(new ProductOrder(id_order,id_prod,title,date,price,image));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void payer_order_btn(ActionEvent event) {
        Double montant = Double.valueOf(order_price.getText());
        String title = order_title.getText();
        Stripe.apiKey = "sk_test_51Oop0OBQCHJCIBnOp9sP9YNzRUVOleVW4d5FgsXox60XUClnwh8ZiMMamUtpz8LgHkIfYzQw8q40ocGEf1fVkj7G00Qk2ILK7A";

        try {
            SessionCreateParams params = SessionCreateParams.builder()
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("https://ruperhat.com/wp-content/uploads/2020/06/Paymentsuccessful21.png")
                    .setCancelUrl("https://hypixel.net/attachments/1690923493412-png.3230490/")
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("usd")
                                                    .setUnitAmount((long) (montant * 100)) // Stripe expects the amount in cents
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName(title)
                                                                    .addImage("https://i.imgur.com/vceNLz2.jpeg")
                                                                    .setDescription("ArtyVenci Votre Gallery Virtuelle")
                                                                    .build()
                                                    )
                                                    .build()
                                    )
                                    .build()
                    )
                    .build();

            Session session = Session.create(params);
            URI checkoutUri = new URI(session.getUrl());
            //loadCheckoutPage(checkoutUri.toString());

            java.awt.Desktop.getDesktop().browse(checkoutUri); // Opens the URL in the default browser
        } catch (StripeException e) {
            System.err.println("Error creating Checkout Session: " + e.getMessage());
            e.printStackTrace();
            // Handle the error, display a message to the user, etc.
        } catch (Exception ex) {
            System.err.println("Error redirecting to Stripe Checkout: " + ex.getMessage());
            ex.printStackTrace();
            // Handle the error, display a message to the user, etc.
        }


    }
    public static PaymentIntent retrievePaymentData(String paymentId) throws StripeException {
        return PaymentIntent.retrieve(paymentId);
    }

    @FXML
    void PayFlouci(ActionEvent event) {
        Double montant = Double.valueOf(order_price.getText());
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
                delete();
                send_SMS();

            } else {
                System.out.println("Error generating payment: " + response.code());
                System.out.println(response.body().string());
            }
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
    private void handleResponse(String responseBody) {
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    void send_SMS (){
        // Initialisation de la bibliothèque Twilio avec les informations de votre compte


        Twilio.init(System.getenv("sid_sms"), System.getenv("key_sms"));

        String recipientNumber = "Number";
        String message = "Bonjour ,\n"
                + "Nous sommes heureux de vous informer.\n "
                + "que votre paiement a été effectué avec succès.\n "
                + "Merci d'avoir choisi nos services ! \n"
                + "Cordialement,\n"
                + "ArtyVenci,";

        Message twilioMessage = Message.creator(
                new PhoneNumber(recipientNumber),
                new PhoneNumber("Number"),message).create();

        System.out.println("SMS envoyé : " + twilioMessage.getSid());
    }
}


