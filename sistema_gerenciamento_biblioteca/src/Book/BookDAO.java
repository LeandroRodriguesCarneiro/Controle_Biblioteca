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
        	throw new Exception("Esse ISBN ja está cadastrado em um livro");
        }
        
    }
    
    public void UpdateBook(int id, int id_publisher, String isbn, String title, Year year_publication, int quantity, 
    		List<Genres> genresList, List<Author> authorList, boolean active) throws Exception {
        MySQLConnector sql = new MySQLConnector();
        int idBook = 0;
        try {
        	idBook = sql.executeProcedure("SP_UpdateBook", id, id_publisher, isbn, title, year_publication.getValue(), quantity, active);
            
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
        	throw new Exception("Esse ISBN ja está cadastrado em um livro");
        }
        
    }
    
    public void deleteBook(int id) throws Exception {
    	try {
    		MySQLConnector sql = new MySQLConnector();
       	 	sql.executeProcedure("SP_DeleteBook", id);
    	}catch(Exception e){
        	throw new Exception("Este livro não pode ser excluido pois possui empréstimos registrados");
        	
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
                			resultSet.getInt("quantity"),resultSet.getString("publisher"),resultSet.getString("author"),resultSet.getString("genre"),
                			resultSet.getBoolean("active")));
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
    
    public List<Book> selectBooksByfilter(String title, long ISBN, String publisher, Integer yearPublication, String genre, String author, boolean active) {
        MySQLConnector sql = new MySQLConnector();
        String query = "SELECT * FROM vw_books WHERE 1=1";
        
        if(title!=null && !title.isEmpty()) {
        	query+=" AND title LIKE '%"+title+"%'";
        }
        if(ISBN>0) {
        	query+=" AND isbn = '"+ISBN+"'";
        }
        if(publisher!=null && !publisher.isEmpty()) {
        	query+=" AND publisher LIKE '%"+publisher+"%'";
        }
        if(yearPublication>0 && yearPublication!=null) {
        	query+=" AND year = "+yearPublication;
        }
        if(genre!=null && !genre.isEmpty()) {
        	query+=" AND genre LIKE '%"+genre+"%'";
        }
        if(author!=null && !author.isEmpty()) {
        	query+=" AND author LIKE '%"+author+"%'";
        }
        
        if(active) {
        	query+= " AND active = 1";
        }
        
        ResultSet resultSet = sql.selectSQL(query);
        booksList.clear(); 

        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                	booksList.add(new Book(resultSet.getInt("id"),resultSet.getString("title"),resultSet.getString("isbn"),resultSet.getInt("year"),
                			resultSet.getInt("quantity"),resultSet.getString("publisher"),resultSet.getString("author"),resultSet.getString("genre"),
                			resultSet.getBoolean("active")));
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
    
    public List<Book> selectBooksByISBN(String ISBN, boolean active) {
        MySQLConnector sql = new MySQLConnector();
        String query = "SELECT * FROM vw_books WHERE isbn = '"+ISBN+"'";
        if(active) {
        	query+= " AND active = 1";
        }
        ResultSet resultSet = sql.selectSQL(query);
        booksList.clear();
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                	booksList.add(new Book(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("isbn"),
                            resultSet.getInt("year"), resultSet.getInt("quantity"), resultSet.getString("publisher"),
                            resultSet.getString("author"), resultSet.getString("genre"), resultSet.getBoolean("active")));
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
    
    public List<Book> selectBooksTimesBorrowed(String genre, Integer minTimesBorrowed, String publisher) {
        MySQLConnector sql = new MySQLConnector();
        String query = "SELECT \r\n"
        	    + "vbk.*,\r\n"
        	    + "COUNT(bbk.id_book) AS times_borrowed,\r\n" 
        	    + "vbk.active AS active " 
        	    + "FROM vw_books AS vbk\r\n" 
        	    + "LEFT JOIN borrowed_books AS bbk ON vbk.id = bbk.id_book\r\n"
        	    + "WHERE 1=1";


        		if(genre != null && !genre.isEmpty()) {
        			query += " vbk.genre LIKE '%"+genre+"%'  \r\n";
        		}
        		
        		if(publisher != null && !publisher.isEmpty()) {
        			query += " vbk.publisher LIKE '%"+publisher+"%'";
        		}
        		
        		query += " GROUP BY vbk.id\r\n";
        		
        		if(minTimesBorrowed != null && minTimesBorrowed >0) {
        			query+= " HAVING times_borrowed >= "+minTimesBorrowed+"  \r\n";
        		}
        		query+= " ORDER BY times_borrowed DESC;";
        ResultSet resultSet = sql.selectSQL(query);
        booksList.clear();
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                	Book book = new Book(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("isbn"),
                            resultSet.getInt("year"), resultSet.getInt("quantity"), resultSet.getString("publisher"),
                            resultSet.getString("author"), resultSet.getString("genre"), resultSet.getBoolean("active"));
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
    
    public List<Book> selectBooksOutStockBooks(boolean lowQuantity, String publisher, String genre) {
        MySQLConnector sql = new MySQLConnector();
        String query = "SELECT vbk.* FROM vw_books AS vbk "
                + "LEFT JOIN vw_borrowedbooks AS bbk ON vbk.id = book_id "
                + "WHERE 1 = 1 ";

	   if (publisher != null && !publisher.isEmpty()) {
	       query += " AND vbk.publisher LIKE '%" + publisher + "%'";
	   }
	
	   if (genre != null && !genre.isEmpty()) {
	       query += " AND vbk.genre LIKE '%" + genre + "%'";
	   }
	
	   query += " GROUP BY vbk.id HAVING ";
	
	   if (lowQuantity) {
	       query += "vbk.quantity <= ROUND((vbk.quantity + COUNT(bbk.id)) / 3)";
	   } else {
	       query += "vbk.quantity <= 0";
	   }
        ResultSet resultSet = sql.selectSQL(query);
        booksList.clear();
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                	Book book = new Book(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("isbn"),
                            resultSet.getInt("year"), resultSet.getInt("quantity"), resultSet.getString("publisher"),
                            resultSet.getString("author"), resultSet.getString("genre"), resultSet.getBoolean("active"));
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
    
    public List<Book> selectBooksPublishedPeriodTime(LocalDate dateInit, LocalDate dateEnd, String title, String genre, String publisher) {
        MySQLConnector sql = new MySQLConnector();
        String query = "SELECT * FROM vw_books WHERE year >= '"+dateInit+"' AND year <= '"+dateEnd+"' ";
        if(title != null && !title.isEmpty()) {
        	query+=" AND title LIKE '%"+title+"%'";
        }
        
        if(genre != null && !genre.isEmpty()) {
        	query+=" AND genre LIKE '%"+genre+"%'";
        }
        
        if(publisher != null && !publisher.isEmpty()) {
        	query+=" AND publisher LIKE'%"+publisher+"%' ";
        } 
        
        ResultSet resultSet = sql.selectSQL(query);
        booksList.clear();
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                	Book book = new Book(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("isbn"),
                            resultSet.getInt("year"), resultSet.getInt("quantity"), resultSet.getString("publisher"),
                            resultSet.getString("author"), resultSet.getString("genre"), resultSet.getBoolean("active"));
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
    
    public List<Book> selectBooksSpecificAuthor (String authorName, String title) {
        MySQLConnector sql = new MySQLConnector();
        String query = "SELECT "
        	    + "bok.id AS id,"
        	    + "bok.title AS title,"
        	    + "bok.isbn AS isbn,"
        	    + "bok.year_publication AS year,"
        	    + "bok.quantity AS quantity,"
        	    + "pub.name AS publisher,"
        	    + "GROUP_CONCAT(DISTINCT atr.name "
        	    + "    SEPARATOR ',') AS author,"
        	    + "GROUP_CONCAT(DISTINCT gen.name "
        	    + "    SEPARATOR ',') AS genre,"
        	    + "bok.active AS active " 
        	    + "FROM "
        	    + "book bok "
        	    + "LEFT JOIN publisher pub ON bok.id_publisher = pub.id "
        	    + "LEFT JOIN authors_books abk ON bok.id = abk.id_books "
        	    + "LEFT JOIN author atr ON abk.id_author = atr.id "
        	    + "LEFT JOIN genres_books gbk ON bok.id = gbk.id_books "
        	    + "LEFT JOIN genre gen ON gbk.id_genre = gen.id "
        	    + "WHERE 1=1 ";
        	if(authorName != null && !authorName.isEmpty()) {
        	    query +=" AND atr.name LIKE '%"+authorName+"%'";
        	}

        	if (title != null && !title.isEmpty()) {
        	    query += " AND bok.title LIKE '%" + title + "%' ";
        	}

        	query+=" GROUP BY bok.id ORDER BY atr.name";

        ResultSet resultSet = sql.selectSQL(query);
        booksList.clear();
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                	Book book = new Book(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("isbn"),
                            resultSet.getInt("year"), resultSet.getInt("quantity"), resultSet.getString("publisher"),
                            resultSet.getString("author"), resultSet.getString("genre"), resultSet.getBoolean("active"));
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
    
    public List<Book> selectBooksByGenre (String genre, String title) {
        MySQLConnector sql = new MySQLConnector();
        String query = "SELECT "
                + "bok.id AS id,"
                + "bok.title AS title,"
                + "bok.isbn AS isbn,"
                + "bok.year_publication AS year,"
                + "bok.quantity AS quantity,"
                + "pub.name AS publisher,"
                + "GROUP_CONCAT(DISTINCT atr.name "
                + "    SEPARATOR ',') AS author,"
                + "GROUP_CONCAT(DISTINCT gen.name "
                + "    SEPARATOR ',') AS genre,"
                + "bok.active AS active"  
                + " FROM "  
                + "book bok "
                + "LEFT JOIN publisher pub ON bok.id_publisher = pub.id "
                + "LEFT JOIN authors_books abk ON bok.id = abk.id_books "
                + "LEFT JOIN author atr ON abk.id_author = atr.id "
                + "LEFT JOIN genres_books gbk ON bok.id = gbk.id_books "
                + "LEFT JOIN genre gen ON gbk.id_genre = gen.id"
                + " WHERE 1=1 ";

        if (genre != null && !genre.isEmpty()) {
            query += " AND gen.name LIKE '%" + genre + "%' ";
        }
        
        if (title != null && !title.isEmpty()) {
            query += " AND bok.title LIKE '%" + title + "%' ";
        }

        query += " GROUP BY bok.id ORDER BY gen.name";	
        ResultSet resultSet = sql.selectSQL(query);
        booksList.clear();
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                	Book book = new Book(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("isbn"),
                            resultSet.getInt("year"), resultSet.getInt("quantity"), resultSet.getString("publisher"),
                            resultSet.getString("author"), resultSet.getString("genre"), resultSet.getBoolean("active"));
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
