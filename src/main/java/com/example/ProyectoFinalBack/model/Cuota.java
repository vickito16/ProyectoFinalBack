package com.example.ProyectoFinalBack.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cuota")
public class Cuota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCuota;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(columnDefinition = "tinyint default 0")
    private Integer estadoPago;

    @ManyToOne
    @JoinColumn(name = "IdMatricula", nullable = false)
    private Matricula matricula;

    @ManyToOne
    @JoinColumn(name = "IdConcepto", nullable = false)
    private Concepto concepto;

    @Column(name = "IdRecibo", nullable = true)
    private Integer idRecibo;

    @PrePersist
    public void prePersist() {
        if (this.estadoPago == null) {
            this.estadoPago = 0;
        }
    }

    // --------------------------
    // Getters y Setters
    // --------------------------

    public Integer getIdCuota() {
        return idCuota;
    }

    public void setIdCuota(Integer idCuota) {
        this.idCuota = idCuota;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(Integer estadoPago) {
        this.estadoPago = estadoPago;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public Concepto getConcepto() {
        return concepto;
    }

    public void setConcepto(Concepto concepto) {
        this.concepto = concepto;
    }

    public Integer getIdRecibo() {
        return idRecibo;
    }

    public void setIdRecibo(Integer idRecibo) {
        this.idRecibo = idRecibo;
    }
}
