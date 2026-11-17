package com.iesvdm.gestionacademica.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grupo {
	private int id;
	private String codigo;
	private String nombre;
	private int curso;
	private Integer tutorId; // porque el valor puede ser null, ya que un grupo puede no tener tutor asignado
}