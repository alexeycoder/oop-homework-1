package edu.oop.schooladmin.client.viewmodels;

import java.util.Map;

public class Commons {
	public static final int DEFAULT_EXIT_CMD = 0;

	public static final String MENU_MAKE_YOUR_CHOICE = "Выберите пункт меню: ";

	public static final Map<Integer, String> MAIN_MENU = Map.of(
			1, "Ученики",
			2, "Учителя",
			3, "Группы",
			4, "Предметы",
			5, "Журнал оценок",

			0, "Завершить работу");

	public static final Map<Integer, String> STUDENTS_MENU = Map.of(
			1, "Вывести список всех учеников",
			2, "Найти ученика по ID",
			3, "Найти ученика по имени и/или фамилии",
			4, "Добавить ученика",
			5, "Редактировать ученика",
			6, "Удалить ученика",

			0, "Вернуться в главное меню");
}
