package Screens;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Devolution {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Devolution window = new Devolution();
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
	public Devolution() {
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
		
		JLabel lblNmeroDaMatrcula = new JLabel("Número da Matrícula");
		lblNmeroDaMatrcula.setHorizontalAlignment(SwingConstants.CENTER);
		lblNmeroDaMatrcula.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 25));
		lblNmeroDaMatrcula.setBounds(143, 58, 170, 21);
		frame.getContentPane().add(lblNmeroDaMatrcula);
		
		JLabel lblIsbn = new JLabel("ISBN");
		lblIsbn.setHorizontalAlignment(SwingConstants.CENTER);
		lblIsbn.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 25));
		lblIsbn.setBounds(198, 124, 53, 21);
		frame.getContentPane().add(lblIsbn);
		
		JLabel lblDevoluo = new JLabel("Devolução ");
		lblDevoluo.setHorizontalAlignment(SwingConstants.CENTER);
		lblDevoluo.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 33));
		lblDevoluo.setBounds(133, 11, 179, 36);
		frame.getContentPane().add(lblDevoluo);
		
		textField = new JTextField();
		textField.setBounds(143, 93, 170, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(143, 156, 170, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JButton ConcluirFour = new JButton("Concluir");
		ConcluirFour.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 20));
		ConcluirFour.setBounds(153, 205, 147, 31);
		frame.getContentPane().add(ConcluirFour);
	}

}
