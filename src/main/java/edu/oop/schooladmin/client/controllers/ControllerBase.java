package edu.oop.schooladmin.client.controllers;

import java.time.LocalDate;
import java.util.Map;
import java.util.OptionalInt;

import edu.oop.schooladmin.client.AppSettings;
import edu.oop.schooladmin.client.viewmodels.Commons;
import edu.oop.schooladmin.client.views.ViewBase;
import edu.oop.schooladmin.model.entities.Discipline;
import edu.oop.schooladmin.model.entities.Group;
import edu.oop.schooladmin.model.entities.Person;
import edu.oop.schooladmin.model.entities.Rating;
import edu.oop.schooladmin.model.entities.Student;
import edu.oop.schooladmin.model.entities.Teacher;
import edu.oop.schooladmin.model.entities.TeacherAppointment;
import edu.oop.schooladmin.model.interfaces.DataProvider;

public abstract class ControllerBase {

	protected final DataProvider dp;
	protected final ViewBase view;

	public ControllerBase(DataProvider dataProvider, ViewBase viewManager) {
		this.dp = dataProvider;
		this.view = viewManager;
	}

	/** Запуск жизненного цикла контроллера */
	public void runLifecycle() {
		do {
			var menuModel = getMenuModel();
			view.clear();
			view.showMenu(menuModel);
			Object userChoice = view.askUserChoice(Commons.MENU_MAKE_YOUR_CHOICE, menuModel);
			if (userChoice.equals(Commons.CMD_EXIT)) {

				forceExit();

			} else if (userChoice.equals(Commons.CMD_GO_BACK)) {

				return;

			} else if (userChoice instanceof Integer menuId) {

				switchToAction(menuId, null);
			}

		} while (true);
	}

	protected abstract Map<Object, String> getMenuModel();

	protected abstract void switchToAction(int menuId, Object relatedEntity);

	protected void dummyAction() {
		System.out.println("Приветики. Данная функция ожидается в следующей версии...");
		view.waitToProceed();
	}

	protected void forceExit() {
		view.showGoodbye();
		System.exit(0);
	}

	protected OptionalInt findSuitableMenuId(Map<Object, String> menuModel, String stringSample) {
		assert menuModel != null && stringSample != null && !stringSample.isEmpty();

		final String sample = stringSample.toLowerCase(AppSettings.LOCALE);

		var key = menuModel.entrySet().stream()
				.filter(entry -> entry.getValue() != null
						&& entry.getValue().toLowerCase(AppSettings.LOCALE).contains(sample))
				.findFirst();
		if (key.isPresent() && key.get().getKey() instanceof Integer menuId) {
			return OptionalInt.of(menuId);
		}
		return OptionalInt.empty();
	}

	// little bit more specific shared mtods:

	protected Student askStudent() {
		Student student = null;
		int id;
		do {
			OptionalInt answer = view.askInteger("Введите ID ученика (или пустой Ввод чтобы отменить): ", 0, null);
			if (answer.isEmpty()) {
				return null;
			}
			id = answer.getAsInt();
			var studentsRepo = dp.studentsRepository();
			student = studentsRepo.getStudentById(id);
		} while (student == null
				&& view.askYesNo(String.format("Записи с ID %d не найдено.\nПовторить поиск? (Д/н)", id), true));

		return student;
	}

	protected Teacher askTeacher() {
		Teacher teacher = null;
		int id;
		do {
			OptionalInt answer = view.askInteger("Введите ID учителя (или пустой Ввод чтобы отменить): ", 0, null);
			if (answer.isEmpty()) {
				return null;
			}
			id = answer.getAsInt();
			var teachersRepo = dp.teachersRepository();
			teacher = teachersRepo.getTeacherById(id);
		} while (teacher == null
				&& view.askYesNo(String.format("Записи с ID %d не найдено.\nПовторить поиск? (Д/н)", id), true));
		return teacher;
	}

