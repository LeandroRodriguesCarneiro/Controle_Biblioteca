package Genres;
//-*- coding: utf-8 -*-
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DataBaseConnector.MySQLConnector;

public class GenresDAO {
	public List<Genres> genresList = new ArrayList<>();

    public void insertGenres(String name) throws Exception {
    	try {
	        MySQLConnector sql = new MySQLConnector();
	        sql.executeProcedure("SP_insertGenre", name);
    	}catch(Exception e) {
    		throw new Exception("Este gênero ja foi adicionado");
    	}
    }
    
    public void deleteGenre(int id) throws Exception {
    	try {
	    	MySQLConnector sql = new MySQLConnector();
	    	sql.executeProcedure("SP_DeleteGenre", id);
    	}catch(Exception e) {
    		throw new Exception("Este gênero possui livros associados");
    	}
    }
    
    public void updateGenre(int id, String name) throws Exception {
    	try {
	    	MySQLConnector sql = new MySQLConnector();
	   	 	sql.executeProcedure("SP_UpdateGenre", id, name);
    	}catch(Exception e) {
    		throw new Exception("Este gênero ja foi adicionado");
    	}
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
