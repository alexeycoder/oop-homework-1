package edu.oop.schooladmin.client.viewmodels;

import java.time.LocalDateTime;

import edu.oop.schooladmin.model.entities.Discipline;
import edu.oop.schooladmin.model.entities.Rating;
import edu.oop.schooladmin.model.entities.Student;

public class RatingViewModel extends ViewModelBase {
	private final int ratingId;
	private final LocalDateTime dateTime;
	private final int value;
	private final String studentInfo;
	private final String disciplineInfo;
	private final String commentary;

	public RatingViewModel(Rating rating, Student student, Discipline discipline) {
		this.ratingId = rating.getRatingId();
		this.dateTime = rating.getDateTime();
		this.value = rating.getValue();
		this.commentary = rating.getCommentary();
		if (student != null) {
			this.studentInfo = StudentViewModel.studentSimplifiedRepr(student);
		} else {
			this.studentInfo = null;
		}
		if (discipline != null) {
			this.disciplineInfo = DisciplineViewModel.disciplineSimplifiedRept(discipline);
		} else {
			this.disciplineInfo = null;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(String.format("%d. %s Оценка: '%d'", ratingId, dateTime, value));
		if (disciplineInfo != null) {
			sb.append("\tПредмет: ").append(String.format("%-15s", disciplineInfo));
		}
		if (studentInfo != null) {
			sb.append("\tУченик: ").append(studentInfo);
		}
		if (commentary != null && !commentary.isBlank()) {
			sb.append("\tКомментарий: ").append(commentary);
		}
		return sb.toString();
	}
}
