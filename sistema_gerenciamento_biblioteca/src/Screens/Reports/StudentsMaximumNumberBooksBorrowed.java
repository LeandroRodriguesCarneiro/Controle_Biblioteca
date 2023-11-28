package Screens.Reports;


//-*- coding: utf-8 -*-

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import Screens.ConfigPanel.Styles;
import Student.Student;
import Student.StudentDAO;

public class StudentsMaximumNumberBooksBorrowed extends JPanel{
	private static final long serialVersionUID = -6757445173507348372L;
	private JLabel lblSubTitle, lblName;
	private JTextField  txtName;
	private JButton search;
	private JTable table;
	private DefaultTableModel tableModel;
	private JScrollPane scrollPane;
	private StudentDAO studentDAO = new StudentDAO();
	private List<Student> StudentsList = new ArrayList<>();
	
	public StudentsMaximumNumberBooksBorrowed() {
		setLayout(null);
		lblSubTitle = new JLabel("Alunos com débitos");
		lblSubTitle.setBounds(140,0, 300, 35);
		Styles.styleSubtitleFont(lblSubTitle);
		add(lblSubTitle);
		
		lblName = new JLabel("Nome:");
        Styles.styleFont(lblName);
        lblName.setBounds(140, 45, 80, 25);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(200, 45, 500, 25);
        add(txtName);
		
        search = new JButton("Pesquisar");
        search.setBounds(932, 45, 140, 25);
        Styles.styleButton(search);
        add(search);
        
        search.addActionListener(e ->{
        	refreshStudentTable();
        });
		
        tableModel = new DefaultTableModel() {
			private static final long serialVersionUID = -3751069658207964279L;

			@Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableModel.addColumn("ID");
        tableModel.addColumn("Nome");
        tableModel.addColumn("Número de matricula");
        tableModel.addColumn("Livros Emprestados");
        
        table = new JTable(tableModel);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setPreferredWidth(0);
        columnModel.getColumn(0).setWidth(0);
        
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(140, 90, 930, 500);
        Styles.styleTable(table,scrollPane);
        add(scrollPane);
        refreshStudentTable();

	}
	
	public void refreshStudentTable() {
    	loadStudentsIntoTable();
    }

    public void loadStudentsIntoTable() {
        tableModel.setRowCount(0);
        StudentsList.clear();
        List<Student> updatedStudentsList = studentDAO.selectStudentsMaximumNumberBooksBorrowed(txtName.getText());
    	if (updatedStudentsList != null) {
            StudentsList.addAll(updatedStudentsList);
            if (!StudentsList.isEmpty()) {
                SwingUtilities.invokeLater(() -> {
                    for (Student student : StudentsList) {
                        tableModel.addRow(new Object[]{
                                student.getId(),
                                student.getName(),
                                student.getNumberRegistration(),
                                student.getBorrowedBooks()
                        });
                    }
                });
            } else {
            	if(!txtName.getText().isEmpty()) {
            		JOptionPane.showMessageDialog(this, "Não foi encontrado registros com esse filtro.", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                    txtName.requestFocus();
            	}
            }
        }
    }
	
	 public JPanel getPanel() {
	        return this;
	    }
	 
}
