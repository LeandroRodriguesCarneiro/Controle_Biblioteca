package Genres;
//-*- coding: utf-8 -*-
public class Genres {
	private String name;
	private int id;
	private boolean active;
    
    public Genres(int id,String name, boolean active) {
    	this.id = id;
    	this.name = name;
    	this.active = active;
    }

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
