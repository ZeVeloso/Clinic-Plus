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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Formatter;
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


    public boolean display(int id){
        ClinicFacade c = new ClinicFacade();
        this.dateHelper = new DateHelper();

        dateHelper.convertDatePicker(dataField);

        addButton.setOnAction(e -> {
            String data = dataField.getValue().toString();
            String dataFormatada = dateHelper.formatDate(data);
            String hora = horaField.getText();
            try{
                LocalTime.parse(hora);
            } catch (DateTimeParseException | NullPointerException a){
                hora = hora + ":00";
            }
            String dataHora = dataFormatada + " " + hora;
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Date d1 = null;
            Date d2 = null;

            System.out.println(dataHora + " " + id);
            Collection<Consulta> colec = c.getConsultas();
            boolean flag=true;
            for(Consulta u: colec){
                try {
                    d1 = format.parse(dataHora);
                    d2 = format.parse(u.getData());
                } catch (ParseException g) {
                    g.printStackTrace();
                }
                long diff = d2.getTime() - d1.getTime();

                long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);

                if (u.getData().equals(dataHora) || (minutes<45 && minutes >-45)) {
                    flag = false;
                    break;
                }
            }

            if(flag) {
                c.adicionaConsulta(new Consulta(null, dataHora, 0, " ", "Agendado","",id));
                AlertBox.display("Confirmação", "Consulta adicionada com sucesso! :)");
            } else  AlertBox.display("Confirmação", "Consulta já marcada neste horario :(");
            answer = true;
            Stage window = (Stage) addButton.getScene().getWindow();
            window.close();
        });

        cancelButton.setOnAction(e -> {
            answer = false;
            Stage window = (Stage) cancelButton.getScene().getWindow();
            window.close();
        });



        return answer;
    }

}
