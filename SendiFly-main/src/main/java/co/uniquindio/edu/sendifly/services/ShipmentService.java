package co.uniquindio.edu.sendifly.services;

import co.uniquindio.edu.sendifly.models.AdditionalServices.AdditionalService;
import co.uniquindio.edu.sendifly.models.Address;
import co.uniquindio.edu.sendifly.models.Pack;
import co.uniquindio.edu.sendifly.models.Shipment;
import co.uniquindio.edu.sendifly.models.ShippingStatus.ShippingStatus;
import co.uniquindio.edu.sendifly.models.User;
import co.uniquindio.edu.sendifly.repositories.ShipmentRepository;
import co.uniquindio.edu.sendifly.session.SessionManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar la lógica de negocio de envíos.
 * Implementa el patrón Singleton para garantizar una única instancia.
 */
public class ShipmentService {

    // Singleton
    private static final ShipmentService instance = new ShipmentService();

    /**
     * Obtiene la única instancia del servicio
     */
    public static ShipmentService getInstance() {
        return instance;
    }

    // Constructor privado para evitar instanciación externa
    private ShipmentService() {
        this.shipmentRepository = ShipmentRepository.getInstance();
        this.sessionManager = SessionManager.getInstance();
        this.priceCalculator = new PriceCalculator();

    }

    private final ShipmentRepository shipmentRepository;
    private final SessionManager sessionManager;
    private final PriceCalculator priceCalculator;

    private static final double PRIORITY_DISCOUNT = 0.7; // 30% de reducción en tiempo
    private static final double SPEED_FACTOR = 25.0; // km/h velocidad promedio
    private static final long MIN_DELIVERY_HOURS = 1; // Mínimo 1 hora de entrega
    private static final int MAX_WEIGHT_KG = 1000; // Peso máximo permitido
    private static final int MAX_VOLUME_M3 = 100; // Volumen máximo permitido


    /**
     * Crea un nuevo envío completo con validaciones y cálculos automáticos
     *
     * @return El envío creado con su ID generado
     */
    public Shipment createShipment(String originAlias, String destinationAlias,
                                   AdditionalService additionalService,
                                   ShippingStatus shippingStatus,
                                   float weight, float volume) {

        //obtener user
        User currentUser = sessionManager.getCurrentUser();

        //buscar direcciones
        Address origin = findAddressByAlias(currentUser, originAlias);
        Address destination = findAddressByAlias(currentUser, destinationAlias);

        // Validaciones de negocio
        validateShipmentData(origin, destination, weight, volume);

        // Crear Package con Builder
        Pack pack = new Pack.PackageBuilder()
                .id(generatePackId())
                .weight(weight)
                .volume(volume)
                .build();

        // Calcular precio usando el calculador
        double price = priceCalculator.calculatePrice(pack, additionalService, origin, destination);

        // Calcular fecha de entrega estimada
        LocalDateTime deliveryDate = calculateEstimatedDelivery(origin, destination, additionalService);

        // Construir el Shipment
        String id = generateShipmentId();
        Shipment shipment = new Shipment.ShipmentBuilder()
                .id(id)
                .origin(origin)
                .destination(destination)
                .orderDate(LocalDate.now())
                .deliveryDate(deliveryDate)
                .price(price)
                .additionalService(additionalService)
                .shippingStatus(shippingStatus)
                .pack(pack)
                .build();

        shipmentRepository.addShipment(shipment);

        currentUser.getShipmentsList().add(shipment);

        return shipment;
    }

    /**
     * Cotiza un envío sin crearlo
     */
    public double quoteShipment(float weight, float volume,
                                AdditionalService additionalService,
                                String originAlias, String destinationAlias) {

        User currentUser = sessionManager.getCurrentUser();

        Address origin = findAddressByAlias(currentUser, originAlias);
        Address destination = findAddressByAlias(currentUser, destinationAlias);

        validateShipmentData(origin, destination, weight, volume);

        Pack tempPack = new Pack.PackageBuilder()
                .weight(weight)
                .volume(volume)
                .build();

        return priceCalculator.calculatePrice(tempPack, additionalService, origin, destination);
    }

    /**
     * Obtiene información detallada de cotización (precio + tiempo estimado)
     */
    public QuotationDetails getQuotationDetails(float weight, float volume,
                                                AdditionalService additionalService,
                                                String originAlias, String destinationAlias) {

        User currentUser = sessionManager.getCurrentUser();

        Address origin = findAddressByAlias(currentUser, originAlias);
        Address destination = findAddressByAlias(currentUser, destinationAlias);

        validateShipmentData(origin, destination, weight, volume);

        Pack tempPack = new Pack.PackageBuilder()
                .weight(weight)
                .volume(volume)
                .build();

        double price = priceCalculator.calculatePrice(tempPack, additionalService, origin, destination);
        LocalDateTime deliveryDate = calculateEstimatedDelivery(origin, destination, additionalService);
        double distance = origin.calculateDistanceTo(destination);

        return new QuotationDetails(price, deliveryDate, distance);
    }

