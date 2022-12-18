package edu.oop.schooladmin.model.implementations.testdb;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.List;

import edu.oop.schooladmin.model.entities.Group;
import edu.oop.schooladmin.model.entities.Rating;
import edu.oop.schooladmin.model.entities.Student;
import edu.oop.schooladmin.model.interfaces.StudentsRepository;
import edu.oop.schooladmin.testdatatables.GroupsTable;
import edu.oop.schooladmin.testdatatables.Queryable;
import edu.oop.schooladmin.testdatatables.RatingsTable;
import edu.oop.schooladmin.testdatatables.StudentsTable;
import edu.oop.utils.DateTimeUtils;
import edu.oop.utils.StringUtils;

public class TestDbStudentsRepository implements StudentsRepository {

    private final Queryable<Student> students;
    private final Queryable<Group> groups;
    private final Queryable<Rating> ratings;

    public TestDbStudentsRepository() {
        students = StudentsTable.instance();
        groups = GroupsTable.instance();
        ratings = RatingsTable.instance();
    }

    /**
     * Служет для удостоверения, что операция добавления/обновления указанной
     * записи не нарушит *целостность БД*.
     * 
     * @param student Экземпляр добавляемой/обновляемой записи.
     * @return Возвращает true, если операция допустима для переданных данных.
     */
    private boolean checkUpdateValidity(Student student) {
        // Students:
        // Допускается либо отсутствие ссылки на Группу, либо ссылка на существующую
        // группу.
        // Не-null Ссылка на несуществующую Группу не допускается.
        var groupId = student.getGroupId();
        if (groupId != null && groups.get(groupId) == null) {
            return false;
        }
        return true;
    }

    /**
     * Служет для удостоверения, что операция удаления указанной
     * записи не нарушит *целостность БД*.
     * 
     * @param student Запись для удаления.
     * @return Возвращает true, если удаление указанной записи не нарушит
     *         целостность БД.
     */
    private boolean checkRemoveValidity(Student student) {
        // Students: не зависит от других таблиц - всегда можно удалять.
        // Однако при удалении необходимо: Удалить все связанные Rating
        return true;
    }

    private void deleteRelatedRatings(int studentId) {
        var ratingsSelect = ratings.queryAll()
                .filter(r -> r.getStudentId() != null && r.getStudentId().equals(studentId));
        for (var rating : ratingsSelect.toList()) {
            ratings.delete(rating.getRatingId());
        }
    }

    @Override
    public Student addStudent(Student student) {
        basicCheck(student);

        if (checkUpdateValidity(student)) {
            return students.add(student);
        }
        return null;
    }

    @Override
    public List<Student> getAllStudents() {
        return students.queryAll().toList();
    }

    // Меняем в репозиторных методах параметры идентификатора на примитивный int,
    // чтобы не заботится о null лишний раз. См. комменты в docs
    @Override
    public Student getStudentById(int studentId) {
        return students.get(studentId);
    }

    @Override
    public List<Student> getStudentsByName(String nameSample) {
        if (nameSample == null) {
            return List.of();
        }
        if (nameSample.isBlank()) {
            return List.of();
        }

        String regex = StringUtils.getRegexContainsAll(nameSample);
        return students.queryAll()
                .filter(s -> (s.getFirstName() + " " + s.getLastName()).matches(regex)).toList();
    }

    @Override
    public List<Student> getStudentsByBirthDate(LocalDate from, LocalDate to) {
        return students.queryAll()
                .filter(d -> DateTimeUtils.isInRange(d.getBirthDate(), from, to)).toList();
    }

    @Override
    public List<Student> getStudentsByGroupId(int groupId) {
        return students.queryAll()
                .filter(s -> s.getGroupId() != null && s.getGroupId().equals(groupId)).toList();
    }

    @Override
    public boolean updateStudent(Student student) {
        basicCheck(student);

        if (checkUpdateValidity(student)) {
            return students.update(student);
        }
        return false;
    }

    @Override
    public boolean removeStudent(int studentId) {
        var dbEntryToRemove = students.get(studentId);
        if (dbEntryToRemove == null) {
            return false;
        }
        if (checkRemoveValidity(dbEntryToRemove)) {
            if (students.delete(studentId) != null) {
                // Обновляем связанные таблицы только если удаление фактически прошло
                deleteRelatedRatings(studentId);
                return true;
            }
        }
        return false;
    }

    // aux:

    private void basicCheck(Student student) {
        if (student == null) {
            throw new NullPointerException("student");
        }
        if (student.getFirstName() == null) {
            throw new NullPointerException("student.firstName");
        }
        if (student.getFirstName().isBlank()) {
            throw new InvalidParameterException("student.firstName");
        }
        if (student.getLastName() == null) {
            throw new NullPointerException("student.lastName");
        }
        if (student.getLastName().isBlank()) {
            throw new InvalidParameterException("student.lastName");
        }
        if (student.getBirthDate() == null) {
            throw new NullPointerException("student.birthDate");
        }
    }
}
