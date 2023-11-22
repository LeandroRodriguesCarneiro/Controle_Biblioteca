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

import Publisher.Publisher;
import Publisher.PublisherDAO;

public class PublisherPanel extends JPanel{
	private static final long serialVersionUID = -4843807817212241104L;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnEdit, btnDelete;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private PublisherDAO PublisherDAO = new PublisherDAO();
    private JButton backButton;
    private JLabel lblBooks;
    private List<Publisher> PublisherList = new ArrayList<>();
    private boolean loadPublisherList = false;
    
    public PublisherPanel(CardLayout cardLayout, JPanel cardPanel, BookPanel BookPanel) {
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
        loadPublisherIntoTable();
        
        btnAdd.addActionListener(e -> {
        	loadPublisherList = false;
            AddPublisherPanel AddPublisherPanel = new AddPublisherPanel(tableModel, cardLayout, cardPanel, this); 
            cardPanel.add(AddPublisherPanel, "AddPublisherPanel");
            cardLayout.show(cardPanel, "AddPublisherPanel");
        });
        
        btnDelete.addActionListener(e -> {
        	int selectedRow = table.getSelectedRow();
    	    if (selectedRow != -1) {
    	        int PublisherID = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
	    	    int dialogResult = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir?", "Confirmação", JOptionPane.YES_NO_OPTION);
	    	    if (dialogResult == JOptionPane.YES_OPTION) {
		    	    PublisherDAO.deletePublisher(PublisherID);
		    	    loadPublisherList = false;
		    	    loadPublisherIntoTable();
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
    	    	Publisher publisher= new Publisher(
    	    			Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString()),
    	    	    	tableModel.getValueAt(selectedRow, 1).toString()
    	    		);
    	    	loadPublisherList = false;
    	    	UpdatePublisherPanel UpdatePublisherPanel = new UpdatePublisherPanel(tableModel, cardLayout, cardPanel, this, publisher);
    	    	cardPanel.add(UpdatePublisherPanel, "UpdatePublisherPanel");
    	    	cardLayout.show(cardPanel, "UpdatePublisherPanel");
    	    }
        });
    }
    
    public void refreshGesnresTable() {
    	loadPublisherIntoTable();
    }

    public void loadPublisherIntoTable() {
        tableModel.setRowCount(0);
        PublisherList.clear();
        List<Publisher> updatedPublisherList = PublisherDAO.selectAllPublisher();
        if(loadPublisherList == false) {
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
                    loadPublisherList = true;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao carregar dados do Banco de dados.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    
}
