package co.uniquindio.edu.sendifly.models.PaymenMethods;

public interface PaymentMethod {
    void pay(double amount);

    String getName();
}

