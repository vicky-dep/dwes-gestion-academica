package com.iesvdm.gestionacademica.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.iesvdm.gestionacademica.dao.GrupoDAO;
import com.iesvdm.gestionacademica.modelo.Grupo;

@Service
public class GrupoService {
	private final GrupoDAO grupoDAO;

	public GrupoService(GrupoDAO grupoDAO) {
		this.grupoDAO = grupoDAO;
	}

	public List<Grupo> listAll() {
		return grupoDAO.findAll();
	}

	public Optional<Grupo> find(int id) {
		return grupoDAO.findById(id);
	}

	public void create(Grupo grupo) {
		grupoDAO.save(grupo);
	}

	public void update(Grupo grupo) {
		grupoDAO.update(grupo);
	}

	public void delete(int id) {
		grupoDAO.delete(id);
	}

	public List<Grupo> findGruposByTutorId(int profesorId) {
		return grupoDAO.findGruposByTutorId(profesorId);
	}
}