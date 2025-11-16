package co.uniquindio.edu.sendifly.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PayShippingController {

    @FXML
    private Button backBtn;

    @FXML
    private Label destinoLabel;

    @FXML
    private Label infoMetodoPagoLabel;

    @FXML
    private VBox infoMetodoPagoVBox;

    @FXML
    private ComboBox<?> metodoPagoCombox;

    @FXML
    private Label origenLabel;

    @FXML
    private Button pagarButton;

    @FXML
    private Label pesoLabel;

    @FXML
    private Label precioLabel;

    @FXML
    private Label prioridadLabel;

    @FXML
    private Label volumenLabel;

    @FXML
    void handleBack(ActionEvent event) {

    }

    @FXML
    void handlePagar(ActionEvent event) {

    }

    // Cargar métodos de pago del cliente initialize
    private void cargarMetodosPago() {
        //List<String> metodosPago = UserActual.getMetodosPago(); // Tu ArrayList
       // metodoPagoComboBox.getItems().addAll(metodosPago);
    }
}
