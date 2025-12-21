package com.example.ProyectoFinalBack.dto;

import com.example.ProyectoFinalBack.model.Cuota;
import lombok.Data; // Usamos @Data para el DTO. Si no te funciona, avísame para hacerlo manual.
import java.math.BigDecimal;

@Data
public class infoCuotaDTO {
    private Integer idCuota;
    private BigDecimal precio;
    private Integer estadoPago;
    private String nombreConcepto;

    public infoCuotaDTO() {
    }

    // Constructor que convierte la Entidad al DTO
    public infoCuotaDTO(Cuota c) {
        this.idCuota = c.getIdCuota();
        this.precio = c.getPrecio();
        this.estadoPago = c.getEstadoPago();

        // Extraemos el nombre del concepto de la relación
        if (c.getConcepto() != null) {
            this.nombreConcepto = c.getConcepto().getNombreConcepto();
        }
    }
}
