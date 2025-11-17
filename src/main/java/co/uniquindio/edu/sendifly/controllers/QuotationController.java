package co.uniquindio.edu.sendifly.controllers;

import co.uniquindio.edu.sendifly.utils.NavigationUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class QuotationController {

    @FXML
    private Button backButton;

    @FXML
    private Button calcularButton;

    @FXML
    private Button crearEnvioButton;

    @FXML
    private TextField destinoTextField;

    @FXML
    private Label detalleLabel;

    @FXML
    private Label dimensionesLabel;

    @FXML
    private Button nuevaCotizacionButton;

    @FXML
    private TextField origenTextField;

    @FXML
    private TextField pesoTextField;

    @FXML
    private Label precioLabel;

    @FXML
    private ComboBox<?> prioridadComboBox;

    @FXML
    private Label prioridadLabel;

    @FXML
    private VBox resultadoVBox;

    @FXML
    private Label rutaLabel;

    @FXML
    private TextField volumenTextField;

    @FXML
    void handleBack(ActionEvent event) {
        System.out.println("Volviendo");
        String path = "/co/uniquindio/edu/sendifly/views/ViewUser.fxml";
        String title = "SendiFly - Panel De Usuario";
        //Usa cualquier node
        Node node = backButton;
        NavigationUtil.navigateToScene(node, path, title,400 ,600);
    }

    @FXML
    void handleCalcular(ActionEvent event) {
        System.out.println("Calculando...");
        mostrarResultados();
        System.out.println();
    }

    @FXML
    void handleCrearEnvio(ActionEvent event) {
        //Lógica para pasar los datos ingresados al otro controller, el controller de crear envío, y debería inicializarse aspi
        System.out.println("Crear envío/cotización");

        //después de creado se va a la vista de crear
        //ver en crear envio controller más comentarios
        String path = "/co/uniquindio/edu/sendifly/views/user/CreateShipmentView.fxml";
        String title = "SendiFly-Envío";
        //Usa cualquier node
        Node node = backButton;
        NavigationUtil.navigateToScene(node, path, title,400 ,600);
    }

    @FXML
    void handleNuevaCotizacion(ActionEvent event) {
        // ... limpiar campos ...
        ocultarResultados();
    }

    // Mostrar resultados
    private void mostrarResultados() {
        resultadoVBox.setVisible(true);
        resultadoVBox.setManaged(true);
    }

    // Ocultar resultados
    private void ocultarResultados() {
        resultadoVBox.setVisible(false);
        resultadoVBox.setManaged(false);
    }
}
