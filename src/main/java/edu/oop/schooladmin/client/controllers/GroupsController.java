package edu.oop.schooladmin.client.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import edu.oop.schooladmin.client.controllers.MainController.ControllersBag;
import edu.oop.schooladmin.client.viewmodels.Commons;
import edu.oop.schooladmin.client.viewmodels.GroupViewModel;
import edu.oop.schooladmin.client.viewmodels.TeacherViewModel;
import edu.oop.schooladmin.client.views.ViewBase;
import edu.oop.schooladmin.model.entities.Group;
import edu.oop.schooladmin.model.entities.Teacher;
import edu.oop.schooladmin.model.interfaces.DataProvider;

public class GroupsController extends ControllerBase {

	// private final ControllersBag controllersBag;

	public GroupsController(DataProvider dataProvider, ViewBase viewManager, ControllersBag controllersBag) {
		super(dataProvider, viewManager);
		if (controllersBag == null) {
			throw new NullPointerException("controllersBag");
		}
		// this.controllersBag = controllersBag;
	}

	@Override
	protected Map<Object, String> getMenuModel() {
		return Commons.GROUPS_MENU;
	}

	@Override
	protected void switchToAction(int menuId, Object relatedEntity) {
		switch (menuId) {
			case 1 -> showAll();
			case 2 -> addNew(relatedEntity instanceof Teacher teacher ? teacher : null);
			case 3 -> edit(relatedEntity instanceof Group group ? group : null);
			case 4 -> delete(relatedEntity instanceof Group group ? group : null);
			default -> throw new NoSuchElementException();
		}
	}

	private void showAll() {
		ArrayList<GroupViewModel> resultList = new ArrayList<>();

		var groupsRepo = dp.groupsRepository();
		var teachersRepo = dp.teachersRepository();
		for (var group : groupsRepo.getAllGroups()) {
			var teacher = group.getTeacherId() != null ? teachersRepo.getTeacherById(group.getTeacherId()) : null;
			resultList.add(new GroupViewModel(group, teacher));
		}
		view.clear();
		view.showList(resultList, "СПИСОК ГРУПП");
		view.waitToProceed();
	}

	private void addNew(Teacher teacherHomeroom) {
		view.clear();
		view.showText("ДОБАВЛЕНИЕ ГРУППЫ (КЛАССА)");

		boolean askMore = false;
		if (teacherHomeroom == null) {
			askMore = true;
			view.showEmpty();
			view.showText("Задайте классного руководителя:");
			teacherHomeroom = askTeacher();
			if (teacherHomeroom == null) {
				view.showText("Добавление отменено.");
				view.waitToProceed();
				return;
			}
		}
		view.showText(TeacherViewModel.teacherSimplifiedRepr(teacherHomeroom));

		boolean cancelled = false;
		do {
			view.showEmpty();
			var answer = view.askInteger("Введите номер учебного года (или пустой Ввод чтобы отменить): ", 1, 12);
			if (answer.isEmpty()) {
				cancelled = true;
				break;
			}
			var answer2 = view.askString("Введите букву класса (или пустой Ввод чтобы отменить): ",
					s -> s.length() > 0,
					"Некорректный ввод: требуется ввести букву класса.");
			if (answer2.isEmpty()) {
				cancelled = true;
				break;
			}

			Group group = dp.groupsRepository()
					.addGroup(new Group(null, answer.getAsInt(), answer2.get().charAt(0),
							teacherHomeroom.getTeacherId()));

			if (group != null) {
				view.showEmpty();
				view.showList(List.of(new GroupViewModel(group, teacherHomeroom)),
						"Успешно назначено классное руководство:");
			} else {
				view.showText("Что-то пошло не так.");
			}

		} while (askMore && view.askYesNo("Добавить ещё? (Д/н)", true));

		if (cancelled) {
			view.showText("Добавление отменено.");
			view.waitToProceed();
		}
	}

	private void edit(Group group) {
		view.clear();
		view.showText("ИЗМЕНЕНИЕ КЛАССНОГО РУКОВОДИТЕЛЯ");
		var teachersRepo = dp.teachersRepository();
		boolean cancelled = false;
		do {
			if (group == null) {
				group = askGroup();
				if (group == null) {
					cancelled = true;
					break;
				}
			}

			view.clear();
			view.showList(
					List.of(new GroupViewModel(group, teachersRepo.getTeacherById(group.getTeacherId()))),
					"ИЗМЕНЕНИЕ КЛАССНОГО РУКОВОДИТЕЛЯ");

			var teacher = askTeacher();
			if (teacher == null) {
				cancelled = true;
				break;
			}
			if (view.askYesNo(
					"Назначить классным руководителем " + TeacherViewModel.teacherSimplifiedRepr(teacher) + "? (Д/н)",
					true)) {
				group.setTeacherId(teacher.getTeacherId());
				var groupsRepo = dp.groupsRepository();
				groupsRepo.updateGroup(group);
			} else {
				view.showText("Назначение отменено.");
				view.waitToProceed();
			}

			group = null;

		} while (view.askYesNo("Изменить классного руководителя у другого класса? (Д/н)", true));

		if (cancelled) {
			view.showText("Редактирование отменено.");
		}
		view.waitToProceed();
	}

	private void delete(Group group) {
		view.clear();
		view.showText("УДАЛЕНИЕ ГРУППЫ");
		view.showText("(все ссылки на группу будут также удалены у учеников)");
		var teachersRepo = dp.teachersRepository();
		boolean cancelled = false;
		do {
			if (group == null) {
				group = askGroup();
				if (group == null) {
					cancelled = true;
					break;
				}
			}
			view.showList(
					List.of(new GroupViewModel(group, teachersRepo.getTeacherById(group.getTeacherId()))),
					null);

			if (dp.groupsRepository().removeGroup(group.getGroupId())) {
				view.showText("Группа успешно удалена.");
			} else {
				view.showText(
						"Невозможно удалить группу: вероятно всё-ещё имеются связанные с ней назначения учителей.");
			}

			group = null;
		} while (view.askYesNo("Редактировать другого учителя? (Д/н)", true));

		if (cancelled) {
			view.showText("Редактирование отменено.");
		}
		view.waitToProceed();
	}
}
