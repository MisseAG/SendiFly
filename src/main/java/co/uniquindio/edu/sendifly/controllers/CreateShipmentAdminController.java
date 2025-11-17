package co.uniquindio.edu.sendifly.controllers;

import co.uniquindio.edu.sendifly.dtos.ShipmentDTO;
import co.uniquindio.edu.sendifly.models.Shipment;
import co.uniquindio.edu.sendifly.services.ShipmentService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreateShipmentAdminController {

    @FXML private TextField originField;
    @FXML private TextField destinationField;
    @FXML private TextField priceField;
    @FXML private DatePicker orderDatePicker;
    @FXML private DatePicker deliveryDatePicker;
    @FXML private TextField deliveryTimeField; // formato HH:mm
    @FXML private TextField statusField;
    @FXML private Label errorLabel;

    private ObservableList<ShipmentDTO> shipmentObservable;

    public void setShipmentObservable(ObservableList<ShipmentDTO> shipmentObservable) {
        this.shipmentObservable = shipmentObservable;
    }

    private ShipmentDTO shipmentToEdit;

    public void setShipmentToEdit(ShipmentDTO dto) {
        this.shipmentToEdit = dto;
        originField.setText(dto.getOriginAlias());
        destinationField.setText(dto.getDestinationAlias());
        priceField.setText(String.valueOf(dto.getPrice()));
        orderDatePicker.setValue(dto.getOrderDate());
        deliveryDatePicker.setValue(dto.getDeliveryDate().toLocalDate());
        deliveryTimeField.setText(dto.getDeliveryDate().toLocalTime().toString());
        statusField.setText(dto.getStatus());
    }

    private Runnable onShipmentChange;

    public void setOnShipmentChange(Runnable callback) {
        this.onShipmentChange = callback;
    }

    @FXML
    private void handleCreateShipment() {
        String origin = originField.getText();
        String destination = destinationField.getText();
        String priceText = priceField.getText();
        LocalDate orderDate = orderDatePicker.getValue();
        LocalDate deliveryDate = deliveryDatePicker.getValue();
        String deliveryTime = deliveryTimeField.getText();
        String status = statusField.getText();

        if (origin.isEmpty() || destination.isEmpty() || priceText.isEmpty()
                || orderDate == null || deliveryDate == null || deliveryTime.isEmpty() || status.isEmpty()) {
            errorLabel.setText("Todos los campos son obligatorios.");
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            LocalDateTime fullDeliveryDate = LocalDateTime.of(deliveryDate, LocalDateTime.parse("2000-01-01T" + deliveryTime).toLocalTime());

            if (shipmentToEdit != null) {
                Shipment original = ShipmentService.getInstance().getShipment(shipmentToEdit.getId());
                Shipment updated = new Shipment.ShipmentBuilder()
                        .id(original.getId())
                        .origin(original.getOrigin())
                        .destination(original.getDestination())
                        .orderDate(orderDate)
                        .deliveryDate(fullDeliveryDate)
                        .price(price)
                        .additionalService(original.getAdditionalService())
                        .shippingStatus(original.getShippingStatus())
                        .pack(original.getPack())
                        .build();

                ShipmentService.getInstance().updateShipment(original.getId(), updated);
            }

            if (shipmentObservable != null) {
                shipmentObservable.clear();
                ShipmentService.getInstance().getAllShipments().stream()
                        .map(ShipmentDTO::fromShipment)
                        .forEach(shipmentObservable::add);
            }

            closeWindow();
            if (onShipmentChange != null) {
                onShipmentChange.run();
            }

        } catch (NumberFormatException e) {
            errorLabel.setText("El precio debe ser un número válido.");
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) originField.getScene().getWindow();
        stage.close();
    }
}