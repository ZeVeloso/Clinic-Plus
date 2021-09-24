package clinic.view;


import clinic.Helpers.UtenteConsultaClinica;
import clinic.business.ClinicFacade;
import clinic.business.Clinica;
import clinic.business.Utente;
import clinic.view.Box.AlertBox;
import clinic.view.Helpers.*;
import clinic.view.calendar.FXCalendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
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
    private DatePicker nascField;

    @FXML
    private TextField telField;

    @FXML
    private TextField moradaField;

    @FXML
    private Label telErrorLabel;
    @FXML
    private Label dataErrorLabel;
    @FXML
    private ImageView logoImageView;

    @FXML
    private BorderPane borderPane;

    @FXML
    private VBox vBoxButtons;

    private FXCalendar calendar;

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

    public ClinicFacade model;

    private ObservableList<UtenteConsultaClinica> dataNotes;

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

        UtenteClinicaHelper.setupFilterBox(vBoxButtons, calendar);


        clinicaCol.setCellValueFactory(
                new PropertyValueFactory<UtenteConsultaClinica, String>("nomeClinica")
        );


        telErrorLabel.setManaged(false);
        dataErrorLabel.setManaged(false);

        //dateHelper.convertDatePicker(nascField);

        Collection<UtenteConsultaClinica> colec =  model.getUtentesClinica();
        System.out.println(colec.toString());
        dataNotes.addAll(colec);

        tableUtentes.setItems(dataNotes);

    }

    private void textErrorLabelHandler(TextField telemovel, DatePicker dataPicker){
        telemovel.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) {
                if (!telemovel.getText().matches("[0-9]+")) {
                    telemovel.setStyle("-fx-text-box-border: red; -fx-focus-color: red ;");
                    telErrorLabel.setManaged(true);
                    telErrorLabel.setVisible(true);
                    telErrorLabel.setText("Campo telemovel só aceita números!");
                } else {
                    telemovel.setStyle("-fx-border-width: 0px ;");
                    telErrorLabel.setManaged(false);
                    telErrorLabel.setVisible(false);
                }
            }
        });
        dataPicker.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) {
                    if(dataPicker.getValue()!=null) {
                        dataPicker.setStyle("-fx-border-width: 0px ;");
                        dataErrorLabel.setManaged(false);
                        dataErrorLabel.setVisible(false);
                    }else {
                    dataPicker.setStyle("-fx-text-box-border: red; -fx-focus-color: red ;");
                    dataErrorLabel.setManaged(true);
                    dataErrorLabel.setVisible(true);
                    dataErrorLabel.setText("Data do tipo dd-MM-yyyy");
                }
            }
        });
    }

}
