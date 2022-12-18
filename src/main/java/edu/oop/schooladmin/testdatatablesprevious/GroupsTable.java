package edu.oop.schooladmin.testdatatablesprevious;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
// import java.util.Arrays;

import edu.oop.schooladmin.model.entities.Group;

public class GroupsTable {
	// public static void main(String[] args) {
	// 	System.out.println(groups());
	// }
		/**
		 * Метод считывания групп из файла
		 */
	public static ArrayList<Group> groups() {
		String groupId, classYear, classMark, teacherId;
		ArrayList<Group> groups = new ArrayList<>();
		try {
			File file = new File("src/main/java/edu/oop/schooladmin/testdatatables/GroupsTable.txt");
			FileReader fr = new FileReader(file); // создаем объект FileReader для объекта File
			BufferedReader reader = new BufferedReader(fr); // создаем BufferedReader с существующего FileReader для построчного считывания
			String line = reader.readLine(); // считаем сначала первую строку
			while (line != null) {
				groupId = line.split(";")[0];
				classYear = line.split(";")[1];
				classMark = line.split(";")[2];
				teacherId = line.split(";")[3];

				groups.add(new Group(Integer.parseInt(groupId), Integer.parseInt(classYear), classMark.charAt(0),
						Integer.parseInt(teacherId)));
				
				line = reader.readLine(); // считываем остальные строки в цикле
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return groups;
	}

	// private static final ArrayList<Group> groups = new ArrayList<>(Arrays.asList(
	// new Group(91, 9, 'А', 2),
	// new Group(92, 9, 'Б', 5),
	// new Group(93, 9, 'В', 8),
	// new Group(101, 11, 'А', 4),
	// new Group(102, 11, 'Б', 5)));
}
