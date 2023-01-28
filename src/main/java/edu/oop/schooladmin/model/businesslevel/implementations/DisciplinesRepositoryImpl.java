package edu.oop.schooladmin.model.businesslevel.implementations;

import java.security.InvalidParameterException;
import java.util.List;

import edu.oop.schooladmin.model.businesslevel.interfaces.DisciplinesRepository;
import edu.oop.schooladmin.model.dblayer.interfaces.Queryable;
import edu.oop.schooladmin.model.entities.Discipline;

public class DisciplinesRepositoryImpl implements DisciplinesRepository {

	private final Queryable<Discipline> disciplines;

	public DisciplinesRepositoryImpl(Queryable<Discipline> disciplines) {
		this.disciplines = disciplines;
	}

	@Override
	public Discipline addDiscipline(Discipline discipline) {
		basicCheck(discipline);

		disciplines.add(discipline);
		return discipline;
	}

	@Override
	public Discipline getDisciplineById(int disciplineId) {
		return disciplines.get(disciplineId);
	}

	@Override
	public Discipline getDisciplineByName(String disciplineName) {
		if (disciplineName == null) {
			throw new NullPointerException("disciplineName");
		}
		if (disciplineName.isBlank()) {
			throw new InvalidParameterException("disciplineName");
		}
		final String dName = disciplineName.toLowerCase();
		var dbEntity = disciplines.queryAll().filter(d -> d.getName().toLowerCase().contains(dName)).findFirst();
		if (dbEntity.isPresent()) {
			return new Discipline(dbEntity.get());
		}
		return null;
	}

	@Override
	public boolean updateDiscipline(Discipline discipline) {
		basicCheck(discipline);

		return disciplines.update(discipline);
	}

	@Override
	public List<Discipline> getAllDisciplines() {
		return disciplines.queryAll().toList();
	}

	@Override
	public boolean removeDiscipline(int disciplineId) {
		return disciplines.delete(disciplineId) != null;
	}

	// aux:

	private void basicCheck(Discipline discipline) {
		if (discipline == null) {
			throw new NullPointerException("discipline");
		}
		if (discipline.getName() == null) {
			throw new NullPointerException("discipline.name");
		}
		if (discipline.getName().isBlank()) {
			throw new InvalidParameterException("discipline.name");
		}
	}
}
