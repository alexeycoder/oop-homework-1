package edu.oop.schooladmin.model.implementations.testdb;

import java.util.NoSuchElementException;

import edu.oop.schooladmin.model.interfaces.DataProvider;
import edu.oop.schooladmin.model.interfaces.DisciplinesRepository;
import edu.oop.schooladmin.model.interfaces.GroupsRepository;
import edu.oop.schooladmin.model.interfaces.RatingsRepository;
import edu.oop.schooladmin.model.interfaces.StudentsRepository;
import edu.oop.schooladmin.model.interfaces.TeacherAppointmentsRepository;
import edu.oop.schooladmin.model.interfaces.TeachersRepository;
import edu.oop.schooladmin.model.interfaces.UserRolesRepository;
import edu.oop.schooladmin.model.interfaces.UsersRepository;

public class TestDbProvider implements DataProvider {

	private final DisciplinesRepository disciplinesRepository;
	private final TeachersRepository teachersRepository;
	private final GroupsRepository groupsRepository;
	private final StudentsRepository studentsRepository;
	private final TeacherAppointmentsRepository teacherAppointmentsRepository;
	private final RatingsRepository ratingsRepository;

	public TestDbProvider() {
		// repos
		this.disciplinesRepository = new TestDbDisciplinesRepository();
		this.teachersRepository = new TestDbTeachersRepository();
		this.groupsRepository = new TestDbGroupsRepository();
		this.studentsRepository = new TestDbStudentsRepository();
		this.teacherAppointmentsRepository = new TestDbTeacherAppointmentsRepository();
		this.ratingsRepository = new TestDbRatingsRepository();
	}

	@Override
	public DisciplinesRepository disciplinesRepository() {
		return disciplinesRepository;
	}

	@Override
	public TeachersRepository teachersRepository() {
		return teachersRepository;
	}

	@Override
	public GroupsRepository groupsRepository() {
		return groupsRepository;
	}

	@Override
	public StudentsRepository studentsRepository() {
		return studentsRepository;
	}

	@Override
	public TeacherAppointmentsRepository teacherAppointmentsRepository() {
		return teacherAppointmentsRepository;
	}

	@Override
	public RatingsRepository ratingsRepository() {
		return ratingsRepository;
	}

	// До этой парочки возможно и не доберёмся ещё

	@Override
	public UsersRepository usersRepository() {
		throw new NoSuchElementException();
	}

	@Override
	public UserRolesRepository userRolesRepository() {
		throw new NoSuchElementException();
	}
}
