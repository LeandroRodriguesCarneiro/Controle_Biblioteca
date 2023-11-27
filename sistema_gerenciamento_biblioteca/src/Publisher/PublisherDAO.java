package Publisher;
//-*- coding: utf-8 -*-
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DataBaseConnector.MySQLConnector;

public class PublisherDAO {
	public List<Publisher> publisherList = new ArrayList<>();

    public void insertPublisher(String name) throws Exception {
    	try {
	        MySQLConnector sql = new MySQLConnector();
	        sql.executeProcedure("SP_insertPublisher", name);
    	}catch(Exception e) {
    		throw new Exception("Está editora já foi adicionada");
    	}
    }
    
    public void updatePublisher(int id, String name) throws Exception {
    	try {
			MySQLConnector sql = new MySQLConnector();
	    	sql.executeProcedure("SP_UpdatePublisher", id, name);
    	}catch(Exception e) {
    		throw new Exception("Está editora já foi adicionada");
    	}
	}
    
    public void deletePublisher(int id) throws Exception {
    	try {
    	MySQLConnector sql = new MySQLConnector();
    	sql.executeProcedure("SP_DeletePublisher", id);
    	}catch(Exception e) {
    		throw new Exception("Está editora possui livros associados");
    	}
    }

    public List<Publisher> selectAllPublisher() {
        MySQLConnector sql = new MySQLConnector();
        ResultSet resultSet = sql.selectSQL("SELECT id, name FROM publisher");
        publisherList.clear(); 

        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                	publisherList.add(new Publisher(resultSet.getInt("id"), resultSet.getString("name")));
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

        return publisherList;
    }

    public List<Publisher> selectPublisherByName(String nameSearch) {
        MySQLConnector sql = new MySQLConnector();
        ResultSet resultSet = sql.selectSQL("SELECT id, name FROM publisher WHERE name LIKE '%" + nameSearch + "%'");
        publisherList.clear();
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                	publisherList.add(new Publisher(resultSet.getInt("id"), resultSet.getString("name")));
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

        return publisherList;
    }
    
    public List<Publisher> selectPublisherByCountTitles10() {
        MySQLConnector sql = new MySQLConnector();
        ResultSet resultSet = sql.selectSQL("SELECT \r\n"
        		+ "	pub.id, \r\n"
        		+ "    pub.name as publisher,\r\n"
        		+ "    COUNT(bok.id) as qtd_titles\r\n"
        		+ "FROM publisher AS pub \r\n"
        		+ "LEFT JOIN book AS bok ON pub.id = bok.id_publisher \r\n"
        		+ "GROUP BY pub.id, pub.name \r\n"
        		+ "ORDER BY qtd_titles DESC \r\n"
        		+ "LIMIT 10");
        publisherList.clear();
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                	publisherList.add(new Publisher(resultSet.getInt("id"), resultSet.getString("publisher")));
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

        return publisherList;
    }
    
    public List<Publisher> selectPublisherByCountTitles(String name, int qtd) {
        MySQLConnector sql = new MySQLConnector();
        String query = "SELECT \r\n"
        		+ "	   pub.id, \r\n"
        		+ "    pub.name as publisher,\r\n"
        		+ "    COUNT(bok.id) as qtd_titles\r\n"
        		+ "FROM publisher AS pub \r\n"
        		+ "LEFT JOIN book AS bok ON pub.id = bok.id_publisher \r\n";
        
        if(name != null && !name.isEmpty()) {
        	query +="WHERE pub.name LIKE '%"+name+"%'";
        }
        		
		query+= " GROUP BY pub.id, pub.name ";
		
		if(qtd>0) {
			query+=" HAVING qtd_titles >="+qtd;
		}
		
		query +=" ORDER BY qtd_titles DESC ";
        ResultSet resultSet = sql.selectSQL(query);
        publisherList.clear();
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                	Publisher publisher = new Publisher(resultSet.getInt("id"), resultSet.getString("publisher"));
                	publisher.setQtd_titles(resultSet.getInt("qtd_titles"));
                	publisherList.add(publisher);
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

        return publisherList;
    }
}
