package Screens.CRUDBook;
//-*- coding: utf-8 -*-
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Author.Author;
import Author.AuthorDAO;
import Screens.ConfigPanel.Styles;

public class AddAuthorPanel extends JPanel{
		private static final long serialVersionUID = 8876543210123456789L;
	    private JTextField txtAuthor;
	    private JLabel lblBooks, lblAuthor;
	    private JButton btnAdd;
	    private JButton btnBack;
	    private CardLayout cardLayout;
	    private JPanel cardPanel;
	    private AuthorPanel AuthorPanel;

	    public AddAuthorPanel(CardLayout cardLayout,
	        JPanel cardPanel, AuthorPanel AuthorPanel) {
	        this.cardLayout = cardLayout;
	        this.cardPanel = cardPanel;
	        this.AuthorPanel = AuthorPanel;
	        setLayout(null);
	        
	        lblBooks = new JLabel("Inserir Autor");
	        lblBooks.setBounds(140, 10, 400, 30);
	        Styles.styleTitleFont(lblBooks);
	        add(lblBooks);
	        
	        lblAuthor = new JLabel("Autor:");
	        lblAuthor.setBounds(140, 45, 80, 30);
	        Styles.styleFont(lblAuthor);
	        add(lblAuthor);
	        
	        txtAuthor = new JTextField();
	        txtAuthor.setBounds(292, 45, 150, 30);
	        add(txtAuthor);

	        btnAdd = new JButton("Adicionar Autor");
	        btnAdd.setBounds(140, 135, 150, 30);
	        Styles.styleButton(btnAdd);
	        btnAdd.addActionListener(new ActionListener() {
				@Override
	            public void actionPerformed(ActionEvent e) {
					String Author = null;
					
				    if (txtAuthor.getText().isEmpty()) {
				        JOptionPane.showMessageDialog(null, "Por favor, preencha o nome do Autor.");
				        txtAuthor.requestFocus();
				        return;
				    }
				    Author = String.valueOf(txtAuthor.getText());
					try {
						AuthorDAO AuthorDAO = new AuthorDAO();
						AuthorDAO.insertAuthor(Author);
						JOptionPane.showMessageDialog(null, "Autor adicionado com sucesso.");
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
