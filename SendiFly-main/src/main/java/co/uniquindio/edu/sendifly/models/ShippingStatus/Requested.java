package co.uniquindio.edu.sendifly.models.ShippingStatus;

import co.uniquindio.edu.sendifly.models.PaymentMethods.PaymentMethod;

public class Requested implements ShippingStatus {
    @Override
    public void showStatus() {
        System.out.println("El envio ha sido solicitado, pero todavia no ha sido asignado" +
                ", mantente informado de las actualizaciones." + "\n ");
    }

    @Override
    public String getName() {
        return "Requested";
    }
}
