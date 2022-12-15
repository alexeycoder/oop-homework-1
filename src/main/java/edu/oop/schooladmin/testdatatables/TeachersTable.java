package edu.oop.schooladmin.testdatatables;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import edu.oop.schooladmin.model.entities.Teacher;

public class TeachersTable {

	public static ArrayList<Teacher> teachers() {
		return teachers;
	}


	private static final ArrayList<Teacher> teachers = new ArrayList<>(
			Arrays.asList(
					new Teacher(1, "Алиса", "Большакова", LocalDate.of(1978, 1, 29), 9),
					new Teacher(2, "Мирослава", "Акимова", LocalDate.of(1971, 3, 23), 12),
					new Teacher(3, "Артём", "Хромов", LocalDate.of(1982, 8, 13), 9),
					new Teacher(4, "Артём", "Овсянников", LocalDate.of(1975, 11, 17), 10),
					new Teacher(5, "Юлия", "Иванова", LocalDate.of(1970, 1, 26), 13),
					new Teacher(6, "Дарина", "Шишкина", LocalDate.of(1974, 6, 6), 10),
					new Teacher(7, "Евгения", "Иванова", LocalDate.of(1983, 9, 30), 7),
					new Teacher(8, "Тимур", "Горелов", LocalDate.of(1972, 10, 12), 11),
					new Teacher(9, "Марк", "Максимов", LocalDate.of(1977, 6, 16), 10),
					new Teacher(10, "Борис", "Чеботарев", LocalDate.of(1976, 3, 23), 10),
					new Teacher(11, "Мария", "Гаврилова", LocalDate.of(1981, 6, 24), 8),
					new Teacher(12, "Ульяна", "Игнатьева", LocalDate.of(1971, 1, 20), 12)));
}
