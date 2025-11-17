package co.uniquindio.edu.sendifly.repositories;

import co.uniquindio.edu.sendifly.models.Pack;
import co.uniquindio.edu.sendifly.models.Shipment;

import java.util.ArrayList;
import java.util.Optional;

public class PackRepository {

    private static PackRepository instance = new PackRepository();

    private final ArrayList<Pack> packages;

    private PackRepository() {
        this.packages = new ArrayList<>();
        //loadDefaultPackages();
    }

    public static PackRepository getInstance() {
        if (instance == null) {
            instance = new PackRepository();
        }
        return instance;
    }

//    private void loadDefaultPackages() {
//        packages.add(new Pack.PackageBuilder()
//                .id("Paquete: 001")
//                .product("Smartphone")
//                .price(50000)
//                .weight(0.3f)
//                .volume(0.2f)
//                .build());
//
//        packages.add(new Pack.PackageBuilder()
//                .id("Paquete: 002")
//                .product("Laptop")
//                .price(120000)
//                .weight(2.0f)
//                .volume(1.5f)
//                .build());
//
//        packages.add(new Pack.PackageBuilder()
//                .id("Paquete: 003")
//                .product("Libros")
//                .price(30000)
//                .weight(0.7f)
//                .volume(0.8f)
//                .build());
//
//        packages.add(new Pack.PackageBuilder()
//                .id("Paquete: 004")
//                .product("Zapatos")
//                .price(70000)
//                .weight(1.0f)
//                .volume(0.6f)
//                .build());
//
//        packages.add(new Pack.PackageBuilder()
//                .id("Paquete: 005")
//                .product("headphones")
//                .price(90000)
//                .weight(0.0032f)
//                .volume(0.02f)
//                .build());
//
//    }

    public void addPackage(Pack pack) {
        for (Pack p : packages) {
            if (p.getId().equals(pack.getId())) {
                throw new IllegalArgumentException("[PackageRepository] El paquete ya existe");
            }
        }
        packages.add(pack);
    }

    public Optional<Pack> getPackage(String id) {
        return packages.stream().filter(p -> p.getId().equals(id)).findFirst();}

    public ArrayList<Pack> getAllPackages() {
        return new ArrayList<>(packages);}

    public void removePackage(Pack pack) {
        packages.removeIf(p -> p.getId().equals(pack.getId()));}

    public int countPackages() {
        return packages.size();
    }

}
