package Screens;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextField;

public class Loan {

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
					Loan window = new Loan();
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
	public Loan() {
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
		
		JLabel lblNewLabel = new JLabel("Empréstimo");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 33));
		lblNewLabel.setBounds(112, 0, 216, 36);
		frame.getContentPane().add(lblNewLabel);
		
		JButton ConcluirThree = new JButton("Concluir");
		ConcluirThree.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 20));
		ConcluirThree.setBounds(150, 219, 147, 31);
		frame.getContentPane().add(ConcluirThree);
		
		JLabel lblNmeroDaMatrcula = new JLabel("Número da matrícula");
		lblNmeroDaMatrcula.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 25));
		lblNmeroDaMatrcula.setBounds(150, 58, 163, 27);
		frame.getContentPane().add(lblNmeroDaMatrcula);
		
		JLabel lblIsbn = new JLabel("ISBN");
		lblIsbn.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 25));
		lblIsbn.setBounds(198, 131, 46, 22);
		frame.getContentPane().add(lblIsbn);
		
		textField = new JTextField();
		textField.setBounds(135, 97, 178, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(135, 164, 178, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
	}

}
