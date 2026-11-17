package com.iesvdm.gestionacademica.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.iesvdm.gestionacademica.modelo.Alumno;

@Repository
public class AlumnoDAOImpl implements AlumnoDAO {
	// Alternativa (inyección en campo):
	// @Autowired
	// private JdbcTemplate jdbcTemplate;
	private final JdbcTemplate jdbcTemplate;

	public AlumnoDAOImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Alumno> findAll() {
		return jdbcTemplate.query("SELECT * FROM alumno ORDER BY id", (rs, rowNum) -> new Alumno(
				rs.getInt("id"),
				rs.getString("nia"),
				rs.getString("nombre"),
				rs.getString("apellido1"),
				rs.getString("apellido2"),
				rs.getString("email"),
				rs.getObject("grupo_id", Integer.class)));
	}

	@Override
	public Optional<Alumno> findById(int id) {
		return jdbcTemplate.query("SELECT * FROM alumno WHERE id = ?",
				(rs, rowNum) -> new Alumno(
						rs.getInt("id"),
						rs.getString("nia"),
						rs.getString("nombre"),
						rs.getString("apellido1"),
						rs.getString("apellido2"),
						rs.getString("email"),
						rs.getObject("grupo_id", Integer.class)),
				id).stream().findFirst();
	}

	@Override
	public void save(Alumno alumno) {
		String sql = "INSERT INTO alumno (nia, nombre, apellido1, apellido2, email, grupo_id) VALUES (?, ?, ?, ?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, alumno.getNia());
			statement.setString(2, alumno.getNombre());
			statement.setString(3, alumno.getApellido1());
			statement.setString(4, alumno.getApellido2());
			statement.setString(5, alumno.getEmail());
			statement.setObject(6, alumno.getGrupoId());
			return statement;
		}, keyHolder);
		alumno.setId(keyHolder.getKey().intValue());
	}

	@Override
	public void update(Alumno alumno) {
		jdbcTemplate.update("UPDATE alumno SET nia = ?, nombre = ?, apellido1 = ?, apellido2 = ?, email = ?, grupo_id = ? WHERE id = ?",
				alumno.getNia(), alumno.getNombre(), alumno.getApellido1(), alumno.getApellido2(),
				alumno.getEmail(), alumno.getGrupoId(), alumno.getId());
	}

	@Override
	public void delete(int id) {
		jdbcTemplate.update("DELETE FROM alumno WHERE id = ?", id);
	}

	@Override
	public List<Alumno> findAlumnosByGrupoId(int grupoId) {
		return jdbcTemplate.query("SELECT * FROM alumno WHERE grupo_id = ? ORDER BY id", (rs, rowNum) -> new Alumno(
				rs.getInt("id"),
				rs.getString("nia"),
				rs.getString("nombre"),
				rs.getString("apellido1"),
				rs.getString("apellido2"),
				rs.getString("email"),
				rs.getObject("grupo_id", Integer.class)), grupoId);
	}
}