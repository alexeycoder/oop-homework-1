package edu.oop.schooladmin.testdatatablesprevious;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
// import java.util.Arrays;

import edu.oop.schooladmin.model.entities.Teacher;

public class TeachersTable {
	public static void main(String[] args) {
		System.out.println(teachers());
	}
	/**
	* Метод считывания учителей из файла 
	*/
	public static ArrayList<Teacher> teachers() {
		String teacherId, firstName, secondName, date, grade;
		ArrayList<Teacher> teachers = new ArrayList<>();
		try {
			File file = new File("src/main/java/edu/oop/schooladmin/testdatatables/TeachersTable.txt");
			FileReader fr = new FileReader(file); // создаем объект FileReader для объекта File
			BufferedReader reader = new BufferedReader(fr); // создаем BufferedReader с существующего FileReader для
															// построчного считывания
			String line = reader.readLine(); // считаем сначала первую строку

			while (line != null) {
				teacherId = line.split(" ; ")[0];
				firstName = line.split(" ; ")[1];
				secondName = line.split(" ; ")[2];
				date = line.split(" ; ")[3];
				grade = line.split(" ; ")[4];

				teachers.add(new Teacher(Integer.parseInt(teacherId),
						firstName, secondName, LocalDate.parse(date), Integer.parseInt(grade)));

				line = reader.readLine(); // считываем остальные строки в цикле
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return teachers;
	}

	// private static final ArrayList<Teacher> teachers = new ArrayList<>(
	// Arrays.asList(
	// new Teacher(1, "Алиса", "Большакова", LocalDate.of(1978, 1, 29), 9),
	// new Teacher(2, "Мирослава", "Акимова", LocalDate.of(1971, 3, 23), 12),
	// new Teacher(3, "Артём", "Хромов", LocalDate.of(1982, 8, 13), 9),
	// new Teacher(4, "Артём", "Овсянников", LocalDate.of(1975, 11, 17), 10),
	// new Teacher(5, "Юлия", "Иванова", LocalDate.of(1970, 1, 26), 13),
	// new Teacher(6, "Дарина", "Шишкина", LocalDate.of(1974, 6, 6), 10),
	// new Teacher(7, "Евгения", "Иванова", LocalDate.of(1983, 9, 30), 7),
	// new Teacher(8, "Тимур", "Горелов", LocalDate.of(1972, 10, 12), 11),
	// new Teacher(9, "Марк", "Максимов", LocalDate.of(1977, 6, 16), 10),
	// new Teacher(10, "Борис", "Чеботарев", LocalDate.of(1976, 3, 23), 10),
	// new Teacher(11, "Мария", "Гаврилова", LocalDate.of(1981, 6, 24), 8),
	// new Teacher(12, "Ульяна", "Игнатьева", LocalDate.of(1971, 1, 20), 12)));
}
