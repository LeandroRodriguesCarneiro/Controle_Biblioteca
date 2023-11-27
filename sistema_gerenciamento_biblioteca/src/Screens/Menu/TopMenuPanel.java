package Screens.Menu;

//-*- coding: utf-8 -*-

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import Screens.ConfigPanel.Styles;

public class TopMenuPanel extends JPanel {

    private static final long serialVersionUID = -1666701738391775114L;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public TopMenuPanel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        setBackground(new Color(240, 240, 240));
        setLayout(new FlowLayout(FlowLayout.LEFT));
        
        addButton("Tela Principal", "MainPanel", null);
        addButton("Livros", "BookPanel", null);
        addButton("Alunos", "StudentPanel", null); 
        addButton("Emprestimo", "LoanPanel", null);
        addButton("Devolução", "ReturnBookPanel", null);
        addButton("Relatório", "ReportsPanel", null);
    }

    private void addButton(String buttonText, String panelName, Runnable additionalAction) {
        JButton button = new JButton(buttonText);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getMinimumSize().height));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (additionalAction != null) {
                    additionalAction.run();
                }
                
                cardLayout.show(cardPanel, panelName);

                if (panelName.equals("ReturnBookPanel")) {
                    if (cardPanel.getComponentCount() > 0) {
                        for (Component comp : cardPanel.getComponents()) {
                            if (comp instanceof Screens.Functionalities.ReturnBookPanel) {
                                Screens.Functionalities.ReturnBookPanel returnBookPanel = (Screens.Functionalities.ReturnBookPanel) comp;
                                returnBookPanel.setInvisible();
                            }
                            if (comp instanceof Screens.CRUDStudent.StudentPanel) {
                            	Screens.CRUDStudent.StudentPanel studentPanel = (Screens.CRUDStudent.StudentPanel) comp;
                            	studentPanel.refreshStudentTable();
                            }
                            if (comp instanceof Screens.Functionalities.LoanPanel) {
                                Screens.Functionalities.LoanPanel LoanPanel = (Screens.Functionalities.LoanPanel) comp;
                                LoanPanel.setInvisible();
                            }
                            if (comp instanceof Screens.CRUDBook.BookPanel) {
                                Screens.CRUDBook.BookPanel BookPanel = (Screens.CRUDBook.BookPanel) comp;
                                BookPanel.refreshBookTable();
                            }
                            if (comp instanceof Screens.CRUDStudent.StudentPanel) {
                                Screens.CRUDStudent.StudentPanel StudentPanel = (Screens.CRUDStudent.StudentPanel) comp;
                                StudentPanel.refreshStudentTable();
                            }
                        }
                    }
                }
            }
        });
        Styles.styleButtonMenu(button);
        add(button);
    }

}