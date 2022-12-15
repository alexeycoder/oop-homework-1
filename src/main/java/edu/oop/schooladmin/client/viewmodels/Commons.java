package edu.oop.schooladmin.client.viewmodels;

import java.util.Map;

public class Commons {
	public static final int CMD_GO_BACK = 0;
	public static final String CMD_EXIT = "q";
	public static final String HEADER_KEY = "header";

	public static final String MENU_MAKE_YOUR_CHOICE = "Выберите пункт меню: ";

	public static final Map<Object, String> MAIN_MENU = Map.of(
			HEADER_KEY, "ГЛАВНОЕ МЕНЮ",
			1, "Ученики",
			2, "Учителя",
			3, "Группы",
			4, "Предметы",
			5, "Журнал оценок",

			CMD_EXIT, "Завершить работу");

	public static final Map<Object, String> STUDENTS_MENU = Map.of(
			HEADER_KEY, "УЧЕНИКИ",
			1, "Вывести список всех учеников",
			2, "Найти ученика по ID",
			3, "Найти ученика по имени и/или фамилии",
			4, "Добавить ученика",
			5, "Редактировать ученика",
			6, "Удалить ученика",

			CMD_GO_BACK, "Вернуться в главное меню",
			CMD_EXIT, "Завершить работу");

	public static final Map<Object, String> TEACHERS_MENU = Map.of(
			HEADER_KEY, "УЧИТЕЛЯ",
			1, "Вывести список всех учителей",
			2, "Найти учителя по ID",
			3, "Найти учителя по имени и/или фамилии",
			4, "Добавить учителя",
			5, "Редактировать учителя",
			6, "Удалить учителя",

			CMD_GO_BACK, "Вернуться в главное меню",
			CMD_EXIT, "Завершить работу");

	public static final Map<Object, String> GROUPS_MENU = Map.of(
			HEADER_KEY, "ГРУППЫ",
			1, "Вывести список всех групп",
			2, "Добавить группу",
			3, "Редактировать группу",
			4, "Удалить группу",

			CMD_GO_BACK, "Вернуться в главное меню",
			CMD_EXIT, "Завершить работу");
}
