package Book;
//-*- coding: utf-8 -*-
import java.util.ArrayList;
import java.util.List;
import java.time.Year;

public class Book {
	private int id;
	private String publisher = new String();
	private String isbn = new String();
	private String title = new String();
	private List<String> author = new ArrayList<>();
	private List<String> genre = new ArrayList<>();
	private Year yearPublication;
	private int quantity;
	private int timesBorrowed;
	private boolean active;
	
	public Book(int id, String title, String isbn, int yearPublication, int quantity, String publisher, String authors, String genres, boolean active) {
		this.id = id;
		this.title = title;
		this.isbn = isbn;
		this.yearPublication = Year.of(yearPublication);
		this.quantity = quantity;
		this.publisher = publisher;
		this.active = active;
		String[] Temp;
		if (genres != null) {
			 Temp = genres.split(",");
			for (String genreTemp: Temp) {
				this.genre.add(genreTemp);
			}
		}
		
		if(authors != null) {
			Temp = authors.split(",");
			for (String authorTemp: Temp) {
				this.author.add(authorTemp);
			}
		}
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getAuthor() {
		return author;
	}

	public void setAuthor(List<String> author) {
		this.author = author;
	}

	public List<String> getGenre() {
		return genre;
	}

	public void setGenre(List<String> genre) {
		this.genre = genre;
	}

	public Year getYearPublication() {
		return yearPublication;
	}

	public void setYearPublication(int yearPublication) {
		this.yearPublication = Year.of(yearPublication);
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getTimesBorrowed() {
		return timesBorrowed;
	}

	public void setTimesBorrowed(int timesBorrowed) {
		this.timesBorrowed = timesBorrowed;
	}

	public void setYearPublication(Year yearPublication) {
		this.yearPublication = yearPublication;
	}
}
