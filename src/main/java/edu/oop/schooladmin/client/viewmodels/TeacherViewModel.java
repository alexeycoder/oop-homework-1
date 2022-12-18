package edu.oop.schooladmin.client.viewmodels;

import java.time.LocalDate;
import java.util.List;

import edu.oop.schooladmin.model.entities.Group;
import edu.oop.schooladmin.model.entities.Teacher;

public class TeacherViewModel extends ViewModelBase {
	private final int id;
	private final String firstName;
	private final String lastName;
	private final LocalDate birthDate;
	private final int grade;
	private final List<String> groupsInfo;

	public TeacherViewModel(Teacher teacher, List<Group> groups) {
		this.id = teacher.getTeacherId();
		this.firstName = teacher.getFirstName();
		this.lastName = teacher.getLastName();
		this.birthDate = teacher.getBirthDate();
		this.grade = teacher.getGrade();
		if (groups == null || groups.size() == 0) {
			this.groupsInfo = null;
		} else {
			this.groupsInfo = groups.stream().filter(g -> g != null).map(TeacherViewModel::groupRepr).toList();
		}
	}

	private static String groupRepr(Group group) {
		return String.format("%d-%s", group.getClassYear(), group.getClassMark());
	}

	@Override
	public String toString() {
		String str = String.format("%d.\t%-12s\t%-12s\tРод. %s\tКатегория %d", id, firstName, lastName, birthDate,
				grade);
		if (groupsInfo != null) {
			str += "\tКл. руков.:";
			for (String gi : groupsInfo) {
				str += " " + gi;
			}
		}
		return str;
	}

	public static String teacherSimplifiedRepr(Teacher teacher) {
		return String.format("(ID %d) %-12s %-12s", teacher.getTeacherId(), teacher.getFirstName(),
				teacher.getLastName());
	}
}
