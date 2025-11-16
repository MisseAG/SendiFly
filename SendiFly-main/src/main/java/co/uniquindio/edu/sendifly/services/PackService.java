package co.uniquindio.edu.sendifly.services;

import co.uniquindio.edu.sendifly.models.Pack;
import co.uniquindio.edu.sendifly.repositories.PackRepository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class PackService {

    private final PackRepository packageRepository = PackRepository.getInstance();

    public void registerPackage(String product, float price, float height, float volume) {
        if (product == null || product.isBlank()) {
            throw new IllegalArgumentException("[PackageService] Product name is required");
        }

        String id = generatePackageId();

        Pack pack = new Pack.PackageBuilder()
                .id(id)
                .product(product)
                .price(price)
                .weight(height)
                .volume(volume)
                .build();

        packageRepository.addPackage(pack);
    }

    public Pack getPackageById(String id) {
        return packageRepository.getPackage(id)
                .orElseThrow(() -> new IllegalArgumentException("[PackageService] Package not found"));
    }

    public ArrayList<Pack> listAllPackages() {
        return packageRepository.getAllPackages();
    }

    public void deletePackage(String id) {
        Pack pack = getPackageById(id);
        packageRepository.removePackage(pack);
    }

    private String generatePackageId() {
        return "Paquete: " + UUID.randomUUID();
    }
}
