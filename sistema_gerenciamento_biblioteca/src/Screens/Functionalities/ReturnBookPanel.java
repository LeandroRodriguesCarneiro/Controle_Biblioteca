package Screens.Functionalities;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import Book.Book;
import Book.BookDAO;
import Student.Student;
import Student.StudentDAO;
import Loan.Loan;
import Loan.LoanDAO;

public class ReturnBookPanel extends JPanel{
	private JTable table, tableBorrowed;
    private DefaultTableModel tableModel,tableModelBorrowed;
    private static final long serialVersionUID = -4843807817212241104L;
    private JTextField txtNumberRegistration, txtISBN, txtDays;
    private JButton btnAdd, btnRemove,btnNumberRegistration, backButton, btnLoan, btnReNew, btnView;
    private JCheckBox chkReNew;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private BookDAO bookDAO = new BookDAO();
    private JLabel lblTitlePage, lblNumberRegistration, lblISBN, lblStudent, lblDays, lblStatus, lblBorrowedTable, lblchkReNew;
    private Student student;
    private List<Book> booksList = new ArrayList<>();
    private LoanDAO loanDAO = new LoanDAO();
    private JScrollPane scrollPane, scrollPaneBorrowed;
    private Loan loan;
    private boolean componentsVisible = false, componentReNew = false;
    public ReturnBookPanel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardPanel = cardPanel;
        setLayout(null);
        
        lblTitlePage = new JLabel("Devolucao");
        lblTitlePage.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitlePage.setBounds(20, 10, 400, 30);
        add(lblTitlePage);
        
        backButton = new JButton("Voltar");
        backButton.setBounds(870, 10, 80, 30);
        add(backButton);
        
        lblchkReNew = new JLabel("Renovar?");
        lblchkReNew.setBounds(675, 80, 60, 25);
        add(lblchkReNew);
        
        chkReNew = new JCheckBox();
        chkReNew.setBounds(740, 72, 40, 40);
        add(chkReNew);
        
        chkReNew.addActionListener(e -> {
        	lblDays.setVisible(!componentReNew);
            txtDays.setVisible(!componentReNew);
            btnReNew.setVisible(!componentReNew);
            componentReNew = !componentReNew;
        });
        
        lblDays = new JLabel("Dias de emprestimo (1 - 15): ");
        lblDays.setBounds(660, 115, 160, 25);
        add(lblDays);
        
        txtDays = new JTextField();
        txtDays.setBounds(830, 115, 40, 25);
        add(txtDays);
        
        lblNumberRegistration = new JLabel("Insira o numero de matricula:");
        lblNumberRegistration.setBounds(10,	 45, 200, 25);
        add(lblNumberRegistration);
        
        txtNumberRegistration = new JTextField();
        txtNumberRegistration.setBounds(265, 45, 90, 25);
        add(txtNumberRegistration);
        
        btnNumberRegistration = new JButton("Buscar Aluno");
        btnNumberRegistration.setBounds(530, 45, 125, 25);
        add(btnNumberRegistration);
        
        btnNumberRegistration.addActionListener(e -> {
        	Long NumberRegistration = null;
        	try {
        		if(txtNumberRegistration.getText().isEmpty() || txtNumberRegistration.getText().length() != 10) {
            		JOptionPane.showMessageDialog(null, "Por favor!, digite um numero de matricula com 10 digitos");
            		txtNumberRegistration.setText("");
            		return;
            	}
        		NumberRegistration = Long.parseLong(txtNumberRegistration.getText());
        	}catch(NumberFormatException ex) {
        		JOptionPane.showMessageDialog(null, "Por favor!, digite um numero com 10 digitos");
        		txtNumberRegistration.setText("");
        		return;
        	}
        	
        	StudentDAO studentDAO = new StudentDAO();
        	List<Student> studentsList = studentDAO.selectStudentByNumerRegistration(NumberRegistration);
        	if(studentsList.isEmpty()) {
        		JOptionPane.showMessageDialog(null, "Numero de matricula nao encontrado");
        		txtNumberRegistration.setText("");
        		return;
        	}
        	student = studentsList.get(0);
        	List<Loan> loansList = loanDAO.selectLoanBooks(student.getId());
        	if(loansList.isEmpty()) {
        		JOptionPane.showMessageDialog(null, "Este aluno nao possui emprestimos");
        		txtNumberRegistration.setText("");
        		return;
        	}
        	loan = loanDAO.selectLoanBooks(student.getId()).get(0);
        	lblStudent.setText("Aluno: "+student.getName()+", Numero de matricula: "+student.getNumberRegistration());
        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        	lblStatus.setText("Data de Emprestimo: "+loan.getDateInit().format(formatter)+", Data de devolucao: "+loan.getDateEnd().format(formatter));
        	if(LocalDate.now().isBefore(loan.getDateEnd()) || loan.getDateEnd().isEqual(LocalDate.now())){
                lblchkReNew.setVisible(true);
            	chkReNew.setVisible(true);
        	}
        	setVisilble();
        	txtNumberRegistration.setText("");
        	refreshBooksBorrowedIntoTable();
        });
        
