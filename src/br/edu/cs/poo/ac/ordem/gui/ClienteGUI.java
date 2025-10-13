package br.edu.cs.poo.ac.ordem.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.beans.Beans;
import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatterFactory;
import java.text.ParseException;
import java.awt.event.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import br.edu.cs.poo.ac.ordem.daos.ClienteDAO;
import br.edu.cs.poo.ac.ordem.entidades.Cliente;
import br.edu.cs.poo.ac.ordem.entidades.Contato;
import br.edu.cs.poo.ac.utils.ListaString;
import br.edu.cs.poo.ac.utils.ResultadoValidacaoCPFCNPJ;
import br.edu.cs.poo.ac.utils.StringUtils;
import br.edu.cs.poo.ac.utils.ValidadorCPFCNPJ;
import br.edu.cs.poo.ac.ordem.mediators.ClienteMediator;
import br.edu.cs.poo.ac.ordem.mediators.ResultadoMediator;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;


@SuppressWarnings("serial")
public class ClienteGUI extends JFrame {

    private JTextField txtCpfcnpj;
    private JTextField txtNomeCompleto;
    private JTextField txtEmail;
    private JTextField txtCelular;
    private JFormattedTextField txtDataAtual;
    private JCheckBox chkWhatsapp;


    public ClienteGUI() {
        if (!Beans.isDesignTime()) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                SwingUtilities.updateComponentTreeUI(this);
            } catch (Exception ignore) {}
        }

        setTitle("Registrar Cliente");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 300);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel lblGeral = new JLabel("Geral:");
        lblGeral.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        lblGeral.setBounds(20, 10, 70, 23);
        getContentPane().add(lblGeral);

        JLabel lblContato = new JLabel("Contato:");
        lblContato.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblContato.setBounds(246, 16, 80, 17);
        getContentPane().add(lblContato);

        JLabel lblNomeCompleto = new JLabel("Nome Completo");
        lblNomeCompleto.setBounds(20, 96, 104, 17);
        getContentPane().add(lblNomeCompleto);

        JLabel lblCpfcnpj = new JLabel("CPF/CNPJ");
        lblCpfcnpj.setBounds(20, 41, 80, 17);
        getContentPane().add(lblCpfcnpj);

        JLabel lblEmail = new JLabel("E-mail");
        lblEmail.setBounds(236, 41, 54, 17);
        getContentPane().add(lblEmail);

        JLabel lblCelular = new JLabel("Celular");
        lblCelular.setBounds(236, 96, 54, 17);
        getContentPane().add(lblCelular);

        JLabel lblDataDoCadastro = new JLabel("Data do cadastro:");
        lblDataDoCadastro.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
        lblDataDoCadastro.setBounds(20, 168, 122, 29);
        getContentPane().add(lblDataDoCadastro);

        txtNomeCompleto = new JTextField();
        txtNomeCompleto.setToolTipText("Digite o nome completo do cliente");
        txtNomeCompleto.setBounds(20, 124, 171, 21);
        getContentPane().add(txtNomeCompleto);
        
        
        txtCpfcnpj = new JTextField();
        txtCpfcnpj.setBounds(20, 64, 171, 21);
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
        txtCpfcnpj.setBounds(20, 64, 171, 21);
        getContentPane().add(txtCpfcnpj);

        txtEmail = new JTextField();
        txtEmail.setToolTipText("Digite email do cliente");
        txtEmail.setBounds(236, 64, 177, 21);
        getContentPane().add(txtEmail);

        txtCelular = new JTextField();
        txtCelular.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusLost(FocusEvent e) {
        		String celular = txtCelular.getText();
        		if(celular.length() == 13 && celular.charAt(0) == '(' && celular.charAt(3) == ')') {
        			return;
        		}
        		if(celular.length() == 11) {
        			String celular1 = celular.replaceAll("\\D", "");
        			txtCelular.setText("("+ celular1.substring(0,2)
        					+ ")" + celular1.substring(2));
        		}
        	}
        });
        txtCelular.setToolTipText("Digite o celular do cliente");
        txtCelular.setBounds(236, 119, 176, 21);
        getContentPane().add(txtCelular);

        chkWhatsapp = new JCheckBox("é WhatsApp?");
        chkWhatsapp.setBackground(Color.WHITE);
        chkWhatsapp.setBounds(236, 146, 110, 17);
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
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            txtDataAtual.setText(hoje.format(fmt));
            
            txtDataAtual.setFocusLostBehavior(JFormattedTextField.COMMIT);
            txtDataAtual.setCaretPosition(txtDataAtual.getText().length());
        }

        Font btnFont = new Font(Font.SANS_SERIF, Font.BOLD, 12);

        JButton btnBuscar = new JButton("BUSCAR");
        btnBuscar.setFont(btnFont);
        btnBuscar.setForeground(Color.BLACK);
        btnBuscar.setBounds(20, 230, 105, 30);
        getContentPane().add(btnBuscar);

        JButton btnAdicionar = new JButton("ADICIONAR");
        btnAdicionar.setFont(btnFont);
        btnAdicionar.setForeground(Color.BLACK);
        btnAdicionar.setBounds(130, 230, 110, 30);
        getContentPane().add(btnAdicionar);

        JButton btnExcluir = new JButton("EXCLUIR");
        btnExcluir.setFont(btnFont);
        btnExcluir.setForeground(Color.BLACK);
        btnExcluir.setBounds(245, 230, 95, 30);
        getContentPane().add(btnExcluir);

        JButton btnAlterar = new JButton("ALTERAR");
        btnAlterar.setFont(btnFont);
        btnAlterar.setForeground(Color.BLACK);
        btnAlterar.setBounds(345, 230, 95, 30);
        getContentPane().add(btnAlterar);


        btnAdicionar.addActionListener(e -> {
        	ClienteMediator addMediator = ClienteMediator.getInstancia();
        	DateTimeFormatter FMT1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        	LocalDate addData = LocalDate.parse(txtDataAtual.getText(),FMT1);
        	Contato addContato = new Contato(txtEmail.getText(), txtCelular.getText(), chkWhatsapp.isSelected());
        	Cliente addCliente = new Cliente(txtCpfcnpj.getText(),txtNomeCompleto.getText(),addContato,addData);
        	ResultadoMediator addResultado = addMediator.incluir(addCliente);
        	
        	if(addResultado.isOperacaoRealizada() == false) {
        		String erros = "Operação não realizada pois:";
        		for(int i = 0; i < addResultado.getMensagensErro().listar().length; i++) {
        			erros += "\n" + addResultado.getMensagensErro().listar()[i];
        		}
        		JOptionPane.showMessageDialog(this, erros,
                        "Resultado da Inclusão", JOptionPane.WARNING_MESSAGE);
        	}
        	else {
        		JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!",
                        "Resultado da Inclusão", JOptionPane.INFORMATION_MESSAGE);
        	}
        });
        btnBuscar.addActionListener(e -> {
            if (Beans.isDesignTime()) return;
            ClienteMediator busMediator = ClienteMediator.getInstancia();
            Cliente cliente = busMediator.buscar(txtCpfcnpj.getText());
            if (cliente == null) {
                JOptionPane.showMessageDialog(this, "Nenhum cliente encontrado.",
                        "Resultado da Busca", JOptionPane.WARNING_MESSAGE);
            } else {
                String nome = safe(cliente.getNome());
                String cpfCnpj = safe(cliente.getCpfCnpj());
                String idade = String.valueOf(cliente.getIdadeCadastro());
                String email = cliente.getContato() != null ? safe(cliente.getContato().getEmail()) : "";
                String celular = cliente.getContato() != null ? safe(cliente.getContato().getCelular()) : "";
                String eZap = (cliente.getContato() != null && cliente.getContato().isEhZap()) ? "SIM" : "NÃO";

                String msg = "Nome: " + nome +
                        "\nCPF/CNPJ: " + cpfCnpj +
                        "\nData de cadastro: " + idade +
                        "\nE-mail: " + email +
                        "\nCelular: " + celular +
                        "\nÉ WhatsApp: " + eZap;

                JOptionPane.showMessageDialog(this, msg, "Resultado da Busca",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        btnExcluir.addActionListener(e -> {
        	ClienteMediator excMediator = ClienteMediator.getInstancia();
        	String excCliente = txtCpfcnpj.getText();
        	ResultadoMediator resExcCliente = excMediator.excluir(excCliente);
        	
        	if(resExcCliente.isOperacaoRealizada() == false) {
        		String excErros = "Operação não realizada pois:";
        		for(int i = 0; i < resExcCliente.getMensagensErro().listar().length; i++) {
        			excErros += "\n" + resExcCliente.getMensagensErro().listar()[i];
        		}
        		JOptionPane.showMessageDialog(this, excErros,
                        "Resultado da Inclusão", JOptionPane.WARNING_MESSAGE);
        	}
        	else {
        		JOptionPane.showMessageDialog(this, "Exclusão realizada com sucesso!", "Resultado da Exclusão",
                        JOptionPane.INFORMATION_MESSAGE);
        	}
        });
        btnAlterar.addActionListener(e -> {
        	ClienteMediator altMediator = ClienteMediator.getInstancia();
        	DateTimeFormatter FMT1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        	LocalDate altData = LocalDate.parse(txtDataAtual.getText(),FMT1);
        	Contato altContato = new Contato(txtEmail.getText(), txtCelular.getText(), chkWhatsapp.isSelected());
        	Cliente altCliente = new Cliente(txtCpfcnpj.getText(),txtNomeCompleto.getText(),altContato,altData);
        	ResultadoMediator altResultado = altMediator.alterar(altCliente);
        	
        	if(altResultado.isOperacaoRealizada() == false) {
        		String erros = "Operação não realizada pois:";
        		for(int i = 0; i < altResultado.getMensagensErro().listar().length; i++) {
        			erros += "\n" + altResultado.getMensagensErro().listar()[i];
        		}
        		JOptionPane.showMessageDialog(this, erros,
                        "Resultado da Alteração", JOptionPane.WARNING_MESSAGE);
        	}
        	else {
        		JOptionPane.showMessageDialog(this, "Cadastro alterado com sucesso!",
                        "Resultado da Alteração", JOptionPane.INFORMATION_MESSAGE);
        	}
        });
    }

    private static String safe(String s) { return s == null ? "" : s; }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new ClienteGUI().setVisible(true));
    }
}
