package co.uniquindio.edu.sendifly.models;

public abstract class Person {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String password;

    public Person(String id, String name, String phone, String email, String password) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;}

    public String getId() {
        return id;}

    public void setId(String id) {
        this.id = id;}

    public String getName() {
        return name;}

    public void setName(String name) {
        this.name = name;}

    public String getPhone() {
        return phone;}

    public void setPhone(String phone) {
        this.phone = phone;}

    public String getEmail() {
        return email;}

    public void setEmail(String email) {
        this.email = email;}


    public String getPassword() {
        return password;}

    public void setPassword(String password) {
        this.password = password;}

    @Override
    public String toString() {
        return "ID: " + id + "\nName: " + name +
                "\nPhone: " + phone + "\nEmail: " + email +
                "\nPassword: " + password + "\n";
    }



}
