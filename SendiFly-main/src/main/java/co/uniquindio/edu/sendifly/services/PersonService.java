package co.uniquindio.edu.sendifly.services;

import co.uniquindio.edu.sendifly.models.*;
import co.uniquindio.edu.sendifly.repositories.PersonRepository;

import java.util.Optional;
import java.util.UUID;
/**
 * Servicio para gestionar la lógica de negocio de Personas.
 * Implementa el patrón Singleton para garantizar una única instancia.
 */
public class PersonService {
    private static PersonService instance;
    private final PersonRepository personRepository;

    private PersonService(PersonRepository personRepository) {
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
      //Crear direcciones de prueba
      createTestAddresses((User) p2);

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

    /**
     * Crea direcciones de prueba para un usuario
     */
    private void createTestAddresses(User user) {
        // Dirección 1: Casa
        Address casa = new Address.AddressBuilder()
                .id("addr_001")
                .alias("Casa")
                .street("Calle 15 #23-45")
                .city("Armenia")
                .coordinates(4.5339, -75.6811)
                .build();

        // Dirección 2: Oficina
        Address oficina = new Address.AddressBuilder()
                .id("addr_002")
                .alias("Oficina")
                .street("Carrera 14 #11-20")
                .city("Armenia")
                .coordinates(20, -20)
                .build();

        // Dirección 3: Casa de Padres
        Address padres = new Address.AddressBuilder()
                .id("addr_003")
                .alias("Casa Padres")
                .street("Calle 20 Norte #8-34")
                .city("Calarcá")
                .coordinates(25, -15)
                .build();

        // Dirección 4: Bodega
        Address bodega = new Address.AddressBuilder()
                .id("addr_004")
                .alias("Bodega")
                .street("Zona Industrial, Bodega 5")
                .city("La Tebaida")
                .coordinates(30, 10)
                .build();

        // Dirección 5: Finca
        Address finca = new Address.AddressBuilder()
                .id("addr_005")
                .alias("Finca")
                .street("Vereda La Bella, Km 3")
                .city("Montenegro")
                .coordinates(5, 12)
                .build();

        // Agregar todas las direcciones al usuario
        try {
            user.addAddress(casa);
            user.addAddress(oficina);
            user.addAddress(padres);
            user.addAddress(bodega);
            user.addAddress(finca);

            System.out.println("✓ Direcciones de prueba agregadas a " + user.getName());

        } catch (IllegalArgumentException e) {
            System.err.println("Error al agregar direcciones: " + e.getMessage());
        }
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

    public boolean updateUserProfile(String userId, String name, String email, String password) {
        Optional<Person> personOpt = personRepository.getPerson(userId);

        if (personOpt.isPresent() && personOpt.get() instanceof User) {
            User user = (User) personOpt.get();

            user.setName(name);
            user.setEmail(email);

            if (password != null && !password.isEmpty()) {
                user.setPassword(password);
            }

            System.out.println("Perfil actualizado");
            return true;
        }

        System.err.println("Usuario no encontrado");
        return false;
    }

    public Optional<User> getUserById(String userId) {
        Optional<Person> personOpt = personRepository.getPerson(userId);

        if (personOpt.isPresent() && personOpt.get() instanceof User) {
            return Optional.of((User) personOpt.get());
        }

        return Optional.empty();
    }
}



