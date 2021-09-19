package clinic.view;

import clinic.MainFX;
import clinic.business.ClinicFacade;
import clinic.business.Consulta;
import clinic.business.Utente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private ObservableList<Consulta> dataa;

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

    public void updateConsulta() throws IOException {
        Integer id = Integer.parseInt(idLabel.getText());
        String data = dataField.getValue().format(dateHelper.getFormatter());
        String hora = horaField.getText();
        String dataHora = data + " " + hora;
        String estado = estadoLabel.getText();
        float custo = Float.parseFloat(custoField.getText());
        String obs = obsArea.getText();
        String motivo = motivoField.getText();
        Consulta novo = new Consulta(id, dataHora, custo, obs, estado, motivo , 0);
        this.model.updateConsulta(novo);
        this.refreshDataUtente();
    }

    private void refreshDataUtente(){
        if(dataa!=null)
            dataa.clear();
        Collection<Consulta> colec =  this.model.getConsultasUtente(idUtente);
        dataa.addAll(colec);
        tb.setItems(dataa);
    }

    public void displayConsulta(Consulta c, TableView t, ObservableList<Consulta> data){
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




    }


}
