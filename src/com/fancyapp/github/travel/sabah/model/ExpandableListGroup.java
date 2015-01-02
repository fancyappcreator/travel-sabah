package com.fancyapp.github.travel.sabah.model;

import java.util.ArrayList;
import java.util.List;

public class ExpandableListGroup {
	private String groupName;
	private int iconResourceId;
	private final List<String> children = new ArrayList<String>();

	public ExpandableListGroup(String groupName, int iconResourceId) {
		this.groupName = groupName;
		this.iconResourceId = iconResourceId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getIconResourceId() {
		return iconResourceId;
	}

	public void setIconResourceId(int iconResourceId) {
		this.iconResourceId = iconResourceId;
	}

	public List<String> getChildren() {
		return children;
	}

	public void addChild(String child) {
		children.add(child);
	}
}