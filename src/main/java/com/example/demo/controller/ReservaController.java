package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ReservaRequest;
import com.example.demo.model.Reserva;
import com.example.demo.service.ReservaService;

@RestController
@RequestMapping("/reservas")
@CrossOrigin(origins = "http://localhost:4200")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    // Crear reserva
    @PostMapping
    public Reserva crearReserva(
            @RequestBody ReservaRequest request,
            @RequestParam(required = false) Long userId) {

        return reservaService.crearReserva(request, userId);
    }

    // Buscar reserva por ID
    @GetMapping("/{id}")
    public Reserva buscarPorId(@PathVariable Long id) {
        return reservaService.buscarPorId(id);
    }

    // Cancelar reserva
    @PutMapping("/cancelar/{id}")
    public String cancelar(@PathVariable Long id) {
        reservaService.cancelarReserva(id);
        return "Reserva cancelada correctamente";
    }

    // Listar reservas por usuario
    @GetMapping("/usuario/{usuarioId}")
    public List<Reserva> listarPorUsuario(@PathVariable Long usuarioId) {
        return reservaService.listarPorUsuario(usuarioId);
    }

    // Listar reservas por campo y fecha
    @GetMapping("/campo")
    public List<Reserva> listarPorCanchaYFecha(
            @RequestParam Long campoId,
            @RequestParam String fecha) {

        LocalDate f = LocalDate.parse(fecha);
        return reservaService.listarPorCanchaYFecha(campoId, f);
    }

    // ✅  Disponibilidad de horarios
    @GetMapping("/disponibilidad")
    public List<String> obtenerDisponibilidad(
            @RequestParam Long campoId,
            @RequestParam String fecha) {

        LocalDate f = LocalDate.parse(fecha);
        return reservaService.obtenerDisponibilidad(campoId, f);
    }

    // Listar todas las reservas (admin)
    @GetMapping
    public List<Reserva> listarTodas() {
        return reservaService.listarTodas();
    }

    // Buscar reservas por estado (PENDIENTE, PAGADO, CANCELADO)
    @GetMapping("/estado/{estado}")
    public List<Reserva> listarPorEstado(@PathVariable String estado) {
        return reservaService.listarPorEstado(estado);
    }
}
