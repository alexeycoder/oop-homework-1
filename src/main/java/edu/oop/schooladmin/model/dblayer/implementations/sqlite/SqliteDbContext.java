package edu.oop.schooladmin.model.dblayer.implementations.sqlite;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.sqlite.JDBC;
import org.sqlite.SQLiteConfig;

import edu.oop.schooladmin.model.dblayer.interfaces.DbLayerContext;
import edu.oop.schooladmin.model.dblayer.interfaces.Queryable;
import edu.oop.schooladmin.model.entities.Discipline;
import edu.oop.schooladmin.model.entities.Group;
import edu.oop.schooladmin.model.entities.Rating;
import edu.oop.schooladmin.model.entities.Student;
import edu.oop.schooladmin.model.entities.Teacher;
import edu.oop.schooladmin.model.entities.TeacherAppointment;

public class SqliteDbContext implements DbLayerContext {

	static {
		try {
			DriverManager.registerDriver(new JDBC());
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
	}

	private final Connection connection;

	private final SqliteTableBase<Discipline> disciplines;
	private final SqliteTableBase<Teacher> teachers;
	private final SqliteTableBase<Group> groups;
	private final SqliteTableBase<Student> students;
	private final SqliteTableBase<Rating> ratings;
	private final SqliteTableBase<TeacherAppointment> teacherAppointments;

	public SqliteDbContext(String databaseUrl) throws SQLException {
		if (!JDBC.isValidURL(databaseUrl)) {
			throw new InvalidParameterException("databaseUrl");
		}

		SQLiteConfig config = new SQLiteConfig();
		config.enforceForeignKeys(true);
		this.connection = DriverManager.getConnection(databaseUrl, config.toProperties());
		this.disciplines = new DisciplinesTable(this.connection);
		this.teachers = new TeachersTable(this.connection);
		this.groups = new GroupsTable(this.connection);
		this.students = new StudentsTable(this.connection);
		this.ratings = new RatingsTable(this.connection);
		this.teacherAppointments = new TeacherAppointmentsTable(this.connection);
	}

	private boolean isClosed = false;

	@Override
	public void close() throws Exception {
		if (isClosed) {
			return;
		}
		isClosed = true;
		try {
			if (!connection.isClosed()) {
				connection.close();
			}
		} catch (Exception ex) {
			isClosed = false;
			throw ex;
		}
	}

	@Override
	public Queryable<Discipline> disciplines() {
		return disciplines;
	}

	@Override
	public Queryable<Teacher> teachers() {
		return teachers;
	}

	@Override
	public Queryable<Group> groups() {
		return groups;
	}

	@Override
	public Queryable<Student> students() {
		return students;
	}

	@Override
	public Queryable<Rating> ratings() {
		return ratings;
	}

	@Override
	public Queryable<TeacherAppointment> teacherAppointments() {
		return teacherAppointments;
	}

	public static void main(String[] args) {
		try {

			// SqliteDbContext dbContext = new SqliteDbContext(JDBC.PREFIX +
			// "resources/schooladmin.db");

			// var disciplinesTable = dbContext.disciplines();
			// disciplinesTable.add(new Discipline(null, "Некий предмет"));

			// dbContext.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
