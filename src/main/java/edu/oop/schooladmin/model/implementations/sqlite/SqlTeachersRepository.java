package edu.oop.schooladmin.model.implementations.sqlite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import edu.oop.schooladmin.model.entities.Group;
import edu.oop.schooladmin.model.entities.Teacher;
import edu.oop.schooladmin.model.entities.TeacherAppointment;
import edu.oop.schooladmin.model.interfaces.TeachersRepository;

public class SqlTeachersRepository implements TeachersRepository {

    @Override
    public Teacher addTeacher(Teacher teacher) {
        RepositoryUtils utils = new RepositoryUtils();
        String sql = "INSERT INTO Teachers( 'firstName', 'lastName', 'birthDate', 'grade') " +
                "VALUES( ?, ?, ?, ?);";
        List<Object> parametersList = new ArrayList<>();
        parametersList.add(teacher.getFirstName());
        parametersList.add(teacher.getLastName());
        parametersList.add(teacher.getBirthDate().toString());
        parametersList.add(teacher.getGrade());

        utils.setTable(sql, parametersList);
        return teacher;
    }

    @Override
    public Teacher getTeacherById(int teacherId) {
        RepositoryUtils utils = new RepositoryUtils();
        String sql = "SELECT teacherId, firstName, lastName, birthDate, grade FROM Teachers WHERE teacherId=? ";
        List<Object> parametersList = new ArrayList<>();
        parametersList.add(teacherId);
        try {
            ResultSet resultSet = utils.getFromTable(sql, parametersList);
            if (resultSet.next()) {
                Teacher teacher = new Teacher((Integer) (resultSet.getInt("teacherId")),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        LocalDate.parse(resultSet.getString("birthDate")),
                        (Integer) (resultSet.getInt("grade")));
                resultSet.close();
                return teacher;
            }
            return null;
        } catch (SQLException e) {
            // e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Teacher> getAllTeachers() {
        String sql = "SELECT teacherId, firstName, lastName, birthDate, grade FROM Teachers";
        return getTeachersDataList(sql);
    }

    @Override
    public List<Teacher> getTeachersByDisciplineId(int disciplineId) {
        // TODO Auto-generated method stub
        return null;
    }

    // @Override
    // public List<Teacher> getTeachersByFirstName(String firstName) {
    // String sql = "SELECT teacherId, firstName, lastName, birthDate, grade FROM
    // Teachers WHERE firstName=?";
    // List<Object> parametersList = new ArrayList<>();
    // parametersList.add(firstName);
    // return getTeachersDataList(sql, parametersList);
    // }

    // @Override
    // public List<Teacher> getTeachersByLastName(String lastName) {
    // String sql = "SELECT teacherId, firstName, lastName, birthDate, grade FROM
    // Teachers WHERE LastName=?";
    // List<Object> parametersList = new ArrayList<>();
    // parametersList.add(lastName);
    // return getTeachersDataList(sql, parametersList);
    // }

    @Override
    public List<Teacher> getTeachersByName(String namePattern) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Teacher> getTeachersByBirthDate(LocalDate from, LocalDate to) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Teacher> getTeachersByBirthDate(LocalDate birthDate) {
        String sql = "SELECT teacherId, firstName, lastName, birthDate, grade FROM Teachers WHERE birthDate=?";
        List<Object> parametersList = new ArrayList<>();
        parametersList.add(birthDate.toString());
        return getTeachersDataList(sql, parametersList);
    }

    @Override
    public List<Teacher> getTeachersByGrade(int from, int to) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Teacher> getTeachersByGrade(int grade) {
        String sql = "SELECT teacherId, firstName, lastName, birthDate, grade FROM Teachers WHERE grade=?";
        List<Object> parametersList = new ArrayList<>();
        parametersList.add(grade);
        return getTeachersDataList(sql, parametersList);
    }

    @Override
    public boolean updateTeacher(Teacher teacher) {
        RepositoryUtils utils = new RepositoryUtils();
        String sql = "UPDATE Teachers SET firstName=?, lastName=?, birthDate=?, grade=? WHERE teacherId=?";
        if (getTeacherById(teacher.getTeacherId()) != null) {
            List<Object> parametersList = new ArrayList<>();
            parametersList.add(teacher.getFirstName());
            parametersList.add(teacher.getLastName());
            parametersList.add(teacher.getBirthDate().toString());
            parametersList.add(teacher.getGrade());
            parametersList.add(teacher.getTeacherId());
            utils.setTable(sql, parametersList);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeTeacher(int teacherId) {
        RepositoryUtils utils = new RepositoryUtils();
        String sql = "DELETE FROM Teachers WHERE teacherId=?";
        SqlGroupsRepository groupsRepository = new SqlGroupsRepository();
        SqlAppointmentsRepository appointmentsRepository = new SqlAppointmentsRepository();
        if (getTeacherById(teacherId) != null) {
            List<Object> parametersList = new ArrayList<>();
            parametersList.add(teacherId);
            utils.setTable(sql, parametersList);
            List<Group> groups = groupsRepository.getGroupsByTeacherId(teacherId);
            List<TeacherAppointment> appointments = appointmentsRepository.getTeacherAppointmentsByTeacherId(teacherId);
            if (!groups.isEmpty()) {
                for (Group group : groups) {
                    group.setTeacherId(null);
                    groupsRepository.updateGroup(group);
                }
            }
            if (!appointments.isEmpty()) {
                for (TeacherAppointment appointment : appointments) {
                    appointmentsRepository.removeTeacherAppointment(appointment.getAppointmentId());
                }
            }
            return true;
        }
        return false;
    }

    private List<Teacher> getTeachersDataList(String sql, List<Object> parametersList) {
        RepositoryUtils utils = new RepositoryUtils();
        try {
            ResultSet resultSet = utils.getFromTable(sql, parametersList);
            List<Teacher> teachers = new ArrayList<>();
            while (resultSet.next()) {
                teachers.add(new Teacher((Integer) (resultSet.getInt("teacherId")),
                        resultSet.getString("firstName"),
                        resultSet.getString("LastName"),
                        LocalDate.parse(resultSet.getString("birthDate")),
                        (Integer) (resultSet.getInt("grade"))));
            }
            resultSet.close();
            return teachers;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Teacher> getTeachersDataList(String sql) {
        RepositoryUtils utils = new RepositoryUtils();
        try {
            ResultSet resultSet = utils.getFromTable(sql);
            List<Teacher> teachers = new ArrayList<>();
            while (resultSet.next()) {
                teachers.add(new Teacher((Integer) (resultSet.getInt("teacherId")),
                        resultSet.getString("firstName"),
                        resultSet.getString("LastName"),
                        LocalDate.parse(resultSet.getString("birthDate")),
                        (Integer) (resultSet.getInt("grade"))));
            }
            resultSet.close();
            return teachers;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
