package com.example.demo.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ActualizarUsuarioRequest;
import com.example.demo.dto.CambiarPasswordRequest;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegistroRequest;
import com.example.demo.dto.UsuarioResponse;
import com.example.demo.model.Rol;
import com.example.demo.model.Usuario;
import com.example.demo.repository.RolRepository;
import com.example.demo.repository.UsuarioRepository;

import jakarta.annotation.PostConstruct;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private RolRepository rolRepository;
    
 // 🔽 Crear admin por defecto al iniciar la aplicación 
    @PostConstruct public void initAdmin() { 
    	if (!usuarioRepository.existsByEmail("admin@pacificsport.com")) { 
    		Usuario admin = new Usuario(); admin.setNombre("Administrador"); 
    		admin.setEmail("admin@pacificsport.com"); admin.setTelefono("999999999");
    		admin.setPassword(passwordEncoder.encode("admin123")); // contraseña inicial 
    		Rol rolAdmin = rolRepository.findByNombre("ROLE_ADMIN") 
    				.orElseThrow(() -> new RuntimeException("Rol ADMIN no encontrado")); 
    		admin.setRoles(Set.of(rolAdmin)); usuarioRepository.save(admin); }
    	}
    

    // ✅ Registrar usuario
    public UsuarioResponse registrarUsuario(RegistroRequest request) {

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setTelefono(request.getTelefono());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));

        Rol rolUser = rolRepository.findByNombre("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Rol USER no encontrado"));
        usuario.setRoles(Set.of(rolUser));

        Usuario guardado = usuarioRepository.save(usuario);

        UsuarioResponse response = new UsuarioResponse();
        response.setId(guardado.getId());
        response.setNombre(guardado.getNombre());
        response.setEmail(guardado.getEmail());
        response.setTelefono(guardado.getTelefono());
        response.setRol(guardado.getRoles().iterator().next().getNombre()); // ✅ AGREGADO

        return response;
    }

    // ✅ Login
    public UsuarioResponse login(LoginRequest request) {

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail());
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        UsuarioResponse response = new UsuarioResponse();
        response.setId(usuario.getId());
        response.setNombre(usuario.getNombre());
        response.setEmail(usuario.getEmail());
        response.setTelefono(usuario.getTelefono());
        response.setRol(usuario.getRoles().iterator().next().getNombre()); // ✅ AGREGADO

        return response;
    }

    // ✅ Buscar por ID (solo interno)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    // ✅ Obtener perfil por email
    public UsuarioResponse obtenerPorEmail(String email) {

        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado con email: " + email);
        }

        UsuarioResponse response = new UsuarioResponse();
        response.setId(usuario.getId());
        response.setNombre(usuario.getNombre());
        response.setEmail(usuario.getEmail());
        response.setTelefono(usuario.getTelefono());
        response.setRol(usuario.getRoles().iterator().next().getNombre()); // ✅ AGREGADO

        return response;
    }

    // ✅ Eliminar usuario
    public void eliminarUsuario(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado con email: " + email);
        }
        usuarioRepository.delete(usuario);
    }

    // ✅ Actualizar perfil
    public UsuarioResponse actualizarUsuario(String emailActual, ActualizarUsuarioRequest request) {

        Usuario usuario = usuarioRepository.findByEmail(emailActual);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        if (request.getNombre() != null && !request.getNombre().trim().isEmpty()) {
            usuario.setNombre(request.getNombre());
        }

        if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
            if (!usuario.getEmail().equals(request.getEmail()) &&
                usuarioRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("El email ya está en uso por otro usuario");
            }
            usuario.setEmail(request.getEmail());
        }

        if (request.getTelefono() != null && !request.getTelefono().trim().isEmpty()) {
            usuario.setTelefono(request.getTelefono());
        }

        Usuario actualizado = usuarioRepository.save(usuario);

        UsuarioResponse response = new UsuarioResponse();
        response.setId(actualizado.getId());
        response.setNombre(actualizado.getNombre());
        response.setEmail(actualizado.getEmail());
        response.setTelefono(actualizado.getTelefono());
        response.setRol(actualizado.getRoles().iterator().next().getNombre()); // ✅ AGREGADO

        return response;
    }

    // ✅ Cambiar contraseña
    public void cambiarPassword(String email, CambiarPasswordRequest request) {

        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        if (!passwordEncoder.matches(request.getPasswordActual(), usuario.getPassword())) {
            throw new RuntimeException("La contraseña actual es incorrecta");
        }

        if (!request.getNuevaPassword().equals(request.getConfirmarNuevaPassword())) {
            throw new RuntimeException("La nueva contraseña y la confirmación no coinciden");
     }

        if (passwordEncoder.matches(request.getNuevaPassword(), usuario.getPassword())) {
            throw new RuntimeException("La nueva contraseña debe ser diferente a la actual");
        }

        usuario.setPassword(passwordEncoder.encode(request.getNuevaPassword()));
        usuarioRepository.save(usuario);
    }
}
