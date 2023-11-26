package Screens;

//-*- coding: utf-8 -*-

import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import Screens.Menu.MainPanel;
import Screens.Menu.TopMenuPanel;
import Screens.Reports.ReportsPanel;
import Screens.CRUDBook.BookPanel;
import Screens.CRUDStudent.StudentPanel;
import Screens.ConfigPanel.Styles;
import Screens.Functionalities.LoanPanel;
import Screens.Functionalities.ReturnBookPanel;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = -6340235651217630471L;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public MainFrame() {
        setTitle("Sistema de Gestão de Biblioteca");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        MainPanel mainPanel = new MainPanel();
        mainPanel.setBackground(Styles.BACKGROUND_COLOR);
        cardPanel.add(mainPanel, "MainPanel");

        BookPanel bookPanel = new BookPanel(cardLayout, cardPanel);
        bookPanel.setBackground(Styles.BACKGROUND_COLOR);
        cardPanel.add(bookPanel, "BookPanel");

        StudentPanel studentPanel = new StudentPanel(cardLayout, cardPanel);
        studentPanel.setBackground(Styles.BACKGROUND_COLOR);
        cardPanel.add(studentPanel, "StudentPanel");

        LoanPanel loanPanel = new LoanPanel(cardLayout, cardPanel);
        loanPanel.setBackground(Styles.BACKGROUND_COLOR);
        cardPanel.add(loanPanel, "LoanPanel");
        
        ReportsPanel reportsPanel = new ReportsPanel(cardLayout, cardPanel);
        studentPanel.setBackground(Styles.BACKGROUND_COLOR);
        cardPanel.add(reportsPanel, "ReportsPanel");

        ReturnBookPanel returnBookPanel = new ReturnBookPanel(cardLayout, cardPanel);
        returnBookPanel.setBackground(Styles.BACKGROUND_COLOR);
        cardPanel.add(returnBookPanel, "ReturnBookPanel");

        TopMenuPanel topMenuPanel = new TopMenuPanel(cardLayout, cardPanel);
        topMenuPanel.setBackground(Styles.BACKGROUND_COLOR);
        getContentPane().add(topMenuPanel, BorderLayout.NORTH); 
        add(cardPanel);
        cardLayout.show(cardPanel, "MainPanel");
    }

    public void showPanel(String panelName) {
        cardLayout.show(cardPanel, panelName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
