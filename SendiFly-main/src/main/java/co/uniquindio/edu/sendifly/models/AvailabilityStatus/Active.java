package co.uniquindio.edu.sendifly.models.AvailabilityStatus;

public class Active implements AvailabilityStatus {


    @Override
    public void showStatus() {
        System.out.println("El repartidor se encuentra disponible");
    }

    @Override
    public String getName() {
        return "Active";
    }
}
