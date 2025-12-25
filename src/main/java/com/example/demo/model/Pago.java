package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;

    private BigDecimal monto;

    private String metodoPago; // TARJETA, YAPE, PLIN

    private LocalDateTime fechaPago;

    // Datos de tarjeta (si aplica)
    private String numeroTarjeta;
    private String titular;
    private String vencimiento;
    private String cvv;

    // QR o número de billetera
    private String codigoQR;


}
