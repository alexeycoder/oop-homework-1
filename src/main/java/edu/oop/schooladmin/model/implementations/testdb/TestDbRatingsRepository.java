package edu.oop.schooladmin.model.implementations.testdb;

import java.time.LocalDateTime;
import java.util.List;

import edu.oop.schooladmin.model.entities.Discipline;
import edu.oop.schooladmin.model.entities.Rating;
import edu.oop.schooladmin.model.entities.Student;
import edu.oop.schooladmin.model.interfaces.RatingsRepository;
import edu.oop.schooladmin.testdatatables.DisciplinesTable;
import edu.oop.schooladmin.testdatatables.Queryable;
import edu.oop.schooladmin.testdatatables.RatingsTable;
import edu.oop.schooladmin.testdatatables.StudentsTable;
import edu.oop.utils.DateTimeUtils;

public class TestDbRatingsRepository implements RatingsRepository {

    private final Queryable<Rating> ratings;
    private final Queryable<Student> students;
    private final Queryable<Discipline> disciplines;

    public TestDbRatingsRepository() {
        ratings = RatingsTable.instance();
        students = StudentsTable.instance();
        disciplines = DisciplinesTable.instance();
    }

    /**
     * Служет для удостоверения, что операция добавления/обновления указанной
     * записи не нарушит *целостность БД*.
     * 
     * @param rating Экземпляр добавляемой/обновляемой записи.
     * @return Возвращает true, если операция допустима для переданных данных.
     */
    private boolean checkUpdateValidity(Rating rating) {
        // Ratings:
        // Ссылка на несуществующего Student не допускается.
        // Ссылка на несуществующий Discipline не допускается.
        var studentId = rating.getStudentId();
        if (studentId == null || students.get(studentId) == null) {
            return false;
        }
        var disciplineId = rating.getDisciplineId();
        if (disciplineId == null || disciplines.get(disciplineId) == null) {
            return false;
        }
        return true;
    }

    /**
     * Служет для удостоверения, что операция удаления указанной
     * записи не нарушит *целостность БД*.
     * 
     * @param rating Запись для удаления.
     * @return Возвращает true, если удаление указанной записи не нарушит
     *         целостность БД.
     */
    private boolean checkRemoveValidity(Rating rating) {
        // Удаление записи Rating разрешается всегда.
        return true;
    }

    @Override
    public Rating addRating(Rating rating) {
        if (rating == null) {
            throw new NullPointerException("rating");
        }
        if (checkUpdateValidity(rating)) {
            return ratings.add(rating);
        }
        return null;
    }

    @Override
    public Rating getRatingById(int ratingId) {
        return ratings.get(ratingId);
    }

    @Override
    public List<Rating> getAllRatings() {
        return ratings.queryAll().toList();
    }

    @Override
    public List<Rating> getRatingsByStudentId(int studentId) {
        return ratings.queryAll()
                .filter(r -> r.getStudentId() != null && r.getStudentId().equals(studentId))
                .toList();
    }

    @Override
    public List<Rating> getRatingsByDisciplineId(int disciplineId) {
        return ratings.queryAll()
                .filter(r -> r.getDisciplineId() != null && r.getDisciplineId().equals(disciplineId))
                .toList();
    }

    @Override
    public List<Rating> getRatingsByDateTime(LocalDateTime from, LocalDateTime to) {
        return ratings.queryAll()
                .filter(r -> DateTimeUtils.isInRange(r.getDateTime(), from, to))
                .toList();
    }

    @Override
    public List<Rating> getRatingsByValue(int from, int to) {
        return ratings.queryAll()
                .filter(r -> r.getValue() >= from && r.getValue() <= to)
                .toList();
    }

    @Override
    public boolean updateRating(Rating rating) {
        if (rating == null) {
            throw new NullPointerException("rating");
        }
        if (checkUpdateValidity(rating)) {
            return ratings.update(rating);
        }
        return false;
    }

    @Override
    public boolean removeRating(int ratingId) {
        Rating dbEntryToRemove = ratings.get(ratingId);
        if (dbEntryToRemove == null) {
            return false;
        }
        if (checkRemoveValidity(dbEntryToRemove)) {
            return ratings.delete(ratingId) != null;
        }
        return false;
    }
}
