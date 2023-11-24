package Book;
//-*- coding: utf-8 -*-
public class BooksBorrowed extends Book{
	private String status = new String();
	public BooksBorrowed(int id, String title, String isbn, int yearPublication, int quantity, String publisher, String authors, String genres, String status) {
		super(id, title, isbn, yearPublication, quantity, publisher, authors, genres);
		this.setStatus(status);
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
