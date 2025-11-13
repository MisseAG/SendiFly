package co.uniquindio.edu.sendifly;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppTest extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppTest.class.getResource("/co/uniquindio/edu/sendifly/views/ViewAdmin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 500);
        stage.setTitle("SendiFly");
        stage.setScene(scene);
        stage.show();
    }
}
