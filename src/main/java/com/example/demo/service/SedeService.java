package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.example.demo.model.Sede;
import com.example.demo.repository.SedeRepository;

@Service
public class SedeService {

    @Autowired
    private SedeRepository sedeRepository;

    // ✅ Registrar sede
    public Sede registrarSede(Sede sede) {
        return sedeRepository.save(sede);
    }

    // ✅ Listar todas las sedes
    public List<Sede> listarSedes() {
        return sedeRepository.findAll();
    }

    // ✅ Listar sedes por ciudad
    public List<Sede> listarPorCiudad(Long ciudadId) {
        return sedeRepository.findByCiudadId(ciudadId);
    }

    // ✅ Obtener sede por ID
    public Sede obtenerPorId(Long id) {
        return sedeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sede no encontrada con ID: " + id));
    }

    // ✅ Actualizar sede completa (PUT)
    public Sede actualizarSede(Long id, Sede sedeActualizada) {
        Sede sedeExistente = obtenerPorId(id); // Reutiliza la validación de existencia

        // Copiamos todas las propiedades excepto el ID
        sedeActualizada.setId(sedeExistente.getId());
        return sedeRepository.save(sedeActualizada);
    }

    // Actualización parcial (PATCH) - solo campos enviados
    public Sede actualizarParcialSede(Long id, Sede sedeParcial) {
        Sede sedeExistente = obtenerPorId(id); // Verifica que exista

        // Copia solo las propiedades no nulas de sedeParcial a sedeExistente
        BeanUtils.copyProperties(sedeParcial, sedeExistente,
                "id"); // Ignora el campo id para no sobrescribirlo

        return sedeRepository.save(sedeExistente);
    }

    // Eliminar sede
    public void eliminarSede(Long id) {
        Sede sede = obtenerPorId(id); // Verifica que exista (lanza excepción si no)
        sedeRepository.delete(sede);
        // Alternativa: sedeRepository.deleteById(id); (lanza excepción si no existe)
    }
}