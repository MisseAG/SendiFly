package co.uniquindio.edu.sendifly.controllers;

import co.uniquindio.edu.sendifly.utils.NavigationUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;


public class ViewDeliveryController implements Initializable {

    @FXML
    private TableColumn<?, ?> colAccionCancelar;

    @FXML
    private TableColumn<?, ?> colAccionConfirmar;

    @FXML
    private TableColumn<?, ?> colDestino;

    @FXML
    private TableColumn<?, ?> colFechaEntrega;

    @FXML
    private TableColumn<?, ?> colNroEnvio;

    @FXML
    private TableColumn<?, ?> colOrigen;

    @FXML
    private MenuItem logoutMenuItem;

    @FXML
    private MenuItem profileMenuItem;

    @FXML
    private TableView<?> tablaEnvios;

    @FXML
    private Label userGreetingLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        String path = "/co/uniquindio/edu/sendifly/views/MainView.fxml";
        String title = "SendiFly-Home";
        //Usa cualquier node
        Node node = userGreetingLabel;
        NavigationUtil.navigateToScene(node, path, title);
    }

    @FXML
    private void handleProfileSettings(ActionEvent event) {
        System.out.println("Configuración de perfil clicada");
    }


}
