package co.uniquindio.edu.sendifly.repositories;

import co.uniquindio.edu.sendifly.models.User;

import java.util.ArrayList;
import java.util.Optional;

/*
* Clase repository que almacena arraylist de users e implementa funciones básicas
* */

public class UserRepository {

    private static UserRepository instance= new UserRepository();

    private  ArrayList<User> users;

    private UserRepository() {
        this.users = new ArrayList<>();
    }

    //Singleton

    public static UserRepository getInstance(){
        if(instance == null){
            instance = new UserRepository();
        }
        return instance;
    }

    public void addUser(User user){
        for(User u:users){
            if(u.getId().equals(user.getId())){
                throw new IllegalArgumentException("[UserRepository]el usuario ya existe");

            }
        }users.add(user);
    }

    public void removeUser(User user){
        for(User u:users){
            if(u.getId().equals(user.getId())){
                users.remove(u);
                return;
            }
        }throw new IllegalArgumentException("[UserRepository]el usuario No existe");
    }

    //falta update en service

    public Optional<User> getUser(String id) {
        for (User u : users) {
            if (u.getId().equals(id)) {
                return Optional.of(u);
            }
        }
        return Optional.empty(); // optional es clave, pues service ya decide que hacer si el usuario no existe ;)
    }

    public ArrayList<User> getAllUsers(){
        return new ArrayList<>(users);
    }

    public int countUsers(){
        return users.size();
    }


    public boolean existsUser(String id) {
        return getUser(id).isPresent();
    }

    //TODO: public List<User> findByRole(String role);
    //TODO: public List<User> findByEmail(String email);


}
