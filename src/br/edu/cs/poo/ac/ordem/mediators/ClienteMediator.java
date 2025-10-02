package br.edu.cs.poo.ac.ordem.mediators;

import java.time.LocalDate;

import br.edu.cs.poo.ac.ordem.daos.ClienteDAO;
import br.edu.cs.poo.ac.ordem.entidades.Cliente;
import br.edu.cs.poo.ac.ordem.entidades.Contato;
import br.edu.cs.poo.ac.utils.ListaString;
import br.edu.cs.poo.ac.utils.ResultadoValidacaoCPFCNPJ;
import br.edu.cs.poo.ac.utils.StringUtils;
import br.edu.cs.poo.ac.utils.ValidadorCPFCNPJ;

public class ClienteMediator {
	private static final ClienteMediator INSTANCIA = new ClienteMediator();
	private final ClienteDAO clienteDAO = new ClienteDAO();
	
	private ClienteMediator() {}
	
	public static ClienteMediator getInstancia() {
		return INSTANCIA;
	}
	
	public ResultadoMediator incluir(Cliente cliente) {
		
	}
	public ResultadoMediator alterar(Cliente cliente) {}
	public ResultadoMediator excluir(String cpfCnpj) {}
	public Cliente buscar(String cpfCnpj) {}
	public ResultadoMediator validar(Cliente cliente) {}
}
