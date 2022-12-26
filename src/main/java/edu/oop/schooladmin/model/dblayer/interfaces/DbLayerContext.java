package edu.oop.schooladmin.model.dblayer.interfaces;

import edu.oop.schooladmin.model.entities.Discipline;
import edu.oop.schooladmin.model.entities.Group;
import edu.oop.schooladmin.model.entities.Rating;
import edu.oop.schooladmin.model.entities.Student;
import edu.oop.schooladmin.model.entities.Teacher;
import edu.oop.schooladmin.model.entities.TeacherAppointment;

public interface DbLayerContext extends AutoCloseable {
	Queryable<Discipline> disciplines();

	Queryable<Teacher> teachers();

	Queryable<Group> groups();

	Queryable<Student> students();

	Queryable<Rating> ratings();

	Queryable<TeacherAppointment> teacherAppointments();
}
