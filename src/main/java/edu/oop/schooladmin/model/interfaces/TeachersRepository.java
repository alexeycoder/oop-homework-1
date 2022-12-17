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

    /**
     * Поиск по имени и/илм фамилии. Допускается частичное совпадение.
     * 
     * @param namePattern Имя, фамилия, или и то и другое через пробел.
     *                    Имя и фамилию допускается предоставить не полностью -
     *                    поиск осуществляется по частичному совпадению.
     * @return Экземпляр, найденной сущности или null если запись не найдена.
     */
    List<Teacher> getTeachersByName(String namePattern);

    List<Teacher> getTeachersByBirthDate(LocalDate from, LocalDate to);

    List<Teacher> getTeachersByGrade(int from, int to);

    // update

    boolean updateTeacher(Teacher teacher);

    // delete

    boolean removeTeacher(int teacherId);
}
