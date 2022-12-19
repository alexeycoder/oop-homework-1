package edu.oop.schooladmin.client;

import java.util.Locale;

import edu.oop.schooladmin.client.controllers.MainController;
import edu.oop.schooladmin.client.views.ConsoleView;
import edu.oop.schooladmin.model.implementations.testdb.TestDbProvider;

public class App {
	public static void main(String[] args) {
		Locale.setDefault(AppSettings.LOCALE);

		var dataProvider = new TestDbProvider();
		var consoleView = new ConsoleView();
		MainController mainController = new MainController(dataProvider, consoleView);

		mainController.runLifecycle();
	}
}
