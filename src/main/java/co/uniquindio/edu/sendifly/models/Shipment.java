package co.uniquindio.edu.sendifly.models;

import co.uniquindio.edu.sendifly.models.AdditionalServices.AdditionalService;
import co.uniquindio.edu.sendifly.models.ShippingStatus.ShippingStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Shipment {
    private String id;
    private Address origin;
    private Address destination;
    private LocalDate orderDate;
    private LocalDateTime deliveryDate;
    private double price;
    private AdditionalService additionalService;
    private ShippingStatus shippingStatus;
    private Pack pack;


    public Shipment(ShipmentBuilder build) {
        this.id = build.id;
        this.origin = build.origin;
        this.destination = build.destination;
        this.orderDate = build.orderDate;
        this.deliveryDate = build.deliveryDate;
        this.price = build.price;
        this.additionalService = build.additionalService;
        this.shippingStatus = build.shippingStatus;
        this.pack = build.pack;
    }

    public static class ShipmentBuilder {
        public String id;
        public Address origin;
        public Address destination;
        public LocalDate orderDate;
        public LocalDateTime deliveryDate;
        public double price;
        public AdditionalService additionalService;
        public ShippingStatus shippingStatus;
        public Pack pack;

        public ShipmentBuilder id(String id) {
            this.id = id;
            return this;}

        public ShipmentBuilder origin(Address origin) {
            this.origin = origin;
            return this;}

        public ShipmentBuilder destination(Address destination) {
            this.destination = destination;
            return this;}

        public ShipmentBuilder orderDate(LocalDate orderDate) {
            this.orderDate = orderDate;
            return this;}

        public ShipmentBuilder deliveryDate(LocalDateTime deliveryDate) {
            this.deliveryDate = deliveryDate;
            return this;}

        public ShipmentBuilder price(double price) {
            this.price = price;
            return this;}

        public ShipmentBuilder additionalService(AdditionalService additionalService) {
            this.additionalService = additionalService;
            return this;}

        public ShipmentBuilder shippingStatus(ShippingStatus shippingStatus) {
            this.shippingStatus = shippingStatus;
            return this;}


        public ShipmentBuilder pack(Pack pack) {
            this.pack = pack;
            return this;}

        public Shipment build() {
            return new Shipment(this);
        }
    }

    public String getId() {
        return id;}

    public void setId(String id) {
        this.id = id;}

    public Address getOrigin() {
        return origin;}

    public void setOrigin(Address origin) {
        this.origin = origin;}

    public Address getDestination() {
        return destination;}

    public void setDestination(Address destination) {
        this.destination = destination;}

    public LocalDate getOrderDate() {
        return orderDate;}

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;}

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;}

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;}

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public AdditionalService getAdditionalService() {
        return additionalService;}

    public void setAdditionalService(AdditionalService additionalService) {
        this.additionalService = additionalService;}

    public ShippingStatus getShippingStatus() {
        return shippingStatus;}

    public void setShippingStatus(ShippingStatus shippingStatus) {
        this.shippingStatus = shippingStatus;}

    public Pack getPack() {
        return pack;}

    public void setPack(Pack pack) {
        this.pack = pack;}


    public boolean isDelivered() {
        return shippingStatus.getName().equals("Delivered");
    }

    public double getTotalWeight() {
        if (pack != null){
            return pack.getWeight();
        }
        return 0;
    }



}
