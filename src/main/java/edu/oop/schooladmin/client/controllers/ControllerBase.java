package edu.oop.schooladmin.client.controllers;

import java.util.Map;
import java.util.OptionalInt;

import edu.oop.schooladmin.client.AppSettings;
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

	protected abstract void switchToAction(int menuId, Object relatedEntity);

	protected void dummyAction() {
		System.out.println("Приветики. Тут пока ничего.");
		view.waitToProceed();
	}

	protected void forceExit() {
		view.showGoodbye();
		System.exit(0);
	}

	protected OptionalInt findSuitableMenuId(Map<Object, String> menuModel, String stringSample) {
		assert menuModel != null && stringSample != null && !stringSample.isEmpty();

		final String sample = stringSample.toLowerCase(AppSettings.LOCALE);

		var key = menuModel.entrySet().stream()
				.filter(entry -> entry.getValue() != null
						&& entry.getValue().toLowerCase(AppSettings.LOCALE).contains(sample))
				.findFirst();
		if (key.isPresent() && key.get().getKey() instanceof Integer menuId) {
			return OptionalInt.of(menuId);
		}
		return OptionalInt.empty();
	}
}
