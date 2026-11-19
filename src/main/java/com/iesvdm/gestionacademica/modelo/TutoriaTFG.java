package com.iesvdm.gestionacademica.modelo;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TutoriaTFG {
	private int profesorId;
	private int alumnoId;
	private BigDecimal nota;
	private String observaciones;
}