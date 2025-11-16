package co.uniquindio.edu.sendifly.models.Priorities;

public class Express implements ShippingPriority {


    @Override
    public float rateIncrease() {
        return 0.15F;
    }

    @Override
    public String getName() {
        return "Express";
    }
}
