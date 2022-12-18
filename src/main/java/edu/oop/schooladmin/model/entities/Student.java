package edu.oop.schooladmin.model.entities;

import java.time.LocalDate;

public class Student extends Person {
    private Integer studentId;
    private Integer groupId;

    public Student() {
    }

    public Student(Integer studentId, String firstName, String lastName, LocalDate birthDate, Integer groupId) {
        super(firstName, lastName, birthDate);
        this.studentId = studentId;
        this.groupId = groupId;
    }

    public Student(Student other) {
        this(
                other.studentId,
                other.firstName,
                other.lastName,
                other.birthDate,
                other.groupId);
    }

    public Student(String firstName, String secondName, LocalDate birthDate, Integer groupId) {
        this(null, firstName, secondName, birthDate, groupId);
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    @Override
    public String toString() {
        return studentId + ":" + super.toString() + ":" + groupId;
    }
}