    // CRUD
    /**
     * Obtiene un envío por su ID
     */
    public Shipment getShipment(String id) {
        validateId(id);
        return shipmentRepository.getShipment(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "[ShipmentService] El envío con ID '" + id + "' no existe"));
    }

    /**
     * Obtiene todos los envíos
     */
    public ArrayList<Shipment> getAllShipments() {
        return shipmentRepository.getAllShipments();
    }

    /**
     * Actualiza un envío existente
     */
    public void updateShipment(String id, Shipment updatedShipment) {
        validateId(id);

        if (!shipmentRepository.getShipment(id).isPresent()) {
            throw new IllegalArgumentException(
                    "[ShipmentService] No se puede actualizar: el envío con ID '" + id + "' no existe");
        }

        // Validar que el nuevo envío tenga datos válidos
        validateShipmentObject(updatedShipment);

        shipmentRepository.removeShipment(shipmentRepository.getShipment(id).get());
        shipmentRepository.addShipment(updatedShipment);
    }

    /**
     * Actualiza el estado de un envío
     */
    public void updateShipmentStatus(String shipmentId, ShippingStatus newStatus) {
        validateId(shipmentId);

        Shipment shipment = getShipment(shipmentId);

        // Validar transición de estado
        validateStatusTransition(shipment.getShippingStatus(), newStatus);

        // Crear nuevo envío con estado actualizado
        Shipment updatedShipment = new Shipment.ShipmentBuilder()
                .id(shipment.getId())
                .origin(shipment.getOrigin())
                .destination(shipment.getDestination())
                .orderDate(shipment.getOrderDate())
                .deliveryDate(shipment.getDeliveryDate())
                .price(shipment.getPrice())
                .additionalService(shipment.getAdditionalService())
                .shippingStatus(newStatus)
                .pack(shipment.getPack())
                .build();

        updateShipment(shipmentId, updatedShipment);
    }

    /**
     * Elimina un envío
     */
    public void deleteShipment(String id) {
        Shipment shipment = getShipment(id);

        // Validar que se pueda eliminar (por ejemplo, no entregado)
        if (shipment.getShippingStatus().getName().equals("Delivered")) {
            throw new IllegalStateException(
                    "[ShipmentService] No se puede eliminar un envío ya entregado");
        }

        shipmentRepository.removeShipment(shipment);
    }

    //Consultas

    /**
     * Verifica si existe un envío
     */
    public boolean existsShipment(String id) {
        return id != null && !id.isBlank() && shipmentRepository.getShipment(id).isPresent();
    }

    /**
     * Cuenta el total de envíos
     */
    public int countShipments() {
        return shipmentRepository.countShipments();
    }

    /**
     * Obtiene envíos por estado
     */
    public List<Shipment> getShipmentsByStatus(ShippingStatus status) {
        return shipmentRepository.getAllShipments().stream()
                .filter(s -> s.getShippingStatus().equals(status))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene envíos activos (en tránsito o pendientes)
     */
    public List<Shipment> getActiveShipments() {
        return shipmentRepository.getAllShipments().stream()
                .filter(s -> {
                    String statusName = s.getShippingStatus().getName();
                    return "OnTheWay".equals(statusName) || "Inactive".equals(statusName);
                })
                .collect(Collectors.toList());
    }

    /**
     * Obtiene envíos retrasados
     */
    public List<Shipment> getDelayedShipments() {
        LocalDateTime now = LocalDateTime.now();
        return shipmentRepository.getAllShipments().stream()
                .filter(s -> s.getDeliveryDate().isBefore(now) &&
                        !"Delivered".equals(s.getShippingStatus().getName()))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene envíos por rango de fechas
     */
    public List<Shipment> getShipmentsByDateRange(LocalDate startDate, LocalDate endDate) {
        validateDateRange(startDate, endDate);

        return shipmentRepository.getAllShipments().stream()
                .filter(s -> !s.getOrderDate().isBefore(startDate) &&
                        !s.getOrderDate().isAfter(endDate))
                .collect(Collectors.toList());
    }

    //BUscar direcciones

    private Address findAddressByAlias(User user, String alias) {
        if (alias== null || alias.trim().isEmpty()){
            throw new IllegalArgumentException("[ShipmentService] El alias de la dirección no puede estar vacío");
        }
        return user.getAddressesList().stream()
                .filter(address -> alias.equalsIgnoreCase(address.getAlias()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ShipmentService] No se encontró la dirección con el alias" + alias + "en tus direcciones"));
    }

    /**
     * Obtiene todos los alias de direcciones del usuario actual (para ComboBox)
     */
    public List<String> getCurrentUserAddressAliases() {
        User currentUser = sessionManager.getCurrentUser();
        return currentUser.getAddressesList().stream()
                .map(Address::getAlias)
                .sorted()
                .collect(Collectors.toList());
    }

    // Validaciones internas

    private void validateShipmentData(Address origin, Address destination,
                                      float weight, float volume) {
        validateLocation(origin, "origen");
        validateLocation(destination, "destino");
        validateWeight(weight);
        validateVolume(volume);
    }

    private void validateLocation(Address location, String fieldName) {
        if (location == null) {
            throw new IllegalArgumentException(
                    "[ShipmentService] La dirección de " + fieldName + " no puede ser nula");
        }

        if (location.getId() == null || location.getId().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "[ShipmentService] El ID de " + fieldName + " es requerido");
        }

        if (location.getStreet() == null || location.getStreet().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "[ShipmentService] La calle de " + fieldName + " es requerida");
        }

        if (location.getCity() == null || location.getCity().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "[ShipmentService] La ciudad de " + fieldName + " es requerida");
        }
    }

    private void validateWeight(float weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException(
                    "[ShipmentService] El peso debe ser mayor a 0 kg");
        }
        if (weight > MAX_WEIGHT_KG) {
            throw new IllegalArgumentException(
                    "[ShipmentService] El peso no puede exceder " + MAX_WEIGHT_KG + " kg");
        }
    }

    private void validateVolume(float volume) {
        if (volume <= 0) {
            throw new IllegalArgumentException(
                    "[ShipmentService] El volumen debe ser mayor a 0 m³");
        }
        if (volume > MAX_VOLUME_M3) {
            throw new IllegalArgumentException(
                    "[ShipmentService] El volumen no puede exceder " + MAX_VOLUME_M3 + " m³");
        }
    }

    private void validateId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("[ShipmentService] El ID no puede estar vacío");
        }
    }

    private void validateShipmentObject(Shipment shipment) {
        if (shipment == null) {
            throw new IllegalArgumentException("[ShipmentService] El envío no puede ser nulo");
        }
        validateShipmentData(
                shipment.getOrigin(),
                shipment.getDestination(),
                shipment.getPack().getWeight(),
                shipment.getPack().getVolume()
        );
    }

    private void validateStatusTransition(ShippingStatus current, ShippingStatus next) {
        if (current == null || next == null) {
            throw new IllegalArgumentException("[ShipmentService] Los estados no pueden ser nulos");
        }

        String currentName = current.getName();
        String nextName = next.getName();

        // No se puede cambiar el estado de un envío entregado
        if ("Delivered".equals(currentName)) {
            throw new IllegalStateException(
                    "[ShipmentService] No se puede modificar un envío ya entregado");
        }

    }

    private void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("[ShipmentService] Las fechas no pueden ser nulas");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(
                    "[ShipmentService] La fecha inicial debe ser anterior a la fecha final");
        }
    }

    // Cálculos

    /**
     * Calcula la fecha de entrega estimada basada en distancia y servicio adicional
     */
    private LocalDateTime calculateEstimatedDelivery(Address origin, Address destination,
                                                     AdditionalService service) {
        // Calcular distancia entre origen y destino
        double distance = origin.calculateDistanceTo(destination);

        // Convertir distancia a horas (usando velocidad promedio)
        double baseHours = distance / SPEED_FACTOR;

        // Menos tiempo si es prioritario
        if (service != null && "Priority".equals(service.getName())) {
            baseHours *= PRIORITY_DISCOUNT;
        }

        long finalHours = (long) Math.ceil(Math.max(MIN_DELIVERY_HOURS, baseHours));

        return LocalDateTime.now().plusHours(finalHours);
    }

    // ========== GENERADORES DE ID ==========

    private String generateShipmentId() {
        return "Shipment_" + generateId();
    }

    private String generatePackId() {
        return "Pack_" + generateId();
    }

    private String generateId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    //

    /**
     * Clase para devolver información completa de cotización
     */
    public static class QuotationDetails {
        private final double price;
        private final LocalDateTime estimatedDelivery;
        private final double distance;

        public QuotationDetails(double price, LocalDateTime estimatedDelivery, double distance) {
            this.price = price;
            this.estimatedDelivery = estimatedDelivery;
            this.distance = distance;
        }

        public double getPrice() {
            return price;
        }

        public LocalDateTime getEstimatedDelivery() {
            return estimatedDelivery;
        }

        public double getDistance() {
            return distance;
        }

        public String getFormattedPrice() {
            return String.format("$%.2f", price);
        }

        public String getFormattedDistance() {
            return String.format("%.2f km", distance);
        }

        @Override
        public String toString() {
            return String.format(
                    "Cotización: %s | Entrega: %s | Distancia: %s",
                    getFormattedPrice(),
                    estimatedDelivery.toString(),
                    getFormattedDistance()
            );
        }
    }
}
