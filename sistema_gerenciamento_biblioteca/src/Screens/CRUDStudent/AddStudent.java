package Screens.CRUDStudent;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextField;

public class AddStudent {

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
					AddStudent window = new AddStudent();
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
	public AddStudent() {
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
		
		JLabel lblNewLabel = new JLabel("Adicionar Aluno");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 33));
		lblNewLabel.setBounds(61, 0, 328, 43);
		frame.getContentPane().add(lblNewLabel);
		
		JButton ConcluirTwo = new JButton("Concluir");
		ConcluirTwo.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 20));
		ConcluirTwo.setBounds(152, 219, 147, 31);
		frame.getContentPane().add(ConcluirTwo);
		
		JLabel lblNewLabel_1 = new JLabel("Número da Matrícula");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 25));
		lblNewLabel_1.setBounds(121, 54, 190, 21);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Nome ");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 25));
		lblNewLabel_2.setBounds(178, 126, 78, 21);
		frame.getContentPane().add(lblNewLabel_2);
		
		textField = new JTextField();
		textField.setBounds(131, 86, 180, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(131, 158, 180, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
	}

}
