package co.uniquindio.edu.sendifly.models;

import co.uniquindio.edu.sendifly.models.AdditionalServices.AdditionalService;
import co.uniquindio.edu.sendifly.models.Priorities.ShippingPriority;
import co.uniquindio.edu.sendifly.models.ShippingStatus.ShippingStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public class Shipment {
    private String id;
    private String origin;
    private String destination;
    private LocalDate orderDate;
    private LocalTime deliveryDate;
    private AdditionalService additionalService;
    private ShippingStatus shippingStatus;
    private ShippingPriority shippingPriority;
    private Pack pack;
    private float price;

    public Shipment(ShipmentBuilder build) {
        this.id = build.id;
        this.origin = build.origin;
        this.destination = build.destination;
        this.orderDate = build.orderDate;
        this.deliveryDate = build.deliveryDate;
        this.additionalService = build.additionalService;
        this.shippingStatus = build.shippingStatus;
        this.shippingPriority = build.shippingPriority;
        this.pack = build.pack;
        this.price= 10000F;
    }

    public static class ShipmentBuilder {
        public String id;
        public String origin;
        public String destination;
        public LocalDate orderDate;
        public LocalTime deliveryDate;
        public AdditionalService additionalService;
        public ShippingStatus shippingStatus;
        public ShippingPriority shippingPriority;
        public Pack pack;

        public ShipmentBuilder id(String id) {
            this.id = id;
            return this;}

        public ShipmentBuilder origin(String origin) {
            this.origin = origin;
            return this;}

        public ShipmentBuilder destination(String destination) {
            this.destination = destination;
            return this;}

        public ShipmentBuilder orderDate(LocalDate orderDate) {
            this.orderDate = orderDate;
            return this;}

        public ShipmentBuilder deliveryDate(LocalTime deliveryDate) {
            this.deliveryDate = deliveryDate;
            return this;}

        public ShipmentBuilder additionalService(AdditionalService additionalService) {
            this.additionalService = additionalService;
            return this;}

        public ShipmentBuilder shippingStatus(ShippingStatus shippingStatus) {
            this.shippingStatus = shippingStatus;
            return this;}

        public ShipmentBuilder shippingPriority(ShippingPriority shippingPriority) {
            this.shippingPriority = shippingPriority;
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

    public String getOrigin() {
        return origin;}

    public void setOrigin(String origin) {
        this.origin = origin;}

    public String getDestination() {
        return destination;}

    public void setDestination(String destination) {
        this.destination = destination;}

    public LocalDate getOrderDate() {
        return orderDate;}

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;}

    public LocalTime getDeliveryDate() {
        return deliveryDate;}

    public void setDeliveryDate(LocalTime deliveryDate) {
        this.deliveryDate = deliveryDate;}

    public AdditionalService getAdditionalService() {
        return additionalService;}

    public void setAdditionalService(AdditionalService additionalService) {
        this.additionalService = additionalService;}

    public ShippingStatus getShippingStatus() {
        return shippingStatus;}

    public void setShippingStatus(ShippingStatus shippingStatus) {
        this.shippingStatus = shippingStatus;}

    public ShippingPriority getShippingPriority() {
        return shippingPriority;}

    public void setShippingPriority(ShippingPriority shippingPriority) {
        this.shippingPriority = shippingPriority;}

    public Pack getPack() {
        return pack;}

    public void setPack(Pack pack) {
        this.pack = pack;}

    // public float getShippingPrice(){
        //float price= this.pack.getPrice();
        // float increaseByVolume= (this.pack.getHeight()*this.pack.getWidth()*this.pack.getLength())/500;
        // float increaseByWeight= (float) (this.pack.getWeight()/0.99);
        // price= price + price * increaseByVolume * increaseByWeight;
        //price= price + price * this.additionalService.rateIncrease();
       // return price + price * this.shippingPriority.rateIncrease();
   // }


}
