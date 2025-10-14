package br.edu.cs.poo.ac.ordem.gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import br.edu.cs.poo.ac.utils.ListaString;
import br.edu.cs.poo.ac.utils.StringUtils;
import br.edu.cs.poo.ac.ordem.mediators.EquipamentoMediator;
import br.edu.cs.poo.ac.ordem.mediators.ResultadoMediator;
import br.edu.cs.poo.ac.ordem.entidades.Desktop;
import br.edu.cs.poo.ac.ordem.entidades.Notebook;
import br.edu.cs.poo.ac.ordem.entidades.Equipamento;
import br.edu.cs.poo.ac.ordem.mediators.DadosEquipamento;
import br.edu.cs.poo.ac.ordem.daos.DesktopDAO;
import br.edu.cs.poo.ac.ordem.daos.NotebookDAO;

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
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

public class EquipamentoGUI extends JFrame {

    private static final long serialVersionUID = 1L;

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
        setSize(560, 420);
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
        txtSerial.setBounds(220, 55, 300, 24);
        getContentPane().add(txtSerial);

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
        spDesc.setBounds(20, 132, 500, 70);
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
        txtValor.setBounds(330, 208, 190, 24);
        configurarCampoMonetario();
        getContentPane().add(txtValor);

        panelNotebook = new JPanel(null);
        panelNotebook.setBackground(Color.WHITE);
        panelNotebook.setBorder(new EmptyBorder(6, 6, 6, 6));
        panelNotebook.setBounds(20, 242, 500, 48);
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
        panelDesktop.setBounds(20, 242, 500, 48);
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

        JButton btnBuscar = new JButton("BUSCAR");
        btnBuscar.setFont(btnFont);
        btnBuscar.setBounds(20, 310, 110, 32);
        getContentPane().add(btnBuscar);

        JButton btnAdicionar = new JButton("ADICIONAR");
        btnAdicionar.setFont(btnFont);
        btnAdicionar.setBounds(135, 310, 120, 32);
        getContentPane().add(btnAdicionar);

        JButton btnExcluir = new JButton("EXCLUIR");
        btnExcluir.setFont(btnFont);
        btnExcluir.setBounds(260, 310, 110, 32);
        getContentPane().add(btnExcluir);

        JButton btnAlterar = new JButton("ALTERAR");
        btnAlterar.setFont(btnFont);
        btnAlterar.setBounds(375, 310, 110, 32);
        getContentPane().add(btnAlterar);

        btnAdicionar.addActionListener(e -> {

            String tipo = (String) cbTipo.getSelectedItem();
            String serial = txtSerial.getText();
            String descricao = txtDescricao.getText();
            boolean eNovo = rbNovoSim.isSelected();
            double valor = Double.valueOf(txtValor.getText().replace(".", "").replace(",", "."));

            boolean carregaSens = rbSensSim.isSelected();
            boolean eServidor = rbServSim.isSelected(); 
            
            EquipamentoMediator EMAdd = EquipamentoMediator.getInstancia();
            
            if(cbTipo.getSelectedIndex() == 0) {
            	Notebook no = new Notebook(serial, descricao, eNovo, valor, carregaSens);
            	ResultadoMediator addResultado = EMAdd.incluirNotebook(no);
            	
            	if(addResultado.isOperacaoRealizada() == false) {
            		String erro = "Operação não realizada pois:";
            		for(int i = 0; i < addResultado.getMensagensErro().listar().length; i++) {
            			erro += "\n" + addResultado.getMensagensErro().listar()[i];
            		}
            		JOptionPane.showMessageDialog(this,erro,
                            "Resultado da Inclusão", JOptionPane.WARNING_MESSAGE);
            	}
            	else {
            		JOptionPane.showMessageDialog(this,"Inclusão realizada com sucesso!",
                            "Resultado da Inclusão", JOptionPane.INFORMATION_MESSAGE);
            	}
            }
            else if(cbTipo.getSelectedIndex() == 1) {
            	Desktop de = new Desktop(serial, descricao, eNovo, valor, eServidor);
            	ResultadoMediator addResultado = EMAdd.incluirDesktop(de);
            	if(addResultado.isOperacaoRealizada() == false) {
            		String erro = "Operação não realizada pois:";
            		for(int i = 0; i < addResultado.getMensagensErro().listar().length; i++) {
            			erro += "\n" + addResultado.getMensagensErro().listar()[i];
            		}
            		JOptionPane.showMessageDialog(this,erro,
                            "Resultado da Inclusão", JOptionPane.WARNING_MESSAGE);
            	}
            	else {
            		JOptionPane.showMessageDialog(this,"Inclusão realizada com sucesso!",
                            "Resultado da Inclusão", JOptionPane.INFORMATION_MESSAGE);
            	}
            }
        });

