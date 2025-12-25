package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PagoRequest;
import com.example.demo.email.EmailService;
import com.example.demo.model.Pago;
import com.example.demo.model.Reserva;
import com.example.demo.service.PagoService;

@RestController
@RequestMapping("/pagos")
@CrossOrigin("*")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/registrar/{reservaId}")
    public Pago procesarPago(
            @PathVariable Long reservaId,
            @RequestBody PagoRequest request) {

        Pago pago = pagoService.registrarPago(reservaId, request);
        Reserva reserva = pago.getReserva();

        // RF11 – correo de confirmación de pago
        String correo = reserva.getUsuario() != null
                ? reserva.getUsuario().getEmail()
                : reserva.getEmailInvitado();

        emailService.enviarCorreo(
            correo,
            "Confirmación de Pago",
            "Tu pago ha sido registrado exitosamente.\n" +
            "Campo: " + reserva.getCampo().getNombre() + "\n" +
            "Sede: " + reserva.getCampo().getSede().getNombre() + "\n" +
            "Ciudad: " + reserva.getCampo().getSede().getCiudad().getNombre() + "\n" +
            "Fecha: " + reserva.getFecha() + "\n" +
            "Horario: " + reserva.getHoraInicio() + " - " + reserva.getHoraFin() + "\n" +
            "Monto pagado: " + pago.getMonto() + "\n" +
            "Método: " + pago.getMetodoPago()
        );

        return pago;
    }

    //  Obtener pago por reserva
    @GetMapping("/reserva/{reservaId}")
    public Pago obtenerPagoPorReserva(@PathVariable Long reservaId) {
        return pagoService.obtenerPagoPorReserva(reservaId);
    }

    //istorial de pagos por usuario
    @GetMapping("/usuario/{usuarioId}")
    public java.util.List<Pago> listarPagosPorUsuario(@PathVariable Long usuarioId) {
        return pagoService.listarPagosPorUsuario(usuarioId);
    }
}

