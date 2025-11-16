package co.uniquindio.edu.sendifly.services;

import co.uniquindio.edu.sendifly.dtos.ShipmentReportDTO;
import co.uniquindio.edu.sendifly.models.Shipment;
import co.uniquindio.edu.sendifly.repositories.ShipmentRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReportService {
    private static ReportService instance;
    private ShipmentRepository shipmentRepository;

    private ReportService() {
        this.shipmentRepository = ShipmentRepository.getInstance();
    }

    public static ReportService getInstance() {
        if (instance == null) {
            instance = new ReportService();
        }
        return instance;
    }

    public List<ShipmentReportDTO> getShipmentsReport(String userId,
                                                      LocalDateTime startDate,
                                                      LocalDateTime endDate) {
        List<Shipment> allShipments = shipmentRepository.getAllShipments();
        List<ShipmentReportDTO> reportList = new ArrayList<>();

        for (Shipment s : allShipments) {
            ShipmentReportDTO dto = convertToShipmentDTO(s);
            reportList.add(dto);
        }

        return reportList;
    }

    private ShipmentReportDTO convertToShipmentDTO(Shipment shipment) {
        return new ShipmentReportDTO(
                shipment.getId(),
                getOriginString(shipment),
                getDestinationString(shipment),
                getWeight(shipment),
                getFee(shipment),
                getStatus(shipment),
                LocalDateTime.now(),
                null
        );
    }

    private String getOriginString(Shipment s) {
        try {
            return s.getOrigin() != null ? s.getOrigin().toString() : "N/A";
        } catch (Exception e) {
            return "N/A";
        }
    }

    private String getDestinationString(Shipment s) {
        try {
            return s.getDestination() != null ? s.getDestination().toString() : "N/A";
        } catch (Exception e) {
            return "N/A";
        }
    }

    private double getWeight(Shipment s) {
        try {
            return 5.0;
        } catch (Exception e) {
            return 0.0;
        }
    }

    private double getFee(Shipment s) {
        try {
            return 25000.0;
        } catch (Exception e) {
            return 0.0;
        }
    }

    private String getStatus(Shipment s) {
        try {
            return "Pendiente";
        } catch (Exception e) {
            return "Desconocido";
        }
    }
}