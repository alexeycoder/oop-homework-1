package edu.oop.schooladmin.model.implementations.testdb;

import java.util.List;

import edu.oop.schooladmin.model.entities.Discipline;
import edu.oop.schooladmin.model.entities.Group;
import edu.oop.schooladmin.model.entities.Teacher;
import edu.oop.schooladmin.model.entities.TeacherAppointment;
import edu.oop.schooladmin.model.interfaces.TeacherAppointmentsRepository;
import edu.oop.schooladmin.testdatatables.DisciplinesTable;
import edu.oop.schooladmin.testdatatables.GroupsTable;
import edu.oop.schooladmin.testdatatables.Queryable;
import edu.oop.schooladmin.testdatatables.TeacherAppointmentsTable;
import edu.oop.schooladmin.testdatatables.TeachersTable;

public class TestDbTeacherAppointmentsRepository implements TeacherAppointmentsRepository {

    private final Queryable<TeacherAppointment> teacherAppointments;
    private final Queryable<Teacher> teachers;
    private final Queryable<Discipline> disciplines;
    private final Queryable<Group> groups;

    public TestDbTeacherAppointmentsRepository() {
        teacherAppointments = TeacherAppointmentsTable.instance();
        teachers = TeachersTable.instance();
        disciplines = DisciplinesTable.instance();
        groups = GroupsTable.instance();
    }

    /**
     * Служет для удостоверения, что операция добавления/обновления указанной
     * записи не нарушит *целостность БД*.
     * 
     * @param teacherAppointment Экземпляр добавляемой/обновляемой записи.
     * @return Возвращает true, если операция допустима для переданных данных.
     */
    private boolean checkUpdateValidity(TeacherAppointment teacherAppointment) {
        // TeachersAppointments - связующая реляция (т.е. таблица),
        // поэтому добавлять в неё записи с отсутствующими ссылками на связуемые
        // сущности в других таблицах (внешние ключи) просто не имеет смысла:
        var disciplineId = teacherAppointment.getDisciplineId();
        if (disciplineId == null || disciplines.get(disciplineId) == null) {
            return false;
        }
        var groupId = teacherAppointment.getGroupId();
        if (groupId == null || groups.get(groupId) == null) {
            return false;
        }
        var teacherId = teacherAppointment.getTeacherId();
        if (teacherId == null || teachers.get(teacherId) == null) {
            return false;
        }
        return true;
    }

    private boolean checkRemoveValidity(TeacherAppointment teacherAppointment) {
        // Удаление записи TeacherAppointment - no problem:
        return true;
    }

    @Override
    public TeacherAppointment addTeacherAppointment(Teacher teacher, Discipline discipline, Group group) {
        TeacherAppointment appointment = new TeacherAppointment(
                null,
                teacher.getTeacherId(),
                discipline.getDisciplineId(),
                group.getGroupId());
        if (checkUpdateValidity(appointment)) {
            return teacherAppointments.add(appointment);
        }
        return null;
    }

    @Override
    public List<TeacherAppointment> getAllTeacherAppointments() {
        return teacherAppointments.queryAll().toList();
    }

    @Override
    public TeacherAppointment getTeacherAppointmentById(int teacherAppointmentId) {
        return teacherAppointments.get(teacherAppointmentId);
    }

    @Override
    public List<TeacherAppointment> getTeacherAppointmentsByTeacherId(int teacherId) {
        return teacherAppointments.queryAll().filter(ta -> ta.getTeacherId().equals(teacherId)).toList();
    }

    @Override
    public List<TeacherAppointment> getTeacherAppointmentsByDisciplineId(int disciplineId) {
        return teacherAppointments.queryAll().filter(ta -> ta.getDisciplineId().equals(disciplineId)).toList();
    }

    @Override
    public List<TeacherAppointment> getTeacherAppointmentsByGroupId(int groupId) {
        return teacherAppointments.queryAll().filter(ta -> ta.getGroupId().equals(groupId)).toList();
    }

    @Override
    public boolean updateTeacherAppointment(TeacherAppointment appointment) {
        if (checkUpdateValidity(appointment)) {
            return teacherAppointments.update(appointment);
        }
        return false;
    }

    @Override
    public boolean removeTeacherAppointment(int teacherAppointmentId) {
        var dbEntryToRemove = teacherAppointments.get(teacherAppointmentId);
        if (dbEntryToRemove == null) {
            return false;
        }
        if (checkRemoveValidity(dbEntryToRemove)) {
            return teacherAppointments.delete(teacherAppointmentId) != null;
        }
        return false;
    }
}
