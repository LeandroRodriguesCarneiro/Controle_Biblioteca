package Book;
//-*- coding: utf-8 -*-
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.Year;

import Genres.Genres;
import Author.Author;

import DataBaseConnector.MySQLConnector;

public class BookDAO {
	public List<Book> booksList = new ArrayList<>();

    public void insertBook(int id_publisher, String isbn, String title, Year year_publication, int quantity, List<Genres> genresList, List<Author> authorList) throws Exception {
        MySQLConnector sql = new MySQLConnector();
        try {
        	int idBook = 0;
            idBook = sql.executeProcedure("SP_InsertBook", id_publisher, isbn, title, year_publication.getValue(), quantity);
            
            for (Author author : authorList) {
            	sql.executeProcedure("SP_InsertAuthorBook",idBook,author.getId());
            }
            for (Genres genres: genresList) {
            	sql.executeProcedure("SP_InsertGenreBook",idBook,genres.getId());
            }
        }catch(Exception e){
        	if(e.getMessage().equals("Entrada duplicada")) {
        		throw new Exception("Esse ISBN ja está cadastrado em um livro");
        	}
        }
        
    }
    
    public void UpdateBook(int id, int id_publisher, String isbn, String title, Year year_publication, int quantity, List<Genres> genresList, List<Author> authorList) throws Exception {
        MySQLConnector sql = new MySQLConnector();
        int idBook = 0;
        try {
        	idBook = sql.executeProcedure("SP_UpdateBook", id, id_publisher, isbn, title, year_publication.getValue(), quantity);
            
            if(!authorList.isEmpty()) {
            	sql.executeProcedure("SP_DeleteAuthorBook", id);
            	for (Author author : authorList) {
                	sql.executeProcedure("SP_InsertAuthorBook",idBook,author.getId());
                }
            }
            
            if(!genresList.isEmpty()) {
            	sql.executeProcedure("SP_DeleteGenreBook", id);
            	for (Genres genres: genresList) {
                	sql.executeProcedure("SP_InsertGenreBook",idBook,genres.getId());
                }
            }
        }catch(Exception e){
        	if(e.getMessage().equals("Entrada duplicada")) {
        		throw new Exception("Esse ISBN ja está cadastrado em um livro");
        	}
        }
        
    }
    
    public void deleteBook(int id) throws Exception {
    	try {
    		MySQLConnector sql = new MySQLConnector();
       	 	sql.executeProcedure("SP_DeleteBook", id);
    	}catch(Exception e){
        	if(e.getMessage().equals("Entrada duplicada")) {
        		throw new Exception("Esse ISBN ja está cadastrado em um livro");
        	}
        }
    	
    }

    public List<Book> selectAllBooks() {
        MySQLConnector sql = new MySQLConnector();
        ResultSet resultSet = sql.selectSQL("SELECT * FROM vw_books");
        booksList.clear(); 

        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                	booksList.add(new Book(resultSet.getInt("id"),resultSet.getString("title"),resultSet.getString("isbn"),resultSet.getInt("year"),
                			resultSet.getInt("quantity"),resultSet.getString("publisher"),resultSet.getString("author"),resultSet.getString("genre")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return booksList;
    }

    public List<Book> selectBooksByISBN(String ISBN) {
        MySQLConnector sql = new MySQLConnector();
        String query = "SELECT * FROM vw_books WHERE isbn = '"+ISBN+"'";
        ResultSet resultSet = sql.selectSQL(query);
        booksList.clear();
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                	booksList.add(new Book(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("isbn"),
                            resultSet.getInt("year"), resultSet.getInt("quantity"), resultSet.getString("publisher"),
                            resultSet.getString("author"), resultSet.getString("genre")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return booksList;
    }
    
    public List<Book> selectBooksTimesBorrowed() {
        MySQLConnector sql = new MySQLConnector();
        String query = "SELECT \r\n"
        		+ "	vbk.*,\r\n"
        		+ "    COUNT(bbk.id_book) AS times_borrowed\r\n"
        		+ "FROM vw_books as vbk\r\n"
        		+ "LEFT JOIN borrowed_books as bbk ON vbk.id = bbk.id_book\r\n"
        		+ "GROUP BY vbk.id\r\n"
        		+ "ORDER BY times_borrowed DESC";
        ResultSet resultSet = sql.selectSQL(query);
        booksList.clear();
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                	Book book = new Book(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("isbn"),
                            resultSet.getInt("year"), resultSet.getInt("quantity"), resultSet.getString("publisher"),
                            resultSet.getString("author"), resultSet.getString("genre"));
                	book.setTimesBorrowed(resultSet.getInt("times_borrowed"));
                	booksList.add(book);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return booksList;
    }
    
    public List<Book> selectBooksOutStockBooks() {
        MySQLConnector sql = new MySQLConnector();
        String query = "SELECT * FROM vw_books WHERE quantity<=0";
        ResultSet resultSet = sql.selectSQL(query);
        booksList.clear();
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                	Book book = new Book(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("isbn"),
                            resultSet.getInt("year"), resultSet.getInt("quantity"), resultSet.getString("publisher"),
                            resultSet.getString("author"), resultSet.getString("genre"));
                	booksList.add(book);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return booksList;
    }
    
    public List<Book> selectBooksPublishedPeriodTime(LocalDate dateInit, LocalDate dateEnd) {
        MySQLConnector sql = new MySQLConnector();
        String query = "SELECT * FROM vw_books WHERE year >= '"+dateInit+"' AND year <= '"+dateEnd+"'";
        ResultSet resultSet = sql.selectSQL(query);
        booksList.clear();
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                	Book book = new Book(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("isbn"),
                            resultSet.getInt("year"), resultSet.getInt("quantity"), resultSet.getString("publisher"),
                            resultSet.getString("author"), resultSet.getString("genre"));
                	booksList.add(book);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return booksList;
    }
    
    public List<Book> selectBooksSpecificAuthor (String authorName) {
        MySQLConnector sql = new MySQLConnector();
        String query = "SELECT * FROM vw_books WHERE author LIKE '%"+authorName+"%'";
        ResultSet resultSet = sql.selectSQL(query);
        booksList.clear();
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                	Book book = new Book(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("isbn"),
                            resultSet.getInt("year"), resultSet.getInt("quantity"), resultSet.getString("publisher"),
                            resultSet.getString("author"), resultSet.getString("genre"));
                	booksList.add(book);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return booksList;
    }
}
