package edu.oop.schooladmin.model.dblayer.implementations.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.stream.Collectors;

import org.sqlite.JDBC;
import org.sqlite.SQLiteConfig;

import edu.oop.schooladmin.client.AppSettings;
import edu.oop.schooladmin.model.dblayer.implementations.inmemory.InMemoryDbContext;
import edu.oop.schooladmin.model.dblayer.interfaces.DbLayerContext;

/**
 * Вспомогательный класс.
 * Создатель тестового экземпляра файловой БД SQLite, используя для содержимого
 * данные, "зашитые" в тестовой in-memory db.
 * Предполагается для использования лишь один раз для создания файла БД
 * и наполнения БД начальными данными.
 */
public class DbCreator {
	static {
		try {
			// Class.forName("org.sqlite.JDBC");
			DriverManager.registerDriver(new JDBC());
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
	}

	private final DbLayerContext dbContext;

	public DbCreator() {
		this.dbContext = new InMemoryDbContext();
	}

	private boolean create() {

		SQLiteConfig config = new SQLiteConfig();
		config.enforceForeignKeys(true);

		try (Connection connection = DriverManager.getConnection(AppSettings.DATABASE_URL, config.toProperties());
				Statement statement = connection.createStatement()) {
			System.out.println("Connected");

			create_disciplines_table(statement);
			create_teachers_table(statement);

			create_groups_table(statement);
			create_students_table(statement);
			create_ratings_table(statement);
			create_teacher_appointments_table(statement);

		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			return false;
		}

		return true;
	}

	private void create_disciplines_table(Statement statement) {
		var tableName = "disciplines";
		var table = dbContext.disciplines();

		if (!checkIfTableExists(statement, tableName)) {
			String createTableQuery = String.format("""
					CREATE TABLE %s (
						discipline_id INTEGER PRIMARY KEY,
						name TEXT NOT NULL
						);
						""", tableName);
			executeUpdateQuery(statement, createTableQuery);
		}

		if (checkIfTableEmpty(statement, tableName)) {
			String values = table.queryAll()
					.map(d -> String.format("(%d, '%s')", d.getDisciplineId(), d.getName()))
					.collect(Collectors.joining(",\n"));

			StringBuilder insertIntoQuery = new StringBuilder("INSERT INTO ")
					.append(tableName).append(" (discipline_id, name)")
					.append("\nVALUES\n").append(values).append(";");

			executeUpdateQuery(statement, insertIntoQuery.toString());
		}
	}

	private void create_teachers_table(Statement statement) {
		var tableName = "teachers";
		var table = dbContext.teachers();

		if (!checkIfTableExists(statement, tableName)) {
			String createTableQuery = String.format("""
					CREATE TABLE %s (
						teacher_id INTEGER PRIMARY KEY,
						first_name TEXT NOT NULL,
						last_name TEXT NOT NULL,
						birth_date TEXT NOT NULL,
						grade INTEGER NOT NULL
						);
						""", tableName);
			executeUpdateQuery(statement, createTableQuery);
		}

		if (checkIfTableEmpty(statement, tableName)) {
			String values = table.queryAll()
					.map(t -> String.format("(%d, '%s', '%s', '%s', %d)",
							t.getTeacherId(),
							t.getFirstName(),
							t.getLastName(),
							t.getBirthDate().toString(),
							t.getGrade()))
					.collect(Collectors.joining(",\n"));

			StringBuilder insertIntoQuery = new StringBuilder("INSERT INTO ")
					.append(tableName)
					.append(" (teacher_id, first_name, last_name, birth_date, grade)")
					.append("\nVALUES\n").append(values).append(";");

			executeUpdateQuery(statement, insertIntoQuery.toString());
		}
	}

	private void create_groups_table(Statement statement) {
		var tableName = "groups";
		var table = dbContext.groups();
		var teachersTableName = "teachers";

		if (!checkIfTableExists(statement, tableName)) {
			String createTableQuery = String.format("""
					CREATE TABLE %s (
						group_id INTEGER PRIMARY KEY,
						class_year INTEGER NOT NULL,
						class_mark TEXT NOT NULL,
						teacher_id INTEGER NULL,
						FOREIGN KEY(teacher_id) REFERENCES %s(teacher_id)
						);
						""", tableName, teachersTableName);
			executeUpdateQuery(statement, createTableQuery);
		}

		if (checkIfTableEmpty(statement, tableName)) {
			String values = table.queryAll()
					.map(g -> String.format("(%d, %d, '%s', %d)",
							g.getGroupId(),
							g.getClassYear(),
							g.getClassMark(),
							g.getTeacherId()))
					.collect(Collectors.joining(",\n"));

			StringBuilder insertIntoQuery = new StringBuilder("INSERT INTO ")
					.append(tableName)
					.append(" (group_id, class_year, class_mark, teacher_id)")
					.append("\nVALUES\n").append(values).append(";");

			executeUpdateQuery(statement, insertIntoQuery.toString());
		}
	}

	private void create_students_table(Statement statement) {
		var tableName = "students";
		var table = dbContext.students();
		var groupsTableName = "groups";

		if (!checkIfTableExists(statement, tableName)) {
			String createTableQuery = String.format("""
					CREATE TABLE %s (
						student_id INTEGER PRIMARY KEY,
						first_name TEXT NOT NULL,
						last_name TEXT NOT NULL,
						birth_date TEXT NOT NULL,
						group_id INTEGER,
						FOREIGN KEY(group_id) REFERENCES %s(group_id)
						);
						""", tableName, groupsTableName);
			executeUpdateQuery(statement, createTableQuery);
		}

		if (checkIfTableEmpty(statement, tableName)) {
			String values = table.queryAll()
					.map(s -> String.format("(%d, '%s', '%s', '%s', %d)",
							s.getStudentId(),
							s.getFirstName(),
							s.getLastName(),
							s.getBirthDate().toString(),
							s.getGroupId()))
					.collect(Collectors.joining(",\n"));

			StringBuilder insertIntoQuery = new StringBuilder("INSERT INTO ")
					.append(tableName)
					.append(" (student_id, first_name, last_name, birth_date, group_id)")
					.append("\nVALUES\n").append(values).append(";");

			executeUpdateQuery(statement, insertIntoQuery.toString());
		}
	}

	private void create_ratings_table(Statement statement) {
		var tableName = "ratings";
		var table = dbContext.ratings();
		var studentsTableName = "students";
		var disciplinesTableName = "disciplines";

		if (!checkIfTableExists(statement, tableName)) {
			String createTableQuery = String.format("""
					CREATE TABLE %s (
						rating_id INTEGER PRIMARY KEY,
						student_id INTEGER NOT NULL,
						discipline_id INTEGER NOT NULL,
						date_time TEXT NOT NULL,
						value INTEGER NOT NULL,
						commentary TEXT,
						FOREIGN KEY(student_id) REFERENCES %s(student_id),
						FOREIGN KEY(discipline_id) REFERENCES %s(discipline_id)
						);
						""", tableName, studentsTableName, disciplinesTableName);
			executeUpdateQuery(statement, createTableQuery);
		}

		if (checkIfTableEmpty(statement, tableName)) {
			String values = table.queryAll()
					.map(r -> String.format("(%d, '%s', '%s', '%s', %d, '%s')",
							r.getRatingId(),
							r.getStudentId(),
							r.getDisciplineId(),
							r.getDateTime().toString(),
							r.getValue(),
							r.getCommentary().replace("'", "\\'")))
					.collect(Collectors.joining(",\n"));

			StringBuilder insertIntoQuery = new StringBuilder("INSERT INTO ")
					.append(tableName)
					.append(" (rating_id, student_id, discipline_id, date_time, value, commentary)")
					.append("\nVALUES\n").append(values).append(";");

			executeUpdateQuery(statement, insertIntoQuery.toString());
		}
	}

	private void create_teacher_appointments_table(Statement statement) {
		var tableName = "teacher_appointments";
		var table = dbContext.teacherAppointments();
		var teachersTableName = "teachers";
		var disciplinesTableName = "disciplines";
		var groupsTableName = "groups";

		if (!checkIfTableExists(statement, tableName)) {
			String createTableQuery = String.format("""
					CREATE TABLE %s (
						appointment_id INTEGER PRIMARY KEY,
						teacher_id INTEGER NOT NULL,
						discipline_id INTEGER,
						group_id INTEGER,
						FOREIGN KEY(teacher_id) REFERENCES %s(teacher_id),
						FOREIGN KEY(discipline_id) REFERENCES %s(discipline_id),
						FOREIGN KEY(group_id) REFERENCES %s(group_id)
						);
						""", tableName, teachersTableName, disciplinesTableName, groupsTableName);
			executeUpdateQuery(statement, createTableQuery);
		}

		if (checkIfTableEmpty(statement, tableName)) {
			String values = table.queryAll()
					.map(ta -> String.format("(%d, %d, %d, %d)",
							ta.getAppointmentId(),
							ta.getTeacherId(),
							ta.getDisciplineId(),
							ta.getGroupId()))
					.collect(Collectors.joining(",\n"));

			StringBuilder insertIntoQuery = new StringBuilder("INSERT INTO ")
					.append(tableName)
					.append(" (appointment_id, teacher_id, discipline_id, group_id)")
					.append("\nVALUES\n").append(values).append(";");

			executeUpdateQuery(statement, insertIntoQuery.toString());
		}
	}

	// aux

	private static boolean executeUpdateQuery(Statement statement, String query) {
		System.out.println(query.toString());
		try {
			statement.executeUpdate(query);
			return true;
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
		return false;
	}

	private static boolean checkIfTableEmpty(Statement statement, String tableName) {
		var query = String.format("SELECT * FROM %s LIMIT 1;", tableName);
		try (var cursor = statement.executeQuery(query)) {
			return !cursor.next();
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
		return false;
	}

	private static boolean checkIfTableExists(Statement statement, String tableName) {
		var query = String.format("SELECT name FROM sqlite_master WHERE type='table' AND name='%s';", tableName);
		try (var cursor = statement.executeQuery(query)) {
			return cursor.next();
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
		return false;
	}

	public static void main(String[] args) {
		var dbCreator = new DbCreator();
		dbCreator.create();
	}
}
