package Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.Year;

import Genres.Genres;
import Genres.GenresDAO;
import Author.Author;
import Author.AuthorDAO;

import DataBaseConnector.MySQLConnector;

public class BookDAO {
	public List<Book> booksList = new ArrayList<>();

    public void insertBook(int id_publisher, String isbn, String title, Year year_publication, int quantity, List<Genres> genresList, List<Author> authorList) {
        MySQLConnector sql = new MySQLConnector();
        int idBook = sql.insertSQL("INSERT INTO book (id_publisher,isbn,title,year_publication,quantity) VALUES ("+ id_publisher+","+isbn+",'"+title+"',"+year_publication+","+quantity+")");
        
        for (Author author : authorList) {
        	AuthorDAO authorDAO = new AuthorDAO();
        	authorDAO.insertAthorBook(author, idBook);
        }
        for (Genres genres: genresList) {
        	GenresDAO genresDAO = new GenresDAO();
        	genresDAO.insertGenresBooks(idBook, genres);
        }
    }
    
    public void deleteBook(int id) {
    	MySQLConnector sql = new MySQLConnector();
    	 sql.executeSQL("DELETE FROM book WHERE id = "+ id);
    }

    public List<Book> selectAllBooks() {
        MySQLConnector sql = new MySQLConnector();
        ResultSet resultSet = sql.selectSQL("SELECT \r\n"
        		+ "    bok.id AS id,\r\n"
        		+ "    bok.title AS title,\r\n"
        		+ "    bok.isbn AS isbn,\r\n"
        		+ "    bok.year_publication AS year,\r\n"
        		+ "    bok.quantity AS quantity,\r\n"
        		+ "    pub.name AS publisher,\r\n"
        		+ "    GROUP_CONCAT(DISTINCT atr.name) AS author,\r\n"
        		+ "    GROUP_CONCAT(DISTINCT gen.name) AS genre\r\n"
        		+ "FROM book AS bok\r\n"
        		+ "JOIN publisher AS pub ON bok.id_publisher = pub.id\r\n"
        		+ "LEFT JOIN authors_books AS abk ON bok.id = abk.id_books\r\n"
        		+ "LEFT JOIN author AS atr ON abk.id_author = atr.id\r\n"
        		+ "LEFT JOIN genres_books AS gbk ON bok.id = gbk.id_books\r\n"
        		+ "LEFT JOIN genre AS gen ON gbk.id_genre = gen.id\r\n"
        		+ "GROUP BY bok.id");
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

    public List<Book> selectBooksByFilter(Integer id, Integer yearInit, Integer yearEnd, List<String> genreList, String title, String author) {
        MySQLConnector sql = new MySQLConnector();
        String query = "SELECT \r\n"
            + "    bok.id AS id,\r\n"
            + "    bok.title AS title,\r\n"
            + "    bok.isbn AS isbn,\r\n"
            + "    bok.year_publication AS year,\r\n"
            + "    bok.quantity AS quantity,\r\n"
            + "    pub.name AS publisher,\r\n"
            + "    GROUP_CONCAT(DISTINCT atr.name) AS author,\r\n"
            + "    GROUP_CONCAT(DISTINCT gen.name) AS genre\r\n"
            + "FROM book AS bok\r\n"
            + "JOIN publisher AS pub ON bok.id_publisher = pub.id\r\n"
            + "LEFT JOIN authors_books AS abk ON bok.id = abk.id_books\r\n"
            + "LEFT JOIN author AS atr ON abk.id_author = atr.id\r\n"
            + "LEFT JOIN genres_books AS gbk ON bok.id = gbk.id_books\r\n"
            + "LEFT JOIN genre AS gen ON gbk.id_genre = gen.id\r\n"
            + "WHERE bok.title LIKE '%" + title + "%'";

        if (id != null) {
            query += " AND bok.id = " + id + "\r\n";
        }
        if (yearInit != null && yearEnd != null) {
            query += " AND bok.year_publication >= " + yearInit + " AND bok.year_publication <=" + yearEnd;
        }
        if (!genreList.isEmpty()) {
        	String genres = new String();
        	for (int i = 0; i < genreList.size(); i++) {
        		genres += "'"+genreList.get(i)+"'";
        		if (i < genreList.size() - 1) {
        			genres += ",";
        		}
        	}
            query += " AND gen.name IN (" + genres + ")";
        }
        if (!author.isEmpty()) {
            query += " AND atr.name LIKE '%" + author + "%'";
        }
        query += " GROUP BY bok.id";
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
    
    public List<Book> selectBooksByISBN(String ISBN) {
        MySQLConnector sql = new MySQLConnector();
        String query = "SELECT \r\n"
            + "    bok.id AS id,\r\n"
            + "    bok.title AS title,\r\n"
            + "    bok.isbn AS isbn,\r\n"
            + "    bok.year_publication AS year,\r\n"
            + "    bok.quantity AS quantity,\r\n"
            + "    pub.name AS publisher,\r\n"
            + "    GROUP_CONCAT(DISTINCT atr.name) AS author,\r\n"
            + "    GROUP_CONCAT(DISTINCT gen.name) AS genre\r\n"
            + "FROM book AS bok\r\n"
            + "JOIN publisher AS pub ON bok.id_publisher = pub.id\r\n"
            + "LEFT JOIN authors_books AS abk ON bok.id = abk.id_books\r\n"
            + "LEFT JOIN author AS atr ON abk.id_author = atr.id\r\n"
            + "LEFT JOIN genres_books AS gbk ON bok.id = gbk.id_books\r\n"
            + "LEFT JOIN genre AS gen ON gbk.id_genre = gen.id\r\n"
            + "WHERE bok.isbn = '"+ISBN+"'";
        query += " GROUP BY bok.id";
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
