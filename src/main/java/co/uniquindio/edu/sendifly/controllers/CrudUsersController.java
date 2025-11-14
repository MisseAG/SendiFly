package co.uniquindio.edu.sendifly.controllers;

import co.uniquindio.edu.sendifly.dtos.UserDTO;
import co.uniquindio.edu.sendifly.models.Person;
import co.uniquindio.edu.sendifly.models.User;
import co.uniquindio.edu.sendifly.repositories.PersonRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

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

    }

    @FXML
    void handleBuscar(ActionEvent event) {

    }

    @FXML
    void handleCrear(ActionEvent event) {

    }

    @FXML
    void handleEliminar(ActionEvent event) {

    }

}