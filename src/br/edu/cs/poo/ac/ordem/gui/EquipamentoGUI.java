package br.edu.cs.poo.ac.ordem.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import br.edu.cs.poo.ac.ordem.entidades.Desktop;
import br.edu.cs.poo.ac.ordem.entidades.Notebook;
import br.edu.cs.poo.ac.ordem.mediators.EquipamentoMediator;
import br.edu.cs.poo.ac.ordem.mediators.ResultadoMediator;

//tela opicional
public class EquipamentoGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private enum Modo { INICIAL, NOVO, EDICAO }

    private JComboBox<String> cbTipo;
    private JTextField txtSerial;
    private JTextArea txtDescricao;
    private JRadioButton rbNovoNao, rbNovoSim;
    private ButtonGroup grpNovo;

    private JFormattedTextField txtValor;
    private NumberFormatter valorFormatter;

    private JPanel panelNotebook;
    private JRadioButton rbSensNao, rbSensSim;
    private ButtonGroup grpSens;

    private JPanel panelDesktop;
    private JRadioButton rbServNao, rbServSim;
    private ButtonGroup grpServ;

    private JButton btnNovo, btnBuscar, btnAdicionar, btnAlterar, btnExcluir, btnCancelar, btnLimpar;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                EquipamentoGUI frame = new EquipamentoGUI();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public EquipamentoGUI() {
        setTitle("CRUD de Equipamento");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(640, 440);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel lblAcesso = new JLabel("Acesso:");
        lblAcesso.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        lblAcesso.setBounds(20, 10, 100, 18);
        getContentPane().add(lblAcesso);

        JLabel lblTipo = new JLabel("Tipo de Equipamento");
        lblTipo.setBounds(20, 35, 160, 18);
        getContentPane().add(lblTipo);

        cbTipo = new JComboBox<>(new String[]{"Notebook", "Desktop"});
        cbTipo.setBounds(20, 55, 180, 24);
        cbTipo.setSelectedIndex(0);
        getContentPane().add(cbTipo);

        JLabel lblSerial = new JLabel("Serial");
        lblSerial.setBounds(220, 35, 80, 18);
        getContentPane().add(lblSerial);

        txtSerial = new JTextField();
        txtSerial.setBounds(220, 55, 180, 24);
        getContentPane().add(txtSerial);

        btnNovo = new JButton("Novo");
        btnNovo.setBounds(410, 55, 90, 24);
        getContentPane().add(btnNovo);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(510, 55, 90, 24);
        getContentPane().add(btnBuscar);

        JLabel lblDados = new JLabel("Dados:");
        lblDados.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        lblDados.setBounds(20, 90, 100, 18);
        getContentPane().add(lblDados);

        JLabel lblDescricao = new JLabel("Descrição");
        lblDescricao.setBounds(20, 112, 100, 18);
        getContentPane().add(lblDescricao);

        txtDescricao = new JTextArea(3, 20);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        JScrollPane spDesc = new JScrollPane(txtDescricao);
        spDesc.setBounds(20, 132, 580, 70);
        getContentPane().add(spDesc);

        JLabel lblENovo = new JLabel("É novo?");
        lblENovo.setBounds(20, 210, 60, 18);
        getContentPane().add(lblENovo);

        rbNovoNao = new JRadioButton("NÃO", true);
        rbNovoSim = new JRadioButton("SIM");
        rbNovoNao.setBackground(Color.WHITE);
        rbNovoSim.setBackground(Color.WHITE);

        grpNovo = new ButtonGroup();
        grpNovo.add(rbNovoNao);
        grpNovo.add(rbNovoSim);

        rbNovoNao.setBounds(80, 208, 70, 22);
        rbNovoSim.setBounds(150, 208, 60, 22);

        getContentPane().add(rbNovoNao);
        getContentPane().add(rbNovoSim);

        JLabel lblValor = new JLabel("Valor estimado");
        lblValor.setBounds(230, 210, 120, 18);
        getContentPane().add(lblValor);

        txtValor = new JFormattedTextField();
        txtValor.setBounds(330, 208, 270, 24);
        configurarCampoMonetario();
        getContentPane().add(txtValor);

        panelNotebook = new JPanel(null);
        panelNotebook.setBackground(Color.WHITE);
        panelNotebook.setBorder(new EmptyBorder(6, 6, 6, 6));
        panelNotebook.setBounds(20, 242, 580, 48);
        panelNotebook.setBorder(BorderFactory.createTitledBorder("Notebook"));

        JLabel lblSens = new JLabel("Carrega dados sensíveis?");
        lblSens.setBounds(12, 18, 170, 20);
        panelNotebook.add(lblSens);

        rbSensNao = new JRadioButton("NÃO", true);
        rbSensSim = new JRadioButton("SIM");
        rbSensNao.setBackground(Color.WHITE);
        rbSensSim.setBackground(Color.WHITE);

        grpSens = new ButtonGroup();
        grpSens.add(rbSensNao);
        grpSens.add(rbSensSim);

        rbSensNao.setBounds(190, 16, 70, 22);
        rbSensSim.setBounds(260, 16, 60, 22);
        panelNotebook.add(rbSensNao);
        panelNotebook.add(rbSensSim);

        getContentPane().add(panelNotebook);

        panelDesktop = new JPanel(null);
        panelDesktop.setBackground(Color.WHITE);
        panelDesktop.setBorder(new EmptyBorder(6, 6, 6, 6));
        panelDesktop.setBounds(20, 242, 580, 48);
        panelDesktop.setBorder(BorderFactory.createTitledBorder("Desktop"));

        JLabel lblServ = new JLabel("É Servidor?");
        lblServ.setBounds(12, 18, 120, 20);
        panelDesktop.add(lblServ);

        rbServNao = new JRadioButton("NÃO", true);
        rbServSim = new JRadioButton("SIM");
        rbServNao.setBackground(Color.WHITE);
        rbServSim.setBackground(Color.WHITE);

        grpServ = new ButtonGroup();
        grpServ.add(rbServNao);
        grpServ.add(rbServSim);

        rbServNao.setBounds(120, 16, 70, 22);
        rbServSim.setBounds(190, 16, 60, 22);
        panelDesktop.add(rbServNao);
        panelDesktop.add(rbServSim);

        getContentPane().add(panelDesktop);

        alternarPaineisCondicionais("Notebook");
        cbTipo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                alternarPaineisCondicionais((String) e.getItem());
            }
        });

        Font btnFont = new Font(Font.SANS_SERIF, Font.BOLD, 12);

        btnAdicionar = new JButton("Incluir");
        btnAdicionar.setFont(btnFont);
        btnAdicionar.setBounds(20, 310, 110, 32);
        getContentPane().add(btnAdicionar);

        btnAlterar = new JButton("Alterar");
        btnAlterar.setFont(btnFont);
        btnAlterar.setBounds(135, 310, 110, 32);
        getContentPane().add(btnAlterar);

        btnExcluir = new JButton("Excluir");
        btnExcluir.setFont(btnFont);
        btnExcluir.setBounds(250, 310, 110, 32);
        getContentPane().add(btnExcluir);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(btnFont);
        btnCancelar.setBounds(365, 310, 110, 32);
        getContentPane().add(btnCancelar);

        btnLimpar = new JButton("Limpar");
        btnLimpar.setFont(btnFont);
        btnLimpar.setBounds(490, 310, 110, 32);
        getContentPane().add(btnLimpar);

        setModo(Modo.INICIAL);

        btnNovo.addActionListener(e -> {
            String serial = txtSerial.getText().trim();
            if (serial.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Serial deve ser preenchido!");
                return;
            }
            EquipamentoMediator med = EquipamentoMediator.getInstancia();
            if (isNotebookSelecionado()) {
                if (med.buscarNotebook("NO" + serial) != null) {
                    JOptionPane.showMessageDialog(this, "Notebook já existente!");
                    return;
                }
            } else {
                if (med.buscarDesktop("DE" + serial) != null) {
                    JOptionPane.showMessageDialog(this, "Desktop já existente!");
                    return;
                }
            }
            setModo(Modo.NOVO);
        });

        btnBuscar.addActionListener(e -> {
            String serial = txtSerial.getText().trim();
            if (serial.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Serial deve ser preenchido!");
                return;
            }
            EquipamentoMediator med = EquipamentoMediator.getInstancia();
            if (isNotebookSelecionado()) {
                Notebook note = med.buscarNotebook("NO" + serial);
                if (note == null) {
                    JOptionPane.showMessageDialog(this, "Notebook não existente!");
                    return;
                }
                preencherNotebook(note);
                setModo(Modo.EDICAO);
            } else {
                Desktop desk = med.buscarDesktop("DE" + serial);
                if (desk == null) {
                    JOptionPane.showMessageDialog(this, "Desktop não existente!");
                    return;
                }
                preencherDesktop(desk);
                setModo(Modo.EDICAO);
            }
        });

        btnAdicionar.addActionListener(e -> {
            String tipo = (String) cbTipo.getSelectedItem();
            String serial = txtSerial.getText().trim();
            String descricao = txtDescricao.getText().trim();
            boolean eNovo = rbNovoSim.isSelected();
            double valor;
            try {
                valor = Double.valueOf(txtValor.getText().replace(".", "").replace(",", "."));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Valor estimado inválido.");
                return;
            }

            EquipamentoMediator med = EquipamentoMediator.getInstancia();
            ResultadoMediator res;

            if ("Notebook".equalsIgnoreCase(tipo)) {
                boolean carregaSens = rbSensSim.isSelected();
                Notebook no = new Notebook(serial, descricao, eNovo, valor, carregaSens);
                res = med.incluirNotebook(no);
            } else {
                boolean eServidor = rbServSim.isSelected();
                Desktop de = new Desktop(serial, descricao, eNovo, valor, eServidor);
                res = med.incluirDesktop(de);
            }

            if (!res.isOperacaoRealizada()) {
                StringBuilder sb = new StringBuilder("Operação não realizada pois:");
                for (String m : res.getMensagensErro().listar()) sb.append("\n").append(m);
                JOptionPane.showMessageDialog(this, sb.toString(), "Resultado da Inclusão", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Inclusão realizada com sucesso!", "Resultado da Inclusão", JOptionPane.INFORMATION_MESSAGE);
                setModo(Modo.INICIAL);
            }
        });

        btnAlterar.addActionListener(e -> {
            String tipo = (String) cbTipo.getSelectedItem();
            String serial = txtSerial.getText().trim();
            String descricao = txtDescricao.getText().trim();
            boolean eNovo = rbNovoSim.isSelected();
            double valor;
            try {
                valor = Double.valueOf(txtValor.getText().replace(".", "").replace(",", "."));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Valor estimado inválido.");
                return;
            }

            EquipamentoMediator med = EquipamentoMediator.getInstancia();
            ResultadoMediator res;

            if ("Notebook".equalsIgnoreCase(tipo)) {
                boolean carregaSens = rbSensSim.isSelected();
                Notebook no = new Notebook(serial, descricao, eNovo, valor, carregaSens);
                res = med.alterarNotebook(no);
            } else {
                boolean eServidor = rbServSim.isSelected();
                Desktop de = new Desktop(serial, descricao, eNovo, valor, eServidor);
                res = med.alterarDesktop(de);
            }

            if (!res.isOperacaoRealizada()) {
                StringBuilder sb = new StringBuilder("Operação não realizada pois:");
                for (String m : res.getMensagensErro().listar()) sb.append("\n").append(m);
                JOptionPane.showMessageDialog(this, sb.toString(), "Resultado da Alteração", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Alteração realizada com sucesso!", "Resultado da Alteração", JOptionPane.INFORMATION_MESSAGE);
                setModo(Modo.INICIAL);
            }
        });

        btnExcluir.addActionListener(e -> {
            String serial = txtSerial.getText().trim();
            if (serial.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe o Serial para excluir.", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            EquipamentoMediator med = EquipamentoMediator.getInstancia();
            ResultadoMediator res;
            if (isNotebookSelecionado()) {
                res = med.excluirNotebook("NO" + serial);
            } else {
                res = med.excluirDesktop("DE" + serial);
            }
            if (!res.isOperacaoRealizada()) {
                StringBuilder sb = new StringBuilder("Operação não realizada pois:");
                for (String m : res.getMensagensErro().listar()) sb.append("\n").append(m);
                JOptionPane.showMessageDialog(this, sb.toString(), "Resultado da Exclusão", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Exclusão realizada com sucesso!", "Resultado da Exclusão", JOptionPane.INFORMATION_MESSAGE);
                setModo(Modo.INICIAL);
            }
        });

        btnCancelar.addActionListener(e -> setModo(Modo.INICIAL));

        btnLimpar.addActionListener(e -> {
            if (txtSerial.isEnabled()) txtSerial.setText("");
            txtDescricao.setText("");
            txtValor.setText("0,00");
            rbNovoNao.setSelected(true);
            rbSensNao.setSelected(true);
            rbServNao.setSelected(true);
        });
    }

    private boolean isNotebookSelecionado() {
        return cbTipo.getSelectedIndex() == 0;
    }

    private void alternarPaineisCondicionais(String tipo) {
        boolean isNotebook = "Notebook".equalsIgnoreCase(tipo);
        panelNotebook.setVisible(isNotebook);
        panelDesktop.setVisible(!isNotebook);
        if (isNotebook) rbServNao.setSelected(true);
        else rbSensNao.setSelected(true);
        panelNotebook.repaint();
        panelDesktop.repaint();
    }

    private void configurarCampoMonetario() {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(new Locale("pt", "BR"));
        DecimalFormat df = new DecimalFormat("#,##0.00", dfs);
        df.setParseBigDecimal(true);

        valorFormatter = new NumberFormatter(df);
        valorFormatter.setValueClass(BigDecimal.class);
        valorFormatter.setAllowsInvalid(false);
        valorFormatter.setMinimum(new BigDecimal("0.00"));
        valorFormatter.setMaximum(new BigDecimal("9999999999.99"));

        txtValor.setFormatterFactory(new DefaultFormatterFactory(valorFormatter));
        txtValor.setFocusLostBehavior(JFormattedTextField.COMMIT);
        txtValor.setText("0,00");
    }


    private void setModo(Modo modo) {
        switch (modo) {
            case INICIAL:
                cbTipo.setEnabled(true);
                txtSerial.setEnabled(true);

                txtDescricao.setEnabled(false);
                rbNovoNao.setEnabled(false);
                rbNovoSim.setEnabled(false);
                txtValor.setEnabled(false);
                rbSensNao.setEnabled(false);
                rbSensSim.setEnabled(false);
                rbServNao.setEnabled(false);
                rbServSim.setEnabled(false);

                btnNovo.setEnabled(true);
                btnBuscar.setEnabled(true);
                btnAdicionar.setEnabled(false);
                btnAlterar.setEnabled(false);
                btnExcluir.setEnabled(false);
                btnCancelar.setEnabled(false);
                btnLimpar.setEnabled(true);

                txtSerial.setText("");
                txtDescricao.setText("");
                txtValor.setText("0,00");
                rbNovoNao.setSelected(true);
                rbSensNao.setSelected(true);
                rbServNao.setSelected(true);
                
                alternarPaineisCondicionais((String) cbTipo.getSelectedItem());
                break;

            case NOVO:
                cbTipo.setEnabled(false);
                txtSerial.setEnabled(false);

                txtDescricao.setEnabled(true);
                rbNovoNao.setEnabled(true);
                rbNovoSim.setEnabled(true);
                txtValor.setEnabled(true);

                if (isNotebookSelecionado()) {
                    rbSensNao.setEnabled(true);
                    rbSensSim.setEnabled(true);
                    rbServNao.setEnabled(false);
                    rbServSim.setEnabled(false);
                } else {
                    rbServNao.setEnabled(true);
                    rbServSim.setEnabled(true);
                    rbSensNao.setEnabled(false);
                    rbSensSim.setEnabled(false);
                }

                btnNovo.setEnabled(false);
                btnBuscar.setEnabled(false);
                btnAdicionar.setEnabled(true);
                btnAlterar.setEnabled(false);
                btnExcluir.setEnabled(false);
                btnCancelar.setEnabled(true);
                btnLimpar.setEnabled(true);
                break;

            case EDICAO:
                cbTipo.setEnabled(false);
                txtSerial.setEnabled(false);

                txtDescricao.setEnabled(true);
                rbNovoNao.setEnabled(true);
                rbNovoSim.setEnabled(true);
                txtValor.setEnabled(true);

                if (isNotebookSelecionado()) {
                    rbSensNao.setEnabled(true);
                    rbSensSim.setEnabled(true);
                    rbServNao.setEnabled(false);
                    rbServSim.setEnabled(false);
                } else {
                    rbServNao.setEnabled(true);
                    rbServSim.setEnabled(true);
                    rbSensNao.setEnabled(false);
                    rbSensSim.setEnabled(false);
                }

                btnNovo.setEnabled(false);
                btnBuscar.setEnabled(false);
                btnAdicionar.setEnabled(false);
                btnAlterar.setEnabled(true);
                btnExcluir.setEnabled(true);
                btnCancelar.setEnabled(true);
                btnLimpar.setEnabled(true);
                break;
        }
    }


    private void preencherNotebook(Notebook n) {
        cbTipo.setSelectedItem("Notebook");
        txtSerial.setText(n.getSerial());
        txtDescricao.setText(n.getDescricao());
        if (n.isEhNovo()) rbNovoSim.setSelected(true); else rbNovoNao.setSelected(true);
        txtValor.setText(String.format(new Locale("pt", "BR"), "%,.2f", n.getValorEstimado()));
        if (n.isCarregaDadosSensiveis()) rbSensSim.setSelected(true); else rbSensNao.setSelected(true);
        alternarPaineisCondicionais("Notebook");
    }

    private void preencherDesktop(Desktop d) {
        cbTipo.setSelectedItem("Desktop");
        txtSerial.setText(d.getSerial());
        txtDescricao.setText(d.getDescricao());
        if (d.isEhNovo()) rbNovoSim.setSelected(true); else rbNovoNao.setSelected(true);
        txtValor.setText(String.format(new Locale("pt", "BR"), "%,.2f", d.getValorEstimado()));
        if (d.isEhServidor()) rbServSim.setSelected(true); else rbServNao.setSelected(true);
        alternarPaineisCondicionais("Desktop");
    }
}
