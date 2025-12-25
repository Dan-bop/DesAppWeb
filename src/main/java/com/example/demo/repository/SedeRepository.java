package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Sede;

public interface SedeRepository extends JpaRepository<Sede, Long> {

    List<Sede> findByCiudadId(Long ciudadId);
}
