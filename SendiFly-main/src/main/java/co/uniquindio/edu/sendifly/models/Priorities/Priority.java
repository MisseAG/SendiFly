package co.uniquindio.edu.sendifly.models.Priorities;

public class Priority implements ShippingPriority {


    @Override
    public float rateIncrease() {
        return 0.10F;
    }

    @Override
    public String getName() {
        return "Priority";
    }
}
