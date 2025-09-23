package br.edu.cs.poo.ac.ordem.daos;
import java.io.Serializable;
import br.edu.cs.poo.ac.ordem.entidades.Cliente;

//O identificador único, por objeto, de Cliente é o cpfCnpj.  
public class ClienteDAO extends DAOGenerico{
	public ClienteDAO() {
		super(Cliente.class);
	}
	public Cliente buscar(String cpfCnpj) {
		return (Cliente)cadastroObjetos.buscar(cpfCnpj);		
	}
	public boolean incluir(Cliente cliente) {
		if (buscar(cliente.getCpfCnpj()) == null) {
			cadastroObjetos.incluir(cliente, cliente.getCpfCnpj());
			return true;
		} else {
			return false;
		}
	}
	public boolean alterar(Cliente cliente) {
		if (buscar(cliente.getCpfCnpj()) != null) {
			cadastroObjetos.alterar(cliente, cliente.getCpfCnpj());
			return true;
		} else {
			return false;
		}
	}
	public boolean excluir(String cpfCnpj) {
		if (buscar(cpfCnpj) != null) {
			cadastroObjetos.excluir(cpfCnpj);
			return true;
		} else {
			return false;
		}
	}	
	public Cliente[] buscarTodos() {
		Serializable[] ret = cadastroObjetos.buscarTodos();
		Cliente[] retorno;
		if (ret != null && ret.length > 0) {
			retorno = new Cliente[ret.length];
			for (int i=0; i<ret.length; i++) {
				retorno[i] = (Cliente)ret[i];
			}
		} else {
			retorno = new Cliente[0]; 
		}
		return retorno;
	}
}