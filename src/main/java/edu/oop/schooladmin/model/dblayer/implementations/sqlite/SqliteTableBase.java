package edu.oop.schooladmin.model.dblayer.implementations.sqlite;

import java.sql.Connection;
import java.sql.SQLException;

import edu.oop.schooladmin.model.dblayer.interfaces.Queryable;

public abstract class SqliteTableBase<T> implements Queryable<T> {

	protected final Connection connection;

	protected SqliteTableBase(Connection connection) throws SQLException {
		this.connection = connection;
	}

	// private boolean isClosed = false;

	// @Override
	// public void close() throws Exception {
	// if (isClosed) {
	// return;
	// }
	// isClosed = true;
	// try {
	// this.statement.close();
	// } catch (Exception ex) {
	// isClosed = false;
	// throw ex;
	// }
	// }

}
