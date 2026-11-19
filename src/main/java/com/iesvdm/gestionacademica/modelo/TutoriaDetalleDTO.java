package com.iesvdm.gestionacademica.modelo;

import java.math.BigDecimal;

// record genera constructor, accesores y métodos habituales; sus componentes son inmutables.
public record TutoriaDetalleDTO(
		int profesorId,
		String nombreCompletoProfesor,
		int alumnoId,
		String nombreCompletoAlumno,
		String codigoGrupo,
		BigDecimal nota,
		String observaciones) {
}