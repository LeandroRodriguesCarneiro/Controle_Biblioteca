package Screens;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;





public class butPanel extends JPanel {

    private static final long serialVersionUID = -1666701738391775114L;
    private CardLayout cardLayout;
    private JPanel cardPanel; //Painel que usa CardLayout
    private PromotionsPanel promotionsPanel;
    private Color lightModeBackground = Color.WHITE;
    private Color darkModeBackground = Color.DARK_GRAY;
    private Color lightModeForeground = Color.BLACK;
    private Color darkModeForeground = Color.WHITE;
    private boolean isDarkMode = false;

    public butPanel(CardLayout cardLayout, JPanel cardPanel, PromotionsPanel promotionsPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.promotionsPanel = promotionsPanel;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        addButton("Tela Principal", "MainPanel", null);
        addButton("Adicionar Livro", "SalesPanel", null);
        addButton("Adicionar Aluno", "PromotionsPanel", this::atualizarPromotionsPanel); //metodo para atualizar a jcombobox no painel de promoção
        addButton("Emprestimo", "ProductsPanel", null);
        addButton("Devolucao", "CustomersPanel", null);
        addButton("Visualizacao", "AddSupplierPanel", null);
        addButton("Deletar Livro", "AddCategoryPanel", null);
        addButton("Relatorio", "ReportsPanel", null);
        
        setPreferredSize(new Dimension(200, getHeight()));
    }

    private void addButton(String buttonText, String panelName, Runnable additionalAction) {
        JButton button = new JButton(buttonText);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getMinimumSize().height));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (additionalAction != null) {
                    additionalAction.run();
                }
                cardLayout.show(cardPanel, panelName);
            }
        });
        add(button);
    }

    private void atualizarPromotionsPanel() {
        promotionsPanel.atualizarListaProdutos();
    }
}

