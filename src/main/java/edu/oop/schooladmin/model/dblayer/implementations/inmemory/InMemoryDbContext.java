package edu.oop.schooladmin.model.dblayer.implementations.inmemory;

import edu.oop.schooladmin.model.dblayer.interfaces.DbLayerContext;
import edu.oop.schooladmin.model.dblayer.interfaces.Queryable;
import edu.oop.schooladmin.model.entities.Discipline;
import edu.oop.schooladmin.model.entities.Group;
import edu.oop.schooladmin.model.entities.Rating;
import edu.oop.schooladmin.model.entities.Student;
import edu.oop.schooladmin.model.entities.Teacher;
import edu.oop.schooladmin.model.entities.TeacherAppointment;

public class InMemoryDbContext implements DbLayerContext {

	private final QueryableBase<Discipline> disciplines;
	private final QueryableBase<Teacher> teachers;
	private final QueryableBase<Group> groups;
	private final QueryableBase<Student> students;
	private final QueryableBase<Rating> ratings;
	private final QueryableBase<TeacherAppointment> teacherAppointments;

	public InMemoryDbContext() {
		this.disciplines = new DisciplinesTable();
		this.teachers = new TeachersTable();
		this.groups = new GroupsTable();
		this.students = new StudentsTable();
		this.ratings = new RatingsTable();
		this.teacherAppointments = new TeacherAppointmentsTable();
	}

	@Override
	public void close() throws Exception {
		// just do nothing
		// Как вариант можно при выходе сохранять in-memory database на диск.
	}

	@Override
	public Queryable<Discipline> disciplines() {
		return disciplines;
	}

	@Override
	public Queryable<Teacher> teachers() {
		return teachers;
	}

	@Override
	public Queryable<Group> groups() {
		return groups;
	}

	@Override
	public Queryable<Student> students() {
		return students;
	}

	@Override
	public Queryable<Rating> ratings() {
		return ratings;
	}

	@Override
	public Queryable<TeacherAppointment> teacherAppointments() {
		return teacherAppointments;
	}
}
