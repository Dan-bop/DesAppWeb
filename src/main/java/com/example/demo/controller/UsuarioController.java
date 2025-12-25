package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ActualizarUsuarioRequest;
import com.example.demo.dto.CambiarPasswordRequest;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegistroRequest;
import com.example.demo.dto.UsuarioResponse;
import com.example.demo.email.EmailService;
import com.example.demo.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:4200") // Mejor especificar el origen en lugar de "*"
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmailService emailService;

    // Registro
    @PostMapping("/registro")
    public UsuarioResponse registrar(@RequestBody RegistroRequest request) {
        UsuarioResponse usuario = usuarioService.registrarUsuario(request);

        emailService.enviarCorreo(
            usuario.getEmail(),
            "🎉 Bienvenido a Pacific Sport 🎉",
            "Hola " + usuario.getNombre() + " 👋\n\n" +
            "¡Gracias por unirte a la familia de Pacific Sport! ⚽🏟️\n\n" +
            "Desde ahora podrás:\n" +
            "• Reservar canchas fácilmente\n" +
            "• Elegir sedes y horarios disponibles\n" +
            "• Organizar tus partidos sin complicaciones\n\n" +
            "Estamos felices de tenerte con nosotros.\n" +
            "Prepárate para jugar como se debe 🔥\n\n" +
            "¡Nos vemos en la cancha!\n" +
            "Equipo Pacific Sport"
        );

        return usuario;
    }


    // Login
    @PostMapping("/login")
    public UsuarioResponse login(@RequestBody LoginRequest request) {
        return usuarioService.login(request);
    }

    // NEW: Obtener perfil del usuario autenticado (útil después de login)
    @GetMapping("/perfil")
    public UsuarioResponse obtenerPerfil(@RequestParam String email) {
        return usuarioService.obtenerPorEmail(email);
    }



    // NEW: Actualizar usuario (por ejemplo, cambiar nombre, teléfono, etc.)
    @PutMapping("/perfil")
    public UsuarioResponse actualizarPerfil(Authentication authentication, @RequestBody ActualizarUsuarioRequest request) {
        String email = authentication.getName();
        return usuarioService.actualizarUsuario(email, request);
    }

    // NEW: Cambiar contraseña
    @PutMapping("/cambiar-password")
    public ResponseEntity<String> cambiarPassword(Authentication authentication, @RequestBody CambiarPasswordRequest request) {
        String email = authentication.getName();
        usuarioService.cambiarPassword(email, request);
        return ResponseEntity.ok("Contraseña actualizada correctamente");
    }

    // NEW: Eliminar cuenta (baja lógica o física)
    @DeleteMapping("/perfil")
    public ResponseEntity<Void> eliminarCuenta(Authentication authentication) {
        String email = authentication.getName();
        usuarioService.eliminarUsuario(email);
        return ResponseEntity.noContent().build();
    }
}