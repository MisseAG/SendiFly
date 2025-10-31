package co.uniquindio.edu.sendifly.models.PaymentMethods;

public interface PaymentMethod {
    void pay(double amount);

    String getName();
}

