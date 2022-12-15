package edu.oop.schooladmin.consoleclient;

import java.nio.charset.Charset;
import java.nio.charset.spi.CharsetProvider;
import java.util.Scanner;

import edu.oop.schooladmin.consoleclient.controllers.MainController;
import edu.oop.schooladmin.model.implementations.testdb.TestDbProvider;
import edu.oop.schooladmin.model.interfaces.DataProvider;

public class App {
	public static void main(String[] args) {

		DataProvider dataProvider = new TestDbProvider();
		Scanner consoleInputScanner = new Scanner(System.in);
		MainController mainController = new MainController(dataProvider, consoleInputScanner);

		mainController.runLifecycle();
	}
}
