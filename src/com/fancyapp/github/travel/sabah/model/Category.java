package com.fancyapp.github.travel.sabah.model;

import java.util.ArrayList;
import java.util.List;

public class Category {
	private int id;
	private String title;
	private String desc;
	private String icon;
	private int status;
	private int sort_order;
	private List<Destination> destinations;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getSort_order() {
		return sort_order;
	}
	public void setSort_order(int sort_order) {
		this.sort_order = sort_order;
	}
	public List<Destination> getDestinations() {
		return destinations;
	}
	public void setDestinations(List<Destination> destinations) {
		this.destinations = destinations;
	}
	
	public void addDestination(Destination destination)
	{
		if (this.destinations == null)
			this.destinations = new ArrayList<Destination>();
		this.destinations.add(destination);
	}
}
