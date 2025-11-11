package co.uniquindio.edu.sendifly.models;

import co.uniquindio.edu.sendifly.models.PaymentMethods.PaymentMethod;

import java.util.ArrayList;
import java.util.List;

public class User extends Person{
    private List<PaymentMethod> paymentMethodsList;
    private List<Address> addressesList;

    //private list<envíos> envíos, los que aún no están asignados, que se muestran en la tabla para poder ser modificados

    public User(UserBuilder build) {
        super(build.id, build.name, build.phone, build.email, build.password);
        this.paymentMethodsList = new ArrayList<>();
        this.addressesList = new ArrayList<>();
    }

    public List<Address> getAddressesList() {
        return addressesList;
    }

    public void setAddressesList(List<Address> addressesList) {
        this.addressesList = addressesList;
    }

    public List<PaymentMethod> getPaymentMethodsList() {
        return paymentMethodsList;
    }

    public void setPaymentMethodsList(List<PaymentMethod> paymentMethodsList) {
        this.paymentMethodsList = paymentMethodsList;
    }

    public static class UserBuilder {
        private String id;
        private String name;
        private String phone;
        private String email;
        private String password;

        public UserBuilder id(String id){
            this.id = id;
            return this;}

        public UserBuilder name(String name){
            this.name = name;
            return this;}

        public UserBuilder phone(String phone){
            this.phone = phone;
            return this;}

        public UserBuilder email(String email){
            this.email = email;
            return this;}

        public UserBuilder password(String password){
            this.password = password;
            return this;}

        public User  build(){
            return new User(this);
        }
    }

    public void makePayment(String methodName, double amount) {
        for (PaymentMethod pm : paymentMethodsList) {
            if (pm.getName().equalsIgnoreCase(methodName)) {
                pm.pay(amount);
                return;
            }
        }
        throw new IllegalArgumentException("[User]Método de pago no encontrado: " + methodName);
    }

    //falta agregar lo de las direcciones

}
