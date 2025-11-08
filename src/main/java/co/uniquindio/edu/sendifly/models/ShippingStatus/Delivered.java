package co.uniquindio.edu.sendifly.models.ShippingStatus;

public class Delivered implements ShippingStatus{
    @Override
    public void showStatus(){
        System.out.println("¡El envio fue entregado, ve reclamarlo!");
    }

    @Override
    public String getName() {
        return "Delivered";
    }


}
