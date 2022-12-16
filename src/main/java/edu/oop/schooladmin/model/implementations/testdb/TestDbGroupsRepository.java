package edu.oop.schooladmin.model.implementations.testdb;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import edu.oop.schooladmin.model.entities.Group;
import edu.oop.schooladmin.model.interfaces.GroupsRepository;
import edu.oop.schooladmin.testdatatables.GroupsTable;

public class TestDbGroupsRepository implements GroupsRepository{

    private final ArrayList<Group> groups;
	private int lastId;
    

    public int getLastId() {
        return lastId;
    }

	public TestDbGroupsRepository() {
		groups = GroupsTable.groups();
		lastId = RepositoryUtils.getLastPrimaryKey(groups, g -> g.getGroupId());
	}

    @Override
    public boolean addGroup(Group group) {
        TestDbTeachersRepository teachersDb = new TestDbTeachersRepository();
        makeSureValidity(group);

        if (teachersDb.getTeacherById(group.getTeacherId()) != null){
            group.setGroupId(++lastId);

            var addedEntity = new Group(group);
            groups.add(addedEntity);
            return true;
        }
        else return false;
    }

    @Override
    public List<Group> getAllGroups() {
        List<Group> resultList = new ArrayList<>();
        for (Group group : groups) {
            resultList.add(new Group(group));
        }
        return resultList;
    }

    @Override
    public Group getGroupById(int groupId) {
        var dbEntity = groups.stream().filter(s -> s.getGroupId().equals(groupId)).findFirst();
        if (dbEntity.isPresent()) {
            return new Group(dbEntity.get());
        }
        return null;
    }

    @Override
    public List<Group> getGroupsByTeacherId(int teacherId) {
        List<Group> resultList = new ArrayList<>();
        for (Group group : groups) {
            if (group.getTeacherId() == teacherId) {
                resultList.add(new Group(group));
            }
        }
        return resultList;
    }

    @Override
    public List<Group> getGroupsByClassYear(int classYear) {
        List<Group> resultList = new ArrayList<>();
        for (Group group : groups) {
            if (group.getClassYear() == classYear) {
                resultList.add(new Group(group));
            }
        }
        return resultList;
    }

    @Override
    public List<Group> getGroupsByClassMark(char classMark) {
        List<Group> resultList = new ArrayList<>();
        for (Group group : groups) {
            if (group.getClassMark() == classMark) {
                resultList.add(new Group(group));
            }
        }
        return resultList;
    }

    @Override
    public boolean updateGroup(Group group) {
        Integer index = null;
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).getGroupId().equals(group.getGroupId())) {
                index = i;
                break;
            }
        }
        if (index != null){
            groups.set(index.intValue(), new Group(group));
            return true;
        }
        else return false;
    }

    @Override
    public boolean removeGroup(int groupId) {
        TestDbStudentsRepository studentsDb = new TestDbStudentsRepository();
        Group dbEntityToRemove = null;
        Integer index = null;
        for (int i = 0; i < groups.size(); i++) {
            var dbEntity = groups.get(i);
            if (dbEntity.getGroupId().equals(groupId)) {
                dbEntityToRemove = dbEntity;
                index = i;
                break;
            }
        }
        if (dbEntityToRemove != null &&
            studentsDb.getStudentsByGroupId(groupId).isEmpty())
        {
            groups.remove(index.intValue());
            return true;
        } else
            return false;
    }
    
    private void makeSureValidity(Group group) {
        if (group == null) {
            throw new InvalidParameterException("group");
        }
    }

    /**
     * Копирует значения свойств экземпляра источника в экземпляр назначения
     * и возвращает экземпляр назначения.
     */
    private static Group copyProperties(Group instanceFrom, Group instanceTo) {
        assert instanceFrom != null && instanceTo != null;
        instanceTo.setGroupId(instanceFrom.getGroupId());
        instanceTo.setClassMark(instanceFrom.getClassMark());
        instanceTo.setClassYear(instanceFrom.getClassYear());
        instanceTo.setTeacherId(instanceFrom.getTeacherId());
        return instanceTo;
    }

}
