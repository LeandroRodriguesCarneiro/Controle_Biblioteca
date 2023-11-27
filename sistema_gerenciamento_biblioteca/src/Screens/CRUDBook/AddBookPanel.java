package Screens.CRUDBook;

//-*- coding: utf-8 -*-

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

import java.time.LocalDate;
import java.time.Year;

import Genres.Genres;
import Genres.GenresDAO;
import Author.Author;
import Author.AuthorDAO;
import Book.BookDAO;
import Publisher.Publisher;
import Publisher.PublisherDAO;
import Screens.ConfigPanel.Styles;

public class AddBookPanel extends JPanel{
	 private static final long serialVersionUID = -1723482129844832445L;
	    private JTextField txtTitle, txtISBN, txtYearPublication, txtQuantity, txtSearchGenre, txtSearchAuthor;
	    private List<Genres> allGenresList;
	    private JList<Genres> availableGenresList,selectedGenresList;
	    private DefaultListModel<Genres> availableGenresModel,selectedGenresModel;
	    private List<Author> allAuthorList;
	    private JList<Author> availableAuthorList,selectedAuthorList;
	    private DefaultListModel<Author> availableAuthorModel,selectedAuthorModel;
	    private JLabel lblBooks,lblTtitle, lblISBN, lblYearPublication, lblQuantity, lblPublisher, lblAuthor, lblGenre;
	    private JComboBox<Publisher>cbbPublisher;
	    private JScrollPane availableAuthorPane, selectedAuthorPane, availableGenrePane, selectedGenrePane;
	    private JButton btnAdd,btnBack, addAuthor,removeAuthor,AddGenre,removeGenre, btnSearchGenre, btnSearchAuthor;
	    private CardLayout cardLayout;
	    private JPanel cardPanel;
	    private BookPanel BookPanel;
	    private int selectedPublisher = -1;

