package edu.oop.schooladmin.client.controllers;

import edu.oop.schooladmin.client.views.Menu;
import edu.oop.schooladmin.client.views.ViewBase;
import edu.oop.schooladmin.model.interfaces.DataProvider;

public class TeachersController extends ControllerBase {



	public TeachersController(DataProvider dataProvider, ViewBase viewManager) {
		super(dataProvider, viewManager);
	}

	@Override
	void runLifecycle() {
		do {

			Menu.teacherMenu();



		} while (false);

	}

	// private void showAllTeachers(name, cartId) {

	// // View

	// // var rep =dp.teachersRepository();
	// // rep.getAllTeachers();
	// // rep.

	// // List<String> items =

	// // View.showAllTeachers()

	// }

}
