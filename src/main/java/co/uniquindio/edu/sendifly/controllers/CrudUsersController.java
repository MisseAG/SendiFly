package co.uniquindio.edu.sendifly.controllers;

import co.uniquindio.edu.sendifly.dtos.UserDTO;
import co.uniquindio.edu.sendifly.models.Person;
import co.uniquindio.edu.sendifly.models.User;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import co.uniquindio.edu.sendifly.models.User;
import co.uniquindio.edu.sendifly.repositories.PersonRepository;
import java.util.Optional;

public class CrudUsersController {

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnCrear;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableView<UserDTO> tablaUsuarios;

    @FXML
    private TableColumn<UserDTO, String> colCorreo;

    @FXML
    private TableColumn<UserDTO, String> colId;

    @FXML
    private TableColumn<UserDTO, String> colNombreCompleto;

    @FXML
    private TableColumn<UserDTO, String> colTelefono;

    @FXML
    private Label lblRegistroSeleccionado;

    @FXML
    private Label lblTotalRegistros;

    @FXML
    private TextField txtBuscar;

    private ObservableList<UserDTO> userObservable = FXCollections.observableArrayList();
    private PersonRepository personRepository;


    @FXML
    public void initialize() {
        personRepository = PersonRepository.getInstance();
        configurarTabla();
        cargarClientes();

        //modificacion de inicializacion para editar men
        tablaUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            btnActualizar.setDisable(newSel == null);
        });
        //hasta aquí
        //y esta parte es para seleccionar y eliminar
        tablaUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            btnEliminar.setDisable(newSel == null);
        });
        //hasta aquí con H
    }

    private void configurarTabla() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        colNombreCompleto.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("email"));

        tablaUsuarios.setItems(userObservable);
    }

    private void cargarClientes() {
        userObservable.clear();
        List<Person> personas = personRepository.getAll();
        for (Person person : personas) {
            if (person instanceof User) {
                UserDTO dto = UserDTO.fromUser((User) person);
                userObservable.add(dto);
            }
        }
    }

    @FXML
    void handleActualizar(ActionEvent event) {
        UserDTO seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            System.out.println("No se ha seleccionado ningún usuario para editar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/uniquindio/edu/sendifly/views/admin/CreateUser.fxml"));
            Parent root = loader.load();

            CreateUserController controller = loader.getController();
            controller.setUserObservable(userObservable);
            controller.setUserToEdit(seleccionado); // Cargar datos en el formulario

            Stage stage = new Stage();
            stage.setTitle("Editar usuario");
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
        if (resultado.isPresent() && resultado.get() instanceof User) {
            User usuario = (User) resultado.get();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/uniquindio/edu/sendifly/views/admin/ShowUser.fxml"));
                Parent root = loader.load();

                ShowUserController controller = loader.getController();
                controller.setUser(usuario);

                Stage stage = new Stage();
                stage.setTitle("Detalles del Usuario");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("No se encontró ningún usuario con ese ID.");
        }
    }


    @FXML
    void handleCrear(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/uniquindio/edu/sendifly/views/admin/CreateUser.fxml"));
            Parent root = loader.load();

            CreateUserController controller = loader.getController();
            controller.setUserObservable(userObservable); // Conectamos la tabla

            Stage stage = new Stage();
            stage.setTitle("Crear nuevo usuario");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar la interfaz de creación de usuario: " + e.getMessage());
        }
    }

    @FXML
    void handleEliminar(ActionEvent event) {
        UserDTO seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            System.out.println("No se ha seleccionado ningún usuario para eliminar.");
            return;
        }

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmar eliminación");
        alerta.setHeaderText("¿Estás seguro de que deseas eliminar este usuario?");
        alerta.setContentText("Usuario: " + seleccionado.getName());

        Optional<ButtonType> resultado = alerta.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            // Buscar y eliminar del repositorio
            User user = (User) PersonRepository.getInstance().findByEmail(seleccionado.getEmail());
            if (user != null) {
                PersonRepository.getInstance().removePerson(user);
                userObservable.remove(seleccionado);
            }
        }
    }

    @FXML private TextField idSearchField;
    @FXML private Label buscarErrorLabel;

}