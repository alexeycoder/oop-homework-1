package edu.oop.schooladmin.client.views;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import edu.oop.schooladmin.client.viewmodels.Commons;
import edu.oop.schooladmin.client.viewmodels.ViewModelBase;
import edu.oop.utils.Console;

public class ConsoleView implements ViewBase {
	protected static final Scanner SCANNER = new Scanner(System.in);

	private static final String PROMPT_ENTER = "Нажмите Ввод чтобы продолжить...";
	private static final String WARN_WRONG_MENU_ITEM = "Некорректный ввод: требуется выбрать пункт меню. "
			+ Console.PLEASE_REPEAT;

	public void showMenu(Map<Integer, String> menuModel) {
		StringBuilder sb = new StringBuilder();
		int maxKey = Collections.max(menuModel.keySet());
		for (int key = 1; key <= maxKey; ++key) {
			if (menuModel.containsKey(key)) {
				var menuItemValue = menuModel.get(key);
				sb.append(key).append(" \u2014 ").append(menuItemValue).append("\n");
			}
		}
		int exit_cmd = Commons.DEFAULT_EXIT_CMD;
		if (menuModel.containsKey(exit_cmd)) {
			sb.append("\n")
					.append(exit_cmd).append(" \u2014 ").append(menuModel.get(exit_cmd))
					.append("\n");
		}

		Console.clearScreen();
		System.out.println(sb.toString());
	}

	public int askUserChoice(String askModel, Map<Integer, String> menuModel) {
		int choice = Console.getUserInputInt(SCANNER, askModel, c -> menuModel.containsKey(c), WARN_WRONG_MENU_ITEM);
		return choice;
	}

	public void waitEnterToProceed() {
		System.out.println(PROMPT_ENTER);
		SCANNER.nextLine();
	}

	public void showList(List<? extends ViewModelBase> viewModelsList, String title) {
		StringBuilder sb = new StringBuilder("\n");
		for (var viewModel : viewModelsList) {
			sb.append(viewModel.toString()).append("\n");
		}

		Console.clearScreen();
		System.out.println(title);
		System.out.println(sb.toString());
	}
}
