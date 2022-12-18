package edu.oop.schooladmin.model.implementations.testdb;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import edu.oop.schooladmin.model.entities.Rating;
import edu.oop.schooladmin.model.interfaces.RatingsRepository;
import edu.oop.schooladmin.testdatatablesprevious.RatingsTable;

public class TestDbRatingsRepository implements RatingsRepository {

    private final ArrayList<Rating> ratings;
    private int lastId;

    public int getLastId() {
        return lastId;
    }


    public TestDbRatingsRepository() {
        ratings = RatingsTable.ratings();
        lastId = RepositoryUtils.getLastPrimaryKey(ratings, r -> r.getRatingId());
    }

    @Override
    public Rating addRating(Rating rating) {
        rating.setRatingId(++lastId);

        var addedEntity = new Rating(rating);
        ratings.add(addedEntity);

        return rating;
    }

    @Override
    public Rating getRatingById(int ratingId) {
        var dbEntity = ratings.stream().filter(r -> r.getRatingId().equals(ratingId)).findFirst();
        if (dbEntity.isPresent()) {
            return new Rating(dbEntity.get());
        }
        return null;
    }

    @Override
    public List<Rating> getRatingsByStudentId(int studentId) {
        List<Rating> resultList = new ArrayList<>();
        for (Rating rating : ratings) {
            if (rating.getStudentId().equals(studentId)) {
                resultList.add(new Rating(rating));
            }
        }
        return resultList;
    }

    @Override
    public List<Rating> getRatingsByDisciplineId(int disciplineId) {
        List<Rating> resultList = new ArrayList<>();
        for (Rating rating : ratings) {
            if (rating.getDisciplineId().equals(disciplineId)) {
                resultList.add(new Rating(rating));
            }
        }
        return resultList;
    }

    @Override
    public List<Rating> getRatingsByDateTime(LocalDateTime from, LocalDateTime to) {
        List<Rating> resultList = new ArrayList<>();
        for (Rating rating : ratings) {
            if (rating.getDateTime().isAfter(from) && rating.getDateTime().isBefore(to)) {
                resultList.add(new Rating(rating));
            }
        }
        return resultList;
    }

    @Override
    public List<Rating> getRatingsByValue(int value) {
        List<Rating> resultList = new ArrayList<>();
        for (Rating rating : ratings) {
            if (rating.getValue() == value) {
                resultList.add(new Rating(rating));
            }
        }
        return resultList;
    }

    @Override
    public boolean updateRating(Rating rating) {
        Integer index = null;
        for (int i = 0; i < ratings.size(); i++) {
            if (ratings.get(i).getRatingId().equals(rating.getRatingId())) {
                index = i;
                break;
            }
        }
        if (index != null) {
            ratings.set(index.intValue(), new Rating(rating));
            return true;
        } else
            return false;
    }

    @Override
    public boolean removeRating(int ratingId) {
        Rating dbEntityToRemove = null;
        Integer index = null;
        for (int i = 0; i < ratings.size(); i++) {
            var dbEntity = ratings.get(i);
            if (dbEntity.getRatingId().equals(ratingId)) {
                dbEntityToRemove = dbEntity;
                index = i;
                break;
            }
        }
        if (dbEntityToRemove != null) {
            ratings.remove(index.intValue());
            return true;
        } else
            return false;
    }
    
}
