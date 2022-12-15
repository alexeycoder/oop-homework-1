package edu.oop.schooladmin.model.interfaces;

import java.time.LocalDate;
import java.util.List;

import edu.oop.schooladmin.model.entities.Teacher;

public interface TeachersRepository {

    // create

    Teacher addTeacher(Teacher teacher);

    // read

    Teacher getTeacherById(int teacherId);

    List<Teacher> getAllTeachers();

    List<Teacher> getTeachersByFirstName(String firstName);

    List<Teacher> getTeachersByLastName(String lastName);

    List<Teacher> getTeachersByBirthDate(LocalDate from, LocalDate to);

    List<Teacher> getTeachersByGrade(int from, int to);

    // update

    boolean updateTeacher(Teacher teacher);

    // delete

    Teacher removeTeacher(int teacherId);
}
