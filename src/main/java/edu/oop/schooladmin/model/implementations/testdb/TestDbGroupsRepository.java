package edu.oop.schooladmin.model.implementations.testdb;

import java.util.List;

import edu.oop.schooladmin.model.entities.Group;
import edu.oop.schooladmin.model.entities.Student;
import edu.oop.schooladmin.model.entities.Teacher;
import edu.oop.schooladmin.model.entities.TeacherAppointment;
import edu.oop.schooladmin.model.interfaces.GroupsRepository;
import edu.oop.schooladmin.testdatatables.GroupsTable;
import edu.oop.schooladmin.testdatatables.Queryable;
import edu.oop.schooladmin.testdatatables.StudentsTable;
import edu.oop.schooladmin.testdatatables.TeacherAppointmentsTable;
import edu.oop.schooladmin.testdatatables.TeachersTable;

public class TestDbGroupsRepository implements GroupsRepository {

    private final Queryable<Group> groups;
    private final Queryable<Teacher> teachers;
    private final Queryable<Student> students;
    private final Queryable<TeacherAppointment> teacherAppointments;

    public TestDbGroupsRepository() {
        groups = GroupsTable.instance();
        teachers = TeachersTable.instance();
        students = StudentsTable.instance();
        teacherAppointments = TeacherAppointmentsTable.instance();
    }

    /**
     * Служет для удостоверения, что операция добавления/обновления указанной
     * записи не нарушит *целостность БД*.
     * 
     * @param group Экземпляр добавляемой/обновляемой записи.
     * @return Возвращает true, если операция допустима для переданных данных.
     */
    private boolean checkUpdateValidity(Group group) {
        // Groups: допускается либо отсутствующая ссылка на Teacher, либо ссылка
        // на существующего Teacher.
        // Ссылка на несуществующего Teacher не допускается.
        var teacherId = group.getTeacherId();
        if (teacherId != null && teachers.get(teacherId) == null) {
            return false;
        }
        return true;
    }

    /**
     * Служет для удостоверения, что операция удаления указанной
     * записи не нарушит *целостность БД*.
     * 
     * @param group Запись для удаления.
     * @return Возвращает true, если удаление указанной записи не нарушит
     *         целостность БД.
     */
    private boolean checkRemoveValidity(Group group) {
        // Удаление записи Group разрешается только при отсутствии Назначений Учителей
        // ссылающихся на группу.
        // Кроме того при удалении необходимо обеспечить об-null-ение соответствующей
        // внешней ссылки на удаляемую группу у всех учеников.
        var groupId = group.getGroupId();
        return !teacherAppointments.queryAll().anyMatch(ta -> groupId.equals(ta.getGroupId()));
    }

    private void resetGroupOnRelatedStudents(int groupId) {
        students.queryAll()
                .filter(s -> s.getGroupId() != null && s.getGroupId().equals(groupId))
                .forEach(s -> students.update(resetGroupOnStudent(s)));
    }

    private static Student resetGroupOnStudent(Student student) {
        student.setGroupId(null);
        return student;
    }

    @Override
    public Group addGroup(Group group) {
        if (group == null) {
            throw new NullPointerException("group");
        }

        if (checkUpdateValidity(group)) {
            return groups.add(group);
        }
        return null;
    }

    @Override
    public List<Group> getAllGroups() {
        return groups.queryAll().toList();
    }

    @Override
    public List<Group> getGroupsByTeacherId(int teacherId) {
        return groups.queryAll()
                .filter(d -> d.getTeacherId() != null && d.getTeacherId().equals(teacherId)).toList();
    }

    @Override
    public Group getGroupById(int groupId) {
        return groups.get(groupId);
    }

    @Override
    public List<Group> getGroupsByClassYear(int classYear) {
        return groups.queryAll().filter(g -> g.getClassYear() == classYear).toList();
    }

    @Override
    public List<Group> getGroupsByClassMark(char classMark) {
        return groups.queryAll().filter(g -> g.getClassMark() == classMark).toList();
    }

    @Override
    public boolean updateGroup(Group group) {
        if (group == null) {
            throw new NullPointerException("group");
        }

        if (checkUpdateValidity(group)) {
            return groups.update(group);
        }
        return false;
    }

    @Override
    public boolean removeGroup(int groupId) {
        var dbEntryToRemove = groups.get(groupId);
        if (dbEntryToRemove == null) {
            return false;
        }
        if (checkRemoveValidity(dbEntryToRemove)) {
            if (groups.delete(groupId) != null) {
                // Обновляем связанные таблицы только если удаление фактически прошло
                resetGroupOnRelatedStudents(groupId);
                return true;
            }
        }
        return false;
    }
}
