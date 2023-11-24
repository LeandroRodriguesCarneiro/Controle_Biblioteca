package Screens.Menu;

//-*- coding: utf-8 -*-

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import Screens.ConfigPanel.Styles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class MainPanel extends JPanel {
    private static final long serialVersionUID = -3228624175687997196L;

    public MainPanel() {
        setBackground(Styles.BACKGROUND_COLOR);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Menu", SwingConstants.CENTER);
        Styles.styleTitleFont(titleLabel);

        JTextArea welcomeMessage = new JTextArea(
            "Biblioteca Universitária!\n\n" +
            "Selecione uma opção no menu superior para começar."
        );
        welcomeMessage.setWrapStyleWord(true);
        welcomeMessage.setLineWrap(true);
        welcomeMessage.setOpaque(false);
        welcomeMessage.setEditable(false);
        welcomeMessage.setFocusable(false);
        welcomeMessage.setMargin(new Insets(10, 50, 10, 50));
        Styles.styleFont(welcomeMessage);
        
        setLayout(new BorderLayout());
        add(titleLabel, BorderLayout.NORTH);
        add(welcomeMessage, BorderLayout.CENTER);
    }
}

