package co.uniquindio.edu.sendifly.models;

import co.uniquindio.edu.sendifly.models.AvailabilityStatus.AvailabilityStatus;
import co.uniquindio.edu.sendifly.models.AvailabilityStatus.Inactive;

public class DeliveryMan extends Person{
    private AvailabilityStatus availabilityStatus;

    public DeliveryMan(DeliveryManBuilder build) {
        super(build.id, build.name, build.phone, build.email, build.password);
        this.availabilityStatus = build.availabilityStatus;
    }

    public AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public static class DeliveryManBuilder {
        private String id;
        private String name;
        private String phone;
        private String email;
        private String password;
        private AvailabilityStatus availabilityStatus = new Inactive();

        public DeliveryManBuilder id(String id){
            this.id = id;
            return this;}

        public DeliveryManBuilder name(String name){
            this.name = name;
            return this;}

        public DeliveryManBuilder phone(String phone){
            this.phone = phone;
            return this;}

        public DeliveryManBuilder email(String email){
            this.email = email;
            return this;}

        public DeliveryManBuilder password(String password){
            this.password = password;
            return this;}

        public DeliveryManBuilder availabilityStatus(AvailabilityStatus availabilityStatus){
            this.availabilityStatus = availabilityStatus;
            return this;}

        public DeliveryMan build(){
            if (this.availabilityStatus == null) {
                this.availabilityStatus = new Inactive();
            }
            return new DeliveryMan(this);
        }
    }

}
