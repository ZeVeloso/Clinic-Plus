package clinic.view;

import clinic.MainFX;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ImageViewDoc {

    public static void display (Image img) {
        Stage stage = new Stage();
        ImageView image = new ImageView(img);
        image.fitWidthProperty().bind(stage.widthProperty());
        image.setPreserveRatio(true);
        image.setSmooth(true);
        BorderPane bp = new BorderPane();
        Button closeButton = new Button("Fechar");

        closeButton.setOnAction(e -> stage.close());

        VBox boxDown = new VBox();
        boxDown.setAlignment(Pos.CENTER);
        boxDown.getChildren().add(closeButton);
        closeButton.setAlignment(Pos.CENTER);
        bp.setBottom(boxDown);
        bp.setCenter(image);
        Scene scene = new Scene(bp, 700, 700);
        stage.setScene(scene);
        stage.show();
    }
}
