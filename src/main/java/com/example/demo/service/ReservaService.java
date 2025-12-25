package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ReservaRequest;
import com.example.demo.email.EmailService;
import com.example.demo.model.Campo;
import com.example.demo.model.Reserva;
import com.example.demo.repository.ReservaRepository;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private CampoService canchaService;

    @Autowired
    private UsuarioService usuarioService;

    // ✅ NUEVO: servicio de correo
    @Autowired
    private EmailService emailService;

    // Horario permitido
    private static final LocalTime HORA_APERTURA = LocalTime.of(7, 0);
    private static final LocalTime HORA_CIERRE = LocalTime.of(23, 0);

    // Duración mínima y máxima
    private static final int DURACION_MINIMA_MINUTOS = 60;
    private static final int DURACION_MAXIMA_MINUTOS = 120;

    // ==========================
    // CREAR RESERVA
    // ==========================
    public Reserva crearReserva(ReservaRequest request, Long usuarioId) {

        Campo campo = canchaService.obtenerPorId(request.getCampoId());

        LocalDate fecha = LocalDate.parse(request.getFecha());
        LocalTime inicio = LocalTime.parse(request.getHoraInicio());
        LocalTime fin = LocalTime.parse(request.getHoraFin());

        if (!inicio.isBefore(fin)) {
            throw new RuntimeException("La hora de inicio debe ser menor que la hora de fin");
        }

        long duracion = java.time.Duration.between(inicio, fin).toMinutes();
        if (duracion < DURACION_MINIMA_MINUTOS) {
            throw new RuntimeException("La duración mínima de una reserva es de 1 hora");
        }
        if (duracion > DURACION_MAXIMA_MINUTOS) {
            throw new RuntimeException("La duración máxima de una reserva es de 2 horas");
        }

        if (fecha.isBefore(LocalDate.now())) {
            throw new RuntimeException("No se pueden registrar reservas en fechas pasadas");
        }

        if (inicio.isBefore(HORA_APERTURA) || fin.isAfter(HORA_CIERRE)) {
            throw new RuntimeException("Las reservas solo están permitidas entre " +
                    HORA_APERTURA + " y " + HORA_CIERRE);
        }

        boolean existeChoque = reservaRepository
                .existsByCampoIdAndFechaAndHoraInicioLessThanAndHoraFinGreaterThan(
                        campo.getId(), fecha, fin, inicio);

        if (existeChoque) {
            throw new RuntimeException("El campo ya está reservada en ese horario");
        }

        // Crear reserva
        Reserva reserva = new Reserva();
        reserva.setCampo(campo);
        reserva.setFecha(fecha);
        reserva.setHoraInicio(inicio);
        reserva.setHoraFin(fin);
        reserva.setEstado("PENDIENTE");

        // Usuario o invitado
        if (usuarioId != null) {
            reserva.setUsuario(usuarioService.buscarPorId(usuarioId));
        } else {
            reserva.setNombreInvitado(request.getNombreInvitado());
            reserva.setEmailInvitado(request.getEmailInvitado());
            reserva.setTelefonoInvitado(request.getTelefonoInvitado());
        }

        // Guardar reserva
        Reserva reservaGuardada = reservaRepository.save(reserva);

        // ==========================
        // ENVÍO DE CORREO 📧
        // ==========================
        String correo;
        String nombre;

        if (reservaGuardada.getUsuario() != null) {
            correo = reservaGuardada.getUsuario().getEmail();
            nombre = reservaGuardada.getUsuario().getNombre();
        } else {
            correo = reservaGuardada.getEmailInvitado();
            nombre = reservaGuardada.getNombreInvitado();
        }

        String urlPagar = "http://localhost:4200/pago/" + reservaGuardada.getId();
        String urlVer = "http://localhost:4200/reserva/" + reservaGuardada.getId();

        String mensaje =
            "[IMG=" + reservaGuardada.getCampo().getImagen() + "]" +
            "Hola " + nombre + ",\n\n" +
            "Tu reserva ha sido creada exitosamente en Pacific Sport ⚽\n\n" +
            "📍 Campo: " + reservaGuardada.getCampo().getNombre() + "\n" +
            "🏟️ Sede: " + reservaGuardada.getCampo().getSede().getNombre() + "\n" +
            "🌆 Ciudad: " + reservaGuardada.getCampo().getSede().getCiudad().getNombre() + "\n" +
            "📅 Fecha: " + reservaGuardada.getFecha() + "\n" +
            "⏰ Horario: " + reservaGuardada.getHoraInicio() + " - " + reservaGuardada.getHoraFin() + "\n\n" +
            "Estado actual: PENDIENTE\n\n" +

            // ✅ Botones agregados
            "[BTN_PAGAR=" + urlPagar + "]\n" +
            "[BTN_VER=" + urlVer + "]\n\n" +

            "Te notificaremos cuando el pago sea confirmado.";


        emailService.enviarCorreo(
            correo,
            "Confirmación de Reserva - Pacific Sport",
            mensaje
        );

        return reservaGuardada;
    }

    // ==========================
    // OTROS MÉTODOS (IGUALES)
    // ==========================
    public Reserva buscarPorId(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
    }

    public void cancelarReserva(Long id) {
        Reserva reserva = buscarPorId(id);
        reserva.setEstado("CANCELADO");
        reservaRepository.save(reserva);
    }

    public List<Reserva> listarPorUsuario(Long usuarioId) {
        return reservaRepository.findByUsuarioId(usuarioId);
    }

    public List<Reserva> listarPorCanchaYFecha(Long campoId, LocalDate fecha) {
        return reservaRepository.findByCampoIdAndFecha(campoId, fecha);
    }

    public List<String> obtenerDisponibilidad(Long campoId, LocalDate fecha) {
        List<LocalTime> slots = new ArrayList<>();
        LocalTime hora = HORA_APERTURA;

        while (hora.isBefore(HORA_CIERRE)) {
            slots.add(hora);
            hora = hora.plusHours(1);
        }

        List<Reserva> reservas = reservaRepository.findByCampoIdAndFecha(campoId, fecha);

        for (Reserva reserva : reservas) {
            LocalTime inicio = reserva.getHoraInicio();
            LocalTime fin = reserva.getHoraFin();

            slots.removeIf(slot -> !slot.isBefore(inicio) && slot.isBefore(fin));
        }

        // Convertir a String al final
        List<String> horarios = new ArrayList<>();
        for (LocalTime slot : slots) {
            horarios.add(slot + " - " + slot.plusHours(1));
        }

        return horarios;
    }


    public List<Reserva> listarPorEstado(String estado) {
        return reservaRepository.findByEstado(estado);
    }

    public List<Reserva> listarTodas() {
        return reservaRepository.findAll();
    }
}
