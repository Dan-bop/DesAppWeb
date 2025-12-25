package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	//Verificar si el email ya existe
	boolean existsByEmail(String email);
	
	//Buscar usuario por email
	Usuario findByEmail(String email);

}
