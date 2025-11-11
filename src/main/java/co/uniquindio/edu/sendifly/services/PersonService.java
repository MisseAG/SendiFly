package co.uniquindio.edu.sendifly.services;

import co.uniquindio.edu.sendifly.models.Administrator;
import co.uniquindio.edu.sendifly.models.AvailabilityStatus.AvailabilityStatus;
import co.uniquindio.edu.sendifly.models.DeliveryMan;
import co.uniquindio.edu.sendifly.models.Person;
import co.uniquindio.edu.sendifly.models.User;
import co.uniquindio.edu.sendifly.repositories.PersonRepository;

import java.util.UUID;

public class PersonService {
    private static PersonService instance;
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
        System.out.println("PersonService creado. Repository: " + personRepository);
        initializedData();
    }

    public static PersonService getInstance() {
        if (instance == null) {
            instance = new PersonService(PersonRepository.getInstance());
        }
        return instance;
    }

    private void initializedData() {
        createPerson();
        System.out.println("Personas guardadas: " + personRepository.getAllPeople().size());
    }

    private void createPerson(){
      Person p1= new Administrator.AdministratorBuilder().id("1029482339").
              name("Juan").
              phone("3148846678").
              email("juan@mail.com").
              password("123456").
              build();
      Person p2= new User.UserBuilder().id("1982822057").
                name("Antonio").
                phone("314722894").
                email("antonio@mail.com").
                password("567890").
                build();
      Person p3= new DeliveryMan.DeliveryManBuilder().id("1096449205").
                name("Pacho").
                phone("3119430578").
                email("pacho@mail.com").
                password("345678").
                build();

      personRepository.addPerson(p1);
      personRepository.addPerson(p2);
      personRepository.addPerson(p3);

      System.out.println("Personas en repositorio: " + personRepository.getAllPeople().size());
        personRepository.getAllPeople().forEach(p ->
                System.out.println("Email: " + p.getEmail() + " | Password: " + p.getPassword())
        );
    }

    public void registerPerson(String name, String email, String phone, String password, String role) {
        validateEmail(email);
        validatePassword(password);

        if (emailExists(email)) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        Person person = switch (role.toLowerCase()) {
            case "administrador" -> new Administrator.AdministratorBuilder().id(generateIdAdmin())
                    .name(name)
                    .phone(phone)
                    .email(email)
                    .password(password)
                    .build();

            case "repartidor" -> new DeliveryMan.DeliveryManBuilder().id(generateIdDelivery())
                    .name(name)
                    .phone(phone)
                    .email(email)
                    .password(password)
                    .build();

            case "usuario" -> new User.UserBuilder().id(generateIdUser())
                    .name(name)
                    .phone(phone)
                    .email(email)
                    .password(password)
                    .build();

            default -> throw new IllegalArgumentException("Rol no válido: " + role);
        };

        // Guardar
        personRepository.addPerson(person);
    }

    private void validateEmail(String email){
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Email inválido");
        }
    }
    private void validatePassword(String password){
        if (password.length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
        }
    }

    private boolean emailExists(String email) {
        return personRepository.findByEmail(email) != null;
    }

    private String generateId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    private String generateIdUser() {
        return "User_" + generateId();
    }

    private String generateIdAdmin() {
        return "Admin_" + generateId();
    }

    private String generateIdDelivery() {
        return "DelM_" + generateId();
    }
}



