package Screens.Reports;

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
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import Screens.ConfigPanel.Styles;
import Publisher.Publisher;
import Publisher.PublisherDAO;

public class PublisherByCountTitles extends JPanel{
	private static final long serialVersionUID = -6757445173507348372L;
	private JLabel lblSubtitle, lblPublisher, lblQuantityMin;
	private JTextField txtPublisher, txtQuantityMin;
	private JButton search;
	private JTable table;
	private DefaultTableModel tableModel;
	private JScrollPane scrollPane;
	private PublisherDAO publisherDAO = new PublisherDAO();
	private List<Publisher> PublishersList = new ArrayList<>();
	
	public PublisherByCountTitles() {
		setLayout(null);
		lblSubtitle = new JLabel("Editoras com mais títulos");
		lblSubtitle.setBounds(140,0, 500, 35);
		Styles.styleSubtitleFont(lblSubtitle);
		add(lblSubtitle);
		
        lblPublisher = new JLabel("Editora:");
        lblPublisher.setBounds(140, 45, 80, 25);
        Styles.styleFont(lblPublisher);
        add(lblPublisher);
        
        txtPublisher = new JTextField();
        txtPublisher.setBounds(200, 45, 150, 25);
        add(txtPublisher);
        
        lblQuantityMin = new JLabel("Número mínimo de títulos:");
        lblQuantityMin.setBounds(400, 45, 250, 25);
        Styles.styleFont(lblQuantityMin);
        add(lblQuantityMin);
        
        txtQuantityMin = new JTextField();
        txtQuantityMin.setBounds(620, 45, 60, 25);
        txtQuantityMin.setText("1");
        add(txtQuantityMin);
        
        search = new JButton("Pesquisar");
        search.setBounds(932, 45, 140, 25);
        Styles.styleButton(search);
        add(search);
        
        search.addActionListener(e ->{
        	refreshPublisherTable();
        });
		
        tableModel = new DefaultTableModel() {
			private static final long serialVersionUID = -3751069658207964279L;

			@Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableModel.addColumn("ID");
        tableModel.addColumn("Nome");
        tableModel.addColumn("Quantidade de Titulos");
        
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
        refreshPublisherTable();

	}
	
	public void refreshPublisherTable() {
    	loadPublishersIntoTable();
    }

    public void loadPublishersIntoTable() {
    	if(!txtQuantityMin.getText().matches("\\d+") && !txtQuantityMin.getText().isBlank()) {
        	JOptionPane.showMessageDialog(null, "Por Favor! Digite apenas números inteiros para quantidade.");
        	txtQuantityMin.setText("");
        	txtQuantityMin.requestFocus();
        }
    	int qttMin = StringToInteger(txtQuantityMin.getText());
        tableModel.setRowCount(0);
        PublishersList.clear();
        List<Publisher> updatedPublishersList = publisherDAO.selectPublisherByCountTitles(txtPublisher.getText(), qttMin);
    	if (updatedPublishersList != null) {
            PublishersList.addAll(updatedPublishersList);
            if (!PublishersList.isEmpty()) {
                SwingUtilities.invokeLater(() -> {
                    for (Publisher Publisher : PublishersList) {
                        tableModel.addRow(new Object[]{
                                Publisher.getId(),
                                Publisher.getName(),
                                Publisher.getQtd_titles()
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
	 
	 public Integer StringToInteger(String number) {
		    int converted;
		    try {
		        converted = Integer.parseInt(number);
		    } catch (NumberFormatException e) {
		    	converted = 0;
		        
		    }
		    return converted;
		}
}
