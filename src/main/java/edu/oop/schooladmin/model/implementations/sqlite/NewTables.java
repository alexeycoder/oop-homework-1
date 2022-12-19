package edu.oop.schooladmin.model.implementations.sqlite;

import java.util.ArrayList;

import edu.oop.schooladmin.model.entities.Discipline;
import edu.oop.schooladmin.model.entities.Group;
import edu.oop.schooladmin.model.entities.Rating;
import edu.oop.schooladmin.model.entities.Student;
import edu.oop.schooladmin.model.entities.Teacher;
import edu.oop.schooladmin.model.entities.TeacherAppointment;
import edu.oop.schooladmin.testdatatablesprevious.DisciplinesTable;
import edu.oop.schooladmin.testdatatablesprevious.GroupsTable;
import edu.oop.schooladmin.testdatatablesprevious.RatingsTable;
import edu.oop.schooladmin.testdatatablesprevious.StudentsTable;
import edu.oop.schooladmin.testdatatablesprevious.TeacherAppointmentsTable;
import edu.oop.schooladmin.testdatatablesprevious.TeachersTable;

    public class NewTables {
        RepositoryUtils utils = new RepositoryUtils();

        public void createTableStudents(){
            String sql = "CREATE TABLE Students(studentId INTEGER PRIMARY KEY, firstName text, lastName text, "+
                            "birthDate text, groupId int NOT NULL);";
            utils.setTable(sql);
            }

        public void createTableTeachers(){
            String sql = "CREATE TABLE Teachers(teacherId INTEGER PRIMARY KEY, firstName text, lastName text, " + 
                            "birthDate text, grade int);";
            utils.setTable(sql);
            }

        public void createTableDiscipline(){
            String sql = "CREATE TABLE Disciplines(disciplineId INTEGER PRIMARY KEY, name text);";
            utils.setTable(sql);
            }

        public void createTableGroups(){
            String sql = "CREATE TABLE Groups(groupId INTEGER PRIMARY KEY, classYear int NOT NULL, classMark VARCHAR, " + 
                            "teacherId INTEGER);";
            utils.setTable(sql);
            }

        public void createTableTeacherAppointments(){
            String sql = "CREATE TABLE TeacherAppointments(appointmentId INTEGER PRIMARY KEY, teacherId int NOT NULL, disciplineId int NOT NULL, " + 
                            "groupId int NOT NULL);";
            utils.setTable(sql);
            }

        public void createTableRatings(){
            String sql = "CREATE TABLE Ratings(ratingId INTEGER PRIMARY KEY, studentId int NOT NULL, disciplineId int NOT NULL, dateTime text, " + 
                            "value int NOT NULL, commentary text);";
            utils.setTable(sql);
            }
        
        public void addStudens(){     
            ArrayList<Student> students = StudentsTable.students();
            SqlStudentsRepository repository = new SqlStudentsRepository();
            for (Student student : students) {
                repository.addStudent(student);
            }
        }

        public void addTeachers(){     
            ArrayList<Teacher> teachers = TeachersTable.teachers();
            SqlTeachersRepository repository = new SqlTeachersRepository();
            for (Teacher teacher : teachers) {
                repository.addTeacher(teacher);
            }
        }

        public void addDisciplines(){     
            ArrayList<Discipline> disciplines = DisciplinesTable.disciplines();
            SqlDisciplinesRepository repository = new SqlDisciplinesRepository();
            for (Discipline discipline : disciplines) {
                repository.addDiscipline(discipline);
            }
        }

        public void addGroups(){     
            ArrayList<Group> groups = GroupsTable.groups();
            SqlGroupsRepository repository = new SqlGroupsRepository();
            for (Group group : groups) {
                repository.addGroup(group);
            }
        }

        public void addAppointments(){     
            ArrayList<TeacherAppointment> appointments = TeacherAppointmentsTable.appointments();
            SqlAppointmentsRepository repository = new SqlAppointmentsRepository();
            for (TeacherAppointment appointment : appointments) {
                repository.addTeacherAppointment(appointment);
            }
        }

        public void addRatings(){     
            ArrayList<Rating> ratings = RatingsTable.ratings();
            SqlRatingsRepository repository = new SqlRatingsRepository();
            for (Rating rating : ratings) {
                repository.addRating(rating);
            }
        }

    }