package edu.oop.schooladmin.testdatatablesprevious;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
// import java.util.Arrays;

import edu.oop.schooladmin.model.entities.Discipline;

public class DisciplinesTable {
	// public static void main(String[] args) {
	// 	System.out.println(disciplines());
	// }
	/**
	 * Метод считывания дисциплин из файла
	 */
	public static ArrayList<Discipline> disciplines() {
		String disciplineId, name;
		ArrayList<Discipline> disciplines = new ArrayList<>();
		try {
			File file = new File("src/main/java/edu/oop/schooladmin/testdatatables/DisciplinesTable.txt");
			FileReader fr = new FileReader(file); // создаем объект FileReader для объекта File
			BufferedReader reader = new BufferedReader(fr); // создаем BufferedReader с существующего FileReader для построчного считывания
			// считаем сначала первую строку
			String line = reader.readLine();
			while (line != null) {
				disciplineId = line.split(";")[0];
				name = line.split(";")[1];

				disciplines.add(new Discipline(Integer.parseInt(disciplineId), name));
				
				line = reader.readLine(); // считываем остальные строки в цикле
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return disciplines;
	}

	// private static final ArrayList<Discipline> disciplines = new ArrayList<>(
	// 		Arrays.asList(
	// 				new Discipline(1, "Алгебра"),
	// 				new Discipline(2, "Геометрия"),
	// 				new Discipline(3, "Русский язык"),
	// 				new Discipline(4, "Литература"),
	// 				new Discipline(5, "Физика"),
	// 				new Discipline(6, "Химия"),
	// 				new Discipline(7, "Информатика")));
}
