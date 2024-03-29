package clinic.view.Helpers;

import clinic.Helpers.UtenteConsultaClinica;
import clinic.business.ClinicFacade;
import clinic.business.Utente;
import clinic.view.calendar.FXCalendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;

public class UtenteClinicaHelper {

    public static void setupInit(ImageView logoImageView, TableView<UtenteConsultaClinica> tableUtentes,
                           TableColumn idCol, TableColumn nameCol, TableColumn idadeCol, TableColumn telCol, TableColumn nascCol, TableColumn moradaCol, int id){


        tableUtentes.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );

        idCol.setCellValueFactory(
                new PropertyValueFactory<UtenteConsultaClinica, String>("idUtente")
        );
        nameCol.setCellValueFactory(
                new PropertyValueFactory<UtenteConsultaClinica, String>("nomeUtente")
        );
        idadeCol.setCellValueFactory(
                new PropertyValueFactory<UtenteConsultaClinica, Integer>("idadeUtente")
        );
        telCol.setCellValueFactory(
                new PropertyValueFactory<UtenteConsultaClinica, Integer>("telemovelUtente")
        );
        nascCol.setCellValueFactory(
                new PropertyValueFactory<UtenteConsultaClinica, String>("dataNascimentoUtente")
        );
        moradaCol.setCellValueFactory(
                new PropertyValueFactory<UtenteConsultaClinica, String>("moradaUtente")
        );
        tableUtentes.setRowFactory(tv -> {
            TableRow<UtenteConsultaClinica> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    UtenteConsultaClinica clickedRow = row.getItem();
                    /*Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    Scene scena = ((Node)event.getSource()).getScene();
                    Parent root;*/
                    try {
                        /*FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("perfilUtente.fxml"));
                        root = loader.load();
                        ControllerUtente c=loader.getController();
                        stage.setTitle("Clinic +");
                        c.displayID(clickedRow.getIdUtente(), id);
                        Scene scene = new Scene(root, scena.getWidth(), scena.getHeight());
                        scene.getStylesheets().add(MainFX.class.getResource("calendar_styles.css").toExternalForm());
                        stage.setScene(scene);*/
                        GoToHelper.gotoSameStageController((Node)event.getSource(),"perfilUtente.fxml",clickedRow.getIdUtente(), id);
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                    }

                }
            });
            return row;
        });
    }

    public static void setupFilterBox(VBox vbox, FXCalendar calendar){
        calendar.setBaseColor(Color.web("#0099ff"));
        FXCalendar.setMargin(calendar, new Insets(5,5,5,5));
        calendar.setMinWidth(50);
        vbox.getChildren().add(calendar);
    }

    public static void refreshDataFilter(Collection collec, TableView table, ObservableList data){
        if(data!=null)
            data.clear();
        else data = FXCollections.observableArrayList();
        data.addAll(collec);

        table.setItems(data);
    }

    public static Collection<Utente> filterUtenteHandler(ClinicFacade model, TextField nomeField, TextField telemovelField, TextField moradaField, FXCalendar calendar, String idClinica) throws ParseException {
        DateHelper dateHelper = new DateHelper();
        String nome = nomeField.getText();
        String telemovel =  telemovelField.getText();
        String morada = moradaField.getText();
        Date data = calendar.getValue();
        String novaData="";
        if(nome==null) nome="";
        if(telemovel==null) telemovel="";
        if(morada==null) morada="";
        if(data!=null) novaData = dateHelper.formatDateCalendar(data.toString());
        return model.getUtentesFilter(nome, telemovel, novaData, morada, idClinica);

    }

    public static Collection<UtenteConsultaClinica> filterUtenteCCHandler(ClinicFacade model, TextField nomeField, TextField telemovelField, TextField moradaField, FXCalendar calendar) throws ParseException {
        DateHelper dateHelper = new DateHelper();
        String nome = nomeField.getText();
        String telemovel =  telemovelField.getText();
        String morada = moradaField.getText();
        Date data = calendar.getValue();
        String novaData="";
        if(nome==null) nome="";
        if(telemovel==null) telemovel="";
        if(morada==null) morada="";
        if(data!=null) novaData = dateHelper.formatDateCalendar(data.toString());
        System.out.println(novaData + data);
        return model.getUtentesConsultaClinicaFilter(nome, telemovel, novaData, morada);

    }
}
