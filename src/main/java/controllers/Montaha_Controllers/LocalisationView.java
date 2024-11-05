package controllers.Montaha_Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import utils.OpenStreetMapFX;

public class LocalisationView
{
    @FXML
    private BorderPane pane;

    @FXML
    void down(ActionEvent event) {
        mapView.moveDown();
    }

    @FXML
    void left(ActionEvent event) {
        mapView.moveLeft();
    }

    @FXML
    void right(ActionEvent event) {
        mapView.moveRight();
    }

    @FXML
    void up(ActionEvent event) {
        mapView.moveUp();
    }
    OpenStreetMapFX mapView=new OpenStreetMapFX(AfficherOneEventView.place);

    @FXML
    public void initialize() {
        pane.setCenter(mapView);
    }


}