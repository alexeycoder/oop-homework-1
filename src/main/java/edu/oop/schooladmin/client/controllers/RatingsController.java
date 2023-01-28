package edu.oop.schooladmin.client.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.OptionalInt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.oop.schooladmin.client.controllers.MainController.ControllersBag;
import edu.oop.schooladmin.client.viewmodels.Commons;
import edu.oop.schooladmin.client.viewmodels.RatingViewModel;
import edu.oop.schooladmin.client.viewmodels.StudentViewModel;
import edu.oop.schooladmin.client.views.ViewBase;
import edu.oop.schooladmin.model.businesslevel.interfaces.DataProvider;
import edu.oop.schooladmin.model.entities.Discipline;
import edu.oop.schooladmin.model.entities.Rating;
import edu.oop.schooladmin.model.entities.Student;

public class RatingsController extends ControllerBase {

	private static final Logger logger = LoggerFactory.getLogger(RatingsController.class);
	// private final ControllersBag controllersBag;

	public RatingsController(DataProvider dataProvider, ViewBase viewManager, ControllersBag controllersBag) {
		super(dataProvider, viewManager);
		if (controllersBag == null) {
			throw new NullPointerException("controllersBag");
		}
		// this.controllersBag = controllersBag;
		logger.trace("Controller instance successfully created.");
	}

	@Override
	protected Map<Object, String> getMenuModel() {
		return Commons.RATINGS_MENU;
	}

	@Override
	protected boolean switchToAction(int menuId, Object relatedEntity) {
		switch (menuId) {
			case 1 -> showAll();
			case 2 -> showByStudent(relatedEntity instanceof Student student ? student : null);
			case 3 -> addNew(relatedEntity instanceof Student student ? student : null);
			// case 4 -> edit(relatedEntity instanceof Student student ? student : null);
			case 4 -> delete(relatedEntity instanceof Rating rating ? rating : null);
			default -> throw new NoSuchElementException();
		}
		return false;
	}

	private void showAll() {
		var ratingsRepo = dp.ratingsRepository();
		var studentsRepo = dp.studentsRepository();
		var disciplinesRepo = dp.disciplinesRepository();
		List<RatingViewModel> viewModelsList = ratingsRepo.getAllRatings().stream()
				.map(r -> new RatingViewModel(
						r,
						studentsRepo.getStudentById(r.getStudentId()),
						disciplinesRepo.getDisciplineById(r.getDisciplineId())))
				.toList();

		view.clear();
		view.showList(viewModelsList, "ЖУРНАЛ ОЦЕНОК");
		view.waitToProceed();
	}

	private void showByStudent(Student student) {
		view.clear();
		view.showText("ЖУРНАЛ ОЦЕНОК");
		boolean askMore = false;
		do {
			if (student == null) {
				askMore = true;
				view.showEmpty();
				student = askStudent();
				if (student == null) {
					break;
				}
			}
			view.showEmpty();
			view.showText("Ученик " + StudentViewModel.studentSimplifiedRepr(student));

			var ratingsRepo = dp.ratingsRepository();
			var disciplinesRepo = dp.disciplinesRepository();
			final Integer studentId = student.getStudentId();
			List<RatingViewModel> viewModelsList = ratingsRepo.getAllRatings().stream()
					.filter(r -> r.getStudentId() != null && r.getStudentId().equals(studentId))
					.map(r -> new RatingViewModel(
							r,
							null,
							disciplinesRepo.getDisciplineById(r.getDisciplineId())))
					.toList();

			view.showList(viewModelsList, null);

			student = null;

		} while (askMore && view.askYesNo("Показать оценки другого ученика? (Д/н)", true));

		view.waitToProceed();
	}

	private void addNew(Student student) {
		view.clear();
		view.showText("ДОБАВЛЕНИЕ ОЦЕНКИ В ЖУРНАЛ");

		boolean askMore = false;
		if (student == null) {
			askMore = true;
			student = askStudent();
			if (student == null) {
				view.showText("Добавление отменено.");
				view.waitToProceed();
				return;
			}
		}

		view.showEmpty();
		view.showText("Ученик " + StudentViewModel.studentSimplifiedRepr(student));

		boolean cancelled = false;
		do {
			view.showEmpty();
			OptionalInt ratingValue = view.askInteger("Введите оценку (пустой Ввод чтобы отменить): ", 2, 5);
			if (ratingValue.isEmpty()) {
				cancelled = true;
				break;
			}

			Discipline discipline = askDiscipline();
			if (discipline == null) {
				cancelled = true;
				break;
			}
			var optComment = view.askString("Введите комментарий: ", null, null);
			if (optComment.isEmpty()) {
				cancelled = true;
				break;
			}
			Rating rating = new Rating(student.getStudentId(), discipline.getDisciplineId(), LocalDateTime.now(),
					ratingValue.getAsInt(), optComment.get());

			Rating addedRating = dp.ratingsRepository().addRating(rating);
			if (addedRating != null) {
				view.showEmpty();
				view.showList(List.of(new RatingViewModel(addedRating, student, discipline)),
						"Оценка успешно добавлена:");
				logger.info("New rating '{}' has been added.", addedRating);
			} else {
				logger.warn("Something went wrong when adding a new rating '{}' for '{}' to '{}'.",
						ratingValue, discipline.getName(), student);
				view.showText("Что-то пошло не так. Оценка не была добавлена.");
			}

		} while (askMore && view.askYesNo("Добавить ещё? (Д/н)", true));

		if (cancelled) {
			view.showText("Добавление отменено.");
			view.waitToProceed();
		}
	}

	private void delete(Rating rating) {
		view.clear();
		view.showText("УДАЛЕНИЕ ОЦЕНКИ");
		boolean cancelled = false;
		do {
			if (rating == null) {
				rating = askRating();
				if (rating == null) {
					cancelled = true;
					break;
				}
			}

			var studentsRepo = dp.studentsRepository();
			var disciplinesRepo = dp.disciplinesRepository();
			var viewModel = new RatingViewModel(rating,
					rating.getStudentId() != null ? studentsRepo.getStudentById(rating.getStudentId()) : null,
					rating.getDisciplineId() != null
							? disciplinesRepo.getDisciplineById(rating.getDisciplineId())
							: null);
			view.showList(List.of(viewModel), null);

			if (view.askYesNo("Удалить оценку? (д/Н)", false)) {
				if (dp.ratingsRepository().removeRating(rating.getRatingId())) {
					view.showText("Оценка успешно удалена.");
					view.showEmpty();
					logger.info("Rating '{}' has been deleted.", rating);
				} else {
					logger.warn("Something went wrong when deleting rating '{}'.", rating);
					view.showText("Что-то пошло не так. Оценку удалить не удалось.");
				}
			} else {
				view.showText("Удаление отменено.");
			}

			rating = null;

		} while (view.askYesNo("Удалить ещё? (Д/н)", true));

		if (cancelled) {
			view.showText("Удаление отменено.");
		}
		view.waitToProceed();
	}
}
