package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Ciudad;
import com.example.demo.repository.CiudadRepository;

@Service
public class CiudadService {
    
    @Autowired
    private CiudadRepository ciudadRepository;

    // ✅ Registrar ciudad
    public Ciudad registrarCiudad(Ciudad ciudad) {
        return ciudadRepository.save(ciudad);
    }

    // ✅ Listar ciudades
    public List<Ciudad> listarCiudades() {
        return ciudadRepository.findAll();
    }

    // ✅ Obtener ciudad por ID
    public Ciudad obtenerPorId(Long id) {
        return ciudadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));
    }
}
