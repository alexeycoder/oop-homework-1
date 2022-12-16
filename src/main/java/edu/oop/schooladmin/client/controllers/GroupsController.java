package edu.oop.schooladmin.client.controllers;

import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;

import edu.oop.schooladmin.client.viewmodels.Commons;
import edu.oop.schooladmin.client.viewmodels.GroupViewModel;
import edu.oop.schooladmin.client.views.ViewBase;
import edu.oop.schooladmin.model.interfaces.DataProvider;

public class GroupsController extends ControllerBase {

	public GroupsController(DataProvider dataProvider, ViewBase viewManager) {
		super(dataProvider, viewManager);
	}

	@Override
	protected Map<Object, String> getMenuModel() {
		return Commons.GROUPS_MENU;
	}

	@Override
	protected void switchToAction(int menuId) {
		switch (menuId) {
			case 1 -> showAll();
			case 2 -> dummyAction();
			case 3 -> dummyAction();
			case 4 -> dummyAction();
			default -> throw new NoSuchElementException();
		}
	}

	private void showAll() {
		ArrayList<GroupViewModel> resultList = new ArrayList<>();

		var groupsRepo = dp.groupsRepository();
		var teachersRepo = dp.teachersRepository();
		for (var group : groupsRepo.getAllGroups()) {
			var teacher = teachersRepo.getTeacherById(group.getTeacherId());
			resultList.add(new GroupViewModel(group, teacher));
		}
		view.clear();
		view.showList(resultList, "СПИСОК ГРУПП:");
		view.waitToProceed();
	}
}