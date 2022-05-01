package clinic;

import clinic.view.Box.ConfirmBox;
import clinic.view.FirstPreloader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(MainFX.class.getResource("menuInicial.fxml"));
        primaryStage.setTitle("Clinic +");
        primaryStage.setOnCloseRequest(t -> {
            t.consume();
            boolean boolLeft = ConfirmBox.display("Backup","Quer sair? Todos os dados nao guardados serão apagados");
            if (boolLeft) {
                Platform.exit();
                System.exit(0);
            }

        });
        Image mais = new Image(MainFX.class.getResourceAsStream("maisIcon.jpg"));
        primaryStage.getIcons().add(mais);
        primaryStage.setMinWidth(1050);
        primaryStage.setMinHeight(700);
        primaryStage.setScene(new Scene(root, 1050, 700));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        System.setProperty("javafx.preloader", FirstPreloader.class.getCanonicalName());
        launch(args);
    }
}
