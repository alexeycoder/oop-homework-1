package edu.oop.schooladmin.model.dblayer.implementations.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Stream;

import edu.oop.schooladmin.model.entities.Rating;

public class RatingsTable extends SqliteTableBase<Rating> {

	private final String insertSql = """
			INSERT INTO ratings (student_id, discipline_id, date_time, value, commentary)
			VALUES (?, ?, ?, ?, ?);
			""";
	private final String selectAllSql = "SELECT * FROM ratings;";
	private final String selectByIdSql = "SELECT * FROM ratings WHERE rating_id=?;";
	private final String deleteSql = "DELETE FROM ratings WHERE rating_id=?;";
	private final String updateSql = """
			UPDATE ratings
			SET
				student_id = ?,
				discipline_id = ?,
				date_time = ?,
				value = ?,
				commentary = ?
			WHERE rating_id=?;
			""";

	protected RatingsTable(Connection connection) throws SQLException {
		super(connection);
	}

	private static void bakeEntityToInsertStatement(PreparedStatement ps, Rating entity) throws SQLException {
		// (student_id, discipline_id, date_time, value, commentary)
		ps.setInt(1, entity.getStudentId());
		ps.setInt(2, entity.getDisciplineId());
		ps.setString(3, entity.getDateTime().toString());
		ps.setInt(4, entity.getValue());
		ps.setString(5, entity.getCommentary());
	}

	private static void bakeEntityToUpdateStatement(PreparedStatement ps, Rating entity) throws SQLException {
		bakeEntityToInsertStatement(ps, entity);
		ps.setInt(6, entity.getRatingId());
	}

	private static Rating resultSetToEntity(ResultSet rs) throws SQLException {
		var rating = new Rating(
				rs.getInt("rating_id"),
				rs.getInt("student_id"),
				rs.getInt("discipline_id"),
				LocalDateTime.parse(rs.getString("date_time")),
				rs.getInt("value"),
				rs.getString("commentary"));
		return rating;
	}

	@Override
	public Rating add(Rating entry) {
		try (PreparedStatement ps = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
			bakeEntityToInsertStatement(ps, entry);
			int affectedRows = ps.executeUpdate();
			if (affectedRows == 0) {
				return null;
			}
			try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					entry.setRatingId(generatedKeys.getInt(1));
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
	public Stream<Rating> queryAll() {
		ArrayList<Rating> list = new ArrayList<>();
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
	public Rating get(int id) {
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
	public boolean update(Rating entry) {
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
	public Rating delete(int id) {
		Rating entry = get(id);
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
