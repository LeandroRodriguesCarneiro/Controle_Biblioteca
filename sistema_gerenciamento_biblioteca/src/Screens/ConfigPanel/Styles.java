package Screens.ConfigPanel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class Styles {

    public static final Color BACKGROUND_COLOR = new Color(240, 240, 240);
    public static final Color BUTTON_BACKGROUND_COLOR = new Color(200, 230, 255);
    public static final Color BUTTON_TEXT_COLOR = new Color(30, 69, 134);
    
    public static final Color LABEL_COLOR_TITLE = new Color(0, 51, 102);
    public static final Color LABEL_COLOR = new Color(30, 69, 134);

    public static final Font DEFAULT_FONT = new Font("Times New Roman", Font.PLAIN, 16);
    public static final Font DEFAULT_FONT_TITLE = new Font("Times New Roman", Font.BOLD, 36);
    public static final Font DEFAULT_FONT_SUBTITLE = new Font("Times New Roman", Font.BOLD, 30);
    
    public static void styleButtonMenu(JButton button) {
        button.setFont(DEFAULT_FONT);
        button.setBackground(BUTTON_BACKGROUND_COLOR);
        button.setForeground(BUTTON_TEXT_COLOR);
    }
    
    public static void styleButton(JButton button) {
        button.setFont(DEFAULT_FONT);
        button.setBackground(BUTTON_BACKGROUND_COLOR);
        button.setForeground(BUTTON_TEXT_COLOR);
    }
    
    public static void styleTitleFont(JLabel label) {
    	label.setFont(DEFAULT_FONT_TITLE);
        label.setForeground(LABEL_COLOR_TITLE);
    }
    
    public static void styleSubtitleFont(JLabel label) {
    	label.setFont(DEFAULT_FONT_SUBTITLE);
        label.setForeground(LABEL_COLOR_TITLE);
    }
    
    public static void styleFont(JLabel label) {
    	label.setFont(DEFAULT_FONT);
        label.setForeground(LABEL_COLOR);
    }
    
    public static void styleFont(JTextArea textArea) {
    	textArea.setFont(DEFAULT_FONT);
    	textArea.setForeground(LABEL_COLOR);
    }
    
    public static void styleTable(JTable table, JScrollPane pane) {
        table.setFont(Styles.DEFAULT_FONT);

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(Styles.LABEL_COLOR);
        headerRenderer.setForeground(Styles.BACKGROUND_COLOR);
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setDefaultRenderer(headerRenderer);

        DefaultTableCellRenderer cellRenderer = (DefaultTableCellRenderer) table.getDefaultRenderer(Object.class);
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        table.setRowHeight(25);

        if (pane != null) {
            pane.getViewport().setBackground(Styles.BACKGROUND_COLOR);
        }
    }
    
    public static void styleComboBox(JComboBox<?> comboBox) {
    	comboBox.setBackground(BUTTON_BACKGROUND_COLOR);
    	comboBox.setForeground(LABEL_COLOR);
    	comboBox.setFont(DEFAULT_FONT);

        UIManager.put("ComboBox.background", new Color(240, 240, 255));
        UIManager.put("ComboBox.selectionBackground", new Color(60, 60, 255)); 
    }
    
    public static void styleJScrollPane(JScrollPane pane) {
    	 pane.getViewport().setBackground(BACKGROUND_COLOR); 
    	 pane.setFont(DEFAULT_FONT);
         pane.getVerticalScrollBar().setBackground(BUTTON_BACKGROUND_COLOR); 
         pane.getHorizontalScrollBar().setBackground(BUTTON_BACKGROUND_COLOR); 
         pane.setBorder(BorderFactory.createLineBorder(BUTTON_BACKGROUND_COLOR, 1)); 
         
    }
}
