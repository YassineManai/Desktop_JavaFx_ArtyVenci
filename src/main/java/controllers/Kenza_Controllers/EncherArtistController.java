package controllers.Kenza_Controllers;


import entities.Auction;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import services.ServiceAuction;
import services.ServiceParticipant;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;

public class EncherArtistController {
    ServiceAuction serviceAuction = new ServiceAuction();

    ServiceParticipant serviceParticipant = new ServiceParticipant();
    @FXML
    private HBox box_encherArtist;

    @FXML
    private Label date;

    @FXML
    private ImageView image_enchere;

    @FXML
    private Label tf_nmbreFavori;
    @FXML
    private Label prix_initial;


    @FXML
    private Label titreEnchere;

    Auction auc ;

    public void initData(Auction auction) {
        this.auc=auction;
        titreEnchere.setText(auction.getNom());
        prix_initial.setText("Estimé à Partir de : "+String.valueOf(auction.getPrix_initial()) + "DT");
        date.setText(String.valueOf(auction.getDate_lancement())+" - "+ String.valueOf(auction.getDate_cloture()));
        try{
            String imagepath = loadImageFromDatabase(auc.getId_produit());
            System.out.println(imagepath);
            Image image = new Image(imagepath);
            image_enchere.setImage(image);
        }catch  (Exception e ){
            e.printStackTrace();
        }

        box_encherArtist.setStyle(" -fx-border-radius: 15; ");

        int nbreFavori = serviceParticipant.countFavori(auc.getId());
        tf_nmbreFavori.setText(String.valueOf(nbreFavori));
    }


    //hedhy traja3 taswiret l produit mtaa auction
    private String loadImageFromDatabase(int id_produit) {
        String imageData = "";

        imageData = serviceAuction.loadImageFromDatabase(id_produit);
        if (!imageData.startsWith("file:/")) {
            imageData = "file:/" + imageData;
        }
        return imageData;
    }

    @FXML
    void Supprimer(MouseEvent event) {
        if (auc != null) {
            try {
                serviceAuction.supprimer_auction(auc.getId());

                Scene scene = box_encherArtist.getScene();
                if (scene != null) {
                    Event.fireEvent(scene.getRoot(), new AuctionEvent(AuctionEvent.AUCTION_DELETED));
                }
                Platform.runLater(() -> {
                    ArtistEnchersController artistEnchersController = getArtistEnchersController(scene);
                    if (artistEnchersController != null) {
                        artistEnchersController.refreshData();
                    }
                });

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private ArtistEnchersController getArtistEnchersController(Scene scene) {
        if (scene != null && scene.getRoot() instanceof Parent) {
            return ArtistEnchersController.getInstance();
        }
        return null;
    }

    @FXML
    void modifier(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/AuctionPages/Modifier.fxml"));
            Parent editUserRoot = loader.load();

            ModifierController modifier = loader.getController();

            modifier.initData(auc);

            Scene scene = date.getScene();

            scene.setRoot(editUserRoot);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
