package co.uniquindio.edu.sendifly.services;

import co.uniquindio.edu.sendifly.models.Person;
import co.uniquindio.edu.sendifly.models.User;
import co.uniquindio.edu.sendifly.repositories.PersonRepository;

public class PersonService {
    private static PersonService instance;
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;

        initializedData();
    }

    public static PersonService getInstance() {
        if (instance == null) {
            instance = new PersonService(PersonRepository.getInstance());
        }
        return instance;
    }

    private void initializedData() {
        if(personRepository.countPeople() == 0){
            createPerson();
        }
    }

    public void createPerson(){
      //  Person p1= new User.UserBuilder().id()

    }


}



