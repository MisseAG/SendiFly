package co.uniquindio.edu.sendifly.controllers;

import co.uniquindio.edu.sendifly.services.PersonService;
import co.uniquindio.edu.sendifly.utils.NavigationUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SigninViewController implements Initializable {

    @FXML
    private Button btnBackToLogin;

    @FXML
    private Button btnSignIn;

    @FXML
    private ChoiceBox<String> choiceRole;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtPhone;

    private PersonService personService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Inicializa choicebox
        choiceRole.getItems().addAll("Usuario", "Administrador", "Repartidor");
        choiceRole.setValue("Usuario"); // Valor por defecto

        personService = PersonService.getInstance();

    }

    @FXML
    public void handleBackToLogin (ActionEvent event) {
        String path = "/co/uniquindio/edu/sendifly/views/LoginView.fxml";
        String title = "SendiFly-Login";
        NavigationUtil.navigateToScene(btnBackToLogin, path, title);
    };

    @FXML
    public void handleSignIn(ActionEvent event) {
        // Obtener los datos ingresados
        String name = txtName.getText().trim();
        String confirmPassword = txtConfirmPassword.getText().trim();
        String password = txtPassword.getText().trim();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();
        String role = choiceRole.getValue();

        // Validar campos obligatorios
        if (name.isEmpty() || password.isEmpty() || email.isEmpty() || role == null) {
            mostrarAlerta(Alert.AlertType.WARNING,
                    "Campos incompletos",
                    "Por favor complete todos los campos obligatorios");
            return;
        }

        // Validar que las contraseñas coincidan
        if (!password.equals(confirmPassword)) {
            mostrarAlerta(Alert.AlertType.WARNING,
                    "Contraseñas no coinciden",
                    "Las contraseñas ingresadas no son iguales");
            return;
        }

        try {
            // Registrar persona
            personService.registerPerson(name, email, phone, password, role);

            mostrarAlerta(Alert.AlertType.INFORMATION,
                    "Usuario registrado",
                    "Se ha registrado exitosamente");

            NavigationUtil.navigateToScene(btnSignIn,
                    "/co/uniquindio/edu/sendifly/views/LoginView.fxml",
                    "SENDIFLY - Login");

        } catch (IllegalArgumentException e) {
            mostrarAlerta(Alert.AlertType.ERROR,
                    "Error en el registro",
                    e.getMessage());
        }
    }



    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


}