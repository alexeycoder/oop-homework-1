package edu.oop.schooladmin.testdatatablesprevious;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import edu.oop.schooladmin.model.entities.TeacherAppointment;

public class TeacherAppointmentsTable {

	// public static void main(String[] args) {
	// 	// ПРОВЕРКА
	// 	System.out.println(appointments());
	// }
	/**
	 * Метод считывания назначения учителя из файла
	*/
	public static ArrayList<TeacherAppointment> appointments() {
		ArrayList<TeacherAppointment> appointments = new ArrayList<>();
		String appointmentId, teacher, disciplineId, groupId;
		try {
			File file = new File("src/main/java/edu/oop/schooladmin/testdatatables/TeacherAppointmentsTable.txt");
			// создаем объект FileReader для объекта File
			FileReader fr = new FileReader(file);
			// создаем BufferedReader с существующего FileReader для построчного считывания
			BufferedReader reader = new BufferedReader(fr);
			// считаем сначала первую строку
			String line = reader.readLine();
			while (line != null) {
				appointmentId = line.split(";")[0];
				teacher = line.split(";")[1];
				disciplineId = line.split(";")[2];
				groupId = line.split(";")[3];
				appointments.add(new TeacherAppointment(Integer.parseInt(appointmentId), Integer.parseInt(teacher),
						Integer.parseInt(disciplineId), Integer.parseInt(groupId)));
				// считываем остальные строки в цикле
				line = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return appointments;
	}
}

// private static final ArrayList<TeacherAppointment> Appointments = new
// ArrayList<>(
// Arrays.asList(
// new TeacherAppointment(1, 1, 1, 91),
// new TeacherAppointment(2, 1, 2, 91),
// new TeacherAppointment(3, 1, 1, 92),
// new TeacherAppointment(5, 1, 2, 92),
// new TeacherAppointment(6, 1, 1, 93),
// new TeacherAppointment(7, 1, 2, 93),
// new TeacherAppointment(8, 2, 3, 91),
// new TeacherAppointment(9, 2, 4, 91),
// new TeacherAppointment(10, 2, 3, 92),
// new TeacherAppointment(11, 2, 4, 92),
// new TeacherAppointment(12, 3, 1, 101),
// new TeacherAppointment(13, 3, 2, 101),
// new TeacherAppointment(14, 3, 1, 102),
// new TeacherAppointment(15, 3, 2, 102),
// new TeacherAppointment(16, 4, 3, 93),
// new TeacherAppointment(17, 4, 4, 93),
// new TeacherAppointment(18, 4, 3, 101),
// new TeacherAppointment(19, 4, 4, 101),
// new TeacherAppointment(20, 4, 3, 102),
// new TeacherAppointment(21, 4, 4, 102),
// new TeacherAppointment(22, 5, 5, 91),
// new TeacherAppointment(23, 5, 5, 92),
// new TeacherAppointment(24, 5, 5, 93),
// new TeacherAppointment(25, 6, 5, 101),
// new TeacherAppointment(26, 6, 5, 102),
// new TeacherAppointment(27, 7, 6, 91),
// new TeacherAppointment(28, 7, 6, 92),
// new TeacherAppointment(29, 8, 6, 93),
// new TeacherAppointment(31, 8, 6, 101),
// new TeacherAppointment(33, 8, 6, 102),
// new TeacherAppointment(34, 9, 7, 91),
// new TeacherAppointment(35, 10, 7, 92),
// new TeacherAppointment(36, 10, 7, 93),
// new TeacherAppointment(37, 9, 7, 101),
// new TeacherAppointment(39, 10, 7, 102)));
// }

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