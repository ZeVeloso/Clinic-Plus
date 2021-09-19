module clinic {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens clinic to javafx.fxml;
    opens clinic.view to javafx.fxml;
    opens clinic.business to javafx.base;
    exports clinic;
}