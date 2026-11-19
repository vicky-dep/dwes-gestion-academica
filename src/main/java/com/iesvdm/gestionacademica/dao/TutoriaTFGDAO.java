package com.iesvdm.gestionacademica.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.iesvdm.gestionacademica.modelo.TutoriaDetalleDTO;
import com.iesvdm.gestionacademica.modelo.TutoriaTFG;

@Repository
public interface TutoriaTFGDAO {
	public List<TutoriaDetalleDTO> findByAlumnoId(int alumnoId);
	public List<TutoriaDetalleDTO> findByProfesorId(int profesorId);
	public void create(TutoriaTFG tutoriaTFG);
	public void update(TutoriaTFG tutoriaTFG);
	public void delete(int profesorId, int alumnoId);
}