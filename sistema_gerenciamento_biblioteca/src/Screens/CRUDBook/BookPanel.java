package Screens.CRUDBook;

//-*- coding: utf-8 -*-

import java.awt.CardLayout;
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

import Book.BookDAO;
import Screens.ConfigPanel.Styles;
import Book.Book;

public class BookPanel extends JPanel{
	private static final long serialVersionUID = -4843807817212241104L;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnEdit, btnDelete, btnGenres, btnPublisher, btnAuthor, search;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private BookDAO bookDao = new BookDAO();
    private JButton backButton;
    private JCheckBox chkActive;
    private JTextField txtTitle, txtISBN, txtYearPublication, txtPublisher,txtGenre, txtAuthor;
    private JLabel lblBooks, lblTitle, lblISBN, lblPublisher, lblYearPublication, lblAuthor, lblgenres, lblActive;
    private boolean active = true;
    private List<Book> booksList = new ArrayList<>();
    
    public BookPanel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardPanel = cardPanel;
        this.cardLayout = cardLayout;
        setLayout(null);
        
        lblBooks = new JLabel("Livros");
        Styles.styleTitleFont(lblBooks);
        lblBooks.setBounds(142, 10, 150, 30);
        add(lblBooks);
        
        lblActive = new JLabel("Ativo?");
        lblActive.setBounds(840, 135, 150,30);
        Styles.styleFont(lblActive);
        add(lblActive);
        
        chkActive = new JCheckBox();
        chkActive.setBounds(900,135,90,30);
        chkActive.setSelected(this.active);
        add(chkActive);
        
        chkActive.addActionListener(e ->{
        	this.active = !this.active;
        });
        
        backButton = new JButton("Voltar");
        backButton.setBounds(992, 10, 80, 30);
        Styles.styleButton(backButton);
        add(backButton);
        
        lblTitle = new JLabel("Título:");
        Styles.styleFont(lblTitle);
        lblTitle.setBounds(142, 45, 80, 25);
        add(lblTitle);

        txtTitle = new JTextField();
        txtTitle.setBounds(192, 45, 500, 25);
        add(txtTitle);

        lblISBN = new JLabel("ISBN:");
        Styles.styleFont(lblISBN);
        lblISBN.setBounds(702, 45, 80, 25);
        add(lblISBN);

        txtISBN = new JTextField();
        txtISBN.setBounds(752, 45, 90, 25);
        add(txtISBN);

        lblPublisher = new JLabel("Editora:");
        Styles.styleFont(lblPublisher);
        lblPublisher.setBounds(862, 45, 80, 25);
        add(lblPublisher);

        txtPublisher = new JTextField();
        txtPublisher.setBounds(922, 45, 150, 25);
        add(txtPublisher);

        lblYearPublication = new JLabel("Ano de Publicação:");
        Styles.styleFont(lblYearPublication);
        lblYearPublication.setBounds(142, 90, 150, 25);
        add(lblYearPublication);

        txtYearPublication = new JTextField();
        txtYearPublication.setBounds(272, 90, 35, 25);
        add(txtYearPublication);

        lblgenres = new JLabel("Gênero:");
        Styles.styleFont(lblgenres);
        lblgenres.setBounds(360, 90, 80, 25);
        add(lblgenres);

        txtGenre  = new JTextField();
        txtGenre.setBounds(420, 90, 150, 25);
        add(txtGenre);

        lblAuthor = new JLabel("Autores:");
        Styles.styleFont(lblAuthor);
        lblAuthor.setBounds(630, 90, 80, 25);
        add(lblAuthor);

        txtAuthor = new JTextField();
        txtAuthor.setBounds(700, 90, 150, 25);
        add(txtAuthor);

        search = new JButton("Pesquisar");
        search.setBounds(932, 90, 140, 25);
        Styles.styleButton(search);
        add(search);
        
        search.addActionListener(e ->{
        	loadBooksIntoTable();
        });
        
