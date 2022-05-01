package clinic.view.Helpers;

import clinic.MainFX;
import clinic.view.Box.AlertBox;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class MenuBarHelper {

    public static void setupMenuBar(BorderPane borderPane){
        HBox hBox = new HBox();
        Image logo = new Image(MainFX.class.getResourceAsStream("maisIcon.jpg"));
        ImageView logoView = new ImageView(logo);
        logoView.setFitHeight(26);
        logoView.setFitWidth(26);
        logoView.setOnMouseClicked(e-> {
            try {
                GoToHelper.goToSameStage((Node) e.getSource(),"menuInicial.fxml");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });


        MenuBar menuBar = new MenuBar();

        Menu utentesMenu = new Menu("Utentes");
        Menu clinicasMenu = new Menu("Clinicas");
        Menu consultasMenu = new Menu("Consultas");
        Menu faturamentoMenu = new Menu("Faturamento");
        Menu helpMenu = new Menu("Ajuda");

        MenuItem pesqUtentes = new MenuItem("Pesquisar");
        MenuItem addUtentes = new MenuItem("Adicionar");
        MenuItem addClinica = new MenuItem("Adicionar");
        MenuItem pesqClinicas = new MenuItem("Pesquisar");
        MenuItem pesqConsultas = new MenuItem("Pesquisar");
        MenuItem addConsultas = new MenuItem("Adicionar");
        MenuItem faturamentoItem = new MenuItem("Faturamento");
        MenuItem ajuda = new MenuItem("Ajuda");

        faturamentoItem.setOnAction(e-> {
            try {
                GoToHelper.goToNewStage("money.fxml",400,140);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        pesqUtentes.setOnAction(e-> {
            try {
                GoToHelper.goToSameStage((MenuItem) e.getSource(), "utentes.fxml");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        addUtentes.setOnAction(e->{
            try {
                GoToHelper.goToNewStage("addUtente.fxml",500,190);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        pesqClinicas.setOnAction(e->{
            try {
                GoToHelper.goToSameStage((MenuItem) e.getSource(), "clinicas.fxml");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        addClinica.setOnAction(e->{
            try {
                GoToHelper.goToNewStage("addClinica.fxml", 380, 150);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        pesqConsultas.setOnAction(e-> {
            try {
                GoToHelper.goToSameStage((MenuItem) e.getSource(), "consultas.fxml");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        addConsultas.setOnAction(e-> {
            try {
                GoToHelper.goToNewStage("addConsultaOut.fxml", 400, 183);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        ajuda.setOnAction(e-> AlertBox.display("Ajuda","Email: veloso.j04@gmail.com"));

        utentesMenu.getItems().addAll(pesqUtentes, addUtentes);
        clinicasMenu.getItems().addAll(pesqClinicas, addClinica);
        consultasMenu.getItems().addAll(pesqConsultas, addConsultas);
        faturamentoMenu.getItems().add(faturamentoItem);
        helpMenu.getItems().add(ajuda);

        menuBar.getMenus().addAll(utentesMenu, clinicasMenu, consultasMenu, faturamentoMenu, helpMenu);

        hBox.getChildren().addAll(logoView, menuBar);

        borderPane.setTop(hBox);

    }

}
