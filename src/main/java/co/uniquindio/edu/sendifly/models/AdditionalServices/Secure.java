package co.uniquindio.edu.sendifly.models.AdditionalServices;

public class Secure implements AdditionalService {


    @Override
    public float rateIncrease() {
        return 0.03F;
    }

    @Override
    public String getName() {
        return "Seguro";
    }
}
