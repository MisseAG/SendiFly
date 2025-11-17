package co.uniquindio.edu.sendifly.controllers;

import co.uniquindio.edu.sendifly.models.Shipment;
import co.uniquindio.edu.sendifly.models.AdditionalServices.*;
import co.uniquindio.edu.sendifly.models.ShippingStatus.*;
import co.uniquindio.edu.sendifly.services.ShipmentService;
import co.uniquindio.edu.sendifly.session.SessionManager;
import co.uniquindio.edu.sendifly.utils.NavigationUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class CreateShipmentController {

    @FXML
    private Button backButton;

    @FXML
    private Button calcularCotizacionBtn;

    @FXML
    private Button crearEnvioBtn;

    @FXML
    private ComboBox<String> origenComboBox;

    @FXML
    private ComboBox<String> destinoComboBox;

    @FXML
    private TextField pesoTextField;

    @FXML
    private TextField volumenTextField;

    @FXML
    private ComboBox<AdditionalService> servicioAdicionalComboBox;

    @FXML
    private VBox resultadoVBox;

    @FXML
    private Label precioLabel;

    @FXML
    private Label rutaLabel;

    @FXML
    private Label dimensionesLabel;

    @FXML
    private Label distanciaLabel;

    @FXML
    private Label entregaLabel;

    @FXML
    private Button nuevaCotizacionButton;

    private final ShipmentService shipmentService = ShipmentService.getInstance();
    private final SessionManager sessionManager = SessionManager.getInstance();

    @FXML
    void initialize() {
        try {
            // Verificar que hay usuario logueado
            if (!sessionManager.isLoggedIn()) {
                showError("Sesión no iniciada", "Debes iniciar sesión primero");
                handleBack(null);
                return;
            }

            // Inicializar ComboBox de direcciones con los alias del usuario
            initializeAddressComboBoxes();

            // Inicializar ComboBox de servicios adicionales
            initializeServiceComboBox();

            // Ocultar resultados inicialmente
            resultadoVBox.setVisible(false);
            resultadoVBox.setManaged(false);
            crearEnvioBtn.setDisable(true);

            // Listeners para recálculo automático
            setupListeners();

        } catch (IllegalStateException e) {
            showError("Error de sesión", e.getMessage());
            handleBack(null);
        } catch (Exception e) {
            showError("Error al inicializar", e.getMessage());
        }
    }

    /**
     * Inicializa los ComboBox de direcciones con los alias del usuario actual
     */
    private void initializeAddressComboBoxes() {
        List<String> addressAliases = shipmentService.getCurrentUserAddressAliases();

        if (addressAliases.isEmpty()) {
            showWarning("Sin direcciones",
                    "No tienes direcciones registradas. Por favor, agrega direcciones primero.");
            crearEnvioBtn.setDisable(true);
            calcularCotizacionBtn.setDisable(true);
            return;
        }

        origenComboBox.getItems().addAll(addressAliases);
        destinoComboBox.getItems().addAll(addressAliases);

        // Configurar texto cuando no hay selección
        origenComboBox.setPromptText("Selecciona origen");
        destinoComboBox.setPromptText("Selecciona destino");
    }

    /**
     * Inicializa el ComboBox de servicios adicionales
     */
    private void initializeServiceComboBox() {
        servicioAdicionalComboBox.getItems().addAll(
                new None(),
                new Fragile(),
                new Secure(),
                new Priority(),
                new SignatureRequired()
        );

        servicioAdicionalComboBox.setConverter(new StringConverter<AdditionalService>() {
            @Override
            public String toString(AdditionalService service) {
                return service == null ? "" : service.getName();
            }

            @Override
            public AdditionalService fromString(String string) {
                return null;
            }
        });

        servicioAdicionalComboBox.setPromptText("Selecciona servicio");
        servicioAdicionalComboBox.getSelectionModel().selectFirst(); // None por defecto
    }

    /**
     * Configura listeners para recálculo automático
     */
    private void setupListeners() {
        origenComboBox.valueProperty().addListener((obs, old, newVal) -> recalcularSiCompleto());
        destinoComboBox.valueProperty().addListener((obs, old, newVal) -> recalcularSiCompleto());
        pesoTextField.textProperty().addListener((obs, old, newVal) -> recalcularSiCompleto());
        volumenTextField.textProperty().addListener((obs, old, newVal) -> recalcularSiCompleto());
        servicioAdicionalComboBox.valueProperty().addListener((obs, old, newVal) -> recalcularSiCompleto());
    }

    @FXML
    void handleCotizar(ActionEvent event) {
        if (!validarCampos()) {
            showWarning("Campos incompletos", "Por favor completa todos los campos correctamente");
            return;
        }

        try {
            // Obtener datos del formulario
            String originAlias = origenComboBox.getValue();
            String destinationAlias = destinoComboBox.getValue();
            float peso = Float.parseFloat(pesoTextField.getText());
            float volumen = Float.parseFloat(volumenTextField.getText());
            AdditionalService servicio = servicioAdicionalComboBox.getValue();

            // Validar que origen y destino sean diferentes
            if (originAlias.equals(destinationAlias)) {
                showWarning("Direcciones iguales", "El origen y destino deben ser diferentes");
                return;
            }

            // Obtener detalles de cotización
            ShipmentService.QuotationDetails details = shipmentService.getQuotationDetails(peso, volumen, servicio, originAlias, destinationAlias);

            // Mostrar resultados
            mostrarResultados(details, originAlias, destinationAlias, peso, volumen);

            // Habilitar creación
            crearEnvioBtn.setDisable(false);

        } catch (NumberFormatException e) {
            showError("Datos inválidos", "Peso y volumen deben ser números válidos");
        } catch (IllegalArgumentException e) {
            showError("Error de validación", e.getMessage());
        } catch (Exception e) {
            showError("Error al cotizar", e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleCrear(ActionEvent event) {
        if (!validarCampos()) {
            showWarning("Campos incompletos", "Verifica los datos antes de crear el envío");
            return;
        }

        try {
            // Obtener datos del formulario
            String originAlias = origenComboBox.getValue();
            String destinationAlias = destinoComboBox.getValue();
            float peso = Float.parseFloat(pesoTextField.getText());
            float volumen = Float.parseFloat(volumenTextField.getText());
            AdditionalService servicio = servicioAdicionalComboBox.getValue();

            // Validar que origen y destino sean diferentes
            if (originAlias.equals(destinationAlias)) {
                showWarning("Direcciones iguales", "El origen y destino deben ser diferentes");
                return;
            }

            // Crear el envío usando alias de direcciones
            Shipment shipment = shipmentService.createShipment(
                    originAlias,
                    destinationAlias,
                    servicio,
                    new Requested(), // Estado inicial: Solicitado
                    peso,
                    volumen
            );

            // Mostrar confirmación
            showSuccess("Envío creado exitosamente",
                    "ID del envío: " + shipment.getId() +
                            "\nPrecio: " + String.format("%.2f", shipment.getPrice()));

            // Limpiar formulario
            limpiarFormulario();

        } catch (NumberFormatException e) {
            showError("Datos inválidos", "Peso y volumen deben ser números válidos");
        } catch (IllegalArgumentException e) {
            showError("Error de validación", e.getMessage());
        } catch (Exception e) {
            showError("Error al crear envío", e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleNuevaCotizacion(ActionEvent event) {
        limpiarFormulario();
    }

    @FXML
    void handleBack(ActionEvent event) {
        String path = "/co/uniquindio/edu/sendifly/views/ViewUser.fxml";
        String title = "SendiFly - Panel de Usuario";
        NavigationUtil.navigateToScene(backButton, path, title);
    }

    @FXML
    // ========== MÉTODOS PRIVADOS ==========

    private void recalcularSiCompleto() {
        if (validarCampos()) {
            handleCotizar(null);
        } else {
            // Ocultar resultados si los campos no están completos
            resultadoVBox.setVisible(false);
            resultadoVBox.setManaged(false);
            crearEnvioBtn.setDisable(true);
        }
    }

    private boolean validarCampos() {
        try {
            // Validar ComboBoxes
            if (origenComboBox.getValue() == null || origenComboBox.getValue().isEmpty()) {
                return false;
            }
            if (destinoComboBox.getValue() == null || destinoComboBox.getValue().isEmpty()) {
                return false;
            }
            if (servicioAdicionalComboBox.getValue() == null) {
                return false;
            }

            // Validar campos numéricos
            if (pesoTextField.getText().isEmpty() || volumenTextField.getText().isEmpty()) {
                return false;
            }

            // Intentar parsear los números
            Float.parseFloat(pesoTextField.getText());
            Float.parseFloat(volumenTextField.getText());

            return true;

        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void mostrarResultados(ShipmentService.QuotationDetails details,
                                   String origin, String destination,
                                   float peso, float volumen) {
        // Formatear fecha de entrega
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String fechaEntrega = details.getEstimatedDelivery().format(formatter);

        // Actualizar labels
        precioLabel.setText(details.getFormattedPrice());
        rutaLabel.setText(origin + " → " + destination);
        dimensionesLabel.setText(String.format("Peso: %.2f kg | Volumen: %.2f m³", peso, volumen));
        distanciaLabel.setText(details.getFormattedDistance());
        entregaLabel.setText("Entrega estimada: " + fechaEntrega);

        // Mostrar VBox de resultados
        resultadoVBox.setVisible(true);
        resultadoVBox.setManaged(true);
    }

    private void limpiarFormulario() {
        origenComboBox.getSelectionModel().clearSelection();
        destinoComboBox.getSelectionModel().clearSelection();
        pesoTextField.clear();
        volumenTextField.clear();
        servicioAdicionalComboBox.getSelectionModel().selectFirst(); // Volver a None

        resultadoVBox.setVisible(false);
        resultadoVBox.setManaged(false);
        crearEnvioBtn.setDisable(true);
    }

    // ========== MÉTODOS DE ALERTAS ==========

    private void showSuccess(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}