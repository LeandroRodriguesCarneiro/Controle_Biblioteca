package Student;
//-*- coding: utf-8 -*-
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DataBaseConnector.MySQLConnector;


public class StudentDAO {
	public List<Student> listStudent = new ArrayList<>(); 

	public void insertStudent(String name, long numberRegistration) throws Exception {
		try {
			MySQLConnector sql = new MySQLConnector();
	        sql.executeProcedure("SP_InsertStudent", name, numberRegistration);
		}catch(Exception e) {
        	throw new Exception("Esse Número de matricula ja está cadastrado em um aluno.");
		}
    }
    
    public void deleteStudent(int id) throws Exception {
    	try {
    		MySQLConnector sql = new MySQLConnector();
       	 	sql.executeProcedure("SP_DeleteStudent", id);
    	}catch(Exception e) {
    		throw new Exception("Este aluno não pode ser excluido pois possui emprestimos.");
    	}
    }
    
    public void payDebits(int id,float pay) throws Exception {
    	try {
    		MySQLConnector sql = new MySQLConnector();
        	sql.executeProcedure("SP_PayDebits", id, pay);
    	}catch(Exception e) {
        	e.printStackTrace();
		}
    	
    }
    
    public void updateStudent(int id, String name, Long numberRegistration, int borrowedBooks, float debits) throws Exception {
		try {
			MySQLConnector sql = new MySQLConnector();
	        sql.executeProcedure("SP_UpdateStudent", id, name, numberRegistration, borrowedBooks, debits);
		}catch(Exception e) {
			throw new Exception("Esse Número de matricula ja está cadastrado em um aluno.");
		}
    	
	}
    
    public List<Student> selectAllStudent() {
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

    public List<Student> selectStudentByFilter(String name, Long numberRegistration) {
        MySQLConnector sql = new MySQLConnector();
        String query = "SELECT \r\n"
        		+ "	   id, \r\n"
        		+ "    number_registration, \r\n"
        		+ "    name, \r\n"
        		+ "    borrowed_books, \r\n"
        		+ "    DEBITS \r\n"
        		+ "    FROM student WHERE 1=1";
        
        if(name != null && !name.isEmpty()) {
        	query+= " AND name LIKE '%"+name+"%'";
        }
        
        if(numberRegistration>0) {
        	query +=" AND number_registration = "+numberRegistration;
        }
        ResultSet resultSet = sql.selectSQL(query);
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
    
    public List<Student> selectStudentsMaximumNumberBooksBorrowed(String name) {
        MySQLConnector sql = new MySQLConnector();
        String query = "SELECT \r\n"
        		+ "	id,\r\n"
        		+ "    number_registration,\r\n"
        		+ "    name,\r\n"
        		+ "    borrowed_books,\r\n"
        		+ "    DEBITS * (-1) AS DEBITS\r\n"
        		+ "FROM student\r\n"
        		+ "WHERE borrowed_books = 3";
        
        if(name != null && !name.isEmpty()) {
        	query+=" AND name LIKE '%"+name+"%'";
        }
        
        ResultSet resultSet = sql.selectSQL(query);
        
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
    public List<Student> selectStudentsDebt(float debits, String name) {
        MySQLConnector sql = new MySQLConnector();
        String query = "SELECT \r\n"
        		+ "	id,\r\n"
        		+ "    number_registration,\r\n"
        		+ "    name,\r\n"
        		+ "    borrowed_books,\r\n"
        		+ "    DEBITS \r\n"
        		+ "FROM student\r\n"
        		+ "WHERE DEBITS <= ("+debits+" * (-1))";
        if(name != null && !name.isEmpty()) {
        	query += " AND name LIKE '%"+name+"%'";
        }
        
        ResultSet resultSet = sql.selectSQL(query);
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
