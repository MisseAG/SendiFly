package co.uniquindio.edu.sendifly.dtos;

import java.time.LocalDateTime;

public class ShipmentReportDTO {
    private String idEnvio;
    private String origen;
    private String destino;
    private double peso;
    private double tarifa;
    private String estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEntrega;

    public ShipmentReportDTO(String idEnvio, String origen, String destino,
                             double peso, double tarifa, String estado,
                             LocalDateTime fechaCreacion, LocalDateTime fechaEntrega) {
        this.idEnvio = idEnvio;
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
        this.tarifa = tarifa;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaEntrega = fechaEntrega;
    }

    // Getters y Setters
    public String getIdEnvio() { return idEnvio; }
    public void setIdEnvio(String idEnvio) { this.idEnvio = idEnvio; }

    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    public double getTarifa() { return tarifa; }
    public void setTarifa(double tarifa) { this.tarifa = tarifa; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
}