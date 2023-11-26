package Publisher;
//-*- coding: utf-8 -*-
public class Publisher {
	private String name;
	private int id;
	private int qtd_titles;

	public Publisher(int id,String name) {
    	this.id = id;
    	this.name = name;
    }
	
    public int getQtd_titles() {
		return qtd_titles;
	}

	public void setQtd_titles(int qtd_titles) {
		this.qtd_titles = qtd_titles;
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
