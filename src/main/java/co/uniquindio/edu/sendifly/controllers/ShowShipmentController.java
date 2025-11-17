package co.uniquindio.edu.sendifly.controllers;

import co.uniquindio.edu.sendifly.models.Shipment;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ShowShipmentController {

    @FXML private Label idLabel;
    @FXML private Label origenLabel;
    @FXML private Label destinoLabel;
    @FXML private Label pedidoLabel;
    @FXML private Label entregaLabel;
    @FXML private Label precioLabel;
    @FXML private Label estadoLabel;

    public void setShipment(Shipment shipment) {
        idLabel.setText(shipment.getId());
        origenLabel.setText(shipment.getOrigin().getAlias());
        destinoLabel.setText(shipment.getDestination().getAlias());
        pedidoLabel.setText(shipment.getOrderDate().toString());
        entregaLabel.setText(shipment.getDeliveryDate().toString());
        precioLabel.setText(String.format("$%.2f", shipment.getPrice()));
        estadoLabel.setText(shipment.getShippingStatus().getName());
    }

    @FXML
    private void handleCerrar() {
        Stage stage = (Stage) idLabel.getScene().getWindow();
        stage.close();
    }
}