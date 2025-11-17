package co.uniquindio.edu.sendifly.controllers;

import co.uniquindio.edu.sendifly.controllers.TrackShipmentController;
import co.uniquindio.edu.sendifly.dtos.ShipmentTrackDTO;
import co.uniquindio.edu.sendifly.models.Shipment;
import co.uniquindio.edu.sendifly.models.User;
import co.uniquindio.edu.sendifly.services.ShipmentService;
import co.uniquindio.edu.sendifly.session.SessionManager;
import co.uniquindio.edu.sendifly.utils.NavigationUtil;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ViewUserController implements Initializable {


    @FXML
    private Button createShipmentButton;

    @FXML
    private Button downloadReportsButton;

    @FXML
    private Button historyButton;

    @FXML
    private MenuItem logoutMenuItem;

    @FXML
    private Button newQuoteButton;

    @FXML
    private MenuItem profileMenuItem;

    @FXML
    private Button trackButton;

    @FXML
    private TextField trackingTextField;

    @FXML
    private Label userGreetingLabel;

    @FXML
    private Button paymentMethodsBtn;

    @FXML
    private Button addressesBtn;

    @FXML
    private Button paymentsHistorialBtn;

    @FXML
    private TableView<Shipment> tablaEnvios;

    @FXML
    private TableColumn<Shipment, String> colIdEnvio;

    @FXML
    private TableColumn<Shipment, String> colOrigen;

    @FXML
    private TableColumn<Shipment, String> colDestino;

    @FXML
    private TableColumn<Shipment, Float> colPeso;

    @FXML
    private TableColumn<Shipment, Double> colTarifa;

    @FXML
    private TableColumn<Shipment, Void> colAccionModificar;

    @FXML
    private TableColumn<Shipment, Void> colAccionCancelar;

    @FXML
    private Label noEnviosLabel;

    private SessionManager sessionManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sessionManager = SessionManager.getInstance();
        actualizarSaludo();
        configurarTabla();
        cargarEnviosPendientes();
    }

    private void cargarEnviosPendientes(){
        User currentUser = sessionManager.getCurrentUser();
        List<Shipment> enviosPendientes = currentUser.getShipmentsList().stream()
                .filter(s -> "REQUESTED".equals(s.getShippingStatus().getName()))
                .collect(Collectors.toList());

        if (enviosPendientes.isEmpty()){
            noEnviosLabel.setVisible(true);
            tablaEnvios.setVisible(false);
        } else {
            noEnviosLabel.setVisible(false);
            tablaEnvios.setVisible(true);
            tablaEnvios.getItems().setAll(enviosPendientes);
        }
    }

    private void agregarBotonesAccion() {
        // Botón Modificar
        colAccionModificar.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("✏");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    btn.setOnAction(e -> {
                        Shipment shipment = getTableView().getItems().get(getIndex());
                        modificarEnvio(shipment);
                    });
                    setGraphic(btn);
                }
            }
        });

        // Botón Cancelar
        colAccionCancelar.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("✕");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    btn.setOnAction(e -> {
                        Shipment shipment = getTableView().getItems().get(getIndex());
                        cancelarEnvio(shipment);
                    });
                    setGraphic(btn);
                }
            }
        });
    }

    private void configurarTabla(){
        colIdEnvio.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId()));
        colOrigen.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getOrigin().getAlias()));
        colDestino.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDestination().getAlias()));
        colPeso.setCellValueFactory(data -> new SimpleFloatProperty(data.getValue().getPack().getWeight()).asObject());
        colTarifa.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());

        // Botones de acción
        agregarBotonesAccion();

    }

    private void modificarEnvio(Shipment shipment) {
        System.out.println("Modificar envío: " + shipment.getId());
        String title = "SendiFly - Modificar envío";
        String path = "/co/uniquindio/edu/sendifly/views/user/CreareShipmentView.fxml";
    }

    private void cancelarEnvio(Shipment shipment) {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Cancelar envío");
        confirmacion.setContentText("¿Estás seguro de cancelar el envío " + shipment.getId() + "?");

        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                ShipmentService.getInstance().deleteShipment(shipment.getId());
                cargarEnviosPendientes(); // Refrescar tabla
            }
        });
    }

    @FXML
    private void handleProfileSettings(ActionEvent event) {
        System.out.println("Configuración de perfil clicada");
        String path = "/co/uniquindio/edu/sendifly/views/user/ViewProfileConfig.fxml";
        String title = "SendiFly-Configurar Perfil";
        Node node = userGreetingLabel;
        NavigationUtil.navigateToScene(node, path, title);
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        String path = "/co/uniquindio/edu/sendifly/views/MainView.fxml";
        String title = "SendiFly-Home";
        //Usa cualquier node
        Node node = userGreetingLabel;
        NavigationUtil.navigateToScene(node, path, title, 400, 600);
    }

    @FXML
    private void handleNewQuote(ActionEvent event) {
        System.out.println("Nueva cotización");
        String path = "/co/uniquindio/edu/sendifly/views/user/CreateShipmentView.fxml";
        String title = "SendiFly-Envío";
        //Usa cualquier node
        Node node = userGreetingLabel;
        NavigationUtil.navigateToScene(node, path, title, 400, 600);
    }

    @FXML
    private void handleCreateShipment(ActionEvent event) {
        System.out.println("Crear envío");
        String path = "/co/uniquindio/edu/sendifly/views/user/CreateShipmentView.fxml";
        String title = "SendiFly-Envío";
        //Usa cualquier node
        Node node = userGreetingLabel;
        NavigationUtil.navigateToScene(node, path, title,400 ,600);
    }

    @FXML
    private void handleTrackShipment(ActionEvent event) {
        String shipmentId = trackingTextField.getText().trim();

        if (shipmentId.isEmpty()) {
            showAlert(Alert.AlertType.WARNING,"Campo vacío", "Ingresa un ID de envío");
            return;
        }

        try {
            ShipmentService shipmentService = ShipmentService.getInstance();
            Shipment shipment = shipmentService.getShipment(shipmentId);

            // Crear DTO
            ShipmentTrackDTO dto = new ShipmentTrackDTO(
                    shipment.getId(),
                    shipment.getShippingStatus().getName()
            );

            // Mostrar modal
            TrackShipmentController controller = NavigationUtil.showDialog(
                    "/co/uniquindio/edu/sendifly/views/user/TrackShipmentDialog.fxml",
                    "Rastrear Envío"
            );

            if (controller != null) {
                controller.setShipmentData(dto);
            }

        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.WARNING,"Envío no encontrado",
                    "No existe un envío con ID: " + shipmentId);
        }
    }



    private void showAlert(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void handlePaymentMethods(ActionEvent event){
        String path = "/co/uniquindio/edu/sendifly/views/user/PaymentMethondsView.fxml";
        String title = "SendiFly-FormasDePago";
        //Usa cualquier node
        Node node = userGreetingLabel;
        NavigationUtil.navigateToScene(node, path, title, 400, 600);
    }

    @FXML
    private void handleAddresses(ActionEvent event){
        try {
            String path = "/co/uniquindio/edu/sendifly/views/user/AddressesView.fxml";
            String title = "SendiFly-Direcciones";
            Node node = userGreetingLabel;

            // Cargar el FXML manualmente para obtener el controlador
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();

            // Obtener el controlador
            AddressManagementController controller = loader.getController();

            // Establecer el usuario actual - USAR EL ID EN LUGAR DEL EMAIL
            if (sessionManager.isLoggedIn()) {
                User currentUser = sessionManager.getCurrentUser();
                String userId = currentUser.getId(); // Usar el ID en lugar del email
                controller.setCurrentUser(userId);
                System.out.println("Usuario establecido en controlador de direcciones: " + userId);
            }

            // Cambiar la escena
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = new Scene(root, 400, 600);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();

        } catch (Exception e) {
            System.out.println("Error navegando a direcciones: " + e.getMessage());
            e.printStackTrace();}
    }


    @FXML
    private void handlePaymentsHistorial (ActionEvent event){
        String path = "/co/uniquindio/edu/sendifly/views/user/PaymentMethods.fxml";
        String title = "SendiFly-Historial";
        //Usa cualquier node
        Node node = userGreetingLabel;
        NavigationUtil.navigateToScene(node, path, title, 400, 600);
    }

    @FXML
    private void handleDownloadReports(ActionEvent event) {
        System.out.println("Descargar reportes");
    }

    @FXML
    private void handleViewHistory(ActionEvent event) {
        System.out.println("Ver historial");
    }

    private void actualizarSaludo() {
        if (sessionManager.isLoggedIn()) {
            User currentUser = sessionManager.getCurrentUser();
            userGreetingLabel.setText("Hola, " + currentUser.getName());
        } else {
            userGreetingLabel.setText("Hola, Usuario");
        }
    }
}
