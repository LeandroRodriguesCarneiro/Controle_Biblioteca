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

import java.lang.Math;

import Student.StudentDAO;
import Student.Student;

public class PayDebitsPanel extends JPanel{
	 private static final long serialVersionUID = -1723482129844832445L;
	    private JTextField txtPay;
	    private JLabel lblStudent;
	    private JButton btnPay;
	    private DefaultTableModel tableModel;
	    private JButton btnBack;
	    private CardLayout cardLayout;
	    private JPanel cardPanel;
	    private StudentPanel StudentPanel;
	    private Student student;

	    public PayDebitsPanel(DefaultTableModel tableModel, CardLayout cardLayout,
	            JPanel cardPanel, StudentPanel StudentPanel, Student student) {
	        
	    	this.tableModel = tableModel;
	        this.cardLayout = cardLayout;
	        this.cardPanel = cardPanel;
	        this.StudentPanel = StudentPanel;
	        this.student = student;
	    	lblStudent = new JLabel("Inserir Aluno");
	        lblStudent.setFont(new Font("Arial", Font.BOLD, 30));
	        lblStudent.setBounds(20, 10, 400, 30);
	        add(lblStudent);
	        
	        setLayout(null);

	        JLabel lblTtitle = new JLabel("Aluno: "+student.getName());
	        lblTtitle.setBounds(10, 45, 400, 25);
	        add(lblTtitle);
	        
	        JLabel lblPay = new JLabel("O aluno deve: "+ (student.getDebits() * (-1) +" Informe o pagamento" ));
	        lblPay.setBounds(10, 80, 300, 25);
	        add(lblPay);

	        txtPay = new JTextField();
	        txtPay.setBounds(585, 80, 35, 25);
	        add(txtPay);
	        
	        JLabel lblReturnPay = new JLabel();
	        lblReturnPay.setBounds(10,115,400,25);
	        add(lblReturnPay);
	        
	        btnPay = new JButton("Pagar");
	        btnPay.setBounds(10, 500, 255, 25);
	        btnPay.addActionListener(new ActionListener() {
				@Override
	            public void actionPerformed(ActionEvent e) {
					Float pay = null;
					try {
					   if(txtPay.getText().isEmpty()) {
						   JOptionPane.showMessageDialog(null, "Por favor, preencha o valor do pagamento do aluno");
					        return;
					   }
					    pay = Float.parseFloat(txtPay.getText());
					    if(pay<=0) {
					    	JOptionPane.showMessageDialog(null, "Por favor, Digite um valor valido");
					        return;
					    }
					} catch (NumberFormatException ex) {
					    JOptionPane.showMessageDialog(null, "Certifique-se de inserir números válidos para numero de matricula.");
						
					}
					try {
						StudentDAO studentDAO = new StudentDAO();
						if (pay >= (student.getDebits()*(-1))) {
			                studentDAO.payDebits(student.getId(), (student.getDebits() * (-1)));
			                lblReturnPay.setText("Troco: " + Math.round((pay + student.getDebits()) * 100)/100);
			     
			            } else {
			                studentDAO.payDebits(student.getId(), pay);
			                lblReturnPay.setText("O pagamento não é suficiente para quitar a dívida. A dívida é de: " + ((student.getDebits()*(-1)) - pay));
			            }
						
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, "Falha ao processar o pagamento");
						
					}
	            }
	            
	        }); 
	        add(btnPay);

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
