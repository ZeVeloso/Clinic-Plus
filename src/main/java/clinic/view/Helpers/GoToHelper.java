package clinic.view.Helpers;

import clinic.MainFX;
import clinic.business.Consulta;
import clinic.view.ConsultaDetalhadaController;
import clinic.view.ControllerUtente;
import clinic.view.FirstPreloader;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;

public class GoToHelper {


    public static void goToSameStage(Node e, String fxml) throws IOException {
        System.setProperty("javafx.preloader", FirstPreloader.class.getCanonicalName());
        Stage stage = (Stage) e.getScene().getWindow();
        Scene sceneInfo =  e.getScene();
        FXMLLoader loader = new FXMLLoader(MainFX.class.getResource(fxml));
        Parent root = loader.load();
        stage.setTitle("Clinic +");
        Scene scene = new Scene(root, sceneInfo.getWidth(), sceneInfo.getHeight());
        scene.getStylesheets().add(MainFX.class.getResource("calendar_styles.css").toExternalForm());
        sceneInfo.getStylesheets().add(MainFX.class.getResource("calendar_styles.css").toExternalForm());
        stage.setScene(scene);
    }

    public static void goToSameStage(MenuItem e, String fxml) throws IOException {
        System.setProperty("javafx.preloader", FirstPreloader.class.getCanonicalName());
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
        stage.setMinHeight(heigth);
        stage.setMinWidth(width);
        Scene scene = new Scene(root, width, heigth);
        scene.getStylesheets().add(MainFX.class.getResource("calendar_styles.css").toExternalForm());
        stage.setScene(scene);
        stage.showAndWait();
    }

    public static void gotoNewStageController(String fxml, int type, Consulta clickedRow, TableView tableConsulta, ObservableList dataNotes) throws IOException {
        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader(MainFX.class.getResource(fxml));
        Parent root = loader.load();
        ConsultaDetalhadaController c=loader.getController();
        newStage.setOnCloseRequest(t -> {
            t.consume();
            boolean answer = c.exit();
            if(answer) newStage.close();
        });
        newStage.setMinHeight(600);
        newStage.setMinWidth(800);
        c.displayConsulta(clickedRow, tableConsulta, dataNotes,type);
        newStage.setScene(new Scene(root, 800, 600));
        newStage.showAndWait();
    }

    public static void gotoSameStageController(Node node,String fxml, int idUtente, int id) throws IOException, ParseException {
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = node.getScene();
        FXMLLoader loader = new FXMLLoader(MainFX.class.getResource(fxml));
        Parent root = loader.load();
        ControllerUtente c=loader.getController();
        stage.setTitle("Clinic +");
        c.displayID(idUtente, id);
        Scene sceneNew = new Scene(root, scene.getWidth(), scene.getHeight());
        sceneNew.getStylesheets().add(MainFX.class.getResource("calendar_styles.css").toExternalForm());
        stage.setScene(sceneNew);
    }

}
