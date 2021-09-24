package clinic.view.Helpers;

import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;

public class DateHelper {

    private DateTimeFormatter formatter;

    public DateHelper() {
        this.formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }
    public DateHelper(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

    public void setFormatter(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    public int AgeCalculator(String birth){
        LocalDate birthDate = LocalDate.parse(birth, formatter);
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }

    public boolean isValid(String dateStr) {
        try {
            LocalDate.parse(dateStr, formatter );
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public String formatDate(String date){
        LocalDate local = LocalDate.parse(date);
        return formatter.format(local);
    }

    public String formatDateCalendar(String date) throws ParseException {
        DateFormat inputFormat = new SimpleDateFormat(
                "E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        Date data = inputFormat.parse(date);

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        return formatter.format(data);
    }

    public void convertDatePicker(DatePicker nascField) {
        nascField.setConverter(new StringConverter<>() {

            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null)
                    return "";
                return formatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, formatter);
            }
        });
    }

}
