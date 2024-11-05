package controllers.Montaha_Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import entities.Workshop;
import services.EventChangeListener;
import services.ServiceWorkshop;

import java.io.File;

public class AfficherOneWorkshopView {

    @FXML
    private ImageView img;

    @FXML
    private Label lnom;

    @FXML
    private Label lplace;
    Workshop w;
    private EventChangeListener eventChangeListener;
    ServiceWorkshop sw=new ServiceWorkshop();

    public void setEventChangeListener(EventChangeListener eventChangeListener) {
        this.eventChangeListener = eventChangeListener;
    }
    public void remplireData(Workshop w){
        this.w=w;
        lnom.setText(w.getTitle());
        lplace.setText(w.getDetails());
        String imageData = null;
        imageData = w.getImage();
        if (!imageData.startsWith("file:/")) {
            imageData = "file:/" + imageData;
        }
        Image image = new Image(imageData);

        img.setImage(image);
    }

    @FXML
    void modifierWorkshop(ActionEvent event) {
        if(eventChangeListener!=null){
            eventChangeListener.onModifierClicked(w);
        }

    }

    @FXML
    void supprimerworkshop(ActionEvent event) {
        sw.supprimer(w);
        if(eventChangeListener!=null){
            eventChangeListener.onSupprimerClicked();
        }
    }

}

