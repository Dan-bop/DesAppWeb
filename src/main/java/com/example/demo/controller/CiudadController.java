package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Ciudad;
import com.example.demo.service.CiudadService;

@RestController
@RequestMapping("/ciudades")
public class CiudadController {

    @Autowired
    private CiudadService ciudadService;

    // ✅ Registrar ciudad
    @PostMapping
    public Ciudad registrarCiudad(@RequestBody Ciudad ciudad) {
        return ciudadService.registrarCiudad(ciudad);
    }

    // ✅ Listar todas las ciudades
    @GetMapping
    public List<Ciudad> listarCiudades() {
        return ciudadService.listarCiudades();
    }

    // ✅ Obtener ciudad por ID
    @GetMapping("/{id}")
    public Ciudad obtenerPorId(@PathVariable Long id) {
        return ciudadService.obtenerPorId(id);
    }
}
