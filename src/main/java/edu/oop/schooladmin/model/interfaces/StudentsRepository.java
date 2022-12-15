package edu.oop.schooladmin.model.interfaces;

import java.time.LocalDate;
import java.util.List;

import edu.oop.schooladmin.model.entities.Student;

public interface StudentsRepository {

    // create

    Student addStudent(Student student);

    // read

    Student getStudentById(int studentId);

    List<Student> getAllStudents();

    List<Student> getStudentsByFirstName(String firstName);

    List<Student> getStudentsByLastName(String lastName);

    List<Student> getStudentsByGroupId(int groupId);

    List<Student> getStudentsByBirthDate(LocalDate from, LocalDate to);

    // update

    boolean updateStudent(Student student);

    // delete

    Student removeStudent(int studentId);
}
