package clinic.view;

import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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

    public void convertDatePicker(DatePicker nascField) {
        nascField.setConverter(new StringConverter<LocalDate>()
        {

            @Override
            public String toString(LocalDate localDate)
            {
                if(localDate==null)
                    return "";
                return formatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString)
            {
                if(dateString==null || dateString.trim().isEmpty())
                {
                    return null;
                }
                return LocalDate.parse(dateString,formatter);
            }
        });
    }

}
