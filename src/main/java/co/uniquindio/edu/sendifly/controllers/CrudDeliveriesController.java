package co.uniquindio.edu.sendifly.controllers;

import co.uniquindio.edu.sendifly.dtos.DeliveryManDTO;
import co.uniquindio.edu.sendifly.models.DeliveryMan;
import co.uniquindio.edu.sendifly.models.Person;
import co.uniquindio.edu.sendifly.repositories.PersonRepository;
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
import java.util.List;
import java.util.Optional;

public class CrudDeliveriesController {

    @FXML private Button btnActualizar;
    @FXML private Button btnBuscar;
    @FXML private Button btnCrear;
    @FXML private Button btnEliminar;

    @FXML private TableView<DeliveryManDTO> tablaRepartidores;
    @FXML private TableColumn<DeliveryManDTO, String> colCorreo;
    @FXML private TableColumn<DeliveryManDTO, String> colId;
    @FXML private TableColumn<DeliveryManDTO, String> colNombreCompleto;
    @FXML private TableColumn<DeliveryManDTO, String> colTelefono;

    @FXML private Label lblRegistroSeleccionado;
    @FXML private Label lblTotalRegistros;
    @FXML private TextField txtBuscar;

    private ObservableList<DeliveryManDTO> deliveryObservable = FXCollections.observableArrayList();
    private PersonRepository personRepository;

    @FXML
    public void initialize() {
        personRepository = PersonRepository.getInstance();
        configurarTabla();
        cargarRepartidores();

        tablaRepartidores.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            btnActualizar.setDisable(newSel == null);
            btnEliminar.setDisable(newSel == null);
        });
    }

    private void configurarTabla() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idDeliveryMan"));
        colNombreCompleto.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("email"));

        tablaRepartidores.setItems(deliveryObservable);
    }

    private void cargarRepartidores() {
        deliveryObservable.clear();
        List<Person> personas = personRepository.getAll();
        for (Person person : personas) {
            if (person instanceof DeliveryMan) {
                DeliveryManDTO dto = DeliveryManDTO.fromDeliveryMan((DeliveryMan) person);
                deliveryObservable.add(dto);
            }
        }
        actualizarContadorDomiciliarios();
    }

    @FXML
    void handleActualizar(ActionEvent event) {
        DeliveryManDTO seleccionado = tablaRepartidores.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            System.out.println("No se ha seleccionado ningún domiciliario para editar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/uniquindio/edu/sendifly/views/admin/CreateDelivery.fxml"));
            Parent root = loader.load();

            CreateDeliveryController controller = loader.getController();
            controller.setDeliveryObservable(deliveryObservable);
            controller.setDeliveryToEdit(seleccionado);

            Stage stage = new Stage();
            stage.setTitle("Editar domiciliario");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al abrir el formulario de edición: " + e.getMessage());
        }
    }

    @FXML
    private void handleBuscar() {
        String id = txtBuscar.getText().trim();

        if (id.isEmpty()) {
            System.out.println("Debe ingresar un ID.");
            return;
        }

        Optional<Person> resultado = PersonRepository.getInstance().getPerson(id);
        if (resultado.isPresent() && resultado.get() instanceof DeliveryMan) {
            DeliveryMan deliveryMan = (DeliveryMan) resultado.get();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/uniquindio/edu/sendifly/views/admin/ShowDelivery.fxml"));
                Parent root = loader.load();

                ShowDeliveryController controller = loader.getController();
                controller.setDeliveryMan(deliveryMan);

                Stage stage = new Stage();
                stage.setTitle("Detalles del Domiciliario");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("No se encontró ningún domiciliario con ese ID.");
        }
    }

    @FXML
    void handleCrear(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/uniquindio/edu/sendifly/views/admin/CreateDelivery.fxml"));
            Parent root = loader.load();

            CreateDeliveryController controller = loader.getController();
            controller.setDeliveryObservable(deliveryObservable);
            controller.setOnDeliveryChange(() -> actualizarContadorDomiciliarios());

            Stage stage = new Stage();
            stage.setTitle("Crear nuevo domiciliario");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar la interfaz de creación de domiciliario: " + e.getMessage());
        }
    }

    @FXML
    void handleEliminar(ActionEvent event) {
        DeliveryManDTO seleccionado = tablaRepartidores.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            System.out.println("No se ha seleccionado ningún domiciliario para eliminar.");
            return;
        }

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmar eliminación");
        alerta.setHeaderText("¿Estás seguro de que deseas eliminar este domiciliario?");
        alerta.setContentText("Domiciliario: " + seleccionado.getName());

        Optional<ButtonType> resultado = alerta.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            DeliveryMan deliveryMan = (DeliveryMan) PersonRepository.getInstance().findByEmail(seleccionado.getEmail());
            if (deliveryMan != null) {
                PersonRepository.getInstance().removePerson(deliveryMan);
                deliveryObservable.remove(seleccionado);
                actualizarContadorDomiciliarios();
            }
        }
    }

    private void actualizarContadorDomiciliarios() {
        int total = deliveryObservable.size();
        lblTotalRegistros.setText("Total de registros: " + total);
    }
}