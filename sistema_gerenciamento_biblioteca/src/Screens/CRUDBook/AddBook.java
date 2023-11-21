package Screens.CRUDBook;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class AddBook {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddBook window = new AddBook();
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
	public AddBook() {
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
		
		JLabel lblNewLabel = new JLabel("Adicionar Livro\r\n");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 33));
		lblNewLabel.setBounds(73, -2, 301, 52);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("ISBN\r\n");
		lblNewLabel_1.setFont(new Font("Tw Cen MT", Font.BOLD, 17));
		lblNewLabel_1.setBounds(98, 61, 53, 23);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton ConcluirOne = new JButton("Concluir");
		ConcluirOne.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 20));
		ConcluirOne.setBounds(144, 219, 147, 31);
		frame.getContentPane().add(ConcluirOne);
		
		JLabel lblEditora = new JLabel("Editora");
		lblEditora.setFont(new Font("Tw Cen MT", Font.BOLD, 17));
		lblEditora.setBounds(86, 111, 78, 14);
		frame.getContentPane().add(lblEditora);
		
		JLabel lblTitulo = new JLabel("Titulo");
		lblTitulo.setFont(new Font("Tw Cen MT", Font.BOLD, 17));
		lblTitulo.setBounds(306, 61, 68, 14);
		frame.getContentPane().add(lblTitulo);
		
		JLabel lblAutor = new JLabel("Autor");
		lblAutor.setFont(new Font("Tw Cen MT", Font.BOLD, 17));
		lblAutor.setBounds(300, 167, 63, 14);
		frame.getContentPane().add(lblAutor);
		
		JLabel lblGnero = new JLabel("Gênero");
		lblGnero.setFont(new Font("Tw Cen MT", Font.BOLD, 17));
		lblGnero.setBounds(86, 167, 78, 14);
		frame.getContentPane().add(lblGnero);
		
		JLabel lblAnoDePublicao = new JLabel("Ano de publicação");
		lblAnoDePublicao.setFont(new Font("Tw Cen MT", Font.BOLD, 17));
		lblAnoDePublicao.setBounds(270, 107, 169, 22);
		frame.getContentPane().add(lblAnoDePublicao);
		
		textField = new JTextField();
		textField.setBounds(260, 85, 132, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(260, 136, 132, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(260, 188, 132, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(53, 85, 132, 20);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(53, 136, 132, 20);
		frame.getContentPane().add(textField_4);
		textField_4.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.setBounds(53, 183, 132, 20);
		frame.getContentPane().add(textField_5);
		textField_5.setColumns(10);
	}
}
