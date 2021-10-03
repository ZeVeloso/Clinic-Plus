package clinic.view;


import clinic.Helpers.UtenteConsultaClinica;
import clinic.business.ClinicFacade;
import clinic.view.Box.AlertBox;
import clinic.view.Helpers.DateHelper;
import clinic.view.Helpers.GoToHelper;
import clinic.view.Helpers.MenuBarHelper;
import clinic.view.Helpers.UtenteClinicaHelper;
import clinic.view.calendar.FXCalendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.Collection;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private DateHelper dateHelper;

    @FXML
    private TableView<UtenteConsultaClinica> tableUtentes;

    @FXML
    private TableColumn nameCol;

    @FXML
    private TableColumn idadeCol;

    @FXML
    private TableColumn nascCol;

    @FXML
    private TableColumn telCol;

    @FXML
    private TableColumn moradaCol;

    @FXML
    private TableColumn idCol;

    @FXML
    private TableColumn clinicaCol;

    @FXML
    private TextField nameField;


    @FXML
    private TextField telField;

    @FXML
    private TextField moradaField;

    @FXML
    private ImageView logoImageView;

    @FXML
    private BorderPane borderPane;

    @FXML
    private VBox vBoxButtons;

    private FXCalendar calendar;

    public ClinicFacade model;

    private ObservableList<UtenteConsultaClinica> dataNotes;

    @FXML
    private void clearFieldsHandler(){
        nameField.clear();
        telField.clear();
        moradaField.clear();
        calendar.clear();
    }

    @FXML
    private void filterUtentesHandler() throws ParseException {
        Collection<UtenteConsultaClinica> fitlered = UtenteClinicaHelper.filterUtenteCCHandler(model,nameField,telField, moradaField, calendar);
        UtenteClinicaHelper.refreshDataFilter(fitlered, tableUtentes, dataNotes);
    }

    @FXML
    private void addUtente() throws IOException {
        GoToHelper.goToNewStage("addUtente.fxml", 500,  190);
        this.refreshData();
    }

    @FXML
    private void removeUtente() {
        ObservableList<UtenteConsultaClinica> utentesSelected;
        utentesSelected = tableUtentes.getSelectionModel().getSelectedItems();

        for(UtenteConsultaClinica u: utentesSelected){
            this.model.removeUtente(u.getIdUtente());
        }
        AlertBox.display("Confirmação", "Utente removido com sucesso");
        this.refreshData();

    }

    private void refreshData(){
        Collection<UtenteConsultaClinica> colec =  this.model.getUtentesClinica();
        UtenteClinicaHelper.refreshDataFilter(colec, tableUtentes, dataNotes);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.dateHelper = new DateHelper();
        this.model= new ClinicFacade();
        this.calendar = new FXCalendar();
        dataNotes = FXCollections.observableArrayList();
        UtenteClinicaHelper.setupInit(logoImageView, tableUtentes, idCol, nameCol, idadeCol, telCol, nascCol,
                moradaCol,1);
        MenuBarHelper.setupMenuBar(borderPane);

        calendar = new FXCalendar();
        UtenteClinicaHelper.setupFilterBox(vBoxButtons, calendar);


        clinicaCol.setCellValueFactory(
                new PropertyValueFactory<UtenteConsultaClinica, String>("nomeClinica")
        );


        Collection<UtenteConsultaClinica> colec =  model.getUtentesClinica();
        dataNotes.addAll(colec);

        tableUtentes.setItems(dataNotes);

    }

}
