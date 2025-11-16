package co.uniquindio.edu.sendifly.models.AvailabilityStatus;

public class OnTheWay implements AvailabilityStatus {
    @Override
    public void showStatus() {
        System.out.println("El repartidor está en Ruta");
    }

    @Override
    public String getName() {
        return "OnTheWay";
    }
}
