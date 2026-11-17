package com.iesvdm.gestionacademica.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.iesvdm.gestionacademica.modelo.Profesor;

@Repository
public class ProfesorDAOImpl implements ProfesorDAO {
	// Alternativa (inyección en campo):
	// @Autowired
	// private JdbcTemplate jdbcTemplate;
	private final JdbcTemplate jdbcTemplate;

	public ProfesorDAOImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Profesor> findAll() {
		return jdbcTemplate.query("SELECT * FROM profesor ORDER BY id", (rs, rowNum) -> new Profesor(
				rs.getInt("id"),
				rs.getString("nif"),
				rs.getString("nombre"),
				rs.getString("apellido1"),
				rs.getString("apellido2"),
				rs.getString("departamento"),
				rs.getString("email")));
	}

	@Override
	public Optional<Profesor> findById(int id) {
		return jdbcTemplate.query("SELECT * FROM profesor WHERE id = ?",
				(rs, rowNum) -> new Profesor(
						rs.getInt("id"),
						rs.getString("nif"),
						rs.getString("nombre"),
						rs.getString("apellido1"),
						rs.getString("apellido2"),
						rs.getString("departamento"),
						rs.getString("email")),
				id).stream().findFirst();
	}

	@Override
	public void save(Profesor profesor) {
		String sql = "INSERT INTO profesor (nif, nombre, apellido1, apellido2, departamento, email) VALUES (?, ?, ?, ?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, profesor.getNif());
			statement.setString(2, profesor.getNombre());
			statement.setString(3, profesor.getApellido1());
			statement.setString(4, profesor.getApellido2());
			statement.setString(5, profesor.getDepartamento());
			statement.setString(6, profesor.getEmail());
			return statement;
		}, keyHolder);
		profesor.setId(keyHolder.getKey().intValue());
	}

	@Override
	public void update(Profesor profesor) {
		jdbcTemplate.update("UPDATE profesor SET nif = ?, nombre = ?, apellido1 = ?, apellido2 = ?, departamento = ?, email = ? WHERE id = ?",
				profesor.getNif(), profesor.getNombre(), profesor.getApellido1(), profesor.getApellido2(),
				profesor.getDepartamento(), profesor.getEmail(), profesor.getId());
	}

	@Override
	public void delete(int id) {
		jdbcTemplate.update("DELETE FROM profesor WHERE id = ?", id);
	}
}