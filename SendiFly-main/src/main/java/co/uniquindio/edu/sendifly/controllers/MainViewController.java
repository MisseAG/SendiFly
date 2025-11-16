package co.uniquindio.edu.sendifly.controllers;

import co.uniquindio.edu.sendifly.utils.NavigationUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable{

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnRegister;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnLogin.setOnAction(this::handleLogin);
    }

    //hover
    @FXML
    private void onButtonHoverEnter(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle(
                "-fx-background-color: linear-gradient(to right, #f98144 0%, #c12525 100%);" +
                        "-fx-background-radius: 40;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-cursor: hand;"
        );
    }

    // Efecto hover
    @FXML
    private void onButtonHoverExit(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 40;" +
                        "-fx-text-fill: #333333;" +
                        "-fx-font-weight: bold;" +
                        "-fx-cursor: hand;"
        );
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String path = "/co/uniquindio/edu/sendifly/views/LoginView.fxml";
        String title = "SendiFly-Login";
        NavigationUtil.navigateToScene(btnLogin, path, title);
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        String path = "/co/uniquindio/edu/sendifly/views/SigninView.fxml";
        String title = "SendiFly-SignIn";
        NavigationUtil.navigateToScene(btnLogin, path, title);
    }
}
