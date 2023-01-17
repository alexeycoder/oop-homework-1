package edu.oop.schooladmin.client;

import java.sql.SQLException;
import java.util.Locale;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private static final Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		logger.info("Application started.");

		Locale.setDefault(AppSettings.LOCALE);
		ViewBase consoleView = new ConsoleView();

		try (DbLayerContext dbContext = selectDbContext()) {

			DataProvider dataProvider = new DataProviderImpl(dbContext);
			ControllerBase mainController = new MainController(dataProvider, consoleView);
			mainController.runLifecycle();

		} catch (Exception ex) {
			logger.error("Exception has occurred in the main().", ex);
			consoleView.showText("При выполнении приложения произошла ошибка");
			// consoleView.showText(ex.getMessage());
		}

		logger.info("Application finished.");
	}

	private static DbLayerContext selectDbContext() throws SQLException {
		return switch (AppSettings.DATA_SOURCE) {
			case INMEMORY -> new InMemoryDbContext();
			case SQLITE -> new SqliteDbContext(AppSettings.DATABASE_URL);
			default -> throw new NoSuchElementException();
		};
	}
}
