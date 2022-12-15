package edu.oop.schooladmin.model.implementations.testdb;

import java.util.ArrayList;
import java.util.List;

import edu.oop.schooladmin.model.entities.Group;
import edu.oop.schooladmin.model.interfaces.GroupsRepository;
import edu.oop.schooladmin.model.interfaces.TeachersRepository;
import edu.oop.schooladmin.testdatatables.GroupsTable;

public class TestDbGroupsRepository implements GroupsRepository {

	private final ArrayList<Group> groups;
	private int lastId;

	public TestDbGroupsRepository() {
		this.groups = GroupsTable.groups();
		lastId = RepositoryUtils.getLastPrimaryKey(groups, s -> s.getGroupId());
	}

	// @Override
	// public TeachersRepository teachersRepository() {
	// // TODO Auto-generated method stub
	// return null;
	// }

	@Override
	public Group addGroup(Group group) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Group getGroupById(int groupId) {
		var dbEntity = groups.stream().filter(d -> d.getGroupId().equals(groupId)).findFirst();
		if (dbEntity.isPresent()) {
			return new Group(dbEntity.get());
		}
		return null;
	}

	@Override
	public List<Group> getGroupsByTeacherId(int teacherId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Group> getGroupsByClassYear(int classYear) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Group> getGroupsByClassMark(char classMark) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateGroup(Group group) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Group deleteGroup(int groupId) {
		// TODO Auto-generated method stub
		return null;
	}

}
