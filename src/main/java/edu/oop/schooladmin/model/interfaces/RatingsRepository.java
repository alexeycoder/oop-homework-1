package edu.oop.schooladmin.model.interfaces;

import java.time.LocalDateTime;
import java.util.List;

import edu.oop.schooladmin.model.entities.Rating;

public interface RatingsRepository {

	// create

	Rating addRating(Rating rating);

	// read

	Rating getRatingById(int ratingId);

	List<Rating> getAllRatings();

	List<Rating> getRatingsByStudentId(int studentId);

	List<Rating> getRatingsByDisciplineId(int disciplineId);

	List<Rating> getRatingsByDateTime(LocalDateTime from, LocalDateTime to);

	List<Rating> getRatingsByValue(int from, int to);

	// update

	boolean updateRating(Rating rating);

	// delete

	boolean removeRating(int ratingId);
}
