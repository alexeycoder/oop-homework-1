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

	public static String getRegexContainsAll(String words) {
		if (words == null || words.isEmpty()) {
			return "a^";
		}
		String[] substrings = words.strip().split("\\s+");
		return getRegexContainsAll(substrings);
	}

	public static String getRegexContainsAll(String[] substrings) {
		if (substrings == null || substrings.length == 0) {
			return "a^";
		}
		StringBuilder sb = new StringBuilder("(?iu)^");
		for (String str : substrings) {
			sb.append("(?=.*?").append(str).append(")");
		}
		sb.append(".*");
		return sb.toString();
	}
}
