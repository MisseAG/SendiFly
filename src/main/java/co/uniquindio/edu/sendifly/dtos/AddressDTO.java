package co.uniquindio.edu.sendifly.dtos;

public class AddressDTO {
    private String id;
    private String alias;
    private String street;
    private String city;

    public AddressDTO(String id, String alias, String street, String city) {
        this.id = id;
        this.alias = alias;
        this.street = street;
        this.city = city;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    @Override
    public String toString() {
        return alias + " - " + street + ", "+city;
    }
}
