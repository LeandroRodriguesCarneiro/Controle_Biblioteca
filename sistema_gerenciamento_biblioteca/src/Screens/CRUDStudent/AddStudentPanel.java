package Screens.CRUDStudent;

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

public class AddStudentPanel extends JPanel{
	 private static final long serialVersionUID = -1723482129844832445L;
	    private JTextField txtStudent, txtnumberRegistration;
	    private JLabel lblStudent;
	    private JButton btnAdd;
	    private DefaultTableModel tableModel;
	    private JButton btnBack;
	    private CardLayout cardLayout;
	    private JPanel cardPanel;
	    private StudentPanel StudentPanel;

	    public AddStudentPanel(DefaultTableModel tableModel, CardLayout cardLayout,
	            JPanel cardPanel, StudentPanel StudentPanel) {
	        
	    	lblStudent = new JLabel("Inserir Aluno");
	        lblStudent.setFont(new Font("Arial", Font.BOLD, 30));
	        lblStudent.setBounds(20, 10, 400, 30);
	        add(lblStudent);
	        
	        this.tableModel = tableModel;
	        this.cardLayout = cardLayout;
	        this.cardPanel = cardPanel;
	        this.StudentPanel = StudentPanel;
	        setLayout(null);

	        JLabel lblTtitle = new JLabel("Aluno:");
	        lblTtitle.setBounds(10, 45, 80, 25);
	        add(lblTtitle);

	        txtStudent = new JTextField();
	        txtStudent.setBounds(120, 45, 500, 25);
	        add(txtStudent);

	        JLabel lblnumberRegistration = new JLabel("Matricula:");
	        lblnumberRegistration.setBounds(10, 80, 80, 25);
	        add(lblnumberRegistration);

	        txtnumberRegistration = new JTextField();
	        txtnumberRegistration.setBounds(530, 80, 90, 25);
	        add(txtnumberRegistration);
	        
	        btnAdd = new JButton("Adicionar Aluno");
	        btnAdd.setBounds(10, 500, 255, 25);
	        btnAdd.addActionListener(new ActionListener() {
				@Override
	            public void actionPerformed(ActionEvent e) {
					String name = null;
					Long numberRegistration = null;
					try {
					    if (txtStudent.getText().isEmpty()) {
					        JOptionPane.showMessageDialog(null, "Por favor, preencha o nome.");
					        return;
					    }

					    if (txtnumberRegistration.getText().isEmpty()) {
					        JOptionPane.showMessageDialog(null, "Por favor, preencha o numero de matricula.");
					        return;
					    } else if (txtnumberRegistration.getText().length() != 10) {
					        JOptionPane.showMessageDialog(null, "Por favor, o numero de matricula precisa ter 10 dígitos.");
					        return;
					    }
					   
					    name = String.valueOf(txtStudent.getText());
					    numberRegistration = Long.parseLong(txtnumberRegistration.getText());
					} catch (NumberFormatException ex) {
					    JOptionPane.showMessageDialog(null, "Certifique-se de inserir números válidos para numero de matricula.");
					}
					try {
						StudentDAO StudentDAO = new StudentDAO();
						StudentDAO.insertStudent(name,numberRegistration);
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, "Este ISBN já está em uso. Por favor, insira um ISBN diferente.");
					}
					txtStudent.setText("");
					txtnumberRegistration.setText("");
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
