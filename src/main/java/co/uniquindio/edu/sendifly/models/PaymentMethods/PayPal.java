package co.uniquindio.edu.sendifly.models.PaymentMethods;

public class PayPal implements PaymentMethod {
    public void pay(double amount) {
        System.out.println("Pagando "+amount+" con PayPal.");
    }

    @Override
    public String getName() {
        return "PayPal";
    }
}
