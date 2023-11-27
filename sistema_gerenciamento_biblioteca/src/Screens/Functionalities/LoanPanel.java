package Screens.Functionalities;
//-*- coding: utf-8 -*-
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;
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
import Loan.LoanDAO;
import Screens.ConfigPanel.Styles;

public class LoanPanel extends JPanel{
	private JTable table;
    private DefaultTableModel tableModel;
    private static final long serialVersionUID = -4843807817212241104L;
    private JTextField txtNumberRegistration, txtISBN, txtDays;
    private JButton btnAdd, btnRemove,btnNumberRegistration, backButton, btnLoan;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private BookDAO bookDAO = new BookDAO();
    private JLabel lblLoan, lblNumberRegistration, lblISBN, lblStudent, lblDays;
    private Student student;
    private List<Book> booksList = new ArrayList<>();
    private LoanDAO loanDAO = new LoanDAO();
    private JScrollPane scrollPane;
    public LoanPanel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardPanel = cardPanel;

        setLayout(null);
        
        lblLoan = new JLabel("Empréstimos");
        lblLoan.setBounds(142, 10, 250, 30);
        Styles.styleTitleFont(lblLoan);
        add(lblLoan);
        
        backButton = new JButton("Voltar");
        backButton.setBounds(992, 10, 80, 30);
        Styles.styleButton(backButton);
        add(backButton);
        
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
            		JOptionPane.showMessageDialog(null, "Por favor!, digite um número de matrícula com 10 digitos");
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
        	List<Student> studentsList = studentDAO.selectStudentByNumerRegistration(NumberRegistration);
        	if(studentsList.isEmpty()) {
        		JOptionPane.showMessageDialog(null, "Número de matrícula não encontrado");
        		txtNumberRegistration.setText("");
        		return;
        	}
        	student = studentsList.get(0);
        	
        	if(student.getDebits()<0) {
        		JOptionPane.showMessageDialog(null, "O aluno não pode emprestar livros até quitar a multa");
        		txtNumberRegistration.setText("");
        		return;
        	}
        	
        	if(student.getBorrowedBooks() > 0) {
        		JOptionPane.showMessageDialog(null, "O aluno não pode emprestar livros ate devolver os livros emprestados");
        		txtNumberRegistration.setText("");
        		return;
        	}
        	
        	lblStudent.setText("Aluno: "+student.getName()+", Número de matrícula: "+student.getNumberRegistration());
        	setVisible();
        	Styles.styleFont(lblStudent);
        	txtNumberRegistration.setText("");
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
            List<Book> booksSelectedList = bookDAO.selectBooksByISBN(txtISBN.getText());
            if (booksSelectedList.isEmpty()) {
                JOptionPane.showMessageDialog(null, "ISBN não encontrado");
                return;
            }
            
            Book selectedBook = booksSelectedList.get(0);
            for (Book book : booksList) {
                if (book.getId() == selectedBook.getId()) {
                    JOptionPane.showMessageDialog(null, "Este livro já foi adicionado.");
                    return;
                }
            }
            
            if (selectedBook.getQuantity() <= 0) {
                JOptionPane.showMessageDialog(null, "Todos os exemplares deste livro estão emprestados");
                return;
            }

            if (booksList.size() < 3) {
                booksList.add(selectedBook);
                refreshBookTable();
                txtISBN.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Você só pode emprestar até 3 livros.");
            }
        });

        
        lblStudent = new JLabel();
        lblStudent.setBounds(460, 140, 500, 25);
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
        tableModel.addColumn("Título");
        tableModel.addColumn("ISBN");
        tableModel.addColumn("Editora");
        tableModel.addColumn("Ano de publicação");
        tableModel.addColumn("Quantidade");
        tableModel.addColumn("Autores");
        tableModel.addColumn("Gêneros");

        table = new JTable(tableModel);
        Styles.styleTable(table, scrollPane);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setPreferredWidth(0);
        columnModel.getColumn(0).setWidth(0);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(140, 180, 930, 380);
        add(scrollPane);
        
        btnLoan = new JButton("Emprestar");
        btnLoan.setBounds(140, 580, 125, 25);
        Styles.styleButton(btnLoan);
        add(btnLoan);
        btnLoan.addActionListener(e ->{
        	LocalDate endDate = null;
        	try {
        		int days = -1;
        		if(booksList.isEmpty()) {
        			JOptionPane.showMessageDialog(null, "Por favor!, insira os livros que deseja emprestar");
        			return;
        		}
        		if(booksList.size() == 1) {
        			if(txtDays.getText().trim().isEmpty()) {
            			JOptionPane.showMessageDialog(null, "Por favor!, digite o número de dias que deseja emprestar o livro");
            			return;
            		}
        		}else {
        			if(txtDays.getText().trim().isEmpty()) {
            			JOptionPane.showMessageDialog(null, "Por favor!, digite o número de dias que deseja emprestar os livros");
            			return;
            		}
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
        		loanDAO.insertLoan(student.getId(), LocalDate.now(), endDate, booksList);
            	setInvisible();
            	JOptionPane.showMessageDialog(null, "Empréstimo realizado com sucesso!");
        	}catch(Exception ex) {
        		JOptionPane.showMessageDialog(null, "Houve um problema");
        		return;
        	}
        	
        });
        
        btnRemove = new JButton("Remover");
        btnRemove.setBounds(150, 305, 125, 25);
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
        setInvisible();
        
        
    }
    public void setInvisible() {
    	student = null;
    	booksList.clear();
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
    }
    
    public void setVisible() {
        lblISBN.setVisible(true);
        txtISBN.setVisible(true);
        btnAdd.setVisible(true);
        lblStudent.setVisible(true);
        lblStudent.setVisible(true);
        scrollPane.setVisible(true);
        btnLoan.setVisible(true);
        btnRemove.setVisible(true);
        lblDays.setVisible(true);
        txtDays.setVisible(true);
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


    
}
