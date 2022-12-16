package edu.oop.schooladmin.model.entities;

public class TeacherAppointment {
	private Integer appointmentId;
	private Integer teacherId;
	private Integer disciplineId;
	private Integer groupId;

	public TeacherAppointment() {
	}

	public TeacherAppointment(Integer appointmentId, Integer teacherId, Integer disciplineId, Integer groupId) {
		this.appointmentId = appointmentId;
		this.teacherId = teacherId;
		this.disciplineId = disciplineId;
		this.groupId = groupId;
	}

	public TeacherAppointment(TeacherAppointment other) {
		this(
				other.appointmentId,
				other.teacherId,
				other.disciplineId,
				other.groupId);
	}

	public Integer getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Integer appointmentId) {
		this.appointmentId = appointmentId;
	}

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public Integer getDisciplineId() {
		return disciplineId;
	}

	public void setDisciplineId(Integer disciplineId) {
		this.disciplineId = disciplineId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	@Override
	public String toString() {
		return appointmentId + ":" + teacherId + ":" + disciplineId + ":" + groupId;
	}
}
