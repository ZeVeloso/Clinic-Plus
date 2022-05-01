package clinic.view;

import clinic.Helpers.UtenteConsultaClinica;
import clinic.business.ClinicFacade;
import clinic.business.Consulta;
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
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;

public class ConsultasController implements Initializable {

    @FXML
    private TableView<UtenteConsultaClinica> tableConsultas;

    @FXML
    private TableColumn idCol;

    @FXML
    private TableColumn nomeCol;

    @FXML
    private TableColumn telemovelCol;

    @FXML
    private TableColumn dataCol;

    @FXML
    private TableColumn motivoCol;

    @FXML
    private TableColumn custoCol;

    @FXML
    private TableColumn nomeClinicaCol;

    @FXML
    private TableColumn estadoCol;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField nameField;

    @FXML
    private TextField telField;

    @FXML
    private TextField nomeClinicField;

    @FXML
    private RadioButton radioButton;

    @FXML
    private VBox vBoxButtons;

    @FXML
    private Button irUtenteButton;

    private ClinicFacade model;
    private DateHelper dateHelper;
    private ObservableList data;
    private FXCalendar calendar;

    private void refreshData(){
        Collection<UtenteConsultaClinica> colec =  this.model.getConsultasUC();
        UtenteClinicaHelper.refreshDataFilter(colec, tableConsultas, data);
    }

    @FXML
    private void irUtenteHandler() throws IOException, ParseException {
        UtenteConsultaClinica utentesSelected;
        utentesSelected = tableConsultas.getSelectionModel().getSelectedItem();
        GoToHelper.gotoSameStageController(irUtenteButton,"perfilUtente.fxml", utentesSelected.getIdUtente(),3);
    }
    @FXML
    private void irConsultaHandler() throws IOException {
        UtenteConsultaClinica consultaSelected;
        consultaSelected = tableConsultas.getSelectionModel().getSelectedItem();
        Consulta consulta = this.model.getConsulta(consultaSelected.getIdConsulta());
        GoToHelper.gotoNewStageController("consultaDetalhada.fxml", 2,consulta ,tableConsultas, data);
    }
    @FXML
    public void efetivarHandler(){
        ObservableList<UtenteConsultaClinica> utentesSelected;
        utentesSelected = tableConsultas.getSelectionModel().getSelectedItems();

        for(UtenteConsultaClinica u: utentesSelected){
            this.model.updateConsultaEstado(u.getIdConsulta());
        }
        this.refreshData();
    }

    @FXML
    public void addHandler() throws IOException {
        GoToHelper.goToNewStage("addConsultaOut.fxml", 400, 183);
        this.refreshData();
    }

    @FXML
    public void removeHandler(){
        ObservableList<UtenteConsultaClinica> utentesSelected;
        utentesSelected = tableConsultas.getSelectionModel().getSelectedItems();

        for(UtenteConsultaClinica u: utentesSelected){
            this.model.removeConsulta(u.getIdConsulta());
        }
        AlertBox.display("Confirmação", "Consulta removida com sucesso");
        this.refreshData();
    }


    @FXML
    public void apagarFieldsHandler(){
        nameField.clear();
        telField.clear();
        nomeClinicField.clear();
        radioButton.setSelected(false);
        calendar.clear();
    }

    @FXML
    public void pesquisarFilterHandler() throws ParseException {
        boolean estado = radioButton.isSelected();
        String estadoText;
        if(estado) estadoText = "Agendado";
        else estadoText = "";
        String nome = nameField.getText();
        String telemovel =  telField.getText();
        String nomeClinica = nomeClinicField.getText();
        Date data = calendar.getValue();
        String novaData="";
        if(nome==null) nome="";
        if(telemovel==null) telemovel="";
        if(nomeClinica==null) nomeClinica="";
        if(data!=null) novaData = dateHelper.formatDateCalendar(data.toString());
        Collection<UtenteConsultaClinica> fitlered = model.getConsultasUCFilter(estadoText,telemovel, nomeClinica, nome, novaData);
        UtenteClinicaHelper.refreshDataFilter(fitlered, tableConsultas, this.data);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.model = new ClinicFacade();
        this.dateHelper = new DateHelper();
        this.data = FXCollections.observableArrayList();

        calendar = new FXCalendar();
        UtenteClinicaHelper.setupFilterBox(vBoxButtons, calendar );
        MenuBarHelper.setupMenuBar(borderPane);
        tableConsultas.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );

        idCol.setCellValueFactory(
                new PropertyValueFactory<UtenteConsultaClinica, String>("idConsulta")
        );
        nomeCol.setCellValueFactory(
                new PropertyValueFactory<UtenteConsultaClinica, String>("nomeUtente")
        );
        telemovelCol.setCellValueFactory(
                new PropertyValueFactory<UtenteConsultaClinica, Integer>("telemovelUtente")
        );
        dataCol.setCellValueFactory(
                new PropertyValueFactory<UtenteConsultaClinica, Integer>("dataConsulta")
        );
        motivoCol.setCellValueFactory(
                new PropertyValueFactory<UtenteConsultaClinica, String>("motivoConsulta")
        );
        custoCol.setCellValueFactory(
                new PropertyValueFactory<UtenteConsultaClinica, String>("custoConsulta")
        );
        nomeClinicaCol.setCellValueFactory(
                new PropertyValueFactory<UtenteConsultaClinica, String>("nomeClinica")
        );
        estadoCol.setCellValueFactory(
                new PropertyValueFactory<UtenteConsultaClinica, String>("estadoConsulta")
        );

        Collection<UtenteConsultaClinica> coll = this.model.getConsultasUC();
        data.addAll(coll);
        tableConsultas.setItems(data);
        tableConsultas.setRowFactory(tv -> {
            TableRow<UtenteConsultaClinica> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    UtenteConsultaClinica clickedRow = row.getItem();
                    try {
                        GoToHelper.gotoSameStageController((Node)event.getSource(),"perfilUtente.fxml", clickedRow.getIdUtente(),3);
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                    }

                }
                if (! row.isEmpty() && event.getButton()== MouseButton.SECONDARY
                        && event.getClickCount() == 2) {

                    UtenteConsultaClinica clickedRow = row.getItem();
                    try {
                        Consulta consulta = this.model.getConsulta(clickedRow.getIdConsulta());
                        GoToHelper.gotoNewStageController("consultaDetalhada.fxml", 2,consulta ,tableConsultas, data);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
            return row;
        });
    }

}
