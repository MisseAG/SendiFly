package co.uniquindio.edu.sendifly.controllers;

import co.uniquindio.edu.sendifly.utils.NavigationUtil;

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
    private TextField txtUsername;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Inicializa choicebox
        choiceRole.getItems().addAll("Usuario", "Administrador", "Repartidor");
        choiceRole.setValue("Usuario"); // Valor por defecto

        btnLogin.setOnAction(this::handleLogin);
    }

    //
    @FXML
    private void handleLogin(ActionEvent event) {
        // Obtener los datos ingresados
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText();
        String role = choiceRole.getValue();

        if (username.isEmpty() || password.isEmpty() || role == null) {
            mostrarAlerta(Alert.AlertType.WARNING,
                    "Campos incompletos",
                    "Por favor complete todos los campos");
            return;
        }

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

    private boolean validarCredenciales(String username, String password, String role) {

        if (role.equals("Usuario")) {

            return (username.equals("user") && password.equals("user123")) ||
                    (username.equals("usuario") && password.equals("1234"));
        }
        else if (role.equals("Administrador")) {

            return (username.equals("admin") && password.equals("admin123")) ||
                    (username.equals("administrador") && password.equals("admin"));
        }
        else if (role.equals("Repartidor")){

            return (username.equals("delivery") && password.equals("delivery123"));
        }
        return false;
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
        txtUsername.clear();
        txtUsername.requestFocus();
    }
}
