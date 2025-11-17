package co.uniquindio.edu.sendifly.repositories;

import co.uniquindio.edu.sendifly.models.Person;

import java.util.ArrayList;
import java.util.Optional;

public class PersonRepository {

    private static PersonRepository instance;

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
        System.out.println("Agregando persona: " + person.getEmail());
        for(Person p: People){
            if(p.getId().equals(person.getId())){
                throw new IllegalArgumentException("[PersonRepository]el usuario ya existe");

            }
        }
        People.add(person);
        System.out.println("Total personas ahora: " + People.size());
    }

    public void removePerson(Person person){
        for(Person p: People){
            if(p.getId().equals(person.getId())){
                People.remove(p);
                return;
            }
        }throw new IllegalArgumentException("[PersonRepository]el usuario No existe");
    }

    public boolean updatePerson(Person updatedPerson) {
        if (updatedPerson == null) {
            throw new IllegalArgumentException("[PersonRepository] La persona no puede ser nula");
        }

        // Buscar la persona por ID
        for (int i = 0; i < People.size(); i++) {
            if (People.get(i).getId().equals(updatedPerson.getId())) {
                // Reemplazar la persona en la lista
                People.set(i, updatedPerson);
                System.out.println("[PersonRepository] Persona actualizada: " + updatedPerson.getEmail());
                return true;
            }
        }

        // Si llegamos aquí, la persona no existe
        throw new IllegalArgumentException(
                "[PersonRepository] No se puede actualizar: la persona con ID '" +
                        updatedPerson.getId() + "' no existe");
    }

    public Optional<Person> getPerson(String id) {
        for (Person p : People) {
            if (p.getId().equals(id)) {
                return Optional.of(p);
            }
        }
        return Optional.empty(); // optional es clave, pues service ya decide que hacer si el usuario no existe ;)
    }

    public ArrayList<Person> getAll(){
        return new ArrayList<>(People);
    }

    public int countPeople(){
        return People.size();
    }


    public boolean existPerson(Person person) {
        if (person == null) {
            return false;
        }
        return true;
    }

    public Person findByEmail(String email) {
        for (Person p : People) {
            if (p.getEmail().equals(email)) {
                return p;
            }
        }
        return null;
    }

    public boolean existsByEmail(String email) {
        return findByEmail(email) != null;
    }


    public boolean isEmailTakenByOther(String email, String excludeId) {
        for (Person p : People) {
            if (p.getEmail().equals(email) && !p.getId().equals(excludeId)) {
                return true;
            }
        }
        return false;
    }
}
