package controllers.Montaha_Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import test.MainFX;
import entities.Workshop;
import services.EventChangeListener;
import services.ServiceEvent;
import services.ServiceWorkshop;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AjouterWorkshopView implements EventChangeListener<Workshop> {

    @FXML
    private TextField tftitre;

    @FXML
    private TextArea tadescription;

    @FXML
    private TextField tfimage;

    @FXML
    private ComboBox<String> cbevenement;
    ServiceEvent serviceEvent=new ServiceEvent();
    ServiceWorkshop sw=new ServiceWorkshop();
    @FXML
    private AnchorPane anchore;
    @FXML
    private GridPane grid;

    int idModifier=0;
    private int idEvent;
    public void initEventId(int idEvent){
        this.idEvent=idEvent;
        refresh();
    }
    @FXML
    public void initialize(){
        cbevenement.getItems().setAll(serviceEvent.getEventIdsAndNames());
      /*  cbevenement.setDisable(true);*/

        refresh();
    }
    public void refresh(){
        grid.getChildren().clear();
        List<Workshop> workshops=sw.afficherParIdEvent(idEvent);
        int column=0;
        int row=1;
        for(int i=0;i<workshops.size();i++){

            FXMLLoader card = new FXMLLoader(MainFX.class.getResource("/Interfaces/EventPages/afficher-one-workshop-view.fxml"));
            try {
                AnchorPane anchorPane=card.load();
                AfficherOneWorkshopView item=card.getController();
                item.remplireData(workshops.get(i));
                item.setEventChangeListener(this);
                if(column==1){
                    column=0;
                    row++;
                }
                grid.add(anchorPane,column++,row);

                GridPane.setMargin(anchorPane,new Insets(10));
            }catch (IOException e){
                System.out.println("Erreur:"+e.getMessage());
            }



        }
    }

    @FXML
    void ajouterWorkshop(ActionEvent event) {
        Workshop w=new Workshop();
        w.setDetails(tadescription.getText());
        w.setImage(tfimage.getText());
        w.setTitle(tftitre.getText());
     //   w.setId_Workshop(Integer.parseInt(cbevenement.getValue()));
        String selectedEvent=cbevenement.getValue();
        String[] parts=selectedEvent.split(" - ");
        w.setId_Event(Integer.parseInt(parts[0]));
        System.out.println(w);
        w.setId_Event(idEvent);
        sw.ajouter(w);
        refresh();

    }
/* @FXML
 void ajouterWorkshop(ActionEvent event) {
     Workshop w=new Workshop();
     w.setDetails(tadescription.getText());
     w.setImage(tfimage.getText());
     w.setTitle(tftitre.getText());
     w.setId_Workshop(cbevenement.getValue());
     String selectedEvent=cbevenement.getValue();
     String[] parts=selectedEvent.split(" - ");
     w.setId_Event(Integer.parseInt(parts[0]));
     System.out.println(w);
     sw.ajouter(w);

 }*/
    @FXML
    void modifierWorkshop(ActionEvent event) {
        if(idModifier!=0){
            Workshop w=new Workshop();
            w.setId_Workshop(idModifier);
            w.setDetails(tadescription.getText());
            w.setImage(tfimage.getText());
            w.setTitle(tftitre.getText());
            w.setId_Workshop(Integer.parseInt(cbevenement.getValue()));
          /*  String selectedEvent=cbevenement.getValue();
            String[] parts=selectedEvent.split(" - ");
            w.setId_Event(Integer.parseInt(parts[0]));*/

            sw.modifier(w);
            refresh();
            cbevenement.setDisable(true);
        }
    }
    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser=new FileChooser();
        File file=fileChooser.showOpenDialog(tfimage.getScene().getWindow());
        if(file!=null){
            tfimage.setText(file.getName());
        }
    }

    @Override
    public void onSupprimerClicked() {
        refresh();
    }

    @Override
    public void onModifierClicked(Workshop workshop) {
        cbevenement.setDisable(false);
        idModifier=workshop.getId_Workshop();
        tfimage.setText(workshop.getImage());
        tadescription.setText(workshop.getDetails());
        tftitre.setText(workshop.getTitle());


    }
    @FXML
    void gotoEvent(ActionEvent event) {
        Stage stage=(Stage) tftitre.getScene().getWindow();
        stage.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/Interfaces/EventPages/ajouter-event-view.fxml"));


            Parent root = fxmlLoader.load();

            Stage newStage=new Stage();
            newStage.setTitle("events");
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
