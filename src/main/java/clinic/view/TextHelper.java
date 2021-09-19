package clinic.view;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TextHelper {

    public void textError(TextField text) {
        text.focusedProperty().

        addListener((arg0, oldValue, newValue) ->

        {
            if (!newValue) { // when focus lost
                if (!text.getText().matches("[0-9]*")) {
                    text.setStyle("-fx-text-box-border: red; -fx-focus-color: red ; -fx-border-width: 2px;");
                } else text.setStyle("-fx-border-width: 0px ;");
            }
        });
    }

}
