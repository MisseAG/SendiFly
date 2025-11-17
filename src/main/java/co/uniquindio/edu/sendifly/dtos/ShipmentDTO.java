package co.uniquindio.edu.sendifly.dtos;

import co.uniquindio.edu.sendifly.models.Address;
import co.uniquindio.edu.sendifly.models.Shipment;
import co.uniquindio.edu.sendifly.models.ShippingStatus.ShippingStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ShipmentDTO {

    private String id;
    private String originAlias;
    private String destinationAlias;
    private LocalDate orderDate;
    private LocalDateTime deliveryDate;
    private double price;
    private String status;

    public ShipmentDTO() {
    }

    public ShipmentDTO(String id, String originAlias, String destinationAlias,
                       LocalDate orderDate, LocalDateTime deliveryDate,
                       double price, String status) {
        this.id = id;
        this.originAlias = originAlias;
        this.destinationAlias = destinationAlias;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.price = price;
        this.status = status;
    }

    public static ShipmentDTO fromShipment(Shipment shipment) {
        Address origin = shipment.getOrigin();
        Address destination = shipment.getDestination();
        ShippingStatus status = shipment.getShippingStatus();

        return new ShipmentDTO(
                shipment.getId(),
                origin != null ? origin.getAlias() : "N/A",
                destination != null ? destination.getAlias() : "N/A",
                shipment.getOrderDate(),
                shipment.getDeliveryDate(),
                shipment.getPrice(),
                status != null ? status.getName() : "Sin estado"
        );
    }

    public String getId() {
        return id;
    }

    public String getOriginAlias() {
        return originAlias;
    }

    public String getDestinationAlias() {
        return destinationAlias;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOriginAlias(String originAlias) {
        this.originAlias = originAlias;
    }

    public void setDestinationAlias(String destinationAlias) {
        this.destinationAlias = destinationAlias;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}