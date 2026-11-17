package com.iesvdm.gestionacademica.controlador;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.iesvdm.gestionacademica.modelo.Grupo;
import com.iesvdm.gestionacademica.modelo.Profesor;
import com.iesvdm.gestionacademica.servicios.GrupoService;
import com.iesvdm.gestionacademica.servicios.ProfesorService;

@Controller
public class GrupoController {
	private final GrupoService grupoService;
	private final ProfesorService profesorService;

	public GrupoController(GrupoService grupoService, ProfesorService profesorService) {
		this.grupoService = grupoService;
		this.profesorService = profesorService;
	}

	@GetMapping("/grupos")
	public String listar(Model model) {
		var profesores = profesorService.listAll();
		Map<Integer, String> nombresProfesores = profesores.stream().collect(Collectors.toMap(
				Profesor::getId,
				profesor -> profesor.getNombre() + " " + profesor.getApellido1()));
		model.addAttribute("listaGrupos", grupoService.listAll());
		model.addAttribute("nombresProfesores", nombresProfesores);
		return "grupos";
	}

	@GetMapping("/grupos/crear")
	public String crear(Model model) {
		prepararFormulario(model, new Grupo(), "/grupos/crear", "Crear grupo");
		return "grupo-form";
	}

	@PostMapping("/grupos/crear")
	public String submitCrear(@ModelAttribute("grupo") Grupo grupo) {
		grupoService.create(grupo);
		return "redirect:/grupos";
	}

	@GetMapping("/grupos/editar/{id}")
	public String editar(@PathVariable int id, Model model) {
		return grupoService.find(id)
				.map(grupo -> {
					prepararFormulario(model, grupo, "/grupos/editar/" + id, "Editar grupo");
					return "grupo-form";
				})
				.orElse("redirect:/grupos");
	}

	@PostMapping("/grupos/editar/{id}")
	public String submitEditar(@PathVariable int id, @ModelAttribute("grupo") Grupo grupo) {
		grupo.setId(id);
		grupoService.update(grupo);
		return "redirect:/grupos";
	}

	@PostMapping("/grupos/borrar/{id}")
	public String submitBorrar(@PathVariable int id) {
		grupoService.delete(id);
		return "redirect:/grupos";
	}

	private void prepararFormulario(Model model, Grupo grupo, String accion, String titulo) {
		model.addAttribute("grupo", grupo);
		model.addAttribute("listaProfesores", profesorService.listAll());
		model.addAttribute("accionFormulario", accion);
		model.addAttribute("tituloFormulario", titulo);
	}
}