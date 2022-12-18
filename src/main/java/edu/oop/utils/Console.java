package edu.oop.utils;

import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Function;

public class Console {

	// consts

	private static final String EXIT_APP_CMD = "q";
	private static final String EXIT_APP_NOTE = "Вы завершили программу.";

	public static final String PLEASE_REPEAT = "Пожалуйста попробуйте снова.";
	private static final String ERR_NOT_INT = "Некорректный ввод: Требуется целое число. " + PLEASE_REPEAT;

	private static final String ERR_INT_MUST_BE_IN_RANGE = "Число должно быть в интервале от %d до %d! "
			+ PLEASE_REPEAT;
	private static final String ERR_INT_TOO_LOW = "Число не должно быть меньше %d! " + PLEASE_REPEAT;
	private static final String ERR_INT_TOO_HIGH = "Число не должно быть больше %d! " + PLEASE_REPEAT;

	public static String getUserInput(Scanner inputScanner, String prompt,
			Function<String, Boolean> checkIfValid, String wrongWarn) {

		boolean wrong = false;
		while (true) {
			if (wrong) {
				wrong = false;
				if (wrongWarn != null) {
					printError(wrongWarn, null);
				}
			}

			System.out.print(prompt);
			String inp = inputScanner.nextLine();
			if (inp.isEmpty()) {
				return null;
			}
			if (checkIfValid == null || checkIfValid.apply(inp)) {
				return inp;
			}
			wrong = true;
		}
	}

	public static Integer getUserInputInt(
			Scanner inputScanner, String prompt,
			Function<Integer, Boolean> checkIfValid,
			String warnOutOfRange) {

		boolean wrongType = false;
		boolean outOfRange = false;

		while (true) {
			if (wrongType) {
				wrongType = false;
				printError(ERR_NOT_INT, null);
			}
			if (outOfRange) {
				outOfRange = false;
				if (warnOutOfRange != null)
					printError(warnOutOfRange, null);
			}

			System.out.print(prompt);
			var rawInp = inputScanner.nextLine();
			if (rawInp.isBlank()) {
				return null;
			} else if (rawInp.toLowerCase().startsWith(EXIT_APP_CMD)) {
				forceExit();
			}
			var value = tryParseInt(rawInp);
			if (value != null) {
				if (checkIfValid == null || checkIfValid.apply(value)) {
					return value;
				}
				outOfRange = true;
			} else {
				wrongType = true;
			}
		}
	}

	public static Integer getUserInputIntRange(
			Scanner inputScanner, String prompt,
			Integer min, Integer max) {

		boolean isMinSet = min != null && !min.equals(Integer.MIN_VALUE);
		boolean isMaxSet = max != null && !max.equals(Integer.MAX_VALUE);

		boolean wrongType = false;
		boolean outOfRange = false;

		while (true) {
			if (wrongType) {
				wrongType = false;
				printError(ERR_NOT_INT, null);
			}
			if (outOfRange) {
				outOfRange = false;
				String errOutOfRange;
				if (isMinSet && isMaxSet) {
					errOutOfRange = String.format(ERR_INT_MUST_BE_IN_RANGE, min, max);
				} else if (isMinSet) {
					errOutOfRange = String.format(ERR_INT_TOO_LOW, min);
				} else {
					errOutOfRange = String.format(ERR_INT_TOO_HIGH, max);
				}
				printError(errOutOfRange, null);
			}

			System.out.print(prompt);
			var rawInp = inputScanner.nextLine();
			if (rawInp.isBlank()) {
				return null;
			} else if (rawInp.toLowerCase().startsWith(EXIT_APP_CMD)) {
				forceExit();
			}
			var value = tryParseInt(rawInp);
			if (value == null) {
				wrongType = true;
			} else {
				if (!(outOfRange = isOutOfRange(value, min, max))) {
					return value;
				}
			}
		}
	}

	private static boolean isOutOfRange(Integer value, Integer min, Integer max) {
		return (min != null && value < min) || (max != null && value > max);
	}

	public static Object getUserInputVaryType(
			Scanner inputScanner, String prompt,
			Function<Object, Boolean> checkIfValid,
			String warnIncorrect) {

		boolean outOfRange = false;

		while (true) {
			if (outOfRange) {
				outOfRange = false;
				if (warnIncorrect != null)
					Console.printError(warnIncorrect, null);
			}

			System.out.print(prompt);
			String rawInp = inputScanner.nextLine();
			Integer value = Console.tryParseInt(rawInp);
			Object result = value != null ? value : rawInp;
			if (checkIfValid == null || checkIfValid.apply(result)) {
				return result;
			}
			outOfRange = true;
		}
	}

	/**
	 * Затребует от пользователя ввод 'слов', разделённых пробелом, и возвращает
	 * массив из введённых слов.
	 * 
	 * @param inputScanner        Экземпляр Scanner, завязанного на поток ввода
	 *                            консоли
	 * @param prompt              Текст запроса пользователю
	 * @param suppressExitRequest Если false, то ввод символа q будет приводить к
	 *                            принудительному завершению приложения.
	 * @return Массив слов, введенных пользователем
	 */
	public static String[] getUserInputStringArray(Scanner inputScanner, String prompt, boolean suppressExitRequest) {
		System.out.print(prompt);
		var rawInp = inputScanner.nextLine();
		if (!suppressExitRequest) {
			if (rawInp.equalsIgnoreCase(EXIT_APP_CMD)) {
				forceExit();
			}
		}
		return rawInp.trim().split("\\s+");
	}

	public static boolean askYesNo(Scanner inputScanner, String prompt, boolean isYesDefault) {
		System.out.print(prompt);
		var answer = inputScanner.nextLine();

		if (answer.isBlank()) {
			return isYesDefault;
		}

		answer = answer.toLowerCase();
		if (answer.startsWith(EXIT_APP_CMD)) {
			forceExit();
		}

		if (answer.startsWith("y") || answer.startsWith("д")) {
			return true;
		} else if (answer.startsWith("n") || answer.startsWith("н")) {
			return false;
		} else {
			return isYesDefault;
		}
	}

	public static void forceExit() {
		System.out.println();
		System.out.println(EXIT_APP_NOTE);
		System.exit(0);
	}

	public static void clearScreen() {
		System.out.printf(EscapeCode.CLEAR.getCode());
	}

	public static void resetStyle() {
		System.out.print(EscapeCode.RESET.getCode());
	}

	public static void printError(String customMessage, Exception ex) {
		if (ex != null) {
			customMessage += "\n" + ex.getMessage();
		}
		System.err.println(EscapeCode.FG_RED.getCode() + customMessage + EscapeCode.RESET.getCode());
	}

	public static <T> void printArray(T[] array) {
		System.out.println(Arrays.toString(array));
	}

	// aux
	private static Integer tryParseInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
