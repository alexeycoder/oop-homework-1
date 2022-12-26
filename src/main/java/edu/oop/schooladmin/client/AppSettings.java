package edu.oop.schooladmin.client;

import java.nio.charset.Charset;
import java.util.Locale;

import org.sqlite.JDBC;

public class AppSettings {
	public static final Locale LOCALE = Locale.of("ru", "RU");
	public static final Charset CHARSET = Charset.forName("UTF-8");
	public static final String DATABASE_URL = JDBC.PREFIX + "resources/schooladmin.db";
	public static final DataSource DATA_SOURCE = DataSource.SQLITE;
}
