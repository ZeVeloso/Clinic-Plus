module clinic {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.calendarfx.view;


    opens clinic to javafx.fxml;
    opens clinic.view to javafx.fxml, javafx.graphics;
    opens clinic.business to javafx.base;
    exports clinic;
    opens clinic.view.Helpers to javafx.fxml;
    opens clinic.view.Box to javafx.fxml;
    opens clinic.view.Add to javafx.fxml, javafx.graphics;
    opens clinic.view.calendar.demo to javafx.graphics;
    opens clinic.Helpers to javafx.base;
}