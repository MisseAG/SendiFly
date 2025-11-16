package co.uniquindio.edu.sendifly.controllers;

import co.uniquindio.edu.sendifly.dtos.ShipmentReportDTO;
import co.uniquindio.edu.sendifly.services.ReportService;
import co.uniquindio.edu.sendifly.services.CSVReportAdapter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ViewUserController implements Initializable {

    // ================= HEADER COMPONENTS =================
    @FXML private VBox rootVBox;
    @FXML private Label userGreetingLabel;
    @FXML private MenuItem profileMenuItem;
    @FXML private MenuItem logoutMenuItem;

    // ================= MAIN CONTENT COMPONENTS =================
    @FXML private Button newQuoteButton;
    @FXML private Button createShipmentButton;
    @FXML private TextField trackingTextField;
    @FXML private Button trackButton;

    // Tabla de envíos
    @FXML private Label noEnviosLabel;
    @FXML private TableView<?> tablaEnvios;
    @FXML private TableColumn<?, ?> colIdEnvio;
    @FXML private TableColumn<?, ?> colOrigen;
    @FXML private TableColumn<?, ?> colDestino;
    @FXML private TableColumn<?, ?> colPeso;
    @FXML private TableColumn<?, ?> colTarifa;
    @FXML private TableColumn<?, ?> colAccionModificar;
    @FXML private TableColumn<?, ?> colAccionCancelar;

    // Accesos rápidos
    @FXML private Button paymentMethodsBtn;
    @FXML private Button addressesBtn;
    @FXML private Button paymentsHistorialBtn;

    // Footer
    @FXML private Button downloadReportsButton;
    @FXML private Button historyButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (userGreetingLabel != null) {
            userGreetingLabel.setText("Hola, Usuario");
        }
    }

    // ================= HEADER METHODS =================
    @FXML
    private void handleProfileSettings(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/co/uniquindio/edu/sendifly/views/user/ViewProfileConfig.fxml")
            );
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Configurar Perfil - SENDIFLY");
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "No se pudo abrir la configuración del perfil");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Cerrar Sesión");
            confirmacion.setHeaderText(null);
            confirmacion.setContentText("¿Está seguro que desea cerrar sesión?");

            if (confirmacion.showAndWait().get() == ButtonType.OK) {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/co/uniquindio/edu/sendifly/views/user/LoginView.fxml")
                );
                Parent root = loader.load();

                Stage stage = (Stage) historyButton.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("SENDIFLY - Login");
                stage.show();
            }
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo cerrar sesión");
            e.printStackTrace();
        }
    }

    // ================= MAIN CONTENT METHODS =================
    @FXML
    private void handleNewQuote(ActionEvent event) {
        // TODO: Abrir vista de cotización
    }

    @FXML
    private void handleCreateShipment(ActionEvent event) {
        // TODO: Abrir vista de crear envío
    }

    @FXML
    private void handleTrackShipment(ActionEvent event) {
        String trackingNumber = trackingTextField.getText().trim();
        if (trackingNumber.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Campo vacío", "Ingrese un número de seguimiento");
            return;
        }
        // TODO: Implementar rastreo real
        trackingTextField.clear();
    }

    // ================= ACCESOS RÁPIDOS METHODS =================
    @FXML
    private void handlePaymentMethods(ActionEvent event) {
        // TODO: Abrir vista de métodos de pago
    }

    @FXML
    private void handleAddresses(ActionEvent event) {
        // TODO: Abrir vista de direcciones
    }

    @FXML
    private void handlePaymentsHistorial(ActionEvent event) {
        // TODO: Abrir vista de historial de pagos
    }

    // ================= FOOTER METHODS =================
    @FXML
    private void handleDownloadReports(ActionEvent event) {
        try {
            ReportService reportService = ReportService.getInstance();
            CSVReportAdapter csvAdapter = new CSVReportAdapter();

            String userHome = System.getProperty("user.home");
            String filePath = userHome + "/Downloads/reporte_envios.csv";

            List<ShipmentReportDTO> shipments = reportService.getShipmentsReport(
                    "1982822057", null, null
            );

            boolean success = csvAdapter.export(shipments, filePath);

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Éxito",
                        "Reporte generado en:\n" + filePath);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error",
                        "No se pudo generar el reporte");
            }

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Error al generar reporte");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewHistory(ActionEvent event) {
        // TODO: Abrir vista de historial
    }

    // ================= UTILITY METHODS =================
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setCurrentUser(String userName) {
        if (userGreetingLabel != null) {
            userGreetingLabel.setText("Hola, " + userName);
        }
    }
}
