package co.uniquindio.edu.sendifly.models.PaymenMethods;

public class DebitCard implements PaymentMethod {
    public void pay(double amount) {
        System.out.println("Pagando "+amount+" con tarjeta de débito.");
    }

    @Override
    public String getName() {
        return "DebitCard";
    }

}
