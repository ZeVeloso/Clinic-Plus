package clinic.view;

import clinic.view.Box.AlertBox;
import clinic.view.Box.ConfirmBox;
import clinic.view.Helpers.GoToHelper;
import clinic.view.Helpers.MenuBarHelper;
import javafx.application.Platform;
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
    private Button pesquisarConsultaButton;

    @FXML
    private BorderPane borderPane;


    @FXML
    public void addConsultaHandler() throws IOException {
        GoToHelper.goToNewStage("addConsultaOut.fxml", 400, 183);
    }

    @FXML
    public void configHandler(){
        ConfigController.display();
    }

    @FXML
    public void exitHandler(){
        boolean bool = ConfirmBox.display("Backup","Deseja enviar um backup da base de dados para o email?");
        if (bool) {

            AlertBox.display("Backup", "Loading backup... Nao feches a aplicação\nFechar esta janela para continuar");
            SendFileEmail.main();
        }
        Platform.exit();
        System.exit(0);
    }

    @FXML
    public void pesquisarConsultasHandler() throws IOException {
        GoToHelper.goToSameStage(pesquisarConsultaButton, "consultas.fxml");
    }

    @FXML
    public void adicionarUtenteHandler() throws IOException {
        GoToHelper.goToNewStage("addUtente.fxml",500,  190);
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

        CalendarViewController.initialize(borderPane);
        MenuBarHelper.setupMenuBar(borderPane);

    }
}
