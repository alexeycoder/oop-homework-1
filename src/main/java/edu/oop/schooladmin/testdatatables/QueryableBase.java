package edu.oop.schooladmin.testdatatables;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;

public abstract class QueryableBase<T> implements Queryable<T> {
	private final ArrayList<T> table;
	private int lastId;

	protected QueryableBase(List<T> data) {
		this.table = new ArrayList<>(data);
		lastId = getLastPrimaryKey(this.table, this::getEntryId);
	}

	// protected abstract ArrayList<T> table();

	protected abstract Integer getEntryId(T entry);

	protected abstract void setEntryId(T entry, int id);

	protected abstract T getCopyOf(T entry);

	@Override
	public T add(T entry) {
		setEntryId(entry, ++lastId);
		table.add(getCopyOf(entry));
		return entry;
	}

	// @Override
	// public List<T> getAll() {
	// return table().stream().map(this::getCopyOf).toList();
	// }

	@Override
	public Stream<T> queryAll() {
		return table.stream().map(this::getCopyOf);
	}

	@Override
	public T get(int id) {
		for (var entry : table) {
			Integer entryId = getEntryId(entry);
			if (entryId != null && entryId.equals(id)) {
				return entry;
			}
		}
		return null;
	}

	@Override
	public boolean update(T entry) {
		Integer id = getEntryId(entry);
		if (id == null) {
			return false;
		}

		var dbEntry = get(id);
		if (dbEntry == null) {
			return false;
		}
		int lowLevelIndex = table.indexOf(dbEntry);
		table.set(lowLevelIndex, getCopyOf(entry));
		return true;
	}

	@Override
	public T delete(int id) {
		var dbEntry = get(id);
		if (dbEntry == null) {
			return null;
		}
		table.remove(dbEntry);
		return dbEntry;
		// lastId не трогаем, он только растишка
	}

	// aux

	/** Определяет значение крайнего Первичного Ключа в таблице */
	private static <T> int getLastPrimaryKey(List<T> table, ToIntFunction<T> toPrimaryKeyMapper) {
		int lastPk = 0;
		if (table.size() > 0) {
			lastPk = table.stream().mapToInt(toPrimaryKeyMapper).max().getAsInt();
		}
		return lastPk;
	}
}