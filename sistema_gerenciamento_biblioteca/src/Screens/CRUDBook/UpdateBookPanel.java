package Screens.CRUDBook;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import java.time.Year;

import Genres.Genres;
import Genres.GenresDAO;
import Author.Author;
import Author.AuthorDAO;
import Book.Book;
import Book.BookDAO;
import Publisher.Publisher;
import Publisher.PublisherDAO;

public class UpdateBookPanel extends JPanel{
	 private static final long serialVersionUID = -1723482129844832445L;
	    private JTextField txtTitle, txtISBN, txtYearPublication, txtQuantity;
	    private List<Genres> allGenresList;
	    private JList<Genres> availableGenresList;
	    private DefaultListModel<Genres> availableGenresModel;
	    private List<Author> allAuthorList;
	    private JList<Author> availableAuthorList;
	    private DefaultListModel<Author> availableAuthorModel;
	    private JLabel lblBooks;
	    private JComboBox<Publisher>cbbPublisher;
	    private JButton btnAdd;
	    private DefaultTableModel tableModel;
	    private JButton btnBack;
	    private CardLayout cardLayout;
	    private JPanel cardPanel;
	    private BookPanel BookPanel;
	    private int selectedPublisher = -1;

	    public UpdateBookPanel(DefaultTableModel tableModel, CardLayout cardLayout,
	            JPanel cardPanel, BookPanel BookPanel, Book book) {
	        
	    	lblBooks = new JLabel("Editar Livro");
	        lblBooks.setFont(new Font("Arial", Font.BOLD, 30));
	        lblBooks.setBounds(20, 10, 400, 30);
	        add(lblBooks);
	        
	        this.tableModel = tableModel;
	        this.cardLayout = cardLayout;
	        this.cardPanel = cardPanel;
	        this.BookPanel = BookPanel;
	        setLayout(null);

	        JLabel lblTtitle = new JLabel("title:");
	        lblTtitle.setBounds(10, 45, 80, 25);
	        add(lblTtitle);

	        txtTitle = new JTextField();
	        txtTitle.setBounds(120, 45, 500, 25);
	        txtTitle.setText(book.getTitle());
	        add(txtTitle);

	        JLabel lblISBN = new JLabel("ISBN:");
	        lblISBN.setBounds(10, 80, 80, 25);
	        add(lblISBN);

	        txtISBN = new JTextField();
	        txtISBN.setBounds(530, 80, 90, 25);
	        txtISBN.setText(book.getIsbn());
	        add(txtISBN);

	        JLabel lblYearPublication = new JLabel("Ano de publicacao:");
	        lblYearPublication.setBounds(10, 115, 150, 25);
	        add(lblYearPublication);

	        txtYearPublication = new JTextField();
	        txtYearPublication.setBounds(585, 115, 35, 25);
	        txtYearPublication.setText(book.getYearPublication().toString());
	        add(txtYearPublication);
	        
	        JLabel lblQuantity = new JLabel("Quantidade:");
	        lblQuantity.setBounds(10, 150, 80, 25);
	        add(lblQuantity);

	        txtQuantity = new JTextField();
	        txtQuantity.setBounds(585, 150, 35, 25);
	        txtQuantity.setText(String.valueOf(book.getQuantity()));
	        add(txtQuantity);
	        
	        JLabel lblPublisher = new JLabel("Editora:");
	        lblPublisher.setBounds(10, 185, 80, 25);
	        add(lblPublisher);
	        
	        PublisherDAO publisherDAO = new PublisherDAO();
	        List<Publisher> publisherList = publisherDAO.selectAllPublisher();
	        DefaultComboBoxModel<Publisher> publisherModel = new DefaultComboBoxModel<>(publisherList.toArray(new Publisher[0]));
	        cbbPublisher = new JComboBox<>(publisherModel);
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
	        for (int i = 0; i < publisherList.size(); i++) {
	            Publisher publisher = publisherList.get(i);
	            if (publisher.getName().equals(book.getPublisher())) {
	                cbbPublisher.setSelectedIndex(i);
	                selectedPublisher = publisher.getId();
	                break;
	            }
	        }
	        cbbPublisher.setBounds(455, 185, 165, 25);
	        add(cbbPublisher);
	        
	        JLabel lblAuthor = new JLabel("Autor:");
	        lblAuthor.setBounds(10, 210, 80, 25); 
	        add(lblAuthor);

	        AuthorDAO authorDAO = new AuthorDAO();
	        allAuthorList = authorDAO.selectAllAuthors();
	        availableAuthorModel = new DefaultListModel<>(); // Correção: Inicializando o modelo
	        for (Author author : allAuthorList) {
	            availableAuthorModel.addElement(author);
	        }
	        availableAuthorList = new JList<>(availableAuthorModel); // Correção: Inicializando a lista com o modelo
	        availableAuthorList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	        availableAuthorList.setCellRenderer(new DefaultListCellRenderer() {
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
	        JScrollPane availableAuthorPane = new JScrollPane(availableAuthorList);
	        availableAuthorPane.setBounds(10, 230, 180, 95);
	        add(availableAuthorPane);
	        
	        DefaultListModel<Author> selectedAuthorModel = new DefaultListModel<>();
	        for (Author author: allAuthorList) {
	        	for(String authorSelected: book.getAuthor()) {
	        		if(authorSelected.equals(author.getName())) {
	        			selectedAuthorModel.addElement(author);
	        		}
	        	}
	        }
	        JList<Author> selectedAuthorList = new JList<>(selectedAuthorModel);
	        selectedAuthorList.setCellRenderer(new DefaultListCellRenderer() {
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
	        JScrollPane selectedAuthorPane = new JScrollPane(selectedAuthorList);
	        selectedAuthorPane.setBounds(440, 230, 180, 95);
	        add(selectedAuthorPane);

	        JButton addAuthor = new JButton("Adicionar");
	        addAuthor.setBounds(270, 245, 90, 25);
	        addAuthor.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                List<Author> selectedValues = availableAuthorList.getSelectedValuesList();

	                for (Author selectedValue : selectedValues) {
	                    if (!selectedAuthorModel.contains(selectedValue)) {
	                        selectedAuthorModel.addElement(selectedValue);
	                    }
	                }
	            }
	        });
	        add(addAuthor);

	        JButton removeAuthor = new JButton("Remover");
	        removeAuthor.setBounds(270, 280, 90, 25);
	        removeAuthor.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                int selectedIndex = selectedAuthorList.getSelectedIndex();
	                if (selectedIndex != -1) {
	                    selectedAuthorModel.remove(selectedIndex);
	                }
	            }
	        });
	        add(removeAuthor);
	        
	        JLabel lblGenre = new JLabel("Gênero:");
	        lblGenre.setBounds(10, 335, 80, 25);
	        add(lblGenre);
	        
	        	        
	        GenresDAO genresDAO = new GenresDAO();
	        allGenresList = genresDAO.selectAllGenres();
	        availableGenresModel = new DefaultListModel<>();
	        for (Genres genre : allGenresList) {
	            availableGenresModel.addElement(genre);
	        }
	        
	        availableGenresList = new JList<>(availableGenresModel);
	        availableGenresList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	        availableGenresList.setCellRenderer(new DefaultListCellRenderer() {
	            @Override
	            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	                if (value instanceof Genres) {
	                    Genres genre = (Genres) value;
	                    setText(genre.getName()); 
	                }
	                return this;
	            }
	        });
	        
	        JScrollPane availableGenrePane = new JScrollPane(availableGenresList);
	        availableGenrePane.setBounds(10, 355, 180, 95);
	        add(availableGenrePane);
	        
	        DefaultListModel<Genres> selectedGenresModel = new DefaultListModel<>();
	        for (Genres genre: allGenresList) {
	        	for(String genreSelected: book.getGenre()) {
	        		if(genreSelected.equals(genre.getName())) {
	        			selectedGenresModel.addElement(genre);
	        		}
	        	}
	        }
	        JList<Genres> selectedGenresList = new JList<>(selectedGenresModel);
	        selectedGenresList.setCellRenderer(new DefaultListCellRenderer() {
	            @Override
	            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	                if (value instanceof Genres) {
	                    Genres genre = (Genres) value;
	                    setText(genre.getName()); 
	                }
	                return this;
	            }
	        });
	        
	        JScrollPane selectedGenrePane = new JScrollPane(selectedGenresList);
	        selectedGenrePane.setBounds(440, 355, 180, 95);
	        add(selectedGenrePane);
	        
	        JButton addGenre = new JButton("Adicionar");
	        addGenre.setBounds(270, 365, 90, 25);
	        addGenre.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                List<Genres> selectedValues = availableGenresList.getSelectedValuesList();
	                int selectedCount = selectedGenresModel.size();

	                for (Genres selectedValue : selectedValues) {
	                    if (!selectedGenresModel.contains(selectedValue) && selectedCount < 5) {
	                        selectedGenresModel.addElement(selectedValue);
	                        selectedCount++;
	                    }
	                }
	            }
	        });
	        add(addGenre);

	        JButton removeGenre = new JButton("Remover");
	        removeGenre.setBounds(270, 395, 90, 25);
	        removeGenre.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                int selectedIndex = selectedGenresList.getSelectedIndex();
	                if (selectedIndex != -1) {
	                    selectedGenresModel.remove(selectedIndex);
	                }
	            }
	        });
	        add(removeGenre);
	        
	        
	        
	        btnAdd = new JButton("Salvar");
	        btnAdd.setBounds(10, 500, 255, 25);
	        btnAdd.addActionListener(new ActionListener() {
				@Override
	            public void actionPerformed(ActionEvent e) {
					String title = null;
					String ISBN = null;
					Year yearPublication = null;
					Integer quantity = null;
					List<Author> authorList = new ArrayList<>();
					List<Genres> genresList = new ArrayList<>();
					try {
					    if (txtTitle.getText().isEmpty()) {
					        JOptionPane.showMessageDialog(null, "Por favor, preencha o título.");
					        return;
					    }

					    if (txtISBN.getText().isEmpty()) {
					        JOptionPane.showMessageDialog(null, "Por favor, preencha o ISBN.");
					        return;
					    } else if (txtISBN.getText().length() < 13) {
					        JOptionPane.showMessageDialog(null, "Por favor, o ISBN precisa ter 13 dígitos.");
					        return;
					    }

					    if (txtYearPublication.getText().length() != 4) {
					        JOptionPane.showMessageDialog(null, "Por favor, o ano precisa ter 4 digitos.");
					        return;
					    }
					    
					    if(txtQuantity.getText().isEmpty()) {
					    	JOptionPane.showMessageDialog(null, "Por favor, Digite a quantidade");
					        return;
					    }
					    

					    if(selectedPublisher<1) {
					    	JOptionPane.showMessageDialog(null, "Por favor, selecione uma editora.");
					        return;
					    }
					    
					    if(selectedAuthorModel.isEmpty()) {
					    	JOptionPane.showMessageDialog(null, "Por favor, Selecione um autor");
					        return;
					    }
					    
					    for (int i = 0; i < selectedAuthorModel.size(); i++) {
					        Author author = selectedAuthorModel.getElementAt(i);
					        authorList.add(author);
					    }
					    
					    if(selectedGenresModel.isEmpty()) {
					    	JOptionPane.showMessageDialog(null, "Por favor, Selecione um genero");
					        return;
					    }
					    
					    for (int i = 0; i < selectedGenresModel.size(); i++) {
					        Genres genre = selectedGenresModel.getElementAt(i);
					        genresList.add(genre);
					    }
					    title = String.valueOf(txtTitle.getText());
					    ISBN = String.valueOf(txtISBN.getText());
					    yearPublication = Year.of(Integer.valueOf(txtYearPublication.getText()));
					    quantity = Integer.valueOf(txtQuantity.getText());
					} catch (NumberFormatException ex) {
					    JOptionPane.showMessageDialog(null, "Certifique-se de inserir números válidos para Ano e Quantidade.");
					}
					try {
						BookDAO bookDAO = new BookDAO();
						bookDAO.UpdateBook(book.getId(),selectedPublisher, ISBN, title, yearPublication, quantity, genresList, authorList);
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, "Este ISBN já está em uso. Por favor, insira um ISBN diferente.");
					}
					txtTitle.setText("");
					txtISBN.setText("");
					txtYearPublication.setText("");
					txtQuantity.setText("");
					selectedPublisher = -1;
					cbbPublisher.setSelectedIndex(-1);
					selectedAuthorModel.clear();
					selectedGenresModel.clear();
	            }
	            
	        });
	        cbbPublisher.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
					JComboBox<Publisher> combo = (JComboBox<Publisher>) e.getSource();
	                Publisher selectedPublisherCbb = (Publisher) combo.getSelectedItem();
	                
	                if (selectedPublisherCbb != null) {
	                	UpdateBookPanel.this.selectedPublisher = selectedPublisherCbb.getId();
	                }
	            }
	        }); 
	        add(btnAdd);

	        btnBack = new JButton("Voltar");
	        btnBack.setBounds(351, 500, 89, 25);
	        btnBack.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	BookPanel.refreshBookTable();
	                cardLayout.show(cardPanel, "BookPanel");
	            }
	        });
	        add(btnBack);
	    }
}
