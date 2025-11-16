package co.uniquindio.edu.sendifly.services;
import co.uniquindio.edu.sendifly.dtos.ShipmentReportDTO;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CSVReportAdapter implements ReportExporter<ShipmentReportDTO> {

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    public boolean export(List<ShipmentReportDTO> data, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("ID Envío,Origen,Destino,Peso (kg),Tarifa ($),Estado,Fecha Creación,Fecha Entrega\n");

            for (ShipmentReportDTO s : data) {
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

    private String escapeCSV(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}
