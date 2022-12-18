package edu.oop.schooladmin.client.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import edu.oop.schooladmin.client.controllers.MainController.ControllersBag;
import edu.oop.schooladmin.client.viewmodels.Commons;
import edu.oop.schooladmin.client.viewmodels.StudentViewModel;
import edu.oop.schooladmin.client.views.ViewBase;
import edu.oop.schooladmin.model.entities.Group;
import edu.oop.schooladmin.model.entities.Student;
import edu.oop.schooladmin.model.interfaces.DataProvider;

public class StudentsController extends ControllerBase {

	private final ControllersBag controllersBag;

	public StudentsController(DataProvider dataProvider, ViewBase viewManager, ControllersBag controllersBag) {
		super(dataProvider, viewManager);
		if (controllersBag == null) {
			throw new NullPointerException("controllersBag");
		}
		this.controllersBag = controllersBag;
	}

	@Override
	protected Map<Object, String> getMenuModel() {
		return Commons.STUDENTS_MENU;
	}

	@Override
	protected void switchToAction(int menuId, Object relatedEntity) {
		switch (menuId) {
			case 1 -> showAll();
			case 2 -> showByGroup(relatedEntity instanceof Group group ? group : null);
			case 3 -> showOneById(relatedEntity instanceof Student student ? student : null);
			case 4 -> showAllByName();
			case 5 -> addNew();
			case 6 -> dummyAction();
			case 7 -> dummyAction();
			default -> throw new NoSuchElementException();
		}
	}

	private void showAll() {
		ArrayList<StudentViewModel> resultList = new ArrayList<>();

		var studentsRepo = dp.studentsRepository();
		var groupsRepo = dp.groupsRepository();
		for (var student : studentsRepo.getAllStudents()) {
			var group = groupsRepo.getGroupById(student.getGroupId());
			resultList.add(new StudentViewModel(student, group));
		}
		view.clear();
		view.showList(resultList, "СПИСОК УЧЕНИКОВ:");
		view.waitToProceed();
	}

	private void showOneById(Student student) {
		view.clear();
		view.showText("ПОИСК УЧЕНИКА ПО ID");
		do {
			if (student == null) {
				student = askStudent();
				if (student == null) {
					return;
				}
			}

			var groupsRepo = dp.groupsRepository();
			var group = groupsRepo.getGroupById(student.getGroupId());
			StudentViewModel viewModel = new StudentViewModel(student, group);
			view.showList(List.of(viewModel), "Найдена запись:");

			if (view.askYesNo("Показать оценки ученика? (Y/n)", true)) {
				var possibleMenuId = findSuitableMenuId(Commons.RATINGS_MENU, "ученик");
				if (possibleMenuId.isPresent()) {
					var ratingsController = controllersBag.ratingsController();
					ratingsController.switchToAction(possibleMenuId.getAsInt(), student);
				} else {
					view.showText("Что-то пошло не так.");
				}
			}

			student = null;

		} while (view.askYesNo("Повторить поиск? (Y/n)", true));
	}

	private void showByGroup(Group group) {
		view.clear();
		view.showText("ПОКАЗАТЬ УЧЕНИКОВ ГРУППЫ (КЛАССА)");

		
	}

	private void showAllByName() {
		view.clear();
		view.showText("ПОИСК УЧЕНИКА ПО ИМЕНИ/ФАМИЛИИ");
		do {
			var answer = view.askString(
					"\nВведите имя и/или фамилию в любом порядке и/или частично"
							+ " (пустой Ввод для отмены): ",
					null, null);
			if (answer.isEmpty()) {
				return;
			}

			String nameSample = answer.get();
			var studentsRepo = dp.studentsRepository();
			var students = studentsRepo.getStudentsByName(nameSample);
			if (students.size() == 0) {
				view.showText(String.format("Записей по образцу '%s' не найдено.\n", nameSample));
				continue;
			}

			var groupsRepo = dp.groupsRepository();
			var resultList = students.stream()
					.map(s -> new StudentViewModel(s, groupsRepo.getGroupById(s.getGroupId()))).toList();
			view.showList(resultList, "Найденные записи:");

		} while (view.askYesNo("Повторить поиск? (Y/n)", true));
	}

	private void addNew() {
		view.clear();
		view.showText("ДОБАВЛЕНИЕ УЧЕНИКА");
		boolean cancelled = false;
		do {
			var firstName = view.askString("\nВведите имя (пустой Ввод отменит добавление): ", null, null);
			if (firstName.isEmpty()) {
				cancelled = true;
				break;
			}
			var lastName = view.askString("\nВведите фамилию (пустой Ввод отменит добавление): ", null, null);
			if (lastName.isEmpty()) {
				cancelled = true;
				break;
			}
			var strDate = view.askString(
					"\nВведите дату рождения в формате YYYY-MM-DD (пустой Ввод отменит добавление): ",
					s -> s.matches("^\\d{4}-\\d{2}-\\d{2}$"),
					"Некорректный ввод: не соответствует формату YYYY-MM-DD.");
			if (strDate.isEmpty()) {
				cancelled = true;
				break;
			}
			LocalDate birthDate = LocalDate.parse(strDate.get());

			view.showText("\nДобавление ученика в группу:");
			Group group = askGroup();
			if (group == null) {
				cancelled = true;
				break;
			}

			Student student = new Student(null, firstName.get(), lastName.get(), birthDate, group.getGroupId());

			student = dp.studentsRepository().addStudent(student);
			if (student != null) {
				view.showList(List.of(new StudentViewModel(student, group)), "\nУспешно добавлен ученик:");
			} else {
				view.showText("Что-то пошло не так. Не удалось добавить ученика.");
			}

		} while (view.askYesNo("Добавить ещё? (Y/n)", true));

		if (cancelled) {
			view.showText("Добавление отменено.");
			view.waitToProceed();
		}
	}
}
