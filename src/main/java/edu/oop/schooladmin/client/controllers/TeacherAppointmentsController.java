package edu.oop.schooladmin.client.controllers;

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
import edu.oop.schooladmin.model.entities.Teacher;
import edu.oop.schooladmin.model.entities.TeacherAppointment;
import edu.oop.schooladmin.model.interfaces.DataProvider;

public class TeacherAppointmentsController extends ControllerBase {

	// private final ControllersBag controllersBag;

	public TeacherAppointmentsController(
			DataProvider dataProvider, ViewBase viewManager, ControllersBag controllersBag) {
		super(dataProvider, viewManager);
		if (controllersBag == null) {
			throw new NullPointerException("controllersBag");
		}
		// this.controllersBag = controllersBag;
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
			case 5 -> addNew(relatedEntity instanceof Teacher teacher ? teacher : null);
			case 6 -> edit(relatedEntity instanceof TeacherAppointment appointment ? appointment : null);
			case 7 -> delete(relatedEntity instanceof TeacherAppointment appointment ? appointment : null);
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
		view.showList(resultList, "НАЗНАЧЕНИЯ");
		view.waitToProceed();
	}

	private void showByTeacher(Teacher teacher) {
		view.clear();
		view.showText("НАЗНАЧЕНИЯ ПО УЧИТЕЛЮ");

		if (teacher == null) {
			teacher = askTeacher();
			if (teacher == null) {
				view.waitToProceed();
				return;
			}
		}

		view.showEmpty();
		view.showText(TeacherViewModel.teacherSimplifiedRepr(teacher));

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
		view.showText("НАЗНАЧЕНИЯ ПО ПРЕДМЕТУ");

		if (discipline == null) {
			discipline = askDiscipline();
			if (discipline == null) {
				view.waitToProceed();
				return;
			}
		}
		view.showEmpty();
		view.showText(DisciplineViewModel.disciplineSimplifiedRept(discipline));

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
		view.showText("НАЗНАЧЕНИЯ ПО ГРУППЕ (КЛАССУ)");

		if (group == null) {
			group = askGroup();
			if (group == null) {
				view.waitToProceed();
				return;
			}
		}
		view.showEmpty();
		view.showText(GroupViewModel.groupSimplifiedRepr(group));

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

	private TeacherAppointmentsViewModel createViewModel(TeacherAppointment appointment) {
		return new TeacherAppointmentsViewModel(appointment,
				dp.teachersRepository().getTeacherById(appointment.getTeacherId()),
				dp.disciplinesRepository().getDisciplineById(appointment.getDisciplineId()),
				dp.groupsRepository().getGroupById(appointment.getGroupId()));
	}

	private void addNew(Teacher teacher) {
		view.clear();
		view.showText("ДОБАВЛЕНИЕ НАЗНАЧЕНИЯ УЧИТЕЛЯ");

		boolean askMore = false;
		if (teacher == null) {
			askMore = true;
			teacher = askTeacher();
			if (teacher == null) {
				view.showText("Добавление отменено.");
				view.waitToProceed();
				return;
			}
		}

		boolean cancelled = false;
		do {
			TeacherAppointment temp = new TeacherAppointment(null, teacher.getTeacherId(), null, null);
			view.showEmpty();
			Discipline discipline = editDiscipline(temp);
			if (discipline == null) {
				cancelled = true;
				break;
			}
			Group group = editGroup(temp);
			if (group == null) {
				cancelled = true;
				break;
			}

			var appointment = dp.teacherAppointmentsRepository().addTeacherAppointment(teacher, discipline, group);

			view.showEmpty();
			view.showList(List.of(new TeacherAppointmentsViewModel(appointment, teacher, discipline, group)),
					"Успешно добавлено назначение:");

		} while (askMore && view.askYesNo("Добавить ещё? (Д/н)", true));

		if (cancelled) {
			view.showText("Добавление отменено.");
			view.waitToProceed();
		}
	}

	private void edit(TeacherAppointment appointment) {
		view.clear();
		view.showText("РЕДАКТИРОВАНИЕ НАЗНАЧЕНИЯ");

		boolean cancelled = false;
		do {
			if (appointment == null) {
				appointment = askAppointment();
				if (appointment == null) {
					cancelled = true;
					break;
				}
			}

			do {
				view.clear();
				var viewModel = createViewModel(appointment);
				view.showList(List.of(viewModel), "РЕДАКТИРОВАНИЕ НАЗНАЧЕНИЯ");

				var menuModel = Commons.EDIT_TEACHER_APPOINTMENT_MENU;
				view.showMenu(menuModel);
				Object userChoice = view.askUserChoice("Выберите параметр для изменения: ", menuModel);
				if (userChoice.equals(Commons.CMD_GO_BACK)) {
					break;
				}

				var bkpAppointment = new TeacherAppointment(appointment);

				if (userChoice instanceof Integer menuId) {
					Object result = null;
					switch (menuId) {
						case 1 -> result = editTeacher(appointment);
						case 2 -> result = editDiscipline(appointment);
						case 3 -> result = editGroup(appointment);
						default -> throw new NoSuchElementException();
					}

					if (result != null && view.askYesNo("Сохранить изменения? (Д/н)", true)) {
						if (dp.teacherAppointmentsRepository().updateTeacherAppointment(appointment)) {
							view.showText("Изменения успешно сохранены.");
						} else {
							view.showText("Что-то пошло не так. Изменения не были сохранены.");
						}
						view.waitToProceed();
					} else {
						appointment = bkpAppointment;
					}
				}

			} while (true); // GO_BACK see above

			appointment = null;

		} while (view.askYesNo("Редактировать другое назначение? (Д/н)", true));

		if (cancelled) {
			view.showText("Редактирование отменено.");
		}
		view.waitToProceed();
	}

	private Teacher editTeacher(TeacherAppointment appointment) {
		assert appointment != null;
		var teacher = askTeacher();
		if (teacher != null) {
			view.showText("Новый учитель: " + TeacherViewModel.teacherSimplifiedRepr(teacher));
			appointment.setTeacherId(teacher.getTeacherId());
			return teacher;
		}
		return null;
	}

	private Discipline editDiscipline(TeacherAppointment appointment) {
		assert appointment != null;
		var discipline = askDiscipline();
		if (discipline != null) {
			view.showText("Новый предмет: " + DisciplineViewModel.disciplineSimplifiedRept(discipline));
			appointment.setDisciplineId(discipline.getDisciplineId());
			return discipline;
		}
		return null;
	}

	private Group editGroup(TeacherAppointment appointment) {
		assert appointment != null;
		var group = askGroup();
		if (group != null) {
			view.showText("Новая группа: " + GroupViewModel.groupSimplifiedRepr(group));
			appointment.setGroupId(group.getGroupId());
			return group;
		}
		return null;
	}

	private void delete(TeacherAppointment appointment) {
		view.clear();
		view.showText("УДАЛЕНИЕ НАЗНАЧЕНИЯ УЧИТЕЛЯ");
		boolean cancelled = false;
		do {
			if (appointment == null) {
				appointment = askAppointment();
				if (appointment == null) {
					cancelled = true;
					break;
				}
			}

			var viewModel = createViewModel(appointment);
			view.showList(List.of(viewModel), "Найдена запись:");

			if (view.askYesNo("Удалить? (д/Н)", false)) {
				int appointmentId = appointment.getAppointmentId();
				var teacherAppointmentsRepo = dp.teacherAppointmentsRepository();
				if (teacherAppointmentsRepo.removeTeacherAppointment(appointmentId)) {
					view.showText("Запись успешно удалена!");
				}
			} else {
				view.showText("Удаление отменено.");
			}

			appointment = null;

		} while (view.askYesNo("Удалить ещё? (Д/н)", true));

		if (cancelled) {
			view.showText("Удаление отменено.");
		}
		view.waitToProceed();
	}
}
