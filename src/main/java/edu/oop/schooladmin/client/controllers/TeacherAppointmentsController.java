package edu.oop.schooladmin.client.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import edu.oop.schooladmin.client.controllers.MainController.ControllersBag;
import edu.oop.schooladmin.client.viewmodels.Commons;
import edu.oop.schooladmin.client.viewmodels.DisciplineViewModel;
import edu.oop.schooladmin.client.viewmodels.GroupViewModel;
import edu.oop.schooladmin.client.viewmodels.TeacherAppointmentsViewModel;
import edu.oop.schooladmin.client.viewmodels.TeacherViewModel;
import edu.oop.schooladmin.client.views.ViewBase;
import edu.oop.schooladmin.model.entities.Discipline;
import edu.oop.schooladmin.model.entities.Group;
import edu.oop.schooladmin.model.interfaces.DataProvider;

public class TeacherAppointmentsController extends ControllerBase {

	private final ControllersBag controllersBag;

	public TeacherAppointmentsController(
			DataProvider dataProvider, ViewBase viewManager, ControllersBag controllersBag) {
		super(dataProvider, viewManager);
		if (controllersBag == null) {
			throw new NullPointerException("controllersBag");
		}
		this.controllersBag = controllersBag;
	}

	@Override
	protected Map<Object, String> getMenuModel() {
		return Commons.TEACHER_APPOINTMENTS_MENU;
	}

	@Override
	protected void switchToAction(int menuId, Integer entityId) {
		switch (menuId) {
			case 1 -> showAll();
			case 2 -> showByTeacher(entityId);
			case 3 -> showByDiscipline(entityId);
			case 4 -> showByGroup(entityId);
			case 5 -> edit(entityId);
			case 6 -> delete(entityId);
			default -> throw new NoSuchElementException();
		}
	}

	private void showAll() {
		var appointmentsRepo = dp.teacherAppointmentsRepository();
		var teachersRepo = dp.teachersRepository();
		var disciplinesRepo = dp.disciplinesRepository();
		var groupsRepo = dp.groupsRepository();

		var resultList = appointmentsRepo.getAllTeacherAppointments().stream()
				.map(a -> new TeacherAppointmentsViewModel(
						a,
						teachersRepo.getTeacherById(a.getTeacherId()),
						disciplinesRepo.getDisciplineById(a.getDisciplineId()),
						groupsRepo.getGroupById(a.getGroupId())))
				.toList();

		view.clear();
		view.showList(resultList, "НАЗНАЧЕНИЯ:");
		view.waitToProceed();
	}

	private void showByTeacher(Integer entityId) {
		view.clear();
		view.showText("НАЗНАЧЕНИЯ ПО УЧИТЕЛЮ:");

		if (entityId == null) {
			var answer = view.askInteger("Введите ID учителя (или пустой Ввод для отмены): ", 0, null);
			if (answer.isEmpty()) {
				return;
			}
			entityId = answer.getAsInt();
		}

		var teachersRepo = dp.teachersRepository();
		var teacher = teachersRepo.getTeacherById(entityId);
		if (teacher == null) {
			view.showText(String.format("Учителя не найдено для заданного ID %d", entityId));
			view.waitToProceed();
			return;
		}

		view.showText(TeacherViewModel.teacherSimplifiedRepr(teacher));

		var appointmentsRepo = dp.teacherAppointmentsRepository();
		var appointments = appointmentsRepo.getTeacherAppointmentsByTeacherId(entityId);
		if (appointments.size() == 0) {
			view.showText("Назначений нет.");
		} else {
			var disciplinesRepo = dp.disciplinesRepository();
			var groupsRepo = dp.groupsRepository();
			var resultList = appointments.stream()
					.map(a -> new TeacherAppointmentsViewModel(
							a,
							null,
							disciplinesRepo.getDisciplineById(a.getDisciplineId()),
							groupsRepo.getGroupById(a.getGroupId())))
					.toList();
			view.showList(resultList, null);
		}

		view.waitToProceed();
	}

