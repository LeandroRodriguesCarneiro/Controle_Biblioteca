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

import Author.Author;
import Author.AuthorDAO;

public class AuthorPanel extends JPanel{
	private static final long serialVersionUID = -4843807817212241104L;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnEdit, btnDelete;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private AuthorDAO AuthorDAO = new AuthorDAO();
    private JButton backButton;
    private JLabel lblBooks;
    private List<Author> AuthorList = new ArrayList<>();
    private boolean loadAuthorList = false;
    
    public AuthorPanel(CardLayout cardLayout, JPanel cardPanel, BookPanel BookPanel) {
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
        tableModel.addColumn("Nome");

        lblBooks = new JLabel("Editoras");
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
        
        btnAdd = new JButton("Adicionar Editora");
        btnAdd.setBounds(180, 10, 150, 30);
        add(btnAdd);

        btnEdit = new JButton("Editar Editora");
        btnEdit.setBounds(20, 360, 150, 30);
        add(btnEdit);

        btnDelete = new JButton("Deletar Editora");
        btnDelete.setBounds(180, 360, 150, 30);
        add(btnDelete);

        backButton = new JButton("Voltar");
        backButton.setBounds(870, 10, 80, 30);
        add(backButton);

        backButton.addActionListener(e -> cardLayout.show(cardPanel, "BookPanel"));
        loadAuthorIntoTable();
        
        btnAdd.addActionListener(e -> {
        	loadAuthorList = false;
            AddAuthorPanel AddAuthorPanel = new AddAuthorPanel(tableModel, cardLayout, cardPanel, this); 
            cardPanel.add(AddAuthorPanel, "AddAuthorPanel");
            cardLayout.show(cardPanel, "AddAuthorPanel");
        });
        
        btnDelete.addActionListener(e -> {
        	int selectedRow = table.getSelectedRow();
    	    if (selectedRow != -1) {
    	        int AuthorID = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
	    	    int dialogResult = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir?", "Confirmação", JOptionPane.YES_NO_OPTION);
	    	    if (dialogResult == JOptionPane.YES_OPTION) {
		    	    AuthorDAO.deleteAuthor(AuthorID);
		    	    loadAuthorList = false;
		    	    loadAuthorIntoTable();
	    	    } else {
	    	        return;
	    	    }
    	    } else {
    	        JOptionPane.showMessageDialog(null, "Por favor, selecione um Editora na tabela.");
    	    }
        });
        
        btnEdit.addActionListener(e ->{
        	int selectedRow = table.getSelectedRow();
    	    if (selectedRow != -1) {
    	    	Author Author= new Author(
    	    			Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString()),
    	    	    	tableModel.getValueAt(selectedRow, 1).toString()
    	    		);
    	    	loadAuthorList = false;
    	    	UpdateAuthorPanel UpdateAuthorPanel = new UpdateAuthorPanel(tableModel, cardLayout, cardPanel, this, Author);
    	    	cardPanel.add(UpdateAuthorPanel, "UpdateAuthorPanel");
    	    	cardLayout.show(cardPanel, "UpdateAuthorPanel");
    	    }
        });
    }
    
    public void refreshGesnresTable() {
    	loadAuthorIntoTable();
    }

    public void loadAuthorIntoTable() {
        tableModel.setRowCount(0);
        AuthorList.clear();
        List<Author> updatedAuthorList = AuthorDAO.selectAllAuthors();
        if(loadAuthorList == false) {
        	if (updatedAuthorList != null) {
        		updatedAuthorList.sort(Comparator.comparingInt(Author::getId));
                AuthorList.addAll(updatedAuthorList);
                if (!updatedAuthorList.isEmpty()) {
                    SwingUtilities.invokeLater(() -> {
                        for (Author Author : AuthorList) {
                            tableModel.addRow(new Object[]{
                                    Author.getId(),
                                    Author.getName()
                            });
                        }
                    });
                    loadAuthorList = true;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao carregar dados do Banco de dados.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    
}
