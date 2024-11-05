package controllers.Kenza_Controllers;


import controllers.Yassine_Controllers.LoginController;
import entities.User;
import javafx.concurrent.Task;

import com.google.protobuf.StringValue;
import entities.Auction;
import entities.Auction_participant;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import services.ServiceAuction;
import services.ServiceParticipant;
import services.ServiceUser;

import javax.xml.crypto.Data;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class EncherDetailController{
    ServiceUser serviceUser = new ServiceUser() ;
    private ScheduledExecutorService scheduler;
    int id_user ;
    User CurrentUser = LoginController.userlogged;
    @FXML
    private Button ParticiperButton;
    ServiceParticipant serviceParticipant = new ServiceParticipant();

    @FXML
    private Label nbreParticipant;

    @FXML
    private Label dateCloture;

    @FXML
    private Label dateLancement;

    @FXML
    private Label nomEnchere;


    @FXML
    private Label prixInitial;

    @FXML
    private ImageView imageProduit;

    @FXML
    private Label tfNomProduit;

    @FXML
    private TextField montant;
    @FXML
    private Button effectuerButton;
    @FXML
    private HBox hboxFX;
    ServiceAuction serviceAuction = new ServiceAuction();
    @FXML
    private Label remainingTimeLabel;
    @FXML
    private ImageView photoTimer;
    @FXML
    private VBox ratingBox;
    @FXML
    private Label star;



    List<Auction_participant> recentlyAdded;
    private Auction auc ;

    public void SetDataAgain() {
        photoTimer.setVisible(false);
        if(serviceAuction.getSituation(auc) == -1){
            ParticiperButton.setVisible(false);
            photoTimer.setVisible(false);
            showSuccessAlert("Enchère pas encore commencé !!");
        }else if(serviceAuction.getSituation(auc) == 1){
            ParticiperButton.setVisible(false);
            showSuccessAlert("Enchère Terminé" +
                    "!!");
            photoTimer.setVisible(false);
        }else{
            startTimer();
            updateRemainingTime();
            photoTimer.setVisible(true);

        }
        montant.setVisible(false);

        try{
            System.out.println(auc.getId());
        }catch (NullPointerException j){
            j.printStackTrace();
        }

        String nomProduit = null;
        try {
            nomProduit = serviceAuction.getNomProduit(auc.getId());
        } catch (SQLException e) {
            System.out.println("lalalalalalal");
        }

        tfNomProduit.setText(nomProduit);
        nomEnchere.setText(auc.getNom());
        prixInitial.setText("Commencez A Partir De : "+String.valueOf(auc.getPrix_initial()));
        dateLancement.setText(String.valueOf(auc.getDate_lancement()));
        dateCloture.setText(String.valueOf(auc.getDate_cloture()));
        try{
            String imagepath = loadImageFromDatabase(auc.getId_produit());
            System.out.println(imagepath);
            Image image = new Image(imagepath);

            imageProduit.setImage(image);
        }catch(Exception e ){
            e.printStackTrace();
        }

        nbreParticipant.setText(String.valueOf(countPartcipant(auc.getId())));
    }
    public void initData(Auction auction) {
        if (auction == null) {
            throw new IllegalArgumentException("Auction cannot be null.");
        }
        this.auc = auction;
        System.out.println("Controle From Detail Controller  :");
        System.out.println(auction);
    }

    public void initialize() {
        double averageRating = calculateAverageRatingPerAuction(auc.getId());
        long roundedRating = Math.round(averageRating);
        star.setText(String.valueOf(roundedRating));
        SetDataAgain();
        recentlyAdded =new ArrayList<>(recentlyAddedParticipant());
        System.out.println("the number of Participant "+recentlyAdded.size());
        System.out.println(recentlyAdded);
        try {
            VBox mainVBox = new VBox();
            hboxFX.getChildren().add(mainVBox);
            HBox currentHBox = new HBox();
            for (int i = 0 ; i<recentlyAdded.size();i++){
                mainVBox.getChildren().add(currentHBox);
                currentHBox = new HBox();
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Interfaces/AuctionPages/Participant.fxml"));
                HBox participantBox = fxmlLoader.load();
                ParticipantController participantController = fxmlLoader.getController();
                try{
                    participantController.initData(recentlyAdded.get(i));
                }catch (NullPointerException j){
                    j.printStackTrace();
                }
                currentHBox.getChildren().add(participantBox);
            }
            mainVBox.getChildren().add(currentHBox);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
    // Method to update the star rating based on the current average rating
    private void updateStarRating() {
        double averageRating = calculateAverageRatingPerAuction(auc.getId());
        long roundedRating = Math.round(averageRating);
        star.setText(String.valueOf(roundedRating));
    }


    private void startTimer() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::updateRemainingTime, 0, 1, TimeUnit.SECONDS);
    }

    private void updateRemainingTime() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                LocalDateTime endDateTime = auc.getDate_cloture().atStartOfDay();
                Timestamp endTimeStamp = Timestamp.valueOf(endDateTime);
                long currentTime = System.currentTimeMillis();
                long endTime = endTimeStamp.getTime();

                long remainingTimeMillis = endTime - currentTime;

                if (remainingTimeMillis > 0) {
                    long remainingSeconds = remainingTimeMillis / 1000;
                    long days = remainingSeconds / (24 * 3600);
                    long hours = (remainingSeconds % (24 * 3600)) / 3600;
                    long minutes = ((remainingSeconds % (24 * 3600)) % 3600) / 60;
                    long seconds = ((remainingSeconds % (24 * 3600)) % 3600) % 60;

                    String remainingTime = String.format("%d days, %02d:%02d:%02d", days, hours, minutes, seconds);

                    // Update the JavaFX UI on the JavaFX Application Thread
                    Platform.runLater(() -> remainingTimeLabel.setText("Remaining Time: " + remainingTime));
                } else {
                    // Auction has ended, display a message or take appropriate action
                    Platform.runLater(() -> remainingTimeLabel.setText("Auction has ended"));
                    scheduler.shutdown(); // Optionally, you can stop the timer if needed
                }
                return null;
            }
        };

        task.setOnFailed(event -> {
            task.getException().printStackTrace();
            // Handle failure gracefully...
        });

        Executors.newSingleThreadExecutor().execute(task);
    }


    @FXML
    void effectuerArgent(ActionEvent event) {
        Auction_participant auctionParticipant = new Auction_participant();
        auctionParticipant.setId_participant(id_user);
        auctionParticipant.setPrix(Integer.parseInt(montant.getText()));
        auctionParticipant.setId_auction(auc.getId());
        System.out.println("heeedha hoswa l participant "+auctionParticipant);
        System.out.println("****id auction : +"+ auctionParticipant.getId_auction());

        try {
            if (serviceParticipant.search(id_user, auc.getId())) {
                // User exists, modify
                if (isMontantValide(auctionParticipant)) {
                    serviceParticipant.modifierByAuction(auctionParticipant, auc.getId());
                    System.out.println("hedhaa l auction li yra fih  : "+serviceAuction.getById(auctionParticipant.getId_auction()) );
                    serviceAuction.modifierPrixFinal(serviceAuction.getById(auctionParticipant.getId_auction()));
                    showSuccessAlert("L'enchère a été modifiée avec succès !");
                    refreshData();
                } else {
                    showErrorAlert("Le montant doit être supérieur au prix initial et au dernier montant proposé.");
                }
            } else {
                if (isMontantValide(auctionParticipant)) {
                    serviceParticipant.ajouter(auctionParticipant);
                    serviceAuction.modifierPrixFinal(serviceAuction.getById(auctionParticipant.getId_auction()));

                    showSuccessAlert("L'enchère a été ajoutée avec succès !");
                    refreshData();
                } else {
                    showErrorAlert("Le montant doit être supérieur au prix initial et au dernier montant proposé.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

     getVBox(event);
        updateStarRating();
    }

    private VBox createRatingInterface() {
        VBox ratingBox = new VBox(10);
        ratingBox.setAlignment(Pos.CENTER);

        Label ratingLabel = new Label("Rating: 0");
        Slider ratingSlider = new Slider(0, 5, 0);
        ratingSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            ratingLabel.setText("Rating: " + newValue.intValue());
        });

        Button submitButton = new Button("Submit Rating");
        submitButton.setOnAction(event -> {
            int rating = (int) ratingSlider.getValue();
            ratingLabel.setText("Rating: 0");
            serviceParticipant.submitRating(rating, id_user, auc.getId());

            // Close the window containing the rating interface
            Stage stage = (Stage) ratingBox.getScene().getWindow();
            stage.close();
        });



        ratingBox.getChildren().addAll(ratingLabel, ratingSlider, submitButton);

        return ratingBox;
    }


    private void getVBox(ActionEvent event) {
        VBox ratingBox = createRatingInterface();
        Stage ratingStage = new Stage();
        ratingStage.setTitle("Rating Window");

        Scene ratingScene = new Scene(ratingBox, 250, 200);

        ratingStage.setScene(ratingScene);

        ratingStage.show();
    }


    private void showSuccessAlert(String message) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText(message);
            alert.showAndWait();
        }
    private void showErrorAlert(String message) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(message);
            alert.showAndWait();
        }



    private Parent loadEnchere() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/AuctionPages/EncherDetail.fxml"));
        EncherDetailController controller = new EncherDetailController();
        loader.setController(controller);
        controller.initData(auc);
        Parent root = loader.load();
        return root;
    }
    private String loadImageFromDatabase(int id_produit) {
        String imageData = "";
        imageData = serviceAuction.loadImageFromDatabase(id_produit);
        if (!imageData.startsWith("file:/")) {
            imageData = "file:/" + imageData;
        }
        return imageData;
    }

    //hedhy traja3 nombre de participant
    private int countPartcipant(int id_auction){
        int nbre= 0;
        try {
            nbre = serviceParticipant.countParticipant(id_auction);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nbre;

    }

    @FXML
    public void participerClicked(ActionEvent actionEvent) throws IOException {
        if (id_user == 0){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/Login_User.fxml"));
            Parent loginSuccessRoot = loader.load();
            Scene scene = dateCloture.getScene();
            scene.setRoot(loginSuccessRoot);
        }
        montant.setVisible(true);
        effectuerButton.setVisible(true);
    }



    private boolean isMontantValide(Auction_participant participant) {
        try {
            Auction auction = serviceAuction.getById(participant.getId_auction());
            int prixInitial = auction.getPrix_initial();
            int dernierPrixParticipant = (int) serviceParticipant.getDernierPrix(participant.getId_auction());

            return (participant.getPrix() > prixInitial) && (participant.getPrix() > dernierPrixParticipant);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void retouner(MouseEvent event) {
        String artist = "Artist";
        String member = "Member";
        ServiceUser serviceUser = new ServiceUser() ;
        User user = serviceUser.GetUserById(id_user);
        if (user !=null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                String resourcePath = "/Interfaces/AuctionPages/Enchers.fxml";
                String role = user.getRole();


                if (role.equals(artist)) {
                    resourcePath = "/Interfaces/AuctionPages/artistEnchers.fxml";
                } else if (role.equals(member)) {
                    resourcePath = "/Interfaces/AuctionPages/Enchers.fxml";
                }
                loader.setLocation(getClass().getResource(resourcePath));
                Parent loginSuccessRoot = loader.load();
                Scene scene = prixInitial.getScene();
                scene.setRoot(loginSuccessRoot);

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        else if (CurrentUser == null){
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Interfaces/AuctionPages/Enchers.fxml"));
                Parent loginSuccessRoot = loader.load();
                Scene scene = prixInitial.getScene();
                scene.setRoot(loginSuccessRoot);

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        else {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Interfaces/AuctionPages/EncherDetail.fxml"));
                Parent loginSuccessRoot = loader.load();
                Scene scene = prixInitial.getScene();
                scene.setRoot(loginSuccessRoot);

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    private List<Auction_participant> recentlyAddedParticipant(){
        try {
            return serviceParticipant.list_by_auction(auc.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void refreshData() {
        star.setText("");
        updateStarRating();
        hboxFX.getChildren().clear();
        initialize();
    }
    public void setIDUser(int idUser) {
        this.id_user=idUser;
    }

    public double calculateAverageRatingPerAuction(int auctionId) {
        try {
            List<Integer> ratings = serviceParticipant.getRatingsForAuction(auctionId);

            if (!ratings.isEmpty()) {
                double sum = 0;
                for (int rating : ratings) {
                    sum += rating;
                }
                return sum / ratings.size();
            } else {
                return 0.0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0.0;
        }
    }
}
