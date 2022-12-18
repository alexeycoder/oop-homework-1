// package edu.oop.schooladmin.model.implementations.testdb;

// import java.util.List;

// import edu.oop.schooladmin.model.entities.Group;
// import edu.oop.schooladmin.model.entities.Teacher;
// import edu.oop.schooladmin.model.interfaces.GroupsRepository;
// import edu.oop.schooladmin.model.interfaces.TeacherAppointmentsRepository;
// import edu.oop.schooladmin.model.interfaces.TeachersRepository;
// import edu.oop.schooladmin.model.interfaces.TeachersService;

// public class TestDbTeachersService implements TeachersService {

// 	private final TeachersRepository teachersRepository;
// 	private final TeacherAppointmentsRepository teacherAppointmentsRepository;
// 	private final GroupsRepository groupsRepository;

// 	public TestDbTeachersService(TeachersRepository teachersRepository,
// 			TeacherAppointmentsRepository teacherAppointmentsRepository, GroupsRepository groupsRepository) {
// 		this.teachersRepository = teachersRepository;
// 		this.teacherAppointmentsRepository = teacherAppointmentsRepository;
// 		this.groupsRepository = groupsRepository;
// 	}

// 	@Override
// 	public List<Teacher> getTeachersByDisciplineId(int disciplineId) {
// 		var appointments = teacherAppointmentsRepository
// 				.getTeacherAppointmentsByDisciplineId(disciplineId);
// 		var teachers = appointments.stream().mapToInt(a -> a.getTeacherId()).distinct().boxed()
// 				.map(i -> teachersRepository.getTeacherById(i)).toList();
// 		return teachers;
// 	}
// }
