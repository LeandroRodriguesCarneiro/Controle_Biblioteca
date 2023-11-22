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

import Author.Author;
import Author.AuthorDAO;

public class AddAuthorPanel extends JPanel{
		private static final long serialVersionUID = 8876543210123456789L;
	    private JTextField txtAuthor;
	    private JLabel lblBooks;
	    private JButton btnAdd;
	    private DefaultTableModel tableModel;
	    private JButton btnBack;
	    private CardLayout cardLayout;
	    private JPanel cardPanel;
	    private AuthorPanel AuthorPanel;

	    public AddAuthorPanel(DefaultTableModel tableModel, CardLayout cardLayout,
	        JPanel cardPanel, AuthorPanel AuthorPanel) {
	        
	    	lblBooks = new JLabel("Inserir Autores");
	        lblBooks.setFont(new Font("Arial", Font.BOLD, 30));
	        lblBooks.setBounds(20, 10, 400, 30);
	        add(lblBooks);
	        
	        this.tableModel = tableModel;
	        this.cardLayout = cardLayout;
	        this.cardPanel = cardPanel;
	        this.AuthorPanel = AuthorPanel;
	        setLayout(null);

	        JLabel lblTtitle = new JLabel("Editora:");
	        lblTtitle.setBounds(10, 45, 80, 25);
	        add(lblTtitle);

	        txtAuthor = new JTextField();
	        txtAuthor.setBounds(120, 45, 500, 25);
	        add(txtAuthor);

	        btnAdd = new JButton("Adicionar Editora");
	        btnAdd.setBounds(10, 500, 255, 25);
	        btnAdd.addActionListener(new ActionListener() {
				@Override
	            public void actionPerformed(ActionEvent e) {
					String Author = null;
					
				    if (txtAuthor.getText().isEmpty()) {
				        JOptionPane.showMessageDialog(null, "Por favor, preencha o nome do Editora.");
				        return;
				    }
				    Author = String.valueOf(txtAuthor.getText());
					
					try {
						AuthorDAO AuthorDAO = new AuthorDAO();
						AuthorDAO.insertAuthor(Author);
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, "Este Editora já existe. Por favor, insira um Editora diferente.");
					}
					txtAuthor.setText("");
	            }
	            
	        });
	         
	        add(btnAdd);

	        btnBack = new JButton("Voltar");
	        btnBack.setBounds(351, 500, 89, 25);
	        btnBack.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	AuthorPanel.refreshGesnresTable();
	                cardLayout.show(cardPanel, "AuthorPanel");
	            }
	        });
	        add(btnBack);
	    }
}
