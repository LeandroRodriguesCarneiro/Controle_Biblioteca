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
    private JLabel lblClientes;
    private List<Book> booksList = new ArrayList<>();
    
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
        tableModel.addColumn("Titulo");
        tableModel.addColumn("ISBN");
        tableModel.addColumn("Editora");
        tableModel.addColumn("Ano de publicacao");
        tableModel.addColumn("Quantidade");
        tableModel.addColumn("Autores");
        tableModel.addColumn("Generos");

        loadBooksIntoTable();

        lblClientes = new JLabel("Livros");
        lblClientes.setFont(new Font("Arial", Font.BOLD, 30));
        lblClientes.setBounds(20, 10, 150, 30);
        add(lblClientes);

        table = new JTable(tableModel);
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
        
        btnAdd.addActionListener(e -> {
            AddBookPanel addBookPanel = new AddBookPanel(tableModel, cardLayout, cardPanel,
                    this);
            cardPanel.add(addBookPanel, "AddBookPanel");
            cardLayout.show(cardPanel, "AddBookPanel");
        });
    }
    public void refreshCustomerTable() {
    	loadBooksIntoTable();
    }

    public void loadBooksIntoTable() {
        tableModel.setRowCount(0); // Limpa a tabela
        booksList.clear();
        booksList = bookDao.selectAllBooks();
        if(booksList != null) {
        	booksList.sort(Comparator.comparingInt(Book::getId));
        	 SwingUtilities.invokeLater(() -> { // Atualizar a tabela na thread de despacho de eventos
        		 for (Book book: booksList) {
        			 tableModel.addRow(new Object[] {
        					 book.getTitle(),
        					 book.getIsbn(),
        					 book.getPublisher(),
        					 book.getYearPublication(),
        					 book.getQuantity(),
        					 String.join(",",book.getAuthor()),
        					 String.join(",",book.getGenre())
        			 });
        		 }
        		 
        	 });
        }else {
        	 JOptionPane.showMessageDialog(this, "Erro ao carregar dados do Banco de dados.", "Erro",
                     JOptionPane.ERROR_MESSAGE);
        	 }

    }
    
}
