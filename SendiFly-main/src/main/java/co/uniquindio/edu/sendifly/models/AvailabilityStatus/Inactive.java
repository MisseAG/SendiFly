package co.uniquindio.edu.sendifly.models.AvailabilityStatus;

public class Inactive implements AvailabilityStatus {
    @Override
    public void showStatus() {
        System.out.println("El repartidor se encuentra inactivo");
    }

    @Override
    public String getName() {
        return "Inactive";
    }
}
