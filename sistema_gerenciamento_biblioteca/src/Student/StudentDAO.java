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
        sql.executeProcedure("SP_InsertStudent", name, numberRegistration);
    }
    
    public void deleteStudent(int id) {
    	MySQLConnector sql = new MySQLConnector();
    	 sql.executeProcedure("SP_DeleteStudent", id);
    }
    
    public void payDebits(int id,float pay) {
    	MySQLConnector sql = new MySQLConnector();
    	sql.executeProcedure("SP_PayDebits", id, pay);
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
