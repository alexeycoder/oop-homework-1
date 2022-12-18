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

    /**
     * Поиск по имени и/или фамилии. Допускается частичное совпадение.
     * 
     * @param nameSample Имя, фамилия, или и то и другое через пробел.
     *                    Имя и фамилию допускается предоставить не полностью -
     *                    поиск осуществляется по частичному совпадению.
     * @return Экземпляр, найденной сущности или null если запись не найдена.
     */
    List<Student> getStudentsByName(String nameSample);

    List<Student> getStudentsByGroupId(int groupId);

    List<Student> getStudentsByBirthDate(LocalDate from, LocalDate to);

    // update

    boolean updateStudent(Student student);

    // delete

    boolean removeStudent(int studentId);
}
