package com.iesvdm.gestionacademica.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.iesvdm.gestionacademica.modelo.Profesor;
import com.iesvdm.gestionacademica.servicios.ProfesorService;

@Controller
public class ProfesorController {
	private final ProfesorService profesorService;

	public ProfesorController(ProfesorService profesorService) {
		this.profesorService = profesorService;
	}

	@GetMapping("/profesores")
	public String listar(Model model) {
		model.addAttribute("listaProfesores", profesorService.listAll());
		return "profesores";
	}

	@GetMapping("/profesores/crear")
	public String crear(Model model) {
		model.addAttribute("profesor", new Profesor());
		model.addAttribute("accionFormulario", "/profesores/crear");
		model.addAttribute("tituloFormulario", "Crear profesor");
		return "profesor-form";
	}

	@PostMapping("/profesores/crear")
	public String submitCrear(@ModelAttribute("profesor") Profesor profesor) {
		profesorService.create(profesor);
		return "redirect:/profesores";
	}

	@GetMapping("/profesores/editar/{id}")
	public String editar(@PathVariable int id, Model model) {
		return profesorService.find(id)
				.map(profesor -> {
					model.addAttribute("profesor", profesor);
					model.addAttribute("accionFormulario", "/profesores/editar/" + id);
					model.addAttribute("tituloFormulario", "Editar profesor");
					return "profesor-form";
				})
				.orElse("redirect:/profesores");
	}

	@PostMapping("/profesores/editar/{id}")
	public String submitEditar(@PathVariable int id, @ModelAttribute("profesor") Profesor profesor) {
		profesor.setId(id);
		profesorService.update(profesor);
		return "redirect:/profesores";
	}

	@PostMapping("/profesores/borrar/{id}")
	public String submitBorrar(@PathVariable int id) {
		profesorService.delete(id);
		return "redirect:/profesores";
	}
}