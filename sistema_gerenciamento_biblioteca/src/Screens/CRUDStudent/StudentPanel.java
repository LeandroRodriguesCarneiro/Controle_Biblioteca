package Screens.CRUDStudent;
//-*- coding: utf-8 -*-
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
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
import Student.StudentDAO;
import Student.Student;

public class StudentPanel extends JPanel{
	private static final long serialVersionUID = -4843807817212241104L;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnEdit, btnDelete, btnDebits, search;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JButton backButton;
    private JTextField txtName, txtNumberRegistration, txtYearPublication, txtPublisher,txtGenre, txtAuthor;
    private JLabel lblBooks, lnlName, lblNumberRegistration, lblPublisher, lblYearPublication, lblAuthor, lblgenres;
    private List<Student> studentsList = new ArrayList<>();    
    private StudentDAO studentDAO = new StudentDAO();
    public StudentPanel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardPanel = cardPanel;
        this.cardLayout = cardLayout;
        setLayout(null);
        
        lblBooks = new JLabel("Alunos");
        Styles.styleTitleFont(lblBooks);
        lblBooks.setBounds(142, 10, 150, 30);
        add(lblBooks);
        
        backButton = new JButton("Voltar");
        backButton.setBounds(992, 10, 80, 30);
        Styles.styleButton(backButton);
        add(backButton);
        
        lnlName = new JLabel("Nome:");
        Styles.styleFont(lnlName);
        lnlName.setBounds(142, 45, 80, 25);
        add(lnlName);

        txtName = new JTextField();
        txtName.setBounds(200, 45, 500, 25);
        add(txtName);

        lblNumberRegistration = new JLabel("Matricula:");
        Styles.styleFont(lblNumberRegistration);
        lblNumberRegistration.setBounds(720, 45, 150, 25);
        add(lblNumberRegistration);

        txtNumberRegistration = new JTextField();
        txtNumberRegistration.setBounds(800, 45, 90, 25);
        add(txtNumberRegistration);

        search = new JButton("Pesquisar");
        search.setBounds(932, 45, 140, 25);
        Styles.styleButton(search);
        add(search);
        
        search.addActionListener(e ->{
        	refreshStudentTable();
        });
        
        btnAdd = new JButton("Adicionar Aluno");
        btnAdd.setBounds(140, 90, 150, 30);
        Styles.styleButton(btnAdd);
        add(btnAdd);
        
        btnAdd.addActionListener(e -> {
            AddStudentPanel addStudentPanel = new AddStudentPanel(cardLayout, cardPanel,this);
            cardPanel.add(addStudentPanel, "AddStudentPanel");
            cardLayout.show(cardPanel, "AddStudentPanel");
        });
        
