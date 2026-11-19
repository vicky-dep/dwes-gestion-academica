package com.iesvdm.gestionacademica.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.iesvdm.gestionacademica.modelo.TutoriaDetalleDTO;
import com.iesvdm.gestionacademica.modelo.TutoriaTFG;

@Repository
public class TutoriaTFGDAOImpl implements TutoriaTFGDAO {
	private static final String SELECT_DETALLES = """
			SELECT t.profesor_id AS profesorId,
			       CONCAT_WS(' ', p.nombre, p.apellido1, p.apellido2) AS nombreCompletoProfesor,
			       t.alumno_id AS alumnoId,
			       CONCAT_WS(' ', a.nombre, a.apellido1, a.apellido2) AS nombreCompletoAlumno,
			       g.codigo AS codigoGrupo,
			       t.nota,
			       t.observaciones
			FROM tutoria_tfg t
			JOIN profesor p ON p.id = t.profesor_id
			JOIN alumno a ON a.id = t.alumno_id
			LEFT JOIN grupo g ON g.id = a.grupo_id
			""";

	private static final RowMapper<TutoriaDetalleDTO> TUTORIA_DETALLE_MAPPER = (rs, rowNum) -> new TutoriaDetalleDTO(
			rs.getInt("profesorId"),
			rs.getString("nombreCompletoProfesor"),
			rs.getInt("alumnoId"),
			rs.getString("nombreCompletoAlumno"),
			rs.getString("codigoGrupo"),
			rs.getBigDecimal("nota"),
			rs.getString("observaciones"));

	private final JdbcTemplate jdbcTemplate;

	public TutoriaTFGDAOImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<TutoriaDetalleDTO> findByAlumnoId(int alumnoId) {
		return jdbcTemplate.query(SELECT_DETALLES + "WHERE t.alumno_id = ? ORDER BY p.apellido1, p.nombre", TUTORIA_DETALLE_MAPPER, alumnoId);
	}

	@Override
	public List<TutoriaDetalleDTO> findByProfesorId(int profesorId) {
		return jdbcTemplate.query(SELECT_DETALLES + "WHERE t.profesor_id = ? ORDER BY a.apellido1, a.nombre", TUTORIA_DETALLE_MAPPER, profesorId);
	}

	@Override
	public void create(TutoriaTFG tutoriaTFG) {
		jdbcTemplate.update("""
				INSERT INTO tutoria_tfg (profesor_id, alumno_id, nota, observaciones)
				VALUES (?, ?, ?, ?)
				""",
				tutoriaTFG.getProfesorId(),
				tutoriaTFG.getAlumnoId(),
				tutoriaTFG.getNota(),
				tutoriaTFG.getObservaciones());
	}

	@Override
	public void update(TutoriaTFG tutoriaTFG) {
		jdbcTemplate.update("""
				UPDATE tutoria_tfg
				SET nota = ?, observaciones = ?
				WHERE profesor_id = ? AND alumno_id = ?
				""",
				tutoriaTFG.getNota(),
				tutoriaTFG.getObservaciones(),
				tutoriaTFG.getProfesorId(),
				tutoriaTFG.getAlumnoId());
	}

	@Override
	public void delete(int profesorId, int alumnoId) {
		jdbcTemplate.update("""
				DELETE FROM tutoria_tfg
				WHERE profesor_id = ? AND alumno_id = ?
				""",
				profesorId,
				alumnoId);
	}
}