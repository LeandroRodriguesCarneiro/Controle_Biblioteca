package Author;
//-*- coding: utf-8 -*-
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DataBaseConnector.MySQLConnector;

public class AuthorDAO {
    public List<Author> authorList = new ArrayList<>();

    public void insertAuthor(String name) throws Exception {
    	try {
    		MySQLConnector sql = new MySQLConnector();
            sql.executeProcedure("SP_InsertAuthor", name);
    	}catch(Exception e) {
    		throw new Exception("Este autor já foi adicionado");
    	}
        
    }
    
    public void updateAuthor(int id, String name) throws Exception {
		try {
			MySQLConnector sql = new MySQLConnector();
	    	sql.executeProcedure("SP_UpdateAuthor", id, name);	
		}catch(Exception e) {
    		throw new Exception("Este autor já foi adicionado");
    	}
    	
	}
    
    public void deleteAuthor(int id) throws Exception {
    	try{
    		MySQLConnector sql = new MySQLConnector();
    		sql.executeProcedure("SP_DeleteAuthor", id);
    	}catch(Exception e) {
    		throw new Exception ("Este autor possui livros registrados no sistema");
    	}
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
        String query = "SELECT id, name FROM author";
        if(nameSearch != null && !nameSearch.isEmpty()) {
        	query+=" WHERE name LIKE '%" +nameSearch+ "%'";
        }
        ResultSet resultSet = sql.selectSQL(query);
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