package co.uniquindio.edu.sendifly.services;

import co.uniquindio.edu.sendifly.models.*;
import co.uniquindio.edu.sendifly.models.AvailabilityStatus.AvailabilityStatus;
import co.uniquindio.edu.sendifly.repositories.PersonRepository;

import java.util.ArrayList;
import java.util.List;
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
        System.out.println("Personas guardadas: " + personRepository.getAll().size());
    }

    private void createPerson() {
        Person p1 = new Administrator.AdministratorBuilder()
                .id("1029482339")
                .name("Juan")
                .phone("3148846678")
                .email("juan@mail.com")
                .password("123456")
                .build();

        Person p2 = new User.UserBuilder()
                .id("1982822057")
                .name("Antonio")
                .phone("314722894")
                .email("antonio@mail.com")
                .password("567890")
                .build();

        createTestAddresses((User) p2);

        Person p3 = new DeliveryMan.DeliveryManBuilder()
                .id("1096449205")
                .name("Pacho")
                .phone("3119430578")
                .email("pacho@mail.com")
                .password("345678")
                .build();

        personRepository.addPerson(p1);
        personRepository.addPerson(p2);
        personRepository.addPerson(p3);

        System.out.println("Personas en repositorio: " + personRepository.getAll().size());
        personRepository.getAll().forEach(p ->
                System.out.println("Email: " + p.getEmail() + " | Password: " + p.getPassword())
        );
    }

    /**
     * Crea direcciones de prueba para un usuario
     */
    private void createTestAddresses(User user) {
        Address casa = new Address.AddressBuilder()
                .id("addr_001")
                .alias("Casa")
                .street("Calle 15 #23-45")
                .city("Armenia")
                .coordinates(4.5339, -75.6811)
                .build();

        Address oficina = new Address.AddressBuilder()
                .id("addr_002")
                .alias("Oficina")
                .street("Carrera 14 #11-20")
                .city("Armenia")
                .coordinates(20, -20)
                .build();

        Address padres = new Address.AddressBuilder()
                .id("addr_003")
                .alias("Casa Padres")
                .street("Calle 20 Norte #8-34")
                .city("Calarcá")
                .coordinates(25, -15)
                .build();

        Address bodega = new Address.AddressBuilder()
                .id("addr_004")
                .alias("Bodega")
                .street("Zona Industrial, Bodega 5")
                .city("La Tebaida")
                .coordinates(30, 10)
                .build();

        Address finca = new Address.AddressBuilder()
                .id("addr_005")
                .alias("Finca")
                .street("Vereda La Bella, Km 3")
                .city("Montenegro")
                .coordinates(5, 12)
                .build();

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
        validatePhone(phone);

        if (emailExists(email)) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        Person person = switch (role.toLowerCase()) {
            case "administrador" -> new Administrator.AdministratorBuilder()
                    .id(generateIdAdmin())
                    .name(name)
                    .phone(phone)
                    .email(email)
                    .password(password)
                    .build();

            case "repartidor" -> new DeliveryMan.DeliveryManBuilder()
                    .id(generateIdDelivery())
                    .name(name)
                    .phone(phone)
                    .email(email)
                    .password(password)
                    .build();

            case "usuario" -> new User.UserBuilder()
                    .id(generateIdUser())
                    .name(name)
                    .phone(phone)
                    .email(email)
                    .password(password)
                    .build();

            default -> throw new IllegalArgumentException("Rol no válido: " + role);
        };

        personRepository.addPerson(person);
    }

    public void updatePerson(Person person) {
        if (!personRepository.existPerson(person)) {
            throw new IllegalArgumentException("No existe");
        }

        String email = person.getEmail();
        String id = person.getId();

        if (personRepository.isEmailTakenByOther(email, id)) {
            throw new IllegalArgumentException("Email en uso");
        }

        validateEmail(email);
        validatePassword(person.getPassword());

        personRepository.updatePerson(person);
    }

    public boolean emailExists(String email) {
        return personRepository.findByEmail(email) != null;
    }

    public boolean emailExistsExcluding(String email, String excludeEmail) {
        return PersonRepository.getInstance().getAll().stream()
                .anyMatch(p -> p.getEmail().equalsIgnoreCase(email) && !p.getEmail().equalsIgnoreCase(excludeEmail));
    }

    public void validateEmail(String email) {
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Email inválido");
        }
    }

    public void validatePassword(String password) {
        if (password.length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
        }
    }

    public void validatePhone(String phone) {
        if (!phone.matches("\\d{10}")) {
            throw new IllegalArgumentException("El número telefónico debe tener exactamente 10 dígitos numéricos.");
        }
    }

    public String generateId() {
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


    public boolean addAddress(String userId, Address address) {
        System.out.println("Buscando usuario: " + userId);
        Optional<Person> personOpt = personRepository.getPerson(userId);

        if (personOpt.isPresent() && personOpt.get() instanceof User) {
            User user = (User) personOpt.get();
            System.out.println("Usuario encontrado: " + user.getName());
            System.out.println("Agregando dirección: " + address.getAlias());

            user.getAddressesList().add(address);
            boolean result = personRepository.updatePerson(user);
            System.out.println("Resultado de updatePerson: " + result);
            return result;
        } else {
            System.out.println("Usuario no encontrado o no es tipo User");
            return false;
        }
    }


    public boolean updateAddress(String userId, Address updatedAddress) {
        System.out.println("Actualizando dirección para usuario: " + userId);
        Optional<Person> personOpt = personRepository.getPerson(userId);

        if (personOpt.isPresent() && personOpt.get() instanceof User) {
            User user = (User) personOpt.get();
            List<Address> addresses = user.getAddressesList();

            for (int i = 0; i < addresses.size(); i++) {
                if (addresses.get(i).getId().equals(updatedAddress.getId())) {
                    System.out.println("Dirección encontrada, actualizando: " + updatedAddress.getAlias());
                    addresses.set(i, updatedAddress);
                    boolean result = personRepository.updatePerson(user);
                    System.out.println("Resultado de updatePerson: " + result);
                    return result;
                }
            }
            System.out.println("Dirección no encontrada con ID: " + updatedAddress.getId());
        }
        return false;
    }

    public boolean deleteAddress(String userId, String addressId) {
        Optional<Person> personOpt = personRepository.getPerson(userId);

        if (personOpt.isPresent() && personOpt.get() instanceof User) {
            User user = (User) personOpt.get();
            boolean removed = user.getAddressesList().removeIf(a -> a.getId().equals(addressId));

            if (removed) {
                return personRepository.updatePerson(user);
            }
        }
        return false;
    }

    public List<Address> getUserAddresses(String userId) {
        System.out.println("Obteniendo direcciones para usuario: " + userId);
        Optional<Person> personOpt = personRepository.getPerson(userId);

        if (personOpt.isPresent() && personOpt.get() instanceof User) {
            User user = (User) personOpt.get();
            List<Address> addresses = new ArrayList<>(user.getAddressesList());
            System.out.println("Direcciones encontradas: " + addresses.size());
            return addresses;
        }
        System.out.println("Usuario no encontrado o no es tipo User");
        return new ArrayList<>();
    }

    private String generateAddressId() {
        return "ADDR_" + UUID.randomUUID().toString().substring(0,8);
    }

}






