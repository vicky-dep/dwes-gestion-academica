package com.iesvdm.gestionacademica.servicios;

import java.util.List;

import org.springframework.stereotype.Service;

import com.iesvdm.gestionacademica.dao.TutoriaTFGDAO;
import com.iesvdm.gestionacademica.modelo.TutoriaDetalleDTO;
import com.iesvdm.gestionacademica.modelo.TutoriaTFG;

@Service
public class TutoriaTFGService {
	private final TutoriaTFGDAO tutoriaTFGDAO;

	public TutoriaTFGService(TutoriaTFGDAO tutoriaTFGDAO) {
		this.tutoriaTFGDAO = tutoriaTFGDAO;
	}

	public List<TutoriaDetalleDTO> findByAlumnoId(int alumnoId) {
		return tutoriaTFGDAO.findByAlumnoId(alumnoId);
	}

	public List<TutoriaDetalleDTO> findByProfesorId(int profesorId) {
		return tutoriaTFGDAO.findByProfesorId(profesorId);
	}

	public void create(TutoriaTFG tutoriaTFG) {
		tutoriaTFGDAO.create(tutoriaTFG);
	}

	public void update(TutoriaTFG tutoriaTFG) {
		tutoriaTFGDAO.update(tutoriaTFG);
	}

	public void delete(int profesorId, int alumnoId) {
		tutoriaTFGDAO.delete(profesorId, alumnoId);
	}
}