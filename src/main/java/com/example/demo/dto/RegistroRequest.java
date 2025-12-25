package com.example.demo.dto;

import lombok.Data;

@Data
public class RegistroRequest {
	private String nombre;
	private String email;
	private String telefono;
	private String password;
	
	}