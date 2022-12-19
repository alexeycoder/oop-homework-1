package edu.oop.schooladmin.client.controllers;

import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;

import edu.oop.schooladmin.client.controllers.MainController.ControllersBag;
import edu.oop.schooladmin.client.viewmodels.Commons;
import edu.oop.schooladmin.client.viewmodels.DisciplineViewModel;
import edu.oop.schooladmin.client.views.ViewBase;
import edu.oop.schooladmin.model.interfaces.DataProvider;

public class DisciplinesController extends ControllerBase {

	// private final ControllersBag controllersBag;

	public DisciplinesController(DataProvider dataProvider, ViewBase viewManager, ControllersBag controllersBag) {
		super(dataProvider, viewManager);
		if (controllersBag == null) {
			throw new NullPointerException("controllersBag");
		}
		// this.controllersBag = controllersBag;
	}

	@Override
	protected Map<Object, String> getMenuModel() {
		return Commons.DISCIPLINES_MENU;
	}

	@Override
	protected void switchToAction(int menuId, Object relatedEntity) {
		switch (menuId) {
			case 1 -> showAll();
			case 2 -> dummyAction();
			case 3 -> dummyAction();
			case 4 -> dummyAction();
			default -> throw new NoSuchElementException();
		}
	}

	private void showAll() {
		ArrayList<DisciplineViewModel> resultList = new ArrayList<>();

		var disciplinesRepo = dp.disciplinesRepository();
		var teachersRepo = dp.teachersRepository();

		for (var discipline : disciplinesRepo.getAllDisciplines()) {
			var teachers = teachersRepo.getTeachersByDisciplineId(discipline.getDisciplineId());
			resultList.add(new DisciplineViewModel(discipline, teachers));
		}
		view.clear();
		view.showList(resultList, "ПРЕДМЕТЫ");
		view.waitToProceed();
	}
}
