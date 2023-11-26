package Screens.Reports;

//-*- coding: utf-8 -*-

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Screens.ConfigPanel.Styles;

public class ReportsPanel extends JPanel {
	private static final long serialVersionUID = 7342752059148729042L;
	private CardLayout cardLayout;
	private JButton backButton;
    private JPanel cardPanel, topPanel,  reportsCards, reportPanel;
    private JLabel lblTitlePage;
    private JComboBox<String> comboBox;
    public ReportsPanel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        setLayout(new BorderLayout());

        topPanel = new JPanel();
        topPanel.setLayout(null);
        topPanel.setPreferredSize(new Dimension(1200, 100));

        lblTitlePage = new JLabel("Relatórios");
        Styles.styleTitleFont(lblTitlePage);
        lblTitlePage.setBounds(142, 10, 180, 30);
        
        String[] reportTypes = {
        		"Livros por gênero",
        	    "Livros mais retirados",
        	    "Livros fora de estoque",
        	    "Livros publicados num Intervalo de Tempo",
        	    "Editoras com mais títulos",
        	    "Livros de um autor específico",
        	    "Alunos com Débitos",
        	    "Alunos com o máximo de livros emprestados"
        	    };
        
        comboBox = new JComboBox<>(reportTypes);
        comboBox.setSelectedIndex(-1);
        comboBox.setBounds(460, 45, 320, 30);
        Styles.styleComboBox(comboBox);
        add(comboBox);
        
        backButton = new JButton("Voltar");
        backButton.setBounds(992, 10, 80, 30);
        Styles.styleButton(backButton);
        add(backButton);
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "MainPanel"));
        
        topPanel.add(lblTitlePage);
        topPanel.add(comboBox);
        topPanel.add(backButton);
        
        reportsCards = new JPanel(cardLayout);
        JPanel emptyPanel = new JPanel();
        reportsCards.add(emptyPanel, "Livros por gênero");
        reportsCards.add(createReportPanel("Livros por gênero"), "Livros por gênero");
        reportsCards.add(createReportPanel("Livros mais retirados"), "Livros mais retirados");
        reportsCards.add(createReportPanel("Livros fora de estoque"),"Livros fora de estoque");
        reportsCards.add(createReportPanel("Livros publicados num Intervalo de Tempo"),"Livros publicados num Intervalo de Tempo");
        reportsCards.add(createReportPanel("Editoras com mais títulos"),"Editoras com mais títulos");
        reportsCards.add(createReportPanel("Livros de um autor específico"),"Livros de um autor específico");
        reportsCards.add(createReportPanel("Alunos com Débitos"),"Alunos com Débitos");
        reportsCards.add(createReportPanel("Alunos com o máximo de livros emprestados"),"Alunos com o máximo de livros emprestados");
        
        add(topPanel, BorderLayout.NORTH);
        add(reportsCards, BorderLayout.CENTER);

        comboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedReport = (String) comboBox.getSelectedItem();
                    cardLayout.show(reportsCards, selectedReport);
                }
            }
        });

    }

    private JPanel createReportPanel(String reportName) {
        reportPanel = new JPanel();
        if (reportName.equals("Livros por gênero")) {
            BooksByGenrePanel booksGenre = new BooksByGenrePanel();
            reportPanel = booksGenre.getPanel();
        }else if(reportName.equals("Livros mais retirados")) {
        	BooksTimesBorrowed booksTimesBrrowed = new BooksTimesBorrowed();
        	reportPanel = booksTimesBrrowed.getPanel();
        }else if(reportName.equals("Livros fora de estoque")) {
        	BooksOutStockBooks booksOutStockBooks = new BooksOutStockBooks();
        	reportPanel = booksOutStockBooks.getPanel();
        }else if(reportName.equals("Livros publicados num Intervalo de Tempo")) {
        	BooksPublishedPeriodTime booksPublishedPeriodTime = new BooksPublishedPeriodTime();
        	reportPanel = booksPublishedPeriodTime.getPanel();
        }else if(reportName.equals("Editoras com mais títulos")) {
        	PublisherByCountTitles PublisherByCountTitles = new PublisherByCountTitles();
        	reportPanel = PublisherByCountTitles.getPanel();
        }else if(reportName.equals("Livros de um autor específico")) {
        	BooksSpecificAuthor BooksSpecificAuthor= new BooksSpecificAuthor();
        	reportPanel = BooksSpecificAuthor.getPanel();
        }else if(reportName.equals("Alunos com Débitos")) {
        	StudentsWithDebits StudentsWithDebits =  new StudentsWithDebits();
        	reportPanel = StudentsWithDebits.getPanel();
        }else if(reportName.equals("Alunos com o máximo de livros emprestados")){
        	StudentsMaximumNumberBooksBorrowed StudentsMaximumNumberBooksBorrowed = new StudentsMaximumNumberBooksBorrowed();
        	reportPanel = StudentsMaximumNumberBooksBorrowed.getPanel();
        }else {
        	reportPanel = new JPanel();
        }

        return reportPanel;
    }
}