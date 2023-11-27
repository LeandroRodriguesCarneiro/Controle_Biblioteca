package Screens.CRUDBook;
//-*- coding: utf-8 -*-
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

import java.time.LocalDate;
import java.time.Year;

import Genres.Genres;
import Genres.GenresDAO;
import Author.Author;
import Author.AuthorDAO;
import Book.Book;
import Book.BookDAO;
import Publisher.Publisher;
import Publisher.PublisherDAO;
import Screens.CRUDStudent.StudentPanel;
import Screens.ConfigPanel.Styles;
import Student.Student;

public class UpdateBookPanel extends JPanel{
	 private static final long serialVersionUID = -1723482129844832445L;
	 private JTextField txtTitle, txtISBN, txtYearPublication, txtQuantity;
	    private List<Genres> allGenresList;
	    private JList<Genres> availableGenresList,selectedGenresList;
	    private DefaultListModel<Genres> availableGenresModel,selectedGenresModel;
	    private List<Author> allAuthorList;
	    private JList<Author> availableAuthorList, selectedAuthorList;
	    private DefaultListModel<Author> availableAuthorModel;
	    private JLabel lblBooks,lblTtitle, lblISBN, lblYearPublication, lblQuantity, lblPublisher, lblAuthor, lblGenre;
	    private JComboBox<Publisher>cbbPublisher;
	    private JScrollPane availableAuthorPane, selectedAuthorPane, availableGenrePane, selectedGenrePane;
	    private JButton btnAdd,btnBack, addAuthor,removeAuthor,AddGenre,removeGenre;
	    private CardLayout cardLayout;
	    private JPanel cardPanel;
	    private BookPanel BookPanel;
	    private int selectedPublisher = -1;

