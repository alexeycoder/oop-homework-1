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

			view.showMenu(Commons.STUDENTS_MENU);
			int userChoice = view.askUserChoice(Commons.MENU_MAKE_YOUR_CHOICE, Commons.STUDENTS_MENU);
			if (userChoice == Commons.DEFAULT_EXIT_CMD) {
				return;
			}

			switch (userChoice) {
				case 1 -> {
					showAll();
					view.waitEnterToProceed();
				}
				default -> throw new NoSuchElementException();
			}

		} while (true);
	}

	private void showAll() {
		ArrayList<StudentViewModel> resultList = new ArrayList<>();

		var studentsRepo = dp.studentsRepository();
		var groupsRepo = dp.groupsRepository();
		for (var student : studentsRepo.getAllStudents()) {
			var group = groupsRepo.getGroupById(student.getGroupId());
			resultList.add(new StudentViewModel(student, group));
		}

		view.showList(resultList, "Список учеников:");
	}

}
