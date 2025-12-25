package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ActualizarUsuarioRequest {

    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @Email(message = "Debe ser un email válido")
    private String email;

    @Pattern(regexp = "^\\d{9}$", message = "El teléfono debe tener 9 dígitos")
    private String telefono;

}