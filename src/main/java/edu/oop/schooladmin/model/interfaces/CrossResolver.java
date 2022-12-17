package edu.oop.schooladmin.model.interfaces;

import java.util.List;

import edu.oop.schooladmin.model.entities.Teacher;

public interface CrossResolver {
	List<Teacher> getTeachersByDisciplineId(int disciplineId);
}
