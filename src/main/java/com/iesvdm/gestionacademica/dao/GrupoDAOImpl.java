package com.iesvdm.gestionacademica.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.iesvdm.gestionacademica.modelo.Grupo;

@Repository
public class GrupoDAOImpl implements GrupoDAO {
	// Alternativa (inyección en campo):
	// @Autowired
	// private JdbcTemplate jdbcTemplate;
	private final JdbcTemplate jdbcTemplate;

	public GrupoDAOImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Grupo> findAll() {
		return jdbcTemplate.query("SELECT * FROM grupo ORDER BY id", (rs, rowNum) -> new Grupo(
				rs.getInt("id"),
				rs.getString("codigo"),
				rs.getString("nombre"),
				rs.getInt("curso"),
				rs.getObject("tutor_id", Integer.class)));
	}

	@Override
	public Optional<Grupo> findById(int id) {
		return jdbcTemplate.query("SELECT * FROM grupo WHERE id = ?",
				(rs, rowNum) -> new Grupo(
						rs.getInt("id"),
						rs.getString("codigo"),
						rs.getString("nombre"),
						rs.getInt("curso"),
						rs.getObject("tutor_id", Integer.class)),
				id).stream().findFirst();
	}

	@Override
	public void save(Grupo grupo) {
		String sql = "INSERT INTO grupo (codigo, nombre, curso, tutor_id) VALUES (?, ?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, grupo.getCodigo());
			statement.setString(2, grupo.getNombre());
			statement.setInt(3, grupo.getCurso());
			statement.setObject(4, grupo.getTutorId());
			return statement;
		}, keyHolder);
		grupo.setId(keyHolder.getKey().intValue());
	}

	@Override
	public void update(Grupo grupo) {
		jdbcTemplate.update("UPDATE grupo SET codigo = ?, nombre = ?, curso = ?, tutor_id = ? WHERE id = ?",
				grupo.getCodigo(), grupo.getNombre(), grupo.getCurso(), grupo.getTutorId(), grupo.getId());
	}

	@Override
	public void delete(int id) {
		jdbcTemplate.update("DELETE FROM grupo WHERE id = ?", id);
	}

	@Override
	public List<Grupo> findGruposByTutorId(int profesorId) {
		return jdbcTemplate.query("SELECT * FROM grupo WHERE tutor_id = ? ORDER BY id", (rs, rowNum) -> new Grupo(
				rs.getInt("id"),
				rs.getString("codigo"),
				rs.getString("nombre"),
				rs.getInt("curso"),
				rs.getObject("tutor_id", Integer.class)), profesorId);
	}
}