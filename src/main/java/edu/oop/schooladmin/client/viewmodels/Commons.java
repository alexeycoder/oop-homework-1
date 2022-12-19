package edu.oop.schooladmin.client.viewmodels;

import java.util.Map;

public class Commons {
	public static final int CMD_GO_BACK = 0;
	public static final String CMD_EXIT = "й";
	public static final String HEADER_KEY = "header";

	public static final String MENU_MAKE_YOUR_CHOICE = "Выберите пункт меню: ";

	public static final Map<Object, String> MAIN_MENU = Map.of(
			HEADER_KEY, "ГЛАВНОЕ МЕНЮ",
			1, "Ученики",
			2, "Учителя",
			3, "Группы",
			4, "Предметы",
			5, "Назначения",
			6, "Журнал оценок",

			CMD_EXIT, "Завершить работу");

	public static final Map<Object, String> STUDENTS_MENU = Map.of(
			HEADER_KEY, "УЧЕНИКИ",
			1, "Вывести список всех учеников",
			2, "Вывести учеников группы",
			3, "Найти ученика по ID",
			4, "Найти ученика по имени и/или фамилии",
			5, "Добавить ученика",
			6, "Редактировать ученика",
			7, "Удалить ученика",

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
			3, "Изменить классного руководителя",
			4, "Удалить группу",

			CMD_GO_BACK, "Вернуться в главное меню",
			CMD_EXIT, "Завершить работу");

	public static final Map<Object, String> DISCIPLINES_MENU = Map.of(
			HEADER_KEY, "ПРЕДМЕТЫ",
			1, "Вывести список всех предметов",
			2, "Добавить предмет",
			// 3, "Редактировать предмет",
			3, "Удалить предмет",

			CMD_GO_BACK, "Вернуться в главное меню",
			CMD_EXIT, "Завершить работу");

	public static final Map<Object, String> TEACHER_APPOINTMENTS_MENU = Map.of(
			HEADER_KEY, "НАЗНАЧЕНИЯ УЧИТЕЛЕЙ",
			1, "Вывести список всех назначений",
			2, "Назначения по учителю",
			3, "Назначения по предмету",
			4, "Назначения по группе",
			5, "Добавить назначение",
			6, "Редактировать назначение",
			7, "Удалить назначение",

			CMD_GO_BACK, "Вернуться в главное меню",
			CMD_EXIT, "Завершить работу");

	public static final Map<Object, String> RATINGS_MENU = Map.of(
			HEADER_KEY, "ЖУРНАЛ ОЦЕНОК",
			1, "Все оценки в хронолигическом порядке",
			2, "Все оценки ученика",
			3, "Добавить оценку",
			// 4, "Редактировать оценку",
			4, "Удалить оценку",

			CMD_GO_BACK, "Вернуться в главное меню",
			CMD_EXIT, "Завершить работу");

	public static final Map<Object, String> EDIT_STUDENT_MENU = Map.of(
			// HEADER_KEY, "РЕДАКТИРОВАНИЕ УЧЕНИКА",
			1, "Имя/Фамилия",
			2, "Дата рождения",
			3, "Категория",

			CMD_GO_BACK, "Продолжить");

	public static final Map<Object, String> EDIT_TEACHER_MENU = Map.of(
			// HEADER_KEY, "РЕДАКТИРОВАНИЕ УЧИТЕЛЯ",
			1, "Имя/Фамилия",
			2, "Дата рождения",
			3, "Учительская категория",
			4, "Классное руководство",

			CMD_GO_BACK, "Продолжить");

	public static final Map<Object, String> EDIT_TEACHER_APPOINTMENT_MENU = Map.of(
			// HEADER_KEY, "РЕДАКТИРОВАНИЕ УЧИТЕЛЯ",
			1, "Выбрать другого учителя",
			2, "Изменить предмет",
			3, "Изменить группу",

			CMD_GO_BACK, "Продолжить");
}
