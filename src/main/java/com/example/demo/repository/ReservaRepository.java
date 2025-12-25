package com.example.demo.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByUsuarioId(Long usuarioId);

    List<Reserva> findByCampoIdAndFecha(Long campoId, LocalDate fecha);

    // ✅ Validación profesional de choque de horarios (NOMBRE CORREGIDO)
    boolean existsByCampoIdAndFechaAndHoraInicioLessThanAndHoraFinGreaterThan(
            Long campoId, // CORREGIDO: Usar 'CampoId'
            LocalDate fecha,
            LocalTime horaFinNueva,
            LocalTime horaInicioNueva
    );

    List<Reserva> findByEstado(String estado);
}