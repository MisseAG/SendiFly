package co.uniquindio.edu.sendifly.models.AdditionalServices;

public class Priority implements AdditionalService {


    @Override
    public float rateIncrease() {
        return 0.1F;
    }

    @Override
    public String getName() {
        return "Priority";
    }
}
