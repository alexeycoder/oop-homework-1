package edu.oop.schooladmin.model.interfaces;

import java.util.List;

import edu.oop.schooladmin.model.entities.Discipline;
import edu.oop.schooladmin.model.entities.Group;
import edu.oop.schooladmin.model.entities.Teacher;
import edu.oop.schooladmin.model.entities.TeacherAppointment;

public interface TeacherAppointmentsRepository {

	// create

	TeacherAppointment addTeacherAppointment(Teacher teacher, Discipline discipline, Group group);

	// read

	TeacherAppointment getTeacherAppointmentById(int teacherAppointmentId);

	List<TeacherAppointment> getTeacherAppointmentsByTeacherId(int teacherId);

	List<TeacherAppointment> getTeacherAppointmentsByDisciplineId(int disciplineId);

	List<TeacherAppointment> getTeacherAppointmentsByGroupId(int groupId);

	// update

	boolean updateTeacherAppointment(TeacherAppointment teacherAppointment);

	// delete

	TeacherAppointment removeTeacherAppointment(int teacherAppointmentId);
}
