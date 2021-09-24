package clinic.view;

import clinic.Helpers.Pair;
import clinic.business.ClinicFacade;
import clinic.business.Consulta;
import clinic.business.Utente;
import clinic.view.Helpers.DateHelper;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.DateControl;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.control.skin.DateCellSkin;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import org.controlsfx.control.action.Action;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

public class CalendarViewController {

    public static void initialize(BorderPane borderPane) {
        ClinicFacade model = new ClinicFacade();
        DateHelper dateHelper = new DateHelper();
        CalendarView calendarView = new CalendarView();

        Calendar consultas = new Calendar("Consultas");



        consultas.setStyle(Style.STYLE2);

        CalendarSource myCalendarSource = new CalendarSource("My Calendars");

        calendarView.setRequestedTime(LocalTime.now());
        Collection<Consulta> colll = model.getConsultas();
        Collection<Pair<Consulta, Utente>> coll = model.getTudo();
        for(Pair<Consulta, Utente> c: coll){
            Entry<String> entry = new Entry<>(c.getFirst().getId() + " " + c.getSecond().getNome());
            entry.setInterval(LocalDate.now());
            entry.changeStartDate(LocalDate.parse(c.getFirst().getData().split(" ")[0],DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            entry.changeEndDate  (LocalDate.parse(c.getFirst().getData().split(" ")[0],DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            entry.changeStartTime(LocalTime.parse(c.getFirst().getData().split(" ")[1]));
            entry.changeEndTime(LocalTime.parse(c.getFirst().getData().split(" ")[1]).plusMinutes(45));
            entry.setLocation(c.getSecond().getClinicaID().toString());
            consultas.addEntry(entry);
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
            };
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