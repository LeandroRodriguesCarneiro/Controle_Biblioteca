package Screens.CRUDStudent;
//-*- coding: utf-8 -*-
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Student.StudentDAO;
import Student.Student;

public class UpdateStudentPanel extends JPanel{
	 private static final long serialVersionUID = -1723482129844832445L;
	    private JTextField txtStudent, txtNumberRegistration, txtBorrowedBooks, txtDebits;
	    private JLabel lblStudent;
	    private JButton btnAdd;
	    private DefaultTableModel tableModel;
	    private JButton btnBack;
	    private CardLayout cardLayout;
	    private JPanel cardPanel;
	    private StudentPanel StudentPanel;
	    private Student student;

	    public UpdateStudentPanel(DefaultTableModel tableModel, CardLayout cardLayout,
	            JPanel cardPanel, StudentPanel StudentPanel, Student student) {
	        
	    	this.tableModel = tableModel;
	        this.cardLayout = cardLayout;
	        this.cardPanel = cardPanel;
	        this.StudentPanel = StudentPanel;
	        this.student = student;
	    	lblStudent = new JLabel("Atualizar Aluno");
	        lblStudent.setFont(new Font("Arial", Font.BOLD, 30));
	        lblStudent.setBounds(20, 10, 400, 30);
	        add(lblStudent);
	        
	        setLayout(null);

	        JLabel lblTtitle = new JLabel("Aluno:");
	        lblTtitle.setBounds(10, 45, 80, 25);
	        add(lblTtitle);

	        txtStudent = new JTextField();
	        txtStudent.setBounds(120, 45, 500, 25);
	        txtStudent.setText(student.getName());
	        add(txtStudent);

	        JLabel lblnumberRegistration = new JLabel("Matricula:");
	        lblnumberRegistration.setBounds(10, 80, 80, 25);
	        add(lblnumberRegistration);

	        txtNumberRegistration = new JTextField();
	        txtNumberRegistration.setBounds(530, 80, 90, 25);
	        txtNumberRegistration.setText(String.valueOf(student.getNumberRegistration()));
	        add(txtNumberRegistration);	        
	        
	        JLabel lblBorrowedBooks = new JLabel("Quantidade de Livros Emprestados:");
	        lblBorrowedBooks.setBounds(10, 115, 220, 25);
	        add(lblBorrowedBooks);
	        
	        txtBorrowedBooks = new JTextField();
	        txtBorrowedBooks.setBounds(585, 115, 35, 25);
	        txtBorrowedBooks.setText(String.valueOf(student.getBorrowedBooks()));
	        add(txtBorrowedBooks);
	        
	        JLabel lblDebits = new JLabel("Dividas:");
	        lblDebits.setBounds(10, 150, 80, 25);
	        add(lblDebits);

	        txtDebits = new JTextField();
	        txtDebits.setBounds(585, 150, 35, 25);
	        txtDebits.setText(String.valueOf(student.getDebits() < 0 ? student.getDebits() * (-1) : student.getDebits()));
	        add(txtDebits);
	        
	        btnAdd = new JButton("Salvar");
	        btnAdd.setBounds(10, 500, 255, 25);
	        btnAdd.addActionListener(new ActionListener() {
				@Override
	            public void actionPerformed(ActionEvent e) {
					String name = null;
					Long numberRegistration = null;
					Integer  borrowedBooks = null;
					Float debits = null;
					try {
					    if (txtStudent.getText().isEmpty()) {
					        JOptionPane.showMessageDialog(null, "Por favor, preencha o nome.");
					        return;
					    }

					    if (txtNumberRegistration.getText().isEmpty()) {
					        JOptionPane.showMessageDialog(null, "Por favor, preencha o numero de matricula.");
					        return;
					    } else if (txtNumberRegistration.getText().length() != 10) {
					        JOptionPane.showMessageDialog(null, "Por favor, o numero de matricula precisa ter 10 dígitos.");
					        return;
					    }
					   if(txtBorrowedBooks.getText().isEmpty()) {
						   JOptionPane.showMessageDialog(null, "Por favor, preencha a quantidade de livros emprestados.");
					        return;
					   }
					   if(txtDebits.getText().isEmpty()) {
						   JOptionPane.showMessageDialog(null, "Por favor, preencha a divida do aluno");
					        return;
					   }
					   
					    name = String.valueOf(txtStudent.getText());
					    numberRegistration = Long.parseLong(txtNumberRegistration.getText());
					    borrowedBooks = Integer.parseInt(txtBorrowedBooks.getText());
					    debits = Float.parseFloat(txtDebits.getText()) * (-1);
					} catch (NumberFormatException ex) {
					    JOptionPane.showMessageDialog(null, "Certifique-se de inserir números válidos para numero de matricula.");
						
					}
					try {
						StudentDAO StudentDAO = new StudentDAO();
						StudentDAO.updateStudent(student.getId(),name, numberRegistration, borrowedBooks, debits);
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, "Este Numero de matricula já está em uso. Por favor, insira um Numero de matricula diferente.");
						
					}
	            }
	            
	        }); 
	        add(btnAdd);

	        btnBack = new JButton("Voltar");
	        btnBack.setBounds(351, 500, 89, 25);
	        btnBack.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	StudentPanel.refreshStudentTable();
	                cardLayout.show(cardPanel, "StudentPanel");
	            }
	        });
	        add(btnBack);
	    }
}
