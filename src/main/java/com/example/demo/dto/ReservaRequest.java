package com.example.demo.dto;

import lombok.Data;

@Data
public class ReservaRequest {

    private Long campoId;

    private String fecha;       // formato: YYYY-MM-DD
    private String horaInicio;  // formato: HH:mm
    private String horaFin;     // formato: HH:mm

    // Datos del invitado (si no está logueado)
    private String nombreInvitado;
    private String emailInvitado;
    private String telefonoInvitado;
   
}
