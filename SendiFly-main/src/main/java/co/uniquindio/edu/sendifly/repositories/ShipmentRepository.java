package co.uniquindio.edu.sendifly.repositories;

import co.uniquindio.edu.sendifly.models.Shipment;

import java.util.ArrayList;
import java.util.Optional;

public class ShipmentRepository {

    private static ShipmentRepository instance = new ShipmentRepository();

    private ArrayList<Shipment> shipments;

    private ShipmentRepository() {
        this.shipments = new ArrayList<>();
    }

    // Singleton :v
    public static ShipmentRepository getInstance() {
        if (instance == null) {
            instance = new ShipmentRepository();
        }
        return instance;
    }


    public void addShipment(Shipment shipment) {
        System.out.println("Agregando envío: " + shipment.getId());
        for (Shipment s : shipments) {
            if (s.getId().equals(shipment.getId())) {
                throw new IllegalArgumentException("[ShipmentRepository] El envío ya existe");
            }
        }
        shipments.add(shipment);
        System.out.println("Total envíos ahora: " + shipments.size());
    }


    public void removeShipment(Shipment shipment) {
        for (Shipment s : shipments) {
            if (s.getId().equals(shipment.getId())) {
                shipments.remove(s);
                return;
            }
        }
        throw new IllegalArgumentException("[ShipmentRepository] El envío no existe");
    }

    // Obtener envío por ID
    public Optional<Shipment> getShipment(String id) {
        for (Shipment s : shipments) {
            if (s.getId().equals(id)) {
                return Optional.of(s);
            }
        }
        return Optional.empty();
    }

    public ArrayList<Shipment> getAllShipments() {
        return new ArrayList<>(shipments);
    }

    public int countShipments() {
        return shipments.size();
    }

    public boolean existsShipment(Shipment shipment) {
        return shipment != null && shipments.contains(shipment);
    }


}
