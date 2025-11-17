package co.uniquindio.edu.sendifly.controllers;

import co.uniquindio.edu.sendifly.dtos.ShipmentDTO;
import co.uniquindio.edu.sendifly.models.Shipment;
import co.uniquindio.edu.sendifly.services.ShipmentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class CrudShipmentsController {

    @FXML private Button btnActualizar;
    @FXML private Button btnBuscar;
    @FXML private Button btnCrear;
    @FXML private Button btnEliminar;

    @FXML private TableView<ShipmentDTO> tablaEnvios;
    @FXML private TableColumn<ShipmentDTO, String> colId;
    @FXML private TableColumn<ShipmentDTO, String> colOrigen;
    @FXML private TableColumn<ShipmentDTO, String> colDestino;
    @FXML private TableColumn<ShipmentDTO, String> colEstado;
    @FXML private TableColumn<ShipmentDTO, String> colFechaPedido;
    @FXML private TableColumn<ShipmentDTO, String> colFechaEntrega;
    @FXML private TableColumn<ShipmentDTO, Double> colPrecio;

    @FXML private Label lblRegistroSeleccionado;
    @FXML private Label lblTotalRegistros;
    @FXML private TextField txtBuscar;

    private ObservableList<ShipmentDTO> shipmentObservable = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarTabla();
        cargarEnvios();

        tablaEnvios.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            btnActualizar.setDisable(newSel == null);
            btnEliminar.setDisable(newSel == null);
        });
    }

    private void configurarTabla() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colOrigen.setCellValueFactory(new PropertyValueFactory<>("originAlias"));
        colDestino.setCellValueFactory(new PropertyValueFactory<>("destinationAlias"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("status"));
        colFechaPedido.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colFechaEntrega.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("price"));

        tablaEnvios.setItems(shipmentObservable);
    }

    private void cargarEnvios() {
        shipmentObservable.clear();
        ShipmentService.getInstance().getAllShipments().stream()
                .map(ShipmentDTO::fromShipment)
                .forEach(shipmentObservable::add);
        actualizarContador();
    }

    @FXML
    void handleActualizar(ActionEvent event) {
        ShipmentDTO seleccionado = tablaEnvios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/uniquindio/edu/sendifly/views/admin/CreateShipmentAdmin.fxml"));
            Parent root = loader.load();

            CreateShipmentAdminController controller = loader.getController();
            controller.setShipmentObservable(shipmentObservable);
            controller.setShipmentToEdit(seleccionado);
            controller.setOnShipmentChange(this::actualizarContador);

            Stage stage = new Stage();
            stage.setTitle("Editar envío");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleBuscar() {
        String id = txtBuscar.getText().trim();
        if (id.isEmpty()) return;

        try {
            Shipment shipment = ShipmentService.getInstance().getShipment(id);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/uniquindio/edu/sendifly/views/admin/ShowShipment.fxml"));
            Parent root = loader.load();

            ShowShipmentController controller = loader.getController();
            controller.setShipment(shipment);

            Stage stage = new Stage();
            stage.setTitle("Detalles del Envío");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            System.out.println("No se encontró el envío: " + e.getMessage());
        }
    }

    @FXML
    void handleCrear(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/uniquindio/edu/sendifly/views/admin/CreateShipmentAdmin.fxml"));
            Parent root = loader.load();
            CreateShipmentAdminController controller = loader.getController();
            Stage stage = new Stage();
            stage.setTitle("Crear Envío");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("No se pudo cargar el formulario de creación.");
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    void handleEliminar(ActionEvent event) {
        ShipmentDTO seleccionado = tablaEnvios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) return;

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmar eliminación");
        alerta.setHeaderText("¿Eliminar este envío?");
        alerta.setContentText("ID: " + seleccionado.getId());

        Optional<ButtonType> resultado = alerta.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                ShipmentService.getInstance().deleteShipment(seleccionado.getId());
                shipmentObservable.remove(seleccionado);
                actualizarContador();
            } catch (Exception e) {
                System.out.println("Error al eliminar: " + e.getMessage());
            }
        }
    }

    private void actualizarContador() {
        int total = shipmentObservable.size();
        lblTotalRegistros.setText("Total de registros: " + total);
    }
}