        btnBuscar.addActionListener(e -> {
        	EquipamentoMediator EM = EquipamentoMediator.getInstancia();
            if(cbTipo.getSelectedIndex() == 0) {
            	Notebook notebook = EM.buscarNotebook("NO"+txtSerial.getText());
            	if(notebook == null) {
            		JOptionPane.showMessageDialog(this,"Notebook não encontrado",
                            "Resultado da Busca", JOptionPane.INFORMATION_MESSAGE);
            	}
            	else {
            		String msg = "\nSerial: " + notebook.getSerial()
            			+ "\nDescrição: " + notebook.getDescricao()
            			+ "\nÉ novo: " + (notebook.isEhNovo() ? "SIM" : "NÃO")
            			+ "\nValor estimado: " + String.valueOf(notebook.getValorEstimado());
            		JOptionPane.showMessageDialog(this,msg,
                            "Resultado da Busca", JOptionPane.WARNING_MESSAGE);
            	}
            }
            else if(cbTipo.getSelectedIndex() == 1) {
            	Desktop desktop = EM.buscarDesktop("DE"+txtSerial.getText());
            	if(desktop == null) {
            		JOptionPane.showMessageDialog(this,"Desktop não encontrado",
                            "Resultado da Busca", JOptionPane.INFORMATION_MESSAGE);
            	}
            	else {
            		String msg = "\nSerial: " + desktop.getSerial()
        			+ "\nDescrição: " + desktop.getDescricao()
        			+ "\nÉ novo: " + (desktop.isEhNovo() ? "SIM" : "NÃO")
        			+ "\nValor estimado: " + String.valueOf(desktop.getValorEstimado());
            		JOptionPane.showMessageDialog(this,msg,
                            "Resultado da Busca", JOptionPane.WARNING_MESSAGE);
            	}
            }
            else {
            	JOptionPane.showMessageDialog(this,"Selecione uma das opções",
                        "Erro", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnExcluir.addActionListener(e -> {
        	EquipamentoMediator EMExc = EquipamentoMediator.getInstancia();
            if(cbTipo.getSelectedIndex() == 0) {
            	ResultadoMediator excResultado = EMExc.excluirNotebook("NO"+txtSerial.getText());
            	
            	if(excResultado.isOperacaoRealizada() == false) {
            		String erro = "Operação não realizada pois:";
            		for(int i = 0; i < excResultado.getMensagensErro().listar().length; i++) {
            			erro += "\n" + excResultado.getMensagensErro().listar()[i];
            		}
            		JOptionPane.showMessageDialog(this,erro,
                            "Resultado da Inclusão", JOptionPane.WARNING_MESSAGE);
            	}
            	else {
            		JOptionPane.showMessageDialog(this,"Exclusão realizada com sucesso!",
                            "Erro", JOptionPane.INFORMATION_MESSAGE);
            	}
            }
            else if(cbTipo.getSelectedIndex() == 1){
            	ResultadoMediator excResultado = EMExc.excluirDesktop("DE"+txtSerial.getText());
            	
            	if(excResultado.isOperacaoRealizada() == false) {
            		String erro = "Operação não realizada pois:";
            		for(int i = 0; i < excResultado.getMensagensErro().listar().length; i++) {
            			erro += "\n" + excResultado.getMensagensErro().listar()[i];
            		}
            		JOptionPane.showMessageDialog(this,erro,
                            "Resultado da Inclusão", JOptionPane.WARNING_MESSAGE);
            	}
            	else {
            		JOptionPane.showMessageDialog(this,"Exclusão realizada com sucesso!",
                            "Erro", JOptionPane.INFORMATION_MESSAGE);
            	}
            }
        });

        btnAlterar.addActionListener(e -> {

            String tipo = (String) cbTipo.getSelectedItem();
            String serial = txtSerial.getText();
            String descricao = txtDescricao.getText();
            boolean eNovo = rbNovoSim.isSelected();
            double valor = Double.valueOf(txtValor.getText().replace(".", "").replace(",", "."));

            boolean carregaSens = rbSensSim.isSelected();
            boolean eServidor = rbServSim.isSelected(); 
            
            EquipamentoMediator EMAlt = EquipamentoMediator.getInstancia();
            
            if(cbTipo.getSelectedIndex() == 0) {
            	Notebook no = new Notebook(serial, descricao, eNovo, valor, carregaSens);
            	ResultadoMediator altResultado = EMAlt.alterarNotebook(no);
            	
            	if(altResultado.isOperacaoRealizada() == false) {
            		String erro = "Operação não realizada pois:";
            		for(int i = 0; i < altResultado.getMensagensErro().listar().length; i++) {
            			erro += "\n" + altResultado.getMensagensErro().listar()[i];
            		}
            		JOptionPane.showMessageDialog(this,erro,
                            "Resultado da Alteração", JOptionPane.WARNING_MESSAGE);
            	}
            	else {
            		JOptionPane.showMessageDialog(this,"Alteração realizada com sucesso!",
                            "Resultado da Alteração", JOptionPane.INFORMATION_MESSAGE);
            	}
            }
            else if(cbTipo.getSelectedIndex() == 1) {
            	Desktop de = new Desktop(serial, descricao, eNovo, valor, eServidor);
            	ResultadoMediator altResultado = EMAlt.incluirDesktop(de);
            	if(altResultado.isOperacaoRealizada() == false) {
            		String erro = "Operação não realizada pois:";
            		for(int i = 0; i < altResultado.getMensagensErro().listar().length; i++) {
            			erro += "\n" + altResultado.getMensagensErro().listar()[i];
            		}
            		JOptionPane.showMessageDialog(this,erro,
                            "Resultado da Alteração", JOptionPane.WARNING_MESSAGE);
            	}
            	else {
            		JOptionPane.showMessageDialog(this,"Alteração realizada com sucesso!",
                            "Resultado da Alteração", JOptionPane.INFORMATION_MESSAGE);
            	}
            }
        });
    }
    

    private void alternarPaineisCondicionais(String tipo) {
        boolean isNotebook = "Notebook".equalsIgnoreCase(tipo);
        panelNotebook.setVisible(isNotebook);
        panelDesktop.setVisible(!isNotebook);

        if (isNotebook) {
            rbServNao.setSelected(true);
        } else {
            rbSensNao.setSelected(true);
        }
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
}