package Screens.CRUDBook;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import Book.BookDAO;
import Book.Book;

public class BookPanel extends JPanel{
	private static final long serialVersionUID = -4843807817212241104L;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnEdit, btnDelete;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private BookDAO bookDao = new BookDAO();
    private JButton backButton;
    private JLabel lblBooks;
    private List<Book> booksList = new ArrayList<>();
    private boolean loadBookList = false;
    
    public BookPanel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardPanel = cardPanel;

        setLayout(null);

        tableModel = new DefaultTableModel() {
            /**
             * 
             */
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

        lblBooks = new JLabel("Livros");
        lblBooks.setFont(new Font("Arial", Font.BOLD, 30));
        lblBooks.setBounds(20, 10, 150, 30);
        add(lblBooks);

        table = new JTable(tableModel);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setPreferredWidth(0);
        columnModel.getColumn(0).setWidth(0);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 50, 930, 300);
        add(scrollPane);
        
        btnAdd = new JButton("Adicionar Livro");
        btnAdd.setBounds(180, 10, 150, 30);
        add(btnAdd);

        btnEdit = new JButton("Editar Livro");
        btnEdit.setBounds(20, 360, 150, 30);
        add(btnEdit);

        btnDelete = new JButton("Deletar Livro");
        btnDelete.setBounds(180, 360, 150, 30);
        add(btnDelete);

        backButton = new JButton("Voltar");
        backButton.setBounds(870, 10, 80, 30);
        add(backButton);

        backButton.addActionListener(e -> cardLayout.show(cardPanel, "MainPanel"));
        refreshBookTable();
        btnAdd.addActionListener(e -> {
        	loadBookList = false;
            AddBookPanel addBookPanel = new AddBookPanel(tableModel, cardLayout, cardPanel,
                    this);
            cardPanel.add(addBookPanel, "AddBookPanel");
            cardLayout.show(cardPanel, "AddBookPanel");
        });
        
        btnDelete.addActionListener(e -> {
//            DeleteBookPanel deleteBookPanel = new DeleteBookPanel(tableModel, cardLayout,
//                    cardPanel, this);
//            cardPanel.add(deleteBookPanel, "DeleteBookPanel");
//            cardLayout.show(cardPanel, "DeleteBookPanel");
        	int selectedRow = table.getSelectedRow();
    	    if (selectedRow != -1) {
    	        int BookID = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
	    	    int dialogResult = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir?", "Confirmação", JOptionPane.YES_NO_OPTION);
	    	    if (dialogResult == JOptionPane.YES_OPTION) {
	    	    	BookDAO bookDAO = new BookDAO();
		    	    bookDAO.deleteBook(BookID);
		    	    loadBookList = false;
		    	    refreshBookTable();
	    	    } else {
	    	        return;
	    	    }
    	    } else {
    	        JOptionPane.showMessageDialog(null, "Por favor, selecione um livro na tabela.");
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
    	    	    	tableModel.getValueAt(selectedRow, 7).toString()
    	    		);
    	    	loadBookList = false;
    	    	UpdateBookPanel updateBookPanel = new UpdateBookPanel(tableModel, cardLayout, cardPanel, this, book);
    	    	cardPanel.add(updateBookPanel, "UpdateBookPanel");
    	    	cardLayout.show(cardPanel, "UpdateBookPanel");
    	    }
        });
    }
    
    public void refreshBookTable() {
    	loadBooksIntoTable();
    }

    public void loadBooksIntoTable() {
        tableModel.setRowCount(0);
        booksList.clear();
        List<Book> updatedBooksList = bookDao.selectAllBooks();
        if(loadBookList == false) {
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
                                    String.join(",", book.getGenre())
                            });
                        }
                    });
                    loadBookList = true;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao carregar dados do Banco de dados.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    
}
