package br.edu.cs.poo.ac.ordem.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class InicioGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final Color PRIMARY = new Color(33, 150, 243);
    private static final Color PRIMARY_DARK = new Color(25, 118, 210);
    private static final Color ACCENT = new Color(56, 142, 60);
    private static final Color ACCENT_DARK = new Color(46, 125, 50);

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            // UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignore) {}

        EventQueue.invokeLater(() -> {
        	InicioGUI frame = new InicioGUI();
            frame.setVisible(true);
        });
    }

    public InicioGUI() {
        setTitle("Atendimento • Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel header = new GradientHeaderPanel();
        header.setLayout(new BorderLayout());
        header.setBorder(new EmptyBorder(18, 20, 18, 20));
        JLabel title = new JLabel("Central de Atendimento");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        JLabel subtitle = new JLabel("Escolha uma opção abaixo");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(new Color(225, 245, 254));

        JPanel headerText = new JPanel(new GridLayout(2,1,0,6));
        headerText.setOpaque(false);
        headerText.add(title);
        headerText.add(subtitle);
        header.add(headerText, BorderLayout.WEST);

        getContentPane().add(header, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBorder(new EmptyBorder(24, 24, 24, 24));
        center.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.gridx = 0; gbc.gridy = 0;

        JButton btnCliente = makeBigButton("👤  Cliente", PRIMARY, PRIMARY_DARK);
        JButton btnEquip = makeBigButton("💻  Equipamento", ACCENT, ACCENT_DARK);

        btnCliente.setPreferredSize(new Dimension(220, 56));
        btnEquip.setPreferredSize(new Dimension(220, 56));

        center.add(btnCliente, gbc);
        gbc.gridx = 1;
        center.add(btnEquip, gbc);

        getContentPane().add(center, BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(Color.WHITE);
        footer.setBorder(new EmptyBorder(8, 12, 8, 12));

        JLabel info = new JLabel("© 2025 • Sistema de Ordens");
        info.setFont(new Font("SansSerif", Font.PLAIN, 12));
        info.setForeground(new Color(120, 120, 120));
        footer.add(info, BorderLayout.WEST);

        JButton btnSair = new JButton("Sair");
        btnSair.setFocusable(false);
        btnSair.setFont(new Font("SansSerif", Font.PLAIN, 12));
        btnSair.addActionListener(e -> System.exit(0));
        footer.add(btnSair, BorderLayout.EAST);

        getContentPane().add(footer, BorderLayout.SOUTH);

        btnCliente.addActionListener(e -> {
            ClienteGUI telaCliente = new ClienteGUI();
            telaCliente.setVisible(true);
            dispose();
        });

        btnEquip.addActionListener(e -> {
            EquipamentoGUI telaEquipamento = new EquipamentoGUI();
            telaEquipamento.setVisible(true);
            dispose();
        });

        addHotkeys(btnCliente, btnEquip);

        pack();
        setLocationRelativeTo(null);
    }

    private JButton makeBigButton(String text, Color base, Color hover) {
        JButton b = new JButton(text);
        b.setFont(new Font("SansSerif", Font.BOLD, 16));
        b.setForeground(Color.WHITE);
        b.setBackground(base);
        b.setFocusPainted(false);
        b.setBorder(new EmptyBorder(12, 20, 12, 20));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setOpaque(true);

        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { b.setBackground(hover); }
            @Override public void mouseExited(MouseEvent e)  { b.setBackground(base);  }
        });
        return b;
    }

    private void addHotkeys(JButton btnCliente, JButton btnEquip) {
        JRootPane root = getRootPane();

        root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_1, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()), "openCliente");
        root.getActionMap().put("openCliente", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { btnCliente.doClick(); }
        });

        root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_2, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()), "openEquip");
        root.getActionMap().put("openEquip", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { btnEquip.doClick(); }
        });

        root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "exitApp");
        root.getActionMap().put("exitApp", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { System.exit(0); }
        });
    }

    static class GradientHeaderPanel extends JPanel {
        private static final long serialVersionUID = 1L;
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            int w = getWidth(), h = getHeight();
            GradientPaint gp = new GradientPaint(0, 0,
                    new Color(25,118,210),
                    w, h,
                    new Color(13,71,161));
            g2.setPaint(gp);
            g2.fillRect(0, 0, w, h);
            g2.dispose();
        }
    }
}
