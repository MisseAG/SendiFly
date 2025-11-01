package co.uniquindio.edu.sendifly.controllers;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;

public class ViewUserController {

    @FXML
    private void handleProfileSettings(ActionEvent event) {
        System.out.println("Configuración de perfil clicada");
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        System.out.println("Cerrar sesión");
    }

    @FXML
    private void handleNewQuote(ActionEvent event) {
        System.out.println("Nueva cotización");
    }

    @FXML
    private void handleCreateShipment(ActionEvent event) {
        System.out.println("Crear envío");
    }

    @FXML
    private void handleTrackShipment(ActionEvent event) {
        System.out.println("Rastrear envío");
    }

    @FXML
    private void handleDownloadReports(ActionEvent event) {
        System.out.println("Descargar reportes");
    }

    @FXML
    private void handleViewHistory(ActionEvent event) {
        System.out.println("Ver historial");
    }

}
