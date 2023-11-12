package Publisher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DataBaseConnector.MySQLConnector;

public class PublisherDAO {
	public List<Publisher> publisherList = new ArrayList<>();

    public int insertPublisher(String name) {
        MySQLConnector sql = new MySQLConnector();
        return sql.executeProcedure("SP_insertPublisher", name);
    }
    
    public void deletePublisher(int id) {
    	MySQLConnector sql = new MySQLConnector();
    	 sql.executeSQL("DELETE FROM publisher WHERE id = "+ id);
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
    
    public List<Publisher> selectPublisherByCountTitles() {
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
}
