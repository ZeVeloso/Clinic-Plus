package clinic.view;

import clinic.MainFX;
import clinic.business.*;
import clinic.view.Add.AddConsultaController;
import clinic.view.Box.AlertBox;
import clinic.view.Box.ConfirmBox;
import clinic.view.Helpers.DateHelper;
import clinic.view.Helpers.GoToHelper;
import clinic.view.Helpers.MenuBarHelper;
import clinic.view.calendar.FXCalendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

public class ControllerUtente {

    @FXML
    private Label idField;
    @FXML
    private TextField nomeField;
    @FXML
    private TextField idadeField;
    @FXML
    private TextField telField;
    @FXML
    private TextField profField;
    @FXML
    private TextField moradaField;
    @FXML
    private DatePicker nascField;
    @FXML
    private TextArea fisField;
    @FXML
    private TextArea histPessField;
    @FXML
    private TextArea histFamField;

    @FXML
    private TextField docNameField;

    @FXML
    private ChoiceBox clinicaBox;
    @FXML
    private TableView tableConsulta;
    @FXML
    private TableColumn idCol;

    @FXML
    private TableColumn dataCol;

    @FXML
    private TableColumn custoCol;

    @FXML
    private TableColumn estadoCol;

    @FXML
    private TableColumn motivoCol;

    @FXML
    private Button apagarUtenteButton;
    @FXML
    private Button voltarButton;
    @FXML
    private Button addConsultaButton;

    @FXML
    private TableView tableDocs;

    @FXML
    private TableColumn docCol;

    @FXML
    private BorderPane borderPane;

    @FXML
    private GridPane gridPane;

    @FXML
    private VBox vBoxData;

    @FXML
    private HBox hboxFilter;

    @FXML
    private RadioButton agendadoRadio;

    private FXCalendar calendar;
    private FXCalendar calendarFilter;
    public ClinicFacade model;

    private ObservableList<Consulta> dataNotes;
    private ObservableList<Documento> documentos;
    DateHelper dateHelper;

    private boolean changed;

    private int idCena;

    public void removerDocHandler() {

        ObservableList<Documento> utentesSelected;
        utentesSelected = tableDocs.getSelectionModel().getSelectedItems();

        for(Documento u: utentesSelected){
            this.model.removeDoc(u.getId());
        }
        AlertBox.display("Confirmação", "Documento removido com sucesso");
        this.refreshDataDocs();

    }

