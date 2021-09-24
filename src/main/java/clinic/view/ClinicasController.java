package clinic.view;

import clinic.Helpers.UtenteConsultaClinica;
import clinic.MainFX;
import clinic.business.ClinicFacade;
import clinic.business.Clinica;
import clinic.business.Utente;
import clinic.view.Box.AlertBox;
import clinic.view.Helpers.*;
import clinic.view.calendar.FXCalendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;

public class ClinicasController implements Initializable {

    @FXML
    private TableView<Clinica> tableClinica;
    @FXML
    private TableColumn idClinicaCol;
    @FXML
    private TableColumn nomeClinicaCol;

    @FXML
    private TableView<Utente> tableUtente;
    @FXML
    private TableColumn idUtenteCol;
    @FXML
    private TableColumn nomeUtenteCol;
    @FXML
    private TableColumn idadeUtenteCol;
    @FXML
    private TableColumn telUtenteCol;
    @FXML
    private TableColumn nascUtenteCol;
    @FXML
    private TableColumn moradaUtenteCol;

    @FXML
    private TextField nomeField;

    @FXML
    private TextField telemovelField;

    @FXML
    private DatePicker dataPicker;

    @FXML
    private TextField moradaField;

    @FXML
    private TextField nomeClinicaField;

    @FXML
    private ImageView logoImageView;

    @FXML
    private Button adicionarClinicaButton;

    @FXML
    private BorderPane borderPane;

    @FXML
    private VBox vBoxFilter;

    private ObservableList<Clinica> dataClinicas;
    private ObservableList<Utente> dataUtentes;
    private ClinicFacade model;
    private int idClinica;
    private FXCalendar calendar;
    private DateHelper dateHelper;

    private void refreshData(){

        Collection<Clinica> colec =  model.getClinicas();
        UtenteClinicaHelper.refreshDataFilter(colec, tableClinica, dataClinicas);
    }

    private void refreshDataUtente(){

        Collection<Utente> colec =  model.getUtentesClinica(idClinica);
        UtenteClinicaHelper.refreshDataFilter(colec, tableUtente, dataUtentes);
    }


    @FXML
    private void filterUtenteHandler() throws ParseException {
        Collection<Utente> fitlered = UtenteClinicaHelper.filterUtenteHandler(model,nomeField, telemovelField, moradaField, calendar);
        UtenteClinicaHelper.refreshDataFilter(fitlered, tableUtente, dataUtentes);
    }

    @FXML
    private void filterClinicaHandler() {

        String nome = nomeClinicaField.getText();
        Collection<Clinica> fitlered = this.model.getClinicaFilter(nome);
        UtenteClinicaHelper.refreshDataFilter(fitlered, tableClinica, dataClinicas);
    }

    @FXML
    private void removerClinicaHandler(){
        ObservableList<Clinica> utentesSelected;
        utentesSelected = tableClinica.getSelectionModel().getSelectedItems();

        for(Clinica u: utentesSelected){
            this.model.removeClinica(u.getId());
        }
        AlertBox.display("Confirmação", "Clinica removido com sucesso");
        this.refreshData();
    }

    @FXML
    private void removerUtenteHandler(){
        ObservableList<Utente> utentesSelected;
        utentesSelected = tableUtente.getSelectionModel().getSelectedItems();

        for(Utente u: utentesSelected){
            this.model.removeUtente(u.getId());
        }
        AlertBox.display("Confirmação", "Utente removido com sucesso");
        this.refreshDataUtente();
    }

    @FXML
    private void addUtenteHandler() throws IOException {

        GoToHelper.goToNewStage("addUtente.fxml", 500,  190);
        this.refreshDataUtente();

    }

    @FXML
    private void adicionarClinicaHandler() throws IOException {
        GoToHelper.goToNewStage("addClinica.fxml", 380, 150);
        this.refreshData();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.model= new ClinicFacade();
        this.dateHelper = new DateHelper();
        this.calendar = new FXCalendar();
        dataClinicas = FXCollections.observableArrayList();
        dataUtentes = FXCollections.observableArrayList();

        //UtenteClinicaHelper.setupInit(logoImageView, tableUtente, idUtenteCol, nomeUtenteCol, idadeUtenteCol, telUtenteCol, nascUtenteCol, moradaUtenteCol,2);
        MenuBarHelper.setupMenuBar(borderPane);
        UtenteClinicaHelper.setupFilterBox(vBoxFilter, calendar);

        tableClinica.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );

        idUtenteCol.setCellValueFactory(
                new PropertyValueFactory<Utente, String>("id")
        );
        nomeUtenteCol.setCellValueFactory(
                new PropertyValueFactory<Utente, String>("nome")
        );
        idadeUtenteCol.setCellValueFactory(
                new PropertyValueFactory<Utente, Integer>("idade")
        );
        telUtenteCol.setCellValueFactory(
                new PropertyValueFactory<Utente, Integer>("telemovel")
        );
        nascUtenteCol.setCellValueFactory(
                new PropertyValueFactory<Utente, String>("nascimento")
        );
        moradaUtenteCol.setCellValueFactory(
                new PropertyValueFactory<Utente, String>("morada")
        );
        tableUtente.setRowFactory(tv -> {
            TableRow<Utente> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    Utente clickedRow = row.getItem();
                    Stage stage1 = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    Scene scena = ((Node)event.getSource()).getScene();
                    Parent root;
                    try {
                        FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("perfilUtente.fxml"));
                        root = loader.load();
                        ControllerUtente c=loader.getController();
                        stage1.setTitle("Clinic +");
                        c.displayID(clickedRow.getId(), 2);
                        stage1.setScene(new Scene(root, scena.getWidth(), scena.getHeight()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                if (! row.isEmpty() && event.getButton()== MouseButton.SECONDARY
                        && event.getClickCount() == 2) {

                    Utente clickedRow = row.getItem();


                }
            });
            return row;
        });
        idClinicaCol.setCellValueFactory(
                new PropertyValueFactory<Clinica, Integer>("id")
        );
        nomeClinicaCol.setCellValueFactory(
                new PropertyValueFactory<Clinica, String>("nome")
        );

        Collection<Clinica> clinicasCollec = model.getClinicas();
        dataClinicas.addAll(clinicasCollec);
        tableClinica.setItems(dataClinicas);

        tableClinica.setRowFactory(tv -> {
            TableRow<Clinica> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 1) {
                    Clinica clickedRow = row.getItem();
                    dataUtentes.clear();
                    Collection<Utente> utentesCollec = model.getUtentesClinica(clickedRow.getId());
                    dataUtentes.addAll(utentesCollec);
                    tableUtente.setItems(dataUtentes);
                    idClinica=clickedRow.getId();
                }
            });
            return row;
        });


    }
}
