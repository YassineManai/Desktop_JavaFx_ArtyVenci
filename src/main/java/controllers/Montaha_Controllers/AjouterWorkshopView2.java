package controllers.Montaha_Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import entities.Workshop;
import services.ServiceEvent;
import services.ServiceWorkshop;

public class AjouterWorkshopView2 {

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
    public void initialize(){
        cbevenement.getItems().setAll(serviceEvent.getEventIdsAndNames());
    }

    @FXML
    void ajouterWorkshop(ActionEvent event) {
        Workshop w=new Workshop();
        w.setDetails(tadescription.getText());
        w.setImage(tfimage.getText());
        w.setTitle(tftitre.getText());
        //w.setId_Workshop(cbevenement.getValue());
        String selectedEvent=cbevenement.getValue();
        String[] parts=selectedEvent.split(" - ");
        w.setId_Event(Integer.parseInt(parts[0]));
        System.out.println(w);
        sw.ajouter(w);

    }

    @FXML
    void uploadImage(ActionEvent event) {

    }

}
