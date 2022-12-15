package edu.oop.schooladmin.consoleclient.controllers;

import java.util.Scanner;

import edu.oop.schooladmin.consoleclient.views.Menu;
import edu.oop.schooladmin.model.interfaces.DataProvider;
import edu.oop.utils.Console;

public class TeachersController extends ControllerBase {

	public TeachersController(DataProvider dp, Scanner scanner) {
		super(dp, scanner);
	}

	@Override
	void runLifecycle() {
		do {
			Console.clearScreen();

			Menu.teacherMenu();

			int userChoice = Console.getUserInputIntRange(scanner, "Ваш выбор: ", 0, 5);

			doSomeActionDependingOnUserChoice();
			//

		} while (Console.askYesNo(scanner, "\nЗавершить работу (y/N)? ", false));

	}

	private void doSomeActionDependingOnUserChoice() {

	}

}
