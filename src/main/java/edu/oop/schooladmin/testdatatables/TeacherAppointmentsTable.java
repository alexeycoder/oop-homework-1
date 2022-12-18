package edu.oop.schooladmin.testdatatables;

import java.util.Arrays;
import java.util.List;

import edu.oop.schooladmin.model.entities.TeacherAppointment;

public class TeacherAppointmentsTable extends QueryableBase<TeacherAppointment> {

	protected TeacherAppointmentsTable() {
		super(testData);
	}

	private static TeacherAppointmentsTable instance;

	public static final TeacherAppointmentsTable instance() {
		if (instance == null) {
			instance = new TeacherAppointmentsTable();
		}
		return instance;
	}

	@Override
	protected Integer getEntryId(TeacherAppointment entry) {
		return entry.getAppointmentId();
	}

	@Override
	protected void setEntryId(TeacherAppointment entry, int id) {
		entry.setAppointmentId(id);
	}

	@Override
	protected TeacherAppointment getCopyOf(TeacherAppointment entry) {
		return new TeacherAppointment(entry);
	}

	private static final List<TeacherAppointment> testData = Arrays.asList(
			new TeacherAppointment(1, 1, 1, 91),
			new TeacherAppointment(2, 1, 2, 91),
			new TeacherAppointment(3, 1, 1, 92),
			new TeacherAppointment(5, 1, 2, 92),
			new TeacherAppointment(6, 1, 1, 93),
			new TeacherAppointment(7, 1, 2, 93),
			new TeacherAppointment(8, 2, 3, 91),
			new TeacherAppointment(9, 2, 4, 91),
			new TeacherAppointment(10, 2, 3, 92),
			new TeacherAppointment(11, 2, 4, 92),
			new TeacherAppointment(12, 3, 1, 101),
			new TeacherAppointment(13, 3, 2, 101),
			new TeacherAppointment(14, 3, 1, 102),
			new TeacherAppointment(15, 3, 2, 102),
			new TeacherAppointment(16, 4, 3, 93),
			new TeacherAppointment(17, 4, 4, 93),
			new TeacherAppointment(18, 4, 3, 101),
			new TeacherAppointment(19, 4, 4, 101),
			new TeacherAppointment(20, 4, 3, 102),
			new TeacherAppointment(21, 4, 4, 102),
			new TeacherAppointment(22, 5, 5, 91),
			new TeacherAppointment(23, 5, 5, 92),
			new TeacherAppointment(24, 5, 5, 93),
			new TeacherAppointment(25, 6, 5, 101),
			new TeacherAppointment(26, 6, 5, 102),
			new TeacherAppointment(27, 7, 6, 91),
			new TeacherAppointment(28, 7, 6, 92),
			new TeacherAppointment(29, 8, 6, 93),
			new TeacherAppointment(31, 8, 6, 101),
			new TeacherAppointment(33, 8, 6, 102),
			new TeacherAppointment(34, 9, 7, 91),
			new TeacherAppointment(35, 10, 7, 92),
			new TeacherAppointment(36, 10, 7, 93),
			new TeacherAppointment(37, 9, 7, 101),
			new TeacherAppointment(39, 10, 7, 102));

}

/*
 * КодНазначения КодУчителя КодПредмета КодГруппы
 * 1 1 1 91
 * 2 1 2 91
 * 3 1 1 92
 * 5 1 2 92
 * 6 1 1 93
 * 7 1 2 93
 * 8 2 3 91
 * 9 2 4 91
 * 10 2 3 92
 * 11 2 4 92
 * 12 3 1 101
 * 13 3 2 101
 * 14 3 1 102
 * 15 3 2 102
 * 16 4 3 93
 * 17 4 4 93
 * 18 4 3 101
 * 19 4 4 101
 * 20 4 3 102
 * 21 4 4 102
 * 22 5 5 91
 * 23 5 5 92
 * 24 5 5 93
 * 25 6 5 101
 * 26 6 5 102
 * 27 7 6 91
 * 28 7 6 92
 * 29 8 6 93
 * 31 8 6 101
 * 33 8 6 102
 * 34 9 7 91
 * 35 10 7 92
 * 36 10 7 93
 * 37 9 7 101
 * 39 10 7 102
 * 
 */