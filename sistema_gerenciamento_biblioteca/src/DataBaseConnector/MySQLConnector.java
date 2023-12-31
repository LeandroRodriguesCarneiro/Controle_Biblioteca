package DataBaseConnector;
//-*- coding: utf-8 -*-
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
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
    
    public int executeProcedure(String procedureName, Object... parameters) throws Exception {
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
        }catch (SQLIntegrityConstraintViolationException e) {
        	if(e.getErrorCode() == 1062) {
        		throw new Exception("Entrada duplicada"); 
        	}
        } catch (SQLException e) {
            if(e.getErrorCode() == 1644) {
            	if (e.getMessage().equals("Tem emprestimos relacionados a esse livro")){
            		throw new Exception("Erro de Exclusao");
            	}
            	if (e.getMessage().equals("Tem emprestimos relacionados a esse aluno")) {
            		throw new Exception("Erro de Exclusao");
            	}
            	if (e.getMessage().equals("Tem livros relacionados a esse genero")) {
            		throw new Exception("Erro de Exclusao");
            	}
            	if (e.getMessage().equals("Tem livros relacionados a essa editora")) {
            		throw new Exception("Erro de Exclusao");
            	}
            	if (e.getMessage().equals("Tem livros relacionados a esse autor")) {
            		throw new Exception("Erro de Exclusao");
            	}
            	if (e.getMessage().equals("Este livro está emprestado e não pode ter o status alterado.")) {
            		throw new Exception("Erro de Alteracao");
            	}
            	if (e.getMessage().equals("O aluno possui livros emprestados e não pode ser inativado.")) {
            		throw new Exception("Erro de Alteracao");
            	}
            	if (e.getMessage().equals("O aluno possui empréstimos ativos e a coluna borrowed_books não pode ser alterada.")) {
            		throw new Exception("Erro de Alteracao");
            	}
            	System.out.println(e.getMessage());
            }
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
