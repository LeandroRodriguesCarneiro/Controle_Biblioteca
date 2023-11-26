package Screens.CRUDStudent;
//-*- coding: utf-8 -*-
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Screens.ConfigPanel.Styles;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import Student.StudentDAO;
import Student.Student;

public class UpdateStudentPanel extends JPanel{
	 private static final long serialVersionUID = -1723482129844832445L;
	    private JLabel lblBooks,lblName, lblRegisterNumber, lblBorrowedBooks, lblDebits;
	    private JTextField txtName, txtRegisterNumber,txtBorrowedBooks, txtDebits;
	    private JButton btnAdd;
	    private JButton btnBack;
	    private CardLayout cardLayout;
	    private JPanel cardPanel;
	    private StudentPanel StudentPanel;
	    private Student student;

	    public UpdateStudentPanel(CardLayout cardLayout,JPanel cardPanel, StudentPanel StudentPanel, Student student) {
	        this.cardLayout = cardLayout;
	        this.cardPanel = cardPanel;
	        this.StudentPanel = StudentPanel;
	        this.student = student;
	        
	        setLayout(null);
	        
	        lblBooks = new JLabel("Inserir Alunos");
	    	lblBooks.setBounds(250, 10, 400, 30);
	    	Styles.styleTitleFont(lblBooks);
	        add(lblBooks);

	        lblName = new JLabel("Nome:");
	        lblName.setBounds(250, 45, 80, 25);
	        Styles.styleFont(lblName);
	        add(lblName);

	        txtName = new JTextField();
	        txtName.setBounds(380, 45, 500, 25);
	        txtName.setText(student.getName());
	        add(txtName);

	        lblRegisterNumber = new JLabel("Número de Matricula:");
	        Styles.styleFont(lblRegisterNumber);
	        lblRegisterNumber.setBounds(250, 80, 150, 25);
	        add(lblRegisterNumber);

	        txtRegisterNumber = new JTextField();
	        txtRegisterNumber.setBounds(790, 80, 90, 25);
	        txtRegisterNumber.setText(String.valueOf(student.getNumberRegistration()));
	        add(txtRegisterNumber);
	        
	        lblBorrowedBooks = new JLabel("Quantidade de Livros Emprestados:");
	        lblBorrowedBooks.setBounds(250, 115, 250, 25);
	        Styles.styleFont(lblBorrowedBooks);
	        add(lblBorrowedBooks);
	        
	        txtBorrowedBooks = new JTextField();
	        txtBorrowedBooks.setBounds(845, 115, 35, 25);
	        txtBorrowedBooks.setText(String.valueOf(student.getBorrowedBooks()));
	        add(txtBorrowedBooks);
	        
	        lblDebits = new JLabel("Dividas:");
	        lblDebits.setBounds(250, 150, 80, 25);
	        Styles.styleFont(lblDebits);
	        add(lblDebits);

	        txtDebits = new JTextField();
	        txtDebits.setBounds(845, 150, 35, 25);
	        txtDebits.setText(String.valueOf(student.getDebits() < 0 ? student.getDebits() * (-1) : student.getDebits()));
	        add(txtDebits);
	        
	        btnAdd = new JButton("Salvar");
	        btnAdd.setBounds(250, 195, 255, 25);
	        Styles.styleButton(btnAdd);
	        btnAdd.addActionListener(new ActionListener() {
				@Override
	            public void actionPerformed(ActionEvent e) {
					String name = null;
					Long numberRegistration = null;
					Integer  borrowedBooks = null;
					Float debits = null;
					try {
					    if (txtName.getText().trim().isEmpty()) {
					        JOptionPane.showMessageDialog(null, "Por favor, preencha o nome.");
					        return;
					    }

					    if (txtRegisterNumber.getText().trim().isEmpty()) {
					        JOptionPane.showMessageDialog(null, "Por favor, preencha o numero de matricula.");
					        return;
					    } else if (txtRegisterNumber.getText().trim().length() != 10) {
					        JOptionPane.showMessageDialog(null, "Por favor, o numero de matricula precisa ter 10 dígitos.");
					        return;
					    }
					   if(txtBorrowedBooks.getText().trim().isEmpty()) {
						   JOptionPane.showMessageDialog(null, "Por favor, preencha a quantidade de livros emprestados.");
					        return;
					   }
					   if(txtDebits.getText().trim().isEmpty()) {
						   JOptionPane.showMessageDialog(null, "Por favor, preencha a divida do aluno");
					        return;
					   }
					   
					    name = String.valueOf(txtName.getText());
					    numberRegistration = Long.parseLong(txtRegisterNumber.getText());
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
	        Styles.styleButton(btnBack);
	        btnBack.setBounds(790, 195, 89, 25);
	        btnBack.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	StudentPanel.refreshStudentTable();
	                cardLayout.show(cardPanel, "StudentPanel");
	            }
	        });
	        add(btnBack);
	    }
}
