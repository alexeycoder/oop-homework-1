package edu.oop.schooladmin.model.implementations.testdb;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import edu.oop.schooladmin.model.entities.Student;
import edu.oop.schooladmin.model.interfaces.StudentsRepository;
import edu.oop.schooladmin.testdatatables.StudentsTable;

public class TestDbStudentsRepository implements StudentsRepository {

    private final ArrayList<Student> students;
    private int lastId;

    public int getLastId() {
        return lastId;
    }

    public TestDbStudentsRepository() {
        students = StudentsTable.students();
        lastId = RepositoryUtils.getLastPrimaryKey(students, s -> s.getStudentId());
    }

    @Override
    public Student addStudent(Student student) {
        makeSureValidity(student);

        student.setStudentId(++lastId);

        // В таблицу сохраняем клон переданного экземпляра, а не его самого,
        // чтобы разорвать связь между переданным экземпляром и фактически
        // добавляемой в таблицу сущностью, дабы не возникло ситуации, когда
        // всякие возможные дальнейшие модификации переданного экземпляра
        // в клиентском коде влияли на состояние сущности в нашей таблице:
        var addedEntity = new Student(student);
        students.add(addedEntity);

        // Возвращаем не связный с таблицей экземпляр, но показывая что
        // он уже содержит фактический id:
        return student;
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> resultList = new ArrayList<>();
        for (Student student : students) {
            resultList.add(new Student(student));
        }
        return resultList;
    }

    // Меняем в репозиторных методах параметры идентификатора на примитивный int,
    // чтобы не заботится о null лишний раз. См. комменты в docs
    @Override
    public Student getStudentById(int studentId) {
        var dbEntity = students.stream().filter(s -> s.getStudentId().equals(studentId)).findFirst();
        if (dbEntity.isPresent()) {
            return new Student(dbEntity.get());
        }
        return null;
    }

    @Override
    public List<Student> getStudentsByFirstName(String firstName) {
        List<Student> resultList = new ArrayList<>();
        for (Student student : students) {
            if (student.getFirstName().equalsIgnoreCase(firstName)) {
                resultList.add(new Student(student));
            }
        }
        return resultList;
    }

    @Override
    public List<Student> getStudentsByLastName(String lastName) {
        return students.stream().filter(d -> lastName.equalsIgnoreCase(d.getLastName()))
                .map(Student::new).toList();
    }

    @Override
    public List<Student> getStudentByName(String nameSample) {
        if (nameSample == null) {
            return List.of();
        }
        if (nameSample.isBlank()) {
            return List.of();
        }

        String regex = RepositoryUtils.getRegexContainsAll(nameSample);
        return students.stream().filter(s -> (s.getFirstName() + " " + s.getLastName()).matches(regex))
                .map(Student::new).toList();
    }

    @Override
    public List<Student> getStudentsByBirthDate(LocalDate from, LocalDate to) {
        return students.stream().filter(d -> d.getBirthDate().isAfter(from) && d.getBirthDate().isBefore(to)).toList();
    }

    @Override
    public List<Student> getStudentsByGroupId(int groupId) {
        List<Student> resultList = new ArrayList<>();
        for (Student student : students) {
            if (student.getGroupId().equals(groupId)) {
                resultList.add(new Student(student));
            }
        }
        return resultList;
    }

    @Override
    public boolean removeStudent(int studentId) {
        Student dbEntityToRemove = null;
        Integer index = null;
        for (int i = 0; i < students.size(); i++) {
            var dbEntity = students.get(i);
            if (dbEntity.getStudentId().equals(studentId)) {
                dbEntityToRemove = dbEntity;
                index = i;
                break;
            }
        }
        if (dbEntityToRemove != null) {
            students.remove(index.intValue());
            return true;
        } else
            return false;
    }

    @Override
    public boolean updateStudent(Student student) {
        Integer index = null;
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId().equals(student.getStudentId())) {
                index = i;
                break;
            }
        }
        if (index != null) {
            students.set(index.intValue(), new Student(student));
            return true;
        } else
            return false;
    }

    // aux methods:

    private void makeSureValidity(Student student) {
        if (student == null) {
            throw new InvalidParameterException("student");
        }
        if (student.getFirstName() == null || student.getFirstName().isBlank()) {
            throw new InvalidParameterException("student.firstName");
        }
        // TODO: Посмотреть если что ещё нужно чекать перед добавлением и т.д.
    }

    /**
     * Копирует значения свойств экземпляра источника в экземпляр назначения
     * и возвращает экземпляр назначения.
     */
    private static Student copyProperties(Student instanceFrom, Student instanceTo) {
        assert instanceFrom != null && instanceTo != null;
        instanceTo.setStudentId(instanceFrom.getStudentId());
        instanceTo.setFirstName(instanceFrom.getFirstName());
        instanceTo.setLastName(instanceFrom.getLastName());
        instanceTo.setBirthDate(instanceFrom.getBirthDate());
        instanceTo.setGroupId(instanceFrom.getGroupId());
        return instanceTo;
    }

}
