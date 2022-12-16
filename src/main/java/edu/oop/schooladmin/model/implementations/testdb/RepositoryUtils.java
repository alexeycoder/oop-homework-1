package edu.oop.schooladmin.model.implementations.testdb;

import java.util.List;
import java.util.function.ToIntFunction;

public class RepositoryUtils {
	/** Определяет значение крайнего Первичного Ключа в таблице */
	public static <T> int getLastPrimaryKey(List<T> table, ToIntFunction<T> toPrimaryKeyMapper) {
		int lastPk = 0;
		if (table.size() > 0) {
			lastPk = table.stream().mapToInt(toPrimaryKeyMapper).max().getAsInt();
		}
		return lastPk;
	}

	public static String getRegexForContainsAll(String[] substrings) {
		StringBuilder sb = new StringBuilder("(?iu)^");
		for (String str : substrings) {
			sb.append("(?=.*?").append(str).append(")");
		}
		sb.append(".*");
		return sb.toString();
	}

	// public static void main(String[] args) {
	// String[] substrings = { "пет", "ано" };
	// var regex = getRegexForContainsAll(substrings);
	// var sample = "Петр Иванов";
	// System.out.println(regex);
	// System.out.println(sample.matches(regex));
	// }
}
