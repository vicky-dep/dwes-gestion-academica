package com.iesvdm.gestionacademica.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.iesvdm.gestionacademica.dao.AlumnoDAO;
import com.iesvdm.gestionacademica.modelo.Alumno;

@Service
public class AlumnoService {
	private final AlumnoDAO alumnoDAO;

	public AlumnoService(AlumnoDAO alumnoDAO) {
		this.alumnoDAO = alumnoDAO;
	}

	public List<Alumno> listAll() {
		return alumnoDAO.findAll();
	}

	public Optional<Alumno> find(int id) {
		return alumnoDAO.findById(id);
	}

	public void create(Alumno alumno) {
		alumnoDAO.save(alumno);
	}

	public void update(Alumno alumno) {
		alumnoDAO.update(alumno);
	}

	public void delete(int id) {
		alumnoDAO.delete(id);
	}

	public List<Alumno> findAlumnosByGrupoId(int grupoId) {
		return alumnoDAO.findAlumnosByGrupoId(grupoId);
	}
}