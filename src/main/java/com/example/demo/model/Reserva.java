package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reservas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Usuario registrado (opcional)
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = true)
    private Usuario usuario;

    // Datos del invitado
    private String nombreInvitado;
    private String emailInvitado;
    private String telefonoInvitado;

    @ManyToOne
    @JoinColumn(name = "campo_id")
    private Campo campo;

    private LocalDate fecha;

    private LocalTime horaInicio;

    private LocalTime horaFin;

    private String estado; // PENDIENTE, PAGADO, CANCELADO



}
