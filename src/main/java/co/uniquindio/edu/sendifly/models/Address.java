package co.uniquindio.edu.sendifly.models;

public class Address {
    private String id;
    private String alias;
    private String street;
    private String city;

    public Address(AddressBuilder build){
        this.id = build.id;
        this.alias = build.alias;
        this.street = build.street;
        this.city = build.city;
    }

    public static class AddressBuilder {
        private String id;
        private String alias;
        private String street;
        private String city;

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

        public Address build(){
            return new Address(this);}
    }

    public String getId() {
        return id;}

    public void setId(String id) {
        this.id = id;}

    public String getAlias() {
        return alias;}

    public void setAlias(String alias) {
        this.alias = alias;}

    public String getStreet() {
        return street;}

    public void setStreet(String street) {
        this.street = street;}

    public String getCity() {
        return city;}

    public void setCity(String city) {
        this.city = city;}
}
