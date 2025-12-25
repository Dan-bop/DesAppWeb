package com.example.demo.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value; 
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Campo;
import com.example.demo.repository.CampoRepository;

import java.io.IOException; 
import java.nio.file.Files; 
import java.nio.file.Path; 
import java.nio.file.Paths; 
import java.util.UUID;

@Service
public class CampoService {

    @Autowired
    private CampoRepository campoRepository;

    @Value("${file.upload-dir}") 
    private String rutaAlmacenamiento;

    // Registrar campos
    public Campo registrarCancha(Campo cancha) {
        return campoRepository.save(cancha);
    }

    //  Listar todas las campos
    public List<Campo> listarCanchas() {
        return campoRepository.findAll();
    }

    // Listar campos por sede
    public List<Campo> listarPorSede(Long sedeId) {
        return campoRepository.findBySedeId(sedeId);
    }

    // Obtener campo por ID
    public Campo obtenerPorId(Long id) {
        return campoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cancha no encontrada"));
    }

    public Campo actualizarCampo(Long id, Campo campoDetalles) {
        
        // Busca campo existente
        Campo campoExistente = campoRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Campo no encontrado con ID: " + id));
        if (campoDetalles.getNombre() != null) {
            campoExistente.setNombre(campoDetalles.getNombre());
        }
        if (campoDetalles.getPrecioHora() != null) {
            campoExistente.setPrecioHora(campoDetalles.getPrecioHora());
        }
        if (campoDetalles.getImagen() != null) {
			campoExistente.setImagen(campoDetalles.getImagen());
		}
        return campoRepository.save(campoExistente);
    }
    // Eliminar campo por ID
    public void eliminarCampo(Long id) {
        if (!campoRepository.existsById(id)) {
            throw new NoSuchElementException("Campo no encontrado con ID: " + id + " para eliminar.");
        }
        campoRepository.deleteById(id);
    }
	public void subirImagen(Long id, MultipartFile archivo) {
		
        // Busca campo existente
        Campo campo = campoRepository.findById(id)
            // Lanza si no existe
            .orElseThrow(() -> new NoSuchElementException("Campo no encontrado con ID: " + id));

        // Eliminar imagen antigua
        if (campo.getImagen() != null) {
            eliminarArchivoFisico(campo.getImagen()); // Llama método auxiliar
        }

        // Generar nombre único
        String nombreOriginal = archivo.getOriginalFilename();
        String extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
        String nombreArchivo = UUID.randomUUID().toString() + extension;
        
        // Definir la ruta de destino
        Path rutaCompleta = Paths.get(rutaAlmacenamiento, nombreArchivo);

        try {
            // Guardar archivo físico
            Files.copy(archivo.getInputStream(), rutaCompleta);

            // Actualizar URL en DB
            campo.setImagen("/imagenes/" + nombreArchivo);
            campoRepository.save(campo); // Guarda cambios
            
        } catch (IOException e) {
            // Error al guardar
            throw new RuntimeException("Error al guardar la imagen", e);
        }
	}

	// Eliminar imagen asociada al campo
	public void eliminarImagen(Long id) {
        Campo campo = campoRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Campo no encontrado con ID: " + id));
        String imagenUrl = campo.getImagen();
        if (imagenUrl != null) {
            eliminarArchivoFisico(imagenUrl);
            campo.setImagen(null); 
            campoRepository.save(campo);
        }
	}
    private void eliminarArchivoFisico(String imagenUrl) {
        // Extrae nombre de archivo
        String nombreArchivo = imagenUrl.substring(imagenUrl.lastIndexOf("/") + 1);
        Path rutaCompleta = Paths.get(rutaAlmacenamiento, nombreArchivo);

        try {
            Files.deleteIfExists(rutaCompleta);
        } catch (IOException e) {
            System.err.println("Advertencia: No se pudo eliminar el archivo físico: " + rutaCompleta);
        }
    }
}