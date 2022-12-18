package edu.oop.schooladmin.model.entities;

import java.time.LocalDate;

public class Teacher extends Person {
	private Integer teacherId;
	private int grade;

	public Teacher() {
	}

	public Teacher(Integer teacherId, String firstName, String lastName, LocalDate birthDate, int grade) {
		super(firstName, lastName, birthDate);
		this.teacherId = teacherId;
		this.grade = grade;
	}

	public Teacher(Teacher other) {
		this(
				other.teacherId,
				other.firstName,
				other.lastName,
				other.birthDate,
				other.grade);
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getGrade() {
		return grade;
	}

	@Override
	public String toString() {
		return teacherId + ":" + super.toString() + ":" + grade;
	}
}
