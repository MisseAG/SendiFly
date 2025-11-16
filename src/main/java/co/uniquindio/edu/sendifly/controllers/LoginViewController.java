package co.uniquindio.edu.sendifly.controllers;

import co.uniquindio.edu.sendifly.models.Administrator;
import co.uniquindio.edu.sendifly.models.DeliveryMan;
import co.uniquindio.edu.sendifly.models.Person;
import co.uniquindio.edu.sendifly.models.User;
import co.uniquindio.edu.sendifly.repositories.PersonRepository;
import co.uniquindio.edu.sendifly.services.PersonService;
import co.uniquindio.edu.sendifly.utils.NavigationUtil;
import co.uniquindio.edu.sendifly.session.SessionManager;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginViewController implements Initializable {

    @FXML
    private Button btnLogin;

    @FXML
    private ChoiceBox<String> choiceRole;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtEmail;

    private PersonRepository personRepository;
    private PersonService personService;
    private SessionManager sessionManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Inicializa choicebox
        choiceRole.getItems().addAll("Usuario", "Administrador", "Repartidor");
        choiceRole.setValue("Usuario"); // Valor por defecto

        personRepository = PersonRepository.getInstance();
        personService = PersonService.getInstance();
        sessionManager = SessionManager.getInstance();

    }

    //
    @FXML
    private void handleLogin(ActionEvent event) {
        // Obtener los datos ingresados
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText();
        String role = choiceRole.getValue();

        if (email.isEmpty() || password.isEmpty() || role == null) {
            mostrarAlerta(Alert.AlertType.WARNING,
                    "Campos incompletos",
                    "Por favor complete todos los campos");
            return;
        }

        Person person = validarCredenciales(email, password, role);
        if (person != null) {
            try {
                if (person instanceof User){
                    sessionManager.login((User) person);
                }
                redirigirSegunRol(role);

            } catch (IOException e) {
                mostrarAlerta(Alert.AlertType.ERROR,
                        "Error de navegación",
                        "No se pudo cargar la vista: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            mostrarAlerta(Alert.AlertType.ERROR,
                    "Credenciales incorrectas",
                    "Usuario o contraseña incorrectos");
            limpiarCampos();
        }
    }

    private Person validarCredenciales(String username, String password, String role) {
        try {

            System.out.println("Buscando: " + username + " | Pass: " + password + " | Role: " + role);
            // Buscar persona por email
            Person person = personRepository.findByEmail(username);

            System.out.println("Persona encontrada: " + (person != null ? person.getEmail() : "null"));

            // Verificar si existe
            if (person == null) {
                System.out.println("No se encontró usuario");
                return null;
            }

            // Verificar contraseña
            if (!person.getPassword().equals(password)) {
                System.out.println("Contraseña incorrecta");
                return null;
            }

            // Verificar rol
            boolean rolValido = verificarRol(person, role);
            System.out.println("Rol válido: " + rolValido);

            if (rolValido) {
                return person;
            }
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
