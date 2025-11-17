package co.uniquindio.edu.sendifly.controllers;

import co.uniquindio.edu.sendifly.dtos.DeliveryManDTO;
import co.uniquindio.edu.sendifly.models.AvailabilityStatus.*;
import co.uniquindio.edu.sendifly.models.DeliveryMan;
import co.uniquindio.edu.sendifly.repositories.PersonRepository;
import co.uniquindio.edu.sendifly.services.PersonService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CreateDeliveryController {

    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private ObservableList<DeliveryManDTO> deliveryObservable;

    public void setDeliveryObservable(ObservableList<DeliveryManDTO> deliveryObservable) {
        this.deliveryObservable = deliveryObservable;
    }

    private DeliveryManDTO deliveryToEdit;

    public void setDeliveryToEdit(DeliveryManDTO dto) {
        this.deliveryToEdit = dto;
        nameField.setText(dto.getName());
        phoneField.setText(dto.getPhone());
        emailField.setText(dto.getEmail());
        passwordField.setText(""); // No mostrar contraseña original
    }

    private Runnable onDeliveryChange;

    public void setOnDeliveryChange(Runnable callback) {
        this.onDeliveryChange = callback;
    }

    @FXML
    private void handleCreateDelivery() {
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
            PersonService.getInstance().validateEmail(email);
            PersonService.getInstance().validatePassword(password);
            PersonService.getInstance().validatePhone(phone);
            if (deliveryToEdit != null) {
                if (PersonService.getInstance().emailExistsExcluding(email, deliveryToEdit.getEmail())) {
                    throw new IllegalArgumentException("El email ya está registrado por otro usuario.");
                }
            } else {
                if (PersonService.getInstance().emailExists(email)) {
                    throw new IllegalArgumentException("El email ya está registrado.");
                }
            }
            if (deliveryToEdit != null) {
                DeliveryMan original = (DeliveryMan) PersonRepository.getInstance().findByEmail(deliveryToEdit.getEmail());
                if (original != null) {
                    original.setName(name);
                    original.setPhone(phone);
                    original.setEmail(email);
                    original.setPassword(password);
                }
            } else {
                PersonService.getInstance().registerPerson(name, email, phone, password, "repartidor");
            }
            if (deliveryObservable != null) {
                deliveryObservable.clear();
                PersonRepository.getInstance().getAll().stream()
                        .filter(p -> p instanceof DeliveryMan)
                        .map(p -> (DeliveryMan) p)
                        .map(DeliveryManDTO::fromDeliveryMan)
                        .forEach(deliveryObservable::add);
            }
            closeWindow();
            if (onDeliveryChange != null) {
                onDeliveryChange.run();
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
}
