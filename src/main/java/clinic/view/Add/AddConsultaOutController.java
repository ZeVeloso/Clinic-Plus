package clinic.view.Add;

import clinic.business.ClinicFacade;
import clinic.business.Consulta;
import clinic.business.Utente;
import clinic.view.Box.AlertBox;
import clinic.view.Helpers.DateHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class AddConsultaOutController implements Initializable {

    static boolean answer;
    private DateHelper dateHelper;

    @FXML
    private Button pesqButton;
    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;
    @FXML
    private DatePicker dataField;
    @FXML
    private TextField horaField;
    @FXML
    private TextField nomeField;
    @FXML
    private ChoiceBox<Utente> choiceBox;

    private ClinicFacade model;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.model = new ClinicFacade();
        this.dateHelper = new DateHelper();
        System.out.println("entrou");
        dateHelper.convertDatePicker(dataField);

        //Collection<Utente> utentes = this.model.getUtentes();
        //choiceBox.getItems().addAll(utentes);
        System.out.println("entrou");
        pesqButton.setOnAction(e->{
            String nome = nomeField.getText();
            //Collection<Utente> collection = this.model.getUtentesFilter(nome, "", "", "", "");
            choiceBox.getItems().clear();
            //choiceBox.getItems().addAll(collection);
            Collection<Utente> coll = this.model.getUtentesFilter(nome, "", "", "", "");
            coll = coll.stream().limit(30).collect(Collectors.toList());
            ObservableList<Utente> list = FXCollections.observableArrayList();
            list.addAll(coll);
            choiceBox.setItems(list);

        });

        addButton.setOnAction(e -> {
            try{
            String data = dataField.getValue().toString();
            String dataFormatada = dateHelper.formatDate(data);
            String hora = horaField.getText();
            if(Objects.equals(hora, ""))
                hora = "00:00";
            try {
                if(hora.matches("\\d") || hora.matches("\\d\\d") || hora.matches("\\d\\d:\\d\\d")) {
                    LocalTime.parse(hora);
                } else {
                    AlertBox.display("Erro", "Erro na data");
                    return;
                }

            } catch (DateTimeParseException | NullPointerException a) {
                if (hora.length() == 1 && hora.matches("\\d*")) hora = "0" + hora + ":00";
                else if(hora.matches("\\d*")) hora = hora + ":00";
            }
            String dataHora = dataFormatada + " " + hora;
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Date d1 = null;
            Date d2 = null;

            //System.out.println(dataHora + " " + id);
            //Collection<Consulta> colec = this.model.getConsultas();
            boolean flag = true;
            /*
            for (Consulta u : colec) {
                try {
                    d1 = format.parse(dataHora);
                    d2 = format.parse(u.getData());
                } catch (ParseException ignored) {
                }
                long diff = d2.getTime() - d1.getTime();

                long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);

                if (u.getData().equals(dataHora) || (minutes < 45 && minutes > -45)) {
                    flag = false;
                    break;
                }
            }
            */
            if (flag) {
                Utente utente;
                utente = choiceBox.getSelectionModel().getSelectedItem();
                this.model.adicionaConsulta(new Consulta(null, dataHora, 0, " ", "Agendado", "", utente.getId()));
                AlertBox.display("Confirmação", "Consulta adicionada com sucesso! :)");
            } else AlertBox.display("Confirmação", "Consulta já marcada neste horario :(");
            answer = true;
            Stage window = (Stage) addButton.getScene().getWindow();
            window.close();
        }catch(NullPointerException k){
                AlertBox.display("Error", "Preencha todos os campos corretamente");
        }
        });

        cancelButton.setOnAction(e -> {
            answer = false;
            Stage window = (Stage) cancelButton.getScene().getWindow();
            window.close();
        });

    }
}
