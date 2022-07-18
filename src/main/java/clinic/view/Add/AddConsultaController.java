package clinic.view.Add;

import clinic.business.ClinicFacade;
import clinic.business.Consulta;
import clinic.view.Box.AlertBox;
import clinic.view.Helpers.DateHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class AddConsultaController {

    static boolean answer;
    private DateHelper dateHelper;

    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;
    @FXML
    private DatePicker dataField;
    @FXML
    private TextField horaField;


    public boolean display(int id) {
        ClinicFacade c = new ClinicFacade();
        this.dateHelper = new DateHelper();

        dateHelper.convertDatePicker(dataField);

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
                        AlertBox.display("Erro", "Erro na data. Ex: 9 | 10 | 09:30");
                        return;
                    }

                } catch (DateTimeParseException | NullPointerException a) {
                    if (hora.length() == 1 && hora.matches("\\d")) hora = "0" + hora + ":00";
                    else if(hora.matches("\\d\\d")) hora = hora + ":00";
                    else return;
                    try {
                        LocalTime.parse(hora);
                    } catch (DateTimeException exp) {
                        AlertBox.display("Erro", "Erro na data. Ex: 9 | 10 | 09:30");
                        return;
                    }
                } catch (DateTimeException exp) {
                    AlertBox.display("Erro", "Erro na data. Ex: 9 | 10 | 09:30");
                    return;
                }
                String dataHora = dataFormatada + " " + hora;
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                Date d1 = null;
                Date d2 = null;
            /*
            System.out.println(dataHora + " " + id);
            Collection<Consulta> colec = c.getConsultas();*/
                boolean flag = true;
            /*
            for (Consulta u : colec) {
                try {
                    d1 = format.parse(dataHora);
                    d2 = format.parse(u.getData());
                } catch (ParseException g) {
                    //g.printStackTrace();
                    AlertBox.display("Error", "Data e hora nao estão corretas");
                }
                long diff = d2.getTime() - d1.getTime();

                long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);

                if (u.getData().equals(dataHora) || (minutes < 45 && minutes > -45)) {
                    flag = false;
                    break;
                }
            }
            */
                c.adicionaConsulta(new Consulta(null, dataHora, 0, " ", "Agendado", "", id));
                AlertBox.display("Confirmação", "Consulta adicionada com sucesso! :)");
                answer = true;
                Stage window = (Stage) addButton.getScene().getWindow();
                window.close();
            } catch(NullPointerException k){
                k.printStackTrace();
                AlertBox.display("Error", "Preencha todos os campos");
            }
        });

        cancelButton.setOnAction(e -> {
            answer = false;
            Stage window = (Stage) cancelButton.getScene().getWindow();
            window.close();
        });



        return answer;
    }

}
