package co.uniquindio.edu.sendifly.controllers;

import co.uniquindio.edu.sendifly.models.User;
import co.uniquindio.edu.sendifly.services.PersonService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ViewProfileConfigController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private javafx.scene.control.Button btnBack;

    // Supongamos que tienes un usuario logueado
    private User usuarioActual;

    private final PersonService personService = PersonService.getInstance();

    // Método para inicializar la vista y cargar datos del usuario
    @FXML
    public void initialize() {
        // Aquí debes asignar usuarioActual según tu lógica de login
        // Por ejemplo: usuarioActual = LoginController.getUsuarioLogueado();
        // Para pruebas, tomamos el primer usuario tipo User
        Optional<User> userOpt = personService.getUserById("1982822057"); // o ID dinámico
        userOpt.ifPresent(user -> usuarioActual = user);

        if (usuarioActual != null) {
            nameField.setText(usuarioActual.getName());
            emailField.setText(usuarioActual.getEmail());
            passwordField.setText(usuarioActual.getPassword());
        }
    }

    @FXML
    private void onChangeName() {
        if (usuarioActual != null) {
            usuarioActual.setName(nameField.getText());
            mostrarAlerta("Nombre actualizado correctamente");
        }
    }

    @FXML
    private void onChangeEmail() {
        if (usuarioActual != null) {
            usuarioActual.setEmail(emailField.getText());
            mostrarAlerta("Email actualizado correctamente");
        }
    }

    @FXML
    private void onChangePassword() {
        if (usuarioActual != null && !passwordField.getText().isEmpty()) {
            usuarioActual.setPassword(passwordField.getText());
            mostrarAlerta("Contraseña actualizada correctamente");
        }
    }

    @FXML
    private void onBackToUserView() {
        cambiarEscena("/co/uniquindio/edu/sendifly/views/ViewUser.fxml");
    }

    // Método para cambiar de escena
    private void cambiarEscena(String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();

            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.show();

            System.out.println("✔ Navegación exitosa a: " + rutaFXML);
        } catch (IOException e) {
            System.err.println("❌ Error al cargar la vista: " + rutaFXML);
            e.printStackTrace();
        }
    }

    // Mostrar alerta sencilla
    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
