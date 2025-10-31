package co.uniquindio.edu.sendifly.models;

public class DeliveryMan extends Person{


    public DeliveryMan(DeliveryManBuilder build) {
        super(build.id, build.name, build.phone, build.email, build.password);
    }

    public static class DeliveryManBuilder {
        private String id;
        private String name;
        private String phone;
        private String email;
        private String password;

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

        public DeliveryMan build(){
            return new DeliveryMan(this);
        }
    }

}
