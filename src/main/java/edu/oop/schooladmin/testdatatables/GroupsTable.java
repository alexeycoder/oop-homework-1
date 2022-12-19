package edu.oop.schooladmin.testdatatables;

import java.util.Arrays;
import java.util.List;

import edu.oop.schooladmin.model.entities.Group;

public class GroupsTable extends QueryableBase<Group> {

	protected GroupsTable() {
		super(testData);
	}

	private static GroupsTable instance;

	public static final GroupsTable instance() {
		if (instance == null) {
			instance = new GroupsTable();
		}
		return instance;
	}

	@Override
	protected Integer getEntryId(Group entry) {
		return entry.getGroupId();
	}

	@Override
	protected void setEntryId(Group entry, int id) {
		entry.setGroupId(id);
	}

	@Override
	protected Group getCopyOf(Group entry) {
		return new Group(entry);
	}

	private static final List<Group> testData = Arrays.asList(
			new Group(91, 9, 'А', 2),
			new Group(92, 9, 'Б', 5),
			new Group(93, 9, 'В', 8),
			new Group(101, 11, 'А', 4),
			new Group(102, 11, 'Б', 5));
}
