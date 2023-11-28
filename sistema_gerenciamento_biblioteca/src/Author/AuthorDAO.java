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
    
    public void updateAuthor(int id, String name, boolean active) throws Exception {
		try {
			MySQLConnector sql = new MySQLConnector();
	    	sql.executeProcedure("SP_UpdateAuthor", id, name, active);	
		}catch(Exception e) {
    		throw new Exception("Este autor já foi adicionado");
    	}
    	
	}
    
    public void deleteAuthor(int id) throws Exception {
    	try{
    		MySQLConnector sql = new MySQLConnector();
    		sql.executeProcedure("SP_DeleteAuthor", id);
    	}catch(Exception e) {
    		throw new Exception ("Este autor possui livros associados no sistema");
    	}
    }

    public List<Author> selectAuthorsByName(String nameSearch, boolean active) {
        MySQLConnector sql = new MySQLConnector();
        String query = "SELECT id, name, active FROM author WHERE 1=1";
        if (nameSearch != null && !nameSearch.isEmpty()) {
            query += " AND name LIKE '%" + nameSearch + "%'";
        }
        if (active) {
            query += " AND active = 1"; 
        }else {
        	query += " AND active = 0";
        }

        ResultSet resultSet = sql.selectSQL(query);
        authorList.clear();
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    authorList.add(new Author(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getBoolean("active")));
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