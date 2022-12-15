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
			var menuModel = Commons.MAIN_MENU;
			view.showMenu(menuModel);
			Object userChoice = view.askUserChoice(Commons.MENU_MAKE_YOUR_CHOICE, menuModel);
			if (userChoice.equals(Commons.CMD_EXIT) || userChoice.equals(Commons.CMD_GO_BACK)) {

				forceExit();

			} else if (userChoice instanceof Integer menuId) {

				var controller = selectController(menuId);
				controller.runLifecycle();
			}

		} while (true);
	}

	private ControllerBase selectController(int menuId) {
		var dummyController = new ControllerBase(dp, view) {
			@Override
			void runLifecycle() {
				System.out.println("Приветики. Тут пока ничего.");
				view.waitEnterToProceed();
			}
		};

		return switch (menuId) {
			case 1 -> studentsController;
			case 2 -> teachersController;
			case 3 -> groupsController;
			case 4 -> dummyController;
			case 5 -> dummyController;
			default -> {
				throw new IllegalStateException();
			}
		};
	}
}