	private void showByDiscipline(Integer entityId) {
		view.clear();
		view.showText("НАЗНАЧЕНИЯ ПО ПРЕДМЕТУ:");

		var disciplinesRepo = dp.disciplinesRepository();
		Discipline discipline = null;

		if (entityId == null) {
			var answer = view.askString("Введите название или ID предмета (или пустой Ввод для отмены): ", null, null);
			if (answer.isEmpty()) {
				return;
			}
			String rawStrAnswer = answer.get();
			if (rawStrAnswer.matches("^\\d+$")) { // проверка если на неотрицательное целое число
				entityId = Integer.parseInt(rawStrAnswer);
			} else { // иначе пытаемся искать по наименованию
				discipline = disciplinesRepo.getDisciplineByName(rawStrAnswer);
				if (discipline != null) {
					entityId = discipline.getDisciplineId();
				}
			}
		}

		if (entityId == null || ((discipline = disciplinesRepo.getDisciplineById(entityId)) == null)) {
			view.showText("Предмета не найдено.");
			view.waitToProceed();
			return;
		}

		view.showText(DisciplineViewModel.disciplineSimplifiedRept(discipline));

		var appointmentsRepo = dp.teacherAppointmentsRepository();
		var appointments = appointmentsRepo.getTeacherAppointmentsByDisciplineId(entityId);
		if (appointments.size() == 0) {
			view.showText("Назначений нет.");
		} else {
			var teachersRepo = dp.teachersRepository();
			var groupsRepo = dp.groupsRepository();
			var resultList = appointments.stream()
					.map(a -> new TeacherAppointmentsViewModel(
							a,
							teachersRepo.getTeacherById(a.getTeacherId()),
							null,
							groupsRepo.getGroupById(a.getGroupId())))
					.toList();
			view.showList(resultList, null);
		}

		view.waitToProceed();
	}

	private void showByGroup(Integer entityId) {
		view.clear();
		view.showText("НАЗНАЧЕНИЯ ПО ГРУППЕ (КЛАССУ):");

		var groupsRepo = dp.groupsRepository();
		Group group = null;

		if (entityId == null) {
			var answer = view.askInteger("Введите номер учебного года (или пустой Ввод для отмены): ", 1, null);
			if (answer.isEmpty()) {
				return;
			}
			int classYear = answer.getAsInt();
			var selectionByYear = groupsRepo.getGroupsByClassYear(classYear);
			if (selectionByYear.size() == 0) {
				view.showText("Групп по заданному учебному году не найдено.");
				view.waitToProceed();
				return;
			}

			var answer2 = view.askString("Введите букву класса (или пустой Ввод для отмены): ", s -> s.length() == 1,
					null);
			if (answer2.isEmpty()) {
				return;
			}
			char classMark = Character.toUpperCase(answer2.get().charAt(0));
			var selectionByLetter = selectionByYear.stream()
					.filter(g -> Character.toUpperCase(g.getClassMark()) == classMark).toList();
			if (selectionByLetter.size() > 0) {
				group = selectionByLetter.get(0);
				entityId = group.getGroupId();
				if (selectionByLetter.size() > 1) {
					view.showText("Предупреждение: В базе найдено несколько идентичных групп!");
				}
			}
		}

		if (entityId == null || ((group = groupsRepo.getGroupById(entityId)) == null)) {
			view.showText("Предмета не найдено");
			view.waitToProceed();
			return;
		}

		view.showText(GroupViewModel.groupSimplifiedRepr(group));

		var appointmentsRepo = dp.teacherAppointmentsRepository();
		var appointments = appointmentsRepo.getTeacherAppointmentsByGroupId(entityId);
		if (appointments.size() == 0) {
			view.showText("Назначений нет.");
		} else {
			var teachersRepo = dp.teachersRepository();
			var disciplinesRepo = dp.disciplinesRepository();
			var resultList = appointments.stream()
					.map(a -> new TeacherAppointmentsViewModel(
							a,
							teachersRepo.getTeacherById(a.getTeacherId()),
							disciplinesRepo.getDisciplineById(a.getDisciplineId()),
							null))
					.toList();
			view.showList(resultList, null);
		}

		view.waitToProceed();
	}

	private void edit(Integer entityId) {
	}

	private void delete(Integer entityId) {
	}
}
