package clinic.view;

import clinic.view.Helpers.GoToHelper;
import clinic.view.Helpers.MenuBarHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuInicialController implements Initializable {

    @FXML
    private Button pesquisarUtentesButton;

    @FXML
    private Button adicionarClinicaButton;

    @FXML
    private Button pesquisarClinicasButton;
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
    private BorderPane borderPane;


    @FXML
    public void configHandler(){
        ConfigController.display();
    }

    @FXML
    public void adicionarClinica() throws IOException {
        GoToHelper.goToNewStage("addClinica.fxml",380,150);
    }
    @FXML
    public void pesquisarUtentes() throws IOException {

        GoToHelper.goToSameStage(pesquisarUtentesButton, "utentes.fxml");
    }

    @FXML
    public void pesquisarClinicasHandler() throws IOException {
        GoToHelper.goToSameStage(pesquisarClinicasButton, "clinicas.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /*hboxImages.setSpacing(40);
        tituloLabel.setAlignment(Pos.BASELINE_CENTER);
        Image clinica = new Image(MainFX.class.getResourceAsStream("clinica.png"));
        Image utente = new Image(MainFX.class.getResourceAsStream("utente.png"));
        Image consulta = new Image(MainFX.class.getResourceAsStream("consulta4.png"));
        Image mais = new Image(MainFX.class.getResourceAsStream("maisIcon.jpg"));
        clinicImageView.setImage(clinica);
        utentesImageView.setImage(utente);
        consultasImageView.setImage(consulta);
        logoImageView.setImage(mais);*/
        CalendarViewController.initialize(borderPane);
        MenuBarHelper.setupMenuBar(borderPane);

    }
}
