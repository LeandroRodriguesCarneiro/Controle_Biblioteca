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

import Book.BookDAO;
import Genres.Genres;
import Genres.GenresDAO;
import Screens.ConfigPanel.Styles;


public class UpdateGenrePanel extends JPanel{
		private static final long serialVersionUID = 8876543210123456789L;
	    private JTextField txtGenre;
	    private JLabel lblBooks,lblGenre;
	    private JButton btnAdd;
	    private DefaultTableModel tableModel;
	    private JButton btnBack;
	    private CardLayout cardLayout;
	    private JPanel cardPanel;
	    private GenresPanel GenresPanel;
	    private Genres genre;

	    public UpdateGenrePanel(CardLayout cardLayout,
	        JPanel cardPanel, GenresPanel GenresPanel, Genres genre) {
	        
	    	lblBooks = new JLabel("Editar Gêneros");
	        lblBooks.setBounds(140, 10, 400, 30);
	        Styles.styleTitleFont(lblBooks);
	        add(lblBooks);
	        
	        this.cardLayout = cardLayout;
	        this.cardPanel = cardPanel;
	        this.GenresPanel = GenresPanel;
	        setLayout(null);

	        lblGenre = new JLabel("Genero:");
	        lblGenre.setBounds(140, 45, 80, 30);
	        Styles.styleFont(lblGenre);
	        add(lblGenre);

	        txtGenre = new JTextField();
	        txtGenre.setBounds(292, 45, 150, 30);
	        txtGenre.setText(genre.getName());
	        add(txtGenre);

	        btnAdd = new JButton("Salvar");
	        btnAdd.setBounds(140, 135, 150, 30);
	        Styles.styleButton(btnAdd);
	        btnAdd.addActionListener(new ActionListener() {
				@Override
	            public void actionPerformed(ActionEvent e) {
					String genreName = null;
				    if (txtGenre.getText().trim().isEmpty()) {
				        JOptionPane.showMessageDialog(null, "Por favor, preencha o nome do gênero.");
				        txtGenre.requestFocus();
				        return;
				    }
				    genreName = String.valueOf(txtGenre.getText());
					try {
						int dialogResult = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja salvar?", "Confirmação", JOptionPane.YES_NO_OPTION);
			    	    if (dialogResult == JOptionPane.YES_OPTION) {
			    	    	GenresDAO GenresDAO = new GenresDAO();
							GenresDAO.updateGenre(genre.getId(),genreName);
							GenresPanel.refreshGesnresTable();
			                cardLayout.show(cardPanel, "GenresPanel");
			    	    } else {
			    	        return;
			    	    }
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, "Este genero já existe. Por favor, insira um genero diferente.");
						txtGenre.setText("");
						txtGenre.requestFocus();
					}
					
					txtGenre.setText("");
					txtGenre.requestFocus();
	            }
	            
	        });
	         
	        add(btnAdd);

	        btnBack = new JButton("Voltar");
	        btnBack.setBounds(351, 135, 89, 25);
	        Styles.styleButton(btnBack);
	        btnBack.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	GenresPanel.refreshGesnresTable();
	                cardLayout.show(cardPanel, "GenresPanel");
	            }
	        });
	        add(btnBack);
	    }
}