        lblISBN = new JLabel("Insira o ISBN do Livro: ");
        lblISBN.setBounds(10, 80, 200, 25);
        add(lblISBN);
        
        txtISBN = new JTextField();
        txtISBN.setBounds(265, 80, 90, 25);
        add(txtISBN);
        
        btnAdd = new JButton("Buscar Livro");
        btnAdd.setBounds(530, 80, 125, 25);
        add(btnAdd);
        
        btnAdd.addActionListener(e -> {
        	if (txtISBN.getText().isEmpty()) {
		        JOptionPane.showMessageDialog(null, "Por favor, preencha o ISBN.");
		        return;
		    } 
		    if (txtISBN.getText().length() != 13 || !txtISBN.getText().matches("\\d+")) {
		        JOptionPane.showMessageDialog(null, "Por favor, o ISBN precisa ter 13 dígitos.");
		        return;
		    }
		    bookDAO = new BookDAO();
		    List<Book> booksSelectedList = bookDAO.selectBooksByISBN(txtISBN.getText());
		    if (booksSelectedList.isEmpty()) {
		    	JOptionPane.showMessageDialog(null, "ISBN nao encontrado");
        		return;
		    }
		    for (Book book: booksList) {
		        if (book.getId() == booksSelectedList.get(0).getId()) {
		            JOptionPane.showMessageDialog(null, "Este livro já foi adicionado.");
		            return;
		        }
		    }
		    boolean livroJaEmprestado = false;
		    for (Book bookBorrowed : loan.getListBooks()) {
		        if (bookBorrowed.getId() == booksSelectedList.get(0).getId()) {
		            livroJaEmprestado = true;
		            break;
		        }
		    }
		    if (livroJaEmprestado) {
		        if (booksList.size() < loan.getListBooks().size()) {
		            booksList.add(booksSelectedList.get(0));
		            refreshBookTable();
		            txtISBN.setText("");
		        } else {
		            JOptionPane.showMessageDialog(null, "Você só pode devolver no máximo a mesma quantidade de livros emprestados.");
		        }
		    } else {
		        JOptionPane.showMessageDialog(null, "Este livro não foi emprestado.");
		    }
		    
        });
        
        lblStudent = new JLabel();
        lblStudent.setBounds(10, 115, 600, 25);
        add(lblStudent);
        
        backButton.addActionListener(e -> {
        	setInvisible();
        	cardLayout.show(cardPanel, "MainPanel");
        	});
        
        tableModel = new DefaultTableModel() {
            private static final long serialVersionUID = -9049266189071413309L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("ID");
        tableModel.addColumn("Titulo");
        tableModel.addColumn("ISBN");
        tableModel.addColumn("Editora");
        tableModel.addColumn("Ano de publicacao");
        tableModel.addColumn("Quantidade");
        tableModel.addColumn("Autores");
        tableModel.addColumn("Generos");

        table = new JTable(tableModel);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setPreferredWidth(0);
        columnModel.getColumn(0).setWidth(0);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 150, 930, 150);
        add(scrollPane);
        
