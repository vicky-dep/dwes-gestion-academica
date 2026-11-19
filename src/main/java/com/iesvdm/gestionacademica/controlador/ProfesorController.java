package com.iesvdm.gestionacademica.controlador;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.iesvdm.gestionacademica.modelo.AlumnoGrupoDTO;
import com.iesvdm.gestionacademica.modelo.EstadisticasTFGDTO;
import com.iesvdm.gestionacademica.modelo.Grupo;
import com.iesvdm.gestionacademica.modelo.Profesor;
import com.iesvdm.gestionacademica.modelo.ResumenGrupoDTO;
import com.iesvdm.gestionacademica.modelo.TutoriaDetalleDTO;
import com.iesvdm.gestionacademica.modelo.TutoriaTFG;
import com.iesvdm.gestionacademica.servicios.AlumnoService;
import com.iesvdm.gestionacademica.servicios.GrupoService;
import com.iesvdm.gestionacademica.servicios.ProfesorService;
import com.iesvdm.gestionacademica.servicios.TutoriaTFGService;

@Controller
public class ProfesorController {
	private final ProfesorService profesorService;
	private final GrupoService grupoService;
	private final AlumnoService alumnoService;
	private final TutoriaTFGService tutoriaTFGService;

	public ProfesorController(ProfesorService profesorService, GrupoService grupoService, AlumnoService alumnoService,
			TutoriaTFGService tutoriaTFGService) {
		this.profesorService = profesorService;
		this.grupoService = grupoService;
		this.alumnoService = alumnoService;
		this.tutoriaTFGService = tutoriaTFGService;
	}

	@GetMapping("/profesores")
	public String listar(Model model) {
		model.addAttribute("listaProfesores", profesorService.listAll());
		return "profesores";
	}

	@GetMapping("/profesores/{id}")
	public String detalle(@PathVariable int id, Model model) {
		Optional<Profesor> profesorOpt = profesorService.find(id);
		if (profesorOpt.isEmpty()) {
			return "redirect:/profesores";
		}
		List<TutoriaDetalleDTO> tutoriasTFG = tutoriaTFGService.findByProfesorId(id);
		DoubleSummaryStatistics notasTFG = tutoriasTFG.stream()
				.map(TutoriaDetalleDTO::nota)
				.filter(nota -> nota != null)
				.mapToDouble(BigDecimal::doubleValue)
				.summaryStatistics();
		long totalEvaluados = notasTFG.getCount();
		EstadisticasTFGDTO estadisticasTFG = new EstadisticasTFGDTO(
				notasTFG.getAverage(),
				totalEvaluados == 0 ? 0.0 : notasTFG.getMax(),
				totalEvaluados == 0 ? 0.0 : notasTFG.getMin(),
				totalEvaluados);
		List<Grupo> grupos = grupoService.findGruposByTutorId(id);
		List<AlumnoGrupoDTO> alumnos = grupos.stream()
				.flatMap(grupo -> alumnoService.findAlumnosByGrupoId(grupo.getId()).stream()
						.map(alumno -> new AlumnoGrupoDTO(
								alumno.getId(),
								alumno.getNia(),
								alumno.getNombre(),
								alumno.getApellido1(),
								alumno.getApellido2(),
								alumno.getEmail(),
								grupo.getId(),
								grupo.getCodigo(),
								grupo.getNombre())))
				.sorted(Comparator.comparing(AlumnoGrupoDTO::apellido1,
							Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER))
						.thenComparing(AlumnoGrupoDTO::apellido2,
								Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER))
						.thenComparing(AlumnoGrupoDTO::nombre,
								Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER)))
				.toList();
		Map<Integer, Long> alumnosPorGrupo = alumnos.stream()
				.collect(Collectors.groupingBy(AlumnoGrupoDTO::grupoId, Collectors.counting()));
		List<ResumenGrupoDTO> resumenGrupos = grupos.stream()
				.map(grupo -> new ResumenGrupoDTO(grupo.getCodigo(), alumnosPorGrupo.getOrDefault(grupo.getId(), 0L)))
				.toList();

		model.addAttribute("profesor", profesorOpt.get());
		model.addAttribute("grupos", grupos);
		model.addAttribute("alumnos", alumnos);
		model.addAttribute("resumenGrupos", resumenGrupos);
		model.addAttribute("tutoriasTFG", tutoriasTFG);
		model.addAttribute("estadisticasTFG", estadisticasTFG);
		model.addAttribute("listaAlumnos", alumnoService.listAll());
		return "detalle-profesor";
	}

	@PostMapping("/profesores/{id}/tutorias")
	public String guardarTutoria(@PathVariable int id, @RequestParam int alumnoId,
			@RequestParam(required = false) BigDecimal nota,
			@RequestParam(required = false) String observaciones) {
		if (profesorService.find(id).isEmpty() || alumnoService.find(alumnoId).isEmpty()) {
			return "redirect:/profesores";
		}

		TutoriaTFG tutoriaTFG = new TutoriaTFG(id, alumnoId, nota, observaciones);
		boolean yaAsignada = tutoriaTFGService.findByProfesorId(id).stream()
				.anyMatch(tutoria -> tutoria.alumnoId() == alumnoId);
		if (yaAsignada) {
			tutoriaTFGService.update(tutoriaTFG);
		} else {
			tutoriaTFGService.create(tutoriaTFG);
		}
		return "redirect:/profesores/" + id;
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