	    public AddBookPanel(DefaultTableModel tableModel, CardLayout cardLayout,
	        JPanel cardPanel, BookPanel BookPanel) {
	    	this.cardLayout = cardLayout;
	        this.cardPanel = cardPanel;
	        this.BookPanel = BookPanel;
	        setLayout(null);
	        
	    	lblBooks = new JLabel("Inserir Livros");
	    	lblBooks.setBounds(250, 10, 400, 30);
	    	Styles.styleTitleFont(lblBooks);
	        add(lblBooks);

	        lblTtitle = new JLabel("Título:");
	        lblTtitle.setBounds(250, 70, 80, 25);
	        Styles.styleFont(lblTtitle);
	        add(lblTtitle);

	        txtTitle = new JTextField();
	        txtTitle.setBounds(380, 70, 500, 25);
	        add(txtTitle);

	        lblISBN = new JLabel("ISBN:");
	        Styles.styleFont(lblISBN);
	        lblISBN.setBounds(250, 110, 80, 25);
	        add(lblISBN);

	        txtISBN = new JTextField();
	        txtISBN.setBounds(790, 110, 90, 25);
	        add(txtISBN);

	        lblYearPublication = new JLabel("Ano de publicação:");
	        lblYearPublication.setBounds(250, 150, 150, 25);
	        Styles.styleFont(lblYearPublication);
	        add(lblYearPublication);

	        txtYearPublication = new JTextField();
	        txtYearPublication.setBounds(845, 150, 35, 25);
	        add(txtYearPublication);
	        
	        lblQuantity = new JLabel("Quantidade:");
	        Styles.styleFont(lblQuantity);
	        lblQuantity.setBounds(250, 195, 80, 25);
	        add(lblQuantity);

	        txtQuantity = new JTextField();
	        txtQuantity.setBounds(845, 195, 35, 25);
	        add(txtQuantity);
	        
	        lblPublisher = new JLabel("Editora:");
	        lblPublisher.setBounds(250, 245, 80, 25);
	        Styles.styleFont(lblPublisher);
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
	        cbbPublisher.setSelectedIndex(-1);
	        cbbPublisher.setBounds(715, 245, 165, 25);
	        Styles.styleComboBox(cbbPublisher);
	        add(cbbPublisher);
	        
	        lblAuthor = new JLabel("Autor:");
	        Styles.styleFont(lblAuthor);
	        lblAuthor.setBounds(250, 280, 80, 25); 
	        add(lblAuthor);
	        
	        txtSearchAuthor = new JTextField();
	        txtSearchAuthor.setBounds(250, 305, 180, 25);
	        add(txtSearchAuthor);
	        
	        btnSearchAuthor = new JButton("Buscar");
	        btnSearchAuthor.setBounds(515, 305, 100, 25);
	        Styles.styleButton(btnSearchAuthor);
	        add(btnSearchAuthor);
	        
	        btnSearchAuthor.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                AuthorDAO authorDAO = new AuthorDAO();
	                List<Author> filteredAuthors = authorDAO.selectAuthorsByName(txtSearchAuthor.getText(), true);

	                availableAuthorModel.clear(); 
	                for (Author author : filteredAuthors) {
	                    availableAuthorModel.addElement(author); 
	                }
	            }
	        });

	        
	        AuthorDAO authorDAO = new AuthorDAO();
	        allAuthorList = authorDAO.selectAuthorsByName("", true);
	        availableAuthorModel = new DefaultListModel<>();
	        for (Author author : allAuthorList) {
	            availableAuthorModel.addElement(author);
	        }
	        availableAuthorList = new JList<>(availableAuthorModel);
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
	        availableAuthorPane = new JScrollPane(availableAuthorList);
	        availableAuthorPane.setBounds(250, 340, 180, 95);
	        Styles.styleJScrollPane(availableAuthorPane);
	        add(availableAuthorPane);

	        selectedAuthorModel = new DefaultListModel<>();
	        selectedAuthorList = new JList<>(selectedAuthorModel);
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
	        selectedAuthorPane = new JScrollPane (selectedAuthorList);
	        selectedAuthorPane.setBounds(705, 340, 180, 95);
	        Styles.styleJScrollPane(selectedAuthorPane);
	        add(selectedAuthorPane);

	        addAuthor = new JButton("Adicionar");
	        addAuthor.setBounds(515, 360, 100, 25);
	        Styles.styleButton(addAuthor);
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

	        removeAuthor = new JButton("Remover");
	        removeAuthor.setBounds(515, 390, 100, 25);
	        Styles.styleButton(removeAuthor);
	        removeAuthor.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                int selectedIndex = selectedAuthorList.getSelectedIndex();
	                if (selectedIndex != -1) {
	                    selectedAuthorModel.remove(selectedIndex);
	                }
	            }
	        });
	        add(removeAuthor);
	        
	        lblGenre = new JLabel("Gênero:");
	        lblGenre.setBounds(250, 435, 80, 25);
	        Styles.styleFont(lblGenre);
	        add(lblGenre);
	        
	        txtSearchGenre = new JTextField();
	        txtSearchGenre.setBounds(250, 465, 180, 25);
	        add(txtSearchGenre);
	        
	        btnSearchGenre = new JButton("Buscar");
	        btnSearchGenre.setBounds(515, 465, 100, 25);
	        Styles.styleButton(btnSearchGenre);
	        add(btnSearchGenre);
	        
	        btnSearchGenre.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                GenresDAO genresDAO = new GenresDAO();
	                List<Genres> filteredGenres = genresDAO.selectGenresByName(txtSearchGenre.getText(), true);

	                availableGenresModel.clear(); 
	                for (Genres genre : filteredGenres) {
	                	availableGenresModel.addElement(genre); 
	                }
	            }
	        });
	        
	        GenresDAO genresDAO = new GenresDAO();
	        allGenresList = genresDAO.selectGenresByName("", true);
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
	        
	        availableGenrePane = new JScrollPane(availableGenresList);
	        availableGenrePane.setBounds(250, 500, 180, 95);
	        Styles.styleJScrollPane(availableGenrePane);
	        add(availableGenrePane);
	        
	        selectedGenresModel = new DefaultListModel<>();
	        selectedGenresList = new JList<>(selectedGenresModel);
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
	        selectedGenrePane = new JScrollPane (selectedGenresList);
	        selectedGenrePane.setBounds(705, 500, 180, 95);
	        Styles.styleJScrollPane(selectedGenrePane);
	        add(selectedGenrePane);
	        
	        AddGenre = new JButton("Adicionar");
	        AddGenre.setBounds(515, 515, 100, 25);
	        Styles.styleButton(AddGenre);
	        AddGenre.addActionListener(new ActionListener() {
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
	        add(AddGenre);

	        removeGenre = new JButton("Remover");
	        removeGenre.setBounds(515, 545, 100, 25);
	        Styles.styleButton(removeGenre);
	        removeGenre.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                int selectedIndex = selectedGenresList.getSelectedIndex();
	                if (selectedIndex != -1) {
	                    selectedGenresModel.remove(selectedIndex);
	                }
	            }
	        });
	        add(removeGenre);
	        
	        btnAdd = new JButton("Adicionar Livro");
	        btnAdd.setBounds(250, 635, 255, 25);
	        Styles.styleButton(btnAdd);
	        btnAdd.addActionListener(new ActionListener() {
				@Override
	            public void actionPerformed(ActionEvent e) {
					String title = null;
					String ISBN = null;
					Year yearPublication = null;
					Integer quantity = null;
					List<Author> authorListID = new ArrayList<>();
					List<Genres> genresListID = new ArrayList<>();
					try {
					    if (txtTitle.getText().trim().isEmpty()) {
					        JOptionPane.showMessageDialog(null, "Por favor, preencha o título.");
					        txtTitle.requestFocus();
					        return;
					    }

					    if (txtISBN.getText().trim().isEmpty()) {
					        JOptionPane.showMessageDialog(null, "Por favor, preencha o ISBN.");
					        txtISBN.requestFocus();
					        return;
					    } else if (txtISBN.getText().trim().length() != 13 || !txtISBN.getText().trim().matches("[0-9]+")) {
					        JOptionPane.showMessageDialog(null, "Por favor, o ISBN precisa ter 13 dígitos.");
					        txtISBN.requestFocus();
					        return;
					    }

					    if (txtYearPublication.getText().trim().length() != 4) {
					        JOptionPane.showMessageDialog(null, "Por favor, o ano precisa ter 4 digitos.");
					        txtYearPublication.requestFocus();
					        return;
					    }else if(Year.of(Integer.valueOf(txtYearPublication.getText().trim())).compareTo(Year.of(LocalDate.now().getYear()))>0) {
					    	txtYearPublication.setText("");
					    	txtYearPublication.requestFocus();
					    	JOptionPane.showMessageDialog(null, "Por Favor, Digite um ano valido");
					    	return;
					    }
					    
					    if(txtQuantity.getText().trim().isEmpty() && txtQuantity.getText().trim().matches("[0-9]")) {
					    	JOptionPane.showMessageDialog(null, "Por favor, Digite a quantidade");
					    	txtQuantity.requestFocus();
					        return;
					    }
					    quantity = Integer.valueOf(txtQuantity.getText());
					    if (quantity < 0) {
					    	JOptionPane.showMessageDialog(null, "Por favor, a quantidade não pode ser negativa");
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
					    authorListID = new ArrayList<>();
					    for (int i = 0; i < selectedAuthorModel.size(); i++) {
					        Author author = selectedAuthorModel.getElementAt(i);
					        authorListID.add(author);
					    }
					    
					    if(selectedGenresModel.isEmpty()) {
					    	JOptionPane.showMessageDialog(null, "Por favor, Selecione um genero");
					        return;
					    }
					    genresListID = new ArrayList<>();
					    for (int i = 0; i < selectedGenresModel.size(); i++) {
					        Genres genre = selectedGenresModel.getElementAt(i);
					        genresListID.add(genre);
					    }
					    title = String.valueOf(txtTitle.getText());
					    ISBN = String.valueOf(txtISBN.getText());
					    yearPublication = Year.of(Integer.valueOf(txtYearPublication.getText()));
					} catch (NumberFormatException ex) {
					    JOptionPane.showMessageDialog(null, "Certifique-se de inserir números válidos para Ano e Quantidade.");
					    return;
					}
					try {
						BookDAO bookDAO = new BookDAO();
						bookDAO.insertBook(selectedPublisher, ISBN, title, yearPublication, quantity, genresListID, authorListID);
						JOptionPane.showMessageDialog(null, "Livro inserido com sucesso.");
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, ex.getMessage());
						txtISBN.setText("");
						txtISBN.requestFocus();
						return;
					}
					txtTitle.setText("");
					txtISBN.setText("");
					txtYearPublication.setText("");
					txtQuantity.setText("");
					selectedPublisher = -1;
					cbbPublisher.setSelectedIndex(-1);
					selectedAuthorModel.clear();
					selectedGenresModel.clear();
					txtTitle.requestFocus();
	            }
	            
	        });
	        cbbPublisher.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
					JComboBox<Publisher> combo = (JComboBox<Publisher>) e.getSource();
	                Publisher selectedPublisherCbb = (Publisher) combo.getSelectedItem();
	                
	                if (selectedPublisherCbb != null) {
	                	AddBookPanel.this.selectedPublisher = selectedPublisherCbb.getId();
	                }
	            }
	        }); 
	        add(btnAdd);

	        btnBack = new JButton("Voltar");
	        Styles.styleButton(btnBack);
	        btnBack.setBounds(790, 635, 89, 25);
	        btnBack.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	BookPanel.refreshBookTable();
	                cardLayout.show(cardPanel, "BookPanel");
	            }
	        });
	        add(btnBack);
	    }
}
