package edu.oop.schooladmin.client.viewmodels;

import java.time.LocalDate;

import edu.oop.schooladmin.model.entities.Group;
import edu.oop.schooladmin.model.entities.Student;

public class StudentViewModel extends ViewModelBase {
	private final int id;
	private final String firstName;
	private final String lastName;
	private final LocalDate birthDate;
	private final int classYear;
	private final char classMark;

	public StudentViewModel(Student student, Group group) {
		this.id = student.getStudentId();
		this.firstName = student.getFirstName();
		this.lastName = student.getLastName();
		this.birthDate = student.getBirthDate();
		this.classYear = group.getClassYear();
		this.classMark = group.getClassMark();
	}

	@Override
	public String toString() {
		return String.format("%d\t%-14s %-14s\tРод. %s\tКласс %d-%s", id, firstName, lastName, birthDate, classYear,
				classMark);
	}

}
