package com.iesvdm.gestionacademica.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.iesvdm.gestionacademica.modelo.Grupo;

// Redundante en la interfaz: el bean lo registra @Repository en GrupoDAOImpl.
@Repository
public interface GrupoDAO {
	// En una interfaz, public es implícito.
	public List<Grupo> findAll();
	public Optional<Grupo> findById(int id);
	public void save(Grupo grupo);
	public void update(Grupo grupo);
	public void delete(int id);
	public List<Grupo> findGruposByTutorId(int profesorId);
}