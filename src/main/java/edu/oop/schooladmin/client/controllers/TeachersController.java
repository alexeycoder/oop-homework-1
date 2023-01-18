package edu.oop.schooladmin.client.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.OptionalInt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.oop.schooladmin.client.controllers.MainController.ControllersBag;
import edu.oop.schooladmin.client.viewmodels.Commons;
import edu.oop.schooladmin.client.viewmodels.TeacherViewModel;
import edu.oop.schooladmin.client.views.ViewBase;
import edu.oop.schooladmin.model.businesslevel.interfaces.DataProvider;
import edu.oop.schooladmin.model.entities.Group;
import edu.oop.schooladmin.model.entities.Teacher;

public class TeachersController extends ControllerBase {

	private static final Logger logger = LoggerFactory.getLogger(TeachersController.class);
	private final ControllersBag controllersBag;

	public TeachersController(DataProvider dataProvider, ViewBase viewManager, ControllersBag controllersBag) {
		super(dataProvider, viewManager);
		if (controllersBag == null) {
			throw new NullPointerException("controllersBag");
		}
		this.controllersBag = controllersBag;
		logger.trace("Controller instance successfully created.");
	}

	@Override
	protected Map<Object, String> getMenuModel() {
		return Commons.TEACHERS_MENU;
	}

	@Override
	protected boolean switchToAction(int menuId, Object relatedEntity) {
		switch (menuId) {
			case 1 -> showAll();
			case 2 -> {
				return showOneById(relatedEntity instanceof Teacher teacher ? teacher : null);
			}
			case 3 -> showAllByName();
			case 4 -> {
				return addNew();
			}
			case 5 -> edit(relatedEntity instanceof Teacher teacher ? teacher : null);
			case 6 -> delete(relatedEntity instanceof Teacher teacher ? teacher : null);
			default -> throw new NoSuchElementException();
		}
		return false;
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
		view.showList(resultList, "СПИСОК УЧИТЕЛЕЙ");
		view.waitToProceed();
	}

	private boolean showOneById(Teacher teacher) {
		view.clear();
		view.showText("ПОИСК УЧИТЕЛЯ ПО ID");
		do {
			if (teacher == null) {
				teacher = askTeacher();
				if (teacher == null) {
					return false;
				}
			}

			var groupsRepo = dp.groupsRepository();
			var groups = groupsRepo.getGroupsByTeacherId(teacher.getTeacherId());
			TeacherViewModel viewModel = new TeacherViewModel(teacher, groups);
			view.showList(List.of(viewModel), "Найдена запись:");

			if (view.askYesNo("Показать назначения учителя? (Д/н)", true)) {
				var possibleMenuId = findSuitableMenuId(Commons.TEACHER_APPOINTMENTS_MENU, "учител");
				if (possibleMenuId.isPresent()) {
					var appointmentsController = controllersBag.teacherAppointmentsController();
					if (appointmentsController.switchToAction(possibleMenuId.getAsInt(), teacher)) {
						return true;
					}
				} else {
					logger.warn("Something went wrong when retrieving suitable menuId from TEACHER_APPOINTMENTS_MENU.");
					view.showText("Что-то пошло не так.");
				}
			}

			teacher = null;

		} while (view.askYesNo("Повторить поиск? (Д/н)", true));

		return false;
	}

