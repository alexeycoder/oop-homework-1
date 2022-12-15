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
}
