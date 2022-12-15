package edu.oop.schooladmin.client.controllers;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import edu.oop.schooladmin.client.viewmodels.Commons;
import edu.oop.schooladmin.client.viewmodels.StudentViewModel;
import edu.oop.schooladmin.client.views.ViewBase;
import edu.oop.schooladmin.model.interfaces.DataProvider;

public class StudentsController extends ControllerBase {

	public StudentsController(DataProvider dataProvider, ViewBase viewManager) {
		super(dataProvider, viewManager);
	}

	@Override
	void runLifecycle() {
		do {
			var menuModel = Commons.STUDENTS_MENU;
			view.showMenu(menuModel);
			Object userChoice = view.askUserChoice(Commons.MENU_MAKE_YOUR_CHOICE, menuModel);
			if (userChoice.equals(Commons.CMD_EXIT)) {
				forceExit();
			} else if (userChoice.equals(Commons.CMD_GO_BACK)) {
				return;
			} else if (userChoice instanceof Integer menuId) {

				switchToAction(menuId);
			}

		} while (true);
	}

	private void switchToAction(int menuId) {
		switch (menuId) {
			case 1 -> showAll();
			case 2 -> dummyAction();
			case 3 -> dummyAction();
			case 4 -> dummyAction();
			case 5 -> dummyAction();
			case 6 -> dummyAction();
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

		view.showList(resultList, "СПИСОК УЧЕНИКОВ:");
		view.waitEnterToProceed();
	}

}
