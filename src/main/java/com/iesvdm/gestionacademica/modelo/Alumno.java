package com.iesvdm.gestionacademica.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alumno {
	private int id;
	private String nia;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String email;
	private Integer grupoId; // porque el valor puede ser null, ya que un alumno puede no estar asignado a ningún grupo
}