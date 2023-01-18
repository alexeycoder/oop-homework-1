package edu.oop.schooladmin.model.dblayer.implementations.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.oop.schooladmin.model.entities.Student;

public class StudentsTable extends SqliteTableBase<Student> {

	private static final Logger logger = LoggerFactory.getLogger(StudentsTable.class);

	private final String insertSql = """
			INSERT INTO students (first_name, last_name, birth_date, group_id)
			VALUES (?, ?, ?, ?);
			""";
	private final String selectAllSql = "SELECT * FROM students;";
	private final String selectByIdSql = "SELECT * FROM students WHERE student_id=?;";
	private final String deleteSql = "DELETE FROM students WHERE student_id=?;";
	private final String updateSql = """
			UPDATE students
			SET
				first_name = ?,
				last_name = ?,
				birth_date = ?,
				group_id = ?
			WHERE student_id=?;
			""";

	protected StudentsTable(Connection connection) throws SQLException {
		super(connection);
	}

	private static void bakeEntityToInsertStatement(PreparedStatement ps, Student entity) throws SQLException {
		ps.setString(1, entity.getFirstName());
		ps.setString(2, entity.getLastName());
		ps.setString(3, entity.getBirthDate().toString());
		Integer groupId = entity.getGroupId();
		if (groupId != null) {
			ps.setInt(4, groupId);
		} else {
			ps.setNull(4, Types.INTEGER);
		}
	}

	private static void bakeEntityToUpdateStatement(PreparedStatement ps, Student entity) throws SQLException {
		bakeEntityToInsertStatement(ps, entity);
		ps.setInt(5, entity.getStudentId());
	}

	private static Student resultSetToEntity(ResultSet rs) throws SQLException {
		var student = new Student(
				rs.getInt("student_id"),
				rs.getString("first_name"),
				rs.getString("last_name"),
				LocalDate.parse(rs.getString("birth_date")),
				null);
		int groupId = rs.getInt("group_id");
		if (!rs.wasNull()) {
			student.setGroupId(groupId);
		}
		return student;
	}

	@Override
	public Student add(Student entry) {
		try (PreparedStatement ps = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
			bakeEntityToInsertStatement(ps, entry);
			int affectedRows = ps.executeUpdate();
			if (affectedRows == 0) {
				return null;
			}
			try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					entry.setStudentId(generatedKeys.getInt(1));
					return entry;
				} else {
					return null;
				}
			}
		} catch (Exception ex) {
			logger.error("Error on adding new student.", ex);
			return null;
		}
	}

	@Override
	public Stream<Student> queryAll() {
		ArrayList<Student> list = new ArrayList<>();
		try (Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(selectAllSql)) {
			while (resultSet.next()) {
				var entity = resultSetToEntity(resultSet);
				list.add(entity);
			}
			return list.stream();
		} catch (Exception ex) {
			logger.error("Error on querying all students.", ex);
			return Stream.empty();
		}
	}

	@Override
	public Student get(int id) {
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
			logger.error("Error on getting student by id.", ex);
			return null;
		}
	}

	@Override
	public boolean update(Student entry) {
		try (PreparedStatement ps = connection.prepareStatement(updateSql)) {
			bakeEntityToUpdateStatement(ps, entry);
			int affectedRows = ps.executeUpdate();
			if (affectedRows == 0) {
				return false;
			}
			return true;

		} catch (Exception ex) {
			logger.error("Error on updating student.", ex);
			return false;
		}
	}

	@Override
	public Student delete(int id) {
		Student entry = get(id);
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
			logger.error("Error on deleting student.", ex);
			return null;
		}
	}
}
