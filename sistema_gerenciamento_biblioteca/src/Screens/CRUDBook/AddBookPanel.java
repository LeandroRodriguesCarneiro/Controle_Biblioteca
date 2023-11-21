package Screens.CRUDBook;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.table.DefaultTableModel;

import Genres.Genres;
import Genres.GenresDAO;
import Author.Author;
import Author.AuthorDAO;
import Publisher.Publisher;
import Publisher.PublisherDAO;

public class AddBookPanel extends JPanel{
	 private static final long serialVersionUID = -1723482129844832445L;
	    private JTextField txtTitle, txtISBN, txtYearPublication, txtQuantity;
	    private JComboBox cbbGenre, cbbPublisher, cbbAuthor;
	    private JButton btnAdd;
	    private DefaultTableModel tableModel;
	    private JButton btnBack;
	    private CardLayout cardLayout;
	    private JPanel cardPanel;
	    private BookPanel BookPanel;

	    public AddBookPanel(DefaultTableModel tableModel, CardLayout cardLayout,
	            JPanel cardPanel, BookPanel BookPanel) {
	        
	        this.tableModel = tableModel;
	        this.cardLayout = cardLayout;
	        this.cardPanel = cardPanel;
	        this.BookPanel = BookPanel;
	        setLayout(null);

	        JLabel lblTtitle = new JLabel("Titulo:");
	        lblTtitle.setBounds(10, 10, 80, 25);
	        add(lblTtitle);

	        txtTitle = new JTextField();
	        txtTitle.setBounds(120, 10, 500, 25);
	        add(txtTitle);

	        JLabel lblISBN = new JLabel("ISBN:");
	        lblISBN.setBounds(10, 45, 80, 25);
	        add(lblISBN);

	        txtISBN = new JTextField();
	        txtISBN.setBounds(530, 45, 90, 25);
	        add(txtISBN);

	        JLabel lblYearPublication = new JLabel("Ano de publicacao:");
	        lblYearPublication.setBounds(10, 80, 150, 25);
	        add(lblYearPublication);

	        txtYearPublication = new JTextField();
	        txtYearPublication.setBounds(585, 80, 35, 25);
	        add(txtYearPublication);
	        
	        JLabel lblQuantity = new JLabel("Quantidade:");
	        lblQuantity.setBounds(10, 115, 80, 25);
	        add(lblQuantity);

	        txtQuantity = new JTextField();
	        txtQuantity.setBounds(585, 115, 35, 25);
	        add(txtQuantity);
	        
	        JLabel lblGenre = new JLabel("Gênero:");
	        lblGenre.setBounds(10, 150, 80, 25);
	        add(lblGenre);

	        GenresDAO genresDAO = new GenresDAO();
	        List<Genres> genresList = genresDAO.selectAllGenres();
	        DefaultComboBoxModel<Genres> genresModel = new DefaultComboBoxModel<>(genresList.toArray(new Genres[0]));
	        JComboBox<Genres> cbbGenero = new JComboBox<>(genresModel);
	        cbbGenero.setRenderer(new DefaultListCellRenderer() {
	            @Override
	            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	                if (value instanceof Genres) {
	                    Genres genres = (Genres) value;
	                    setText(genres.getName());
	                }
	                return this;
	            }
	        });
	        cbbGenero.setSelectedIndex(-1);
	        cbbGenero.setBounds(455, 150, 165, 25);
	        add(cbbGenero);

	        JLabel lblAuthor = new JLabel("Autor:");
	        lblAuthor.setBounds(10, 185, 80, 25); 
	        add(lblAuthor);

	        AuthorDAO authorDAO = new AuthorDAO();
	        List<Author> authorList = authorDAO.selectAllAuthors();
	        DefaultComboBoxModel<Author> authorModel = new DefaultComboBoxModel<>(authorList.toArray(new Author[0]));
	        JComboBox<Author> cbbAuthor = new JComboBox<>(authorModel);
	        cbbAuthor.setRenderer(new DefaultListCellRenderer() {
	            @Override
	            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	                if (value instanceof Author) {
	                    Author author = (Author) value;
	                    setText(author.getName());
	                }
	                return this;
	            }
	        });
	        cbbAuthor.setSelectedIndex(-1);
	        cbbAuthor.setBounds(455, 185, 165, 25);
	        add(cbbAuthor);

	        JLabel lblPublisher = new JLabel("Editora:");
	        lblPublisher.setBounds(10, 220, 80, 25);
	        add(lblPublisher);

	        PublisherDAO publisherDAO = new PublisherDAO();
	        List<Publisher> publisherList = publisherDAO.selectAllPublisher();
	        DefaultComboBoxModel<Publisher> publisherModel = new DefaultComboBoxModel<>(publisherList.toArray(new Publisher[0]));
	        JComboBox<Publisher> cbbPublisher = new JComboBox<>(publisherModel);
	        cbbPublisher.setRenderer(new DefaultListCellRenderer() {
	            @Override
	            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	                if (value instanceof Publisher) {
	                    Publisher publisher = (Publisher) value;
	                    setText(publisher.getName());
	                }
	                return this;
	            }
	        });
	        cbbPublisher.setSelectedIndex(-1);
	        cbbPublisher.setBounds(455, 220, 165, 25);
	        add(cbbPublisher);

	        
	        btnAdd = new JButton("Adicionar Cliente");
	        btnAdd.setBounds(10, 265, 255, 25);
	        btnAdd.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                //addCustomer();
	            }
	        });
	        add(btnAdd);

	        btnBack = new JButton("Voltar");
	        btnBack.setBounds(351, 265, 89, 25);
	        btnBack.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                cardLayout.show(cardPanel, "BookPanel");
	            }
	        });
	        add(btnBack);
	    }
}
