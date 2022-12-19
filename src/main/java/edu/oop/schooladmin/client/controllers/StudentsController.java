package edu.oop.schooladmin.client.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import edu.oop.schooladmin.client.controllers.MainController.ControllersBag;
import edu.oop.schooladmin.client.viewmodels.Commons;
import edu.oop.schooladmin.client.viewmodels.GroupViewModel;
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
			case 6 -> edit(relatedEntity instanceof Student student ? student : null);
			case 7 -> delete(relatedEntity instanceof Student student ? student : null);
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
		view.showList(resultList, "СПИСОК УЧЕНИКОВ");
		view.waitToProceed();
	}

	private void showOneById(Student student) {
		view.clear();
		view.showText("ПОИСК УЧЕНИКА ПО ID");
		do {
			if (student == null) {
				view.showEmpty();
				student = askStudent();
				if (student == null) {
					view.waitToProceed();
					return;
				}
			}

			var groupsRepo = dp.groupsRepository();
			var group = groupsRepo.getGroupById(student.getGroupId());
			StudentViewModel viewModel = new StudentViewModel(student, group);
			view.showList(List.of(viewModel), "Найдена запись:");

			if (view.askYesNo("Показать оценки ученика? (Д/н)", true)) {
				var possibleMenuId = findSuitableMenuId(Commons.RATINGS_MENU, "оценки ученик");
				if (possibleMenuId.isPresent()) {
					var ratingsController = controllersBag.ratingsController();
					ratingsController.switchToAction(possibleMenuId.getAsInt(), student);
				} else {
					view.showText("Что-то пошло не так.");
				}
			}

			student = null;

		} while (view.askYesNo("Повторить поиск? (Д/н)", true));
	}

	private void showByGroup(Group group) {
		view.clear();
		view.showText("ПОКАЗАТЬ УЧЕНИКОВ ГРУППЫ (КЛАССА)");
		view.showEmpty();

		if (group == null) {
			group = askGroup();
			if (group == null) {
				view.waitToProceed();
				return;
			}
		}

		view.showText("\n" + GroupViewModel.groupSimplifiedRepr(group));

		var studentsRepo = dp.studentsRepository();
		var students = studentsRepo.getStudentsByGroupId(group.getGroupId());
		if (students.size() == 0) {
			view.showText("В группе ещё нет учеников.");
		} else {
			var viewModelsList = students.stream()
					.map(s -> new StudentViewModel(s, null)).toList();
			view.showList(viewModelsList, null);
		}

		view.waitToProceed();
	}

	private void showAllByName() {
		view.clear();
		view.showText("ПОИСК УЧЕНИКА ПО ИМЕНИ/ФАМИЛИИ");
		do {
			view.showEmpty();
			var answer = view.askString(
					"Введите имя и/или фамилию в любом порядке и/или частично"
							+ " (пустой Ввод для отмены): ",
					null, null);
			if (answer.isEmpty()) {
				break;
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

		} while (view.askYesNo("Повторить поиск? (Д/н)", true));

		view.waitToProceed();
	}

	private void addNew() {
		view.clear();
		view.showText("ДОБАВЛЕНИЕ УЧЕНИКА");
		boolean cancelled = false;
		do {
			Student student = new Student();
			view.showEmpty();
			if (editName(student) == null) {
				cancelled = true;
				break;
			}
			if (editBirthDate(student) == null) {
				cancelled = true;
				break;
			}
			view.showEmpty();
			view.showText("Добавление ученика в группу:");
			Group group = editGroup(student);
			if (group == null) {
				cancelled = true;
				break;
			}

			student = dp.studentsRepository().addStudent(student);
			if (student != null) {
				view.showEmpty();
				view.showList(List.of(new StudentViewModel(student, group)), "Успешно добавлен ученик:");
			} else {
				view.showText("Что-то пошло не так. Не удалось добавить ученика.");
			}

		} while (view.askYesNo("Добавить ещё? (Д/н)", true));

		if (cancelled) {
			view.showText("Добавление отменено.");
		}
		view.waitToProceed();
	}

	private void edit(Student student) {
		view.clear();
		view.showText("РЕДАКТИРОВАНИЕ УЧЕНИКА");
		var groupsRepo = dp.groupsRepository();
		boolean cancelled = false;
		do {
			if (student == null) {
				view.showEmpty();
				student = askStudent();
				if (student == null) {
					cancelled = true;
					break;
				}
			}

			do {
				view.clear();
				view.showList(List.of(new StudentViewModel(student, groupsRepo.getGroupById(student.getGroupId()))),
						"РЕДАКТИРОВАНИЕ УЧЕНИКА");

				var menuModel = Commons.EDIT_STUDENT_MENU;
				view.showMenu(menuModel);
				Object userChoice = view.askUserChoice("Выберите параметр для изменения: ", menuModel);
				if (userChoice.equals(Commons.CMD_GO_BACK)) {
					break;
				}

				var bkpStudent = new Student(student);

				if (userChoice instanceof Integer menuId) {
					Object result = null;
					switch (menuId) {
						case 1 -> result = editName(student);
						case 2 -> result = editBirthDate(student);
						case 3 -> result = editGroup(student);
						default -> throw new NoSuchElementException();
					}

					if (result != null && view.askYesNo("Сохранить изменения? (Д/н)", true)) {
						if (dp.studentsRepository().updateStudent(student)) {
							view.showText("Изменения успешно сохранены.");
						} else {
							view.showText("Что-то пошло не так. Изменения не были сохранены.");
						}
						view.waitToProceed();
					} else {
						student = bkpStudent;
					}
				}

			} while (true); // GO_BACK see above

			student = null;

		} while (view.askYesNo("Редактировать другого ученика? (Д/н)", true));

		if (cancelled) {
			view.showText("Редактирование отменено.");
		}
		view.waitToProceed();
	}

	private Group editGroup(Student student) {
		assert student != null;
		Group group = askGroup();
		if (group != null) {
			student.setGroupId(group.getGroupId());
			return group;
		}
		return null;
	}

	private void delete(Student student) {
		view.clear();
		view.showText("УДАЛЕНИЕ УЧЕНИКА");
		view.showText("(связанные оценки будут также удалены)");
		boolean cancelled = false;
		do {
			if (student == null) {
				view.showEmpty();
				student = askStudent();
				if (student == null) {
					cancelled = true;
					break;
				}

				var groupsRepo = dp.groupsRepository();
				var group = groupsRepo.getGroupById(student.getGroupId());
				StudentViewModel viewModel = new StudentViewModel(student, group);
				view.showList(List.of(viewModel), "Найдена запись:");
			}

			if (view.askYesNo("Удалить? (д/Н)", false)) {
				int studentId = student.getStudentId();
				var studentsRepo = dp.studentsRepository();
				if (studentsRepo.removeStudent(studentId)) {
					view.showText("Запись успешно удалена!");
				}
			} else {
				view.showText("Удаление отменено.");
			}

			student = null;

		} while (view.askYesNo("Удалить ещё? (Д/н)", true));

		if (cancelled) {
			view.showText("Удаление отменено.");
		}
		view.waitToProceed();
	}
}
