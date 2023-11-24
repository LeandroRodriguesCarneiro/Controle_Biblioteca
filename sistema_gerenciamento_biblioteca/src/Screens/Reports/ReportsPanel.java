package Screens.Reports;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import Screens.ConfigPanel.Styles;

import java.awt.*;

public class ReportsPanel extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReportsPanel() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JTable table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Nome");
        model.addColumn("Idade");
        model.addRow(new Object[]{"Alice", 25});
        model.addRow(new Object[]{"Bob", 30});
        model.addRow(new Object[]{"Charlie", 28});

        table.setModel(model);
        
        Styles.styleTable(table,scrollPane);
	}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ReportsPanel example = new ReportsPanel();
            example.setVisible(true);
        });
    }
}

