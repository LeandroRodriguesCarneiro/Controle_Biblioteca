package Loan;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import Book.Book;
import Book.BooksBorrowed;
import DataBaseConnector.MySQLConnector;

public class LoanDAO {
	public List<Loan> loanList = new ArrayList<>();

    public void insertLoan(int idStudent, LocalDate dateInit, LocalDate dateEnd, List<Book> listBooks) {
        MySQLConnector sql = new MySQLConnector();
        
        int idLoan = sql.executeProcedure("SP_InsertLoan", idStudent, dateInit, dateEnd);
        for (Book book: listBooks) {
        	sql.executeProcedure("SP_lendBook", idLoan, book.getId());
        }
    }

    public void returBook(Loan loan, List <Book> listBooks, LocalDate deliveryDate) {
    	MySQLConnector sql = new MySQLConnector();
		for (Book book: listBooks) {
			sql.executeProcedure("SP_returnBook", loan.getId(), book.getId(), deliveryDate);
		}
		sql.executeProcedure("SP_UpdateLoan", loan.getId(), loan.getIdStudent(), deliveryDate);
    }

    public List<Loan> selectLoanBooks(Integer idStudent) {
        MySQLConnector sql = new MySQLConnector();
        String query = "SELECT * FROM vw_borrowedbooks WHERE student_id =" +idStudent;
        ResultSet resultSet = sql.selectSQL(query);
        loanList.clear();
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                	
                	if(resultSet.isFirst()) {
                		loanList.add(new Loan(resultSet.getInt("id"),resultSet.getInt("student_id"), resultSet.getDate("loan_date_init").toLocalDate(), 
                				resultSet.getDate("loan_date_end").toLocalDate(),resultSet.getString("loan_status")));
                	}
                	loanList.get(0).addListBooks(new BooksBorrowed(resultSet.getInt("book_id"),resultSet.getString("book_title"),resultSet.getString("book_isbn"),resultSet.getInt("book_year"),
                			resultSet.getInt("book_quantity"),resultSet.getString("book_publisher"),resultSet.getString("book_author"),resultSet.getString("book_genre"),
                			resultSet.getString("borrowed_books_status")));
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
        return loanList;
    }
}
