package Genres;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DataBaseConnector.MySQLConnector;

public class GenresDAO {
	public List<Genres> genresList = new ArrayList<>();

    public void insertGenres(String name) {
        MySQLConnector sql = new MySQLConnector();
        sql.executeSQL("INSERT INTO genre (name) VALUES ('" + name + "')");
    }
    
    public void insertGenresBooks(int idBook, Genres genres) {
    	MySQLConnector sql = new MySQLConnector();
        sql.executeSQL("INSERT INTO genres_books (id_books, id_genre) VALUES ("+idBook+","+genres.getId()+")");
    }
    
    public void deleteGenre(int id) {
    	MySQLConnector sql = new MySQLConnector();
    	 sql.executeSQL("DELETE FROM genre WHERE id = "+ id);
    }

    public List<Genres> selectAllGenres() {
        MySQLConnector sql = new MySQLConnector();
        ResultSet resultSet = sql.selectSQL("SELECT id, name FROM genre");
        genresList.clear(); 

        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                	genresList.add(new Genres(resultSet.getInt("id"), resultSet.getString("name")));
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

        return genresList;
    }

    public List<Genres> selectGenresByName(String nameSearch) {
        MySQLConnector sql = new MySQLConnector();
        ResultSet resultSet = sql.selectSQL("SELECT id, name FROM genre WHERE name LIKE '%" + nameSearch + "%'");
        genresList.clear();
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                	genresList.add(new Genres(resultSet.getInt("id"), resultSet.getString("name")));
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

        return genresList;
    }
}
