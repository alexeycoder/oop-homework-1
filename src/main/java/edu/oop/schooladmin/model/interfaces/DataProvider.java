package edu.oop.schooladmin.model.interfaces;

public interface DataProvider {

	DisciplinesRepository disciplinesRepository();

	TeachersRepository teachersRepository();

	GroupsRepository groupsRepository();

	StudentsRepository studentsRepository();

	TeacherAppointmentsRepository teacherAppointmentsRepository();

	RatingsRepository ratingsRepository();

	UsersRepository usersRepository();

	UserRolesRepository userRolesRepository();
}
