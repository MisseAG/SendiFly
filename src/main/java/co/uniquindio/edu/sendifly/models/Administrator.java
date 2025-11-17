package co.uniquindio.edu.sendifly.models;

public class Administrator extends Person{


    public Administrator(AdministratorBuilder build) {
        super(build.id, build.name, build.phone, build.email, build.password);
    }

    @Override
    public String getRol() {
        return "Admin";
    }

    public static class AdministratorBuilder{
        private String id;
        private String name;
        private String phone;
        private String email;
        private String password;

        public AdministratorBuilder id(String id){
            this.id = id;
            return this;}

        public AdministratorBuilder name(String name){
            this.name = name;
            return this;}

        public AdministratorBuilder phone(String phone){
            this.phone = phone;
            return this;}

        public AdministratorBuilder email(String email){
            this.email = email;
            return this;}

        public AdministratorBuilder password(String password){
            this.password = password;
            return this;}

        public Administrator  build(){
            return new Administrator(this);
        }
    }

}
