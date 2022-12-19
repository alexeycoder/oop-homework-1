package edu.oop.schooladmin.model.implementations.sqlite;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class RepositoryUtils {

    ConnectionDb connection;

    public RepositoryUtils(){
        try {
            connection = new ConnectionDb();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    public void setTable(String sql, List<Object> parameters){
        try (PreparedStatement statement = this.connection.connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i+1, parameters.get(i));
            }
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }   

    public void setTable(String sql){
        try (Statement statement = this.connection.connection.createStatement()) {
            statement.executeQuery(sql);
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getFromTable(String sql, List<Object> parameters){
        ResultSet resultSet;
        try{
            PreparedStatement statement = this.connection.connection.prepareStatement(sql);
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i+1, parameters.get(i));
            }
            resultSet = statement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
            return resultSet = null;
        }
    }   

    public ResultSet getFromTable(String sql){
        ResultSet resultSet;
        try{
            PreparedStatement statement = this.connection.connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }   

}
