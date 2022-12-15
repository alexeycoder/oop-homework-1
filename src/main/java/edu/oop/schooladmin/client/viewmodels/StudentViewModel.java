package edu.oop.schooladmin.client.viewmodels;

import java.time.LocalDate;

import edu.oop.schooladmin.model.entities.Group;
import edu.oop.schooladmin.model.entities.Student;

public class StudentViewModel extends ViewModelBase {
	private int id;
	private String firstName;
	private String lastName;
	private LocalDate birthDate;
	private int classYear;
	private char classMark;

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
		return String.format("%d\t%s %s\t\tРод. %s\tКласс %d-%s", id, firstName, lastName, birthDate, classYear,
				classMark);
	}

}
