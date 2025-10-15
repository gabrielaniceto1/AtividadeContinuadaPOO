package br.edu.cs.poo.ac.ordem.telas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.Beans;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import br.edu.cs.poo.ac.ordem.entidades.Cliente;
import br.edu.cs.poo.ac.ordem.entidades.Contato;
import br.edu.cs.poo.ac.ordem.mediators.ClienteMediator;
import br.edu.cs.poo.ac.ordem.mediators.ResultadoMediator;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class TelaCliente extends JFrame {

	private static final long serialVersionUID = 1L;

	private enum Modo { INICIAL, NOVO, EDICAO }

	private JTextField txtCpfcnpj;
    private JTextField txtNomeCompleto;
    private JTextField txtEmail;
    private JTextField txtCelular;
    private JFormattedTextField txtDataAtual;
    private JCheckBox chkWhatsapp;

    private JButton btnBuscar, btnAdicionar, btnExcluir, btnAlterar;
    private JButton btnNovo, btnCancelar, btnLimpar;

    private final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				TelaCliente frame = new TelaCliente();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaCliente() {
		if (!Beans.isDesignTime()) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                SwingUtilities.updateComponentTreeUI(this);
            } catch (Exception ignore) {}
        }

        setTitle("Registrar Cliente");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(520, 340);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel lblGeral = new JLabel("Geral:");
        lblGeral.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        lblGeral.setBounds(20, 10, 70, 23);
        getContentPane().add(lblGeral);

        JLabel lblContato = new JLabel("Contato:");
        lblContato.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblContato.setBounds(286, 16, 80, 17);
        getContentPane().add(lblContato);

        JLabel lblNomeCompleto = new JLabel("Nome Completo");
        lblNomeCompleto.setBounds(20, 96, 104, 17);
        getContentPane().add(lblNomeCompleto);

        JLabel lblCpfcnpj = new JLabel("CPF/CNPJ");
        lblCpfcnpj.setBounds(20, 41, 80, 17);
        getContentPane().add(lblCpfcnpj);

        JLabel lblEmail = new JLabel("E-mail");
        lblEmail.setBounds(276, 41, 54, 17);
        getContentPane().add(lblEmail);

        JLabel lblCelular = new JLabel("Celular");
        lblCelular.setBounds(276, 96, 54, 17);
        getContentPane().add(lblCelular);

        JLabel lblDataDoCadastro = new JLabel("Data do cadastro:");
        lblDataDoCadastro.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
        lblDataDoCadastro.setBounds(20, 168, 122, 29);
        getContentPane().add(lblDataDoCadastro);

        txtNomeCompleto = new JTextField();
        txtNomeCompleto.setToolTipText("Digite o nome completo do cliente");
        txtNomeCompleto.setBounds(20, 124, 221, 21);
        getContentPane().add(txtNomeCompleto);

        txtCpfcnpj = new JTextField();
        txtCpfcnpj.setBounds(20, 64, 221, 21);
        getContentPane().add(txtCpfcnpj);
        txtCpfcnpj.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusLost(FocusEvent e) {
        		String cpfCnpj = txtCpfcnpj.getText().replaceAll("\\D", "");
        	    if (cpfCnpj.isEmpty()) { txtCpfcnpj.setText(""); return; }
        	    if (cpfCnpj.length() == 11) {
        	      txtCpfcnpj.setText(cpfCnpj.substring(0,3)+"."+cpfCnpj.substring(3,6)+"."+cpfCnpj.substring(6,9)+"-"+cpfCnpj.substring(9));
        	    } else if (cpfCnpj.length() == 14) {
        	      txtCpfcnpj.setText(cpfCnpj.substring(0,2)+"."+cpfCnpj.substring(2,5)+"."+cpfCnpj.substring(5,8)+"/"+
        	    		  cpfCnpj.substring(8,12)+"-"+cpfCnpj.substring(12));
        	    } else {
        	      txtCpfcnpj.setText(cpfCnpj);
        	    }
        	}
        });
        txtCpfcnpj.setToolTipText("Digite o cpf/cnpj do cliente");

        txtEmail = new JTextField();
        txtEmail.setToolTipText("Digite email do cliente");
        txtEmail.setBounds(276, 64, 207, 21);
        getContentPane().add(txtEmail);

        txtCelular = new JTextField();
        txtCelular.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusLost(FocusEvent e) {
                String raw = txtCelular.getText();
                if (raw.matches("^\\(\\d{2}\\)\\d{8,9}$")) return;
                String digits = raw.replaceAll("\\D", "");
                if (digits.length() == 11 || digits.length() == 10) {
                    String dd  = digits.substring(0, 2);
                    String num = digits.substring(2);
                    txtCelular.setText("(" + dd + ")" + num);
                }
        	}
        });
        txtCelular.setToolTipText("Digite o celular do cliente");
        txtCelular.setBounds(276, 119, 207, 21);
        getContentPane().add(txtCelular);

        chkWhatsapp = new JCheckBox("é WhatsApp?");
        chkWhatsapp.setBackground(Color.WHITE);
        chkWhatsapp.setBounds(276, 146, 110, 17);
        getContentPane().add(chkWhatsapp);

        try {
            MaskFormatter mf = new MaskFormatter("##/##/####");
            txtDataAtual = new JFormattedTextField(mf);
        } catch (Exception ex) {
            txtDataAtual = new JFormattedTextField();
        }
        txtDataAtual.setToolTipText("Data Atual");
        txtDataAtual.setBounds(20, 203, 112, 21);
        getContentPane().add(txtDataAtual);

        if (!Beans.isDesignTime()) {
            LocalDate hoje = LocalDate.now();
            txtDataAtual.setText(hoje.format(FMT));
            txtDataAtual.setFocusLostBehavior(JFormattedTextField.COMMIT);
            txtDataAtual.setCaretPosition(txtDataAtual.getText().length());
        }

        Font btnFont = new Font(Font.SANS_SERIF, Font.BOLD, 12);

        btnNovo = new JButton("NOVO");
        btnNovo.setFont(btnFont);
        btnNovo.setForeground(Color.BLACK);
        btnNovo.setBounds(20, 230, 95, 30);
        getContentPane().add(btnNovo);

        btnBuscar = new JButton("BUSCAR");
        btnBuscar.setFont(btnFont);
        btnBuscar.setForeground(Color.BLACK);
        btnBuscar.setBounds(120, 230, 105, 30);
        getContentPane().add(btnBuscar);

        btnAdicionar = new JButton("ADICIONAR");
        btnAdicionar.setFont(btnFont);
        btnAdicionar.setForeground(Color.BLACK);
        btnAdicionar.setBounds(230, 230, 120, 30);
        getContentPane().add(btnAdicionar);

        btnAlterar = new JButton("ALTERAR");
        btnAlterar.setFont(btnFont);
        btnAlterar.setForeground(Color.BLACK);
        btnAlterar.setBounds(355, 230, 95, 30);
        getContentPane().add(btnAlterar);

        btnExcluir = new JButton("EXCLUIR");
        btnExcluir.setFont(btnFont);
        btnExcluir.setForeground(Color.BLACK);
        btnExcluir.setBounds(20, 265, 95, 30);
        getContentPane().add(btnExcluir);

        btnCancelar = new JButton("CANCELAR");
        btnCancelar.setFont(btnFont);
        btnCancelar.setForeground(Color.BLACK);
        btnCancelar.setBounds(120, 265, 105, 30);
        getContentPane().add(btnCancelar);

        btnLimpar = new JButton("LIMPAR");
        btnLimpar.setFont(btnFont);
        btnLimpar.setForeground(Color.BLACK);
        btnLimpar.setBounds(230, 265, 120, 30);
        getContentPane().add(btnLimpar);

        setModo(Modo.INICIAL);

        btnNovo.addActionListener(e -> {
            String id = txtCpfcnpj.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "CPF/CNPJ deve ser preenchido!");
                return;
            }
            ClienteMediator med = ClienteMediator.getInstancia();
            Cliente existente = med.buscar(id);
            if (existente != null) {
                JOptionPane.showMessageDialog(this, "Cliente já existente!");
                return;
            }
            limparCamposDados();
            setModo(Modo.NOVO);
        });

        btnBuscar.addActionListener(e -> {
            if (Beans.isDesignTime()) return;
            String id = txtCpfcnpj.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "CPF/CNPJ deve ser preenchido!");
                return;
            }
            ClienteMediator med = ClienteMediator.getInstancia();
            Cliente cliente = med.buscar(id);
            if (cliente == null) {
                JOptionPane.showMessageDialog(this, "Nenhum cliente encontrado.",
                        "Resultado da Busca", JOptionPane.WARNING_MESSAGE);
                return;
            }
            preencherTela(cliente);
            setModo(Modo.EDICAO);
        });

        btnAdicionar.addActionListener(e -> {
        	ClienteMediator addMediator = ClienteMediator.getInstancia();
        	try {
                LocalDate addData = LocalDate.parse(txtDataAtual.getText(), FMT);
                Contato addContato = new Contato(txtEmail.getText(), txtCelular.getText(), chkWhatsapp.isSelected());
                Cliente addCliente = new Cliente(txtCpfcnpj.getText().trim(), txtNomeCompleto.getText(), addContato, addData);
                ResultadoMediator addResultado = addMediator.incluir(addCliente);

                if(!addResultado.isOperacaoRealizada()) {
                    String erros = "Operação não realizada pois:";
                    for(String m : addResultado.getMensagensErro().listar()) erros += "\n" + m;
                    JOptionPane.showMessageDialog(this, erros, "Resultado da Inclusão", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!", "Resultado da Inclusão", JOptionPane.INFORMATION_MESSAGE);
                    setModo(Modo.INICIAL);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Data inválida (use dd/MM/yyyy).", "Atenção", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnAlterar.addActionListener(e -> {
        	ClienteMediator altMediator = ClienteMediator.getInstancia();
        	try {
                LocalDate altData = LocalDate.parse(txtDataAtual.getText(), FMT);
                Contato altContato = new Contato(txtEmail.getText(), txtCelular.getText(), chkWhatsapp.isSelected());
                Cliente altCliente = new Cliente(txtCpfcnpj.getText().trim(), txtNomeCompleto.getText(), altContato, altData);
                ResultadoMediator altResultado = altMediator.alterar(altCliente);

                if(!altResultado.isOperacaoRealizada()) {
                    String erros = "Operação não realizada pois:";
                    for(String m : altResultado.getMensagensErro().listar()) erros += "\n" + m;
                    JOptionPane.showMessageDialog(this, erros, "Resultado da Alteração", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Cadastro alterado com sucesso!", "Resultado da Alteração", JOptionPane.INFORMATION_MESSAGE);
                    setModo(Modo.INICIAL);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Data inválida (use dd/MM/yyyy).", "Atenção", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnExcluir.addActionListener(e -> {
        	ClienteMediator excMediator = ClienteMediator.getInstancia();
        	String id = txtCpfcnpj.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe o CPF/CNPJ para excluir.", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
        	ResultadoMediator resExcCliente = excMediator.excluir(id);
        	if(!resExcCliente.isOperacaoRealizada()) {
        		String excErros = "Operação não realizada pois:";
        		for(String m : resExcCliente.getMensagensErro().listar()) excErros += "\n" + m;
        		JOptionPane.showMessageDialog(this, excErros, "Resultado da Exclusão", JOptionPane.WARNING_MESSAGE);
        	} else {
        		JOptionPane.showMessageDialog(this, "Exclusão realizada com sucesso!", "Resultado da Exclusão", JOptionPane.INFORMATION_MESSAGE);
                setModo(Modo.INICIAL);
        	}
        });

        btnCancelar.addActionListener(e -> setModo(Modo.INICIAL));

        btnLimpar.addActionListener(e -> {
            if (txtCpfcnpj.isEnabled()) txtCpfcnpj.setText("");
            limparCamposDados();
        });
    }

    private void setModo(Modo modo) {
        switch (modo) {
            case INICIAL:
                txtCpfcnpj.setEnabled(true);

                txtNomeCompleto.setEnabled(false);
                txtEmail.setEnabled(false);
                txtCelular.setEnabled(false);
                chkWhatsapp.setEnabled(false);
                txtDataAtual.setEnabled(false);

                btnNovo.setEnabled(true);
                btnBuscar.setEnabled(true);

                btnAdicionar.setEnabled(false);
                btnAlterar.setEnabled(false);
                btnExcluir.setEnabled(false);
                btnCancelar.setEnabled(false);
                btnLimpar.setEnabled(true);

                txtCpfcnpj.setText("");
                limparCamposDados();
                txtDataAtual.setText(LocalDate.now().format(FMT));
                break;

            case NOVO:
                txtCpfcnpj.setEnabled(false);

                txtNomeCompleto.setEnabled(true);
                txtEmail.setEnabled(true);
                txtCelular.setEnabled(true);
                chkWhatsapp.setEnabled(true);
                txtDataAtual.setEnabled(true);

                btnNovo.setEnabled(false);
                btnBuscar.setEnabled(false);
                btnAdicionar.setEnabled(true);
                btnAlterar.setEnabled(false);
                btnExcluir.setEnabled(false);
                btnCancelar.setEnabled(true);
                btnLimpar.setEnabled(true);
                break;

            case EDICAO:
                txtCpfcnpj.setEnabled(false);

                txtNomeCompleto.setEnabled(true);
                txtEmail.setEnabled(true);
                txtCelular.setEnabled(true);
                chkWhatsapp.setEnabled(true);
                txtDataAtual.setEnabled(true);

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

    private void limparCamposDados() {
        txtNomeCompleto.setText("");
        txtEmail.setText("");
        txtCelular.setText("");
        chkWhatsapp.setSelected(false);
    }

    private void preencherTela(Cliente cliente) {
        txtCpfcnpj.setText(safe(cliente.getCpfCnpj()));
        txtNomeCompleto.setText(safe(cliente.getNome()));
        if (cliente.getContato() != null) {
            txtEmail.setText(safe(cliente.getContato().getEmail()));
            txtCelular.setText(safe(cliente.getContato().getCelular()));
            chkWhatsapp.setSelected(cliente.getContato().isEhZap());
        } else {
            txtEmail.setText("");
            txtCelular.setText("");
            chkWhatsapp.setSelected(false);
        }
        if (txtDataAtual.getText() == null || txtDataAtual.getText().trim().isEmpty()) {
            txtDataAtual.setText(LocalDate.now().format(FMT));
        }
    }

	private static String safe(String s) { return s == null ? "" : s; }
}
