package edu.oop.schooladmin.model.interfaces;

import java.util.List;

import edu.oop.schooladmin.model.entities.Group;

public interface GroupsRepository {

	// linked repos

	// TeachersRepository teachersRepository();

	// create

	Group addGroup(Group group);

	// read

	Group getGroupById(int groupId);

	List<Group> getGroupsByTeacherId(int teacherId);

	List<Group> getGroupsByClassYear(int classYear);

	List<Group> getGroupsByClassMark(char classMark);

	// Teacher getClassTeacher(int groupId);

	// update

	boolean updateGroup(Group group);

	// delete

	Group deleteGroup(int groupId);
}
