package co.uniquindio.edu.sendifly.models.AdditionalServices;

public class Fragile implements AdditionalService {


    @Override
    public float rateIncrease() {
        return 0.05F;
    }

    @Override
    public String getName() {
        return "Frágil";
    }
}
