package Loan;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import Book.Book;
import Book.BooksBorrowed;
import DataBaseConnector.MySQLConnector;
import Student.StudentDAO;

public class LoanDAO {
	public List<Loan> loanList = new ArrayList<>();

    public void insertLoan(int idStudent, LocalDate dateInit, LocalDate dateEnd, List<Book> listBooks) {
        MySQLConnector sql = new MySQLConnector();
        
        int idLoan = sql.insertSQL("INSERT INTO loan(id_student, date_init, date_end, status) VALUES ("+idStudent+", '"+dateInit+"','"+dateEnd+"','Emprestimo')");
        for (Book book: listBooks) {
        	sql.insertSQL("INSERT INTO borrowed_books (id_loan,id_book,status) VALUES ("+idLoan+","+book.getId()+",'Emprestimo')");
        }
        StudentDAO studentDAO = new StudentDAO();
        studentDAO.updateBorrowedBooks(idStudent, listBooks.size());
    }

    public List<Loan> selectLoanBooks(Integer idStudent) {
        MySQLConnector sql = new MySQLConnector();
        String query = "SELECT \r\n"
        		+ "    loa.id,\r\n"
        		+ "    loa.id_student AS student_id,\r\n"
        		+ "    loa.date_init AS loan_date_init,\r\n"
        		+ "    loa.date_end AS loan_date_end,\r\n"
        		+ "    loa.status AS loan_status,\r\n"
        		+ "    bok.id AS book_id,\r\n"
        		+ "    bok.title AS book_title,\r\n"
        		+ "    bok.isbn AS book_isbn,\r\n"
        		+ "    bok.year_publication AS book_year,\r\n"
        		+ "    bok.quantity AS book_quantity,\r\n"
        		+ "    pub.name AS book_publisher,\r\n"
        		+ "    GROUP_CONCAT(DISTINCT atr.name) AS book_author,\r\n"
        		+ "    GROUP_CONCAT(DISTINCT gen.name) AS book_genre,\r\n"
        		+ "    bob.status AS borrowed_books_status\r\n"
        		+ "FROM loan as loa\r\n"
        		+ "LEFT JOIN borrowed_books AS bob ON loa.id = bob.id_loan\r\n"
        		+ "LEFT JOIN book AS bok ON bob.id_book = bok.id\r\n"
        		+ "LEFT JOIN publisher as pub ON bok.id_publisher = pub.id \r\n"
        		+ "LEFT JOIN genres_books AS gbk ON bok.id = gbk.id_books\r\n"
        		+ "LEFT JOIN genre AS gen ON gbk.id_genre = gen.id\r\n"
        		+ "LEFT JOIN authors_books AS atb ON bok.id = atb.id_books\r\n"
        		+ "LEFT JOIN author AS atr ON atb.id_author = atr.id\r\n"
        		+ "WHERE loa.status IN ('Emprestimo','Atrasado','Devendo') AND  bob.status <> 'Devolvido' AND loa.id_student =" +idStudent;

        query += " GROUP BY bok.id";
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
    
    public void returBook(Loan loan, List <Book> listBooks, List<BooksBorrowed> booksNotReturned) {
    	MySQLConnector sql = new MySQLConnector();
    	if(booksNotReturned.isEmpty()) {
    		sql.executeSQL("UPDATE borrowed_books SET status= 'Devolvido' WHERE id_loan = "+loan.getId());
    	}else {
    		for (Book book: listBooks) {
    			sql.executeSQL("UPDATE borrowed_books SET status= 'Devolvido' WHERE id_loan = "+loan.getId()+" AND id_book = "+book.getId());
    		}
    		for (BooksBorrowed book: booksNotReturned) {
    			sql.executeSQL("UPDATE borrowed_books SET status= 'Atrasado' WHERE id_loan = "+loan.getId()+" AND id_book = "+book.getId());
    		}	
    	}
    	
    	if(LocalDate.now().isAfter(loan.getDateEnd())) {
    		if(booksNotReturned.isEmpty()) {
    			sql.executeSQL("UPDATE loan SET status= 'Devolvido' WHERE id = "+loan.getId());
    		}else {
    			sql.executeSQL("UPDATE loan SET status= 'Atrasado' WHERE id = "+loan.getId());
    		}
    	}else {
    		if(booksNotReturned.isEmpty()) {
    			sql.executeSQL("UPDATE loan SET status= 'Devolvido' WHERE id = "+loan.getId());
    		}
    	}
    }
}
