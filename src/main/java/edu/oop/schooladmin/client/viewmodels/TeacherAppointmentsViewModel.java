package edu.oop.schooladmin.client.viewmodels;

import edu.oop.schooladmin.model.entities.Discipline;
import edu.oop.schooladmin.model.entities.Group;
import edu.oop.schooladmin.model.entities.Teacher;
import edu.oop.schooladmin.model.entities.TeacherAppointment;

public class TeacherAppointmentsViewModel extends ViewModelBase {

	private final int appointmentId;
	private final String teacherInfo;
	private final String disciplineInfo;
	private final String groupInfo;

	public TeacherAppointmentsViewModel(TeacherAppointment appointment,
			Teacher teacher, Discipline discipline, Group group) {

		this.appointmentId = appointment.getAppointmentId();
		this.teacherInfo = teacher != null ? TeacherViewModel.teacherSimplifiedRepr(teacher) : null;
		this.disciplineInfo = discipline != null ? DisciplineViewModel.disciplineSimplifiedRept(discipline) : null;
		this.groupInfo = group != null ? GroupViewModel.groupSimplifiedRepr(group) : null;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(Integer.toString(appointmentId)).append(".");
		if (disciplineInfo != null) {
			sb.append("\tПредмет: ").append(String.format("%-15s", disciplineInfo));
		}
		if (groupInfo != null) {
			sb.append("\tГруппа: ").append(String.format("%-13s", groupInfo));
		}
		if (teacherInfo != null) {
			sb.append("\tУчитель: ").append(teacherInfo);
		}
		return sb.toString();
	}
}
