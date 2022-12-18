package edu.oop.schooladmin.model.implementations.sqlite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.oop.schooladmin.model.entities.Group;
import edu.oop.schooladmin.model.interfaces.GroupsRepository;

public class SqlGroupsRepository implements GroupsRepository {

    @Override
    public Group addGroup(Group group) {
        RepositoryUtils utils = new RepositoryUtils();
        String sql = "INSERT INTO Groups( 'classYear', 'classMark', 'teacherId') "+
        "VALUES( ?, ?, ?);";
        List<Object> parametersList = new ArrayList<>();
        parametersList.add(group.getClassYear());
        parametersList.add(group.getClassMark());
        parametersList.add(group.getTeacherId());

        utils.setTable(sql, parametersList);
        return group;
    }


    @Override
    public Group getGroupById(int groupId) {
        RepositoryUtils utils = new RepositoryUtils();
        String sql = "SELECT groupId, classYear, classMark, teacherId FROM Groups WHERE groupId=? ";
        ArrayList<Object> parametersList = new ArrayList<>();
        parametersList.add(groupId);
        try {
            ResultSet resultSet = utils.getFromTable(sql, parametersList);
            if (resultSet.next()){
                Group group = new Group((Integer)(resultSet.getInt("groupId")),
                                                resultSet.getInt("classYear"),
                                                resultSet.getString("classMark").toCharArray()[0],
                                                (Integer)(resultSet.getInt("teacherId")));
                resultSet.close();
                return group;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Group> getAllGroups() {
        String sql = "SELECT groupId, classYear, classMark, teacherId FROM Groups";
        return getGroupsDataList(sql);
    }

    @Override
    public List<Group> getGroupsByTeacherId(int teacherId) {
        String sql = "SELECT groupId, classYear, classMark, teacherId FROM Groups WHERE teacherId=?";
        List<Object> parametersList = new ArrayList<>();
        parametersList.add(teacherId);
        return getGroupsDataList(sql, parametersList);
    }

    @Override
    public List<Group> getGroupsByClassYear(int classYear) {
        String sql = "SELECT groupId, classYear, classMark, teacherId FROM Groups WHERE classYear=?";
        List<Object> parametersList = new ArrayList<>();
        parametersList.add(classYear);
        return getGroupsDataList(sql, parametersList);
    }

    @Override
    public List<Group> getGroupsByClassMark(char classMark) {
        String sql = "SELECT groupId, classYear, classMark, teacherId FROM Groups WHERE classMark=?";
        List<Object> parametersList = new ArrayList<>();
        parametersList.add(classMark);
        return getGroupsDataList(sql, parametersList);
    }

    @Override
    public boolean updateGroup(Group group) {
        RepositoryUtils utils = new RepositoryUtils();
        String sql = "UPDATE Groups SET classYear=?, classMark=?, teacherId=? WHERE groupId=?";
        if(getGroupById(group.getGroupId()) != null){
            List<Object> parametersList = new ArrayList<>();
            parametersList.add(group.getClassYear());
            parametersList.add(group.getClassMark());
            parametersList.add(group.getTeacherId());
            parametersList.add(group.getGroupId());
            utils.setTable(sql, parametersList);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeGroup(int groupId) {
        RepositoryUtils utils = new RepositoryUtils();
        SqlStudentsRepository repository = new SqlStudentsRepository();
        String sql = "DELETE FROM Groups WHERE groupId=?";
        if(getGroupById(groupId) != null &&
            repository.getStudentsByGroupId(groupId).isEmpty()){
            List<Object> parametersList = new ArrayList<>();
            parametersList.add(groupId);
            utils.setTable(sql, parametersList);
            return true;
        }
        return false;
    }


    private List<Group> getGroupsDataList(String sql, List<Object> parametersList){
        RepositoryUtils utils = new RepositoryUtils();
        try {
            ResultSet resultSet = utils.getFromTable(sql, parametersList);
            List<Group> groups = new ArrayList<>();
            while (resultSet.next()) {
                groups.add(new Group((Integer)(resultSet.getInt("groupId")),
                                                resultSet.getInt("classYear"),
                                                resultSet.getString("classMark").toCharArray()[0],
                                                (Integer)(resultSet.getInt("teacherId")))) ;
            }
            resultSet.close();
            return groups;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Group> getGroupsDataList(String sql){
        RepositoryUtils utils = new RepositoryUtils();
        try {
            ResultSet resultSet = utils.getFromTable(sql);
            List<Group> groups = new ArrayList<>();
            while(resultSet.next()){
                groups.add(new Group((Integer)(resultSet.getInt("groupId")),
                                                resultSet.getInt("classYear"),
                                                resultSet.getString("classMark").toCharArray()[0],
                                                (Integer)(resultSet.getInt("teacherId"))));
            }
            resultSet.close();
            return groups;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
