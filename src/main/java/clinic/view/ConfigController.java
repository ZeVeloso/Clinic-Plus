package clinic.view;

import clinic.data.ConfigDAO;
import clinic.data.DAOconfig;
import clinic.view.Box.AlertBox;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ConfigController {

    public static void display() {
        Stage stage = new Stage();

        Button button = new Button("Select Directory");
        button.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("Database File (*.db)","*.db");
            fileChooser.getExtensionFilters().addAll(extFilterJPG);

            File file = fileChooser.showOpenDialog(null);
            String path = file.getAbsolutePath().replace("\\","\\\\");
            DAOconfig.URL=path;
            ConfigDAO.put(path);

        });


        VBox vBox = new VBox(button);
        //HBox hBox = new HBox(button1, button2);
        Scene scene = new Scene(vBox, 200, 100);

        stage.setScene(scene);
        stage.showAndWait();
        AlertBox.display("Clinic +", "Reiniciar aplicação");
        Platform.exit();
        System.exit(0);
    }
}
