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
      Person p1= new User.UserBuilder().id("1029482339").
              name("Juan").
              phone("3148846678").
              email("juanprogamer@gmail.com").
              password("12345").
              build();
      Person p2= new User.UserBuilder().id("1982822057").
                name("Antonio").
                phone("314722894").
                email("antonio777@gmail.com").
                password("67890").
                build();
      Person p3= new User.UserBuilder().id("1096449205").
                name("Pacho").
                phone("3119430578").
                email("pachitoopresor@gmail.com").
                password("10293").
                build();

    }


}



