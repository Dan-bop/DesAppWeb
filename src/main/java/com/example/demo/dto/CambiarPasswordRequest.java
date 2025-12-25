package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CambiarPasswordRequest {

    @NotBlank(message = "La contraseña actual es obligatoria")
    private String passwordActual;

    @NotBlank(message = "La nueva contraseña es obligatoria")
    @Size(min = 6, max = 100, message = "La nueva contraseña debe tener entre 6 y 100 caracteres")
    private String nuevaPassword;

    @NotBlank(message = "Debe confirmar la nueva contraseña")
    private String confirmarNuevaPassword;

}   
