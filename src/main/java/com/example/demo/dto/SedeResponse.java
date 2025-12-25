package com.example.demo.dto;

public class SedeResponse {

    private Long id;
    private String nombre;
    private String direccion;
    private String imagen;

    private CiudadResponse ciudad;  // ✅ Objeto completo
}
