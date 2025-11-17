package co.uniquindio.edu.sendifly.controllers;

import co.uniquindio.edu.sendifly.models.*;
import co.uniquindio.edu.sendifly.models.AdditionalServices.AdditionalService;
import co.uniquindio.edu.sendifly.models.AdditionalServices.None;
import co.uniquindio.edu.sendifly.models.Priorities.Priority;
import co.uniquindio.edu.sendifly.models.Priorities.ShippingPriority;
import co.uniquindio.edu.sendifly.models.ShippingStatus.Requested;
import co.uniquindio.edu.sendifly.repositories.PackRepository;
import co.uniquindio.edu.sendifly.services.ShipmentService;
import co.uniquindio.edu.sendifly.utils.NavigationUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class CreateShipmentController {

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
    private ComboBox<ShippingPriority> prioridadComboBox;

    @FXML
    private TextField precioProductoTextField;

    @FXML
    private TextField volumenTextField;

    private final ShipmentService shipmentService = new ShipmentService();

    @FXML
    void handleBack(ActionEvent event) {
        String path = "/co/uniquindio/edu/sendifly/views/LoginView.fxml";
        String title = "SendiFly - Login";
        NavigationUtil.navigateToScene(backButton, path, title);
    }

    @FXML
    void handleCrear(ActionEvent event) {
        try {

            String origin = origenTextField.getText();
            String destination = destinoTextField.getText();
            ShippingPriority priority = prioridadComboBox.getValue();


            String productName =  "Generic Product";
            float productPrice = 0f;
            try {
            } catch (NumberFormatException ignored) {}

            float weight = Float.parseFloat(pesoTextField.getText());
            float volume = Float.parseFloat(volumenTextField.getText());

            Pack pack = new Pack.PackageBuilder()
                    .id(generatePackId())
                    .product(productName)
                    .price(productPrice)
                    .weight(weight)
                    .volume(volume)
                    .build();


            AdditionalService selectedService = new None();

            float finalPrice = shipmentService.calculateShippingPrice(pack, selectedService, priority);
            precioLabel.setText("$" + finalPrice);

            shipmentService.createShipment(
                    origin,
                    destination,
                    LocalDate.now(),
                    LocalTime.now(),
                    selectedService,
                    new Requested(),
                    priority,
                    pack
            );

            // Navegar si todo sale bien (sin modificar)
            String path = "/co/uniquindio/edu/sendifly/views/ViewUser.fxml";
            String title = "SendiFly - Dashboard";
            NavigationUtil.navigateToScene(crearEnvioBtn, path, title);

        } catch (NumberFormatException e) {
            showAlert("Invalid input", "Weight, volume and numeric fields must be valid numbers.");
        } catch (IllegalArgumentException e) {
            showAlert("Validation error", e.getMessage());
        } catch (Exception e) {
            showAlert("Unexpected error", "Something went wrong while creating the shipment.");
        }
    }


   /* private double calculatePrice(double weight, double volume, ShippingPriority priority) {
        double base = weight * 1000 + volume * 500;
        if (priority != null && priority instanceof Priority) {
            base *= 1.2;
        }
        return base;
    } */

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String generatePackId() {
        return "PACK-" + UUID.randomUUID();
    }

}
