package co.uniquindio.edu.sendifly.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import co.uniquindio.edu.sendifly.models.User;

public class ShowUserController {

    @FXML private Label nombreLabel;
    @FXML private Label telefonoLabel;
    @FXML private Label correoLabel;
    @FXML private Label passwordLabel;

    public void setUser(User user) {
        nombreLabel.setText(user.getName());
        telefonoLabel.setText(user.getPhone());
        correoLabel.setText(user.getEmail());
        passwordLabel.setText(user.getPassword());
    }

    @FXML
    private void handleCerrar() {
        Stage stage = (Stage) nombreLabel.getScene().getWindow();
        stage.close();
    }
}
