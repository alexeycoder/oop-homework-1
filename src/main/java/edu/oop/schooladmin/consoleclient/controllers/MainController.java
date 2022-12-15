package edu.oop.schooladmin.consoleclient.controllers;

import java.util.Scanner;

import edu.oop.schooladmin.consoleclient.views.MainView;
import edu.oop.schooladmin.consoleclient.views.Menu;
import edu.oop.schooladmin.model.interfaces.DataProvider;
import edu.oop.utils.Console;

public class MainController extends ControllerBase {
	private final ControllerBase teachersController;
	private final ControllerBase studentsController;
	private final ControllerBase groupsController;

	public MainController(DataProvider dp, Scanner scanner) {
		super(dp, scanner);
		this.teachersController = new TeachersController(dp, scanner);
		this.studentsController = new StudentsController(dp, scanner);
		this.groupsController = new GroupsController(dp, scanner);
	}

	public void runLifecycle() {
		do {
			Console.clearScreen();

			Menu.mainMenu();

			int userChoice = Console.getUserInputIntRange(scanner, "Ваш выбор: ", 0, 5);
			var controller = selectController(userChoice);
			controller.runLifecycle();

		} while (Console.askYesNo(scanner, "\nЗавершить работу (y/N)? ", false));
	}

	private ControllerBase selectController(int choice) {
		return switch (choice) {
			case 1 -> teachersController;
			case 2 -> studentsController;
			case 3 -> groupsController;
			default -> {
				throw new IllegalStateException(" ");
			}
		};
	}
}
