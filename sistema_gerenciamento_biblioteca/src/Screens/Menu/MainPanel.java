package Screens.Menu;

//-*- coding: utf-8 -*-

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;

public class MainPanel extends JPanel {
    private Image backgroundImage;
    private static final long serialVersionUID = -3228624175687997196L;

    public MainPanel() {
        backgroundImage = new ImageIcon("src/Screens/ConfigPanel/images/system.jpg").getImage();

        JLabel titleLabel = new JLabel("Menu", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JTextArea welcomeMessage = new JTextArea(
                "Biblioteca Universitária!\n\n" +
                        "Selecione uma opção no menu superior para começar."
        );
        welcomeMessage.setFont(new Font("Arial", Font.PLAIN, 16));
        welcomeMessage.setWrapStyleWord(true);
        welcomeMessage.setLineWrap(true);
        welcomeMessage.setOpaque(false);
        welcomeMessage.setEditable(false);
        welcomeMessage.setFocusable(false);
        welcomeMessage.setMargin(new Insets(10, 50, 10, 50));

        setLayout(new BorderLayout());
        add(titleLabel, BorderLayout.NORTH);
        add(welcomeMessage, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 95, getWidth(), getHeight(), this);
    }
}
