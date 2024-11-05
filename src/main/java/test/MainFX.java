package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try {

           

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Interfaces/UserPages/Home.fxml"));

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login ");
            stage.show();
        }
        catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
