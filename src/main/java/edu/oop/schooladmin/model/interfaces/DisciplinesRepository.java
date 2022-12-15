package edu.oop.schooladmin.model.interfaces;

import java.util.List;

import edu.oop.schooladmin.model.entities.Discipline;

public interface DisciplinesRepository {

	// none linked repos

	// create

	Discipline addDiscipline(Discipline discipline);

	// read

	Discipline getDisciplineById(int disciplineId);

	Discipline getDisciplineByName(String disciplineName);

	List<Discipline> getAllDisciplines();

	// update

	boolean updateDiscipline(Discipline discipline);

	// delete

	Discipline removeDiscipline(int disciplineId);
}
