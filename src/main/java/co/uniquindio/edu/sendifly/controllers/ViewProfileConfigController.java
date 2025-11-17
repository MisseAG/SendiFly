package co.uniquindio.edu.sendifly.controllers;

import co.uniquindio.edu.sendifly.models.User;
import co.uniquindio.edu.sendifly.services.PersonService;
import co.uniquindio.edu.sendifly.session.SessionManager;
import co.uniquindio.edu.sendifly.utils.NavigationUtil;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ViewProfileConfigController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button btnBack;

    private final PersonService personService = PersonService.getInstance();
    private final SessionManager sessionManager = SessionManager.getInstance();
    private User currentUser;

    /**
     * Inicializa la vista cargando los datos del usuario actual
     */
    @FXML
    public void initialize() {
        try {
            // Verificar que hay usuario logueado
            if (!sessionManager.isLoggedIn()) {
                mostrarError("Sesión no iniciada", "Debes iniciar sesión primero");
                onBackToUserView();
                return;
            }

            // Obtener usuario actual desde la sesión
            currentUser = sessionManager.getCurrentUser();

            // Cargar datos del usuario en los campos
            cargarDatosUsuario();

        } catch (IllegalStateException e) {
            mostrarError("Error de sesión", e.getMessage());
            onBackToUserView();
        } catch (Exception e) {
            mostrarError("Error al inicializar", "No se pudieron cargar los datos del perfil");
            e.printStackTrace();
        }
    }

    /**
     * Carga los datos del usuario actual en los campos del formulario
     */
    private void cargarDatosUsuario() {
        nameField.setText(currentUser.getName());
        emailField.setText(currentUser.getEmail());
        passwordField.setText(currentUser.getPassword());
    }

    /**
     * Actualiza el nombre del usuario
     */
    @FXML
    private void onChangeName() {
        try {
            String nuevoNombre = nameField.getText().trim();

            // Validar que no esté vacío
            if (nuevoNombre.isEmpty()) {
                mostrarAdvertencia("Campo vacío", "El nombre no puede estar vacío");
                nameField.setText(currentUser.getName()); // Restaurar valor anterior
                return;
            }

            // Actualizar nombre
            currentUser.setName(nuevoNombre);
            //Guardar en repository
            personService.updatePerson(currentUser);

            mostrarExito("Nombre actualizado correctamente");

        } catch (IllegalArgumentException e) {
            mostrarError("Error de validación", e.getMessage());
            nameField.setText(currentUser.getName());
        } catch (Exception e) {
            mostrarError("Error al actualizar", "No se pudo guardar el nombre");
            nameField.setText(currentUser.getName());
            e.printStackTrace();
        }
    }

    /**
     * Actualiza el email del usuario
     */
    @FXML
    private void onChangeEmail() {
        try {
            String nuevoEmail = emailField.getText().trim();

            // Validar formato de email
            if (!esEmailValido(nuevoEmail)) {
                mostrarAdvertencia("Email inválido", "Por favor ingresa un email válido");
                emailField.setText(currentUser.getEmail()); // Restaurar valor anterior
                return;
            }

            //Actualizar
            String emailAnterior = currentUser.getEmail();
            currentUser.setEmail(nuevoEmail);

            try {
                // Guardar EN REPOSITORIO (valida que no esté en uso)
                personService.updatePerson(currentUser);

                mostrarExito("Email actualizado y guardado correctamente");

            } catch (IllegalArgumentException e) {
                currentUser.setEmail(emailAnterior); // si está usado, usa anterior
                emailField.setText(emailAnterior);
                throw e; //
            }

        } catch (IllegalArgumentException e) {
            mostrarError("Error de validación", e.getMessage());
            emailField.setText(currentUser.getEmail());
        } catch (Exception e) {
            mostrarError("Error al actualizar", "No se pudo guardar el email");
            emailField.setText(currentUser.getEmail());
            e.printStackTrace();
        }
    }

    /**
     * Actualiza la contraseña del usuario
     */
    @FXML
    private void onChangePassword() {
        try {
            String nuevaPassword = passwordField.getText();

            // Validar longitud mínima
            if (nuevaPassword.length() < 6) {
                mostrarAdvertencia("Contraseña débil",
                        "La contraseña debe tener al menos 6 caracteres");
                passwordField.setText(currentUser.getPassword()); // Restaurar
                return;
            }

            currentUser.setPassword(nuevaPassword);
            personService.updatePerson(currentUser);
            mostrarExito("Contraseña actualizada correctamente");

        } catch (IllegalArgumentException e) {
            mostrarError("Error de validación", e.getMessage());
            passwordField.setText(currentUser.getPassword());
        } catch (Exception e) {
            mostrarError("Error al actualizar", "No se pudo guardar la contraseña");
            passwordField.setText(currentUser.getPassword());
            e.printStackTrace();
        }
    }

    /**
     * Vuelve a la vista del usuario
     */
    @FXML
    private void onBackToUserView() {
        String path = "/co/uniquindio/edu/sendifly/views/ViewUser.fxml";
        String title = "SendiFly-Panel De Usuario";
        Node node = btnBack;
        NavigationUtil.navigateToScene(node, path, title,400 ,600);;
    }

    // Validación

    /**
     * Valida el formato de un email
     */
    private boolean esEmailValido(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    // Alertas

    private void mostrarExito(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarAdvertencia(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
