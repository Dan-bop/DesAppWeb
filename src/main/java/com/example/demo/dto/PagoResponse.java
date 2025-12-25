package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PagoResponse {
    private Long id;
    private String estado;
    private BigDecimal monto;
    private LocalDateTime fechaPago;
    private Long reservaId;
}
