package edu.oop.schooladmin.model.dblayer.interfaces;

import java.util.stream.Stream;

/** Абстракция таблицы БД и поддерживаемых низкоуровневых запросов, CRUD */
public interface Queryable<T> {
	T add(T entry);

	Stream<T> queryAll();

	T get(int id);

	boolean update(T entry);

	T delete(int id);
}
