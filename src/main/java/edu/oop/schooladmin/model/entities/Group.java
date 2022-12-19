package edu.oop.schooladmin.model.entities;

public class Group {
	private Integer groupId;
	private int classYear;
	private char classMark;
	private Integer teacherId;

	public Group() {
	}

	public Group(Integer groupId, int classYear, char classMark, Integer teacherId) {
		this.groupId = groupId;
		this.classYear = classYear;
		this.classMark = classMark;
		this.teacherId = teacherId;
	}

	public Group(Group other) {
		this(
				other.groupId,
				other.classYear,
				other.classMark,
				other.teacherId);
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public int getClassYear() {
		return classYear;
	}

	public void setClassYear(int classYear) {
		this.classYear = classYear;
	}

	public char getClassMark() {
		return classMark;
	}

	public void setClassMark(char classMark) {
		this.classMark = classMark;
	}

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	@Override
	public String toString() {
		return groupId + ":" + classYear + ":" + classMark + ":" + teacherId;
	}
}
