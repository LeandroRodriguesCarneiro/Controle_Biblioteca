package Screens;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Delete {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Delete window = new Delete();
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
	public Delete() {
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
		
		JLabel lblDeletarLivro = new JLabel("Deletar Livro");
		lblDeletarLivro.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeletarLivro.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 33));
		lblDeletarLivro.setBounds(104, 11, 224, 42);
		frame.getContentPane().add(lblDeletarLivro);
		
		JLabel lblIsbn = new JLabel("ISBN");
		lblIsbn.setFont(new Font("Tw Cen MT", Font.PLAIN, 30));
		lblIsbn.setHorizontalAlignment(SwingConstants.CENTER);
		lblIsbn.setBounds(145, 83, 135, 29);
		frame.getContentPane().add(lblIsbn);
		
		textField = new JTextField();
		textField.setBounds(104, 123, 224, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnConcluir = new JButton("Concluir");
		btnConcluir.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 20));
		btnConcluir.setBounds(145, 196, 147, 31);
		frame.getContentPane().add(btnConcluir);
	}

}
