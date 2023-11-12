package DataBaseConnector;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnector {
	private String url = "jdbc:mysql://localhost:3306/biblioteca";
    private String user = "root";
    private String password = "";

    public Connection startConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public int insertSQL(String query) {
        int id = 0;

        try (Connection connection = startConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
            id = 0;
        }

        return id;
    }
    
    private CallableStatement prepareCallableStatement(Connection connection, String procedureName, Object... parameters) throws SQLException {
        StringBuilder callString = new StringBuilder("{CALL " + procedureName + "(");

        for (int i = 0; i < parameters.length; i++) {
            if (i > 0) {
                callString.append(",");
            }
            callString.append("?");
        }

        callString.append(")}");

        CallableStatement callableStatement = connection.prepareCall(callString.toString());

        for (int i = 0; i < parameters.length; i++) {
            callableStatement.setObject(i + 1, parameters[i]);
        }

        return callableStatement;
    }
    
    public int executeProcedure(String procedureName, Object... parameters) {
        try (Connection connection = startConnection();
             CallableStatement callableStatement = prepareCallableStatement(connection, procedureName, parameters)) {

            boolean hasResults = callableStatement.execute();

            if (hasResults) {
                ResultSet resultSet = callableStatement.getResultSet();
                
                if (resultSet.next()) {
                    return resultSet.getInt(1); 
                }
            }

            return 0; 
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    
    public void executeSQL(String query) {
        try {
        	Connection connection = startConnection();
        	Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public ResultSet selectSQL(String query) {
        ResultSet resultSet = null;
        Connection connection = startConnection();

        try {
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
            resultSet = statement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return resultSet;
    } 
}
