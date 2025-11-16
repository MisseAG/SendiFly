package co.uniquindio.edu.sendifly.models.Priorities;

public class Standard implements ShippingPriority {


    @Override
    public float rateIncrease() {
        return 0;
    }

    @Override
    public String getName() {
        return "Standard";
    }
}
