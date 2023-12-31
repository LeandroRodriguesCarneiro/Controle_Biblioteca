package Student;
//-*- coding: utf-8 -*-
public class Student {
	private int id;
	private int borrowedBooks;
	private String name = new String();
	private long numberRegistration;
	private float debits;
	private boolean active;
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Student(int id, int borrowedBooks, String name, long numberRegistration, float debits, boolean active) {
		this.id = id;
		this.borrowedBooks = borrowedBooks;
		this.name = name;
		this.numberRegistration = numberRegistration;
		this.debits = debits;
		this.active = active;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBorrowedBooks() {
		return borrowedBooks;
	}

	public void setBorrowedBooks(int borrowedBooks) {
		this.borrowedBooks = borrowedBooks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getNumberRegistration() {
		return numberRegistration;
	}

	public void setNumberRegistration(long numberRegistration) {
		this.numberRegistration = numberRegistration;
	}

	public float getDebits() {
		return debits;
	}

	public void setDebits(float debits) {
		this.debits = debits;
	}
	
}
