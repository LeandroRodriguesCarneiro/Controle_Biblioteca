package Screens.CRUDBook;
//-*- coding: utf-8 -*-
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import Author.Author;
import Author.AuthorDAO;
import Screens.ConfigPanel.Styles;

public class UpdateAuthorPanel extends JPanel{
		private static final long serialVersionUID = 8876543210123456789L;
	    private JTextField txtAuthor;
	    private JLabel lblBooks, lblAuthor, lblActive;
	    private JCheckBox chkActive;
	    private JButton btnAdd;
	    private JButton btnBack;
	    private CardLayout cardLayout;
	    private JPanel cardPanel;
	    private AuthorPanel AuthorPanel;
	    private Author author;
	    private boolean active;

	    public UpdateAuthorPanel(CardLayout cardLayout,
	        JPanel cardPanel, AuthorPanel AuthorPanel, Author author) {
	        this.cardLayout = cardLayout;
	        this.cardPanel = cardPanel;
	        this.AuthorPanel = AuthorPanel;
	        this.author = author;
	        this.active = author.isActive();
	        setLayout(null);
	        
	        lblBooks = new JLabel("Atualizar Autor");
	        lblBooks.setBounds(140, 10, 400, 30);
	        Styles.styleTitleFont(lblBooks);
	        add(lblBooks);
	        
	        lblAuthor = new JLabel("Autor:");
	        lblAuthor.setBounds(140, 45, 80, 30);
	        Styles.styleFont(lblAuthor);
	        add(lblAuthor);
	        
	        txtAuthor = new JTextField();
	        txtAuthor.setBounds(292, 45, 150, 30);
	        txtAuthor.setText(author.getName());
	        add(txtAuthor);

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
					String Author = null;
					
				    if (txtAuthor.getText().trim().isEmpty()) {
				        JOptionPane.showMessageDialog(null, "Por favor, preencha o nome do Autor.");
				        txtAuthor.requestFocus();
				        return;
				    }
				    Author = String.valueOf(txtAuthor.getText());
					try {
						int dialogResult = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja salvar?", "Confirmação", JOptionPane.YES_NO_OPTION);
			    	    if (dialogResult == JOptionPane.YES_OPTION) {
			    	    	AuthorDAO AuthorDAO = new AuthorDAO();
							AuthorDAO.updateAuthor(author.getId(), Author, active);
							AuthorPanel.refreshAuthorTable();
			                cardLayout.show(cardPanel, "AuthorPanel");
			    	    } else {
			    	        return;
			    	    }
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, "Este Autor já existe. Por favor, insira um Autor diferente.");
						txtAuthor.setText("");
						txtAuthor.requestFocus();
						return;
					}
					txtAuthor.setText("");
	            }
	            
	        });
	         
	        add(btnAdd);

	        btnBack = new JButton("Voltar");
	        btnBack.setBounds(351, 135, 89, 25);
	        Styles.styleButton(btnBack);
	        btnBack.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	AuthorPanel.refreshAuthorTable();
	                cardLayout.show(cardPanel, "AuthorPanel");
	            }
	        });
	        add(btnBack);
	    }
}
