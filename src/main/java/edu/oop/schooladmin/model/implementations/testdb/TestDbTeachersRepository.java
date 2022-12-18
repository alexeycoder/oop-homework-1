package edu.oop.schooladmin.model.implementations.testdb;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.List;

import edu.oop.schooladmin.model.entities.Group;
import edu.oop.schooladmin.model.entities.Teacher;
import edu.oop.schooladmin.model.entities.TeacherAppointment;
import edu.oop.schooladmin.model.interfaces.TeachersRepository;
import edu.oop.schooladmin.testdatatables.GroupsTable;
import edu.oop.schooladmin.testdatatables.Queryable;
import edu.oop.schooladmin.testdatatables.TeacherAppointmentsTable;
import edu.oop.schooladmin.testdatatables.TeachersTable;
import edu.oop.utils.StringUtils;

public class TestDbTeachersRepository implements TeachersRepository {

    private final Queryable<Teacher> teachers;
    private final Queryable<TeacherAppointment> teacherAppointments;
    private final Queryable<Group> groups;

    public TestDbTeachersRepository() {
        teachers = TeachersTable.instance();
        teacherAppointments = TeacherAppointmentsTable.instance();
        groups = GroupsTable.instance();
    }

    /**
     * Служет для удостоверения, что операция добавления/обновления указанной
     * записи не нарушит *целостность БД*.
     * 
     * @param teacher Экземпляр добавляемой/обновляемой записи.
     * @return Возвращает true, если операция допустима для переданных данных.
     */
    private boolean checkUpdateValidity(Teacher teacher) {
        // Teachers: не зависит от других таблиц - всегда можно обновлять:
        return true;
    }

    /**
     * Служет для удостоверения, что операция удаления указанной
     * записи не нарушит *целостность БД*.
     * 
     * @param teacher Запись для удаления.
     * @return Возвращает true, если удаление указанной записи не нарушит
     *         целостность БД.
     */
    private boolean checkRemoveValidity(Teacher teacher) {
        // Teachers: не зависит от других таблиц - всегда можно удалять.
        // Однако при удалении необходимо:
        // 1. Об-null-ить все ссылки на удаляемого в Groups
        // 2. Удалить все связанные назначения
        return true;
    }

    private void deleteRelatedAppointments(int teacherId) {
        var appointmentsSelect = teacherAppointments.queryAll()
                .filter(ta -> ta.getTeacherId() != null && ta.getTeacherId().equals(teacherId));
        for (var appointment : appointmentsSelect.toList()) {
            teacherAppointments.delete(appointment.getAppointmentId());
        }
    }

    private void resetTeacherOnRelatedGroups(int teacherId) {
        groups.queryAll()
                .filter(g -> g.getTeacherId() != null && g.getTeacherId().equals(teacherId))
                .forEach(g -> groups.update(resetTeacherOnGroup(g)));
    }

    private static Group resetTeacherOnGroup(Group group) {
        group.setTeacherId(null);
        return group;
    }

    @Override
    public Teacher addTeacher(Teacher teacher) {
        basicCheck(teacher);

        if (checkUpdateValidity(teacher)) {
            return teachers.add(teacher);
        }
        return null;
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return teachers.queryAll().toList();
    }

    @Override
    public Teacher getTeacherById(int teacherId) {
        return teachers.get(teacherId);
    }

    @Override
    public List<Teacher> getTeachersByName(String nameSample) {
        if (nameSample == null) {
            return List.of();
        }
        if (nameSample.isBlank()) {
            return List.of();
        }

        String regex = StringUtils.getRegexContainsAll(nameSample);
        return teachers.queryAll()
                .filter(t -> (t.getFirstName() + " " + t.getLastName()).matches(regex)).toList();
    }

    @Override
    public List<Teacher> getTeachersByBirthDate(LocalDate from, LocalDate to) {
        return teachers.queryAll()
                .filter(d -> d.getBirthDate().isAfter(from) && d.getBirthDate().isBefore(to)).toList();
    }

    @Override
    public List<Teacher> getTeachersByGrade(int from, int to) {
        return teachers.queryAll().filter(t -> t.getGrade() >= from && t.getGrade() <= to).toList();
    }

    @Override
    public List<Teacher> getTeachersByDisciplineId(int disciplineId) {
        var appointmentsSelect = teacherAppointments.queryAll()
                .filter(ta -> ta.getDisciplineId() != null
                        && ta.getDisciplineId().equals(disciplineId)
                        && ta.getTeacherId() != null);

        var teachersSelect = appointmentsSelect.mapToInt(ta -> ta.getTeacherId())
                .distinct().boxed().map(i -> teachers.get(i));

        return teachersSelect.toList();

        // var appointments = teacherAppointmentsRepository
        // .getTeacherAppointmentsByDisciplineId(disciplineId);
        // var teachers = appointments.stream().mapToInt(a ->
        // a.getTeacherId()).distinct().boxed()
        // .map(i -> teachersRepository.getTeacherById(i)).toList();
        // return teachers;
    }

    @Override
    public boolean updateTeacher(Teacher teacher) {
        basicCheck(teacher);

        if (checkUpdateValidity(teacher)) {
            return teachers.update(teacher);
        }
        return false;
    }

    @Override
    public boolean removeTeacher(int teacherId) {
        var dbEntryToRemove = teachers.get(teacherId);
        if (dbEntryToRemove == null) {
            return false;
        }
        if (checkRemoveValidity(dbEntryToRemove)) {
            if (teachers.delete(teacherId) != null) {
                // Обновляем связанные таблицы только если удаление фактически прошло
                resetTeacherOnRelatedGroups(teacherId);
                deleteRelatedAppointments(teacherId);
                return true;
            }
        }
        return false;
    }

    // aux:

    private void basicCheck(Teacher teacher) {
        if (teacher == null) {
            throw new NullPointerException("teacher");
        }
        if (teacher.getFirstName() == null) {
            throw new NullPointerException("teacher.firstName");
        }
        if (teacher.getFirstName().isBlank()) {
            throw new InvalidParameterException("teacher.firstName");
        }
        if (teacher.getLastName() == null) {
            throw new NullPointerException("teacher.lastName");
        }
        if (teacher.getLastName().isBlank()) {
            throw new InvalidParameterException("teacher.lastName");
        }
        if (teacher.getBirthDate() == null) {
            throw new NullPointerException("teacher.birthDate");
        }
    }
}