    public void addDocHandler(){
        FileChooser fileChooser = new FileChooser();
        String nome = docNameField.getText();
        docNameField.setText("");
        if(!nome.equals("")) {
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("Documentos", "*.png","*.jpg","*.jpeg");
            fileChooser.getExtensionFilters().addAll(extFilterPNG);

            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                String type = file.getAbsolutePath().split("\\.")[1];
                Documento d = new Documento(null, type, nome, Integer.parseInt(idField.getText()));
                this.model.putDoc(d, file.getAbsolutePath());
                this.refreshDataDocs();
            }
        } else AlertBox.display("Erro", "Adicione um nome para o ficheiro no campo em baixo");

    }

    @FXML
    private void clearFilterHandler(){
        calendarFilter.clear();
        agendadoRadio.setSelected(false);
    }

    @FXML
    public void pesqFilterHandler() throws ParseException {
        boolean estado = agendadoRadio.isSelected();
        String estadoText;
        if(estado) estadoText = "Agendado";
        else estadoText = "";
        Date data = calendarFilter.getValue();
        String novaData="";
        if(data!=null) novaData = dateHelper.formatDateCalendar(data.toString());
        Collection<Consulta> fitlered = model.getConsultaFilter(estadoText, novaData, Integer.parseInt(idField.getText()));
        this.refreshData(fitlered);
    }

    public void efetivarConsultaHandler(){
        ObservableList<Consulta> consultasSelected;
        consultasSelected = tableConsulta.getSelectionModel().getSelectedItems();

        for(Consulta c:consultasSelected){
            this.model.updateConsultaEstado(c.getId());
        }
        this.refreshData();
    }

    public void cancelHandler(){
        ObservableList<Consulta> consultasSelected;
        consultasSelected = tableConsulta.getSelectionModel().getSelectedItems();

        for(Consulta c:consultasSelected){
            this.model.removeConsulta(c.getId());
        }

        this.refreshData();
    }

    public void refreshData(Collection<Consulta> colec){
        if(dataNotes!=null)
            dataNotes.clear();
        dataNotes.addAll(colec);

        tableConsulta.setItems(dataNotes);
    }

    public void refreshData(){
        if(dataNotes!=null)
            dataNotes.clear();
        Collection<Consulta> colec =  this.model.getConsultasUtente(Integer.parseInt(idField.getText()));
        dataNotes.addAll(colec);

        tableConsulta.setItems(dataNotes);
    }

    public void refreshDataDocs(){
        if(documentos!=null)
            documentos.clear();
        Collection<Documento> colecDocs = this.model.getDocumentos(Integer.parseInt(idField.getText()));
        documentos.addAll(colecDocs);
        tableDocs.setItems(documentos);
    }

    public void addConsulta() throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("addConsulta.fxml"));
        root = loader.load();

        Stage window = new Stage();
        AddConsultaController c=loader.getController();
        window.setTitle("Adicionar Consulta");
        c.display(Integer.parseInt(idField.getText()));
        window.setScene(new Scene(root, 400, 150));
        window.showAndWait();
        this.refreshData();
}

    public void goToUtentes() throws IOException {
        boolean answer=true;
        if(changed) {
            answer= ConfirmBox.display("Confirmação","Dados nao guardados... Pretende sair?");
        }
        if(answer) {
            if(idCena==1)
                GoToHelper.goToSameStage(voltarButton, "utentes.fxml");
            else if(idCena==2)
                GoToHelper.goToSameStage(voltarButton, "clinicas.fxml");
            else if(idCena==3)
                GoToHelper.goToSameStage(voltarButton, "consultas.fxml");
        }
    }

    public void apagarUtenteHandler() throws IOException {
        this.model.removeUtente(Integer.parseInt(idField.getText()));
        AlertBox.display("Confirmação", "Utente removido com SUCESSO! :)");
        GoToHelper.goToSameStage(apagarUtenteButton, "utentes.fxml");
    }


    public void updateUtente() throws ParseException {
        Integer id = Integer.parseInt(idField.getText());
        String profissao = profField.getText();
        int telemovel = Integer.parseInt(telField.getText());
        String nome = nomeField.getText();
        String fis = fisField.getText();
        String histFam = histFamField.getText();
        String histPess = histPessField.getText();
        String morada = moradaField.getText();
        String nasc = calendar.getValue().toString();
        String novaData = dateHelper.formatDateCalendar(nasc);
        Clinica clinica = (Clinica) clinicaBox.getValue();
        int years = this.dateHelper.AgeCalculator(novaData);
        Utente novo = new Utente(id, novaData, telemovel, years, profissao, histFam, histPess, nome, fis, morada,clinica.getId() );
        this.model.updateUtente(novo);
        idadeField.setText(String.valueOf(years));
        AlertBox.display("Confirmação", "Dados alterados com sucesso! :)");
        changed=false;
    }


    public void displayID(int a, int idCena) throws ParseException {
        changed=false;
        this.idCena=idCena;
        this.dateHelper = new DateHelper();
        this.model = new ClinicFacade();
        Utente u = this.model.getUtente(a);

        calendar = new FXCalendar();
        calendar.setBaseColor(Color.web("#0099ff"));
        FXCalendar.setMargin(calendar, new Insets(5,5,5,5));
        calendar.setMinWidth(50);
        vBoxData.getChildren().add(calendar);

        calendarFilter = new FXCalendar();
        calendarFilter.setBaseColor(Color.web("#0099ff"));
        FXCalendar.setMargin(calendarFilter, new Insets(5,5,5,5));
        calendarFilter.setMinWidth(50);
        hboxFilter.getChildren().add(1,calendarFilter);

        try {
            DateFormat inputFormat = new SimpleDateFormat(
                    "dd-MM-yyyy", Locale.ENGLISH);
            Date data = inputFormat.parse(u.getNascimento());
            calendar.setValue(data);
        } catch (ParseException e){
            AlertBox.display("Error","Data nao especificada");
        }

        idField.setText(String.valueOf(u.getId()));
        nomeField.setText(u.getNome());
        telField.setText(String.valueOf(u.getTelemovel()));
        profField.setText(u.getProfissao());
        idadeField.setText(String.valueOf(u.getIdade()));
        histFamField.setText(u.getHistorico_familiar());
        histPessField.setText(u.getHistorico_pessoal());
        fisField.setText(u.getAtividade_fisica());
        moradaField.setText(u.getMorada());
        Clinica clinica = this.model.getClinicaDeUtente(u.getClinicaID());
        clinicaBox.setValue(clinica);
        Collection<Clinica> coll = this.model.getClinicas();
        ObservableList list = FXCollections.observableArrayList();
        list.addAll(coll);
        clinicaBox.setItems(list);
        dataNotes = FXCollections.observableArrayList();

        documentos = FXCollections.observableArrayList();

        tableConsulta.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );
        idCol.setCellValueFactory(
                new PropertyValueFactory<Consulta, Integer>("id")
        );
        dataCol.setCellValueFactory(
                new PropertyValueFactory<Consulta, String>("data")
        );
        custoCol.setCellValueFactory(
                new PropertyValueFactory<Consulta, Float>("custo")
        );
        estadoCol.setCellValueFactory(
                new PropertyValueFactory<Consulta, String>("estado")
        );
        motivoCol.setCellValueFactory(
                new PropertyValueFactory<Consulta, String>("motivo")
        );
        docCol.setCellValueFactory(
                new PropertyValueFactory<Documento, String>("nome"));

        //dateHelper.convertDatePicker(nascField);

        Collection<Documento> colecDocs = this.model.getDocumentos(u.getId());
        documentos.addAll(colecDocs);
        tableDocs.setItems(documentos);
        Collection<Consulta> colec =  this.model.getConsultasUtente(u.getId());
        dataNotes.addAll(colec);
        tableConsulta.setItems(dataNotes);

        tableConsulta.setRowFactory(tv -> {
            TableRow<Consulta> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    Consulta clickedRow = row.getItem();
                    try {
                        GoToHelper.gotoNewStageController("consultaDetalhada.fxml", 1, clickedRow, tableConsulta, dataNotes);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
            return row;
        });

        tableDocs.setRowFactory(tv -> {
            TableRow<Documento> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    Documento clickedRow = row.getItem();
                    Image img = this.model.getDoc(clickedRow.getId());
                    ImageViewDoc.display(img);

                }
            });
            return row;
        });

        textFieldDetectNewInput(nomeField);
        textFieldDetectNewInput(profField);
        textFieldDetectNewInput(telField);
        textFieldDetectNewInput(idadeField);
        textFieldDetectNewInput(moradaField);
        textAreaDetectNewInput(histFamField);
        textAreaDetectNewInput(histPessField);
        textAreaDetectNewInput(fisField);
        datePickerDetectedNewInput(calendar);
        choiceBoxDetectNewInput(clinicaBox);

        MenuBarHelper.setupMenuBar(borderPane);

    }
    private void textAreaDetectNewInput(TextArea textArea) {
        textArea.textProperty().addListener((observable, oldValue, newValue) -> changed = true);
    }
    private void datePickerDetectedNewInput(FXCalendar datePicker){
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(!oldValue.equals("")) {
                changed = true;
            }
        });
    }

    private void textFieldDetectNewInput(TextField textField){
        textField.textProperty().addListener((observable, oldValue, newValue) -> changed=true);
    }
    private void choiceBoxDetectNewInput(ChoiceBox textField){
        textField.valueProperty().addListener((observable, oldValue, newValue) -> {
            changed=true;
        });
    }


}
