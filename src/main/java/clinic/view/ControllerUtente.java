package clinic.view;

import clinic.HelloApplication;
import clinic.MainFX;
import clinic.business.ClinicFacade;
import clinic.business.Consulta;
import clinic.business.Utente;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

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

    public ClinicFacade model;

    private ObservableList<Consulta> dataNotes;

    DateHelper dateHelper;

    private boolean changed;


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
            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("utentes.fxml"));
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
        Stage stage = (Stage) apagarUtenteButton.getScene().getWindow();
        Scene scene = apagarUtenteButton.getScene();
        FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("utentes.fxml"));
        Parent root = loader.load();
        stage.setTitle("Utentes");
        stage.setScene(new Scene(root, scene.getWidth(), scene.getHeight()));

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
        int years = this.dateHelper.AgeCalculator(nascimento);
        Utente novo = new Utente(id, nascimento, telemovel, years, profissao, histFam, histPess, nome, fis, morada, 1);
        this.model.updateUtente(novo);
        idadeField.setText(String.valueOf(years));
        AlertBox.display("Confirmação", "Dados alterados com sucesso! :)");
        changed=false;
    }


    public void displayID(Utente u){
        changed=false;
        this.dateHelper = new DateHelper();
        this.model = new ClinicFacade();
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

        dataNotes = FXCollections.observableArrayList();
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

        dateHelper.convertDatePicker(nascField);

        Collection<Consulta> colec =  this.model.getConsultasUtente(u.getId());
        System.out.println(colec.toString());
        dataNotes.addAll(colec);

        tableConsulta.setItems(dataNotes);

        tableConsulta.setRowFactory(tv -> {
            TableRow<Consulta> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    Consulta clickedRow = row.getItem();
                    Stage stage1 = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    Scene scena = ((Node)event.getSource()).getScene();
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

        textFieldDetectNewInput(nomeField);
        textFieldDetectNewInput(profField);
        textFieldDetectNewInput(telField);
        textFieldDetectNewInput(idadeField);
        textFieldDetectNewInput(moradaField);
        textAreaDetectNewInput(histFamField);
        textAreaDetectNewInput(histPessField);
        textAreaDetectNewInput(fisField);
        datePickerDetectedNewInput(nascField);
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
