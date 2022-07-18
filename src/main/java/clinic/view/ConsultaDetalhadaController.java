package clinic.view;

import clinic.Helpers.UtenteConsultaClinica;
import clinic.business.ClinicFacade;
import clinic.business.Consulta;
import clinic.view.Box.AlertBox;
import clinic.view.Box.ConfirmBox;
import clinic.view.Helpers.DateHelper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

public class ConsultaDetalhadaController {

    @FXML
    private Label idLabel;

    @FXML
    private Label estadoLabel;

    @FXML
    private DatePicker dataField;

    @FXML
    private TextField custoField;

    @FXML
    private TextField motivoField;

    @FXML
    private TextField horaField;
    @FXML
    private Button apagarButton;
    @FXML
    private TextArea obsArea;
    private int idUtente;
    private DateHelper dateHelper;
    private ClinicFacade model;
    private TableView tb;
    private ObservableList dataa;
    private int type;
    private boolean changed;

    public void alterarEstadoHandler(){
        if(estadoLabel.getText().equals("Agendado")) estadoLabel.setText("Efetuado");
        else if(estadoLabel.getText().equals("Efetuado")) estadoLabel.setText("Agendado");
        else estadoLabel.setText("Error - erro impossivel... mas se tas a ver isto é pq nao é");
    }

    public void apagarConsultaHandler(){
        this.model.removeConsulta(Integer.parseInt(idLabel.getText()));
        AlertBox.display("Confirmação", "Consulta removida com sucesso! :)");
        Stage stage = (Stage) apagarButton.getScene().getWindow();
        stage.close();
        this.refreshDataUtente();

    }

    public void updateConsulta() {
        Integer id = Integer.parseInt(idLabel.getText());
        String data = dataField.getValue().format(dateHelper.getFormatter());
        String hora = horaField.getText();
        try{
            if(hora.matches("\\d\\d:\\d\\d")) {
                LocalTime.parse(hora);
            } else {
                AlertBox.display("Erro", "Erro na data. Ex: 09:30");
                return;
            }
        } catch (DateTimeException exp) {
            AlertBox.display("Erro", "Erro na data. Ex: 09:30");
        }

        String dataHora = data + " " + hora;
        String estado = estadoLabel.getText();
        float custo = Float.parseFloat(custoField.getText());
        String obs = obsArea.getText();
        String motivo = motivoField.getText();
        Consulta novo = new Consulta(id, dataHora, custo, obs, estado, motivo , 0);
        this.model.updateConsulta(novo);
        if(this.type==1)
            this.refreshDataUtente();
        else if(this.type==2)
            this.refreshDataConsultas();
        changed=false;
    }

    private void refreshDataConsultas(){
        if(dataa!=null)
            dataa.clear();
        Collection<UtenteConsultaClinica> colec =  this.model.getConsultasUC();
        dataa.addAll(colec);
        tb.setItems(dataa);
    }

    private void refreshDataUtente(){
        if(dataa!=null)
            dataa.clear();
        Collection<Consulta> colec =  this.model.getConsultasUtente(idUtente);
        dataa.addAll(colec);
        tb.setItems(dataa);
    }

    public boolean exit(){
        boolean answer;
        if(changed) {
            answer= ConfirmBox.display("Confirmação","Dados nao guardados... Pretende sair?");
        } else return true;
        return answer;

    }

    private void textAreaDetectNewInput(TextArea textArea) {
        textArea.textProperty().addListener((observable, oldValue, newValue) -> changed = true);
    }
    private void datePickerDetectedNewInput(DatePicker datePicker){
        datePicker.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if(!oldValue.equals("")) {
                changed = true;
            }
        });
    }
    private void textFieldDetectNewInput(TextField textField){
        textField.textProperty().addListener((observable, oldValue, newValue) -> changed=true);
    }
    private void labelDetectNewInput(Label label){
        label.textProperty().addListener((observable, oldValue, newValue) -> changed=true);
    }

    public void displayConsulta(Consulta c, TableView t, ObservableList data, int type){
        this.dateHelper = new DateHelper();
        this.model = new ClinicFacade();
        idLabel.setText(String.valueOf(c.getId()));
        estadoLabel.setText(c.getEstado());
        String[] dataParser = c.getData().split(" ");
        dataField.setValue(LocalDate.parse(dataParser[0],dateHelper.getFormatter()));
        horaField.setText(dataParser[1]);
        custoField.setText(String.valueOf(c.getCusto()));
        motivoField.setText(c.getMotivo());
        obsArea.setText(c.getObs());
        dataa= data;
        tb=t;
        idUtente = c.getUtente();
        this.type = type;

        labelDetectNewInput(estadoLabel);
        textFieldDetectNewInput(horaField);
        textFieldDetectNewInput(custoField);
        textFieldDetectNewInput(motivoField);
        textAreaDetectNewInput(obsArea);
        datePickerDetectedNewInput(dataField);


    }




}
