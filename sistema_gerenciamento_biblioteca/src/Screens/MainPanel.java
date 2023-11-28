package Screens;
//-*- coding: utf-8 -*-
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;


public class MainPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3228624175687997196L;

	public MainPanel() {
        setBackground(new Color(245, 245, 245)); 
        setLayout(new BorderLayout()); 

        JLabel titleLabel = new JLabel("Menu", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Yu Gothic UI", Font.BOLD, 36));
        titleLabel.setForeground(new Color(50, 50, 50)); 
        
        JTextArea welcomeMessage = new JTextArea(
            "Biblioteca Universitária\n\n" + 
            "Selecione uma opção no painel lateral para começar."
        );
        welcomeMessage.setFont(new Font("Yu Gothic UI", Font.PLAIN, 20));
        welcomeMessage.setWrapStyleWord(true);
        welcomeMessage.setLineWrap(true);
        welcomeMessage.setOpaque(false);
        welcomeMessage.setEditable(false);
        welcomeMessage.setFocusable(false);
        welcomeMessage.setMargin(new Insets(10, 50, 10, 50));

        add(titleLabel, BorderLayout.NORTH);
        add(welcomeMessage, BorderLayout.CENTER);
    }
}
