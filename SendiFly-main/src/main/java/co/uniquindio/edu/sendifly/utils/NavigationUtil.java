package co.uniquindio.edu.sendifly.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Clase Util que implementa métodos estáticos para navegar entre vistas FXML
 * Soporta dos tipos de navegación:
 * 1. Navegación en el mismo Stage (cambio de Scene completo)
 * 2. Navegación dentro de un contenedor (cambio de contenido dinámico)
 */
public class NavigationUtil {

    // Dimensiones por defecto (mobile-first)
    private static final double DEFAULT_WIDTH = 400;
    private static final double DEFAULT_HEIGHT = 600;

    private NavigationUtil() {
        throw new UnsupportedOperationException("Clase de utilidad, no se puede instanciar");
    }

    /**
     * Navega a una vista dentro de un contenedor específico (sin cambiar Stage)
     * @param contenedor Puede ser VBox, AnchorPane, BorderPane, StackPane, etc.
     * @param vistaFxml Ruta del archivo FXML
     */
    public static void navigateTo(Pane contenedor, String vistaFxml) {
        if (contenedor == null) {
            System.err.println("Error: El contenedor no puede ser null");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(NavigationUtil.class.getResource(vistaFxml));
            Parent vista = loader.load();

            contenedor.getChildren().clear();
            contenedor.getChildren().add(vista);

            System.out.println("Navegación exitosa a: " + vistaFxml);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la vista: " + vistaFxml);
        }
    }

    /**
     * Navega a una nueva Scene completa (cambia el Stage actual)
     * Usa dimensiones mobile por defecto (400x600)
     * @param node Nodo de la vista actual para obtener el Stage
     * @param vistaFxml Ruta del archivo FXML
     * @param titulo Título de la ventana
     */
    public static void navigateToScene(Node node, String vistaFxml, String titulo) {
        navigateToScene(node, vistaFxml, titulo, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Navega a una nueva Scene completa con dimensiones personalizadas
     * @param node Nodo de la vista actual para obtener el Stage
     * @param vistaFxml Ruta del archivo FXML
     * @param titulo Título de la ventana
     * @param width Ancho de la ventana
     * @param height Alto de la ventana
     */
    public static void navigateToScene(Node node, String vistaFxml, String titulo, double width, double height) {
        try {
            FXMLLoader loader = new FXMLLoader(NavigationUtil.class.getResource(vistaFxml));
            Parent root = loader.load();

            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = new Scene(root, width, height);

            stage.setScene(scene);
            stage.setTitle(titulo);
            stage.show();

            System.out.println("Navegación exitosa a: " + titulo);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la vista: " + vistaFxml);
        }
    }

    /**
     * Navega según el rol del usuario (método especializado para login)
     * @param node Nodo de la vista actual
     * @param role Rol del usuario ("Admin", "Usuario", "Delivery")
     */
    public static void navigateByRole(Node node, String role) {
        String fxmlPath;
        String titulo;

        switch (role.toLowerCase()) {
            case "admin":
            case "administrador":
                fxmlPath = "/co/uniquindio/edu/sendifly/views/viewAdmin.fxml";
                titulo = "SENDIFLY - Panel de Administrador";
                break;

            case "usuario":
            case "user":
                fxmlPath = "/co/uniquindio/edu/sendifly/views/viewUser.fxml";
                titulo = "SENDIFLY - Panel de Usuario";
                break;

            case "delivery":
            case "repartidor":
                fxmlPath = "/co/uniquindio/edu/sendifly/views/ViewDelivery.fxml";
                titulo = "SENDIFLY - Panel de Delivery";
                break;

            default:
                System.err.println("Rol desconocido: " + role);
                return;
        }

        navigateToScene(node, fxmlPath, titulo);
    }

    /**
     * Obtiene el controller de la vista cargada (útil para pasar datos)
     * @param vistaFxml Ruta del archivo FXML
     * @return FXMLLoader con el controller cargado
     */
    public static FXMLLoader loadViewWithController(String vistaFxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(NavigationUtil.class.getResource(vistaFxml));
        loader.load();
        return loader;
    }

    /**
     * Navega a una vista dentro de un contenedor y retorna el controller
     * @param contenedor Contenedor donde se cargará la vista
     * @param vistaFxml Ruta del archivo FXML
     * @return Controller de la vista cargada, o null si hay error
     */
    public static <T> T navigateToWithController(Pane contenedor, String vistaFxml) {
        if (contenedor == null) {
            System.err.println("Error: El contenedor no puede ser null");
            return null;
        }

        try {
            FXMLLoader loader = new FXMLLoader(NavigationUtil.class.getResource(vistaFxml));
            Parent vista = loader.load();

            contenedor.getChildren().clear();
            contenedor.getChildren().add(vista);

            System.out.println("Navegación exitosa a: " + vistaFxml);
            return loader.getController();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la vista: " + vistaFxml);
            return null;
        }
    }
}