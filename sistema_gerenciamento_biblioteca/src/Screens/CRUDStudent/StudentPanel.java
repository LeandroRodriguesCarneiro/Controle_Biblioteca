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
    private JButton btnAdd, btnEdit, btnDelete;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private studentDAO studentDao = new studentDAO();
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
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 50, 930, 300);
        add(scrollPane);
        
        btnAdd = new JButton("Adicionar Aluno");
        btnAdd.setBounds(180, 10, 150, 30);
        add(btnAdd);

        btnEdit = new JButton("Editar");
        btnEdit.setBounds(20, 360, 150, 30);
        add(btnEdit);

        btnDelete = new JButton("Deletar Aluno");
        btnDelete.setBounds(180, 360, 150, 30);
        add(btnDelete);

        backButton = new JButton("Voltar");
        backButton.setBounds(870, 10, 80, 30);
        add(backButton);

        backButton.addActionListener(e -> cardLayout.show(cardPanel, "MainPanel"));
        refreshBookTable();
        btnAdd.addActionListener(e -> {
        	loadStudentList = false;
            AddStudentPanel addStudentPanel = new AddStudentPanel(tableModel, cardLayout, cardPanel,this);
            cardPanel.add(addStudentPanel, "AddStudentPanel");
            cardLayout.show(cardPanel, "AddStudentPanel");
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
	    	    	studentDAO studentDAO = new studentDAO();
		    	    studentDAO.deleteStudent(StudentID);
		    	    loadStudentList = false;
		    	    refreshBookTable();
	    	    } else {
	    	        return;
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
        booksList.clear();
        List<Student> updatedBooksList = studentDao.selectAllBooks();
        if(loadBookList == false) {
        	if (updatedBooksList != null) {
                updatedBooksList.sort(Comparator.comparingInt(Student::getId));
                booksList.addAll(updatedBooksList);
                if (!studentsList.isEmpty()) {
                    SwingUtilities.invokeLater(() -> {
                        for (Student Student : studentsList) {
                            tableModel.addRow(new Object[]{
                                    Student.getId(),
                                    Student.getTitle(),
                                    Student.getIsbn(),
                                    Student.getPublisher(),
                                    Student.getYearPublication(),
                                    Student.getQuantity(),
                                    String.join(",", Student.getAuthor()),
                                    String.join(",", Student.getGenre())
                            });
                        }
                    });
                    loadBookList = true;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao carregar dados do Banco de dados.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    
}
