package Screens.CRUDBook;
//-*- coding: utf-8 -*-
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
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import Publisher.Publisher;
import Publisher.PublisherDAO;
import Publisher.Publisher;
import Publisher.PublisherDAO;
import Screens.ConfigPanel.Styles;

public class PublisherPanel extends JPanel{
	private static final long serialVersionUID = -4843807817212241104L;
	private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnEdit, btnDelete, search;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private PublisherDAO PublisherDAO = new PublisherDAO();
    private JButton backButton;
    private JLabel lblBooks,lblTitle;
    private List<Publisher> PublisherList = new ArrayList<>();
    private JTextField txtPublisher;
    private BookPanel BookPanel;
    
    public PublisherPanel(CardLayout cardLayout, JPanel cardPanel, BookPanel BookPanel) {
    	this.cardPanel = cardPanel;
        this.cardLayout = cardLayout;
        this.BookPanel = BookPanel;
        setLayout(null);
        
        lblBooks = new JLabel("Editoras");
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
        
        lblTitle = new JLabel("Editora:");
        Styles.styleFont(lblTitle);
        lblTitle.setBounds(142, 45, 80, 25);
        add(lblTitle);

        txtPublisher = new JTextField();
        txtPublisher.setBounds(210, 45, 500, 25);
        add(txtPublisher);

        search = new JButton("Pesquisar");
        search.setBounds(932, 45, 140, 25);
        Styles.styleButton(search);
        add(search);
        
        search.addActionListener(e -> {
        	refreshPublisherTable();
        	});
        
        btnAdd = new JButton("Adicionar Editora");
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
        tableModel.addColumn("Editoras");

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
        
        btnEdit = new JButton("Editar Editora");
        btnEdit.setBounds(140, 445, 150, 30);
        Styles.styleButton(btnEdit);
        add(btnEdit);

        btnDelete = new JButton("Deletar Editora");
        btnDelete.setBounds(300, 445, 150, 30);
        Styles.styleButton(btnDelete);
        add(btnDelete);

        refreshPublisherTable();
        
        btnAdd.addActionListener(e -> {
                AddPublisherPanel addPublisherPanel = new AddPublisherPanel(cardLayout, cardPanel,
                        this);
                cardPanel.add(addPublisherPanel, "AddPublisherPanel");
                cardLayout.show(cardPanel, "AddPublisherPanel");
        });
        
        btnDelete.addActionListener(e -> {
        	int selectedRow = table.getSelectedRow();
    	    if (selectedRow != -1) {
    	        int PublisherID = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
	    	    int dialogResult = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir?", "Confirmação", JOptionPane.YES_NO_OPTION);
	    	    if (dialogResult == JOptionPane.YES_OPTION) {
	    	    	try {
	    	    		PublisherDAO.deletePublisher(PublisherID);
			    	    refreshPublisherTable();
	    	    	}catch(Exception ex) {
	    	    		JOptionPane.showMessageDialog(null, ex.getMessage());
	    	    	}
	    	    	
	    	    } else {
	    	        return;
	    	    }
    	    } else {
    	        JOptionPane.showMessageDialog(null, "Por favor, selecione um Editora na tabela.");
    	        return;
    	    }
        });
        btnEdit.addActionListener(e ->{
        	int selectedRow = table.getSelectedRow();
    	    if (selectedRow != -1) {
    	    	Publisher Publisher = new Publisher(
    	    			Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString()),
    	    	    	tableModel.getValueAt(selectedRow, 1).toString()
    	    		);
    	    	UpdatePublisherPanel updatePublisherPanel = new UpdatePublisherPanel(cardLayout, cardPanel, this, Publisher);
    	    	cardPanel.add(updatePublisherPanel, "UpdateBookPanel");
    	    	cardLayout.show(cardPanel, "UpdateBookPanel");
    	    }else {
    	    	JOptionPane.showMessageDialog(null, "Por favor, selecione um Editora na tabela.");
    	        return;
    	    }
        });
    }
    
    public void refreshPublisherTable() {
    	loadPublisherIntoTable();
    }

    public void loadPublisherIntoTable() {
        tableModel.setRowCount(0);
        PublisherList.clear();
        List<Publisher> updatedPublisherList = PublisherDAO.selectPublisherByName(txtPublisher.getText().trim());
    	if (updatedPublisherList != null) {
    		updatedPublisherList.sort(Comparator.comparingInt(Publisher::getId));
            PublisherList.addAll(updatedPublisherList);
            if (!updatedPublisherList.isEmpty()) {
                SwingUtilities.invokeLater(() -> {
                    for (Publisher Publisher : PublisherList) {
                        tableModel.addRow(new Object[]{
                                Publisher.getId(),
                                Publisher.getName()
                        });
                    }
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados do Banco de dados.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        }
    }