package co.uniquindio.edu.sendifly.models.AdditionalServices;

public class SignatureRequired implements AdditionalService {


    @Override
    public float rateIncrease() {
        return 0.01F;
    }

    @Override
    public String getName() {
        return "SignatureRequired";
    }
}
