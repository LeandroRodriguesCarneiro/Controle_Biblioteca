package Loan;

import Book.BooksBorrowed;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Loan {
	private int id;
	private int idStudent;
	private LocalDate dateInit;
	private LocalDate dateEnd;
	private String status = new String();
	private List<BooksBorrowed> listBooks = new ArrayList<>(); 
	
	public Loan(int id, int idStudent, LocalDate dateInit, LocalDate dateEnd, String status, List<BooksBorrowed> listBooks) {
		listBooks.clear();
		this.id = id;
		this.idStudent = idStudent;
		this.dateInit = dateInit;
		this.dateEnd = dateEnd;
		this.status = status;
		this.listBooks = listBooks;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdStudent() {
		return idStudent;
	}

	public void setIdStudent(int idStudent) {
		this.idStudent = idStudent;
	}

	public LocalDate getDateInit() {
		return dateInit;
	}

	public void setDateInit(LocalDate dateInit) {
		this.dateInit = dateInit;
	}

	public LocalDate getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(LocalDate dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<BooksBorrowed> getListBooks() {
		return listBooks;
	}
	
	public void setListBooks(List<BooksBorrowed> listBooks) {
		this.listBooks = listBooks;
	}
	
	public void addListBooks(BooksBorrowed book) {
		listBooks.add(book);
	}
	
	public void removeBook(String isbn) {
		for (BooksBorrowed book: listBooks) {
			if(book.getIsbn().equals(isbn)) {
				listBooks.remove(book);
			}
		}
	}
}
