package edu.oop.schooladmin.model.implementations.testdb;

import java.time.LocalDate;

import edu.oop.schooladmin.model.entities.Teacher;
import edu.oop.schooladmin.model.entities.Student;

public class test {
    public static void main(String[] args) {
        // TestDbStudentsRepository test = new TestDbStudentsRepository();
        // System.out.println("----MaxId--------");
        // System.out.println(test.getMaxId());
        // System.out.println("------Стартовый список------");
        // System.out.println(test.getAllStudents().toString());
        // Student student1 = new Student(null, "Дмитрий", "Ковальчук",
        // LocalDate.of(1996, 10, 14), 1);
        // Student student2 = new Student(null, "Сергей", "Ковальчук",
        // LocalDate.of(1995, 11, 24), 2);
        // System.out.println(test.addStudent(student1));
        // System.out.println(test.addStudent(student2));
        // System.out.println("------MaxId новое значение------");
        // System.out.println(test.getMaxId());
        // System.out.println("------Новый список студентов------");
        // System.out.println(test.getAllStudents().toString());
        // System.out.println("------Поиск по ID------");
        // System.out.println(test.getStudentByStudentId(2));
        // System.out.println("------Поиск по фамилии------");
        // System.out.println(test.getStudentsByStudentSecondName("Ковальчук"));
        // System.out.println("------Поиск по ID группы------");
        // System.out.println(test.getStudentsByGroupId(91));
        // System.out.println("------Удаление по ID------");
        // System.out.println(test.removeStudent(8));
        // System.out.println("------список студентов после удаления------");
        // System.out.println(test.getAllStudents().toString());
        // System.out.println("------Изменение------");
        // student2.setGroupId(155);
        // System.out.println(test.updateStudentData(student2));
        // System.out.println(test.getAllStudents().toString());

        TestDbTeachersRepository test = new TestDbTeachersRepository();
        System.out.println("----MaxId--------");
        System.out.println(test.lastId());
        System.out.println("------Стартовый список------");
        System.out.println(test.getAllTeachers().toString());
        Teacher teacher1 = new Teacher(null, "Дмитрий", "Ковальчук", LocalDate.of(1996, 10, 14), 1);
        Teacher teacher2 = new Teacher(null, "Сергей", "Ковальчук", LocalDate.of(1995, 11, 24), 2);
        System.out.println(test.addTeacher(teacher1));
        System.out.println(test.addTeacher(teacher2));
        System.out.println("------MaxId новое значение------");
        System.out.println(test.lastId());
        System.out.println("------Новый список студентов------");
        System.out.println(test.getAllTeachers().toString());
        System.out.println("------Поиск по ID------");
        System.out.println(test.getTeacherById(2));
        System.out.println("------Поиск по фамилии------");
        System.out.println(test.getTeachersByLastName("Ковальчук"));
        System.out.println("------Поиск по grade------");
        System.out.println(test.getTeachersByGrade(9, 10));
        System.out.println("------Удаление по ID------");
        System.out.println(test.removeTeacher(13));
        System.out.println("------список студентов после удаления------");
        System.out.println(test.getAllTeachers().toString());
        System.out.println("------Изменение------");
        teacher2.setGrade(155);
        System.out.println(test.updateTeacher(teacher2));
        System.out.println(test.getAllTeachers().toString());

    }
}
