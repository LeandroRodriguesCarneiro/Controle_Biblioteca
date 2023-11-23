package Screens;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import Screens.Menu.MainPanel;
import Screens.Menu.SidebarPanel;
import Screens.CRUDBook.BookPanel;
import Screens.CRUDStudent.StudentPanel;
import Screens.Functionalities.LoanPanel;
import Screens.Functionalities.ReturnBookPanel;

public class MainFrame extends JFrame{
	private static final long serialVersionUID = -6340235651217630471L;
	private CardLayout cardLayout;
	private JPanel cardPanel; 

	public MainFrame() {
		setTitle("Sistema de Gestão de biblioteca");
		setSize(1200, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); 

		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);

		MainPanel mainPanel = new MainPanel();
		cardPanel.add(mainPanel, "MainPanel");
		
		BookPanel bookPanel = new BookPanel(cardLayout, cardPanel);
		cardPanel.add(bookPanel, "BookPanel");
		
		StudentPanel StudentPanel = new StudentPanel(cardLayout, cardPanel);
	    cardPanel.add(StudentPanel, "StudentPanel");
	    
	    LoanPanel LoanPanel = new LoanPanel(cardLayout, cardPanel);
	    cardPanel.add(LoanPanel, "LoanPanel");
	    
	    ReturnBookPanel ReturnBookPanel = new ReturnBookPanel(cardLayout, cardPanel);
	    cardPanel.add(ReturnBookPanel, "ReturnBookPanel");

		SidebarPanel sidebarPanel = new SidebarPanel(cardLayout, cardPanel);
		getContentPane().add(sidebarPanel, BorderLayout.WEST);
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
