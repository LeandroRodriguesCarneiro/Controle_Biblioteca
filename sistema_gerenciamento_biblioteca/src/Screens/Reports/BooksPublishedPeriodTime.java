package Screens.Reports;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//-*- coding: utf-8 -*-

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.MaskFormatter;

import Screens.ConfigPanel.Styles;
import Book.Book;
import Book.BookDAO;

public class BooksPublishedPeriodTime extends JPanel{
	private static final long serialVersionUID = -6757445173507348372L;
	private JLabel lblSubtitle, lblDateInit, lblDateEnd, lblTitle, lblGenre, lblPublisher;
	private JTextField txtTitle, txtGenre, txtPublisher;
	private JFormattedTextField txtDateInit, txtDateEnd;
	private JButton search;
	private JTable table;
	private DefaultTableModel tableModel;
	private JScrollPane scrollPane;
	private BookDAO bookDAO = new BookDAO();
	private List<Book> booksList = new ArrayList<>();
	
	public BooksPublishedPeriodTime() {
		setLayout(null);
		lblSubtitle = new JLabel("Livros publicados em num intervalo de tempo");
		lblSubtitle.setBounds(140,0, 600, 35);
		Styles.styleSubtitleFont(lblSubtitle);
		add(lblSubtitle);
		
		lblTitle = new JLabel("Título:");
        Styles.styleFont(lblTitle);
        lblTitle.setBounds(142, 45, 80, 25);
        add(lblTitle);

        txtTitle = new JTextField();
        txtTitle.setBounds(230, 45, 500, 25);
        add(txtTitle);
        
        lblGenre = new JLabel("Gênero: ");
		lblGenre.setBounds(820, 45, 80, 25);
		Styles.styleFont(lblGenre);
		add(lblGenre);
		
		txtGenre  = new JTextField();
        txtGenre.setBounds(920, 45, 150, 25);
        add(txtGenre);
        
        lblPublisher = new JLabel("Editora:");
        lblPublisher.setBounds(140, 90, 80, 25);
        Styles.styleFont(lblPublisher);
        add(lblPublisher);
        
        txtPublisher = new JTextField();
        txtPublisher.setBounds(230, 90, 150, 25);
        add(txtPublisher);
        
		try {
			MaskFormatter dateFormatter = new MaskFormatter("##/##/####");
			
			lblDateInit = new JLabel("Data início: ");
			lblDateInit.setBounds(410, 90, 80, 25);
			Styles.styleFont(lblDateInit);
			add(lblDateInit);
			
			txtDateInit  = new JFormattedTextField(dateFormatter);
	        txtDateInit.setBounds(530, 90, 75, 25);
	        add(txtDateInit);
	        
	        lblDateEnd = new JLabel("Data final:");
	        lblDateEnd.setBounds(660, 90, 80, 25);
	        Styles.styleFont(lblDateEnd);
	        add(lblDateEnd);
	        
	        txtDateEnd = new JFormattedTextField(dateFormatter);
	        txtDateEnd.setBounds(780, 90, 75, 25);
	        add(txtDateEnd);
		
		} catch (ParseException e) {
            e.printStackTrace();
        }
		
		setDateField(LocalDate.of(2011, 01, 01), txtDateInit);
		setDateField(LocalDate.now(), txtDateEnd);
		
        search = new JButton("Pesquisar");
        search.setBounds(932, 90, 140, 25);
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
        tableModel.addColumn("Ano de publicação");
        tableModel.addColumn("Gênero");
        
        table = new JTable(tableModel);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setPreferredWidth(0);
        columnModel.getColumn(0).setWidth(0);
        
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(140, 135, 930, 450);
        Styles.styleTable(table,scrollPane);
        add(scrollPane);
        refreshBookTable();

	}
	
	public void refreshBookTable() {
    	loadBooksIntoTable();
    }

    public void loadBooksIntoTable() {
        tableModel.setRowCount(0);
        booksList.clear();
        List<Book> updatedBooksList;
        try {
        	updatedBooksList = bookDAO.selectBooksPublishedPeriodTime(getDateField(txtDateInit), getDateField(txtDateEnd),txtTitle.getText(), txtGenre.getText(), txtPublisher.getText());
        }catch(Exception e) {
        	JOptionPane.showMessageDialog(this, e.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        	return;
        }
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
                                book.getYearPublication(),
                                String.join(",", book.getGenre())
                        });
                    }
                });
            } else {
            	JOptionPane.showMessageDialog(this, "Não foi encontrado registros com esse filtro.", "Erro",
                       JOptionPane.ERROR_MESSAGE);
            }
        }
    }
	
	 public JPanel getPanel() {
	        return this;
	    }
	 
	 private void setDateField(LocalDate date, JFormattedTextField dateField) {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	        String formattedDate = date.format(formatter);
	        dateField.setText(formattedDate);
	    }
	 
	 private LocalDate getDateField(JFormattedTextField dateField) throws Exception {
	        String text = dateField.getText();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	        try {
	            return LocalDate.parse(text, formatter);
	        } catch (Exception e) {
	        	dateField.requestFocus();
	        	throw new Exception("Por favor! Digite uma data valida!");
	        }
	    }
}
