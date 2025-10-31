package co.uniquindio.edu.sendifly.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

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
    private TextField txtUsername;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Inicializar el ChoiceBox con roles
        choiceRole.getItems().addAll("Usuario", "Administrador", "Repartidor");
        choiceRole.setValue("Usuario"); // Valor por defecto

        // Configurar el evento del botón
        btnLogin.setOnAction(this::handleLogin);
    }

    /**
     * Maneja el proceso de login y redirige según el rol
     */
    @FXML
    private void handleLogin(ActionEvent event) {
        // Obtener los datos ingresados
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText();
        String role = choiceRole.getValue();

        // Validar que los campos no estén vacíos
        if (username.isEmpty() || password.isEmpty() || role == null) {
            mostrarAlerta(Alert.AlertType.WARNING,
                    "Campos incompletos",
                    "Por favor complete todos los campos");
            return;
        }

        // Validar credenciales
        if (validarCredenciales(username, password, role)) {
            try {
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

    /**
     * Valida las credenciales del usuario
     * Aquí puedes conectar con tu base de datos o lógica de negocio
     */
    private boolean validarCredenciales(String username, String password, String role) {
        // CREDENCIALES DE PRUEBA - Reemplaza esto con tu lógica real

        if (role.equals("Usuario")) {
            // Usuarios de prueba
            return (username.equals("user1") && password.equals("user123")) ||
                    (username.equals("usuario") && password.equals("1234"));
        }
        else if (role.equals("Administrador")) {
            // Administradores de prueba
            return (username.equals("admin") && password.equals("admin123")) ||
                    (username.equals("administrador") && password.equals("admin"));
        }

        return false;
    }

    /**
     * Redirige a la vista correspondiente según el rol
     */
    private void redirigirSegunRol(String role) throws IOException {
        String fxmlPath;
        String titulo;

        // Determinar qué vista cargar según el rol
        if (role.equals("Usuario")) {
            fxmlPath = "/co/uniquindio/edu/sendifly/views/viewUser.fxml";
            titulo = "SENDIFLY - Panel de Usuario";
        } else { // Administrador
            fxmlPath = "/co/uniquindio/edu/sendifly/views/viewAdmin.fxml";
            titulo = "SENDIFLY - Panel de Administrador";
        }

        // Cargar la nueva vista
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

        // Obtener el Stage actual
        Stage stage = (Stage) btnLogin.getScene().getWindow();

        // Crear nueva escena y mostrarla
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(titulo);
        stage.show();

        System.out.println("✅ Redirigido a: " + titulo);
    }

    /**
     * Muestra una alerta al usuario
     */
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Limpia los campos del formulario
     */
    private void limpiarCampos() {
        txtPassword.clear();
        txtUsername.clear();
        txtUsername.requestFocus();
    }
}
