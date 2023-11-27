package Screens.Reports;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

//-*- coding: utf-8 -*-

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import Screens.ConfigPanel.Styles;
import Student.Student;
import Student.StudentDAO;

public class StudentsWithDebits extends JPanel{
	private static final long serialVersionUID = -6757445173507348372L;
	private JLabel lbllblSubTitle, lblMinDebits, lblName;
	private JFormattedTextField txtMinDebits; 
	private JTextField  txtName;
	private JButton search;
	private JTable table;
	private DefaultTableModel tableModel;
	private JScrollPane scrollPane;
	private StudentDAO studentDAO = new StudentDAO();
	private List<Student> StudentsList = new ArrayList<>();
	
	public StudentsWithDebits() {
		setLayout(null);
		lbllblSubTitle = new JLabel("Alunos com débitos");
		lbllblSubTitle.setBounds(140,0, 300, 35);
		Styles.styleSubtitleFont(lbllblSubTitle);
		add(lbllblSubTitle);
		
		lblName = new JLabel("Nome:");
        Styles.styleFont(lblName);
        lblName.setBounds(140, 45, 80, 25);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(200, 45, 500, 25);
        add(txtName);
		
        lblMinDebits = new JLabel("Valor mínimo:");
        lblMinDebits.setBounds(720, 45, 250, 25);
        Styles.styleFont(lblMinDebits);
        add(lblMinDebits);
        
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        NumberFormatter numberFormatter = new NumberFormatter(decimalFormat);
        numberFormatter.setValueClass(Double.class);
        numberFormatter.setMinimum(0.00);
        numberFormatter.setMaximum(999.99);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setCommitsOnValidEdit(true);
        
        txtMinDebits = new JFormattedTextField();
        txtMinDebits.setFormatterFactory(new DefaultFormatterFactory(numberFormatter));
        txtMinDebits.setBounds(830, 45, 60, 25);
        txtMinDebits.setValue(0.5);
        add(txtMinDebits);
        
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
        tableModel.addColumn("Dividas");
        
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
        List<Student> updatedStudentsList = studentDAO.selectStudentsDebt(convertStringDecimal(txtMinDebits.getText()),txtName.getText());
    	if (updatedStudentsList != null) {
            StudentsList.addAll(updatedStudentsList);
            if (!StudentsList.isEmpty()) {
                SwingUtilities.invokeLater(() -> {
                    for (Student student : StudentsList) {
                        tableModel.addRow(new Object[]{
                                student.getId(),
                                student.getName(),
                                student.getNumberRegistration(),
                                student.getDebits() < 0 ? formatDecimal(student.getDebits() * (-1)) : formatDecimal(student.getDebits())
                        });
                    }
                });
            }
        }
    }
	
	 public JPanel getPanel() {
	        return this;
	    }
	 
	 public static String formatDecimal(Float numero) {
	        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
	        symbols.setDecimalSeparator(',');
	        symbols.setGroupingSeparator('.');
	        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
	        return df.format(numero);
	    }
	 
	 public static float convertStringDecimal(String numeroStr) {
	        numeroStr = numeroStr.replace(",", ".");
	        
	        try {
	            return Float.parseFloat(numeroStr);
	        } catch (NumberFormatException e) {
	            e.printStackTrace();
	        }
	        return 0;
	    }
}
