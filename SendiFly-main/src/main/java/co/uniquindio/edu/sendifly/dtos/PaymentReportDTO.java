package co.uniquindio.edu.sendifly.dtos;

import java.time.LocalDateTime;

public class PaymentReportDTO {
    private String idPago;
    private String idEnvio;
    private double monto;
    private String metodoPago;
    private String estado;
    private LocalDateTime fecha;

    public PaymentReportDTO(String idPago, String idEnvio, double monto,
                            String metodoPago, String estado, LocalDateTime fecha) {
        this.idPago = idPago;
        this.idEnvio = idEnvio;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.estado = estado;
        this.fecha = fecha;
    }

    // Getters y Setters
    public String getIdPago() { return idPago; }
    public void setIdPago(String idPago) { this.idPago = idPago; }

    public String getIdEnvio() { return idEnvio; }
    public void setIdEnvio(String idEnvio) { this.idEnvio = idEnvio; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}