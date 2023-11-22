package Screens.CRUDBook;

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

import Genres.Genres;
import Genres.GenresDAO;


public class UpdateGenrePanel extends JPanel{
		private static final long serialVersionUID = 8876543210123456789L;
	    private JTextField txtname;
	    private JLabel lblBooks;
	    private JButton btnAdd;
	    private DefaultTableModel tableModel;
	    private JButton btnBack;
	    private CardLayout cardLayout;
	    private JPanel cardPanel;
	    private GenresPanel GenresPanel;
	    private Genres genre;

	    public UpdateGenrePanel(DefaultTableModel tableModel, CardLayout cardLayout,
	        JPanel cardPanel, GenresPanel GenresPanel, Genres genre) {
	        
	    	lblBooks = new JLabel("Atualizar Genero");
	        lblBooks.setFont(new Font("Arial", Font.BOLD, 30));
	        lblBooks.setBounds(20, 10, 400, 30);
	        add(lblBooks);
	        
	        this.tableModel = tableModel;
	        this.cardLayout = cardLayout;
	        this.cardPanel = cardPanel;
	        this.GenresPanel = GenresPanel;
	        setLayout(null);

	        JLabel lblTtitle = new JLabel("Genero:");
	        lblTtitle.setBounds(10, 45, 80, 25);
	        add(lblTtitle);

	        txtname = new JTextField();
	        txtname.setBounds(120, 45, 500, 25);
	        txtname.setText(genre.getName());
	        add(txtname);

	        btnAdd = new JButton("Salvar");
	        btnAdd.setBounds(10, 500, 255, 25);
	        btnAdd.addActionListener(new ActionListener() {
				@Override
	            public void actionPerformed(ActionEvent e) {
					String name = null;
					
				    if (txtname.getText().isEmpty()) {
				        JOptionPane.showMessageDialog(null, "Por favor, preencha o nome do genero.");
				        return;
				    }
				    name = String.valueOf(txtname.getText());
					
					try {
						GenresDAO GenresDAO = new GenresDAO();
						GenresDAO.updateGenre(genre.getId(),name);
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, "Este genero já existe. Por favor, insira um genero diferente.");
					}
					
	            }
	            
	        });
	         
	        add(btnAdd);

	        btnBack = new JButton("Voltar");
	        btnBack.setBounds(351, 500, 89, 25);
	        btnBack.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	GenresPanel.refreshGesnresTable();
	                cardLayout.show(cardPanel, "GenresPanel");
	            }
	        });
	        add(btnBack);
	    }
}
