package clinic.view;

import clinic.Helpers.UtenteConsultaClinica;
import clinic.MainFX;
import clinic.business.*;
import clinic.view.Add.AddConsultaController;
import clinic.view.Box.AlertBox;
import clinic.view.Box.ConfirmBox;
import clinic.view.Helpers.DateHelper;
import clinic.view.Helpers.GoToHelper;
import clinic.view.Helpers.MenuBarHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
;

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

    public ClinicFacade model;

    private ObservableList<Consulta> dataNotes;
    private ObservableList<Documento> documentos;
    DateHelper dateHelper;

    private boolean changed;

    private int idCena;

    public void addDocHandler(){
        FileChooser fileChooser = new FileChooser();
        String nome = docNameField.getText();
        docNameField.setText("");
        if(!nome.equals("")) {
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG (*.png)", "*.png");
            fileChooser.getExtensionFilters().addAll(extFilterPNG);

            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                Documento d = new Documento(null, nome, Integer.parseInt(idField.getText()));
                this.model.putDoc(d, file.getAbsolutePath());
                this.refreshDataDocs();
            }
        } else AlertBox.display("Erro", "Adicione um nome para o ficheiro no campo em baixo");

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
        if(answer==true) {
            Parent root;
            FXMLLoader loader;
            if(idCena==1)
                loader = new FXMLLoader(MainFX.class.getResource("utentes.fxml"));
            else loader = new FXMLLoader(MainFX.class.getResource("clinicas.fxml"));
            root = loader.load();
            Stage stage1 = (Stage) voltarButton.getScene().getWindow();
            stage1.setTitle("Utentes");
            Scene scena = voltarButton.getScene();
            stage1.setScene(new Scene(root, scena.getWidth(), scena.getHeight()));
        }

    }

    public void apagarUtenteHandler() throws IOException {
        this.model.removeUtente(Integer.parseInt(idField.getText()));
        AlertBox.display("Confirmação", "Utente removido com SUCESSO! :)");
        GoToHelper.goToSameStage(apagarUtenteButton, "utentes.fxml");

    }


    public void updateUtente(){
        Integer id = Integer.parseInt(idField.getText());
        String nascimento = nascField.getValue().format(dateHelper.getFormatter());
        String profissao = profField.getText();
        int telemovel = Integer.parseInt(telField.getText());
        String nome = nomeField.getText();
        String fis = fisField.getText();
        String histFam = histFamField.getText();
        String histPess = histPessField.getText();
        String morada = moradaField.getText();
        Clinica clinica = (Clinica) clinicaBox.getValue();
        int years = this.dateHelper.AgeCalculator(nascimento);
        Utente novo = new Utente(id, nascimento, telemovel, years, profissao, histFam, histPess, nome, fis, morada,clinica.getId() );
        this.model.updateUtente(novo);
        idadeField.setText(String.valueOf(years));
        AlertBox.display("Confirmação", "Dados alterados com sucesso! :)");
        changed=false;
    }


    public void displayID(int a, int idCena){
        changed=false;
        this.idCena=idCena;
        this.dateHelper = new DateHelper();
        this.model = new ClinicFacade();
        Utente u = this.model.getUtente(a);
        idField.setText(String.valueOf(u.getId()));
        nomeField.setText(u.getNome());
        telField.setText(String.valueOf(u.getTelemovel()));
        profField.setText(u.getProfissao());
        nascField.setValue(LocalDate.parse(u.getNascimento(),dateHelper.getFormatter()));
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

        dateHelper.convertDatePicker(nascField);

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
                    Parent root;
                    Stage newStage = new Stage();
                    try {
                        FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("consultaDetalhada.fxml"));
                        root = loader.load();
                        //root = FXMLLoader.load(getClass().getResource("perfilUtente.fxml"));
                        ConsultaDetalhadaController c=loader.getController();
                        newStage.setTitle("Consulta "+ clickedRow.getId());
                        c.displayConsulta(clickedRow, tableConsulta, dataNotes);
                        newStage.setScene(new Scene(root, 800, 600));
                        newStage.showAndWait();
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
                    Parent root;
                    Stage newStage = new Stage();
                    this.model.getDoc(clickedRow.getId());
                    ImageViewDoc.display();

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
        datePickerDetectedNewInput(nascField);

        MenuBarHelper.setupMenuBar(borderPane);

    }
    private void textAreaDetectNewInput(TextArea textArea) {
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            changed = true;
        });
    }
    private void datePickerDetectedNewInput(DatePicker datePicker){
        datePicker.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if(!oldValue.equals("")) {
                changed = true;
            }
        });
    }
    private void textFieldDetectNewInput(TextField textField){
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            changed=true;
        });
    }


}
