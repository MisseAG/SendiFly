package co.uniquindio.edu.sendifly.controllers;

import co.uniquindio.edu.sendifly.utils.NavigationUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CrearEnvioController {

    @FXML
    private Button backButton;

    @FXML
    private Button crearEnvioBtn;

    @FXML
    private TextField destinoTextField;

    @FXML
    private TextField origenTextField;

    @FXML
    private TextField pesoTextField;

    @FXML
    private Label precioLabel;

    @FXML
    private ComboBox<?> prioridadComboBox;

    @FXML
    private TextField volumenTextField;

    @FXML
    void handleBack(ActionEvent event) {

    }

    @FXML
    void handleCrear(ActionEvent event) {
        //lógica para crear envío, se puede delegar a un envioservice. para no solid
        //crear envio, también se puede reutilizar como Modificar envio. Lo que hace cuando
        //viene de cotización, es poner esos datos, pero que estén disponibles a modificar.
        //
        String path = "/co/uniquindio/edu/sendifly/views/.fxml";
        String title = "SendiFly-Login";
        NavigationUtil.navigateToScene(backButton, path, title);
    }

}
