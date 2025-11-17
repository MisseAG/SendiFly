package co.uniquindio.edu.sendifly.dtos;

public class ShipmentTrackDTO {
    private String id;
    private String status;

    public ShipmentTrackDTO (String id, String status){
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
