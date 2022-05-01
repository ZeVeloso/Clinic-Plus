package clinic.view;

import clinic.Helpers.Pair;
import clinic.business.ClinicFacade;
import clinic.business.Consulta;
import clinic.business.Utente;
import clinic.view.Helpers.DateHelper;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;

public class CalendarViewController {


    public static void initialize(BorderPane borderPane) {
        ClinicFacade model = new ClinicFacade();
        DateHelper dateHelper = new DateHelper();
        CalendarView calendarView = new CalendarView();

        Calendar consultas = new Calendar("Consultas");
        calendarView.setPadding(new Insets(5,5,5,5));

        consultas.setStyle(Style.STYLE2);
        calendarView.setShowPrintButton(false);
        calendarView.setShowAddCalendarButton(false);

        CalendarSource myCalendarSource = new CalendarSource("Consultas");

        calendarView.setRequestedTime(LocalTime.now());

        Collection<Pair<Consulta, Utente>> coll = model.getTudo();
        try {
            for (Pair<Consulta, Utente> c : coll) {
                Entry<String> entry = new Entry<>(c.getFirst().getId() + " " + c.getSecond().getNome());
                entry.setInterval(LocalDate.now());
                entry.changeStartDate(LocalDate.parse(c.getFirst().getData().split(" ")[0], DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                entry.changeEndDate(LocalDate.parse(c.getFirst().getData().split(" ")[0], DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                entry.changeStartTime(LocalTime.parse(c.getFirst().getData().split(" ")[1]));
                entry.changeEndTime(LocalTime.parse(c.getFirst().getData().split(" ")[1]).plusMinutes(45));
                entry.setLocation(c.getSecond().getClinicaID().toString());
                consultas.addEntry(entry);
            }
        } catch (DateTimeParseException k){
            System.out.println("a");
        }
        myCalendarSource.getCalendars().add(0,consultas);
        calendarView.getCalendarSources().addAll(myCalendarSource);
        calendarView.setDefaultCalendarProvider(param -> consultas);
        consultas.addEventHandler(e->{
            if(e.isEntryAdded()) e.getEntry().removeFromCalendar();
            updateConsulta(e.getEntry(), model, dateHelper);
        });

        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        calendarView.setToday(LocalDate.now());
                        calendarView.setTime(LocalTime.now());
                    });

                    try {
                        // update every 10 seconds
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();

        borderPane.setCenter(calendarView);

    }

    private static void updateConsulta(Entry e, ClinicFacade model, DateHelper dateHelper){
        //System.out.println(e.toString());
        if(e.getTitle().split(" ")[0].equals("New")) return;
        Integer idConsulta = Integer.parseInt(e.getTitle().split(" ")[0]);
        String data = dateHelper.formatDate(e.getStartDate().toString());
        String time = LocalTime.parse(e.getStartTime().toString()).toString();
        String dateTime = data + " " + time;
        Consulta c = new Consulta(idConsulta, dateTime);
        model.updateConsultaCalendario(c);
        e.changeEndTime(e.getStartTime().plusMinutes(45));
    }

}