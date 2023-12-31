package Screens.CRUDBook;
//-*- coding: utf-8 -*-
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Publisher.PublisherDAO;
import Publisher.Publisher;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import Screens.ConfigPanel.Styles;

public class UpdatePublisherPanel extends JPanel{
		private static final long serialVersionUID = 8876543210123456789L;
	    private JTextField txtPublisher;
	    private JLabel lblBooks, lblPublisher, lblActive;
	    private JButton btnAdd;
	    private JCheckBox chkActive;
	    private JButton btnBack;
	    private CardLayout cardLayout;
	    private JPanel cardPanel;
	    private PublisherPanel PublisherPanel;
	    private Publisher publisher;
	    private boolean active;

	    public UpdatePublisherPanel(CardLayout cardLayout,
	        JPanel cardPanel, PublisherPanel PublisherPanel, Publisher publisher) {
	        this.cardLayout = cardLayout;
	        this.cardPanel = cardPanel;
	        this.PublisherPanel = PublisherPanel;
	        this.publisher = publisher;
	        this.active = publisher.isActive();
	        setLayout(null);
	        
	        lblBooks = new JLabel("Editar Editora");
	        lblBooks.setBounds(140, 10, 400, 30);
	        Styles.styleTitleFont(lblBooks);
	        add(lblBooks);
	        
	        lblPublisher = new JLabel("Editora:");
	        lblPublisher.setBounds(140, 45, 80, 30);
	        Styles.styleFont(lblPublisher);
	        add(lblPublisher);
	        
	        txtPublisher = new JTextField();
	        txtPublisher.setBounds(292, 45, 150, 30);
	        txtPublisher.setText(publisher.getName());
	        add(txtPublisher);
	        
	        lblActive = new JLabel("Ativo?");
	        lblActive.setBounds(140,90, 150,30);
	        Styles.styleFont(lblActive);
	        add(lblActive);
	        
	        chkActive = new JCheckBox();
	        chkActive.setBounds(292,90,90,30);
	        chkActive.setSelected(this.active);
	        add(chkActive);
	        
	        chkActive.addActionListener(e ->{
	        	this.active = !this.active;
	        });
	        
	        btnAdd = new JButton("Salvar");
	        btnAdd.setBounds(140, 135, 150, 30);
	        Styles.styleButton(btnAdd);
	        btnAdd.addActionListener(new ActionListener() {
				@Override
	            public void actionPerformed(ActionEvent e) {
					String Publisher = null;
					
				    if (txtPublisher.getText().trim().isEmpty()) {
				        JOptionPane.showMessageDialog(null, "Por favor, preencha o nome da Editora.");
				        txtPublisher.requestFocus();
				        return;
				    }
				    Publisher = String.valueOf(txtPublisher.getText());
					try {
						int dialogResult = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja salvar?", "Confirmação", JOptionPane.YES_NO_OPTION);
			    	    if (dialogResult == JOptionPane.YES_OPTION) {
			    	    	PublisherDAO PublisherDAO = new PublisherDAO();
							PublisherDAO.updatePublisher(publisher.getId(), Publisher, active);
							PublisherPanel.refreshPublisherTable();
			                cardLayout.show(cardPanel, "PublisherPanel");
			    	    } else {
			    	        return;
			    	    }
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, ex.getMessage());
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
