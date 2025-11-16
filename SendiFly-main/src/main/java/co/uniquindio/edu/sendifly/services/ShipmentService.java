package co.uniquindio.edu.sendifly.services;

import co.uniquindio.edu.sendifly.models.AdditionalServices.AdditionalService;
import co.uniquindio.edu.sendifly.models.Pack;
import co.uniquindio.edu.sendifly.models.Priorities.ShippingPriority;
import co.uniquindio.edu.sendifly.models.Shipment;
import co.uniquindio.edu.sendifly.models.ShippingStatus.ShippingStatus;
import co.uniquindio.edu.sendifly.repositories.ShipmentRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class ShipmentService {

    private final ShipmentRepository shipmentRepository = ShipmentRepository.getInstance();


    public void createShipment(String origin, String destination, LocalDate orderDate, LocalTime deliveryDate,
                                 AdditionalService additionalService, ShippingStatus shippingStatus,
                                 ShippingPriority shippingPriority, Pack pack) {

        validateLocation(origin);
        validateLocation(destination);
        validateDates(orderDate, deliveryDate);
        String id = generateShipmentId();
        Shipment shipment = new Shipment.ShipmentBuilder()
                .id(id)
                .origin(origin)
                .destination(destination)
                .orderDate(orderDate)
                .deliveryDate(deliveryDate)
                .additionalService(additionalService)
                .shippingStatus(shippingStatus)
                .shippingPriority(shippingPriority)
                .pack(pack)
                .build();

        shipmentRepository.addShipment(shipment);
    }


    public Shipment getShipment(String id) {
        return shipmentRepository.getShipment(id)
                .orElseThrow(() -> new IllegalArgumentException("[ShipmentService] El envío no existe"));
    }


    public ArrayList<Shipment> getShipmentList() {
        return shipmentRepository.getAllShipments();
    }


    public void updateShipment(String id, Shipment nuevoEnvio) {
        Optional<Shipment> existente = shipmentRepository.getShipment(id);
        if (existente.isEmpty()) {
            throw new IllegalArgumentException("[ShipmentService] No se puede actualizar, el envío no existe");
        }
        shipmentRepository.removeShipment(existente.get());
        shipmentRepository.addShipment(nuevoEnvio);
    }


    public void DeleteShipment(String id) {
        Shipment shipment = getShipment(id);
        shipmentRepository.removeShipment(shipment);
    }


    public boolean existsShipment(String id) {
        return shipmentRepository.getShipment(id).isPresent();
    }


    public int countShipments() {
        return shipmentRepository.countShipments();
    }


    private void validateLocation(String location) {
        if (location == null || location.isBlank()) {
            throw new IllegalArgumentException("[ShipmentService] La locación no puede estar vacia");
        }
    }

    private void validateDates(LocalDate orderDate, LocalTime deliveryDate) {
        if (orderDate == null || deliveryDate == null) {
            throw new IllegalArgumentException("[ShipmentService] Dates must be provided");
        }if (orderDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("[ShipmentService] La fecha no puede estar en el futuro");}
    }

    private String generateShipmentId() {
        return "Envio: " + UUID.randomUUID();}

    public float calculateShippingPrice(Pack pack, AdditionalService additionalService, ShippingPriority priority) {
        if (pack == null || additionalService == null || priority == null) {
            throw new IllegalArgumentException("[ShipmentService] Missing data for price calculation");
        }
        float basePrice = pack.getPrice();
        float increaseByVolume = (pack.getVolume()) / 500f;
        float increaseByWeight = pack.getWeight() / 0.99f;
        float adjustedPrice = basePrice + basePrice * increaseByVolume * increaseByWeight;
        adjustedPrice += adjustedPrice * additionalService.rateIncrease();
        adjustedPrice += adjustedPrice * priority.rateIncrease();

        return adjustedPrice;
    }

}
