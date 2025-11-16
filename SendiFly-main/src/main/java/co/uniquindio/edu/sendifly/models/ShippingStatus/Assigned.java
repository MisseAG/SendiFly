package co.uniquindio.edu.sendifly.models.ShippingStatus;

public class Assigned implements ShippingStatus {
    @Override
    public void showStatus(){
        System.out.println("El envio ya ha sido asignado, mantente informado de las actualizaciones" + "\n");
    }

    @Override
    public String getName(){
        return "Assigned";
    }
}
