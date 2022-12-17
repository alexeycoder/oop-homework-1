package edu.oop.schooladmin.client.controllers;

import java.util.Map;

import edu.oop.schooladmin.client.viewmodels.Commons;
import edu.oop.schooladmin.client.views.ViewBase;
import edu.oop.schooladmin.model.interfaces.DataProvider;

public class MainController extends ControllerBase {

	private final ControllerBase disciplinesController;
	private final ControllerBase teachersController;
	private final ControllerBase studentsController;
	private final ControllerBase groupsController;
	private final ControllerBase teacherAppointmentsController;
	private final ControllerBase ratingsController;

	public MainController(DataProvider dataProvider, ViewBase viewManager) {
		super(dataProvider, viewManager);

		ControllersBag bag = new ControllersBag();
		this.disciplinesController = new DisciplinesController(dataProvider, viewManager, bag);
		this.teachersController = new TeachersController(dataProvider, viewManager, bag);
		this.studentsController = new StudentsController(dataProvider, viewManager, bag);
		this.groupsController = new GroupsController(dataProvider, viewManager, bag);
		this.teacherAppointmentsController = new TeacherAppointmentsController(dataProvider, viewManager, bag);
		this.ratingsController = new RatingsController(dataProvider, viewManager, bag);
	}

	@Override
	protected Map<Object, String> getMenuModel() {
		return Commons.MAIN_MENU;
	}

	@Override
	protected void switchToAction(int menuId, Integer entityId) {
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
			protected void switchToAction(int menuId, Integer entityId) {
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
			case 4 -> disciplinesController;
			case 5 -> dummyController;
			default -> {
				throw new IllegalStateException();
			}
		};
	}

	protected class ControllersBag {
		public ControllerBase disciplinesController() {
			return disciplinesController;
		}

		public ControllerBase teachersController() {
			return teachersController;
		}

		public ControllerBase studentsController() {
			return studentsController;
		}

		public ControllerBase groupsController() {
			return groupsController;
		}

		public ControllerBase teacherAppointmentsController() {
			return teacherAppointmentsController;
		}

		public ControllerBase ratingsController() {
			return ratingsController;
		}
	}

}
