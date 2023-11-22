package Screens.CRUDStudent;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class DeleteStudentPanel  extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 8884562050178127774L;
    private JTextField txtCustomerId;
    private JButton btnDeleteCustomer;
    private DefaultTableModel tableModel;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JButton btnBack;
    private StudentPanel StudentPanel;

    public DeleteStudentPanel(DefaultTableModel tableModel, CardLayout cardLayout,
            JPanel cardPanel, StudentPanel StudentPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.tableModel = tableModel;

        initializeUI();
    }

    private void initializeUI() {
        setLayout(null);

        // Label ID
        JLabel lblCustomerId = new JLabel("ID do Cliente:");
        lblCustomerId.setBounds(10, 10, 150, 25);
        add(lblCustomerId);

        // Campo de texto para o ID
        txtCustomerId = new JTextField();
        txtCustomerId.setBounds(160, 10, 165, 25);
        add(txtCustomerId);

        // Botão de deletar
        btnDeleteCustomer = new JButton("Deletar Cliente");
        btnDeleteCustomer.setBounds(10, 45, 315, 25);
        btnDeleteCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //deleteCustomer();
            }
        });
        add(btnDeleteCustomer);

        btnBack = new JButton("Voltar");
        btnBack.setBounds(351, 271, 89, 23);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "BookPanel");
            }
        });
        add(btnBack);
    }

//    private void deleteCustomer() {
//        String customerIdStr = txtCustomerId.getText();
//        if (customerIdStr.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Por favor, insira o ID do cliente a ser deletado.", "ID Vazio",
//                    JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//
//        try {
//            Integer internalCustomerId = Integer.parseInt(customerIdStr);
//
//            // Obtém o firebaseId associado ao idLocal
//            String firebaseId = clienteManager.getFirebaseId(internalCustomerId);
//
//            if (firebaseId != null) {
//                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("clientes");
//                ApiFuture<Void> future = dbRef.child(firebaseId).removeValueAsync();
//
//                future.addListener(() -> {
//                    try {
//                        future.get(); // Aguarda a operação ser concluída
//                        if (removeFromTableModel(internalCustomerId.toString())) {
//                            clienteManager.removeClienteIdMapping(internalCustomerId); // Remover o mapeamento
//
//                            // Atualizar a UI na EDT
//                            SwingUtilities.invokeLater(() -> {
//                                JOptionPane.showMessageDialog(this, "Cliente deletado com sucesso.", "Cliente Deletado",
//                                        JOptionPane.INFORMATION_MESSAGE);
//                                customersPanel.refreshCustomerTable();
//                            });
//                        }
//                    } catch (Exception e) {
//                        // Tratamento de exceção na EDT
//                        SwingUtilities.invokeLater(() -> {
//                            JOptionPane.showMessageDialog(this,
//                                    "Falha ao deletar cliente do Firebase: " + e.getMessage(), "Erro",
//                                    JOptionPane.ERROR_MESSAGE);
//                        });
//                    }
//                }, Executors.newSingleThreadExecutor());
//            } else {
//                // Mensagem de erro na EDT
//                SwingUtilities.invokeLater(() -> {
//                    JOptionPane.showMessageDialog(this, "ID do Firebase não encontrado para o cliente.", "Erro",
//                            JOptionPane.ERROR_MESSAGE);
//                });
//            }
//        } catch (NumberFormatException e) {
//            // Mensagem de erro na EDT
//            SwingUtilities.invokeLater(() -> {
//                JOptionPane.showMessageDialog(this, "Por favor, insira um ID válido.", "Erro de Formato",
//                        JOptionPane.ERROR_MESSAGE);
//            });
//        }
//    }
//
//    private boolean removeFromTableModel(String customerId) {
//        for (int i = 0; i < tableModel.getRowCount(); i++) {
//            if (String.valueOf(tableModel.getValueAt(i, 0)).equals(customerId)) {
//                tableModel.removeRow(i);
//                txtCustomerId.setText("");
//                return true; // Cliente encontrado e removido
//            }
//        }
//        return false; // Cliente não encontrado na tabela
//    }

}
