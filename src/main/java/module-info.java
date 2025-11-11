module co.uniquindio.edu.sendifly {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires co.uniquindio.edu.sendifly;

    opens co.uniquindio.edu.sendifly to javafx.fxml;
    exports co.uniquindio.edu.sendifly;

    opens co.uniquindio.edu.sendifly.controllers to javafx.fxml;
    exports co.uniquindio.edu.sendifly.controllers;
}