package Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DataBaseConnector.MySQLConnector;


public class StudentDAO {
	public List<Student> listStudent = new ArrayList<>(); 

	public void insertStudent(String name, long numberRegistration) {
        MySQLConnector sql = new MySQLConnector();
        sql.insertSQL("INSERT INTO student(number_registration, name, borrowed_books, DEBITS) VALUES ('"+numberRegistration+"','"+name+"', 0, 0)");
    }
    
    public void deleteStudent(int id) {
    	MySQLConnector sql = new MySQLConnector();
    	 sql.executeSQL("DELETE FROM student WHERE id = "+ id);
    }
    
    public void payDebits(int id,float pay) {
    	MySQLConnector sql = new MySQLConnector();
    	 sql.executeSQL("UPDATE student SET DEBITS = DEBITS - "+pay+" WHERE id ="+id);
    }
    
    public void addDebits(int id, float debits) {
    	MySQLConnector sql = new MySQLConnector();
   	 	sql.executeSQL("UPDATE student SET DEBITS = "+debits+" WHERE id ="+id);
    }
    
    public List<Student> selectAllStudents() {
        MySQLConnector sql = new MySQLConnector();
        ResultSet resultSet = sql.selectSQL("SELECT \r\n"
        		+ "	   id, \r\n"
        		+ "    number_registration, \r\n"
        		+ "    name, \r\n"
        		+ "    borrowed_books, \r\n"
        		+ "    DEBITS \r\n"
        		+ "    FROM student ");
        listStudent.clear(); 

        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                	listStudent.add(new Student(resultSet.getInt("id"),resultSet.getInt("borrowed_books"),resultSet.getString("name"),resultSet.getLong("number_registration"),
                			resultSet.getFloat("DEBITS")));
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

        return listStudent;
    }
    
    public List<Student> selectStudentByNumerRegistration(long numberRegistration) {
        MySQLConnector sql = new MySQLConnector();
        ResultSet resultSet = sql.selectSQL("SELECT \r\n"
        		+ "	   id, \r\n"
        		+ "    number_registration, \r\n"
        		+ "    name, \r\n"
        		+ "    borrowed_books, \r\n"
        		+ "    DEBITS \r\n"
        		+ "    FROM student "
        		+ " WHERE number_registration = "+numberRegistration);
        listStudent.clear(); 

        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                	listStudent.add(new Student(resultSet.getInt("id"),resultSet.getInt("borrowed_books"),resultSet.getString("name"),resultSet.getLong("number_registration"),
                			resultSet.getFloat("DEBITS")));
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

        return listStudent;
    }
    
}
