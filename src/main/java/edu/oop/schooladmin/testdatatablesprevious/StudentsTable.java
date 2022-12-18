package edu.oop.schooladmin.testdatatablesprevious;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
// import java.util.Arrays;

import edu.oop.schooladmin.model.entities.Student;

public class StudentsTable {
	// public static void main(String[] args) {
	// 	System.out.println(students());
	// }
	/**
	 * Метод считывания студентов из файла
	 */
	public static ArrayList<Student> students() {
		String studentId, firstName, secondName, date, groupId;
		ArrayList<Student> students = new ArrayList<>();
		try {
			File file = new File("src/main/java/edu/oop/schooladmin/testdatatables/StudentsTable.txt");
			FileReader fr = new FileReader(file); // создаем объект FileReader для объекта File
			BufferedReader reader = new BufferedReader(fr); // создаем BufferedReader с существующего FileReader для
															// построчного считывания
			String line = reader.readLine(); // считаем сначала первую строку

			while (line != null) {
				studentId = line.split(" ; ")[0];
				firstName = line.split(" ; ")[1];
				secondName = line.split(" ; ")[2];
				date = line.split(" ; ")[3];
				groupId = line.split(" ; ")[4];

				students.add(new Student(Integer.parseInt(studentId),
						firstName, secondName,LocalDate.parse(date), Integer.parseInt(groupId)));

				line = reader.readLine(); // считываем остальные строки в цикле
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return students;
	}

	// private static final ArrayList<Student> students = new ArrayList<>(
	// 		Arrays.asList(
	// 				new Student(1, "Яросла", "Соколов", LocalDate.of(1997, 10, 14), 91),
	// 				new Student(2, "Дмитри", "Андреев", LocalDate.of(1997, 01, 8), 91),
	// 				new Student(3, "Владим", "Сотников", LocalDate.of(1997, 12, 20), 91),
	// 				new Student(4, "Екатер", "Павлова", LocalDate.of(1997, 07, 30), 91),
	// 				new Student(5, "Екатер", "Калашникова", LocalDate.of(1997, 06, 12), 91),
	// 				new Student(6, "Павел", "Басов", LocalDate.of(1997, 9, 12), 92),
	// 				new Student(7, "Максим", "Козлов", LocalDate.of(1997, 11, 20), 92),
	// 				new Student(8, "Александр", "Рябинин", LocalDate.of(1997, 2, 15), 92),
	// 				new Student(9, "София", "Федорова", LocalDate.of(1997, 10, 4), 92),
	// 				new Student(10, "Элина", "Шарова", LocalDate.of(1997, 05, 17), 92),
	// 				new Student(11, "Дарья", "Сергеева", LocalDate.of(1997, 02, 24), 93),
	// 				new Student(12, "Анна", "Семенова", LocalDate.of(1997, 6, 01), 93),
	// 				new Student(13, "Василиса", "Макарова", LocalDate.of(1997, 12, 3), 93),
	// 				new Student(14, "Василиса", "Моисеева", LocalDate.of(1997, 12, 8), 93),
	// 				new Student(15, "Егор", "Глушков", LocalDate.of(1997, 10, 06), 93),
	// 				new Student(16, "Елизавета", "Михайлова", LocalDate.of(1996, 10, 11), 101),
	// 				new Student(17, "Михаил", "Исаев", LocalDate.of(1996, 10, 21), 101),
	// 				new Student(18, "Семён", "Цветков", LocalDate.of(1996, 5, 14), 101),
	// 				new Student(19, "Лидия", "Калинина", LocalDate.of(1996, 9, 7), 101),
	// 				new Student(20, "Майя", "Завьялова", LocalDate.of(1996, 5, 31), 101),
	// 				new Student(21, "Ульяна", "Чернова", LocalDate.of(1996, 9, 2), 102),
	// 				new Student(22, "Олеся", "Маркина", LocalDate.of(1996, 2, 27), 102),
	// 				new Student(23, "Марк", "Дроздов", LocalDate.of(1996, 10, 1), 102),
	// 				new Student(24, "Диана", "Фролова", LocalDate.of(1996, 1, 4), 102),
	// 				new Student(25, "Алёна", "Глушкова", LocalDate.of(1996, 5, 30), 102)));
}

/*
 * id Фамилия Имя ДатаРождения КодГруппы
 * 1 Соколов Ярослав 14.10.97 91
 * 2 Андреев Дмитрий 08.01.97 91
 * 3 Сотников Владимир 20.12.97 91
 * 4 Павлова Екатерина 30.07.97 91
 * 5 Калашникова Екатерина 12.06.97 91
 * 6 Басов Павел 12.09.97 92
 * 7 Козлов Максим 20.11.97 92
 * 8 Рябинин Александр 15.02.97 92
 * 9 Федорова София 04.10.97 92
 * 10 Шарова Элина 17.05.97 92
 * 11 Сергеева Дарья 24.02.97 93
 * 12 Семенова Анна 01.06.97 93
 * 13 Макарова Василиса 03.12.97 93
 * 14 Моисеева Василиса 08.12.97 93
 * 15 Глушков Егор 06.10.97 93
 * 16 Михайлова Елизавета 11.10.96 101
 * 17 Исаев Михаил 21.10.96 101
 * 18 Цветков Семён 14.05.96 101
 * 19 Калинина Лидия 07.09.96 101
 * 20 Завьялова Майя 31.05.96 101
 * 21 Чернова Ульяна 02.09.96 102
 * 22 Маркина Олеся 27.02.96 102
 * 23 Дроздов Марк 01.10.96 102
 * 24 Фролова Диана 04.01.96 102
 * 25 Глушкова Алёна 30.05.96 102
 */