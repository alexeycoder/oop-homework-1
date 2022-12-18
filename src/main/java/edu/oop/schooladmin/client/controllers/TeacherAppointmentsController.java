package edu.oop.schooladmin.client.controllers;

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
import edu.oop.schooladmin.model.entities.Teacher;
import edu.oop.schooladmin.model.entities.TeacherAppointment;
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
	protected void switchToAction(int menuId, Object relatedEntity) {
		switch (menuId) {
			case 1 -> showAll();
			case 2 -> showByTeacher(relatedEntity instanceof Teacher teacher ? teacher : null);
			case 3 -> showByDiscipline(relatedEntity instanceof Discipline discipline ? discipline : null);
			case 4 -> showByGroup(relatedEntity instanceof Group group ? group : null);
			case 5 -> edit(relatedEntity instanceof TeacherAppointment appointment ? appointment : null);
			case 6 -> delete(relatedEntity instanceof TeacherAppointment appointment ? appointment : null);
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

	private void showByTeacher(Teacher teacher) {
		view.clear();
		view.showText("НАЗНАЧЕНИЯ ПО УЧИТЕЛЮ:");

		if (teacher == null) {
			teacher = askTeacher();
			if (teacher == null) {
				return;
			}
		}

		// while (teacher == null) {
		// var answer = view.askInteger("Введите ID учителя (или пустой Ввод для
		// отмены): ", 0, null);
		// if (answer.isEmpty()) {
		// return;
		// }
		// int teacherId = answer.getAsInt();
		// var teachersRepo = dp.teachersRepository();
		// teacher = teachersRepo.getTeacherById(teacherId);
		// if (teacher == null && !view.askYesNo("Учителя не найдено. Повторить поиск?
		// (Y/n)", true)) {
		// view.waitToProceed();
		// return;
		// }
		// }

		view.showText("\n" + TeacherViewModel.teacherSimplifiedRepr(teacher));

		var appointmentsRepo = dp.teacherAppointmentsRepository();
		var appointments = appointmentsRepo.getTeacherAppointmentsByTeacherId(teacher.getTeacherId());
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

	private void showByDiscipline(Discipline discipline) {
		view.clear();
		view.showText("НАЗНАЧЕНИЯ ПО ПРЕДМЕТУ:");

		if (discipline == null) {
			discipline = askDiscipline();
			if (discipline == null) {
				return;
			}
		}

		view.showText("\n" + DisciplineViewModel.disciplineSimplifiedRept(discipline));

		var appointmentsRepo = dp.teacherAppointmentsRepository();
		var appointments = appointmentsRepo.getTeacherAppointmentsByDisciplineId(discipline.getDisciplineId());
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

	private void showByGroup(Group group) {
		view.clear();
		view.showText("НАЗНАЧЕНИЯ ПО ГРУППЕ (КЛАССУ):");

		if (group == null) {
			group = askGroup();
			if (group == null) {
				return;
			}
		}

		view.showText("\n" + GroupViewModel.groupSimplifiedRepr(group));

		var appointmentsRepo = dp.teacherAppointmentsRepository();
		var appointments = appointmentsRepo.getTeacherAppointmentsByGroupId(group.getGroupId());
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

	private void edit(TeacherAppointment appointment) {
		if (appointment == null) {

		}
	}

	private void delete(TeacherAppointment appointment) {
	}
}
