package edu.oop.schooladmin.model.implementations.sqlite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import edu.oop.schooladmin.model.entities.Student;
import edu.oop.schooladmin.model.interfaces.StudentsRepository;

public class SqlStudentsRepository implements StudentsRepository {

    @Override
    public Student addStudent(Student student){ 
        RepositoryUtils utils = new RepositoryUtils();
        String sql = "INSERT INTO Students( 'firstName', 'lastName', 'birthDate', 'groupId') "+
        "VALUES( ?, ?, ?, ?);";
        List<Object> parametersList = new ArrayList<>();
        parametersList.add(student.getFirstName());
        parametersList.add(student.getLastName());
        parametersList.add(student.getBirthDate().toString());
        parametersList.add(student.getGroupId());

        utils.setTable(sql, parametersList);
        return student;
    }

    @Override
    public Student getStudentById(int studentId) {
        RepositoryUtils utils = new RepositoryUtils();
        String sql = "SELECT studentId, firstName, lastName, birthDate, groupId FROM Students WHERE studentId=? ";
        ArrayList<Object> parametersList = new ArrayList<>();
        parametersList.add(studentId);
        try {
            ResultSet resultSet = utils.getFromTable(sql, parametersList);
            if (resultSet.next()){
                Student student = new Student((Integer)(resultSet.getInt("studentId")),
                                                resultSet.getString("firstName"),
                                                resultSet.getString("lastName"),
                                                LocalDate.parse(resultSet.getString("birthDate")) ,
                                                (Integer)(resultSet.getInt("groupId")));
                resultSet.close();
                return student;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Student> getAllStudents() {
        String sql = "SELECT studentId, firstName, lastName, birthDate, groupId FROM Students";
        return getStudentsDataList(sql);
    }

    // @Override
    // public List<Student> getStudentsByFirstName(String firstName) {
    //     String sql = "SELECT studentId, firstName, lastName, birthDate, groupId FROM Students WHERE firstName=?";
    //     List<Object> parametersList = new ArrayList<>();
    //     parametersList.add(firstName);
    //     return getStudentsDataList(sql, parametersList);
    // }

    // @Override
    // public List<Student> getStudentsByLastName(String lastName) {
    //     String sql = "SELECT studentId, firstName, lastName, birthDate, groupId FROM Students WHERE LastName=?";
    //     List<Object> parametersList = new ArrayList<>();
    //     parametersList.add(lastName);
    //     return getStudentsDataList(sql, parametersList);
    // }

    @Override
    public List<Student> getStudentsByName(String nameSample) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Student> getStudentsByGroupId(int groupId) {
        String sql = "SELECT studentId, firstName, lastName, birthDate, groupId FROM Students WHERE groupId=?";
        List<Object> parametersList = new ArrayList<>();
        parametersList.add(groupId);
        return getStudentsDataList(sql, parametersList);
    }

    @Override
    public List<Student> getStudentsByBirthDate(LocalDate from, LocalDate to) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Student> getStudentsByBirthDate(LocalDate birthDate) {
        String sql = "SELECT studentId, firstName, lastName, birthDate, groupId FROM Students WHERE birthDate=?";
        List<Object> parametersList = new ArrayList<>();
        parametersList.add(birthDate.toString());
        return getStudentsDataList(sql, parametersList);
    }

    // UPDATE Basket SET user_basket=? WHERE id=?
    @Override
    public boolean updateStudent(Student student) {
        RepositoryUtils utils = new RepositoryUtils();
        String sql = "UPDATE Students SET firstName=?, lastName=?, birthDate=?, groupId=? WHERE studentId=?";
        if(getStudentById(student.getStudentId()) != null){
            List<Object> parametersList = new ArrayList<>();
            parametersList.add(student.getFirstName());
            parametersList.add(student.getLastName());
            parametersList.add(student.getBirthDate().toString());
            parametersList.add(student.getGroupId());
            parametersList.add(student.getStudentId());
            utils.setTable(sql, parametersList);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeStudent(int studentId) {
        RepositoryUtils utils = new RepositoryUtils();
        String sql = "DELETE FROM Students WHERE studentId=?";
        if(getStudentById(studentId) != null){
            List<Object> parametersList = new ArrayList<>();
            parametersList.add(studentId);
            utils.setTable(sql, parametersList);
            return true;
        }
        return false;
    }

    private List<Student> getStudentsDataList(String sql, List<Object> parametersList){
        RepositoryUtils utils = new RepositoryUtils();
        try {
            ResultSet resultSet = utils.getFromTable(sql, parametersList);
            List<Student> students = new ArrayList<>();
            while (resultSet.next()) {
                students.add(new Student((Integer)(resultSet.getInt("studentId")),
                                        resultSet.getString("firstName"),
                                        resultSet.getString("LastName"),
                                        LocalDate.parse(resultSet.getString("birthDate")) ,
                                        (Integer)(resultSet.getInt("groupId")))) ;
            }
            resultSet.close();
            return students;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Student> getStudentsDataList(String sql){
        RepositoryUtils utils = new RepositoryUtils();
        try {
            ResultSet resultSet = utils.getFromTable(sql);
            List<Student> students = new ArrayList<>();
            while(resultSet.next()){
                students.add(new Student((Integer)(resultSet.getInt("studentId")),
                                            resultSet.getString("firstName"),
                                            resultSet.getString("LastName"),
                                            LocalDate.parse(resultSet.getString("birthDate")) ,
                                            (Integer)(resultSet.getInt("groupId"))));
            }
            resultSet.close();
            return students;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
