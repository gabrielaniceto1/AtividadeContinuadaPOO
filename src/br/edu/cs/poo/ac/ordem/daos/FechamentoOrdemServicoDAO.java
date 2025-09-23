package br.edu.cs.poo.ac.ordem.daos;
import java.io.Serializable;
import br.edu.cs.poo.ac.ordem.entidades.FechamentoOrdemServico;

//O identificador único, por objeto, de FechamentoOrdemServico 
//é o número da ordem de serviço.   
public class FechamentoOrdemServicoDAO extends DAOGenerico{
	public FechamentoOrdemServicoDAO() {
		super(FechamentoOrdemServico.class);
	}
	public FechamentoOrdemServico buscar(String numeroOrdemServico) {
		return (FechamentoOrdemServico)cadastroObjetos.buscar(numeroOrdemServico);		
	}
	public boolean incluir(FechamentoOrdemServico fechamentoOrdemServico) {
		if (buscar(fechamentoOrdemServico.getNumeroOrdemServico()) == null) {
			cadastroObjetos.incluir(fechamentoOrdemServico, fechamentoOrdemServico.getNumeroOrdemServico());
			return true;
		} else {
			return false;
		}
	}
	public boolean alterar(FechamentoOrdemServico fechamentoOrdemServico) {
		if (buscar(fechamentoOrdemServico.getNumeroOrdemServico()) != null) {
			cadastroObjetos.alterar(fechamentoOrdemServico, fechamentoOrdemServico.getNumeroOrdemServico());
			return true;
		} else {
			return false;
		}
	}
	public boolean excluir(String numeroOrdemServico) {
		if (buscar(numeroOrdemServico) != null) {
			cadastroObjetos.excluir(numeroOrdemServico);
			return true;
		} else {
			return false;
		}
	}	
	public FechamentoOrdemServico[] buscarTodos() {
		Serializable[] ret = cadastroObjetos.buscarTodos();
		FechamentoOrdemServico[] retorno;
		if (ret != null && ret.length > 0) {
			retorno = new FechamentoOrdemServico[ret.length];
			for (int i=0; i<ret.length; i++) {
				retorno[i] = (FechamentoOrdemServico)ret[i];
			}
		} else {
			retorno = new FechamentoOrdemServico[0]; 
		}
		return retorno;
	}
}