package Screens.CRUDStudent;
//-*- coding: utf-8 -*-
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import Screens.ConfigPanel.Styles;
import java.text.DecimalFormat;
import Student.StudentDAO;
import Student.Student;

public class PayDebitsPanel extends JPanel{
	 private static final long serialVersionUID = -1723482129844832445L;
	    private JFormattedTextField txtPay;
	    private JLabel lblBooks, lblStudent,lblPay, lblReturnPay;
	    private JButton btnPay;
	    private JButton btnBack;
	    private CardLayout cardLayout;
	    private JPanel cardPanel;
	    private StudentPanel StudentPanel;
	    private Student student;
	    private StudentDAO studentDAO = new StudentDAO();

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
	        lblStudent.setBounds(250, 45, 400, 25);
	        Styles.styleFont(lblStudent);
	        add(lblStudent);
	        
	        lblPay = new JLabel("O aluno deve: "+ (Screens.CRUDStudent.StudentPanel.formatDecimal(student.getDebits() * (-1)) +" Informe o pagamento" ));
	        lblPay.setBounds(250, 80, 300, 25);
	        Styles.styleFont(lblPay);
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
	        
	        lblReturnPay = new JLabel();
	        lblReturnPay.setBounds(250,115,600,25);
	        Styles.styleFont(lblReturnPay);
	        add(lblReturnPay);
	        
	        btnPay = new JButton("Pagar");
	        btnPay.setBounds(250, 160, 255, 25);
	        Styles.styleButton(btnPay);
	        btnPay.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
					Float pay = null;
					try {
					   if(txtPay.getText().isEmpty()) {
						   JOptionPane.showMessageDialog(null, "Por favor, preencha o valor do pagamento do aluno");
					        return;
					   }
					    pay = Screens.CRUDStudent.StudentPanel.convertStringDecimal(txtPay.getText());
					    if(pay<=0) {
					    	JOptionPane.showMessageDialog(null, "Por favor, Digite um valor valido");
					        return;
					    }
					} catch (NumberFormatException ex) {
					    JOptionPane.showMessageDialog(null, "Certifique-se de inserir números válidos para pagamento.");
						
					}
					processesPay(pay);
	            }
	            
	        }); 
	        add(btnPay);

	        btnBack = new JButton("Voltar");
	        btnBack.setBounds(530, 160, 89, 25);
	        Styles.styleButton(btnBack);
	        btnBack.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	StudentPanel.refreshStudentTable();
	                cardLayout.show(cardPanel, "StudentPanel");
	            }
	        });
	        add(btnBack);
	    }
	    
	    private void processesPay(float pay) {
	    	try {
				if (pay >= (student.getDebits()*(-1))) {
	                studentDAO.payDebits(student.getId(), (student.getDebits() * (-1)));
	                if(pay > (student.getDebits()*(-1))){
	                	lblReturnPay.setText("Troco: " + (pay + student.getDebits()));
	                }
	                JOptionPane.showMessageDialog(null, "Pagamento realizado com sucesso.");
	                deactivateBtnPay();
	            } else {
	                studentDAO.payDebits(student.getId(), pay);
	                JOptionPane.showMessageDialog(null, "O pagamento não é suficiente para quitar a dívida.");
	            	this.student = studentDAO.selectStudentByNumerRegistration(student.getNumberRegistration(), true).get(0);
	    	    	lblPay.setText("O aluno deve: "+ (Screens.CRUDStudent.StudentPanel.formatDecimal(student.getDebits() * (-1)) +" Informe o pagamento" ));
	            }
			}catch(Exception ex){
				JOptionPane.showMessageDialog(null, "Falha ao processar o pagamento");
				
			}	    	
	    }
	    
	    private void deactivateBtnPay() {
	    	btnPay.setEnabled(false);
	    }
	    
	    public void activateBtnPay() {
	    	btnPay.setEnabled(true);
	    }
}		

