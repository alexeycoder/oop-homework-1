package edu.oop.schooladmin.testdatatables;

import java.util.Arrays;
import java.util.List;

import edu.oop.schooladmin.model.entities.Discipline;

public class DisciplinesTable extends QueryableBase<Discipline> {

	protected DisciplinesTable() {
		super(testData);
	}

	private static DisciplinesTable instance;

	public static final DisciplinesTable instance() {
		if (instance == null) {
			instance = new DisciplinesTable();
		}
		return instance;
	}

	@Override
	protected Integer getEntryId(Discipline entry) {
		return entry.getDisciplineId();
	}

	@Override
	protected void setEntryId(Discipline entry, int id) {
		entry.setDisciplineId(id);
	}

	@Override
	protected Discipline getCopyOf(Discipline entry) {
		return new Discipline(entry);
	}

	private static final List<Discipline> testData = Arrays.asList(
			new Discipline(1, "Алгебра"),
			new Discipline(2, "Геометрия"),
			new Discipline(3, "Русский язык"),
			new Discipline(4, "Литература"),
			new Discipline(5, "Физика"),
			new Discipline(6, "Химия"),
			new Discipline(7, "Информатика"));
}
