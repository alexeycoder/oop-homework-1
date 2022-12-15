package edu.oop.schooladmin.client.controllers;

import edu.oop.schooladmin.client.viewmodels.Commons;
import edu.oop.schooladmin.client.views.ViewBase;
import edu.oop.schooladmin.model.interfaces.DataProvider;

public class MainController extends ControllerBase {
	private final ControllerBase teachersController;
	private final ControllerBase studentsController;
	private final ControllerBase groupsController;

	public MainController(DataProvider dataProvider, ViewBase viewManager) {
		super(dataProvider, viewManager);
		this.teachersController = new TeachersController(dataProvider, viewManager);
		this.studentsController = new StudentsController(dataProvider, viewManager);
		this.groupsController = new GroupsController(dataProvider, viewManager);
	}

	public void runLifecycle() {
		do {

			view.showMenu(Commons.MAIN_MENU);
			int userChoice = view.askUserChoice(Commons.MENU_MAKE_YOUR_CHOICE, Commons.MAIN_MENU);
			if (userChoice == Commons.DEFAULT_EXIT_CMD) {
				return;
			}
			var controller = selectController(userChoice);
			controller.runLifecycle();

		} while (true);
	}

	private ControllerBase selectController(int choice) {
		return switch (choice) {
			case 1 -> studentsController;
			case 2 -> teachersController;
			case 3 -> groupsController;
			default -> {
				throw new IllegalStateException(" ");
			}
		};
	}
}
