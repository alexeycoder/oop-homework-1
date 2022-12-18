package edu.oop.schooladmin.model.implementations.testdb;

import java.time.LocalDate;
import java.time.LocalDateTime;

import edu.oop.schooladmin.model.entities.Teacher;
import edu.oop.schooladmin.model.entities.TeacherAppointment;
import edu.oop.schooladmin.model.entities.Group;
import edu.oop.schooladmin.model.entities.Rating;
import edu.oop.schooladmin.model.entities.Student;

public class TestsTemp {
    public static void main(String[] args) {
        TestDbStudentsRepository testStudent = new TestDbStudentsRepository();
        TestDbTeachersRepository testTeacher = new TestDbTeachersRepository();
        TestDbGroupsRepository testGroup = new TestDbGroupsRepository();
        TestDbTeacherAppointmentsRepository testAppointment = new TestDbTeacherAppointmentsRepository();
        TestDbDisciplinesRepository testDiscipline = new TestDbDisciplinesRepository();
        TestDbRatingsRepository testRating = new TestDbRatingsRepository();

        System.out.println("----MaxId--------");
        // System.out.println(testStudent.getLastId());
        System.out.println("------Стартовый список студентов------");
        System.out.println(testStudent.getAllStudents().toString());
        Student student1 = new Student(null, "Дмитрий", "Ковальчук",
        LocalDate.of(1996, 10, 14), 1);
        Student student2 = new Student(null, "Алексей", "Ковальчук",
        LocalDate.of(1996, 10, 14), 1);
        System.out.println(testStudent.addStudent(student1));
        System.out.println(testStudent.addStudent(student2));
        System.out.println("------MaxId новое значение------");
        // System.out.println(testStudent.getLastId());
        System.out.println("------Новый список студентов------");
        System.out.println(testStudent.getAllStudents().toString());
        System.out.println("------Поиск по ID 2 студента------");
        System.out.println(testStudent.getStudentById(2));
        System.out.println("------Поиск по фамилии студента------");
        // System.out.println(testStudent.getStudentsByLastName("Ковальчук"));
        System.out.println("------Поиск студетнов по ID группы 91------");
        System.out.println(testStudent.getStudentsByGroupId(91));
        System.out.println("------Удаление студентов по ID 8------");
        System.out.println(testStudent.removeStudent(8));
        System.out.println("------список студентов после удаления------");
        System.out.println(testStudent.getAllStudents().toString());
        System.out.println("------Изменение------");
        student2.setGroupId(155);
        System.out.println(testStudent.updateStudent(student2));
        System.out.println(testStudent.getAllStudents().toString());

        
        System.out.println("----MaxId--------");
        // System.out.println(testTeacher.getTeachersLastId());
        System.out.println("------Стартовый список учителей------");
        System.out.println(testTeacher.getAllTeachers().toString());
        Teacher teacher1 = new Teacher(null, "Дмитрий", "Ковальчук", LocalDate.of(1996, 10, 14), 1);
        Teacher teacher2 = new Teacher(null, "Сергей", "Ковальчук", LocalDate.of(1995, 11, 24), 2);
        System.out.println(testTeacher.addTeacher(teacher1));
        System.out.println(testTeacher.addTeacher(teacher2));
        System.out.println("------MaxId новое значение------");
        // System.out.println(testTeacher.getTeachersLastId());
        System.out.println("------Новый список учителей------");
        System.out.println(testTeacher.getAllTeachers().toString());
        System.out.println("------Поиск учителя по ID 2------");
        System.out.println(testTeacher.getTeacherById(2));
        System.out.println("------Поиск учителей по фамилии------");
        // System.out.println(testTeacher.getTeachersByLastName("Ковальчук"));
        System.out.println("------Поиск учителей по grade------");
        System.out.println(testTeacher.getTeachersByGrade(9, 10));

        System.out.println("------Стартовый список групп------");
        System.out.println(testGroup.getAllGroups().toString());

        System.out.println("------Стартовый список назначений------");
        System.out.println(testAppointment.getAllTeacherAppointments().toString());

        System.out.println("------Удаление по ID 5------");
        System.out.println(testTeacher.removeTeacher(5));
        System.out.println("------список учителей после удаления------");
        System.out.println(testTeacher.getAllTeachers().toString());

        System.out.println("------Список групп после удаления учителя------");
        System.out.println(testGroup.getAllGroups().toString());

        System.out.println("------Список назначений после удаления учителя------");
        System.out.println(testAppointment.getAllTeacherAppointments().toString());

        System.out.println("------Изменение------");
        teacher2.setGrade(155);
        System.out.println(testTeacher.updateTeacher(teacher2));
        System.out.println(testTeacher.getAllTeachers().toString());


        
        System.out.println("----MaxId групп--------");
        // System.out.println(testGroup.getLastId());
        System.out.println("------Стартовый список групп------");
        System.out.println(testGroup.getAllGroups().toString());
        System.out.println("------Добавление групп------");
        Group group1 = new Group(null, 10, 'А', 12);
        Group group2 = new Group(null, 10, 'Г', 12);
        System.out.println(testGroup.addGroup(group1));
        System.out.println(testGroup.addGroup(group2));
        System.out.println("------MaxId групп новое значение------");
        // System.out.println(testGroup.getLastId());
        System.out.println("------Список групп после добавления------");
        System.out.println(testGroup.getAllGroups().toString());
        System.out.println("------Поиск по ID 101------");
        System.out.println(testGroup.getGroupById(101));
        System.out.println("------Поиск по букве класса 'А'------");
        System.out.println(testGroup.getGroupsByClassMark('А'));
        System.out.println("------Поиск по году класса 10------");
        System.out.println(testGroup.getGroupsByClassYear(10));

        System.out.println("------список студентов------");
        System.out.println(testStudent.getAllStudents().toString());

        System.out.println("------попутка удалить группу со студентами------");
        System.out.println(testGroup.removeGroup(91));

        System.out.println("------Удаление студентов по ID 1, 2, 3, 4, 5 (Группа 91)------");
        System.out.println(testStudent.removeStudent(1));
        System.out.println(testStudent.removeStudent(2));
        System.out.println(testStudent.removeStudent(3));
        System.out.println(testStudent.removeStudent(4));
        System.out.println(testStudent.removeStudent(5));

        System.out.println("------попутка удалить группу без студентов------");
        System.out.println(testGroup.removeGroup(91));

        System.out.println("------Список групп после удаления------");
        System.out.println(testGroup.getAllGroups().toString());


        System.out.println("----MaxId назначений--------");
        // System.out.println(testAppointment.getLastId());
        System.out.println("------Стартовый список назначений------");
        System.out.println(testAppointment.getAllTeacherAppointments().toString());
        System.out.println("------Добавление назначений------");
        System.out.println(testAppointment.addTeacherAppointment(teacher1, testDiscipline.getDisciplineById(2), group1));
        System.out.println(testAppointment.addTeacherAppointment(teacher1, testDiscipline.getDisciplineById(2), group2));
        System.out.println("------MaxId назначений новое значение------");
        // System.out.println(testAppointment.getLastId());
        System.out.println("------Список назначений после добавления------");
        System.out.println(testAppointment.getAllTeacherAppointments().toString());
        System.out.println("------Поиск назначения по ID 21------");
        System.out.println(testAppointment.getTeacherAppointmentById(21));
        System.out.println("------Поиск назначения по ID 3 дисцеплины------");
        System.out.println(testAppointment.getTeacherAppointmentsByDisciplineId(3));
        System.out.println("------Поиск назначения по ID 93 группы------");
        System.out.println(testAppointment.getTeacherAppointmentsByGroupId(93));
        System.out.println("------Поиск назначения по ID 4 учителя------");
        System.out.println(testAppointment.getTeacherAppointmentsByTeacherId(5));
        System.out.println("------Изменение назначения ID 6------");
        System.out.println(testAppointment.updateTeacherAppointment(new TeacherAppointment(6, 2, 2, 93)));
        System.out.println("------Список назначений после изменения------");
        System.out.println(testAppointment.getAllTeacherAppointments().toString());


        System.out.println("----MaxId рейтинга--------");
        // System.out.println(testRating.getLastId());
        System.out.println("------Добавление рейтинга------");
        Rating rating1 = new Rating(10, 2, LocalDateTime.of(2021,9,15, 0,0), 5,  "");
        Rating rating2 = new Rating(10, 2, LocalDateTime.of(2021,10,15, 0,0), 2,  "");
        System.out.println(testRating.addRating(rating1).toString());
        System.out.println(testRating.addRating(rating2).toString());
        System.out.println("------MaxId рейтинга новое значение------");
        // System.out.println(testRating.getLastId());
        System.out.println("------Поиск рейтинга по ID 100------");
        System.out.println(testRating.getRatingById(100));
        System.out.println("------Поиск рейтинга по времени------");
        System.out.println(testRating.getRatingsByDateTime(LocalDateTime.of(2021,9,15, 0,0), LocalDateTime.of(2021,12,15, 0,0)));
        System.out.println("------Поиск рейтинга по ID 2 дисциплины------");
        System.out.println(testRating.getRatingsByDisciplineId(2));
        System.out.println("------Поиск рейтинга по ID 10 студента------");
        System.out.println(testRating.getRatingsByStudentId(10));
        System.out.println("------Поиск рейтинга по оценке 2------");
        // System.out.println(testRating.getRatingsByValue(2));
        System.out.println("------Изменение рейтинга------");
        rating1.setValue(2);
        System.out.println(testRating.updateRating(rating1));
        System.out.println("------Поиск рейтинга по оценке 2------");
        // System.out.println(testRating.getRatingsByValue(2));
        System.out.println("------Удаление рейтинга по Id 201------");
        System.out.println(testRating.removeRating(201));
        System.out.println("------Поиск рейтинга по оценке 2------");
        // System.out.println(testRating.getRatingsByValue(2));

    }
}
