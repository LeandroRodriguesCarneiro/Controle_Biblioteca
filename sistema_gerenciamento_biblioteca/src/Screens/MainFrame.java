package Screens;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JButton;

import Screens.Menu.MainPanel;
import Screens.Menu.SidebarPanel;
import Screens.CRUDBook.BookPanel;
import Screens.CRUDStudent.StudentPanel;
public class MainFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6340235651217630471L;
	private CardLayout cardLayout;
	private JPanel cardPanel; // Contém todos os painéis da aplicação
	//private Sistema sistema = new Sistema();
	//private ClienteManager clienteManager = new ClienteManager();
	//private ProdutoManager produtoManager = new ProdutoManager();

	public MainFrame() {
		// sistema.inicializarDadosDeTeste();
		setTitle("Sistema de Gestão de biblioteca");
		setSize(1200, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // Centraliza a janela

		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);

		MainPanel mainPanel = new MainPanel();
		cardPanel.add(mainPanel, "MainPanel");
		
		BookPanel bookPanel = new BookPanel(cardLayout, cardPanel);
		cardPanel.add(bookPanel, "BookPanel");
		
		StudentPanel StudentPanel = new StudentPanel(cardLayout, cardPanel);
	    cardPanel.add(StudentPanel, "StudentPanel");

//		ProductsPanel productsPanel = new ProductsPanel(cardLayout, cardPanel, sistema, produtoManager);
//		cardPanel.add(productsPanel, "ProductsPanel");
//
//		CustomersPanel customersPanel = new CustomersPanel(cardLayout, cardPanel, clienteManager);
//		cardPanel.add(customersPanel, "CustomersPanel");
//
//		SalesPanel salesPanel = new SalesPanel(sistema, cardLayout, cardPanel, produtoManager, clienteManager);
//		cardPanel.add(salesPanel, "SalesPanel");
//
//		PromotionsPanel promotionsPanel = new PromotionsPanel(productsPanel, produtoManager);
//		cardPanel.add(promotionsPanel, "PromotionsPanel");
//
//		ReportsPanel reportsPanel = new ReportsPanel(sistema, produtoManager);
//		cardPanel.add(reportsPanel, "ReportsPanel");
//
//		AddSupplierPanel addSupplierPanel = new AddSupplierPanel(sistema, cardLayout, cardPanel);
//		cardPanel.add(addSupplierPanel, "AddSupplierPanel");
//
//		AddCategoryPanel addCategoryPanel = new AddCategoryPanel(sistema, cardLayout, cardPanel);
//		cardPanel.add(addCategoryPanel, "AddCategoryPanel");
//
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
			// Instancia e mostra a interface gráfica
			MainFrame frame = new MainFrame();
			frame.setVisible(true);
		});
	}
}
