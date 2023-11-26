package Screens.Reports;

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

import Screens.ConfigPanel.Styles;
import Book.Book;
import Book.BookDAO;

public class BooksSpecificAuthor extends JPanel{
	private static final long serialVersionUID = 1L;
	private JLabel lblSubtitle, lblAuthor, lblTitle;
	private JTextField txtAuthor, txtTitle;
	private JButton search;
	private JTable table;
	private DefaultTableModel tableModel;
	private JScrollPane scrollPane;
	private BookDAO bookDAO = new BookDAO();
	private List<Book> booksList = new ArrayList<>();
	
	public BooksSpecificAuthor() {
		setLayout(null);
		lblSubtitle = new JLabel("Livros de um autor específico");
		lblSubtitle.setBounds(140,0, 400, 35);
		Styles.styleSubtitleFont(lblSubtitle);
		add(lblSubtitle);
		
		lblTitle = new JLabel("Título:");
        Styles.styleFont(lblTitle);
        lblTitle.setBounds(140, 45, 80, 25);
        add(lblTitle);

        txtTitle = new JTextField();
        txtTitle.setBounds(200, 45, 500, 25);
        add(txtTitle);
        
        lblAuthor = new JLabel("Autor: ");
		lblAuthor.setBounds(710, 45, 80, 25);
		Styles.styleFont(lblAuthor);
		add(lblAuthor);
		
		txtAuthor  = new JTextField();
        txtAuthor.setBounds(770, 45, 150, 25);
        add(txtAuthor);
        
        search = new JButton("Pesquisar");
        search.setBounds(932, 45, 140, 25);
        Styles.styleButton(search);
        add(search);
        
        search.addActionListener(e ->{
        	refreshBookTable();
        });
        
        tableModel = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableModel.addColumn("ID");
        tableModel.addColumn("Título");
        tableModel.addColumn("ISBN");
        tableModel.addColumn("Autores");
        
        table = new JTable(tableModel);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setPreferredWidth(0);
        columnModel.getColumn(0).setWidth(0);
        columnModel.getColumn(1).setPreferredWidth(100);
	    columnModel.getColumn(2).setPreferredWidth(30);
	    columnModel.getColumn(3).setPreferredWidth(380); 
        
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
        tableModel.setRowCount(0);
        booksList.clear();
        List<Book> updatedBooksList = bookDAO.selectBooksSpecificAuthor(txtAuthor.getText(), txtTitle.getText());
    	if (updatedBooksList != null) {
            updatedBooksList.sort(Comparator.comparingInt(Book::getId));
            booksList.addAll(updatedBooksList);
            if (!booksList.isEmpty()) {
                SwingUtilities.invokeLater(() -> {
                    for (Book book : booksList) {
                        tableModel.addRow(new Object[]{
                                book.getId(),
                                book.getTitle(),
                                book.getIsbn(),
                                String.join(",", book.getAuthor())
                        });
                    }
                });
            } else {
                JOptionPane.showMessageDialog(this, "Não foi encontrado registros com esse filtro.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
                txtAuthor.requestFocus();
            }
        }
    }
	
	 public JPanel getPanel() {
	        return this;
	    }

}
