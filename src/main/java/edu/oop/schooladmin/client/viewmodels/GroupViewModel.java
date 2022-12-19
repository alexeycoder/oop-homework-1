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
			this.teacherInfo = TeacherViewModel.teacherSimplifiedRepr(teacher);
		} else {
			this.teacherInfo = "не назначен";
		}
	}

	@Override
	public String toString() {
		String str = String.format("%d.\t%d-%s\t Кл. руковод.: ", groupId, classYear, classMark);
		str += teacherInfo;
		return str;
	}

	public static String groupSimplifiedRepr(Group group) {
		return String.format("(ID %d) %d-%s", group.getGroupId(), group.getClassYear(), group.getClassMark());
	}
}
