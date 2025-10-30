package co.uniquindio.edu.sendifly.models.PaymenMethods;

public class Nequi implements PaymentMethod{
    public void pay(double amount) {
        System.out.println("Pagando "+amount+" con Nequi.");
    }

    @Override
    public String getName() {
        return "Nequi";
    }
}