        btnAdd = new JButton("Adicionar Livro");
        btnAdd.setBounds(140, 135, 137, 30);
        Styles.styleButton(btnAdd);
        add(btnAdd);
        
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
        tableModel.addColumn("Active");

        table = new JTable(tableModel);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setPreferredWidth(0);
        columnModel.getColumn(0).setWidth(0);
        columnModel.getColumn(8).setMaxWidth(0);
        columnModel.getColumn(8).setMinWidth(0);
        columnModel.getColumn(8).setPreferredWidth(0);
        columnModel.getColumn(8).setWidth(0);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(140, 180, 930, 300);
        Styles.styleTable(table,scrollPane);
        add(scrollPane);
        
        btnEdit = new JButton("Editar Livro");
        btnEdit.setBounds(140, 490, 150, 30);
        Styles.styleButton(btnEdit);
        add(btnEdit);

        btnDelete = new JButton("Deletar Livro");
        btnDelete.setBounds(300, 490, 150, 30);
        Styles.styleButton(btnDelete);
        add(btnDelete);

        btnGenres = new JButton("Gêneros");
        btnGenres.setBounds(460, 490, 150, 30);
        Styles.styleButton(btnGenres);
        add(btnGenres);
        
        btnPublisher = new JButton("Editoras");
        btnPublisher.setBounds(620, 490, 150, 30);
        Styles.styleButton(btnPublisher);
        add(btnPublisher);
        
