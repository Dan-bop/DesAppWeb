package com.example.demo.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PagoRequest;
import com.example.demo.model.Pago;
import com.example.demo.model.Reserva;
import com.example.demo.repository.PagoRepository;
import com.example.demo.repository.ReservaRepository;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    public Pago registrarPago(Long reservaId, PagoRequest request) {

        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        // No pagar reservas canceladas
        if (reserva.getEstado().equals("CANCELADO")) {
            throw new RuntimeException("No se puede pagar una reserva cancelada");
        }

        // No pagar reservas ya pagadas
        if (reserva.getEstado().equals("PAGADO")) {
            throw new RuntimeException("La reserva ya está pagada");
        }

        // No pagar dos veces
        if (pagoRepository.existsByReservaId(reservaId)) {
            throw new RuntimeException("La reserva ya tiene un pago registrado");
        }

        // ✅ Crear pago
        Pago pago = new Pago();
        pago.setReserva(reserva);
        pago.setMetodoPago(request.getMetodoPago());
        pago.setFechaPago(LocalDateTime.now());
        pago.setMonto(new BigDecimal(request.getMonto()));

        //  Datos según método
        switch (request.getMetodoPago().toUpperCase()) {
            case "TARJETA":
                pago.setNumeroTarjeta(request.getNumeroTarjeta());
                pago.setTitular(request.getTitular());
                pago.setVencimiento(request.getVencimiento());
                pago.setCvv(request.getCvv());
                break;

            case "YAPE":
            case "PLIN":
                pago.setCodigoQR(request.getCodigoQR());
                break;

            default:
                throw new RuntimeException("Método de pago no válido");
        }

        //  Cambiar estado de la reserva
        reserva.setEstado("PAGADO");
        reservaRepository.save(reserva);

        return pagoRepository.save(pago);
    }

    //  Obtener pago por reserva
    public Pago obtenerPagoPorReserva(Long reservaId) {
        return pagoRepository.findByReservaId(reservaId)
                .orElseThrow(() -> new RuntimeException("No existe pago para esta reserva"));
    }

    //  Historial de pagos por usuario
    public List<Pago> listarPagosPorUsuario(Long usuarioId) {
        return pagoRepository.findByReservaUsuarioId(usuarioId);
    }
}
