package com.example.ProyectoFinalBack.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Recibo")
public class Recibo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRecibo;

    @Column(nullable = false, length = 3)
    private String serie; // "001"

    @Column(nullable = false)
    private Integer numero; // 1, 2, 3... (El correlativo)

    @Column(name = "FechaPago")
    private LocalDateTime fechaPago;

    @Column(name = "MontoTotal", nullable = false)
    private BigDecimal montoTotal;

    @PrePersist
    public void prePersist() {
        this.fechaPago = LocalDateTime.now();
        if (this.serie == null) {
            this.serie = "001"; // Serie por defecto
        }
    }

    // Constructor vacío
    public Recibo() {}

    // Getters y Setters
    public Integer getIdRecibo() { return idRecibo; }
    public void setIdRecibo(Integer idRecibo) { this.idRecibo = idRecibo; }

    public String getSerie() { return serie; }
    public void setSerie(String serie) { this.serie = serie; }

    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }

    public LocalDateTime getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDateTime fechaPago) { this.fechaPago = fechaPago; }

    public BigDecimal getMontoTotal() { return montoTotal; }
    public void setMontoTotal(BigDecimal montoTotal) { this.montoTotal = montoTotal; }

    // Metodo auxiliar para que el Frontend reciba "001-0000005"
    // No se guarda en BD, es solo visual.
    public String getCodigoCompleto() {
        return this.serie + "-" + String.format("%07d", this.numero);
    }
}
