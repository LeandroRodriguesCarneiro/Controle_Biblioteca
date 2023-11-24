package Screens.CRUDBook;
//-*- coding: utf-8 -*-
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import Publisher.Publisher;
import Publisher.PublisherDAO;

import Screens.ConfigPanel.Styles;

public class AddPublisherPanel extends JPanel{
		private static final long serialVersionUID = 8876543210123456789L;
	    private JTextField txtPublisher;
	    private JLabel lblBooks, lblPublisher;
	    private JButton btnAdd;
	    private JButton btnBack;
	    private CardLayout cardLayout;
	    private JPanel cardPanel;
	    private PublisherPanel PublisherPanel;

	    public AddPublisherPanel(CardLayout cardLayout,
	        JPanel cardPanel, PublisherPanel PublisherPanel) {
	        this.cardLayout = cardLayout;
	        this.cardPanel = cardPanel;
	        this.PublisherPanel = PublisherPanel;
	        setLayout(null);
	        
	        lblBooks = new JLabel("Inserir Editora");
	        lblBooks.setBounds(140, 10, 400, 30);
	        Styles.styleTitleFont(lblBooks);
	        add(lblBooks);
	        
	        lblPublisher = new JLabel("Editora:");
	        lblPublisher.setBounds(140, 45, 80, 30);
	        Styles.styleFont(lblPublisher);
	        add(lblPublisher);
	        
	        txtPublisher = new JTextField();
	        txtPublisher.setBounds(292, 45, 150, 30);
	        add(txtPublisher);

	        btnAdd = new JButton("Adicionar Editora");
	        btnAdd.setBounds(140, 135, 150, 30);
	        Styles.styleButton(btnAdd);
	        btnAdd.addActionListener(new ActionListener() {
				@Override
	            public void actionPerformed(ActionEvent e) {
					String Publisher = null;
					
				    if (txtPublisher.getText().isEmpty()) {
				        JOptionPane.showMessageDialog(null, "Por favor, preencha o nome da Editora.");
				        txtPublisher.requestFocus();
				        return;
				    }
				    Publisher = String.valueOf(txtPublisher.getText());
					try {
						PublisherDAO PublisherDAO = new PublisherDAO();
						PublisherDAO.insertPublisher(Publisher);
						JOptionPane.showMessageDialog(null, "Editora adicionada com sucesso.");
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, "Este Editora já existe. Por favor, insira uma Editora diferente.");
						txtPublisher.setText("");
						txtPublisher.requestFocus();
						return;
					}
					txtPublisher.setText("");
	            }
	            
	        });
	         
	        add(btnAdd);

	        btnBack = new JButton("Voltar");
	        btnBack.setBounds(351, 135, 89, 25);
	        Styles.styleButton(btnBack);
	        btnBack.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	PublisherPanel.refreshPublisherTable();
	                cardLayout.show(cardPanel, "PublisherPanel");
	            }
	        });
	        add(btnBack);
	    }
}
