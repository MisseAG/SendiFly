package co.uniquindio.edu.sendifly.services;

import co.uniquindio.edu.sendifly.dtos.ShipmentReportDTO;
import co.uniquindio.edu.sendifly.dtos.PaymentReportDTO;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExportService {
    private static ExportService instance;
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private ExportService() {}

    public static ExportService getInstance() {
        if (instance == null) {
            instance = new ExportService();
        }
        return instance;
    }

    public boolean exportShipmentsToCSV(List<ShipmentReportDTO> shipments, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("ID Envío,Origen,Destino,Peso (kg),Tarifa ($),Estado,Fecha Creación,Fecha Entrega\n");

            for (ShipmentReportDTO s : shipments) {
                writer.append(escapeCSV(s.getIdEnvio())).append(",");
                writer.append(escapeCSV(s.getOrigen())).append(",");
                writer.append(escapeCSV(s.getDestino())).append(",");
                writer.append(String.valueOf(s.getPeso())).append(",");
                writer.append(String.valueOf(s.getTarifa())).append(",");
                writer.append(escapeCSV(s.getEstado())).append(",");
                writer.append(s.getFechaCreacion() != null ?
                        s.getFechaCreacion().format(formatter) : "N/A").append(",");
                writer.append(s.getFechaEntrega() != null ?
                        s.getFechaEntrega().format(formatter) : "N/A").append("\n");
            }

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean exportPaymentsToCSV(List<PaymentReportDTO> payments, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("ID Pago,ID Envío,Monto ($),Método de Pago,Estado,Fecha\n");

            for (PaymentReportDTO p : payments) {
                writer.append(escapeCSV(p.getIdPago())).append(",");
                writer.append(escapeCSV(p.getIdEnvio())).append(",");
                writer.append(String.valueOf(p.getMonto())).append(",");
                writer.append(escapeCSV(p.getMetodoPago())).append(",");
                writer.append(escapeCSV(p.getEstado())).append(",");
                writer.append(p.getFecha() != null ?
                        p.getFecha().format(formatter) : "N/A").append("\n");
            }

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String escapeCSV(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}