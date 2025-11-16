package co.uniquindio.edu.sendifly.models.ShippingStatus;

public class OnTheWay implements ShippingStatus{
    @Override
    public void showStatus(){
        System.out.println("El envio se encuentra en camino" + ", mantente informado de las actualizaciones." + "\n ");
    }

    @Override
    public String getName(){
        return "OnTheWay";
    }
}
