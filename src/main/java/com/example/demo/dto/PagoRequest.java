package com.example.demo.dto;

import lombok.Data;

@Data
public class PagoRequest {

    private String metodoPago; // TARJETA, YAPE, PLIN
    private String monto;

    // Tarjeta
    private String numeroTarjeta;
    private String titular;
    private String vencimiento;
    private String cvv;

    // Yape / Plin
    private String codigoQR;
}
