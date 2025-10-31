package co.uniquindio.edu.sendifly.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import java.io.IOException;

/*
* Clase Util que Implementa Métodos comunes para Navegar por los fxml
* */

public class NavigationUtil {
    private static NavigationUtil instance;
    private VBox contenedorPrincipal;

    private NavigationUtil() {}

    public static NavigationUtil getInstance() {
        if (instance == null) {
            instance = new NavigationUtil();
        }
        return instance;
    }

    public void setContenedorPrincipal(VBox contenedor) {
        this.contenedorPrincipal = contenedor;
    }

    public void navigateTo(String vistaFxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(vistaFxml));
            Parent vista = loader.load();

            contenedorPrincipal.getChildren().clear();
            contenedorPrincipal.getChildren().add(vista);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la vista: " + vistaFxml);
        }
    }


}
