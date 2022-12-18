package edu.oop.schooladmin.model.implementations.sqlite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.oop.schooladmin.model.entities.Discipline;
import edu.oop.schooladmin.model.entities.Group;
import edu.oop.schooladmin.model.entities.Teacher;
import edu.oop.schooladmin.model.entities.TeacherAppointment;
import edu.oop.schooladmin.model.interfaces.TeacherAppointmentsRepository;

public class SqlAppointmentsRepository implements TeacherAppointmentsRepository {

    @Override
    public TeacherAppointment addTeacherAppointment(Teacher teacher, Discipline discipline, Group group) {
        TeacherAppointment teacherAppointment = new TeacherAppointment(null, teacher.getTeacherId(),
                discipline.getDisciplineId(), group.getGroupId());
        RepositoryUtils utils = new RepositoryUtils();
        String sql = "INSERT INTO TeacherAppointments( 'teacherId', 'disciplineId', 'groupId') " +
                "VALUES( ?, ?, ?);";
        List<Object> parametersList = new ArrayList<>();
        parametersList.add(teacher.getTeacherId());
        parametersList.add(discipline.getDisciplineId());
        parametersList.add(group.getGroupId());

        utils.setTable(sql, parametersList);
        // TODO: Необходимо обновить фактически выданный БД appointmentId в возвращаемом
        // экземпляре:
        return teacherAppointment;
    }

    public boolean addTeacherAppointment(TeacherAppointment appointment) {
        RepositoryUtils utils = new RepositoryUtils();
        String sql = "INSERT INTO TeacherAppointments( 'teacherId', 'disciplineId', 'groupId') " +
                "VALUES( ?, ?, ?);";
        List<Object> parametersList = new ArrayList<>();
        parametersList.add(appointment.getTeacherId());
        parametersList.add(appointment.getDisciplineId());
        parametersList.add(appointment.getGroupId());

        utils.setTable(sql, parametersList);
        return true;
    }

    @Override
    public TeacherAppointment getTeacherAppointmentById(int teacherAppointmentId) {
        RepositoryUtils utils = new RepositoryUtils();
        String sql = "SELECT appointmentId, teacherId, disciplineId, groupId FROM TeacherAppointments WHERE appointmentId=? ";
        List<Object> parametersList = new ArrayList<>();
        parametersList.add(teacherAppointmentId);
        try {
            ResultSet resultSet = utils.getFromTable(sql, parametersList);
            if (resultSet.next()) {
                TeacherAppointment appointment = new TeacherAppointment((Integer) (resultSet.getInt("appointmentId")),
                        (Integer) (resultSet.getInt("teacherId")),
                        (Integer) (resultSet.getInt("disciplineId")),
                        (Integer) (resultSet.getInt("groupId")));
                resultSet.close();
                return appointment;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<TeacherAppointment> getAllTeacherAppointments() {
        String sql = "SELECT appointmentId, teacherId, disciplineId, groupId FROM TeacherAppointments";
        return getTeacherAppointmentsDataList(sql);
    }

    @Override
    public List<TeacherAppointment> getTeacherAppointmentsByTeacherId(int teacherId) {
        String sql = "SELECT appointmentId, teacherId, disciplineId, groupId FROM TeacherAppointments WHERE teacherId=?";
        List<Object> parametersList = new ArrayList<>();
        parametersList.add(teacherId);
        return getTeacherAppointmentsDataList(sql, parametersList);
    }

    @Override
    public List<TeacherAppointment> getTeacherAppointmentsByDisciplineId(int disciplineId) {
        String sql = "SELECT appointmentId, teacherId, disciplineId, groupId FROM TeacherAppointments WHERE disciplineId=?";
        List<Object> parametersList = new ArrayList<>();
        parametersList.add(disciplineId);
        return getTeacherAppointmentsDataList(sql, parametersList);
    }

    @Override
    public List<TeacherAppointment> getTeacherAppointmentsByGroupId(int groupId) {
        String sql = "SELECT appointmentId, teacherId, disciplineId, groupId FROM TeacherAppointments WHERE groupId=?";
        List<Object> parametersList = new ArrayList<>();
        parametersList.add(groupId);
        return getTeacherAppointmentsDataList(sql, parametersList);
    }

    @Override
    public boolean updateTeacherAppointment(TeacherAppointment appointment) {
        RepositoryUtils utils = new RepositoryUtils();
        String sql = "UPDATE TeacherAppointments SET teacherId=?, disciplineId=?, groupId=? WHERE appointmentId=?";
        if (getTeacherAppointmentById(appointment.getAppointmentId()) != null) {
            List<Object> parametersList = new ArrayList<>();
            parametersList.add(appointment.getTeacherId());
            parametersList.add(appointment.getDisciplineId());
            parametersList.add(appointment.getGroupId());
            parametersList.add(appointment.getAppointmentId());
            utils.setTable(sql, parametersList);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeTeacherAppointment(int appointmentId) {
        RepositoryUtils utils = new RepositoryUtils();
        String sql = "DELETE FROM TeacherAppointments WHERE appointmentId=?";
        if (getTeacherAppointmentById(appointmentId) != null) {
            List<Object> parametersList = new ArrayList<>();
            parametersList.add(appointmentId);
            utils.setTable(sql, parametersList);
            return true;
        }
        return false;
    }

    private List<TeacherAppointment> getTeacherAppointmentsDataList(String sql, List<Object> parametersList) {
        RepositoryUtils utils = new RepositoryUtils();
        try {
            ResultSet resultSet = utils.getFromTable(sql, parametersList);
            List<TeacherAppointment> appointments = new ArrayList<>();
            while (resultSet.next()) {
                appointments.add(new TeacherAppointment((Integer) (resultSet.getInt("appointmentId")),
                        (Integer) (resultSet.getInt("teacherId")),
                        (Integer) (resultSet.getInt("disciplineId")),
                        (Integer) (resultSet.getInt("groupId"))));
            }
            resultSet.close();
            return appointments;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<TeacherAppointment> getTeacherAppointmentsDataList(String sql) {
        RepositoryUtils utils = new RepositoryUtils();
        try {
            ResultSet resultSet = utils.getFromTable(sql);
            List<TeacherAppointment> appointments = new ArrayList<>();
            while (resultSet.next()) {
                appointments.add(new TeacherAppointment((Integer) (resultSet.getInt("appointmentId")),
                        (Integer) (resultSet.getInt("teacherId")),
                        (Integer) (resultSet.getInt("disciplineId")),
                        (Integer) (resultSet.getInt("groupId"))));
            }
            resultSet.close();
            return appointments;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
