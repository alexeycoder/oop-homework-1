package edu.oop.schooladmin.consoleclient;

import java.util.Scanner;

import edu.oop.schooladmin.consoleclient.controllers.MainController;
import edu.oop.schooladmin.model.implementations.testdb.TestDbProvider;

public class App {
	public static void main(String[] args) {
		TestDbProvider dataProvider = new TestDbProvider();
		Scanner consoleInputScanner = new Scanner(System.in);
		MainController mainController = new MainController(dataProvider, consoleInputScanner);

		mainController.runLifecycle();
	}
}
