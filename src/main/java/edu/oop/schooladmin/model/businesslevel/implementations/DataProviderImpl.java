package edu.oop.schooladmin.model.businesslevel.implementations;

import java.util.NoSuchElementException;

import edu.oop.schooladmin.model.businesslevel.interfaces.DataProvider;
import edu.oop.schooladmin.model.businesslevel.interfaces.DisciplinesRepository;
import edu.oop.schooladmin.model.businesslevel.interfaces.GroupsRepository;
import edu.oop.schooladmin.model.businesslevel.interfaces.RatingsRepository;
import edu.oop.schooladmin.model.businesslevel.interfaces.StudentsRepository;
import edu.oop.schooladmin.model.businesslevel.interfaces.TeacherAppointmentsRepository;
import edu.oop.schooladmin.model.businesslevel.interfaces.TeachersRepository;
import edu.oop.schooladmin.model.businesslevel.interfaces.UserRolesRepository;
import edu.oop.schooladmin.model.businesslevel.interfaces.UsersRepository;
import edu.oop.schooladmin.model.dblayer.interfaces.DbLayerContext;

public class DataProviderImpl implements DataProvider {

	private final DisciplinesRepository disciplinesRepository;
	private final TeachersRepository teachersRepository;
	private final GroupsRepository groupsRepository;
	private final StudentsRepository studentsRepository;
	private final TeacherAppointmentsRepository teacherAppointmentsRepository;
	private final RatingsRepository ratingsRepository;

	public DataProviderImpl(DbLayerContext dbContext) {
		// repos
		this.disciplinesRepository = new DisciplinesRepositoryImpl(dbContext.disciplines());
		this.teachersRepository = new TeachersRepositoryImpl(
				dbContext.teachers(),
				dbContext.teacherAppointments(),
				dbContext.groups());
		this.groupsRepository = new GroupsRepositoryImpl(
				dbContext.groups(),
				dbContext.teachers(),
				dbContext.students(),
				dbContext.teacherAppointments());
		this.studentsRepository = new StudentsRepositoryImpl(
				dbContext.students(),
				dbContext.groups(),
				dbContext.ratings());
		this.teacherAppointmentsRepository = new TeacherAppointmentsRepositoryImpl(
				dbContext.teacherAppointments(),
				dbContext.teachers(),
				dbContext.disciplines(),
				dbContext.groups());
		this.ratingsRepository = new RatingsRepositoryImpl(
				dbContext.ratings(),
				dbContext.students(),
				dbContext.disciplines());
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
