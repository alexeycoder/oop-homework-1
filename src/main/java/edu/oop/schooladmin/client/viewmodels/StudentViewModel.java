package edu.oop.schooladmin.client.viewmodels;

import java.time.LocalDate;

import edu.oop.schooladmin.model.entities.Group;
import edu.oop.schooladmin.model.entities.Student;

public class StudentViewModel extends ViewModelBase {
	private final int id;
	private final String firstName;
	private final String lastName;
	private final LocalDate birthDate;
	private final String groupInfo;

	public StudentViewModel(Student student, Group group) {
		this.id = student.getStudentId();
		this.firstName = student.getFirstName();
		this.lastName = student.getLastName();
		this.birthDate = student.getBirthDate();
		if (group == null) {
			this.groupInfo = null;
		} else {
			this.groupInfo = String.format("Класс %d-%s", group.getClassYear(), group.getClassMark());
		}
	}

	@Override
	public String toString() {
		String str = String.format("%d.\t%-12s\t%-12s\tРод. %s", id, firstName, lastName, birthDate);
		if (groupInfo != null) {
			str += "\t" + groupInfo;
		}
		return str;
	}

	public static String studentSimplifiedRepr(Student student) {
		return String.format("(ID %d) %-12s %-12s", student.getStudentId(), student.getFirstName(),
				student.getLastName());
	}
}
