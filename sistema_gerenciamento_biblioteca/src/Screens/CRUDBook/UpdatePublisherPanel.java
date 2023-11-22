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

import Publisher.Publisher;
import Publisher.PublisherDAO;

public class UpdatePublisherPanel extends JPanel{
		private static final long serialVersionUID = 8876543210123456789L;
	    private JTextField txtname;
	    private JLabel lblBooks;
	    private JButton btnAdd;
	    private DefaultTableModel tableModel;
	    private JButton btnBack;
	    private CardLayout cardLayout;
	    private JPanel cardPanel;
	    private PublisherPanel PublisherPanel;
	    private Publisher Publisher;

	    public UpdatePublisherPanel(DefaultTableModel tableModel, CardLayout cardLayout,
	        JPanel cardPanel, PublisherPanel PublisherPanel, Publisher Publisher) {
	        
	    	lblBooks = new JLabel("Atualizar Editora");
	        lblBooks.setFont(new Font("Arial", Font.BOLD, 30));
	        lblBooks.setBounds(20, 10, 400, 30);
	        add(lblBooks);
	        
	        this.tableModel = tableModel;
	        this.cardLayout = cardLayout;
	        this.cardPanel = cardPanel;
	        this.PublisherPanel = PublisherPanel;
	        setLayout(null);

	        JLabel lblTtitle = new JLabel("Editora:");
	        lblTtitle.setBounds(10, 45, 80, 25);
	        add(lblTtitle);

	        txtname = new JTextField();
	        txtname.setBounds(120, 45, 500, 25);
	        txtname.setText(Publisher.getName());
	        add(txtname);

	        btnAdd = new JButton("Salvar");
	        btnAdd.setBounds(10, 500, 255, 25);
	        btnAdd.addActionListener(new ActionListener() {
				@Override
	            public void actionPerformed(ActionEvent e) {
					String name = null;
					
				    if (txtname.getText().isEmpty()) {
				        JOptionPane.showMessageDialog(null, "Por favor, preencha o nome do Editora.");
				        return;
				    }
				    name = String.valueOf(txtname.getText());
					
					try {
						PublisherDAO PublisherDAO = new PublisherDAO();
						PublisherDAO.updatePublisher(Publisher.getId(),name);
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, "Este Editora já existe. Por favor, insira um Editora diferente.");
					}
	            }
	            
	        });
	         
	        add(btnAdd);

	        btnBack = new JButton("Voltar");
	        btnBack.setBounds(351, 500, 89, 25);
	        btnBack.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	PublisherPanel.refreshGesnresTable();
	                cardLayout.show(cardPanel, "PublisherPanel");
	            }
	        });
	        add(btnBack);
	    }
}
