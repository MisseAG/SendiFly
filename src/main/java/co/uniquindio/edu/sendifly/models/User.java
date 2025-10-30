package co.uniquindio.edu.sendifly.models;

import co.uniquindio.edu.sendifly.models.PaymenMethods.PaymentMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User extends Person{
    private List<PaymentMethod> paymentMethods;

    public User(Builder build) {
        super(build.id, build.name, build.phone, build.email, build.password);
        this.paymentMethods = new ArrayList<>();
    }

    public static class Builder{
        private String id;
        private String name;
        private String phone;
        private String email;
        private String password;

        public Builder id(String id){
            this.id = id;
            return this;}

        public Builder name(String name){
            this.name = name;
            return this;}

        public Builder phone(String phone){
            this.phone = phone;
            return this;}

        public Builder email(String email){
            this.email = email;
            return this;}

        public Builder password(String password){
            this.password = password;
            return this;}

        public User  build(){
            return new User(this);
        }
    }

    public void makePayment(String methodName, double amount) {
        for (PaymentMethod pm : paymentMethods) {
            if (pm.getName().equalsIgnoreCase(methodName)) {
                pm.pay(amount);
                return;
            }
        }
        throw new IllegalArgumentException("[User]Método de pago no encontrado: " + methodName);
    }

}
