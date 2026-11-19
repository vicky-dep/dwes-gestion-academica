package com.iesvdm.gestionacademica.modelo;

public record EstadisticasTFGDTO(
		double notaMedia,
		double notaMaxima,
		double notaMinima,
		long totalEvaluados) {
}