	private void showAllByName() {
		view.clear();
		view.showText("ПОИСК УЧИТЕЛЯ ПО ИМЕНИ/ФАМИЛИИ");
		do {
			view.showEmpty();
			var answer = view.askString(
					"Введите имя и/или фамилию в любом порядке и/или частично"
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

		} while (view.askYesNo("Повторить поиск? (Д/н)", true));
	}

	private boolean addNew() {
		view.clear();
		view.showText("ДОБАВЛЕНИЕ УЧИТЕЛЯ");
		boolean cancelled = false;
		do {
			Teacher teacher = new Teacher();
			view.showEmpty();
			if (editName(teacher) == null) {
				cancelled = true;
				break;
			}
			if (editBirthDate(teacher) == null) {
				cancelled = true;
				break;
			}
			if (editGrade(teacher) == null) {
				cancelled = true;
				break;
			}

			teacher = dp.teachersRepository().addTeacher(teacher);

			view.showList(List.of(new TeacherViewModel(teacher, null)), "\nУспешно добавлен учитель:");
			logger.info("Teacher '{}' has been added.", teacher);

			if (view.askYesNo("Добавить классное руководство? (Д/н)", true)) {
				var group = editGroup(teacher);
				if (group != null) {
					if (dp.groupsRepository().updateGroup(group)) {
						view.showText("Классное руководство успешно назначено.");
						logger.info("Teacher '{}' became supervisor of '{}' class.", teacher, group);
					} else {
						logger.warn("Something went wrong on making teacher '{}' be supervisor of some class.",
								teacher);
						view.showText("Что-то пошло не так.");
					}
				}
			}

			if (view.askYesNo("Добавить назначение? (Д/н)", true)) {
				var possibleMenuId = findSuitableMenuId(Commons.TEACHER_APPOINTMENTS_MENU, "Добавить");
				if (possibleMenuId.isPresent()) {
					var appointmentsController = controllersBag.teacherAppointmentsController();
					if (appointmentsController.switchToAction(possibleMenuId.getAsInt(), teacher)) {
						return true;
					}
				} else {
					logger.warn("Something went wrong when retrieving suitable menuId from TEACHER_APPOINTMENTS_MENU.");
					view.showText("Что-то пошло не так.");
				}
			}
		} while (view.askYesNo("Добавить ещё учителя (Д/н)", true));

		if (cancelled) {
			view.showText("Добавление отменено.");
			view.waitToProceed();
		}

		return false;
	}

	private void edit(Teacher teacher) {
		view.clear();
		view.showText("РЕДАКТИРОВАНИЕ УЧИТЕЛЯ");
		var groupsRepo = dp.groupsRepository();
		boolean cancelled = false;
		do {
			if (teacher == null) {
				teacher = askTeacher();
				if (teacher == null) {
					cancelled = true;
					break;
				}
			}

			do {
				view.clear();
				view.showList(
						List.of(new TeacherViewModel(teacher, groupsRepo.getGroupsByTeacherId(teacher.getTeacherId()))),
						"РЕДАКТИРОВАНИЕ УЧИТЕЛЯ");

				var menuModel = Commons.EDIT_STUDENT_MENU;
				view.showMenu(menuModel);
				Object userChoice = view.askUserChoice("Выберите параметр для изменения: ", menuModel);
				if (userChoice.equals(Commons.CMD_GO_BACK)) {
					break;
				}

				var bkpTeacher = new Teacher(teacher);

				if (userChoice instanceof Integer menuId) {
					Object result = null;
					switch (menuId) {
						case 1 -> result = editName(teacher);
						case 2 -> result = editBirthDate(teacher);
						case 3 -> result = editGrade(teacher);
						// case 4 -> result = editGroup(teacher);
						// case 5 -> result = editAppointment(teacher);
						default -> throw new NoSuchElementException();
					}

					if (result != null && view.askYesNo("Сохранить изменения? (Д/н)", true)) {
						if (dp.teachersRepository().updateTeacher(teacher)) {
							view.showText("Изменения успешно сохранены.");
							logger.info("Teacher '{}' has been updated to '{}'.", bkpTeacher, teacher);
						} else {
							teacher = bkpTeacher;
							logger.warn("Something went wrong on updating teacher '{}' to '{}'.", bkpTeacher, teacher);
							view.showText("Что-то пошло не так. Изменения не были сохранены.");
						}
						view.waitToProceed();
					} else {
						teacher = bkpTeacher;
					}
				}

			} while (true); // GO_BACK see above

			teacher = null;

		} while (view.askYesNo("Редактировать другого учителя? (Д/н)", true));

		if (cancelled) {
			view.showText("Редактирование отменено.");
		}
		view.waitToProceed();
	}

	private Integer editGrade(Teacher teacher) {
		assert teacher != null;
		OptionalInt grade = view.askInteger(
				"Введите категорию учителя (пустой Ввод чтобы отменить): ", 1, 20);
		if (grade.isPresent()) {
			teacher.setGrade(grade.getAsInt());
			return grade.getAsInt();
		}
		return null;
	}

	private Group editGroup(Teacher teacher) {
		assert teacher != null;
		do {
			Group group = askGroup();
			if (group == null) {
				return null;
			}
			if (group.getTeacherId() == null) {
				group.setTeacherId(teacher.getTeacherId());
				return group;
			} else {
				view.showText("У группы уже есть классный руководитель. Выберите другую группу.");
				continue;
			}
		} while (true);
	}

	// private editAppointment(teacher){
	// }

	private void delete(Teacher teacher) {
		view.clear();
		view.showText("УДАЛЕНИЕ УЧИТЕЛЯ");
		view.showText("(связанные назначения будут также удалены)");
		boolean cancelled = false;
		do {
			if (teacher == null) {
				teacher = askTeacher();
				if (teacher == null) {
					cancelled = true;
					break;
				}

				var groupsRepo = dp.groupsRepository();
				var groups = groupsRepo.getGroupsByTeacherId(teacher.getTeacherId());
				TeacherViewModel viewModel = new TeacherViewModel(teacher, groups);
				view.showList(List.of(viewModel), "Найдена запись:");
			}

			if (view.askYesNo("Удалить? (д/Н)", false)) {
				int teacherId = teacher.getTeacherId();
				var teachersRepo = dp.teachersRepository();
				if (teachersRepo.removeTeacher(teacherId)) {
					view.showText("Запись успешно удалена!");
					logger.info("Teacher '{}' has been deleted.", teacher);
				}
			} else {
				view.showText("Удаление отменено.");
			}

			teacher = null;

		} while (view.askYesNo("Удалить ещё? (Д/н)", true));

		if (cancelled) {
			view.showText("Удаление отменено.");
		}
		view.waitToProceed();
	}
}
