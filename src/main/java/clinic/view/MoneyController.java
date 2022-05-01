/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package clinic.view;

import clinic.business.ClinicFacade;
import clinic.view.Box.AlertBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

public class MoneyController implements Initializable {

    @FXML
    private Label labelMoney;
    @FXML
    private TextField anoField;
    @FXML
    private ChoiceBox<String> choiceMes;

    @FXML
    private Button pesqButton;
    @FXML
    private Button cancelarButton;

    private ClinicFacade model;

    @FXML
    public void pesqButtonHandler(){
        try{
            String mes = choiceMes.getSelectionModel().getSelectedItem().split(" ")[0];
            System.out.println(mes);
            String ano = anoField.getText();
            float o = this.model.getMoneyMes(mes, ano);
            labelMoney.setText(o + " €");
        } catch (NullPointerException e){
            AlertBox.display("Error", "Valores de campos errados");
        }

    }

    @FXML
    public void cancelarHandler(){
        Stage window = (Stage) cancelarButton.getScene().getWindow();
        window.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.model = new ClinicFacade();

        Collection<String> opt = new ArrayList<>();
        int i=1;
        while(i<=12){
            String monthString = new DateFormatSymbols().getMonths()[i-1];
            opt.add(String.format("%02d", i) + " " + monthString);
            i++;
        }
        choiceMes.getItems().addAll(opt);

    }
}
