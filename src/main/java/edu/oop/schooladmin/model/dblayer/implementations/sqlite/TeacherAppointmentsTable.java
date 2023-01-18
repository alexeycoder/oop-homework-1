package edu.oop.schooladmin.model.dblayer.implementations.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.stream.Stream;

import edu.oop.schooladmin.model.entities.TeacherAppointment;

public class TeacherAppointmentsTable extends SqliteTableBase<TeacherAppointment> {

	private final String insertSql = """
			INSERT INTO teacher_appointments (teacher_id, discipline_id, group_id)
			VALUES (?, ?, ?);
			""";
	private final String selectAllSql = "SELECT * FROM teacher_appointments;";
	private final String selectByIdSql = "SELECT * FROM teacher_appointments WHERE appointment_id=?;";
	private final String deleteSql = "DELETE FROM teacher_appointments WHERE appointment_id=?;";
	private final String updateSql = """
			UPDATE teacher_appointments
			SET
				teacher_id = ?,
				discipline_id = ?,
				group_id = ?
			WHERE appointment_id=?;
			""";

	protected TeacherAppointmentsTable(Connection connection) throws SQLException {
		super(connection);
	}

	private static void bakeEntityToInsertStatement(PreparedStatement ps, TeacherAppointment entity) throws SQLException {
		ps.setInt(1, entity.getTeacherId());
		ps.setInt(2, entity.getDisciplineId());

		Integer groupId = entity.getGroupId();
		if (groupId != null) {
			ps.setInt(3, groupId);
		} else {
			ps.setNull(3, Types.INTEGER);
		}
	}

	private static void bakeEntityToUpdateStatement(PreparedStatement ps, TeacherAppointment entity) throws SQLException {
		bakeEntityToInsertStatement(ps, entity);
		ps.setInt(4, entity.getAppointmentId());
	}

	private static TeacherAppointment resultSetToEntity(ResultSet rs) throws SQLException {
		var teacherAppointment = new TeacherAppointment(
				rs.getInt("appointment_id"),
				rs.getInt("teacher_id"),
				rs.getInt("discipline_id"),
				null);
		int groupId = rs.getInt("group_id");
		if (!rs.wasNull()) {
			teacherAppointment.setGroupId(groupId);
		}
		return teacherAppointment;
	}

	@Override
	public TeacherAppointment add(TeacherAppointment entry) {
		try (PreparedStatement ps = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
			bakeEntityToInsertStatement(ps, entry);
			int affectedRows = ps.executeUpdate();
			if (affectedRows == 0) {
				return null;
			}
			try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					entry.setAppointmentId(generatedKeys.getInt(1));
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
	public Stream<TeacherAppointment> queryAll() {
		ArrayList<TeacherAppointment> list = new ArrayList<>();
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
	public TeacherAppointment get(int id) {
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
	public boolean update(TeacherAppointment entry) {
		try (PreparedStatement ps = connection.prepareStatement(updateSql)) {
			bakeEntityToUpdateStatement(ps, entry);
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
	public TeacherAppointment delete(int id) {
		TeacherAppointment entry = get(id);
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
