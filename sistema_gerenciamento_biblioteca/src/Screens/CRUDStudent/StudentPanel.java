package Screens.CRUDStudent;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import Student.StudentDAO;
import Student.Student;

public class StudentPanel extends JPanel{
	private static final long serialVersionUID = -4843807817212241104L;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnEdit, btnDelete, btnDebits;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private StudentDAO studentDAO = new StudentDAO();
    private JButton backButton;
    private JLabel lblStudents;
    private List<Student> studentsList = new ArrayList<>();
    private boolean loadStudentList = false;
    
    public StudentPanel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardPanel = cardPanel;

        setLayout(null);

        tableModel = new DefaultTableModel() {
            /**
             * 
             */
            private static final long serialVersionUID = -9049266189071413309L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("ID");
        tableModel.addColumn("Nome");
        tableModel.addColumn("Matrícula");
        tableModel.addColumn("Livros emprestados");
        tableModel.addColumn("Débitos");
       

        lblStudents = new JLabel("Alunos");
        lblStudents.setFont(new Font("Arial", Font.BOLD, 30));
        lblStudents.setBounds(20, 10, 150, 30);
        add(lblStudents);

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
        scrollPane.setBounds(20, 50, 930, 300);
        add(scrollPane);
        
        btnAdd = new JButton("Adicionar Aluno");
        btnAdd.setBounds(180, 10, 150, 30);
        add(btnAdd);

        btnEdit = new JButton("Editar Aluno");
        btnEdit.setBounds(20, 360, 150, 30);
        add(btnEdit);

        btnDelete = new JButton("Deletar Aluno");
        btnDelete.setBounds(180, 360, 150, 30);
        add(btnDelete);

        backButton = new JButton("Voltar");
        backButton.setBounds(870, 10, 80, 30);
        add(backButton);
        
        btnDebits = new JButton("Pagar Multa");
        btnDebits.setBounds(340, 360, 150, 30);
        add(btnDebits);

        backButton.addActionListener(e -> cardLayout.show(cardPanel, "MainPanel"));
        refreshStudentTable();
        btnAdd.addActionListener(e -> {
        	loadStudentList = false;
            AddStudentPanel addStudentPanel = new AddStudentPanel(tableModel, cardLayout, cardPanel,this);
            cardPanel.add(addStudentPanel, "AddStudentPanel");
            cardLayout.show(cardPanel, "AddStudentPanel");
        });
        
        btnEdit.addActionListener(e ->{
        	int selectedRow = table.getSelectedRow();
    	    if (selectedRow != -1) {
    	    	Student student = new Student(
    	    			Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString()),
    	    	    	Integer.parseInt(tableModel.getValueAt(selectedRow, 3).toString()),
    	    			tableModel.getValueAt(selectedRow, 1).toString(),
    	    	    	Long.parseLong(tableModel.getValueAt(selectedRow, 2).toString()),
    	    	    	Float.parseFloat(tableModel.getValueAt(selectedRow, 4).toString())
    	    	    	);
    	    	loadStudentList = false;
    	    	UpdateStudentPanel updateStudentPanel = new UpdateStudentPanel(tableModel, cardLayout, cardPanel, this, student);
    	    	cardPanel.add(updateStudentPanel, "updateStudentPanel");
    	    	cardLayout.show(cardPanel, "updateStudentPanel");
    	    }
        });
        
        btnDelete.addActionListener(e -> {
//            DeleteBookPanel deleteBookPanel = new DeleteBookPanel(tableModel, cardLayout,
//                    cardPanel, this);
//            cardPanel.add(deleteBookPanel, "DeleteBookPanel");
//            cardLayout.show(cardPanel, "DeleteBookPanel");
        	int selectedRow = table.getSelectedRow();
    	    if (selectedRow != -1) {
    	        int StudentID = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
	    	    int dialogResult = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir?", "Confirmação", JOptionPane.YES_NO_OPTION);
	    	    if (dialogResult == JOptionPane.YES_OPTION) {
	    	    	StudentDAO studentDAO = new StudentDAO();
		    	    studentDAO.deleteStudent(StudentID);
		    	    loadStudentList = false;
		    	    refreshStudentTable();
	    	    } else {
	    	        return;
	    	    }
    	    } else {
    	        JOptionPane.showMessageDialog(null, "Por favor, selecione um livro na tabela.");
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
    	    	    	(Float.parseFloat(tableModel.getValueAt(selectedRow, 4).toString()) * (-1))
    	    	    	);
    	    	if(student.getDebits()>=0) {
    	    		JOptionPane.showMessageDialog(null, "O aluno selecionado nao tem Multa a pagar!");
    	    	}else {
    	    		loadStudentList = false;
    	    		PayDebitsPanel PayDebitsPanel = new PayDebitsPanel(tableModel, cardLayout, cardPanel, this, student); 
    	    		cardPanel.add(PayDebitsPanel, "PayDebitsPanel");
        	    	cardLayout.show(cardPanel, "PayDebitsPanel");
    	    	}
    	    	
    	    } else {
    	        JOptionPane.showMessageDialog(null, "Por favor, selecione um livro na tabela.");
    	    }
        });
    }
    public void refreshStudentTable() {
    	loadStudentsIntoTable();
    }

    public void loadStudentsIntoTable() {
        tableModel.setRowCount(0);
        studentsList.clear();
        List<Student> updatedStudentList = studentDAO.selectAllStudent();
        if(loadStudentList == false) {
        	if (updatedStudentList != null) {
                updatedStudentList.sort(Comparator.comparingInt(Student::getId));
                studentsList.addAll(updatedStudentList);
                if (!studentsList.isEmpty()) {
                    SwingUtilities.invokeLater(() -> {
                        for (Student student : studentsList) {
                            tableModel.addRow(new Object[]{
                                    student.getId(),
                                    student.getName(),
                                    student.getNumberRegistration(),
                                    student.getBorrowedBooks(),
                                    student.getDebits() < 0 ? student.getDebits() * (-1) : student.getDebits()
                            });
                        }
                    });
                    loadStudentList = true;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao carregar dados do Banco de dados.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    
}
