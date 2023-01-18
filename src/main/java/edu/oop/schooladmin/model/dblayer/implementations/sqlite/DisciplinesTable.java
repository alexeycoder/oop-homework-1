package edu.oop.schooladmin.model.dblayer.implementations.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.stream.Stream;

import edu.oop.schooladmin.model.entities.Discipline;

public class DisciplinesTable extends SqliteTableBase<Discipline> {

	private final String insertSql = "INSERT INTO disciplines (name) VALUES (?);";
	private final String selectAllSql = "SELECT * FROM disciplines;";
	private final String selectByIdSql = "SELECT * FROM disciplines WHERE discipline_id=?;";
	private final String deleteSql = "DELETE FROM disciplines WHERE discipline_id=?;";
	private final String updateSql = """
				UPDATE disciplines
				SET
					name = ?
				WHERE discipline_id=?;
			""";

	protected DisciplinesTable(Connection connection) throws SQLException {
		super(connection);
	}

	private static void bakeEntityToInsertStatement(PreparedStatement ps, Discipline entity) throws SQLException {
		ps.setString(1, entity.getName());
	}

	private static void bakeEntityToUpdateStatement(PreparedStatement ps, Discipline entity) throws SQLException {
		bakeEntityToInsertStatement(ps, entity);
		ps.setInt(2, entity.getDisciplineId());
	}

	private static Discipline resultSetToEntity(ResultSet rs) throws SQLException {
		var discipline = new Discipline(
				rs.getInt("discipline_id"),
				rs.getString("name"));
		return discipline;
	}

	@Override
	public Discipline add(Discipline entry) {
		try (PreparedStatement ps = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
			bakeEntityToInsertStatement(ps, entry);
			int affectedRows = ps.executeUpdate();
			if (affectedRows == 0) {
				return null;
			}
			try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					entry.setDisciplineId(generatedKeys.getInt(1));
					return entry;
				} else {
					return null;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public Stream<Discipline> queryAll() {
		ArrayList<Discipline> list = new ArrayList<>();
		try (Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(selectAllSql)) {
			while (resultSet.next()) {
				var entity = resultSetToEntity(resultSet);
				list.add(entity);
			}
			return list.stream();
		} catch (Exception ex) {
			ex.printStackTrace();
			return Stream.empty();
		}
	}

	@Override
	public Discipline get(int id) {
		try (PreparedStatement ps = connection.prepareStatement(selectByIdSql)) {
			ps.setInt(1, id);

			try (ResultSet resultSet = ps.executeQuery()) {
				if (resultSet.next()) {
					var entity = resultSetToEntity(resultSet);
					return entity;
				}
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean update(Discipline entry) {
		try (PreparedStatement ps = connection.prepareStatement(updateSql)) {
			ps.setString(1, entry.getName());
			ps.setInt(2, entry.getDisciplineId());
			int affectedRows = ps.executeUpdate();
			if (affectedRows == 0) {
				return false;
			}
			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public Discipline delete(int id) {
		Discipline entry = get(id);
		if (entry == null) {
			return null;
		}
		try (PreparedStatement ps = connection.prepareStatement(deleteSql)) {
			bakeEntityToUpdateStatement(ps, entry);
			int affectedRows = ps.executeUpdate();
			if (affectedRows == 0) {
				return null;
			}
			return entry;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
