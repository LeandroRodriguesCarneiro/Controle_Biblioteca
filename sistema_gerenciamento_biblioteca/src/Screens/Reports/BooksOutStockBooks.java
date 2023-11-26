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

public class BooksOutStockBooks extends JPanel{
	private static final long serialVersionUID = -6757445173507348372L;
	private JLabel lblSubtitle, lblGenre, lblPublisher, lblLowQuantity;
	private JTextField txtGenre, txtPublisher;
	private JCheckBox chkLowQuantity;
	private JButton search;
	private JTable table;
	private DefaultTableModel tableModel;
	private JScrollPane scrollPane;
	private BookDAO bookDAO = new BookDAO();
	private List<Book> booksList = new ArrayList<>();
	private boolean lowQuantity = false;
	
	public BooksOutStockBooks() {
		setLayout(null);
		lblSubtitle = new JLabel("Livros fora de estoque");
		lblSubtitle.setBounds(140,0, 300, 35);
		Styles.styleSubtitleFont(lblSubtitle);
		add(lblSubtitle);
		
		lblGenre = new JLabel("Gênero: ");
		lblGenre.setBounds(140, 45, 80, 25);
		Styles.styleFont(lblGenre);
		add(lblGenre);
		
		txtGenre  = new JTextField();
        txtGenre.setBounds(200, 45, 150, 25);
        add(txtGenre);
        
        lblPublisher = new JLabel("Editora:");
        lblPublisher.setBounds(400, 45, 80, 25);
        Styles.styleFont(lblPublisher);
        add(lblPublisher);
        
        txtPublisher = new JTextField();
        txtPublisher.setBounds(460, 45, 150, 25);
        add(txtPublisher);

        lblLowQuantity = new JLabel("Estoque Baixo?");
        lblLowQuantity.setBounds(675, 45, 100, 25);
        Styles.styleFont(lblLowQuantity);
        add(lblLowQuantity);
        
        chkLowQuantity = new JCheckBox();
        chkLowQuantity.setBounds(785, 38, 40, 40);
        add(chkLowQuantity);
        
        chkLowQuantity.addActionListener(e -> {
        	lowQuantity = !lowQuantity;
        });
        
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
        tableModel.addColumn("Editora");
        tableModel.addColumn("Quantidade");
        tableModel.addColumn("Gêneros");
        
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
        if(!txtPublisher.getText().matches("\\d+") && !txtPublisher.getText().isBlank()) {
        	JOptionPane.showMessageDialog(null, "Por Favor! Digite apenas números inteiros para quantidade.");
        	txtPublisher.setText("");
        	txtPublisher.requestFocus();
        }
        tableModel.setRowCount(0);
        booksList.clear();
        List<Book> updatedBooksList = bookDAO.selectBooksOutStockBooks(lowQuantity, txtPublisher.getText(), txtGenre.getText());
    	if (updatedBooksList != null) {
            booksList.addAll(updatedBooksList);
            if (!booksList.isEmpty()) {
                SwingUtilities.invokeLater(() -> {
                    for (Book book : booksList) {
                        tableModel.addRow(new Object[]{
                                book.getId(),
                                book.getTitle(),
                                book.getIsbn(),
                                book.getPublisher(),
                                book.getQuantity(),
                                String.join(",", book.getGenre())
                        });
                    }
                });
            } else {
            	if (!txtPublisher.getText().isEmpty()||!txtGenre.getText().isEmpty()) {
            		JOptionPane.showMessageDialog(this, "Não foi encontrado registros com esse filtro.", "Erro",
                            JOptionPane.ERROR_MESSAGE);
            	}
            }
        }
    }
	
	 public JPanel getPanel() {
	        return this;
	    }
}
