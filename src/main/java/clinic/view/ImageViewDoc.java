package clinic.view;

import clinic.MainFX;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ImageViewDoc {

    public static void display () {
        Stage stage = new Stage();
        Image logo = new Image(MainFX.class.getResourceAsStream("tempImg.png"));
        ImageView image = new ImageView(logo);
        image.setPreserveRatio(true);
        BorderPane bp = new BorderPane();

        bp.setCenter(image);
        Scene scene = new Scene(bp, 500, 500);
        stage.setScene(scene);
        stage.show();
    }
}
