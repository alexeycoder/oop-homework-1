package edu.oop.schooladmin.client;

import java.sql.SQLException;
import java.util.Locale;
import java.util.NoSuchElementException;

import edu.oop.schooladmin.client.controllers.ControllerBase;
import edu.oop.schooladmin.client.controllers.MainController;
import edu.oop.schooladmin.client.views.ConsoleView;
import edu.oop.schooladmin.client.views.ViewBase;
import edu.oop.schooladmin.model.businesslevel.implementations.DataProviderImpl;
import edu.oop.schooladmin.model.businesslevel.interfaces.DataProvider;
import edu.oop.schooladmin.model.dblayer.implementations.inmemory.InMemoryDbContext;
import edu.oop.schooladmin.model.dblayer.implementations.sqlite.SqliteDbContext;
import edu.oop.schooladmin.model.dblayer.interfaces.DbLayerContext;

public class App {
	public static void main(String[] args) {
		Locale.setDefault(AppSettings.LOCALE);
		ViewBase consoleView = new ConsoleView();

		try (DbLayerContext dbContext = selectDbContext()) {

			DataProvider dataProvider = new DataProviderImpl(dbContext);
			ControllerBase mainController = new MainController(dataProvider, consoleView);
			mainController.runLifecycle();

		} catch (Exception ex) {
			consoleView.showText("При выполнении приложения произошла ошибка");
			consoleView.showText(ex.getMessage());
		}
	}

	private static DbLayerContext selectDbContext() throws SQLException {
		return switch (AppSettings.DATA_SOURCE) {
			case INMEMORY -> new InMemoryDbContext();
			case SQLITE -> new SqliteDbContext(AppSettings.DATABASE_URL);
			default -> throw new NoSuchElementException();
		};
	}
}
