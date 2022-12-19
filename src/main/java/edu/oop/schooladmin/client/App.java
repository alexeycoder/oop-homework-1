package edu.oop.schooladmin.client;

import java.util.Locale;

import edu.oop.schooladmin.client.controllers.ControllerBase;
import edu.oop.schooladmin.client.controllers.MainController;
import edu.oop.schooladmin.client.views.ConsoleView;
import edu.oop.schooladmin.client.views.ViewBase;
import edu.oop.schooladmin.model.implementations.testdb.TestDbProvider;
import edu.oop.schooladmin.model.interfaces.DataProvider;

public class App {
	public static void main(String[] args) {
		Locale.setDefault(AppSettings.LOCALE);

		DataProvider dataProvider = new TestDbProvider();
		ViewBase consoleView = new ConsoleView();
		ControllerBase mainController = new MainController(dataProvider, consoleView);

		mainController.runLifecycle();
	}
}
