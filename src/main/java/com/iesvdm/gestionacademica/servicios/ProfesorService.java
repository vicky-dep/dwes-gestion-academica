package com.iesvdm.gestionacademica.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.iesvdm.gestionacademica.dao.ProfesorDAO;
import com.iesvdm.gestionacademica.modelo.Profesor;

@Service
public class ProfesorService {
	private final ProfesorDAO profesorDAO;

	public ProfesorService(ProfesorDAO profesorDAO) {
		this.profesorDAO = profesorDAO;
	}

	public List<Profesor> listAll() {
		return profesorDAO.findAll();
	}

	public Optional<Profesor> find(int id) {
		return profesorDAO.findById(id);
	}

	public void create(Profesor profesor) {
		profesorDAO.save(profesor);
	}

	public void update(Profesor profesor) {
		profesorDAO.update(profesor);
	}

	public void delete(int id) {
		profesorDAO.delete(id);
	}
}