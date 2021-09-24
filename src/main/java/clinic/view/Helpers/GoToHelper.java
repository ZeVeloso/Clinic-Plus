package clinic.view.Helpers;

import clinic.MainFX;
import clinic.view.FirstPreloader;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class GoToHelper {


    public static void goToSameStage(Node e, String fxml) throws IOException {

        Stage stage = (Stage) e.getScene().getWindow();
        Scene sceneInfo =  e.getScene();
        FXMLLoader loader = new FXMLLoader(MainFX.class.getResource(fxml));
        Parent root = loader.load();
        stage.setTitle("Clinic +");
        Scene scene = new Scene(root, sceneInfo.getWidth(), sceneInfo.getHeight());
        scene.getStylesheets().add(MainFX.class.getResource("calendar_styles.css").toExternalForm());
        stage.setScene(scene);
    }

    public static void goToSameStage(MenuItem e, String fxml) throws IOException {

        Stage stage = (Stage) e.getParentPopup().getOwnerWindow();
        Scene sceneInfo =  e.getParentPopup().getOwnerWindow().getScene();
        FXMLLoader loader = new FXMLLoader(MainFX.class.getResource(fxml));
        Parent root = loader.load();
        stage.setTitle("Clinic +");
        Scene scene = new Scene(root, sceneInfo.getWidth(), sceneInfo.getHeight());
        scene.getStylesheets().add(MainFX.class.getResource("calendar_styles.css").toExternalForm());
        stage.setScene(scene);
    }

    public static void goToNewStage(String fxml, double width, double heigth) throws IOException {
        System.setProperty("javafx.preloader", FirstPreloader.class.getCanonicalName());
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(MainFX.class.getResource(fxml));
        Parent root = loader.load();
        stage.setTitle("Clinic +");
        Scene scene = new Scene(root, width, heigth);
        scene.getStylesheets().add(MainFX.class.getResource("calendar_styles.css").toExternalForm());
        stage.setScene(scene);
        stage.showAndWait();
    }

}
