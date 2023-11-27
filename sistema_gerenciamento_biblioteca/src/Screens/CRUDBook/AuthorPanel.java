package Screens.CRUDBook;
//-*- coding: utf-8 -*-
import java.awt.CardLayout;
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

import Author.Author;
import Author.AuthorDAO;
import Screens.ConfigPanel.Styles;

public class AuthorPanel extends JPanel{
	private static final long serialVersionUID = -4843807817212241104L;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnEdit, btnDelete, search;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private AuthorDAO AuthorDAO = new AuthorDAO();
    private JButton backButton;
    private JLabel lblBooks,lblTitle;
    private JTextField txtAuthor;
    private List<Author> AuthorList = new ArrayList<>();
    private AuthorDAO authorDAO = new AuthorDAO();
    private BookPanel BookPanel;
    
    public AuthorPanel(CardLayout cardLayout, JPanel cardPanel, BookPanel BookPanel) {
    	this.cardPanel = cardPanel;
        this.cardLayout = cardLayout;
        this.BookPanel = BookPanel;
        setLayout(null);
        
        lblBooks = new JLabel("Autores");
        Styles.styleTitleFont(lblBooks);
        lblBooks.setBounds(142, 10, 150, 30);
        add(lblBooks);
        
        backButton = new JButton("Voltar");
        backButton.setBounds(992, 10, 80, 30);
        Styles.styleButton(backButton);
        backButton.addActionListener(e -> {
            	BookPanel.refreshBookTable();
                cardLayout.show(cardPanel, "BookPanel");
        });
        add(backButton);
        
        lblTitle = new JLabel("Autor:");
        Styles.styleFont(lblTitle);
        lblTitle.setBounds(142, 45, 80, 25);
        add(lblTitle);

        txtAuthor = new JTextField();
        txtAuthor.setBounds(210, 45, 500, 25);
        add(txtAuthor);

        search = new JButton("Pesquisar");
        search.setBounds(932, 45, 140, 25);
        Styles.styleButton(search);
        add(search);
        
        search.addActionListener(e ->{
        	refreshAuthorTable();
        });
        
        btnAdd = new JButton("Adicionar Autor");
        btnAdd.setBounds(140, 90, 150, 30);
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
        tableModel.addColumn("Autores");

        table = new JTable(tableModel);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setPreferredWidth(0);
        columnModel.getColumn(0).setWidth(0);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(140, 135, 930, 300);
        Styles.styleTable(table,scrollPane);
        add(scrollPane);
        
        btnEdit = new JButton("Editar Autor");
        btnEdit.setBounds(140, 445, 150, 30);
        Styles.styleButton(btnEdit);
        add(btnEdit);

        btnDelete = new JButton("Deletar Autor");
        btnDelete.setBounds(300, 445, 150, 30);
        Styles.styleButton(btnDelete);
        add(btnDelete);

        refreshAuthorTable();
        
        btnAdd.addActionListener(e -> {
            AddAuthorPanel addAuthorPanel = new AddAuthorPanel(cardLayout, cardPanel,
                    this);
            cardPanel.add(addAuthorPanel, "AddAuthorPanel");
            cardLayout.show(cardPanel, "AddAuthorPanel");
        });
        
        btnDelete.addActionListener(e -> {
        	int selectedRow = table.getSelectedRow();
    	    if (selectedRow != -1) {
    	        int AuthorID = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
	    	    int dialogResult = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir?", "Confirmação", JOptionPane.YES_NO_OPTION);
	    	    if (dialogResult == JOptionPane.YES_OPTION) {
	    	    	try {
	    	    		authorDAO.deleteAuthor(AuthorID);
			    	    refreshAuthorTable();	
	    	    	}catch(Exception ex){
	    	    		JOptionPane.showMessageDialog(null, ex.getMessage());
	    	    	}
	    	    	
	    	    } else {
	    	        return;
	    	    }
    	    } else {
    	        JOptionPane.showMessageDialog(null, "Por favor, selecione um Autor na tabela.");
    	        return;
    	    }
        });
        btnEdit.addActionListener(e ->{
        	int selectedRow = table.getSelectedRow();
    	    if (selectedRow != -1) {
    	    	Author author = new Author(
    	    			Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString()),
    	    	    	tableModel.getValueAt(selectedRow, 1).toString()
    	    		);
    	    	UpdateAuthorPanel updateAuthorPanel = new UpdateAuthorPanel(cardLayout, cardPanel, this, author);
    	    	cardPanel.add(updateAuthorPanel, "UpdateBookPanel");
    	    	cardLayout.show(cardPanel, "UpdateBookPanel");
    	    }else {
    	    	JOptionPane.showMessageDialog(null, "Por favor, selecione um Autor na tabela.");
    	        return;
    	    }
        });

    }    
    public void refreshAuthorTable() {
    	loadAuthorIntoTable();
    }

    public void loadAuthorIntoTable() {
        tableModel.setRowCount(0);
        AuthorList.clear();
        List<Author> updatedAuthorList = AuthorDAO.selectAuthorsByName(txtAuthor.getText().trim());
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
            }else {
            	JOptionPane.showMessageDialog(this, "Não foi encontrado dados para esse filtro.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados do Banco de dados.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
  }
}
