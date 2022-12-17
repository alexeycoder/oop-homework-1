package edu.oop.schooladmin.client.viewmodels;

import java.util.List;

import edu.oop.schooladmin.model.entities.Discipline;
import edu.oop.schooladmin.model.entities.Teacher;

public class DisciplineViewModel extends ViewModelBase {
	private final int disciplineId;
	private final String name;
	private final List<String> teachersInfo;

	public DisciplineViewModel(Discipline discipline, List<Teacher> teachers) {
		this.disciplineId = discipline.getDisciplineId();
		this.name = discipline.getName();
		if (teachers == null || teachers.size() == 0) {
			this.teachersInfo = null;
		} else {
			this.teachersInfo = teachers.stream().filter(t -> t != null)
					.map(TeacherViewModel::teacherSimplifiedRepr).toList();
		}
	}

	@Override
	public String toString() {
		String str = String.format("%d.\t%s", disciplineId, name);
		if (teachersInfo != null) {
			str += teachersInfo.size() == 1 ? "\n\t\tНазначенный учитель:" : "\n\tНазначенные учителя:";
			for (String ti : teachersInfo) {
				str += "\n\t\t" + ti;
			}
			str += "\n";
		} else {
			str += "\n\tНазначенные учителя: никого\n";
		}
		return str;
	}

	public static String disciplineSimplifiedRept(Discipline discipline) {
		return String.format("(ID %d) %s", discipline.getDisciplineId(), discipline.getName());
	}
}
