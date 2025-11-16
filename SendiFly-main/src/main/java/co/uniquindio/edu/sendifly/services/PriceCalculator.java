package co.uniquindio.edu.sendifly.services;

import co.uniquindio.edu.sendifly.models.AdditionalServices.AdditionalService;
import co.uniquindio.edu.sendifly.models.Address;
import co.uniquindio.edu.sendifly.models.Pack;

public class PriceCalculator {
    private static final double WEIGHT_RATE = 2.5; // $ por kg
    private static final double VOLUME_RATE = 1.8; // $ por m³

    public double calculatePrice(Pack pack, AdditionalService additionalService,
                                 Address origin, Address destination) {

        // Cálculo base por peso y volumen
        double basePrice = (pack.getWeight() * WEIGHT_RATE) +
                (pack.getVolume() * VOLUME_RATE);

        //Considerar peso volumétrico si es mayor
        double volumetricPrice = pack.getVolumetricWeight() * WEIGHT_RATE;
        basePrice = Math.max(basePrice, volumetricPrice);

        //Factor de distancia
        double distanceFactor = calculateDistanceFactor(origin, destination);
        basePrice *= distanceFactor;

        //Aplicar servicio adicional
        if (additionalService != null) {
            basePrice *= (1 + additionalService.rateIncrease());
        }

        // 5. Precio mínimo
        return Math.max(basePrice, 10.0);
    }

    private double calculateDistanceFactor(Address origin, Address destination) {
        double distance = origin.calculateDistanceTo(destination);
        return distance / 25.0; // Factor de conversión a horas
    }
}
