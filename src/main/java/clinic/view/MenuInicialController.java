package clinic.view;

import clinic.HelloApplication;
import clinic.MainFX;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuInicialController implements Initializable {

    @FXML
    private Button pesquisarUtentesButton;

    @FXML
    private Button adicionarClinicaButton;
    @FXML
    private ImageView clinicImageView;
    @FXML
    private ImageView utentesImageView;
    @FXML
    private ImageView consultasImageView;
    @FXML
    private ImageView logoImageView;
    @FXML
    private HBox hboxImages;
    @FXML
    private HBox buttonsHBox;
    @FXML
    private Label tituloLabel;
    @FXML
    public void adicionarClinica() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("addClinica.fxml"));
        Parent root = loader.load();
        stage.setTitle("Adicionar Clinica");
        stage.setScene(new Scene(root, 500, 180));
        stage.showAndWait();
    }
    @FXML
    public void pesquisarUtentes() throws IOException {
        Stage stage = (Stage) pesquisarUtentesButton.getScene().getWindow();
        Scene scene = pesquisarUtentesButton.getScene();
        FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("utentes.fxml"));
        Parent root = loader.load();
        //root = FXMLLoader.load(getClass().getResource("perfilUtente.fxml"));
        stage.setTitle("Utentes");
        stage.setScene(new Scene(root, scene.getWidth(), scene.getHeight()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hboxImages.setSpacing(40);
        tituloLabel.setAlignment(Pos.BASELINE_CENTER);
        Image clinica = new Image(MainFX.class.getResourceAsStream("clinica.png"));
        Image utente = new Image(MainFX.class.getResourceAsStream("utente.png"));
        Image consulta = new Image(MainFX.class.getResourceAsStream("consulta4.png"));
        Image mais = new Image(MainFX.class.getResourceAsStream("maisIcon.jpg"));
        clinicImageView.setImage(clinica);
        utentesImageView.setImage(utente);
        consultasImageView.setImage(consulta);
        logoImageView.setImage(mais);
    }
}
