package edu.oop.schooladmin.model.interfaces;

import java.util.List;

import edu.oop.schooladmin.model.entities.Group;

public interface GroupsRepository {

	// linked repos

	//TeachersRepository teachersRepository();

	// create

	boolean addGroup(Group group);

	// read

	Group getGroupById(int groupId);

	List<Group> getAllGroups();

	List<Group> getGroupsByTeacherId(int teacherId);

	List<Group> getGroupsByClassYear(int classYear);

	List<Group> getGroupsByClassMark(char classMark);

	// Teacher getClassTeacher(int groupId);

	// update

	boolean updateGroup(Group group);

	// delete

	boolean removeGroup(int groupId);
}
