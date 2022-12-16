package edu.oop.schooladmin.client.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.OptionalInt;

import edu.oop.schooladmin.client.viewmodels.Commons;
import edu.oop.schooladmin.client.viewmodels.TeacherViewModel;
import edu.oop.schooladmin.client.views.ViewBase;
import edu.oop.schooladmin.model.interfaces.DataProvider;

public class TeachersController extends ControllerBase {

	public TeachersController(DataProvider dataProvider, ViewBase viewManager) {
		super(dataProvider, viewManager);
	}

	@Override
	protected Map<Object, String> getMenuModel() {
		return Commons.TEACHERS_MENU;
	}

	@Override
	protected void switchToAction(int menuId) {
		switch (menuId) {
			case 1 -> showAll();
			case 2 -> showOneById();
			case 3 -> showOneByName();
			case 4 -> dummyAction();
			case 5 -> dummyAction();
			case 6 -> dummyAction();
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

	private void showOneById() {
		view.clear();
		view.showText("ПОИСК УЧИТЕЛЯ ПО ID");
		do {
			OptionalInt answer = view.askInteger("\nВведите ID (или пустой Ввод для отмены): ", 0, null);
			if (answer.isEmpty()) {
				return;
			}

			int id = answer.getAsInt();
			var teachersRepo = dp.teachersRepository();
			var teacher = teachersRepo.getTeacherById(id);
			if (teacher == null) {
				view.showText(String.format("Записи для ID %d не найдено.\n", id));
				continue;
			}

			var groupsRepo = dp.groupsRepository();
			var groups = groupsRepo.getGroupsByTeacherId(id);
			TeacherViewModel viewModel = new TeacherViewModel(teacher, groups);
			view.showList(List.of(viewModel), "Найдена запись:");

		} while (view.askYesNo("Повторить поиск? (Y/n)", true));
	}

	private void showOneByName() {
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
}
