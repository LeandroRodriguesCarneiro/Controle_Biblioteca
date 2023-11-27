package Screens.CRUDBook;
//-*- coding: utf-8 -*-
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import Book.Book;
import Book.BookDAO;
import Genres.Genres;
import Genres.GenresDAO;
import Screens.ConfigPanel.Styles;

public class GenresPanel extends JPanel{
	private static final long serialVersionUID = -4843807817212241104L;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnEdit, btnDelete,search;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JButton backButton;
    private JTextField txtGenre;
    private JCheckBox chkActive;
    private JLabel lblBooks, lblTitle, lblActive;
    private List<Genres> genresList = new ArrayList<>();
    private GenresDAO genresDAO = new GenresDAO();
    private BookPanel BookPanel;
    private boolean active = true;
    
    public GenresPanel(CardLayout cardLayout, JPanel cardPanel, BookPanel BookPanel) {
    	this.cardPanel = cardPanel;
        this.cardLayout = cardLayout;
        this.BookPanel = BookPanel;
        setLayout(null);
        
        lblBooks = new JLabel("Gêneros");
        Styles.styleTitleFont(lblBooks);
        lblBooks.setBounds(142, 10, 150, 30);
        add(lblBooks);
        
        backButton = new JButton("Voltar");
        backButton.setBounds(992, 10, 80, 30);
        Styles.styleButton(backButton);
        backButton.addActionListener(e -> {
            	BookPanel.refreshBookTable();
                cardLayout.show(cardPanel, "BookPanel");
        });
        add(backButton);
        
        lblActive = new JLabel("Ativo?");
        lblActive.setBounds(840,90, 150,30);
        Styles.styleFont(lblActive);
        add(lblActive);
        
        chkActive = new JCheckBox();
        chkActive.setBounds(900,90,90,30);
        chkActive.setSelected(this.active);
        add(chkActive);
        
        chkActive.addActionListener(e ->{
        	this.active = !this.active;
        });
        
        lblTitle = new JLabel("Gênero:");
        Styles.styleFont(lblTitle);
        lblTitle.setBounds(142, 45, 80, 25);
        add(lblTitle);

        txtGenre = new JTextField();
        txtGenre.setBounds(210, 45, 500, 25);
        add(txtGenre);

        search = new JButton("Pesquisar");
        search.setBounds(932, 45, 140, 25);
        Styles.styleButton(search);
        add(search);
        
        search.addActionListener(e ->{
        	refreshGesnresTable();
        });
        
        btnAdd = new JButton("Adicionar Gênero");
        btnAdd.setBounds(140, 90, 150, 30);
        Styles.styleButton(btnAdd);
        add(btnAdd);
        
        tableModel = new DefaultTableModel() {
            private static final long serialVersionUID = -9049266189071413309L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("ID");
        tableModel.addColumn("Genêros");
        tableModel.addColumn("active");

        table = new JTable(tableModel);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setPreferredWidth(0);
        columnModel.getColumn(0).setWidth(0);
        columnModel.getColumn(2).setMaxWidth(0);
        columnModel.getColumn(2).setMinWidth(0);
        columnModel.getColumn(2).setPreferredWidth(0);
        columnModel.getColumn(2).setWidth(0);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(140, 135, 930, 300);
        Styles.styleTable(table,scrollPane);
        add(scrollPane);
        
        btnEdit = new JButton("Editar Gênero");
        btnEdit.setBounds(140, 445, 150, 30);
        Styles.styleButton(btnEdit);
        add(btnEdit);

        btnDelete = new JButton("Deletar Gênero");
        btnDelete.setBounds(300, 445, 150, 30);
        Styles.styleButton(btnDelete);
        add(btnDelete);

        refreshGesnresTable();
        
        btnAdd.addActionListener(e -> {
            AddGenrePanel addGenrePanel = new AddGenrePanel(cardLayout, cardPanel,
                    this);
            cardPanel.add(addGenrePanel, "AddGenrePanel");
            cardLayout.show(cardPanel, "AddGenrePanel");
        });
        
        btnDelete.addActionListener(e -> {
        	int selectedRow = table.getSelectedRow();
    	    if (selectedRow != -1) {
    	        int GenreID = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
	    	    int dialogResult = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir?", "Confirmação", JOptionPane.YES_NO_OPTION);
	    	    if (dialogResult == JOptionPane.YES_OPTION) {
	    	    	try{
	    	    		genresDAO.deleteGenre(GenreID);
	    	    		refreshGesnresTable();
	    	    	}catch(Exception ex) {
	    	    		JOptionPane.showMessageDialog(null, ex.getMessage());
	    	    	}
		    	    
	    	    } else {
	    	        return;
	    	    }
    	    } else {
    	        JOptionPane.showMessageDialog(null, "Por favor, selecione um gênero na tabela.");
    	        return;
    	    }
        });
        btnEdit.addActionListener(e ->{
        	int selectedRow = table.getSelectedRow();
    	    if (selectedRow != -1) {
    	    	Genres genre = new Genres(
    	    			Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString()),
    	    	    	tableModel.getValueAt(selectedRow, 1).toString(),
    	    	    	Boolean.valueOf(tableModel.getValueAt(selectedRow, 2).toString())
    	    		);
    	    	UpdateGenrePanel updateGenrePanel = new UpdateGenrePanel(cardLayout, cardPanel, this, genre);
    	    	cardPanel.add(updateGenrePanel, "UpdateBookPanel");
    	    	cardLayout.show(cardPanel, "UpdateBookPanel");
    	    }else {
    	    	JOptionPane.showMessageDialog(null, "Por favor, selecione um gênero na tabela.");
    	        return;
    	    }
        });

    }
    
    public void refreshGesnresTable() {
    	loadGenresIntoTable();
    }

    public void loadGenresIntoTable() {
        tableModel.setRowCount(0);
        genresList.clear();
        List<Genres> updatedGenresList = genresDAO.selectGenresByName(txtGenre.getText().trim(), this.active);
        	if (updatedGenresList != null) {
        		updatedGenresList.sort(Comparator.comparingInt(Genres::getId));
                genresList.addAll(updatedGenresList);
                if (!updatedGenresList.isEmpty()) {
                    SwingUtilities.invokeLater(() -> {
                        for (Genres genres : genresList) {
                            tableModel.addRow(new Object[]{
                                    genres.getId(),
                                    genres.getName(),
                                    genres.isActive()
                            });
                        }
                    });
                    
                }else {
                	JOptionPane.showMessageDialog(this, "Não foi encontrado dados para esse filtro.", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao carregar dados do Banco de dados.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
  }