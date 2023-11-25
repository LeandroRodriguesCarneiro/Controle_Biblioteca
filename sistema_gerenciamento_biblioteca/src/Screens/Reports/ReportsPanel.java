package Screens.Reports;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

public class ReportsPanel extends JFrame {

    public ReportsPanel() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);

        JPanel panel = new JPanel(new FlowLayout());

        // Cria um formato para valores monetários
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        NumberFormatter numberFormatter = new NumberFormatter(decimalFormat);
        numberFormatter.setValueClass(Double.class);
        numberFormatter.setMinimum(0.00);
        numberFormatter.setMaximum(999.99);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setCommitsOnValidEdit(true);

        JFormattedTextField textField = new JFormattedTextField();
        textField.setFormatterFactory(new DefaultFormatterFactory(numberFormatter));
        textField.setPreferredSize(new Dimension(150, 30));

        panel.add(textField);

        add(panel);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ReportsPanel exemplo = new ReportsPanel();
            exemplo.setVisible(true);
        });
    }
}
