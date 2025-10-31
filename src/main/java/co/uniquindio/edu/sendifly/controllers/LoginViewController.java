package co.uniquindio.edu.sendifly.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginViewController {
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private ChoiceBox<String> choiceRole;

    @FXML
    private Button btnLogin;
}
