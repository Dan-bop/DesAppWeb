package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping; 
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Campo;
import com.example.demo.service.CampoService;

@RestController
@RequestMapping("/campos") 
public class CampoController {

    @Autowired
    private CampoService campoService; 

    // Registrar una campo.
    @PostMapping
    public Campo registrarCancha(@RequestBody Campo campo) {
        return campoService.registrarCancha(campo);
    }

    // Listar todas las campos.
    @GetMapping
    public List<Campo> listarCanchas() {
        return campoService.listarCanchas();
    }

    //  Listar campos por sede.
    @GetMapping("/sede/{sedeId}")
    public List<Campo> listarPorSede(@PathVariable Long sedeId) {
        return campoService.listarPorSede(sedeId);
    }

    // Obtener campo por ID.
    @GetMapping("/{id}")
    public Campo obtenerPorId(@PathVariable Long id) {
        return campoService.obtenerPorId(id);
    }
    
    // Actualizar campo por ID.
    @PutMapping("/{id}")
    public Campo actualizarCancha(@PathVariable Long id, @RequestBody Campo campoDetalles) {
        return campoService.actualizarCampo(id, campoDetalles);
    }

    // Eliminar campo por ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCancha(@PathVariable Long id) {
        campoService.eliminarCampo(id);
        // Retorna HTTP 200 OK.
        return ResponseEntity.ok().build(); 
    }
 
    @PostMapping("/{id}/imagen")
    public ResponseEntity<?> subirImagenCampo(
        @PathVariable Long id, 
        @RequestParam("archivo") MultipartFile archivo) { 
         campoService.subirImagen(id, archivo);        
         return ResponseEntity.ok("Imagen subida correctamente");
    }

    // Eliminar imagen asociada.
    @DeleteMapping("/{id}/imagen")
    public ResponseEntity<?> eliminarImagenCampo(@PathVariable Long id) {
    	
        campoService.eliminarImagen(id);
        return ResponseEntity.ok("Imagen eliminada correctamente"); 
    }
}
