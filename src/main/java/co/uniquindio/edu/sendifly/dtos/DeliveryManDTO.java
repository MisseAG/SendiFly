package co.uniquindio.edu.sendifly.dtos;

import co.uniquindio.edu.sendifly.models.AvailabilityStatus.AvailabilityStatus;
import co.uniquindio.edu.sendifly.models.DeliveryMan;

public class DeliveryManDTO {
    private String idDeliveryMan;
    private String name;
    private String phone;
    private String email;
    private AvailabilityStatus availabilityStatus;

    public DeliveryManDTO(String idDeliveryMan, String name, String phone, String email, AvailabilityStatus availabilityStatus) {
        this.idDeliveryMan = idDeliveryMan;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.availabilityStatus = availabilityStatus;
    }

    public DeliveryManDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdDeliveryMan() {
        return idDeliveryMan;
    }

    public void setIdDeliveryMan(String idDeliveryMan) {
        this.idDeliveryMan = idDeliveryMan;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public static DeliveryManDTO fromDeliveryMan(DeliveryMan deliveryman) {
        DeliveryManDTO dto = new DeliveryManDTO();
        dto.setIdDeliveryMan(deliveryman.getId());
        dto.setName(deliveryman.getName());
        dto.setEmail(deliveryman.getEmail());
        dto.setPhone(deliveryman.getPhone());
        return dto;
    }
}
