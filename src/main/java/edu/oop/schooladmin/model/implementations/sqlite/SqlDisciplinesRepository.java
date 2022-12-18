package edu.oop.schooladmin.model.implementations.sqlite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.oop.schooladmin.model.entities.Discipline;
import edu.oop.schooladmin.model.interfaces.DisciplinesRepository;

public class SqlDisciplinesRepository implements DisciplinesRepository {

    @Override
    public Discipline addDiscipline(Discipline discipline) {
        RepositoryUtils utils = new RepositoryUtils();
        String sql = "INSERT INTO Disciplines( 'name') "+
        "VALUES( ?);";
        List<Object> parametersList = new ArrayList<>();
        parametersList.add(discipline.getName());
        utils.setTable(sql, parametersList);
        return discipline;
    }


    @Override
    public Discipline getDisciplineById(int disciplineId) {
        RepositoryUtils utils = new RepositoryUtils();
        String sql = "SELECT disciplineId, name FROM Disciplines WHERE disciplineId=? ";
        List<Object> parametersList = new ArrayList<>();
        parametersList.add(disciplineId);
        try {
            ResultSet resultSet = utils.getFromTable(sql, parametersList);
            if (resultSet.next()){
                Discipline discipline = new Discipline((Integer)(resultSet.getInt("disciplineId")),
                                                        resultSet.getString("name"));
                resultSet.close();
                return discipline;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Discipline getDisciplineByName(String name) {
        RepositoryUtils utils = new RepositoryUtils();
        String sql = "SELECT disciplineId, name FROM Disciplines WHERE name=? ";
        List<Object> parametersList = new ArrayList<>();
        parametersList.add(name);
        try {
            ResultSet resultSet = utils.getFromTable(sql, parametersList);
            if (resultSet.next()){
                Discipline discipline = new Discipline((Integer)(resultSet.getInt("disciplineId")),
                    resultSet.getString("name"));
                resultSet.close();
                return discipline;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Discipline> getAllDisciplines() {
        String sql = "SELECT disciplineId, name FROM Disciplines";
        return getDisciplinesDataList(sql);
    }

    @Override
    public boolean updateDiscipline(Discipline discipline) {
        RepositoryUtils utils = new RepositoryUtils();
        String sql = "UPDATE Disciplines SET name=? WHERE disciplineId=?";
        if(getDisciplineById(discipline.getDisciplineId()) != null){
            List<Object> parametersList = new ArrayList<>();
            parametersList.add(discipline.getName());
            parametersList.add(discipline.getDisciplineId());
            utils.setTable(sql, parametersList);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeDiscipline(int disciplineId) {
        RepositoryUtils utils = new RepositoryUtils();
        String sql = "DELETE FROM Disciplines WHERE disciplineId=?";
        //Discipline discipline = getDisciplineById(disciplineId);
        if(getDisciplineById(disciplineId) != null){
            List<Object> parametersList = new ArrayList<>();
            parametersList.add(disciplineId);
            utils.setTable(sql, parametersList);
            return true; //discipline;
        }
        return false; //discipline;
    }


    private List<Discipline> getDisciplinesDataList(String sql){
        RepositoryUtils utils = new RepositoryUtils();
        try {
            ResultSet resultSet = utils.getFromTable(sql);
            List<Discipline> disciplines = new ArrayList<>();
            while(resultSet.next()){
                disciplines.add(new Discipline((Integer)(resultSet.getInt("disciplineId")),
                                                        resultSet.getString("name")));
            }
            resultSet.close();
            return disciplines;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
