package edu.oop.schooladmin.model.dblayer.implementations.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Stream;

import edu.oop.schooladmin.model.entities.Teacher;

public class TeachersTable extends SqliteTableBase<Teacher> {

	private final String insertSql = """
			INSERT INTO teachers (first_name, last_name, birth_date, grade)
			VALUES (?, ?, ?, ?);
			""";
	private final String selectAllSql = "SELECT * FROM teachers;";
	private final String selectByIdSql = "SELECT * FROM teachers WHERE teacher_id=?;";
	private final String deleteSql = "DELETE FROM teachers WHERE teacher_id=?;";
	private final String updateSql = """
			UPDATE teachers
			SET
				first_name = ?,
				last_name = ?,
				birth_date = ?,
				grade = ?
			WHERE teacher_id=?;
			""";

	protected TeachersTable(Connection connection) throws SQLException {
		super(connection);
	}

	private static void bakeEntityToStatement(PreparedStatement ps, Teacher entity) throws SQLException {
		ps.setString(1, entity.getFirstName());
		ps.setString(2, entity.getLastName());
		ps.setString(3, entity.getBirthDate().toString());
		ps.setInt(4, entity.getGrade());
	}

	private static Teacher resultSetToEntity(ResultSet rs) throws SQLException {
		var teacher = new Teacher(
				rs.getInt("teacher_id"),
				rs.getString("first_name"),
				rs.getString("last_name"),
				LocalDate.parse(rs.getString("birth_date")),
				rs.getInt("grade"));
		return teacher;
	}

	@Override
	public Teacher add(Teacher entry) {
		try (PreparedStatement ps = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
			bakeEntityToStatement(ps, entry);
			int affectedRows = ps.executeUpdate();
			if (affectedRows == 0) {
				return null;
			}
			try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					entry.setTeacherId(generatedKeys.getInt(1));
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
	public Stream<Teacher> queryAll() {
		ArrayList<Teacher> list = new ArrayList<>();
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
	public Teacher get(int id) {
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
	public boolean update(Teacher entry) {
		try (PreparedStatement ps = connection.prepareStatement(updateSql)) {
			bakeEntityToStatement(ps, entry);
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
	public Teacher delete(int id) {
		Teacher entry = get(id);
		if (entry == null) {
			return null;
		}
		try (PreparedStatement ps = connection.prepareStatement(deleteSql)) {
			ps.setInt(1, id);
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
