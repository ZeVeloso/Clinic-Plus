package clinic.view.Add;

import clinic.business.ClinicFacade;
import clinic.business.Clinica;
import clinic.business.Utente;
import clinic.view.Box.AlertBox;
import clinic.view.Helpers.DateHelper;
import clinic.view.calendar.FXCalendar;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.net.URL;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;

public class AddUtenteController implements Initializable {
    @FXML
    private TextField nomeField;
    @FXML
    private TextField telField;
    @FXML
    private TextField moradaField;


    @FXML
    private ChoiceBox<Clinica> clinicaChooser;

    @FXML
    private Button adicionarButton;
    @FXML
    private Button cancelarButton;
    @FXML
    private GridPane gridPane;

    @FXML
    private BorderPane borderPane;

    private ClinicFacade model;
    private DateHelper dateHelper;
    private FXCalendar calendar;

    @FXML
    public void cancelarHandler(){
        Stage window = (Stage) cancelarButton.getScene().getWindow();
        window.close();
    }
    @FXML
    public void adicionarHandler() throws ParseException {


        String name = nomeField.getText();
        String tel = telField.getText();
        String nasc = calendar.getValue().toString();
        String morada = moradaField.getText();
        String novaData = dateHelper.formatDateCalendar(nasc);
        Clinica clinica;
        if(!clinicaChooser.getItems().isEmpty())
            clinica = clinicaChooser.getSelectionModel().getSelectedItem();
        else clinica = new Clinica(null, "Temporario");
        int years=this.dateHelper.AgeCalculator(novaData);
        int telInt=0;
        if(tel.equals("")){
            tel="0";
        }

        try {
            telInt = Integer.parseInt(tel);
            this.model.adicionaUtente(new Utente(null, name, years, telInt, morada, novaData, clinica.getId()));
            nomeField.clear();
            telField.clear();
            calendar.clear();
            moradaField.clear();
            Stage window = (Stage) adicionarButton.getScene().getWindow();
            window.close();
        }catch (NullPointerException e) {
            AlertBox.display("Erro", "Preencher todos os campos com *");
            telField.clear();
        } catch (NumberFormatException e){
            AlertBox.display("Erro", "Telemovel nao é um numero EX:961231231");
            telField.clear();
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.calendar = new FXCalendar();
        calendar.setValue(new Date());
        calendar.setBaseColor(Color.web("#0099ff"));
        this.model = new ClinicFacade();
        this.dateHelper = new DateHelper();
        Label data = new Label("Data de Nascimento *");
        VBox.setMargin(data, new Insets(5, 5, 5, 5));
        FXCalendar.setMargin(calendar, new Insets(5,5,5,5));
        VBox vbox = new VBox();
        vbox.getChildren().addAll(data, calendar);
        gridPane.add(vbox,2,0);
        Collection<Clinica> clinicas = this.model.getClinicas();
        clinicaChooser.getItems().addAll(clinicas);

    }
}
