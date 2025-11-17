package co.uniquindio.edu.sendifly.controllers;

import co.uniquindio.edu.sendifly.dtos.ShipmentTrackDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class TrackShipmentController {

    @FXML
    private Label idEnvioLabel;

    @FXML
    private Label estadoLabel;

    @FXML
    private Label iconoEstadoLabel;

    @FXML
    private Label descripcionEstadoLabel;

    @FXML
    private Button cerrarButton;

    /**
     * Establece los datos del envío en la vista
     * @param dto DTO con ID y estado del envío
     */
    public void setShipmentData(ShipmentTrackDTO dto) {
        if (dto == null) {
            idEnvioLabel.setText("--");
            estadoLabel.setText("--");
            descripcionEstadoLabel.setText("No se encontró información del envío");
            return;
        }

        // Establecer ID
        idEnvioLabel.setText(dto.getId());

        // Establecer estado y personalizar según el estado
        String status = dto.getStatus();
        estadoLabel.setText(traducirEstado(status));

        // Personalizar icono y descripción según el estado
        configurarVisualizacionEstado(status);
    }

    /**
     * Traduce el estado técnico a un texto legible
     */
    private String traducirEstado(String status) {
        return switch (status.toUpperCase()) {
            case "REQUESTED" -> "Solicitado";
            case "PENDING" -> "Pendiente";
            case "IN_TRANSIT" -> "En Tránsito";
            case "DELIVERED" -> "Entregado";
            case "CANCELLED" -> "Cancelado";
            case "DELAYED" -> "Retrasado";
            default -> status;
        };
    }

    /**
     * Configura el icono y descripción según el estado
     */
    private void configurarVisualizacionEstado(String status) {
        switch (status.toUpperCase()) {
            case "REQUESTED":
                iconoEstadoLabel.setText("📝");
                descripcionEstadoLabel.setText("Tu envío ha sido solicitado y está siendo procesado");
                estadoLabel.setStyle("-fx-text-fill: #ff9800;");
                break;

            case "PENDING":
                iconoEstadoLabel.setText("⏳");
                descripcionEstadoLabel.setText("Tu envío está pendiente de asignación");
                estadoLabel.setStyle("-fx-text-fill: #ff9800;");
                break;

            case "IN_TRANSIT":
                iconoEstadoLabel.setText("🚚");
                descripcionEstadoLabel.setText("Tu envío está en camino");
                estadoLabel.setStyle("-fx-text-fill: #2196F3;");
                break;

            case "DELIVERED":
                iconoEstadoLabel.setText("✅");
                descripcionEstadoLabel.setText("Tu envío ha sido entregado exitosamente");
                estadoLabel.setStyle("-fx-text-fill: #4CAF50;");
                break;

            case "CANCELLED":
                iconoEstadoLabel.setText("❌");
                descripcionEstadoLabel.setText("Tu envío ha sido cancelado");
                estadoLabel.setStyle("-fx-text-fill: #f44336;");
                break;

            case "DELAYED":
                iconoEstadoLabel.setText("⚠️");
                descripcionEstadoLabel.setText("Tu envío presenta un retraso");
                estadoLabel.setStyle("-fx-text-fill: #FF5722;");
                break;

            default:
                iconoEstadoLabel.setText("📍");
                descripcionEstadoLabel.setText("Estado desconocido");
                estadoLabel.setStyle("-fx-text-fill: #666666;");
                break;
        }
    }

    /**
     * Cierra la ventana del diálogo
     */
    @FXML
    private void handleCerrar() {
        Stage stage = (Stage) cerrarButton.getScene().getWindow();
        stage.close();
    }
}