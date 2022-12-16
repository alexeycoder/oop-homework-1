package edu.oop.schooladmin.client.controllers;

import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;

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
			case 2 -> dummyAction();
			case 3 -> dummyAction();
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

		view.showList(resultList, "СПИСОК УЧИТЕЛЕЙ:");
		view.waitEnterToProceed();
	}
}
