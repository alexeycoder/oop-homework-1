package edu.oop.schooladmin.model.implementations.testdb;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import edu.oop.schooladmin.model.entities.Teacher;
import edu.oop.schooladmin.model.interfaces.TeachersRepository;
import edu.oop.schooladmin.testdatatables.TeachersTable;

public class TestDbTeachersRepository implements TeachersRepository {
    private final ArrayList<Teacher> teachers;
    private int lastId;

    // TODO: Временный аксессор для теста.
    public int lastId() {
        return lastId;
    }

    public TestDbTeachersRepository() {
        teachers = TeachersTable.teachers();
        lastId = RepositoryUtils.getLastPrimaryKey(teachers, t -> t.getTeacherId());
    }

    @Override
    public Teacher addTeacher(Teacher teacher) {
        makeSureValidity(teacher);

        teacher.setTeacherId(++lastId);

        // В таблицу сохраняем клон переданного экземпляра, а не его самого,
        // чтобы разорвать связь между переданным экземпляром и фактически
        // добавляемой в таблицу сущностью, дабы не возникло ситуации, когда
        // всякие возможные дальнейшие модификации переданного экземпляра
        // в клиентском коде влияли на состояние сущности в нашей таблице:
        var addedEntity = new Teacher(teacher);
        teachers.add(addedEntity);

        // Возвращаем не связный с таблицей экземпляр, но показывая что
        // он уже содержит фактический id:
        return teacher;
    }

    @Override
    public List<Teacher> getAllTeachers() {
        List<Teacher> resultList = new ArrayList<>();
        for (Teacher teacher : teachers) {
            resultList.add(new Teacher(teacher));
        }
        return resultList;
    }

    @Override
    public Teacher getTeacherById(int teacherId) {
        var dbEntity = teachers.stream().filter(s -> s.getTeacherId().equals(teacherId)).findFirst();
        if (dbEntity.isPresent()) {
            return new Teacher(dbEntity.get());
        }
        return null;
    }

    @Override
    public List<Teacher> getTeachersByFirstName(String firstName) {
        List<Teacher> resultList = new ArrayList<>();
        for (Teacher teacher : teachers) {
            if (teacher.getFirstName().equalsIgnoreCase(firstName)) {
                resultList.add(new Teacher(teacher));
            }
        }
        return resultList;
    }

    @Override
    public List<Teacher> getTeachersByLastName(String lastName) {
        List<Teacher> resultList = new ArrayList<>();
        for (Teacher teacher : teachers) {
            if (teacher.getLastName().equalsIgnoreCase(lastName)) {
                resultList.add(new Teacher(teacher));
            }
        }
        return resultList;
    }

    @Override
    public List<Teacher> getTeachersByBirthDate(LocalDate from, LocalDate to) {
        return teachers.stream().filter(d -> d.getBirthDate().isAfter(from) && d.getBirthDate().isBefore(to)).toList();
    }

    @Override
    public List<Teacher> getTeachersByGrade(int from, int to) {
        List<Teacher> resultList = new ArrayList<>();
        for (Teacher teacher : teachers) {
            int teacherGrade = teacher.getGrade();
            if (teacherGrade >= from && teacherGrade <= to) {
                resultList.add(new Teacher(teacher));
            }
        }
        return resultList;
    }

    @Override
    public Teacher removeTeacher(int teacherId) {
        Teacher dbEntityToRemove = null;
        for (int i = 0; i < teachers.size(); i++) {
            var dbEntity = teachers.get(i);
            if (dbEntity.getTeacherId().equals(teacherId)) {
                dbEntityToRemove = dbEntity;
                break;
            }
        }
        if (dbEntityToRemove != null) {
            teachers.remove(teacherId);
            return dbEntityToRemove;
        } else
            return null;
    }

    @Override
    public boolean updateTeacher(Teacher teacher) {

        // TODO: потом на базе copyProperties сделать, чтобы копировать по-свойствам
        // значения из переданного экземпляра teacher в соответствующий по id
        // экземпляр в таблице

        Integer index = null;
        for (int i = 0; i < teachers.size(); i++) {
            if (teachers.get(i).getTeacherId().equals(teacher.getTeacherId())) {
                index = i;
                break;
            }
        }
        if (index != null) {
            teachers.set(index.intValue(), teacher);
            return true;
        } else
            return false;
    }

    // aux methods:

    private void makeSureValidity(Teacher teacher) {
        if (teacher == null) {
            throw new InvalidParameterException("teacher");
        }
        if (teacher.getFirstName() == null || teacher.getFirstName().isBlank()) {
            throw new InvalidParameterException("teacher.firstName");
        }
        // TODO: Посмотреть если что ещё нужно чекать перед добавлением и т.д.
    }

    /**
     * Копирует значения свойств экземпляра источника в экземпляр назначения
     * и возвращает экземпляр назначения.
     */
    private static Teacher copyProperties(Teacher instanceFrom, Teacher instanceTo) {
        assert instanceFrom != null && instanceTo != null;
        instanceTo.setTeacherId(instanceFrom.getTeacherId());
        instanceTo.setFirstName(instanceFrom.getFirstName());
        instanceTo.setLastName(instanceFrom.getLastName());
        instanceTo.setBirthDate(instanceFrom.getBirthDate());
        instanceTo.setGrade(instanceFrom.getGrade());
        return instanceTo;
    }
}
