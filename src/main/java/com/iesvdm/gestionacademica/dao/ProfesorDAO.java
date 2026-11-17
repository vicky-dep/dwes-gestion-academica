package com.iesvdm.gestionacademica.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.iesvdm.gestionacademica.modelo.Profesor;

// Redundante en la interfaz: el bean lo registra @Repository en ProfesorDAOImpl.
@Repository
public interface ProfesorDAO {
	// En una interfaz, public es implícito.
	public List<Profesor> findAll();
	public Optional<Profesor> findById(int id);
	public void save(Profesor profesor);
	public void update(Profesor profesor);
	public void delete(int id);
}