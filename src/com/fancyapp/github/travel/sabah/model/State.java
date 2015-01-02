package com.fancyapp.github.travel.sabah.model;

public class State {
	private int id;
	private String title;
	private String status;
	
	public State(){}
	
	public State(String title){
		this.title = title;
	}
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
