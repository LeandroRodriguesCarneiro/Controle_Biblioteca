package Screens.CRUDStudent;
//-*- coding: utf-8 -*-
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import Screens.ConfigPanel.Styles;
import Student.StudentDAO;

public class AddStudentPanel extends JPanel{
	private static final long serialVersionUID = -1723482129844832445L;
    private JTextField txtName, txtnumberRegistration;
    private JButton btnAdd;
    private JButton btnBack;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private StudentPanel StudentPanel;
    private JLabel lblBooks,lblName, lblRegisterNumber;
    public AddStudentPanel(CardLayout cardLayout, JPanel cardPanel, StudentPanel StudentPanel) {
    	this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.StudentPanel = StudentPanel;
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
        add(txtName);

        lblRegisterNumber = new JLabel("Número de Matricula:");
        Styles.styleFont(lblRegisterNumber);
        lblRegisterNumber.setBounds(250, 80, 150, 25);
        add(lblRegisterNumber);

        txtnumberRegistration = new JTextField();
        txtnumberRegistration.setBounds(790, 80, 90, 25);
        add(txtnumberRegistration);

        btnAdd = new JButton("Adicionar Aluno");
        btnAdd.setBounds(250, 115, 255, 25);
        Styles.styleButton(btnAdd);
        btnAdd.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				String name = null;
				Long numberRegistration = null;
				try {
				    if (txtName.getText().trim().isEmpty()) {
				        JOptionPane.showMessageDialog(null, "Por favor, preencha o nome.");
				        txtName.requestFocus();
				        return;
				    }

				    if (txtnumberRegistration.getText().trim().isEmpty()) {
				        JOptionPane.showMessageDialog(null, "Por favor, preencha o número de matricula.");
				        txtnumberRegistration.requestFocus();
				        return;
				    } else if (txtnumberRegistration.getText().trim().length() != 10) {
				        JOptionPane.showMessageDialog(null, "Por favor, o número de matricula precisa ter 10 dígitos.");
				        txtnumberRegistration.setText("");
				        txtnumberRegistration.requestFocus();
				        return;
				    }
				   
				    name = String.valueOf(txtName.getText());
				    numberRegistration = Long.parseLong(txtnumberRegistration.getText());
				} catch (NumberFormatException ex) {
				    JOptionPane.showMessageDialog(null, "Certifique-se de inserir números válidos para número de matricula.");
				    txtnumberRegistration.setText("");
			        txtnumberRegistration.requestFocus();
				    return;
				}
				try {
					StudentDAO StudentDAO = new StudentDAO();
					StudentDAO.insertStudent(name,numberRegistration);
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
				txtName.setText("");
				txtnumberRegistration.setText("");
            }
            
        });
        add(btnAdd);
        
        btnBack = new JButton("Voltar");
        Styles.styleButton(btnBack);
        btnBack.setBounds(790, 125, 89, 25);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	StudentPanel.refreshStudentTable();
                cardLayout.show(cardPanel, "StudentPanel");
            }
        });
        add(btnBack);
        
        
    }
}
