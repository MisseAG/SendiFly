package co.uniquindio.edu.sendifly.models;

import co.uniquindio.edu.sendifly.models.PaymentMethods.PaymentMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class User extends Person{
    private List<PaymentMethod> paymentMethodsList;
    private List<Address> addressesList;
    private List<Shipment> shipmentsList;

    public User(UserBuilder build) {
        super(build.id, build.name, build.phone, build.email, build.password);
        this.paymentMethodsList = new ArrayList<>();
        this.addressesList = new ArrayList<>();
        this.shipmentsList = new ArrayList<>();
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

    public List<Shipment> getShipmentsList() {
        return shipmentsList;
    }

    public void setShipmentsList(List<Shipment> shipmentsList) {
        this.shipmentsList = shipmentsList;
    }

    //Gestión de direcciones


    /**
     * Agrega una nueva dirección al usuario
     * @throws IllegalArgumentException si el alias ya existe
     */
    public void addAddress(Address address) {
        if (address == null) {
            throw new IllegalArgumentException("[User] La dirección no puede ser nula");
        }

        // Verificar que el alias no exista
        if (findAddressByAlias(address.getAlias()).isPresent()) {
            throw new IllegalArgumentException(
                    "[User] Ya existe una dirección con el alias '" + address.getAlias() + "'");
        }

        addressesList.add(address);
    }

    /**
     * Elimina una dirección por su alias
     * @return true si se eliminó, false si no existía
     */
    public boolean removeAddressByAlias(String alias) {
        return addressesList.removeIf(addr -> addr.getAlias().equalsIgnoreCase(alias));
    }

    /**
     * Elimina una dirección específica
     */
    public boolean removeAddress(Address address) {
        return addressesList.remove(address);
    }

    /**
     * Busca una dirección por su alias
     */
    public Optional<Address> findAddressByAlias(String alias) {
        if (alias == null || alias.trim().isEmpty()) {
            return Optional.empty();
        }

        return addressesList.stream()
                .filter(addr -> alias.equalsIgnoreCase(addr.getAlias()))
                .findFirst();
    }

    /**
     * Busca una dirección por su ID
     */
    public Optional<Address> findAddressById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return Optional.empty();
        }

        return addressesList.stream()
                .filter(addr -> id.equals(addr.getId()))
                .findFirst();
    }

    /**
     * Actualiza una dirección existente
     */
    public void updateAddress(String alias, Address updatedAddress) {
        Optional<Address> existing = findAddressByAlias(alias);

        if (existing.isEmpty()) {
            throw new IllegalArgumentException(
                    "[User] No existe una dirección con el alias '" + alias + "'");
        }

        // Si cambió el alias, verificar que el nuevo no exista
        if (!alias.equalsIgnoreCase(updatedAddress.getAlias())) {
            if (findAddressByAlias(updatedAddress.getAlias()).isPresent()) {
                throw new IllegalArgumentException(
                        "[User] El nuevo alias '" + updatedAddress.getAlias() + "' ya está en uso");
            }
        }

        addressesList.remove(existing.get());
        addressesList.add(updatedAddress);
    }

    /**
     * Verifica si el usuario tiene una dirección con ese alias
     */
    public boolean hasAddress(String alias) {
        return findAddressByAlias(alias).isPresent();
    }

    /**
     * Cuenta las direcciones del usuario
     */
    public int getAddressCount() {
        return addressesList.size();
    }

    //Gestión De envíos

    /**
     * Agrega un envío a la lista del usuario
     */
    public void addShipment(Shipment shipment) {
        if (shipment == null) {
            throw new IllegalArgumentException("[User] El envío no puede ser nulo");
        }
        shipmentsList.add(shipment);
    }

    /**
     * Elimina un envío de la lista del usuario
     */
    public boolean removeShipment(Shipment shipment) {
        return shipmentsList.remove(shipment);
    }

    // Pagos

    public void makePayment(String methodName, double amount) {
        for (PaymentMethod pm : paymentMethodsList) {
            if (pm.getName().equalsIgnoreCase(methodName)) {
                pm.pay(amount);
                return;
            }
        }
        throw new IllegalArgumentException("[User] Método de pago no encontrado: " + methodName);
    }


    //Builder
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

}
