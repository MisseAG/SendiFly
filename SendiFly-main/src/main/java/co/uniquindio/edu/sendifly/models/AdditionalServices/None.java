package co.uniquindio.edu.sendifly.models.AdditionalServices;

public class None implements AdditionalService {


    @Override
    public float rateIncrease() {
        return 0;
    }

    @Override
    public String getName() {
        return "None";
    }
}
