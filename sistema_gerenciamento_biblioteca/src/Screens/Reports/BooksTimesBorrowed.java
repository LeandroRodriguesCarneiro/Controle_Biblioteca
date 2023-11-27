package Screens.Reports;

//-*- coding: utf-8 -*-

import java.util.ArrayList;
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

import Screens.ConfigPanel.Styles;
import Book.Book;
import Book.BookDAO;

public class BooksTimesBorrowed extends JPanel{
	private static final long serialVersionUID = -6757445173507348372L;
	private JLabel lblSubtitle, lblGenre, lblminTimesBorrowed;
	private JTextField txtGenre, txtminTimesBorrowed;
	private JButton search;
	private JTable table;
	private DefaultTableModel tableModel;
	private JScrollPane scrollPane;
	private BookDAO bookDAO = new BookDAO();
	private List<Book> booksList = new ArrayList<>();
	
	public BooksTimesBorrowed() {
		setLayout(null);
		lblSubtitle = new JLabel("Livros mais retirados");
		lblSubtitle.setBounds(140,0, 300, 35);
		Styles.styleSubtitleFont(lblSubtitle);
		add(lblSubtitle);
		
		lblGenre = new JLabel("Gênero: ");
		lblGenre.setBounds(140, 45, 80, 30);
		Styles.styleFont(lblGenre);
		add(lblGenre);
		
		txtGenre  = new JTextField();
        txtGenre.setBounds(200, 45, 150, 25);
        add(txtGenre);
        
        lblminTimesBorrowed = new JLabel("Número mínimo de emprestimos:");
        lblminTimesBorrowed.setBounds(400, 45, 250, 25);
        Styles.styleFont(lblminTimesBorrowed);
        add(lblminTimesBorrowed);
        
        txtminTimesBorrowed = new JTextField();
        txtminTimesBorrowed.setBounds(620, 45, 60, 25);
        txtminTimesBorrowed.setText("1");
        add(txtminTimesBorrowed);
        
        search = new JButton("Pesquisar");
        search.setBounds(932, 45, 140, 25);
        Styles.styleButton(search);
        add(search);
        
        search.addActionListener(e ->{
        	refreshBookTable();
        });
		
        tableModel = new DefaultTableModel() {
			private static final long serialVersionUID = -3751069658207964279L;

			@Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableModel.addColumn("ID");
        tableModel.addColumn("Título");
        tableModel.addColumn("ISBN");
        tableModel.addColumn("Gêneros");
        tableModel.addColumn("Vezes emprestado");
        
        table = new JTable(tableModel);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setPreferredWidth(0);
        columnModel.getColumn(0).setWidth(0);
        
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(140, 90, 930, 500);
        Styles.styleTable(table,scrollPane);
        add(scrollPane);
        refreshBookTable();

	}
	
	public void refreshBookTable() {
    	loadBooksIntoTable();
    }

    public void loadBooksIntoTable() {
        if(!txtminTimesBorrowed.getText().matches("\\d+") && !txtminTimesBorrowed.getText().isBlank()) {
        	JOptionPane.showMessageDialog(null, "Por Favor! Digite apenas números inteiros para quantidade.");
        	txtminTimesBorrowed.setText("");
        	txtminTimesBorrowed.requestFocus();
        }
        tableModel.setRowCount(0);
        booksList.clear();
        int TimesBorrowed = StringToInteger(txtminTimesBorrowed.getText());
        List<Book> updatedBooksList = bookDAO.selectBooksTimesBorrowed(txtGenre.getText(), TimesBorrowed);
    	if (updatedBooksList != null) {
            booksList.addAll(updatedBooksList);
            if (!booksList.isEmpty()) {
                SwingUtilities.invokeLater(() -> {
                    for (Book book : booksList) {
                        tableModel.addRow(new Object[]{
                                book.getId(),
                                book.getTitle(),
                                book.getIsbn(),
                                String.join(",", book.getGenre()),
                                book.getTimesBorrowed()
                                
                        });
                    }
                });
            } else {
                JOptionPane.showMessageDialog(this, "Não foi encontrado registros com esse filtro.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
                txtGenre.requestFocus();
            }
        }
    }
	
	 public JPanel getPanel() {
	        return this;
	    }
	 public Integer StringToInteger(String number) {
		    int converted;
		    try {
		        converted = Integer.parseInt(number);
		    } catch (NumberFormatException e) {
		    	converted = 0;
		        
		    }
		    return converted;
		}
}
