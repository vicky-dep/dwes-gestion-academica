package com.iesvdm.gestionacademica.controlador;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.iesvdm.gestionacademica.modelo.Alumno;
import com.iesvdm.gestionacademica.modelo.Grupo;
import com.iesvdm.gestionacademica.servicios.AlumnoService;
import com.iesvdm.gestionacademica.servicios.GrupoService;

@Controller
public class AlumnoController {
	private final AlumnoService alumnoService;
	private final GrupoService grupoService;

	public AlumnoController(AlumnoService alumnoService, GrupoService grupoService) {
		this.alumnoService = alumnoService;
		this.grupoService = grupoService;
	}

	@GetMapping("/alumnos")
	public String listar(Model model) {
		Map<Integer, String> nombresGrupos = grupoService.listAll().stream().collect(Collectors.toMap(
				Grupo::getId,
				grupo -> grupo.getCodigo() + " - " + grupo.getNombre()));
		model.addAttribute("listaAlumnos", alumnoService.listAll());
		model.addAttribute("nombresGrupos", nombresGrupos);
		return "alumnos";
	}

	@GetMapping("/alumnos/crear")
	public String crear(Model model) {
		prepararFormulario(model, new Alumno(), "/alumnos/crear", "Crear alumno");
		return "alumno-form";
	}

	@PostMapping("/alumnos/crear")
	public String submitCrear(@ModelAttribute("alumno") Alumno alumno) {
		alumnoService.create(alumno);
		return "redirect:/alumnos";
	}

	@GetMapping("/alumnos/editar/{id}")
	public String editar(@PathVariable int id, Model model) {
		return alumnoService.find(id)
				.map(alumno -> {
					prepararFormulario(model, alumno, "/alumnos/editar/" + id, "Editar alumno");
					return "alumno-form";
				})
				.orElse("redirect:/alumnos");
	}

	@PostMapping("/alumnos/editar/{id}")
	public String submitEditar(@PathVariable int id, @ModelAttribute("alumno") Alumno alumno) {
		alumno.setId(id);
		alumnoService.update(alumno);
		return "redirect:/alumnos";
	}

	@PostMapping("/alumnos/borrar/{id}")
	public String submitBorrar(@PathVariable int id) {
		alumnoService.delete(id);
		return "redirect:/alumnos";
	}

	private void prepararFormulario(Model model, Alumno alumno, String accion, String titulo) {
		model.addAttribute("alumno", alumno);
		model.addAttribute("listaGrupos", grupoService.listAll());
		model.addAttribute("accionFormulario", accion);
		model.addAttribute("tituloFormulario", titulo);
	}
}