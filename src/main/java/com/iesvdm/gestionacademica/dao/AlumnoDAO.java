package com.iesvdm.gestionacademica.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.iesvdm.gestionacademica.modelo.Alumno;

// Redundante en la interfaz: el bean lo registra @Repository en AlumnoDAOImpl.
@Repository
public interface AlumnoDAO {
	// En una interfaz, public es implícito (no seria necesario).
	public List<Alumno> findAll();
	public Optional<Alumno> findById(int id);
	public void save(Alumno alumno);
	public void update(Alumno alumno);
	public void delete(int id);
	public List<Alumno> findAlumnosByGrupoId(int grupoId);
}