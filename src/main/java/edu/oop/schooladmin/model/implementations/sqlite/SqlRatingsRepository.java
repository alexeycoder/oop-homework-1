package edu.oop.schooladmin.model.implementations.sqlite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import edu.oop.schooladmin.model.entities.Rating;
import edu.oop.schooladmin.model.interfaces.RatingsRepository;

public class SqlRatingsRepository implements RatingsRepository {

    @Override
    public Rating addRating(Rating rating) {
        RepositoryUtils utils = new RepositoryUtils();
        String sql = "INSERT INTO Ratings( 'studentId', 'disciplineId', 'dateTime', 'value', 'commentary') " +
                "VALUES( ?, ?, ?, ?, ?);";
        List<Object> parametersList = new ArrayList<>();
        parametersList.add(rating.getStudentId());
        parametersList.add(rating.getDisciplineId());
        parametersList.add(rating.getDateTime().toString());
        parametersList.add(rating.getValue());
        parametersList.add(rating.getCommentary());

        utils.setTable(sql, parametersList);
        return rating;
    }

    // "CREATE TABLE Ratings(ratingId INTEGER PRIMARY KEY, studentId int NOT NULL,
    // disciplineId int NOT NULL, dateTime text, " +
    // "value int NOT NULL, commentary text);";
    @Override
    public Rating getRatingById(int ratingId) {
        RepositoryUtils utils = new RepositoryUtils();
        String sql = "SELECT ratingId, studentId, disciplineId, dateTime, value, commentary FROM Ratings WHERE ratingId=? ";
        ArrayList<Object> parametersList = new ArrayList<>();
        parametersList.add(ratingId);
        try {
            ResultSet resultSet = utils.getFromTable(sql, parametersList);
            if (resultSet.next()) {
                Rating rating = new Rating((Integer) (resultSet.getInt("ratingId")),
                        (Integer) resultSet.getInt("studentId"),
                        (Integer) resultSet.getInt("disciplineId"),
                        LocalDateTime.parse(resultSet.getString("dateTime")),
                        (resultSet.getInt("value")),
                        (resultSet.getString("commentary")));
                resultSet.close();
                return rating;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Rating> getAllRatings() {
        String sql = "SELECT ratingId, studentId, disciplineId, dateTime, value, commentary FROM Ratings";
        return getRatingsDataList(sql);
    }

    @Override
    public List<Rating> getRatingsByStudentId(int studentId) {
        String sql = "SELECT ratingId, studentId, disciplineId, dateTime, value, commentary FROM Ratings WHERE studentId=?";
        List<Object> parametersList = new ArrayList<>();
        parametersList.add(studentId);
        return getRatingsDataList(sql, parametersList);
    }

    @Override
    public List<Rating> getRatingsByDisciplineId(int disciplineId) {
        String sql = "SELECT ratingId, studentId, disciplineId, dateTime, value, commentary FROM Ratings WHERE disciplineId=?";
        List<Object> parametersList = new ArrayList<>();
        parametersList.add(disciplineId);
        return getRatingsDataList(sql, parametersList);
    }

    @Override
    public List<Rating> getRatingsByDateTime(LocalDateTime from, LocalDateTime to) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Rating> getRatingsByDateTime(LocalDateTime dateTime) {
        String sql = "SELECT ratingId, studentId, disciplineId, dateTime, value, commentary FROM Ratings WHERE dateTime=?";
        List<Object> parametersList = new ArrayList<>();
        parametersList.add(dateTime.toString());
        return getRatingsDataList(sql, parametersList);
    }

    @Override
    public List<Rating> getRatingsByValue(int from, int to) {
        // Изменил с getRatingsByValue(int value) на getRatingsByValue(int from, int to)
        // надо проверить соответсвие запроса:
        String sql = "SELECT ratingId, studentId, disciplineId, dateTime, value, commentary FROM Ratings WHERE value>=? AND value<=?";
        List<Object> parametersList = new ArrayList<>();
        parametersList.add(from);
        parametersList.add(to);
        return getRatingsDataList(sql, parametersList);
    }

    @Override
    public boolean updateRating(Rating rating) {
        RepositoryUtils utils = new RepositoryUtils();
        String sql = "UPDATE Ratings SET studentId=?, disciplineId=?, dateTime=?, value=?, commentary=? WHERE ratingId=?";
        if (getRatingById(rating.getRatingId()) != null) {
            List<Object> parametersList = new ArrayList<>();
            parametersList.add(rating.getStudentId());
            parametersList.add(rating.getDisciplineId());
            parametersList.add(rating.getDateTime().toString());
            parametersList.add(rating.getValue());
            parametersList.add(rating.getCommentary());
            parametersList.add(rating.getRatingId());
            utils.setTable(sql, parametersList);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeRating(int ratingId) {
        RepositoryUtils utils = new RepositoryUtils();
        String sql = "DELETE FROM Ratings WHERE ratingId=?";
        if (getRatingById(ratingId) != null) {
            List<Object> parametersList = new ArrayList<>();
            parametersList.add(ratingId);
            utils.setTable(sql, parametersList);
            return true;
        }
        return false;
    }

    private List<Rating> getRatingsDataList(String sql, List<Object> parametersList) {
        RepositoryUtils utils = new RepositoryUtils();
        try {
            ResultSet resultSet = utils.getFromTable(sql, parametersList);
            List<Rating> ratings = new ArrayList<>();
            while (resultSet.next()) {
                ratings.add(new Rating((Integer) (resultSet.getInt("ratingId")),
                        (Integer) resultSet.getInt("studentId"),
                        (Integer) resultSet.getInt("disciplineId"),
                        LocalDateTime.parse(resultSet.getString("dateTime")),
                        (resultSet.getInt("value")),
                        (resultSet.getString("commentary"))));
            }
            resultSet.close();
            return ratings;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Rating> getRatingsDataList(String sql) {
        RepositoryUtils utils = new RepositoryUtils();
        try {
            ResultSet resultSet = utils.getFromTable(sql);
            List<Rating> ratings = new ArrayList<>();
            while (resultSet.next()) {
                ratings.add(new Rating((Integer) (resultSet.getInt("ratingId")),
                        (Integer) resultSet.getInt("studentId"),
                        (Integer) resultSet.getInt("disciplineId"),
                        LocalDateTime.parse(resultSet.getString("dateTime")),
                        (resultSet.getInt("value")),
                        (resultSet.getString("commentary"))));
            }
            resultSet.close();
            return ratings;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
