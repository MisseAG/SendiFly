package co.uniquindio.edu.sendifly.controllers;

import co.uniquindio.edu.sendifly.models.Address;
import co.uniquindio.edu.sendifly.services.PersonService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;

public class AddressManagementController implements Initializable {

    @FXML private TableView<Address> tableAddresses;
    @FXML private TableColumn<Address, String> colAlias;
    @FXML private TableColumn<Address, String> colStreet;

    @FXML private Button onAgregarButton;
    @FXML private Button onEditarButton;
    @FXML private Button onEliminarButton;

    private PersonService personService;
    private ObservableList<Address> addressList;
    private Address selectedAddress;
    private String currentUserId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        personService = PersonService.getInstance();
        addressList = FXCollections.observableArrayList();

        setupTable();
        setupTableSelection();
    }

    private void setupTable() {
        // Cambiar PropertyValueFactory por lambda
        colAlias.setCellValueFactory(cellData -> {
            Address address = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(address.getAlias());
        });

        colStreet.setCellValueFactory(cellData -> {
            Address address = cellData.getValue();
            String direccionCompleta = address.getStreet() + ", " + address.getCity();
            return new javafx.beans.property.SimpleStringProperty(direccionCompleta);
        });

        tableAddresses.setItems(addressList);
    }

    private void setupTableSelection() {
        tableAddresses.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        selectedAddress = newSelection;
                    }
                }
        );
    }

    private void loadAddresses() {
        if (currentUserId != null) {
            List<Address> addresses = personService.getUserAddresses(currentUserId);
            addressList.clear();
            addressList.addAll(addresses);
        }
    }

    @FXML
    private void onAgregarButtonClicked(ActionEvent event) {
        if (currentUserId == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Usuario no identificado");
            return;
        }
        showAddressDialog(null);
    }

    @FXML
    private void onEditarButtonClicked(ActionEvent event) {
        if (selectedAddress == null) {
            showAlert(Alert.AlertType.WARNING, "Sin selección",
                    "Seleccione una dirección de la tabla para editar");
            return;
        }
        showAddressDialog(selectedAddress);
    }

    @FXML
    private void onEliminarButtonClicked(ActionEvent event) {
        if (selectedAddress == null) {
            showAlert(Alert.AlertType.WARNING, "Sin selección",
                    "Seleccione una dirección de la tabla para eliminar");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Está seguro de eliminar: " + selectedAddress.getAlias() + "?");

        Optional<ButtonType> result = confirmacion.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean success = personService.deleteAddress(currentUserId, selectedAddress.getId());

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Éxito",
                        "Dirección eliminada correctamente");
                loadAddresses();
                selectedAddress = null;
            } else {
                showAlert(Alert.AlertType.ERROR, "Error",
                        "No se pudo eliminar la dirección");
            }
        }
    }

    private void showAddressDialog(Address address) {
        Dialog<Address> dialog = new Dialog<>();
        dialog.setTitle(address == null ? "Agregar Dirección" : "Editar Dirección");

        TextField txtAlias = new TextField();
        TextField txtStreet = new TextField();
        TextField txtCity = new TextField();
        TextField txtLatitude = new TextField();
        TextField txtLongitude = new TextField();

        txtLatitude.setText("0.0");
        txtLongitude.setText("0.0");

        if (address != null) {
            txtAlias.setText(address.getAlias());
            txtStreet.setText(address.getStreet());
            txtCity.setText(address.getCity());
            txtLatitude.setText(String.valueOf(address.getLatitude()));
            txtLongitude.setText(String.valueOf(address.getLongitude()));
        }

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("Alias (Nombre):"), 0, 0);
        grid.add(txtAlias, 1, 0);
        grid.add(new Label("Calle:"), 0, 1);
        grid.add(txtStreet, 1, 1);
        grid.add(new Label("Ciudad:"), 0, 2);
        grid.add(txtCity, 1, 2);
        grid.add(new Label("Latitud:"), 0, 3);
        grid.add(txtLatitude, 1, 3);
        grid.add(new Label("Longitud:"), 0, 4);
        grid.add(txtLongitude, 1, 4);

        dialog.getDialogPane().setContent(grid);

        ButtonType saveButtonType = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                if (validateAddressForm(txtAlias.getText(), txtStreet.getText(), txtCity.getText())) {
                    try {
                        String addressId = address == null ? generateAddressId() : address.getId();

                        return new Address.AddressBuilder()
                                .id(addressId)
                                .alias(txtAlias.getText().trim())
                                .street(txtStreet.getText().trim())
                                .city(txtCity.getText().trim())
                                .latitude(parseDouble(txtLatitude.getText()))
                                .longitude(parseDouble(txtLongitude.getText()))
                                .build();

                    } catch (Exception e) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Error en los datos ingresados");
                        return null;
                    }
                }
            }
            return null;
        });

        Optional<Address> result = dialog.showAndWait();
        result.ifPresent(newAddress -> {
            boolean success;
            if (address == null) {
                success = personService.addAddress(currentUserId, newAddress);
                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Éxito", "Dirección agregada correctamente");
                    loadAddresses();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "No se pudo agregar la dirección");
                }
            } else {
                success = personService.updateAddress(currentUserId, newAddress);
                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Éxito", "Dirección actualizada correctamente");
                    loadAddresses();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "No se pudo actualizar la dirección");
                }
            }
        });
    }

    private boolean validateAddressForm(String alias, String street, String city) {
        if (alias == null || alias.trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Campo vacío", "Ingrese un alias para la dirección");
            return false;
        }

        if (street == null || street.trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Campo vacío", "Ingrese la calle");
            return false;
        }

        if (city == null || city.trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Campo vacío", "Ingrese la ciudad");
            return false;
        }

        return true;
    }

    private double parseDouble(String text) {
        try {
            return text.trim().isEmpty() ? 0.0 : Double.parseDouble(text.trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private String generateAddressId() {
        return "ADDR_" + UUID.randomUUID().toString().substring(0, 8);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setCurrentUser(String userId) {
        this.currentUserId = userId;
        loadAddresses();
    }
}