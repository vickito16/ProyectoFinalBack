package com.example.ProyectoFinalBack.service;


import com.example.ProyectoFinalBack.model.Cuota;
import com.example.ProyectoFinalBack.model.Recibo;
import com.example.ProyectoFinalBack.repository.CuotaRepository;
import com.example.ProyectoFinalBack.repository.ReciboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ReciboService {

    @Autowired
    private ReciboRepository repo;

    @Autowired
    private CuotaRepository cuotaRepo; // Necesario para modificar las cuotas

    @Autowired
    private GoogleAuthService authService; // Necesario para validar el código 2FA

    // ==========================================
    // 🔹 Generar Recibo (Tu método original)
    // ==========================================
    public Recibo generarRecibo(BigDecimal monto) {
        // 1. Consultar cuál es el último número usado
        Integer ultimoNumero = repo.obtenerUltimoNumero();

        // 2. Calcular el nuevo número (Si es null, empezamos en 1)
        int nuevoNumero = (ultimoNumero == null) ? 1 : ultimoNumero + 1;

        // 3. Crear el objeto Recibo
        Recibo nuevoRecibo = new Recibo();
        nuevoRecibo.setSerie("001");
        nuevoRecibo.setNumero(nuevoNumero);
        nuevoRecibo.setMontoTotal(monto);
        // La fecha se pone sola en el @PrePersist

        // 4. Guardar en BD
        return repo.save(nuevoRecibo);
    }

    // ==========================================
    // 🔹 NUEVO: Anular Recibo con 2FA
    // ==========================================
    @Transactional // Importante: Si algo falla, se deshacen todos los cambios
    public void anularRecibo(Integer idRecibo, int codigo2FA) {

        // 1. Validar Código del Director (Seguridad)
        boolean esValido = authService.esCodigoValido(codigo2FA);
        if (!esValido) {
            throw new RuntimeException("Código de Google Authenticator incorrecto o expirado.");
        }

        // 2. Verificar que el recibo existe
        if (!repo.existsById(idRecibo)) {
            throw new RuntimeException("El recibo con ID " + idRecibo + " no existe.");
        }
        // (Opcional) Podrías traer el recibo si necesitas cambiarle un estado a "ANULADO"
        // Recibo recibo = repo.findById(idRecibo).get();

        // 3. Buscar las cuotas asociadas a este recibo
        List<Cuota> cuotasAsociadas = cuotaRepo.findByIdRecibo(idRecibo);

        if (cuotasAsociadas.isEmpty()) {
            throw new RuntimeException("Este recibo no tiene cuotas asociadas o ya fue procesado.");
        }

        // 4. "Soltar" las cuotas (Volver a deuda)
        for (Cuota c : cuotasAsociadas) {
            c.setEstadoPago(0);  // Vuelve a estado 'Debe'
            c.setIdRecibo(null); // Se desvincula del recibo (null)
        }

        // 5. Guardar cambios masivos en la tabla Cuota
        cuotaRepo.saveAll(cuotasAsociadas);
    }
}
