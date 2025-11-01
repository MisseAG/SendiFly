package co.uniquindio.edu.sendifly.repositories;

import co.uniquindio.edu.sendifly.models.Person;

import java.util.ArrayList;
import java.util.Optional;

public class PersonRepository {

    private static PersonRepository instance= new PersonRepository();

    private ArrayList<Person> People;

    private PersonRepository() {
        this.People = new ArrayList<>();
    }

    //Singleton

    public static PersonRepository getInstance(){
        if(instance == null){
            instance = new PersonRepository();
        }
        return instance;
    }

    public void addPerson(Person person){
        for(Person p: People){
            if(p.getId().equals(person.getId())){
                throw new IllegalArgumentException("[UserRepository]el usuario ya existe");

            }
        }
        People.add(person);
    }

    public void removePerson(Person person){
        for(Person p: People){
            if(p.getId().equals(person.getId())){
                People.remove(p);
                return;
            }
        }throw new IllegalArgumentException("[UserRepository]el usuario No existe");
    }

    //falta update en service

    public Optional<Person> getPerson(String id) {
        for (Person p : People) {
            if (p.getId().equals(id)) {
                return Optional.of(p);
            }
        }
        return Optional.empty(); // optional es clave, pues service ya decide que hacer si el usuario no existe ;)
    }

    public ArrayList<Person> getAllPeople(){
        return new ArrayList<>(People);
    }

    public int countPeople(){
        return People.size();
    }


    public boolean existPerson(String id) {
        return getPerson(id).isPresent();
    }

}
