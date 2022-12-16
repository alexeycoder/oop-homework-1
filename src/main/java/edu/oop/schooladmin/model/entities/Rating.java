package edu.oop.schooladmin.model.entities;

import java.time.LocalDateTime;

public class Rating {
	private Integer ratingId;
	private Integer studentId;
	private Integer disciplineId;
	private LocalDateTime dateTime;
	private int value;
	private String commentary;

	public Rating() {
	}

	public Rating(Integer ratingId, Integer studentId, Integer disciplineId, LocalDateTime dateTime, int value,
			String commentary) {
		this.ratingId = ratingId;
		this.studentId = studentId;
		this.disciplineId = disciplineId;
		this.dateTime = dateTime;
		this.value = value;
		this.commentary = commentary;
	}

	public Rating(Rating other) {
		this(
				other.ratingId,
				other.studentId,
				other.disciplineId,
				other.dateTime,
				other.value,
				other.commentary);
	}

	public Rating(Integer studentId, Integer disciplineId, LocalDateTime dateTime, int value,
	String commentary) {
		this(
				null,
				studentId,
				disciplineId,
				dateTime,
				value,
				commentary);
	}

	public Integer getRatingId() {
		return ratingId;
	}

	public void setRatingId(Integer ratingId) {
		this.ratingId = ratingId;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getDisciplineId() {
		return disciplineId;
	}

	public void setDisciplineId(Integer disciplineId) {
		this.disciplineId = disciplineId;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getCommentary() {
		return commentary;
	}

	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}

	@Override
	public String toString() {
		return ratingId + ":" + studentId + ":" + disciplineId + ":" + dateTime.toString() + ":" + value + ":" + commentary;
	}
}