	protected Group askGroup() {
		Group group = null;
		do {
			var answer = view.askInteger("Введите номер учебного года (или пустой Ввод чтобы отменить): ", 1, 12);
			if (answer.isEmpty()) {
				return null;
			}
			int classYear = answer.getAsInt();
			var groupsRepo = dp.groupsRepository();
			var selectionByYear = groupsRepo.getGroupsByClassYear(classYear);
			if (selectionByYear.size() == 0) {
				view.showText("Групп по заданному учебному году не найдено.");
				continue;
			}

			var answer2 = view.askString("Введите букву класса (или пустой Ввод чтобы отменить): ",
					s -> s.length() > 0,
					"Некорректный ввод: требуется ввести букву класса.");
			if (answer2.isEmpty()) {
				return null;
			}
			char classMark = Character.toUpperCase(answer2.get().charAt(0));
			var selectionByLetter = selectionByYear.stream()
					.filter(g -> Character.toUpperCase(g.getClassMark()) == classMark).toList();
			if (selectionByLetter.size() > 0) {
				group = selectionByLetter.get(0);
				if (selectionByLetter.size() > 1) {
					view.showText("Предупреждение: В базе найдено несколько идентичных групп!");
				}
			}
		} while (group == null && view.askYesNo("Группа не найдена. Повторить поиск? (Д/н)", true));

		return group;
	}

	protected Discipline askDiscipline() {
		Discipline discipline = null;
		do {
			var answer = view.askString("Введите название или ID предмета (или пустой Ввод чтобы отменить): ", null,
					null);
			if (answer.isEmpty()) {
				return null;
			}
			String rawStrAnswer = answer.get();
			var disciplinesRepo = dp.disciplinesRepository();
			if (rawStrAnswer.matches("^\\d+$")) { // проверка если на неотрицательное целое число
				int disciplineId = Integer.parseInt(rawStrAnswer);
				discipline = disciplinesRepo.getDisciplineById(disciplineId);
			} else { // иначе пытаемся искать по наименованию
				discipline = disciplinesRepo.getDisciplineByName(rawStrAnswer);
			}
		} while (discipline == null && view.askYesNo("Предмет не найден. Повторить поиск? (Д/н)", true));

		return discipline;
	}

	protected TeacherAppointment askAppointment() {
		TeacherAppointment appointment = null;
		int id;
		do {
			OptionalInt answer = view.askInteger("Введите ID назначения (или пустой Ввод чтобы отменить): ", 0, null);
			if (answer.isEmpty()) {
				return null;
			}
			id = answer.getAsInt();
			var teacherAppointmentsRepo = dp.teacherAppointmentsRepository();
			appointment = teacherAppointmentsRepo.getTeacherAppointmentById(id);
		} while (appointment == null
				&& view.askYesNo(String.format("Назначения с ID %d не найдено.\nПовторить поиск? (Д/н)", id), true));

		return appointment;
	}

	protected Rating askRating() {
		Rating rating = null;
		do {
			var answer = view.askInteger("Введите ID оценки (или пустой Ввод чтобы отменить): ", 0, null);
			if (answer.isEmpty()) {
				return null;
			}
			rating = dp.ratingsRepository().getRatingById(answer.getAsInt());
		} while (rating == null && view.askYesNo("Оценки с таким ID в журнал нет. Повторить поиск? (Д/н)", true));

		return rating;
	}

	protected String[] editName(Person person) {
		assert person != null;
		var firstName = view.askString("Введите имя (пустой Ввод чтобы отменить): ", null, null);
		if (firstName.isEmpty()) {
			return null;
		}
		var lastName = view.askString("Введите фамилию (пустой Ввод чтобы отменить): ", null, null);
		if (lastName.isEmpty()) {
			return null;
		}
		person.setFirstName(firstName.get());
		person.setLastName(lastName.get());
		return new String[] { firstName.get(), lastName.get() };
	}

	protected LocalDate editBirthDate(Person person) {
		assert person != null;
		var strDate = view.askString(
				"Введите дату рождения в формате YYYY-MM-DD (пустой Ввод чтобы отменить): ",
				s -> s.matches("^\\d{4}-\\d{2}-\\d{2}$"),
				"Некорректный ввод: не соответствует формату YYYY-MM-DD.");
		if (strDate.isEmpty()) {
			return null;
		}
		LocalDate birthDate = LocalDate.parse(strDate.get());
		person.setBirthDate(birthDate);
		return birthDate;
	}
}
