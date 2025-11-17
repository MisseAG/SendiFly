package co.uniquindio.edu.sendifly.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import co.uniquindio.edu.sendifly.models.DeliveryMan;

public class ShowDeliveryController {

    @FXML private Label nombreLabel;
    @FXML private Label telefonoLabel;
    @FXML private Label correoLabel;
    @FXML private Label passwordLabel;
    @FXML private Label estadoLabel;

    public void setDeliveryMan(DeliveryMan deliveryMan) {
        nombreLabel.setText(deliveryMan.getName());
        telefonoLabel.setText(deliveryMan.getPhone());
        correoLabel.setText(deliveryMan.getEmail());
        passwordLabel.setText(deliveryMan.getPassword());
        estadoLabel.setText(deliveryMan.getAvailabilityStatus().getClass().getSimpleName());
    }

    @FXML
    private void handleCerrar() {
        Stage stage = (Stage) nombreLabel.getScene().getWindow();
        stage.close();
    }
}
