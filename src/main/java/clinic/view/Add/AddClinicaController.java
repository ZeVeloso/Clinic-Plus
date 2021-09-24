package clinic.view.Add;

import clinic.MainFX;
import clinic.business.ClinicFacade;
import clinic.business.Clinica;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddClinicaController implements Initializable {

    @FXML
    private TextField nomeField;

    @FXML
    private Button adicionarButton;
    @FXML
    private Button cancelarButton;

    private ClinicFacade model;

    @FXML
    public void cancelarHandler(){
        Stage window = (Stage) cancelarButton.getScene().getWindow();
        window.close();
    }
    @FXML
    public void adicionarHandler(){

        String nome = nomeField.getText();
        this.model.adicionaClinica(new Clinica(null, nome));

        Stage window = (Stage) adicionarButton.getScene().getWindow();
        window.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        this.model = new ClinicFacade();

    }
}
