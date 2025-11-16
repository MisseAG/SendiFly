package co.uniquindio.edu.sendifly.models.PaymentMethods;

public class CreditCard implements PaymentMethod {
    public void pay(double amount) {
        System.out.println("Pagando "+amount+" con tarjeta de crédito.");
    }

    @Override
    public String getName() {
        return "CreditCard";
    }
}
