module co.uniquindio.edu.sendifly {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.uniquindio.edu.sendifly to javafx.fxml;
    exports co.uniquindio.edu.sendifly;

    opens co.uniquindio.edu.sendifly.controllers to javafx.fxml;
    exports co.uniquindio.edu.sendifly.controllers;
}