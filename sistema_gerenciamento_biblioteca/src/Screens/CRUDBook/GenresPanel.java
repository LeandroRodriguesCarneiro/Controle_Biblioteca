package Screens.CRUDBook;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import Genres.Genres;
import Genres.GenresDAO;

public class GenresPanel extends JPanel{
	private static final long serialVersionUID = -4843807817212241104L;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnEdit, btnDelete;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private GenresDAO genresDAO = new GenresDAO();
    private JButton backButton;
    private JLabel lblBooks;
    private List<Genres> genresList = new ArrayList<>();
    private boolean loadGenresList = false;
    
    public GenresPanel(CardLayout cardLayout, JPanel cardPanel, BookPanel BookPanel) {
        this.cardPanel = cardPanel;

        setLayout(null);

        tableModel = new DefaultTableModel() {
            /**
             * 
             */
            private static final long serialVersionUID = -9049266189071413309L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("ID");
        tableModel.addColumn("Nome");

        lblBooks = new JLabel("Generos");
        lblBooks.setFont(new Font("Arial", Font.BOLD, 30));
        lblBooks.setBounds(20, 10, 150, 30);
        add(lblBooks);

        table = new JTable(tableModel);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setPreferredWidth(0);
        columnModel.getColumn(0).setWidth(0);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 50, 930, 300);
        add(scrollPane);
        
        btnAdd = new JButton("Adicionar Genero");
        btnAdd.setBounds(180, 10, 150, 30);
        add(btnAdd);

        btnEdit = new JButton("Editar Genero");
        btnEdit.setBounds(20, 360, 150, 30);
        add(btnEdit);

        btnDelete = new JButton("Deletar Genero");
        btnDelete.setBounds(180, 360, 150, 30);
        add(btnDelete);

        backButton = new JButton("Voltar");
        backButton.setBounds(870, 10, 80, 30);
        add(backButton);

        backButton.addActionListener(e -> cardLayout.show(cardPanel, "BookPanel"));
        loadGenresIntoTable();
        
        btnAdd.addActionListener(e -> {
        	loadGenresList = false;
            AddGenrePanel AddGenresPanel = new AddGenrePanel(tableModel, cardLayout, cardPanel, this); 
            cardPanel.add(AddGenresPanel, "AddGenresPanel");
            cardLayout.show(cardPanel, "AddGenresPanel");
        });
        
        btnDelete.addActionListener(e -> {
        	int selectedRow = table.getSelectedRow();
    	    if (selectedRow != -1) {
    	        int GenreID = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
	    	    int dialogResult = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir?", "Confirmação", JOptionPane.YES_NO_OPTION);
	    	    if (dialogResult == JOptionPane.YES_OPTION) {
		    	    genresDAO.deleteGenre(GenreID);
		    	    loadGenresList = false;
		    	    loadGenresIntoTable();
	    	    } else {
	    	        return;
	    	    }
    	    } else {
    	        JOptionPane.showMessageDialog(null, "Por favor, selecione um genero na tabela.");
    	    }
        });
        
        btnEdit.addActionListener(e ->{
        	int selectedRow = table.getSelectedRow();
    	    if (selectedRow != -1) {
    	    	Genres genre= new Genres(
    	    			Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString()),
    	    	    	tableModel.getValueAt(selectedRow, 1).toString()
    	    		);
    	    	loadGenresList = false;
    	    	UpdateGenrePanel UpdateGenrePanel = new UpdateGenrePanel(tableModel, cardLayout, cardPanel, this, genre);
    	    	cardPanel.add(UpdateGenrePanel, "UpdateGenrePanel");
    	    	cardLayout.show(cardPanel, "UpdateGenrePanel");
    	    }
        });
    }
    
    public void refreshGesnresTable() {
    	loadGenresIntoTable();
    }

    public void loadGenresIntoTable() {
        tableModel.setRowCount(0);
        genresList.clear();
        List<Genres> updatedGenresList = genresDAO.selectAllGenres();
        if(loadGenresList == false) {
        	if (updatedGenresList != null) {
        		updatedGenresList.sort(Comparator.comparingInt(Genres::getId));
                genresList.addAll(updatedGenresList);
                if (!updatedGenresList.isEmpty()) {
                    SwingUtilities.invokeLater(() -> {
                        for (Genres genres : genresList) {
                            tableModel.addRow(new Object[]{
                                    genres.getId(),
                                    genres.getName()
                            });
                        }
                    });
                    loadGenresList = true;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao carregar dados do Banco de dados.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    
}
