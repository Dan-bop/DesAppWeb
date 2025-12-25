package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long> {

    // ✅ Verifica si existe un pago asociado a una reserva específica.
    //    Útil para evitar pagos duplicados.
    boolean existsByReservaId(Long reservaId);

    // ✅ Obtiene el pago asociado a una reserva.
    //    Devuelve Optional para manejar el caso donde no exista.
    Optional<Pago> findByReservaId(Long reservaId);

    // ✅ Obtiene todos los pagos realizados por un usuario.
    //    Navega: Pago → Reserva → Usuario → id
    List<Pago> findByReservaUsuarioId(Long usuarioId);

    // ✅ Calcula el total de ingresos en un rango de tiempo.
    //    Como fechaPago es LocalDateTime, usamos >= inicio y < fin
    //    para evitar problemas con milisegundos y límites del día.
    @Query("""
        SELECT SUM(p.monto)
        FROM Pago p
        WHERE p.fechaPago >= :inicio
          AND p.fechaPago < :fin
    """)
    Double totalIngresosPorDia(LocalDateTime inicio, LocalDateTime fin);
}
