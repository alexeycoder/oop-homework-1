package edu.oop.schooladmin.model.implementations.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.sqlite.JDBC;

    public class ConnectionDb {
 
        // Константа, в которой хранится адрес подключения
        private static final String CON_STR = "jdbc:sqlite:src/main/java/edu/oop/schooladmin/model/implementations/sqlite/school.db";
     
        // Объект, в котором будет храниться соединение с БД
        public Connection connection;
     
        public ConnectionDb() throws SQLException {
            // Регистрируем драйвер, с которым будем работать
            // в нашем случае Sqlite
            DriverManager.registerDriver(new JDBC());
            // Выполняем подключение к базе данных
            this.connection = DriverManager.getConnection(CON_STR);
        }
    }