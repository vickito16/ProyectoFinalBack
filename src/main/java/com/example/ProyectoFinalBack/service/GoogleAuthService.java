package com.example.ProyectoFinalBack.service;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.springframework.stereotype.Service;

@Service
public class GoogleAuthService {

    // ⚠️ IMPORTANTE: En producción, esto debe venir de la BD del usuario 'Director'.
    // Para probar, usa este secreto o genera uno nuevo.
    // Si escaneas un QR con este secreto en tu app, generará códigos válidos.
    private static final String SECRET_KEY_DIRECTOR = "JBSWY3DPEHPK3PXP";

    private final GoogleAuthenticator gAuth;

    public GoogleAuthService() {
        this.gAuth = new GoogleAuthenticator();
    }

    public boolean esCodigoValido(int codigoIngresado) {
        // Valida el código contra la llave secreta del director
        return gAuth.authorize(SECRET_KEY_DIRECTOR, codigoIngresado);
    }
}