        tableModelBorrowed = new DefaultTableModel() {
            private static final long serialVersionUID = -9049266189071478958L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModelBorrowed.addColumn("ID");
        tableModelBorrowed.addColumn("Titulo");
        tableModelBorrowed.addColumn("ISBN");
        tableModelBorrowed.addColumn("Editora");
        tableModelBorrowed.addColumn("Ano de publicacao");
        tableModelBorrowed.addColumn("Quantidade");
        tableModelBorrowed.addColumn("Autores");
        tableModelBorrowed.addColumn("Generos");
                
        lblBorrowedTable = new JLabel("Livros Emprestados:");
        lblBorrowedTable.setBounds(10, 375, 200, 25);
        add(lblBorrowedTable);
        
        lblStatus = new JLabel();
        lblStatus.setBounds(10, 345, 500, 25);
        add(lblStatus);
        
        tableBorrowed = new JTable(tableModelBorrowed);
        TableColumnModel columnModelBorroed = tableBorrowed.getColumnModel();
        columnModelBorroed.getColumn(0).setMaxWidth(0);
        columnModelBorroed.getColumn(0).setMinWidth(0);
        columnModelBorroed.getColumn(0).setPreferredWidth(0);
        columnModelBorroed.getColumn(0).setWidth(0);
        scrollPaneBorrowed = new JScrollPane(tableBorrowed);
        scrollPaneBorrowed.setBounds(10, 400, 930, 71);
        add(scrollPaneBorrowed);
        
        btnLoan = new JButton("Devolver");
        btnLoan.setBounds(10, 305, 125, 25);
        add(btnLoan);
        btnLoan.addActionListener(e ->{
    		if(booksList.isEmpty()) {
    			JOptionPane.showMessageDialog(null, "Por favor!, insira os livros que deseja devolver");
    			return;
    		}
        	try {
        		loanDAO.returBook(loan, booksList, LocalDate.now());
            	setInvisible();
            	JOptionPane.showMessageDialog(null, "Emprestimo realizado com sucesso!");
        	}catch(Exception ex) {
        		JOptionPane.showMessageDialog(null, "Houve um problema");
        		return;
        	}
        	
        });
        
        btnRemove = new JButton("Remover");
        btnRemove.setBounds(150, 305, 125, 25);
        add(btnRemove);
        btnRemove.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int bookIdToRemove = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                Book bookToRemove = null;
                for (Book book : booksList) {
                    if (book.getId() == bookIdToRemove) {
                        bookToRemove = book;
                        break;
                    }
                }
                if (bookToRemove != null) {
                    booksList.remove(bookToRemove);
                    refreshBookTable();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, selecione um livro na tabela.");
            }
        });
        
        btnView = new JButton("Visualizar");
        btnView.setBounds(290, 305, 125, 25);
        add(btnView);
        btnView.addActionListener(e ->{
            lblStatus.setVisible(!componentsVisible);
            scrollPaneBorrowed.setVisible(!componentsVisible);
            lblBorrowedTable.setVisible(!componentsVisible);
            componentsVisible = !componentsVisible;
        });
        
        btnReNew = new JButton("Renovar");
        btnReNew.setBounds(430, 305, 125, 25);
        add(btnReNew);
        setInvisible();
    }
    public void setInvisible() {
    	student = null;
    	booksList.clear();
    	loan = null;
    	refreshBookTable();
    	btnReNew.setVisible(false);
    	btnView.setVisible(false);
    	lblStatus.setVisible(false);
    	scrollPaneBorrowed.setVisible(false);
    	lblBorrowedTable.setVisible(false);
    	lblISBN.setVisible(false);
    	txtISBN.setVisible(false);
    	btnAdd.setVisible(false);
    	lblStudent.setVisible(false);
    	lblStudent.setVisible(false);
    	scrollPane.setVisible(false);
    	btnLoan.setVisible(false);
    	btnRemove.setVisible(false);
    	lblDays.setVisible(false);
    	txtDays.setVisible(false);
    	lblchkReNew.setVisible(false);
    	chkReNew.setVisible(false);
    }
    
    public void setVisilble() {
    	lblISBN.setVisible(true);
    	txtISBN.setVisible(true);
    	btnAdd.setVisible(true);
    	lblStudent.setVisible(true);
    	lblStudent.setVisible(true);
    	scrollPane.setVisible(true);
    	btnLoan.setVisible(true);
    	btnRemove.setVisible(true);
    	btnView.setVisible(true);
    }
    
    public void resetComponents() {
        setInvisible(); 
    }
    
    public void refreshBookTable() {
    	loadBooksIntoTable();
    }

    public void loadBooksIntoTable() {
        tableModel.setRowCount(0);
        if (!booksList.isEmpty()) {
            booksList.sort(Comparator.comparingInt(Book::getId));
            SwingUtilities.invokeLater(() -> {
                for (Book book : booksList) {
                    tableModel.addRow(new Object[]{
                            book.getId(),
                            book.getTitle(),
                            book.getIsbn(),
                            book.getPublisher(),
                            book.getYearPublication(),
                            book.getQuantity(),
                            String.join(",", book.getAuthor()),
                            String.join(",", book.getGenre())
                    });
                }
            });
        }
    }
    
    public void refreshBooksBorrowedIntoTable() {
    	loadBooksBorrowedIntoTable();
    }
    
    public void loadBooksBorrowedIntoTable() {
    	tableModelBorrowed.setRowCount(0);
        if (!loan.getListBooks().isEmpty()) {
            booksList.sort(Comparator.comparingInt(Book::getId));
            SwingUtilities.invokeLater(() -> {
                for (Book book : loan.getListBooks()) {
                	tableModelBorrowed.addRow(new Object[]{
                            book.getId(),
                            book.getTitle(),
                            book.getIsbn(),
                            book.getPublisher(),
                            book.getYearPublication(),
                            book.getQuantity(),
                            String.join(",", book.getAuthor()),
                            String.join(",", book.getGenre())
                    });
                }
            });
        }
    }


    
}