        btnAuthor = new JButton("Autores");
        btnAuthor.setBounds(780, 490, 150, 30);
        Styles.styleButton(btnAuthor);
        add(btnAuthor);
        
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "MainPanel"));
        refreshBookTable();
        
        btnAdd.addActionListener(e -> {
            AddBookPanel addBookPanel = new AddBookPanel(tableModel, cardLayout, cardPanel,
                    this);
            cardPanel.add(addBookPanel, "AddBookPanel");
            cardLayout.show(cardPanel, "AddBookPanel");
        });
        
        btnDelete.addActionListener(e -> {
        	int selectedRow = table.getSelectedRow();
    	    if (selectedRow != -1) {
    	        int BookID = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
	    	    int dialogResult = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir?", "Confirmação", JOptionPane.YES_NO_OPTION);
	    	    if (dialogResult == JOptionPane.YES_OPTION) {
	    	    	try {
	    	    		BookDAO bookDAO = new BookDAO();
			    	    bookDAO.deleteBook(BookID);
			    	    refreshBookTable();
	    	    	}catch(Exception ex) {
	    	    		JOptionPane.showMessageDialog(null, ex.getMessage());
	    	    	}
	    	    	
	    	    } else {
	    	        return;
	    	    }
    	    } else {
    	        JOptionPane.showMessageDialog(null, "Por favor, selecione um livro na tabela.");
    	        return;
    	    }
        });
        btnEdit.addActionListener(e ->{
        	int selectedRow = table.getSelectedRow();
    	    if (selectedRow != -1) {
    	    	Book book = new Book(
    	    			Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString()),
    	    	    	tableModel.getValueAt(selectedRow, 1).toString(),
    	    	    	tableModel.getValueAt(selectedRow, 2).toString(),
    	    	    	Integer.parseInt(tableModel.getValueAt(selectedRow, 4).toString()),
    	    	    	Integer.parseInt(tableModel.getValueAt(selectedRow, 5).toString()),
    	    	    	tableModel.getValueAt(selectedRow, 3).toString(),
    	    	    	tableModel.getValueAt(selectedRow, 6).toString(),
    	    	    	tableModel.getValueAt(selectedRow, 7).toString(),
    	    	    	Boolean.valueOf(tableModel.getValueAt(selectedRow, 8).toString())
    	    		);
    	    	UpdateBookPanel updateBookPanel = new UpdateBookPanel(cardLayout, cardPanel, this, book);
    	    	cardPanel.add(updateBookPanel, "UpdateBookPanel");
    	    	cardLayout.show(cardPanel, "UpdateBookPanel");
    	    }else {
    	    	JOptionPane.showMessageDialog(null, "Por favor, selecione um livro na tabela.");
    	    }
        });
        
        btnGenres.addActionListener(e ->{
        	GenresPanel GenresPanel = new GenresPanel(cardLayout, cardPanel, this);
        	GenresPanel.setBackground(Styles.BACKGROUND_COLOR);
        	cardPanel.add(GenresPanel, "GenresPanel");
            cardLayout.show(cardPanel, "GenresPanel");
        });
        
        btnPublisher.addActionListener(e ->{
        	PublisherPanel PublisherPanel = new PublisherPanel(cardLayout, cardPanel, this);
        	PublisherPanel.setBackground(Styles.BACKGROUND_COLOR);
        	cardPanel.add(PublisherPanel, "PublisherPanel");
            cardLayout.show(cardPanel, "PublisherPanel");
        });
        
        btnAuthor.addActionListener(e ->{
        	AuthorPanel AuthorPanel = new AuthorPanel(cardLayout, cardPanel, this);
        	AuthorPanel.setBackground(Styles.BACKGROUND_COLOR);
        	cardPanel.add(AuthorPanel, "AuthorPanel");
            cardLayout.show(cardPanel, "AuthorPanel");
        });
    }
    
    public void refreshBookTable() {
    	loadBooksIntoTable();
    }

    public void loadBooksIntoTable() {
        long ISBN;
        Integer year = 0;
        try {
        	if (txtISBN.getText().trim().length() != 13 && !txtISBN.getText().isEmpty()) {
        		JOptionPane.showMessageDialog(this, "O ISBN precisa conter 13 digitos", "Erro",
                        JOptionPane.ERROR_MESSAGE);
        		txtISBN.setText("");
        		txtISBN.requestFocus();
        		return;
        	}
        	
        	ISBN = Long.valueOf(txtISBN.getText());
        	
        }catch(NumberFormatException e) {
        	if(txtISBN.getText().trim().length() > 0) {
        		JOptionPane.showMessageDialog(this, "O ISBN precisa ser um número", "Erro",
                        JOptionPane.ERROR_MESSAGE);
        		txtISBN.setText("");
        		txtISBN.requestFocus();
            	return;
        	}else {
        		ISBN = 0;
        	}
        }
        
        try {
        	if(txtYearPublication.getText().trim().length() != 4 && !txtYearPublication.getText().isEmpty()) {
        		JOptionPane.showMessageDialog(this, "O ano precisa conter 4 digitos", "Erro",
                        JOptionPane.ERROR_MESSAGE);
        		txtYearPublication.setText("");
        		txtYearPublication.requestFocus();
        		return;
        	}
        	if(!txtYearPublication.getText().trim().isEmpty()) {
        		year = Integer.valueOf(txtYearPublication.getText());
        	}
        	
        }catch(NumberFormatException e){
        	if(txtYearPublication.getText().length()>0) {
        		JOptionPane.showMessageDialog(this, "O ano precisa ser um número", "Erro",
                        JOptionPane.ERROR_MESSAGE);
        		txtYearPublication.setText("");
        		txtYearPublication.requestFocus();
        		return;
        	}
        }
        
        tableModel.setRowCount(0);
        booksList.clear();
        List<Book> updatedBooksList = bookDao.selectBooksByfilter(txtTitle.getText().trim(),ISBN, txtPublisher.getText().trim(), 
        		year, txtGenre.getText().trim(), txtAuthor.getText().trim(), this.active);
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
                                book.getPublisher(),
                                book.getYearPublication(),
                                book.getQuantity(),
                                String.join(",", book.getAuthor()),
                                String.join(",", book.getGenre()),
                                book.isActive()
                        });
                    }
                });
            } else {
                JOptionPane.showMessageDialog(this, "Não foi encontrado dados para esse filtro.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }else {
        	JOptionPane.showMessageDialog(this, "Erro ao carregar dados do Banco de dados.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
