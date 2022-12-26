package edu.oop.schooladmin.model.dblayer.implementations.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.stream.Stream;

import edu.oop.schooladmin.model.entities.Group;

public class GroupsTable extends SqliteTableBase<Group> {

	private final String insertSql = """
			INSERT INTO groups (class_year, class_mark, teacher_id)
			VALUES (?, ?, ?);
			""";
	private final String selectAllSql = "SELECT * FROM groups;";
	private final String selectByIdSql = "SELECT * FROM groups WHERE group_id=?;";
	private final String deleteSql = "DELETE FROM groups WHERE group_id=?;";
	private final String updateSql = """
				UPDATE groups
				SET
					class_year = ?,
					class_mark = ?,
					teacher_id = ?
				WHERE group_id=?;
			""";

	protected GroupsTable(Connection connection) throws SQLException {
		super(connection);
	}

	private static void bakeEntityToStatement(PreparedStatement ps, Group entity) throws SQLException {
		ps.setInt(1, entity.getClassYear());
		ps.setString(2, Character.toString(entity.getClassMark()));
		ps.setInt(3, entity.getTeacherId());
	}

	private static Group resultSetToEntity(ResultSet rs) throws SQLException {
		var group = new Group(
				rs.getInt("group_id"),
				rs.getInt("class_year"),
				rs.getString("class_mark").charAt(0),
				rs.getInt("teacher_id"));
		return group;
	}

	@Override
	public Group add(Group entry) {
		try (PreparedStatement ps = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
			bakeEntityToStatement(ps, entry);
			int affectedRows = ps.executeUpdate();
			if (affectedRows == 0) {
				return null;
			}
			try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					entry.setGroupId(generatedKeys.getInt(1));
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
	public Stream<Group> queryAll() {
		ArrayList<Group> list = new ArrayList<>();
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
	public Group get(int id) {
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
	public boolean update(Group entry) {
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
	public Group delete(int id) {
		Group entry = get(id);
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
