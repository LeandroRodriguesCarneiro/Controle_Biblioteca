package Screens.Functionalities;
//-*- coding: utf-8 -*-
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
import Screens.ConfigPanel.Styles;

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
        
        lblTitlePage = new JLabel("Devolução");
        lblTitlePage.setBounds(142, 10, 250, 30);
        Styles.styleTitleFont(lblTitlePage);
        add(lblTitlePage);
        
        backButton = new JButton("Voltar");
        backButton.setBounds(992, 10, 80, 30);
        Styles.styleButton(backButton);
        add(backButton);
        
        lblchkReNew = new JLabel("Renovar?");
        lblchkReNew.setBounds(675, 100, 80, 25);
        Styles.styleFont(lblchkReNew);
        add(lblchkReNew);
        
        chkReNew = new JCheckBox();
        chkReNew.setBounds(740, 92, 40, 40);
        add(chkReNew);
        
        chkReNew.addActionListener(e -> {
        	lblDays.setVisible(!componentReNew);
            txtDays.setVisible(!componentReNew);
            btnReNew.setVisible(!componentReNew);
            componentReNew = !componentReNew;
        });
        
        lblDays = new JLabel("Dias de empréstimo (1 - 15): ");
        lblDays.setBounds(142, 140, 200, 25);
        Styles.styleFont (lblDays);
        add(lblDays);
        
        txtDays = new JTextField();
        txtDays.setBounds(340, 140, 40, 25);
        add(txtDays);
        
        lblNumberRegistration = new JLabel("Insira o número de matrícula:");
        lblNumberRegistration.setBounds(142, 60, 200, 25);
        Styles.styleFont (lblNumberRegistration);
        add(lblNumberRegistration);
        
        txtNumberRegistration = new JTextField();
        txtNumberRegistration.setBounds(340, 60, 90, 25);
        add(txtNumberRegistration);
        
        btnNumberRegistration = new JButton("Buscar Aluno");
        btnNumberRegistration.setBounds(460, 60, 125, 25);
        Styles.styleButtonMenu(btnNumberRegistration);
        add(btnNumberRegistration);
        
        btnNumberRegistration.addActionListener(e -> {
        	Long NumberRegistration = null;
        	try {
        		if(txtNumberRegistration.getText().trim().isEmpty() || txtNumberRegistration.getText().trim().length() != 10) {
            		JOptionPane.showMessageDialog(null, "Por favor!, digite um numero de matricula com 10 digitos");
            		txtNumberRegistration.setText("");
            		return;
            	}
        		NumberRegistration = Long.parseLong(txtNumberRegistration.getText());
        	}catch(NumberFormatException ex) {
        		JOptionPane.showMessageDialog(null, "Por favor!, digite um número com 10 digitos");
        		txtNumberRegistration.setText("");
        		return;
        	}
        	
        	StudentDAO studentDAO = new StudentDAO();
        	List<Student> studentsList = studentDAO.selectStudentByNumerRegistration(NumberRegistration, true);
        	if(studentsList.isEmpty()) {
        		JOptionPane.showMessageDialog(null, "Número de matrícula não encontrado");
        		txtNumberRegistration.setText("");
        		return;
        	}
        	student = studentsList.get(0);
        	List<Loan> loansList = loanDAO.selectLoanBooks(student.getId());
        	if(loansList.isEmpty()) {
        		JOptionPane.showMessageDialog(null, "Este aluno não possui empréstimos");
        		txtNumberRegistration.setText("");
        		return;
        	}
        	loan = loanDAO.selectLoanBooks(student.getId()).get(0);
        	lblStudent.setText("Aluno: "+student.getName()+", Número de matrícula: "+student.getNumberRegistration());
        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        	lblStatus.setText("Data de Empréstimo: "+loan.getDateInit().format(formatter)+", Data de devolução: "+loan.getDateEnd().format(formatter));
        	if(LocalDate.now().isBefore(loan.getDateEnd()) || loan.getDateEnd().isEqual(LocalDate.now())){
                lblchkReNew.setVisible(true);
            	chkReNew.setVisible(true);
        	}
        	setVisilble();
        	txtNumberRegistration.setText("");
        	refreshBooksBorrowedIntoTable();
        });
        
        lblISBN = new JLabel("Insira o ISBN do Livro: ");
        lblISBN.setBounds(142, 100, 200, 25);
        Styles.styleFont (lblISBN);
        add(lblISBN);
        
        txtISBN = new JTextField();
        txtISBN.setBounds(340, 100, 90, 25);
        add(txtISBN);
        
        btnAdd = new JButton("Buscar Livro");
        btnAdd.setBounds(460, 100, 125, 25);
        Styles.styleButton(btnAdd);
        add(btnAdd);
        
        btnAdd.addActionListener(e -> {
        	if (txtISBN.getText().trim().isEmpty()) {
		        JOptionPane.showMessageDialog(null, "Por favor, preencha o ISBN.");
		        return;
		    } 
		    if (txtISBN.getText().trim().length() != 13 || !txtISBN.getText().trim().matches("\\d+")) {
		        JOptionPane.showMessageDialog(null, "Por favor, o ISBN precisa ter 13 dígitos.");
		        return;
		    }
		    bookDAO = new BookDAO();
		    List<Book> booksSelectedList = bookDAO.selectBooksByISBN(txtISBN.getText(), true);
		    if (booksSelectedList.isEmpty()) {
		    	JOptionPane.showMessageDialog(null, "ISBN não encontrado");
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
        lblStudent.setBounds(460, 140, 500, 25);
        Styles.styleFont(lblStudent);
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
        scrollPane.setBounds(140, 180, 930, 150);
        Styles.styleTable(table, scrollPane);
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
        tableModelBorrowed.addColumn("Ano de publicação");
        tableModelBorrowed.addColumn("Quantidade");
        tableModelBorrowed.addColumn("Autores");
        tableModelBorrowed.addColumn("Gêneros");
                
        lblBorrowedTable = new JLabel("Livros Emprestados:");
        lblBorrowedTable.setBounds(140, 400, 200, 25);
        Styles.styleFont(lblBorrowedTable);
        add(lblBorrowedTable);
        
        lblStatus = new JLabel();
        lblStatus.setBounds(140, 375, 500, 25);
        Styles.styleFont(lblStatus);
        add(lblStatus);
        
        tableBorrowed = new JTable(tableModelBorrowed);
        TableColumnModel columnModelBorroed = tableBorrowed.getColumnModel();
        columnModelBorroed.getColumn(0).setMaxWidth(0);
        columnModelBorroed.getColumn(0).setMinWidth(0);
        columnModelBorroed.getColumn(0).setPreferredWidth(0);
        columnModelBorroed.getColumn(0).setWidth(0);
        scrollPaneBorrowed = new JScrollPane(tableBorrowed);
        Styles.styleTable(tableBorrowed, scrollPaneBorrowed);
        scrollPaneBorrowed.setBounds(140, 445, 930, 135);
        
        add(scrollPaneBorrowed);
        
        btnLoan = new JButton("Devolver");
        btnLoan.setBounds(140, 340, 125, 25);
        Styles.styleButton(btnLoan);
        add(btnLoan);
        btnLoan.addActionListener(e ->{
    		if(booksList.isEmpty()) {
    			JOptionPane.showMessageDialog(null, "Por favor!, insira os livros que deseja devolver");
    			return;
    		}
        	try {
        		loanDAO.returBook(loan, booksList, LocalDate.now());
            	setInvisible();
            	JOptionPane.showMessageDialog(null, "Devolução realizada com sucesso!");
        	}catch(Exception ex) {
        		JOptionPane.showMessageDialog(null, "Houve um problema");
        		return;
        	}
        	
        });
        
        btnRemove = new JButton("Remover");
        btnRemove.setBounds(300, 340, 125, 25);
        Styles.styleButton(btnRemove);
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
        btnView.setBounds(460, 340, 125, 25);
        Styles.styleButton(btnView);
        add(btnView);
        btnView.addActionListener(e ->{
            lblStatus.setVisible(!componentsVisible);
            scrollPaneBorrowed.setVisible(!componentsVisible);
            lblBorrowedTable.setVisible(!componentsVisible);
            componentsVisible = !componentsVisible;
        });
        
        btnReNew = new JButton("Renovar");
        btnReNew.setBounds(620, 340, 125, 25);
        Styles.styleButton(btnReNew);
        add(btnReNew);
        btnReNew.addActionListener(e ->{
        	LocalDate endDate = null;
        	try {
        		int days = -1;
        		 
    			if(txtDays.getText().trim().isEmpty()) {
        			JOptionPane.showMessageDialog(null, "Por favor!, digite o número de dias que deseja emprestar os livros");
        			return;
        		}
        		
        		days = Integer.parseInt(txtDays.getText());
        		if (days >= 1 && days <= 15) {
		            endDate = LocalDate.now().plusDays(days);
		        } else {
		        	JOptionPane.showMessageDialog(null, "O prazo do empréstimo deve estar entre 1 e 15 dias.");
		        	return;
		        }
        	}catch(NumberFormatException ex) {
        		JOptionPane.showMessageDialog(null, "Por favor!, digite um número válido");
        		return;
        	}
        	try {
        		loanDAO.reNewLoan(student.getId(), LocalDate.now(), endDate);
            	setInvisible();
            	JOptionPane.showMessageDialog(null, "Renovação realizada com sucesso!");
        	}catch(Exception ex) {
        		JOptionPane.showMessageDialog(null, "Houve um problema");
        		return;
        	}
        	
        });
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
