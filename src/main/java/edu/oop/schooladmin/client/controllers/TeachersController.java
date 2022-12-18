package edu.oop.schooladmin.client.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.OptionalInt;

import edu.oop.schooladmin.client.controllers.MainController.ControllersBag;
import edu.oop.schooladmin.client.viewmodels.Commons;
import edu.oop.schooladmin.client.viewmodels.TeacherViewModel;
import edu.oop.schooladmin.client.views.ViewBase;
import edu.oop.schooladmin.model.entities.Teacher;
import edu.oop.schooladmin.model.interfaces.DataProvider;

public class TeachersController extends ControllerBase {

	private final ControllersBag controllersBag;

	public TeachersController(DataProvider dataProvider, ViewBase viewManager, ControllersBag controllersBag) {
		super(dataProvider, viewManager);
		if (controllersBag == null) {
			throw new NullPointerException("controllersBag");
		}
		this.controllersBag = controllersBag;
	}

	@Override
	protected Map<Object, String> getMenuModel() {
		return Commons.TEACHERS_MENU;
	}

	@Override
	protected void switchToAction(int menuId, Object relatedEntity) {
		switch (menuId) {
			case 1 -> showAll();
			case 2 -> showOneById(relatedEntity instanceof Teacher teacher ? teacher : null);
			case 3 -> showAllByName();
			case 4 -> addNew();
			case 5 -> edit(relatedEntity instanceof Teacher teacher ? teacher : null);
			case 6 -> delete(relatedEntity instanceof Teacher teacher ? teacher : null);
			default -> throw new NoSuchElementException();
		}
	}

	private void showAll() {
		ArrayList<TeacherViewModel> resultList = new ArrayList<>();

		var teachersRepo = dp.teachersRepository();
		var groupsRepo = dp.groupsRepository();
		for (var teacher : teachersRepo.getAllTeachers()) {
			var groups = groupsRepo.getGroupsByTeacherId(teacher.getTeacherId());
			resultList.add(new TeacherViewModel(teacher, groups));
		}
		view.clear();
		view.showList(resultList, "СПИСОК УЧИТЕЛЕЙ:");
		view.waitToProceed();
	}

	private Teacher askTeacher() {
		Teacher teacher = null;
		int id;
		do {
			OptionalInt answer = view.askInteger("\nВведите ID (или пустой Ввод для отмены): ", 0, null);
			if (answer.isEmpty()) {
				return null;
			}
			id = answer.getAsInt();
			var teachersRepo = dp.teachersRepository();
			teacher = teachersRepo.getTeacherById(id);
		} while (teacher == null
				&& view.askYesNo(String.format("Записи для ID %d не найдено.\nПовторить поиск? (Y/n)", id), true));
		return teacher;
	}

	private void showOneById(Teacher teacher) {
		view.clear();
		view.showText("ПОИСК УЧИТЕЛЯ ПО ID");
		do {

			if (teacher == null) {
				teacher = askTeacher();
				if (teacher == null) {
					return;
				}
			}

			var groupsRepo = dp.groupsRepository();
			var groups = groupsRepo.getGroupsByTeacherId(teacher.getTeacherId());
			TeacherViewModel viewModel = new TeacherViewModel(teacher, groups);
			view.showList(List.of(viewModel), "Найдена запись:");

			if (view.askYesNo("Показать назначения учителя? (Y/n)", true)) {
				var possibleMenuId = findSuitableMenuId(Commons.TEACHER_APPOINTMENTS_MENU, "учител");
				if (possibleMenuId.isPresent()) {
					var appointmentsController = controllersBag.teacherAppointmentsController();
					appointmentsController.switchToAction(possibleMenuId.getAsInt(), teacher);
				} else {
					view.showText("Что-то пошло не так.");
				}
			}

			teacher = null;

		} while (view.askYesNo("Повторить поиск? (Y/n)", true));
	}

