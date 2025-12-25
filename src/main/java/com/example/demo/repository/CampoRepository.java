package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Campo;

public interface CampoRepository extends JpaRepository<Campo, Long> {

    List<Campo> findBySedeId(Long sedeId);
}

