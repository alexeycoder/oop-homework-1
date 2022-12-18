package edu.oop.schooladmin.model.implementations.testdb;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import edu.oop.schooladmin.model.entities.Discipline;
import edu.oop.schooladmin.model.entities.Group;
import edu.oop.schooladmin.model.entities.Teacher;
import edu.oop.schooladmin.model.entities.TeacherAppointment;
import edu.oop.schooladmin.model.interfaces.TeacherAppointmentsRepository;
import edu.oop.schooladmin.testdatatablesprevious.TeacherAppointmentsTable;

public class TestDbTeacherAppointmentsRepository implements TeacherAppointmentsRepository {

    private final ArrayList<TeacherAppointment> appointments;
    private int lastId;

    public int getLastId() {
        return lastId;
    }

    public TestDbTeacherAppointmentsRepository() {
        appointments = TeacherAppointmentsTable.appointments();
        lastId = RepositoryUtils.getLastPrimaryKey(appointments, a -> a.getAppointmentId());
    }

    @Override
    public boolean addTeacherAppointment(Teacher teacher, Discipline discipline, Group group) {
        TestDbTeachersRepository teachersDb = new TestDbTeachersRepository();
        TestDbGroupsRepository groupsDb = new TestDbGroupsRepository();
        TestDbDisciplinesRepository disciplinesDb = new TestDbDisciplinesRepository();
        if (teachersDb.getTeacherById(teacher.getTeacherId()) != null &&
                disciplinesDb.getDisciplineById(discipline.getDisciplineId()) != null &&
                groupsDb.getGroupById(group.getGroupId()) != null) {
            TeacherAppointment appointment = new TeacherAppointment(++lastId,
                    teacher.getTeacherId(),
                    discipline.getDisciplineId(),
                    group.getGroupId());
            appointments.add(appointment);
            return true;
        } else
            return false;
    }

    @Override
    public List<TeacherAppointment> getAllTeacherAppointments() {
        List<TeacherAppointment> resultList = new ArrayList<>();
        for (TeacherAppointment appointment : appointments) {
            resultList.add(new TeacherAppointment(appointment));
        }
        return resultList;
    }

    @Override
    public TeacherAppointment getTeacherAppointmentById(int teacherAppointmentId) {
        var dbEntity = appointments.stream().filter(a -> a.getAppointmentId().equals(teacherAppointmentId)).findFirst();
        if (dbEntity.isPresent()) {
            return new TeacherAppointment(dbEntity.get());
        }
        return null;
    }

    @Override
    public List<TeacherAppointment> getTeacherAppointmentsByTeacherId(int teacherId) {
        // return appointments.stream().filter(a -> a.getTeacherId().equals(teacherId))
        // .map(TeacherAppointment::new).toList();
        List<TeacherAppointment> resultList = new ArrayList<>();
        for (TeacherAppointment appointment : appointments) {
            if (appointment.getTeacherId().equals(teacherId)) {
                resultList.add(new TeacherAppointment(appointment));
            }
        }
        return resultList;
    }

    @Override
    public List<TeacherAppointment> getTeacherAppointmentsByDisciplineId(int disciplineId) {
        List<TeacherAppointment> resultList = new ArrayList<>();
        for (TeacherAppointment appointment : appointments) {
            if (appointment.getDisciplineId().equals(disciplineId)) {
                resultList.add(new TeacherAppointment(appointment));
            }
        }
        return resultList;
    }

    @Override
    public List<TeacherAppointment> getTeacherAppointmentsByGroupId(int groupId) {
        List<TeacherAppointment> resultList = new ArrayList<>();
        for (TeacherAppointment appointment : appointments) {
            if (appointment.getGroupId().equals(groupId)) {
                resultList.add(new TeacherAppointment(appointment));
            }
        }
        return resultList;
    }

    @Override
    public boolean updateTeacherAppointment(TeacherAppointment appointment) {
        TestDbTeachersRepository teachersDb = new TestDbTeachersRepository();
        TestDbGroupsRepository groupsDb = new TestDbGroupsRepository();
        TestDbDisciplinesRepository disciplinesDb = new TestDbDisciplinesRepository();
        Integer index = null;
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getAppointmentId().equals(appointment.getAppointmentId())) {
                index = i;
                break;
            }
        }
        if (teachersDb.getTeacherById(appointment.getTeacherId()) != null &&
                disciplinesDb.getDisciplineById(appointment.getDisciplineId()) != null &&
                groupsDb.getGroupById(appointment.getGroupId()) != null &&
                index != null) {
            appointments.set(index.intValue(), new TeacherAppointment(appointment));
            return true;
        } else
            return false;
    }

    @Override
    public boolean removeTeacherAppointment(int teacherAppointmentId) {
        TeacherAppointment dbEntityToRemove = null;
        Integer index = null;
        for (int i = 0; i < appointments.size(); i++) {
            var dbEntity = appointments.get(i);
            if (dbEntity.getAppointmentId().equals(teacherAppointmentId)) {
                dbEntityToRemove = dbEntity;
                index = i;
                break;
            }
        }
        if (dbEntityToRemove != null) {
            appointments.remove(index.intValue());
            return true;
        } else
            return false;
    }

    private void makeSureValidity(Group group) {
        if (group == null) {
            throw new InvalidParameterException("group");
        }
    }

    /**
     * Копирует значения свойств экземпляра источника в экземпляр назначения
     * и возвращает экземпляр назначения.
     */
    private static Group copyProperties(Group instanceFrom, Group instanceTo) {
        assert instanceFrom != null && instanceTo != null;
        instanceTo.setGroupId(instanceFrom.getGroupId());
        instanceTo.setClassMark(instanceFrom.getClassMark());
        instanceTo.setClassYear(instanceFrom.getClassYear());
        instanceTo.setTeacherId(instanceFrom.getTeacherId());
        return instanceTo;
    }

}
