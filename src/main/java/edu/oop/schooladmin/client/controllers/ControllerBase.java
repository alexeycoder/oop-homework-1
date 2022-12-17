package edu.oop.schooladmin.client.controllers;

import java.util.Map;

import edu.oop.schooladmin.client.viewmodels.Commons;
import edu.oop.schooladmin.client.views.ViewBase;
import edu.oop.schooladmin.model.interfaces.DataProvider;

public abstract class ControllerBase {

	protected final DataProvider dp;
	protected final ViewBase view;

	public ControllerBase(DataProvider dataProvider, ViewBase viewManager) {
		this.dp = dataProvider;
		this.view = viewManager;
	}

	/** Запуск жизненного цикла контроллера */
	public void runLifecycle() {
		do {
			var menuModel = getMenuModel();
			view.clear();
			view.showMenu(menuModel);
			Object userChoice = view.askUserChoice(Commons.MENU_MAKE_YOUR_CHOICE, menuModel);
			if (userChoice.equals(Commons.CMD_EXIT)) {

				forceExit();

			} else if (userChoice.equals(Commons.CMD_GO_BACK)) {

				return;

			} else if (userChoice instanceof Integer menuId) {

				switchToAction(menuId, null);
			}

		} while (true);
	}

	protected abstract Map<Object, String> getMenuModel();

	protected abstract void switchToAction(int menuId, Integer entityId);

	protected void dummyAction() {
		System.out.println("Приветики. Тут пока ничего.");
		view.waitToProceed();
	}

	protected void forceExit() {
		view.showGoodbye();
		System.exit(0);
	}
}
