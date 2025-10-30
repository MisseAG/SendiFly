package co.uniquindio.edu.sendifly.models.PaymenMethods;

public class Cash implements PaymentMethod {
    public void pay(double amount) {
        System.out.println("Pagando "+amount+" en efectivo.");
    }

    @Override
    public String getName() {
        return "Cash";
    }

}
