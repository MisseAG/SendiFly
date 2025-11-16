package co.uniquindio.edu.sendifly.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewUserController {

    @FXML
    private VBox rootVBox;

    // ---------------------------------------------------
    //      NAVEGACIÓN A CONFIGURACIÓN DE PERFIL
    // ---------------------------------------------------
    @FXML
    private void handleProfileSettings() {
        cambiarEscena("/co/uniquindio/edu/sendifly/views/user/ViewProfileConfig.fxml");
    }

    // ---------------------------------------------------
    //      CERRAR SESIÓN
    // ---------------------------------------------------
    @FXML
    private void handleLogout() {
        cambiarEscena("/co/uniquindio/edu/sendifly/views/LoginView.fxml");
    }

    // ---------------------------------------------------
    //      NUEVA COTIZACIÓN
    // ---------------------------------------------------
    @FXML
    private void handleNewQuote() {
        cambiarEscena("/co/uniquindio/edu/sendifly/views/user/ViewNewQuote.fxml");
    }

    // ---------------------------------------------------
    //      CREAR ENVÍO
    // ---------------------------------------------------
    @FXML
    private void handleCreateShipment() {
        cambiarEscena("/co/uniquindio/edu/sendifly/views/user/ViewCreateShipment.fxml");
    }

    // ---------------------------------------------------
    //      FORMAS DE PAGO
    // ---------------------------------------------------
    @FXML
    private void handlePaymentMethods() {
        cambiarEscena("/co/uniquindio/edu/sendifly/views/user/ViewPaymentMethods.fxml");
    }

    // ---------------------------------------------------
    //      DIRECCIONES
    // ---------------------------------------------------
    @FXML
    private void handleAddresses() {
        cambiarEscena("/co/uniquindio/edu/sendifly/views/user/ViewAddresses.fxml");
    }

    // ---------------------------------------------------
    //      HISTORIAL DE PAGOS
    // ---------------------------------------------------
    @FXML
    private void handlePaymentsHistorial() {
        cambiarEscena("/co/uniquindio/edu/sendifly/views/user/ViewPaymentsHistory.fxml");
    }

    // ---------------------------------------------------
    //      DESCARGAR REPORTES
    // ---------------------------------------------------
    @FXML
    private void handleDownloadReports() {
        cambiarEscena("/co/uniquindio/edu/sendifly/views/user/ViewDownloadReports.fxml");
    }

    // ---------------------------------------------------
    //      HISTORIAL DE ENVÍOS
    // ---------------------------------------------------
    @FXML
    private void handleViewHistory() {
        cambiarEscena("/co/uniquindio/edu/sendifly/views/user/ViewShipmentsHistory.fxml");
    }

    // ---------------------------------------------------
    //      RASTREO
    // ---------------------------------------------------
    @FXML
    private void handleTrackShipment() {
        System.out.println("Buscando: " /* + trackingTextField.getText() */);
    }

    // ---------------------------------------------------
    //      MÉTODO GENERAL DE CAMBIO DE ESCENA
    // ---------------------------------------------------
    private void cambiarEscena(String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();

            Stage stage = (Stage) rootVBox.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.show();

            System.out.println("✔ Navegación exitosa a: " + rutaFXML);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Error al cargar la vista: " + rutaFXML);
        }
    }
}
