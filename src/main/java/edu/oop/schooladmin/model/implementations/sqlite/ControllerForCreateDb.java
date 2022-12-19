package edu.oop.schooladmin.model.implementations.sqlite;

import java.sql.SQLException;

public class ControllerForCreateDb {
    public static void main(String[] args) throws SQLException {
        NewTables tables = new NewTables();
        tables.createTableStudents();
        tables.createTableTeachers();
        tables.createTableDiscipline();
        tables.createTableGroups();
        tables.createTableTeacherAppointments();
        tables.createTableRatings();
        tables.addStudens();
        tables.addTeachers();
        tables.addDisciplines();
        tables.addGroups();
        tables.addAppointments();
        tables.addRatings();

    }
}
