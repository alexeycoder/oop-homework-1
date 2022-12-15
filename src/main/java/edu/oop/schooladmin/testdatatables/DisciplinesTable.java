package edu.oop.schooladmin.testdatatables;

import java.util.ArrayList;
import java.util.Arrays;

import edu.oop.schooladmin.model.entities.Discipline;

public class DisciplinesTable {

	public static ArrayList<Discipline> disciplines() {
		return disciplines;
	}

	private static final ArrayList<Discipline> disciplines = new ArrayList<>(
			Arrays.asList(
					new Discipline(1, "Алгебра"),
					new Discipline(2, "Геометрия"),
					new Discipline(3, "Русский язык"),
					new Discipline(4, "Литература"),
					new Discipline(5, "Физика"),
					new Discipline(6, "Химия"),
					new Discipline(7, "Информатика")));
}
