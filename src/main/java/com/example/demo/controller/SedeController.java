package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;  // Nuevo import
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Sede;
import com.example.demo.service.SedeService;

@RestController
@RequestMapping("/sedes")
public class SedeController {

    @Autowired
    private SedeService sedeService;

    // 1. Registrar sede (CREATE)
    @PostMapping
    public Sede registrarSede(@RequestBody Sede sede) {
        return sedeService.registrarSede(sede);
    }

    // 2. Listar todas las sedes (READ)
    @GetMapping
    public List<Sede> listarSedes() {
        return sedeService.listarSedes();
    }

    // 3. Listar sedes por ciudad (READ filtrado)
    @GetMapping("/ciudad/{ciudadId}")
    public List<Sede> listarPorCiudad(@PathVariable Long ciudadId) {
        return sedeService.listarPorCiudad(ciudadId);
    }

    // 4. Obtener sede por ID (READ individual)
    @GetMapping("/{id}")
    public ResponseEntity<Sede> obtenerPorId(@PathVariable Long id) {
        Sede sede = sedeService.obtenerPorId(id);
        return ResponseEntity.ok(sede);  // Devuelve 200 OK con la sede
        // Si no existe, podrías lanzar excepción y manejarla con 404
    }

    // 5. Actualizar sede completa (UPDATE - PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Sede> actualizarSede(@PathVariable Long id, @RequestBody Sede sedeActualizada) {
        Sede sede = sedeService.actualizarSede(id, sedeActualizada);
        return ResponseEntity.ok(sede);
    }

    // 6. Actualización parcial (opcional - PATCH)
    // Útil si solo quieres actualizar algunos campos
    @PatchMapping("/{id}")
    public ResponseEntity<Sede> actualizarParcialSede(@PathVariable Long id, @RequestBody Sede sedeParcial) {
        Sede sede = sedeService.actualizarParcialSede(id, sedeParcial);
        return ResponseEntity.ok(sede);
    }

    // 7. Eliminar sede (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSede(@PathVariable Long id) {
        sedeService.eliminarSede(id);
        return ResponseEntity.noContent().build();  // Devuelve 204 No Content (éxito sin cuerpo)
    }
}