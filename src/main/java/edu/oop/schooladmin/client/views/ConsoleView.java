package edu.oop.schooladmin.client.views;

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
	private static final String EXIT_APP_NOTE = "Вы завершили программу.";

	public void showGoodbye() {
		System.out.println();
		System.out.println(EXIT_APP_NOTE);
	}

	public void showMenu(Map<Object, String> menuModel) {

		StringBuilder sb = new StringBuilder();
		if (menuModel.containsKey(Commons.HEADER_KEY)) {
			sb.append(menuModel.get(Commons.HEADER_KEY)).append("\n\n");
		}

		int maxKey = menuModel.keySet().stream().filter(o -> o instanceof Integer)
				.mapToInt(o -> (Integer) o).max().getAsInt();
		for (int key = 1; key <= maxKey; ++key) {
			if (menuModel.containsKey(key)) {
				var menuItemValue = menuModel.get(key);
				sb.append(key).append(" \u2014 ").append(menuItemValue).append("\n");
			}
		}
		boolean goBack = menuModel.containsKey(Commons.CMD_GO_BACK);
		boolean exit = menuModel.containsKey(Commons.CMD_EXIT);
		if (goBack || exit) {

			sb.append("\n");
			if (goBack) {
				sb.append(Commons.CMD_GO_BACK).append(" \u2014 ").append(menuModel.get(Commons.CMD_GO_BACK));
			}
			if (exit) {
				if (goBack) {
					sb.append("\n");
				}
				sb.append(Commons.CMD_EXIT).append(" \u2014 ").append(menuModel.get(Commons.CMD_EXIT));
			}
			sb.append("\n");
		}

		Console.clearScreen();
		System.out.println(sb.toString());
	}

	public Object askUserChoice(String askModel, Map<Object, String> menuModel) {
		Object choice = Console.getUserInputVaryType(SCANNER, askModel, inp -> menuModel.containsKey(inp),
				WARN_WRONG_MENU_ITEM);
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
