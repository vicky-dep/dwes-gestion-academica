package com.iesvdm.gestionacademica.modelo;

// record genera constructor, accesores y métodos habituales; sus componentes son inmutables.
public record AlumnoGrupoDTO(
		int id,
		String nia,
		String nombre,
		String apellido1,
		String apellido2,
		String email,
		Integer grupoId,
		String codigoGrupo,
		String nombreGrupo) {
}