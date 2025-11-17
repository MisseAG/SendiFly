package co.uniquindio.edu.sendifly.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import co.uniquindio.edu.sendifly.models.User;
import co.uniquindio.edu.sendifly.services.PersonService;
import co.uniquindio.edu.sendifly.repositories.PersonRepository;
import co.uniquindio.edu.sendifly.dtos.UserDTO;
import javafx.collections.ObservableList;

public class CreateUserController {

    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    // Referencia a la lista observable de la tabla
    private ObservableList<UserDTO> userObservable;

    public void setUserObservable(ObservableList<UserDTO> userObservable) {
        this.userObservable = userObservable;
    }

    @FXML
    private void handleCreateUser() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Todos los campos son obligatorios.");
            return;
        }
        if (!email.endsWith("@mail.com")) {
            errorLabel.setText("El correo debe terminar en @mail.com");
            return;
        }
        try {
            // Validaciones comunes
            PersonService.getInstance().validateEmail(email);
            PersonService.getInstance().validatePassword(password);
            PersonService.getInstance().validatePhone(phone);
            if (userToEdit != null) {
                if (PersonService.getInstance().emailExistsExcluding(email, userToEdit.getEmail())) {
                    throw new IllegalArgumentException("El email ya está registrado por otro usuario.");
                }
            } else {
                if (PersonService.getInstance().emailExists(email)) {
                    throw new IllegalArgumentException("El email ya está registrado.");
                }
            }

            // Crear o editar un usuario o User men
            if (userToEdit != null) {
                User original = (User) PersonRepository.getInstance().findByEmail(userToEdit.getEmail());
                if (original != null) {
                    original.setName(name);
                    original.setPhone(phone);
                    original.setEmail(email);
                    original.setPassword(password);
                }
            } else {
                PersonService.getInstance().registerPerson(name, email, phone, password, "usuario");
            }

            // Actualizar la tabla
            if (userObservable != null) {
                userObservable.clear();
                PersonRepository.getInstance().getAll().stream()
                        .filter(p -> p instanceof User)
                        .map(p -> (User) p)
                        .map(UserDTO::fromUser)
                        .forEach(userObservable::add);
            }
            closeWindow();
            if (onUserChange != null) {  //esto es para que el contador sume
                onUserChange.run();
            }
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    private UserDTO userToEdit;

    public void setUserToEdit(UserDTO dto) {
        this.userToEdit = dto;
        nameField.setText(dto.getName());
        phoneField.setText(dto.getPhone());
        emailField.setText(dto.getEmail());
        passwordField.setText(""); // No mostramos la contraseña original por si las moscas
    }


    private Runnable onUserChange;
    public void setOnUserChange(Runnable callback) {
        this.onUserChange = callback;
    }
}