	private void showAllByName() {
		view.clear();
		view.showText("ПОИСК УЧИТЕЛЯ ПО ИМЕНИ/ФАМИЛИИ");
		do {
			var answer = view.askString(
					"\nВведите имя и/или фамилию в любом порядке и/или частично"
							+ " (пустой Ввод для отмены): ",
					null, null);
			if (answer.isEmpty()) {
				return;
			}

			String nameSample = answer.get();
			var teachersRepo = dp.teachersRepository();
			var teachers = teachersRepo.getTeachersByName(nameSample);
			if (teachers.size() == 0) {
				view.showText(String.format("Записей по образцу '%s' не найдено.\n", nameSample));
				continue;
			}

			var groupsRepo = dp.groupsRepository();
			var resultList = teachers.stream()
					.map(t -> new TeacherViewModel(t, groupsRepo.getGroupsByTeacherId(t.getTeacherId()))).toList();
			view.showList(resultList, "Найденные записи:");

		} while (view.askYesNo("Повторить поиск? (Y/n)", true));
	}

	private void addNew() {
		view.clear();
		view.showText("ДОБАВЛЕНИЕ УЧИТЕЛЯ");

		do {
			var firstName = view.askString("\nВведите имя (пустой Ввод отменит добавление): ", null, null);
			if (firstName.isEmpty()) {
				return;
			}
			var lastName = view.askString("\nВведите фамилию (пустой Ввод отменит добавление): ", null, null);
			if (lastName.isEmpty()) {
				return;
			}
			var strDate = view.askString(
					"\nВведите дату рождения в формате YYYY-MM-DD (пустой Ввод отменит добавление): ",
					s -> s.matches("^\\d{4}-\\d{2}-\\d{2}$"),
					"Некорректный ввод: не соответствует формату YYYY-MM-DD.");
			if (strDate.isEmpty()) {
				return;
			}
			LocalDate birthDate = LocalDate.parse(strDate.get());

			OptionalInt grade = view.askInteger(
					"\nВведите категорию учителя (пустой Ввод отменит добавление): : ", 1, 20);
			if (grade.isEmpty()) {
				return;
			}

			Teacher teacher = new Teacher(null, firstName.get(), lastName.get(), birthDate, grade.getAsInt());
			teacher = dp.teachersRepository().addTeacher(teacher);

			view.showList(List.of(new TeacherViewModel(teacher, null)), "\nУспешно добавлен учитель:");

			if (view.askYesNo("Добавить назначение? (Y/n)", true)) {
				var possibleMenuId = findSuitableMenuId(Commons.TEACHER_APPOINTMENTS_MENU, "Редактировать");
				if (possibleMenuId.isPresent()) {
					var appointmentsController = controllersBag.teacherAppointmentsController();
					appointmentsController.switchToAction(possibleMenuId.getAsInt(), teacher);
				} else {
					view.showText("Что-то пошло не так.");
				}
			}

		} while (view.askYesNo("Добавить ещё? (Y/n)", true));
	}

	private void edit(Teacher teacher) {

	}

	private void delete(Teacher teacher) {
		view.clear();
		view.showText("УДАЛЕНИЕ УЧИТЕЛЯ ПО ID");
		do {

			if (teacher == null) {
				teacher = askTeacher();
				if (teacher == null) {
					return;
				}

				var groupsRepo = dp.groupsRepository();
				var groups = groupsRepo.getGroupsByTeacherId(teacher.getTeacherId());
				TeacherViewModel viewModel = new TeacherViewModel(teacher, groups);
				view.showList(List.of(viewModel), "Найдена запись:");
			}

			if (view.askYesNo("Удалить? (y/N)", false)) {
				int teacherId = teacher.getTeacherId();
				var teachersRepo = dp.teachersRepository();
				if (teachersRepo.removeTeacher(teacherId)) {
					view.showText("Запись успешно удалена!");
				}
			} else {
				view.showText("Удаление отменено.");
			}

			// if (view.askYesNo("Показать назначения учителя? (Y/n)", true)) {
			// var possibleMenuId = findSuitableMenuId(Commons.TEACHER_APPOINTMENTS_MENU,
			// "учител");
			// if (possibleMenuId.isPresent()) {
			// var appointmentsController = controllersBag.teacherAppointmentsController();
			// appointmentsController.switchToAction(possibleMenuId.getAsInt(), teacher);
			// } else {
			// view.showText("Что-то пошло не так.");
			// }
			// }

			teacher = null;

		} while (view.askYesNo("Удалить ещё? (Y/n)", true));
	}
}
