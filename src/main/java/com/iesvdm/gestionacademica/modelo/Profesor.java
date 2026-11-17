package com.iesvdm.gestionacademica.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profesor {
	private int id;
	private String nif;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String departamento;
	private String email;
}