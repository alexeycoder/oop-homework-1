package edu.oop.schooladmin.client.controllers;

import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;

import edu.oop.schooladmin.client.controllers.MainController.ControllersBag;
import edu.oop.schooladmin.client.viewmodels.Commons;
import edu.oop.schooladmin.client.viewmodels.DisciplineViewModel;
import edu.oop.schooladmin.client.views.ViewBase;
import edu.oop.schooladmin.model.interfaces.DataProvider;

public class TeacherAppointmentsController extends ControllerBase {

	private final ControllersBag controllersBag;

	public TeacherAppointmentsController(DataProvider dataProvider, ViewBase viewManager,
			ControllersBag controllersBag) {
		super(dataProvider, viewManager);
		if (controllersBag == null) {
			throw new NullPointerException("controllersBag");
		}
		this.controllersBag = controllersBag;
	}

	@Override
	protected Map<Object, String> getMenuModel() {
		return Commons.TEACHER_APPOINTMENTS_MENU;
	}

	@Override
	protected void switchToAction(int menuId, Integer entityId) {
		

	}
}