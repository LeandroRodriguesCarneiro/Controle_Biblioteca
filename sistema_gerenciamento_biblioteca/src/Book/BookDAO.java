package Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.Year;

import Genres.Genres;
import Author.Author;

import DataBaseConnector.MySQLConnector;

public class BookDAO {
	public List<Book> booksList = new ArrayList<>();

    public void insertBook(int id_publisher, String isbn, String title, Year year_publication, int quantity, List<Genres> genresList, List<Author> authorList) {
        MySQLConnector sql = new MySQLConnector();
        int idBook = 0;
        idBook = sql.executeProcedure("SP_InsertBook", id_publisher, isbn, title, year_publication.getValue(), quantity);
        
        for (Author author : authorList) {
        	sql.executeProcedure("SP_InsertAuthorBook",idBook,author.getId());
        }
        for (Genres genres: genresList) {
        	sql.executeProcedure("SP_InsertGenreBook",idBook,genres.getId());
        }
    }
    
    public void UpdateBook(int id, int id_publisher, String isbn, String title, Year year_publication, int quantity, List<Genres> genresList, List<Author> authorList) {
        MySQLConnector sql = new MySQLConnector();
        int idBook = 0;
        idBook = sql.executeProcedure("SP_UpdateBook", id, id_publisher, isbn, title, year_publication, quantity);
        
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
    }
    
    public void deleteBook(int id) {
    	MySQLConnector sql = new MySQLConnector();
    	 sql.executeProcedure("SP_DeleteBook", id);
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

}
