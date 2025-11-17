package co.uniquindio.edu.sendifly.controllers;

import co.uniquindio.edu.sendifly.utils.NavigationUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class ViewAdminController {
    // Header
    @FXML private MenuItem menuProfile;
    @FXML private MenuItem menuLogout;

    // Navegación
    @FXML private Button btnClientes;
    @FXML private Button btnRepartidores;
    @FXML private Button btnCRUD;
    @FXML private Button btnConsultar;
    @FXML private Button btnAsignar;
    @FXML private Button btnChart1;
    @FXML private Button btnChart2;
    @FXML private Button btnChart3;

    // Dashboard
    @FXML private Label dashboardTitle;
    @FXML private StackPane contenedorDinamico;

    @FXML
    public void initialize() {

        System.out.println("Panel de Administración cargado");
    }

    // ============================================
    // HEADER
    // ============================================

    @FXML
    private void handleLogout(ActionEvent event) {
        String path = "/co/uniquindio/edu/sendifly/views/MainView.fxml";
        String title = "SendiFly-Home";
        //Usa cualquier node
        Node node = contenedorDinamico;
        NavigationUtil.navigateToScene(node, path, title,400 ,600);
    }
    // ============================================
    // NAVEGACIÓN DE CONTENIDO DINÁMICO
    // ============================================

    /**
     * Carga una vista en el contenedor dinámico
     */
    private void cargarVista(String fxmlPath, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent vista = loader.load();

            contenedorDinamico.getChildren().clear();
            contenedorDinamico.getChildren().add(vista);
            dashboardTitle.setText(titulo);

            System.out.println("✓ Vista cargada: " + titulo);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("✗ Error al cargar vista: " + fxmlPath);
            mostrarError("No se pudo cargar la vista: " + titulo);
        }
    }

    /**
     * Muestra un mensaje de error en el contenedor
     */
    private void mostrarError(String mensaje) {
        contenedorDinamico.getChildren().clear();
        Label errorLabel = new Label(mensaje);
        errorLabel.setStyle("-fx-text-fill: #dc3545; -fx-font-size: 16px; -fx-font-weight: bold;");
        contenedorDinamico.getChildren().add(errorLabel);
    }

    // ============================================
    // HANDLERS - GESTIÓN USUARIOS
    // ============================================

    @FXML
    private void handleClientes() {
        cargarVista("/co/uniquindio/edu/sendifly/views/admin/gestionClientes.fxml", "Gestión de Clientes");
    }

    @FXML
    private void handleRepartidores() {
        cargarVista("/co/uniquindio/edu/sendifly/views/admin/gestionRepartidores.fxml", "Gestión de Repartidores");
    }

    // ============================================
    // HANDLERS - GESTIÓN ENVÍOS
    // ============================================

    @FXML
    private void handleCRUD() {
        cargarVista("/co/uniquindio/edu/sendifly/views/admin/gestionEnvios.fxml", "CRUD Envíos");
    }

    @FXML
    private void handleConsultar() {
        cargarVista("/co/uniquindio/edu/sendifly/views/admin/consultarEnvio.fxml", "Envío");
    }

    @FXML
    private void handleAsignar() {
        cargarVista("/co/uniquindio/edu/sendifly/views/admin/asignarEnvio.fxml", "Asignar Envío");
    }

    // ============================================
    // HANDLERS - ESTADÍSTICAS
    // ============================================

    @FXML
    private void handleChart1() {
        cargarVista("/co/uniquindio/edu/sendifly/views/admin/chartEnviosMensuales.fxml", "📈 Envíos Mensuales");
    }

    @FXML
    private void handleChart2() {
        cargarVista("/co/uniquindio/edu/sendifly/views/chartTopRutas.fxml", "Top Rutas");
    }

    @FXML
    private void handleChart3() {
        cargarVista("/co/uniquindio/edu/sendifly/views/chartRendimiento.fxml", "Rendimiento");
    }

    // ============================================
    // HANDLERS - MENÚ USUARIO
    // ============================================

    @FXML
    private void handleProfile() {
        System.out.println("Configurar Perfil");
        cargarVista("/co/uniquindio/edu/sendifly/views/configurarPerfil.fxml", "⚙onfigurar Perfil");
    }

    @FXML
    private void handleLogout() {
        System.out.println("Cerrar Sesión");
        NavigationUtil.navigateToScene(
                contenedorDinamico,
                "/co/uniquindio/edu/sendifly/views/Login.fxml",
                "SENDIFLY - Iniciar Sesión"
        );
    }

    // ============================================
    // MÉTODOS AUXILIARES
    // ============================================

    /**
     * Carga el dashboard por defecto
     */
    public void cargarDashboard() {
        dashboardTitle.setText("Dashboard");
        // Aquí puedes cargar estadísticas generales o una vista de resumen
    }
}
