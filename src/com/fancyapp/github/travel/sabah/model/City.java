package com.fancyapp.github.travel.sabah.model;

public class City {
	private int id;
	private String title;
	private String status;
	private State state;
	
	public City(){}
	
	public City(int id, String title, State state){
		this.id = id;
		this.title = title;
		this.state = state;
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
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
}
