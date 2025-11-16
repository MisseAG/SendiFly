package co.uniquindio.edu.sendifly.models;

public class Fee {
    private float distance;
    private Shipment shipment;

    public Fee(FeeBuilder build){
        this.distance = build.distance;
        this.shipment = build.shipment;
    }

    public static class FeeBuilder{
        private float distance;
        private Shipment shipment;

        public FeeBuilder distance(float distance){
            this.distance = distance;
            return this;}

        public FeeBuilder shipment(Shipment shipment){
            this.shipment = shipment;
            return this;}

        public Fee build(){
            return new Fee(this);
        }
    }

    public float getDistance() {
        return distance;}

    public void setDistance(float distance) {
        this.distance = distance;}

    public Shipment getShipment() {
        return shipment;}

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;}

    public float getPercentageOfCostIncrease() {
        float contador= 0;
        float percentage= 0;
        while(contador<this.distance){
            contador= contador + 100;
            percentage= percentage + 0.005F;
        }
        return percentage;
    }

//    public float getTotalPrice(){
//        return this.shipment.getShippingPrice() + this.shipment.getShippingPrice() * getPercentageOfCostIncrease();
//    }



}
