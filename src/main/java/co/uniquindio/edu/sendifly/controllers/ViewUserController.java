package co.uniquindio.edu.sendifly.controllers;

import co.uniquindio.edu.sendifly.models.User;
import co.uniquindio.edu.sendifly.session.SessionManager;
import co.uniquindio.edu.sendifly.utils.NavigationUtil;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewUserController implements Initializable {


    @FXML
    private Button createShipmentButton;

    @FXML
    private Button downloadReportsButton;

    @FXML
    private Button historyButton;

    @FXML
    private MenuItem logoutMenuItem;

    @FXML
    private Button newQuoteButton;

    @FXML
    private MenuItem profileMenuItem;

    @FXML
    private Button trackButton;

    @FXML
    private TextField trackingTextField;

    @FXML
    private Label userGreetingLabel;

    @FXML
    private Button paymentMethodsBtn;

    @FXML
    private Button addressesBtn;

    @FXML
    private Button paymentsHistorialBtn;

    private SessionManager sessionManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sessionManager = SessionManager.getInstance();
        actualizarSaludo();
    }

    @FXML
    private void handleProfileSettings(ActionEvent event) {
        System.out.println("Configuración de perfil clicada");
        String path = "/co/uniquindio/edu/sendifly/views/user/ViewProfileConfig.fxml";
        String title = "SendiFly-Configurar Perfil";
        Node node = userGreetingLabel;
        NavigationUtil.navigateToScene(node, path, title);
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        String path = "/co/uniquindio/edu/sendifly/views/MainView.fxml";
        String title = "SendiFly-Home";
        //Usa cualquier node
        Node node = userGreetingLabel;
        NavigationUtil.navigateToScene(node, path, title, 400, 600);
    }

    @FXML
    private void handleNewQuote(ActionEvent event) {
        System.out.println("Nueva cotización");
        String path = "/co/uniquindio/edu/sendifly/views/user/crearEnvioView.fxml";
        String title = "SendiFly-Envío";
        //Usa cualquier node
        Node node = userGreetingLabel;
        NavigationUtil.navigateToScene(node, path, title, 400, 600);
    }

    @FXML
    private void handleCreateShipment(ActionEvent event) {
        System.out.println("Crear envío");
        String path = "/co/uniquindio/edu/sendifly/views/user/CreateShipmentView.fxml";
        String title = "SendiFly-Envío";
        //Usa cualquier node
        Node node = userGreetingLabel;
        NavigationUtil.navigateToScene(node, path, title,400 ,600);
    }

    @FXML
    private void handleTrackShipment(ActionEvent event) {
        System.out.println("Rastrear envío");
    }

    @FXML
    private void handlePaymentMethods(ActionEvent event){
        String path = "/co/uniquindio/edu/sendifly/views/user/PaymentMethondsView.fxml";
        String title = "SendiFly-FormasDePago";
        //Usa cualquier node
        Node node = userGreetingLabel;
        NavigationUtil.navigateToScene(node, path, title, 400, 600);
    }

    @FXML
    private void handleAddresses(ActionEvent event){
        String path = "/co/uniquindio/edu/sendifly/views/user/AddressesView.fxml";
        String title = "SendiFly-Envío";
        //Usa cualquier node
        Node node = userGreetingLabel;
        NavigationUtil.navigateToScene(node, path, title, 400, 600);
    }


    @FXML
    private void handlePaymentsHistorial (ActionEvent event){
        String path = "/co/uniquindio/edu/sendifly/views/user/FormasDePago.fxml";
        String title = "SendiFly-Historial";
        //Usa cualquier node
        Node node = userGreetingLabel;
        NavigationUtil.navigateToScene(node, path, title, 400, 600);
    }

    @FXML
    private void handleDownloadReports(ActionEvent event) {
        System.out.println("Descargar reportes");
    }

    @FXML
    private void handleViewHistory(ActionEvent event) {
        System.out.println("Ver historial");
    }

    private void actualizarSaludo() {
        if (sessionManager.isLoggedIn()) {
            User currentUser = sessionManager.getCurrentUser();
            userGreetingLabel.setText("Hola, " + currentUser.getName());
        } else {
            userGreetingLabel.setText("Hola, Usuario");
        }
    }
}