        tableModel = new DefaultTableModel() {
            private static final long serialVersionUID = -9049266189071413309L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("ID");
        tableModel.addColumn("Nome");
        tableModel.addColumn("Matrícula");
        tableModel.addColumn("Alunos emprestados");
        tableModel.addColumn("Débitos");

        table = new JTable(tableModel);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setPreferredWidth(0);
        columnModel.getColumn(0).setWidth(0);
        columnModel.getColumn(3).setMaxWidth(0);
        columnModel.getColumn(3).setMinWidth(0);
        columnModel.getColumn(3).setPreferredWidth(0);
        columnModel.getColumn(3).setWidth(0);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(140, 135, 930, 300);
        Styles.styleTable(table,scrollPane);
        add(scrollPane);
        
        btnEdit = new JButton("Editar Aluno");
        btnEdit.setBounds(140, 445, 150, 30);
        Styles.styleButton(btnEdit);
        add(btnEdit);

        btnDelete = new JButton("Deletar Aluno");
        btnDelete.setBounds(300, 445, 150, 30);
        Styles.styleButton(btnDelete);
        add(btnDelete);

        btnDebits = new JButton("Pagar Multa");
        btnDebits.setBounds(460, 445, 150, 30);
        Styles.styleButton(btnDebits);
        add(btnDebits);
        
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "MainPanel"));
        refreshStudentTable();
        
        btnEdit.addActionListener(e ->{
        	int selectedRow = table.getSelectedRow();
    	    if (selectedRow != -1) {
    	    	Student student = new Student(
    	    			Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString()),
    	    	    	Integer.parseInt(tableModel.getValueAt(selectedRow, 3).toString()),
    	    			tableModel.getValueAt(selectedRow, 1).toString(),
    	    	    	Long.parseLong(tableModel.getValueAt(selectedRow, 2).toString()),
    	    	    	convertStringDecimal(tableModel.getValueAt(selectedRow, 4).toString())
    	    	    	);
    	    	UpdateStudentPanel updateStudentPanel = new UpdateStudentPanel(cardLayout, cardPanel, this, student);
    	    	cardPanel.add(updateStudentPanel, "updateStudentPanel");
    	    	cardLayout.show(cardPanel, "updateStudentPanel");
    	    } else {
    	        JOptionPane.showMessageDialog(null, "Por favor, selecione um Aluno na tabela.");
    	    }
        });
        
        btnDelete.addActionListener(e -> {
        	int selectedRow = table.getSelectedRow();
    	    if (selectedRow != -1) {
    	        int StudentID = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
	    	    int dialogResult = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir?", "Confirmação", JOptionPane.YES_NO_OPTION);
	    	    if (dialogResult == JOptionPane.YES_OPTION) {
	    	    	StudentDAO studentDAO = new StudentDAO();
	    	    	try {
	    	    		studentDAO.deleteStudent(StudentID);
			    	    refreshStudentTable();
	    	    	}catch(Exception ex) {
	    	    		JOptionPane.showMessageDialog(null, ex.getMessage());
	    	    	}
		    	    
	    	    } else {
	    	        return;
	    	    }
    	    } else {
    	        JOptionPane.showMessageDialog(null, "Por favor, selecione um Aluno na tabela.");
    	    }
        });
        
        btnDebits.addActionListener(e -> {
        	int selectedRow = table.getSelectedRow();
    	    if (selectedRow != -1) {
    	    	Student student = new Student(
    	    			Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString()),
    	    	    	Integer.parseInt(tableModel.getValueAt(selectedRow, 3).toString()),
    	    			tableModel.getValueAt(selectedRow, 1).toString(),
    	    	    	Long.parseLong(tableModel.getValueAt(selectedRow, 2).toString()),
    	    	    	convertStringDecimal(tableModel.getValueAt(selectedRow, 4).toString()) * (-1)
    	    	    	);
    	    	if(student.getDebits()>=0) {
    	    		JOptionPane.showMessageDialog(null, "O aluno selecionado nao tem Multa a pagar!");
    	    	}else {
    	    		PayDebitsPanel PayDebitsPanel = new PayDebitsPanel(cardLayout, cardPanel, this, student);
    	    		PayDebitsPanel.activateBtnPay();
    	    		cardPanel.add(PayDebitsPanel, "PayDebitsPanel");
        	    	cardLayout.show(cardPanel, "PayDebitsPanel");
    	    	}
    	    } else {
    	        JOptionPane.showMessageDialog(null, "Por favor, selecione um Aluno na tabela.");
    	    }
        });
    }
    public void refreshStudentTable() {
    	loadStudentsIntoTable();
    }

    public void loadStudentsIntoTable() {
    	Long numberRegistration = (long) 0;
    	try {
    		if(txtNumberRegistration.getText().trim().length() == 10 && txtNumberRegistration.getText().trim().isEmpty()) {
    			txtNumberRegistration.setText("");
    			txtNumberRegistration.requestFocus();
    			JOptionPane.showMessageDialog(this, "O número de matricula precisa conter 10 digitos", "Erro",
                        JOptionPane.ERROR_MESSAGE);
    			return;
    		}
    		numberRegistration = Long.parseLong(txtNumberRegistration.getText());
    	}catch(NumberFormatException e) {
    		if(txtNumberRegistration.getText().trim().length()>0) {
    			JOptionPane.showMessageDialog(null, "O número de matricula precisa ser um número");
    			txtNumberRegistration.setText("");
    			txtNumberRegistration.requestFocus();
    			return;
    		}
    	}
    	tableModel.setRowCount(0);
        studentsList.clear();
        List<Student> updatedStudentList = studentDAO.selectStudentByFilter(txtName.getText(), numberRegistration);
        	if (updatedStudentList != null) {
                updatedStudentList.sort(Comparator.comparingInt(Student::getId));
                studentsList.addAll(updatedStudentList);
                if (!studentsList.isEmpty()) {
                    SwingUtilities.invokeLater(() -> {
                        for (Student student : studentsList) {
                        	String formattedRegistration = String.format("%010d", student.getNumberRegistration());
                            tableModel.addRow(new Object[]{
                                    student.getId(),
                                    student.getName(),
                                    formattedRegistration,
                                    student.getBorrowedBooks(),
                                    student.getDebits() < 0 ? formatDecimal(student.getDebits() * (-1)) : formatDecimal(student.getDebits())
                            });
                        }
                    });
                }
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao carregar dados do Banco de dados.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
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