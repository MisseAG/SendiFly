package co.uniquindio.edu.sendifly.models.ShippingStatus;

public class Incident implements ShippingStatus{
    @Override
    public void showStatus(){
        System.out.println("El envio sufrio una descompostura" + ", mantente informado de las actualizaciones." + "\n");
    }

    @Override
    public String getName(){
        return "Incident";
    }
}
