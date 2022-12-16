package edu.oop.schooladmin.client.controllers;

import java.util.Map;

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

	@Override
	protected Map<Object, String> getMenuModel() {
		return Commons.MAIN_MENU;
	}

	@Override
	protected void switchToAction(int menuId) {
		var controller = selectController(menuId);
		controller.runLifecycle();
	}

	private ControllerBase selectController(int menuId) {

		var dummyController = new ControllerBase(dp, view) {

			@Override
			public void runLifecycle() {
				System.out.println("Приветики. Тут пока ничего.");
				view.waitToProceed();
			}

			@Override
			protected void switchToAction(int menuId) {
			}

			@Override
			protected Map<Object, String> getMenuModel() {
				return null;
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
