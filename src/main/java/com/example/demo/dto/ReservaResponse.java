package com.example.demo.dto;

import lombok.Data;

@Data
public class ReservaResponse {

    private Long id;

    private CampoResponse campo;

    private UsuarioResponse usuario; // si es usuario registrado

    private String nombreInvitado;   // si es invitado
    private String emailInvitado;
    private String telefonoInvitado;

    private String fecha;
    private String horaInicio;
    private String horaFin;

    private String estado;
}
