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

    /**
     * Поиск по имени и/или фамилии. Допускается частичное совпадение.
     * 
     * @param nameSample Имя, фамилия, или и то и другое через пробел.
     *                   Имя и фамилию допускается предоставить не полностью -
     *                   поиск осуществляется по частичному совпадению.
     * @return Экземпляр, найденной сущности или null если запись не найдена.
     */
    List<Teacher> getTeachersByName(String nameSample);

    List<Teacher> getTeachersByBirthDate(LocalDate from, LocalDate to);

    List<Teacher> getTeachersByGrade(int from, int to);

    List<Teacher> getTeachersByDisciplineId(int disciplineId);

    // update

    boolean updateTeacher(Teacher teacher);

    // delete

    boolean removeTeacher(int teacherId);
}
