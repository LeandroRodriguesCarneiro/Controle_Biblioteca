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
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import Screens.ConfigPanel.Styles;

import java.lang.Math;
import java.text.DecimalFormat;

import Student.StudentDAO;
import Student.Student;

public class PayDebitsPanel extends JPanel{
	 private static final long serialVersionUID = -1723482129844832445L;
	    private JFormattedTextField txtPay;
	    private JLabel lblBooks, lblStudent,lblPay;
	    private JButton btnPay;
	    private JButton btnBack;
	    private CardLayout cardLayout;
	    private JPanel cardPanel;
	    private StudentPanel StudentPanel;
	    private Student student;

	    public PayDebitsPanel(CardLayout cardLayout, JPanel cardPanel, StudentPanel StudentPanel, Student student) {
	        
	        this.cardLayout = cardLayout;
	        this.cardPanel = cardPanel;
	        this.StudentPanel = StudentPanel;
	        this.student = student;
	    	
	        setLayout(null);
	        
	        lblBooks = new JLabel("Inserir Alunos");
	    	lblBooks.setBounds(250, 10, 400, 30);
	    	Styles.styleTitleFont(lblBooks);
	        add(lblBooks);
	        
	        lblStudent = new JLabel("Aluno: "+student.getName());
	        lblStudent.setBounds(10, 45, 400, 25);
	        Styles.styleFont(lblStudent);
	        add(lblStudent);
	        
	        lblPay = new JLabel("O aluno deve: "+ (student.getDebits() * (-1) +" Informe o pagamento" ));
	        lblPay.setBounds(10, 80, 300, 25);
	        add(lblPay);
	        
	        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
	        NumberFormatter numberFormatter = new NumberFormatter(decimalFormat);
	        numberFormatter.setValueClass(Double.class);
	        numberFormatter.setMinimum(0.00);
	        numberFormatter.setMaximum(999.99);
	        numberFormatter.setAllowsInvalid(false);
	        numberFormatter.setCommitsOnValidEdit(true);
	        
	        txtPay = new JFormattedTextField();
	        txtPay.setFormatterFactory(new DefaultFormatterFactory(numberFormatter));
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
