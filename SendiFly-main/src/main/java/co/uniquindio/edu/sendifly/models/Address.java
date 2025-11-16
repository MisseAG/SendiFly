package co.uniquindio.edu.sendifly.models;

public class Address {
    private String id;
    private String alias;
    private String street;
    private String city;
    private double latitude;   // Coordenada latitud
    private double longitude;  // Coordenada longitud

    public Address(AddressBuilder build){
        this.id = build.id;
        this.alias = build.alias;
        this.street = build.street;
        this.city = build.city;
        this.latitude = build.latitude;
        this.longitude = build.longitude;
    }

    public static class AddressBuilder {
        private String id;
        private String alias;
        private String street;
        private String city;
        private double latitude = 0.0;   // Valor por defecto
        private double longitude = 0.0;  // Valor por defecto

        public AddressBuilder id(String id){
            this.id = id;
            return this;
        }

        public AddressBuilder alias(String alias){
            this.alias = alias;
            return this;
        }

        public AddressBuilder street(String street){
            this.street = street;
            return this;
        }

        public AddressBuilder city(String city){
            this.city = city;
            return this;
        }

        public AddressBuilder latitude(double latitude){
            this.latitude = latitude;
            return this;
        }

        public AddressBuilder longitude(double longitude){
            this.longitude = longitude;
            return this;
        }

        // Establecer ambas coordenadas a la vez
        public AddressBuilder coordinates(double latitude, double longitude){
            this.latitude = latitude;
            this.longitude = longitude;
            return this;
        }

        public Address build(){
            return new Address(this);
        }
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // Obtener ambas coordenadas como array
    public double[] getCoordinates() {
        return new double[]{latitude, longitude};
    }

    // Calcular distancia aproximada entre dos direcciones con hipotenusa
    public double calculateDistanceTo(Address other) {
        double latDiff = this.latitude - other.latitude;
        double lonDiff = this.longitude - other.longitude;
        return Math.sqrt(latDiff * latDiff + lonDiff * lonDiff);
    }
}