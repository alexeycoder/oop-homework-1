package edu.oop.schooladmin.client.viewmodels;

import edu.oop.schooladmin.model.entities.Group;
import edu.oop.schooladmin.model.entities.Teacher;

public class GroupViewModel extends ViewModelBase {
	private final int groupId;
	private final int classYear;
	private final char classMark;
	private final String teacherInfo;

	public GroupViewModel(Group group, Teacher teacher) {
		this.groupId = group.getGroupId();
		this.classYear = group.getClassYear();
		this.classMark = group.getClassMark();
		if (teacher != null) {
			this.teacherInfo = teacherRepr(teacher);
		} else {
			this.teacherInfo = "не назначен";
		}
	}

	private String teacherRepr(Teacher teacher) {
		return String.format("%-14s %-14s (ID %d)", teacher.getFirstName(),
				teacher.getLastName(), teacher.getTeacherId());
	}

	@Override
	public String toString() {
		String str = String.format("%d\t%d-%s\t Кл. руковод.: ", groupId, classYear, classMark);
		str += teacherInfo;
		return str;
	}
}