	    public UpdateBookPanel(CardLayout cardLayout,JPanel cardPanel, BookPanel bookPanel, Book book) {
	        this.cardLayout = cardLayout;
	        this.cardPanel = cardPanel;
	        this.BookPanel = bookPanel;
	        setLayout(null);
	        
	    	lblBooks = new JLabel("Atualizar Livros");
	    	lblBooks.setBounds(250, 10, 400, 30);
	    	Styles.styleTitleFont(lblBooks);
	        add(lblBooks);

	        lblTtitle = new JLabel("Título:");
	        lblTtitle.setBounds(250, 45, 80, 25);
	        Styles.styleFont(lblTtitle);
	        add(lblTtitle);

	        txtTitle = new JTextField();
	        txtTitle.setBounds(380, 45, 500, 25);
	        txtTitle.setText(book.getTitle());
	        add(txtTitle);

	        lblISBN = new JLabel("ISBN:");
	        Styles.styleFont(lblISBN);
	        lblISBN.setBounds(250, 80, 80, 25);
	        add(lblISBN);

	        txtISBN = new JTextField();
	        txtISBN.setBounds(790, 80, 90, 25);
	        txtISBN.setText(book.getIsbn());
	        add(txtISBN);

	        lblYearPublication = new JLabel("Ano de publicação:");
	        lblYearPublication.setBounds(250, 115, 150, 25);
	        Styles.styleFont(lblYearPublication);
	        add(lblYearPublication);

	        txtYearPublication = new JTextField();
	        txtYearPublication.setBounds(845, 115, 35, 25);
	        txtYearPublication.setText(book.getYearPublication().toString());
	        add(txtYearPublication);
	        
	        lblQuantity = new JLabel("Quantidade:");
	        Styles.styleFont(lblQuantity);
	        lblQuantity.setBounds(250, 150, 80, 25);
	        add(lblQuantity);

	        txtQuantity = new JTextField();
	        txtQuantity.setBounds(845, 150, 35, 25);
	        txtQuantity.setText(String.valueOf(book.getQuantity()));
	        add(txtQuantity);
	        
	        lblPublisher = new JLabel("Editora:");
	        lblPublisher.setBounds(250, 185, 80, 25);
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
	        for (int i = 0; i < publisherList.size(); i++) {
	            Publisher publisher = publisherList.get(i);
	            if (publisher.getName().equals(book.getPublisher())) {
	                cbbPublisher.setSelectedIndex(i);
	                selectedPublisher = publisher.getId();
	                break;
	            }
	        }
	        cbbPublisher.setBounds(715, 185, 165, 25);
	        Styles.styleComboBox(cbbPublisher);
	        add(cbbPublisher);
	        
	        lblAuthor = new JLabel("Autor:");
	        Styles.styleFont(lblAuthor);
	        lblAuthor.setBounds(250, 230, 80, 25); 
	        add(lblAuthor);

	        AuthorDAO authorDAO = new AuthorDAO();
	        allAuthorList = authorDAO.selectAllAuthors();
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
	        availableAuthorPane.setBounds(250, 255, 180, 95);
	        Styles.styleJScrollPane(availableAuthorPane);
	        add(availableAuthorPane);
	        
	        DefaultListModel<Author> selectedAuthorModel = new DefaultListModel<>();
	        for (Author author: allAuthorList) {
	        	for(String authorSelected: book.getAuthor()) {
	        		if(authorSelected.equals(author.getName())) {
	        			selectedAuthorModel.addElement(author);
	        		}
	        	}
	        }
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
	        selectedAuthorPane.setBounds(705, 255, 180, 95);
	        Styles.styleJScrollPane(selectedAuthorPane);
	        add(selectedAuthorPane);

	        addAuthor = new JButton("Adicionar");
	        addAuthor.setBounds(515, 275, 100, 25);
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
	        removeAuthor.setBounds(515, 305, 100, 25);
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
	        lblGenre.setBounds(250, 350, 80, 25);
	        Styles.styleFont(lblGenre);
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
	        
	        availableGenrePane = new JScrollPane(availableGenresList);
	        availableGenrePane.setBounds(250, 375, 180, 95);
	        Styles.styleJScrollPane(availableGenrePane);
	        add(availableGenrePane);
	        
	        selectedGenresModel = new DefaultListModel<>();
	        for (Genres genre: allGenresList) {
	        	for(String genreSelected: book.getGenre()) {
	        		if(genreSelected.equals(genre.getName())) {
	        			selectedGenresModel.addElement(genre);
	        		}
	        	}
	        }
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
	        selectedGenrePane.setBounds(705, 375, 180, 95);
	        Styles.styleJScrollPane(selectedGenrePane);
	        add(selectedGenrePane);
	        
	        AddGenre = new JButton("Adicionar");
	        AddGenre.setBounds(515, 395, 100, 25);
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
	        removeGenre.setBounds(515, 425, 100, 25);
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
	        
	        btnAdd = new JButton("Salvar");
	        btnAdd.setBounds(250, 500, 255, 25);
	        Styles.styleButton(btnAdd);
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
					    }else if(Year.of(Integer.valueOf(txtYearPublication.getText())).compareTo(Year.of(LocalDate.now().getYear()))>0) {
					    	txtYearPublication.setText("");
					    	txtYearPublication.requestFocus();
					    	JOptionPane.showMessageDialog(null, "Por Favor, Digite um ano valido");
					    	return;
					    }
					    
					    if(txtQuantity.getText().isEmpty()) {
					    	JOptionPane.showMessageDialog(null, "Por favor, Digite a quantidade");
					    	txtQuantity.requestFocus();
					        return;
					    }
					    quantity = Integer.valueOf(txtQuantity.getText());
					    if (quantity<0) {
					    	JOptionPane.showMessageDialog(null, "Por favor, a quantidade deve ser maior que 0");
					    	txtQuantity.requestFocus();
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
					} catch (NumberFormatException ex) {
					    JOptionPane.showMessageDialog(null, "Certifique-se de inserir números válidos para Ano e Quantidade.");
					}
					try {
						int dialogResult = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja salvar?", "Confirmação", JOptionPane.YES_NO_OPTION);
			    	    if (dialogResult == JOptionPane.YES_OPTION) {
			    	    	BookDAO bookDAO = new BookDAO();
							bookDAO.UpdateBook(book.getId(),selectedPublisher, ISBN, title, yearPublication, quantity, genresList, authorList);
							bookPanel.refreshBookTable();
			                cardLayout.show(cardPanel, "BookPanel");
			    	    } else {
			    	        return;
			    	    }
						
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, "Este ISBN já está em uso. Por favor, insira um ISBN diferente.");
					}
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
	        Styles.styleButton(btnBack);
	        btnBack.setBounds(790, 500, 89, 25);
	        btnBack.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	bookPanel.refreshBookTable();
	                cardLayout.show(cardPanel, "BookPanel");
	            }
	        });
	        add(btnBack);
	    }
}
