package co.uniquindio.edu.sendifly.controllers;

import co.uniquindio.edu.sendifly.models.Administrator;
import co.uniquindio.edu.sendifly.models.DeliveryMan;
import co.uniquindio.edu.sendifly.models.Person;
import co.uniquindio.edu.sendifly.models.User;
import co.uniquindio.edu.sendifly.repositories.PersonRepository;
import co.uniquindio.edu.sendifly.services.PersonService;
import co.uniquindio.edu.sendifly.utils.NavigationUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginViewController implements Initializable {

    public static Person usuarioActual;

    @FXML private Button btnLogin;
    @FXML private ChoiceBox<String> choiceRole;
    @FXML private PasswordField txtPassword;
    @FXML private TextField txtEmail;

    private PersonRepository personRepository;
    private PersonService personService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceRole.getItems().addAll("Usuario", "Administrador", "Repartidor");
        choiceRole.setValue("Usuario");
        personRepository = PersonRepository.getInstance();
        personService = PersonService.getInstance();
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText();
        String role = choiceRole.getValue();

        if (email.isEmpty() || password.isEmpty() || role == null) {
            mostrarAlerta(Alert.AlertType.WARNING,"Campos incompletos","Por favor complete todos los campos");
            return;
        }

        if (validarCredenciales(email, password, role)) {
            // Guardar usuario logueado
            usuarioActual = personRepository.findByEmail(email);

            try {
                redirigirSegunRol(role);
            } catch (IOException e) {
                mostrarAlerta(Alert.AlertType.ERROR,"Error de navegación","No se pudo cargar la vista: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            mostrarAlerta(Alert.AlertType.ERROR,"Credenciales incorrectas","Usuario o contraseña incorrectos");
            limpiarCampos();
        }
    }

    private boolean validarCredenciales(String email, String password, String role) {
        Person person = personRepository.findByEmail(email);
        if (person == null) return false;
        if (!person.getPassword().equals(password)) return false;
        return verificarRol(person, role);
    }

    private boolean verificarRol(Person person, String role) {
        return switch (role) {
            case "Usuario" -> person instanceof User;
            case "Administrador" -> person instanceof Administrator;
            case "Repartidor" -> person instanceof DeliveryMan;
            default -> false;
        };
    }

    private void redirigirSegunRol(String role) throws IOException {
        NavigationUtil.navigateByRole(btnLogin, role);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void limpiarCampos() {
        txtPassword.clear();
        txtEmail.clear();
        txtEmail.requestFocus();
    }

    @FXML
    public void handleBackToHome(ActionEvent actionEvent) {
        String path = "/co/uniquindio/edu/sendifly/views/MainView.fxml";
        String title = "SendiFly-Login";
        NavigationUtil.navigateToScene(btnLogin, path, title);
    }
}
