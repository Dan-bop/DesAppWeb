package com.example.demo.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

@Entity
@Table(name = "campos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Campo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(precision = 10, scale = 2)
    private BigDecimal precioHora;

    private String descripcion;

    private String imagen;   // <-- Cambiado para coincidir con Angular

    private boolean disponible = true;   // <-- Por defecto disponible

    @ManyToOne
    @JoinColumn(name = "sede_id")
    @JsonIgnoreProperties("campos") // Evita que dentro del campo se vuelva a cargar la lista de sedes
    private Sede sede;
}
