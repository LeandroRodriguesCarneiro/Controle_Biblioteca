package Screens;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblBiblioteca = new JLabel("Biblioteca");
		lblBiblioteca.setHorizontalAlignment(SwingConstants.CENTER);
		lblBiblioteca.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 35));
		lblBiblioteca.setBounds(140, 11, 160, 44);
		frame.getContentPane().add(lblBiblioteca);
		
		JButton ButAddBook = new JButton("Adicionar Livro");
		ButAddBook.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 20));
		ButAddBook.setBounds(49, 66, 147, 31);
		frame.getContentPane().add(ButAddBook);
		
		JButton ButPreview = new JButton("Visualizar");
		ButPreview.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 20));
		ButPreview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		ButPreview.setBounds(49, 116, 147, 31);
		frame.getContentPane().add(ButPreview);
		
		JButton ButAddStud = new JButton("Adicionar Aluno");
		ButAddStud.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 20));
		ButAddStud.setBounds(242, 66, 147, 31);
		frame.getContentPane().add(ButAddStud);
		
		JButton ButUpdate = new JButton("Atualizar");
		ButUpdate.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 20));
		ButUpdate.setBounds(242, 116, 147, 31);
		frame.getContentPane().add(ButUpdate);
		
		JButton ButDelete = new JButton("Deletar Livro");
		ButDelete.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 20));
		ButDelete.setBounds(49, 158, 147, 31);
		frame.getContentPane().add(ButDelete);
		
		JButton ButReport = new JButton("Relatório");
		ButReport.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 20));
		ButReport.setBounds(242, 158, 147, 31);
		frame.getContentPane().add(ButReport);
		
		JButton ButDevo = new JButton("Devolução ");
		ButDevo.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 20));
		ButDevo.setBounds(242, 206, 147, 31);
		frame.getContentPane().add(ButDevo);
		
		JButton ButLoan = new JButton("Empréstimo");
		ButLoan.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 20));
		ButLoan.setBounds(49, 206, 147, 31);
		frame.getContentPane().add(ButLoan);
	}
}
