package Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DataBaseConnector.MySQLConnector;

public class AuthorDAO {
    public List<Author> authorList = new ArrayList<>();

    public void insertAuthor(String name) {
        MySQLConnector sql = new MySQLConnector();
        sql.executeSQL("INSERT INTO author (name) VALUES ('" + name + "')");
    }
    
    public void insertAthorBook(Author author, int idBook) {
    	 MySQLConnector sql = new MySQLConnector();
    	 sql.executeSQL("INSERT INTO authors_books (id_books,id_author) VALUES ("+idBook+","+author.getId()+")");
    }
    
    public void deleteAuthor(int id) {
    	MySQLConnector sql = new MySQLConnector();
    	 sql.executeSQL("DELETE FROM author WHERE id = "+ id);
    }

    public List<Author> selectAllAuthors() {
        MySQLConnector sql = new MySQLConnector();
        ResultSet resultSet = sql.selectSQL("SELECT id, name FROM author");
        authorList.clear(); 

        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    authorList.add(new Author(resultSet.getInt("id"), resultSet.getString("name")));
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

        return authorList;
    }

    public List<Author> selectAuthorsByName(String nameSearch) {
        MySQLConnector sql = new MySQLConnector();
        ResultSet resultSet = sql.selectSQL("SELECT id, name FROM author WHERE name LIKE '%" + nameSearch + "%'");
        authorList.clear();
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    authorList.add(new Author(resultSet.getInt("id"), resultSet.getString("name")));
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

        return authorList;
    }
}