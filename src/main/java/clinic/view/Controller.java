package clinic.view;


import clinic.MainFX;
import clinic.business.ClinicFacade;
import clinic.business.Clinica;
import clinic.business.Consulta;
import clinic.business.Utente;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    private DateHelper dateHelper;

    @FXML
    private TableView<Utente> tableUtentes;

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
    private ChoiceBox choiceBoxClinica;

    @FXML
    private Label telErrorLabel;
    @FXML
    private Label dataErrorLabel;


    @FXML
    private void addUtente() {
        String name = nameField.getText();
        String tel = telField.getText();
        String nasc = nascField.getValue().toString();
        String morada = moradaField.getText();
        String novaData = dateHelper.formatDate(nasc);
        Clinica clinica = (Clinica) choiceBoxClinica.getSelectionModel().getSelectedItem();
        int years=this.dateHelper.AgeCalculator(novaData);
        int telInt;
        try {
            telInt = Integer.parseInt(tel);
            this.model.adicionaUtente(new Utente(null, name, years, telInt, morada, novaData, clinica.getId()));
        }catch (NumberFormatException e) {
            AlertBox.display("Erro", "Campo telemóvel nao é um número");
        }
        nameField.clear();
        telField.clear();
        nascField.getEditor().clear();
        moradaField.clear();

        this.refreshData();

    }

    @FXML
    private void removeUtente() {
        ObservableList<Utente> utentesSelected, todosUtentes;
        todosUtentes = tableUtentes.getItems();
        utentesSelected = tableUtentes.getSelectionModel().getSelectedItems();

        for(Utente u: utentesSelected){
            this.model.removeUtente(u.getId());
        }
        AlertBox.display("Confirmação", "Utente removido com sucesso");
        this.refreshData();

    }

    public ClinicFacade model;

    private ObservableList<Utente> dataNotes;

    private void refreshData(){
        dataNotes.clear();
        Collection<Utente> colec =  this.model.getUtentes();
        //System.out.println(colec.toString());
        dataNotes.addAll(colec);

        tableUtentes.setItems(dataNotes);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dateHelper = new DateHelper();
        this.model= new ClinicFacade();


        choiceBoxClinica.setValue("Clinica");
        dataNotes = FXCollections.observableArrayList();
        tableUtentes.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );
        idCol.setCellValueFactory(
                new PropertyValueFactory<Utente, String>("id")
        );
        nameCol.setCellValueFactory(
                new PropertyValueFactory<Utente, String>("nome")
        );
        idadeCol.setCellValueFactory(
                new PropertyValueFactory<Utente, Integer>("idade")
        );
        telCol.setCellValueFactory(
                new PropertyValueFactory<Utente, Integer>("telemovel")
        );
        nascCol.setCellValueFactory(
                new PropertyValueFactory<Utente, String>("nascimento")
        );
        moradaCol.setCellValueFactory(
                new PropertyValueFactory<Utente, String>("morada")
        );
        clinicaCol.setCellValueFactory(
                new PropertyValueFactory<Utente, String>("clinicaID")
        );
        tableUtentes.setRowFactory(tv -> {
            TableRow<Utente> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    Utente clickedRow = row.getItem();
                    System.out.println(clickedRow.getNome());
                    Stage stage1 = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    Scene scena = ((Node)event.getSource()).getScene();
                    Stage window = (Stage) tableUtentes.getScene().getWindow();
                    Parent root;
                    try {
                        FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("perfilUtente.fxml"));
                        root = loader.load();
                        //root = FXMLLoader.load(getClass().getResource("perfilUtente.fxml"));
                        ControllerUtente c=loader.getController();
                        window.setTitle("Utentes");
                        System.out.println(clickedRow.getId());
                        c.displayID(clickedRow);
                        stage1.setScene(new Scene(root, scena.getWidth(), scena.getHeight()));
                        //stage1.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
            return row;
        });

        Collection<Clinica> clinicas = this.model.getClinicas();
        choiceBoxClinica.getItems().addAll(clinicas);

        dateHelper.convertDatePicker(nascField);

        Collection<Utente> colec =  this.model.getUtentes();
        System.out.println(colec.toString());
        dataNotes.addAll(colec);

        tableUtentes.setItems(dataNotes);

        telErrorLabel.setManaged(false);
        dataErrorLabel.setManaged(false);

        textErrorLabelHandler(telField, nascField);


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
