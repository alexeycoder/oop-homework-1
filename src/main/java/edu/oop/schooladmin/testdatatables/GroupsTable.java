package edu.oop.schooladmin.testdatatables;

import java.util.ArrayList;
import java.util.Arrays;

import edu.oop.schooladmin.model.entities.Group;

public class GroupsTable {

	public static ArrayList<Group> groups() {
		return groups;
	}

	private static final ArrayList<Group> groups = new ArrayList<>(Arrays.asList(
			new Group(91, 9, 'А', 2),
			new Group(92, 9, 'Б', 5),
			new Group(93, 9, 'В', 8),
			new Group(101, 11, 'А', 4),
			new Group(102, 11, 'Б', 5)));

	public static void main(String[] args) {

	}
}
