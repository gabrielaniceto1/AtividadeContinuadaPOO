package br.edu.cs.poo.ac.ordem.testes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.edu.cs.poo.ac.ordem.daos.ClienteDAO;
import br.edu.cs.poo.ac.ordem.daos.DAOGenerico;
import br.edu.cs.poo.ac.ordem.daos.DesktopDAO;
import br.edu.cs.poo.ac.ordem.daos.FechamentoOrdemServicoDAO;
import br.edu.cs.poo.ac.ordem.daos.NotebookDAO;
import br.edu.cs.poo.ac.ordem.daos.OrdemServicoDAO;

public class TesteDAOs extends TesteAbstrato {
	public TesteDAOs() {
		super(DAOGenerico.class);
	}
	@Test
	public void testeHerancaClienteDAO() {
		Assertions.assertTrue(herdaDe(ClienteDAO.class, DAOGenerico.class));
	}
	@Test
	public void testeHerancaDesktopDAO() {
		Assertions.assertTrue(herdaDe(DesktopDAO.class, DAOGenerico.class));
	}
	@Test
	public void testeHerancaNotebookDAO() {
		Assertions.assertTrue(herdaDe(NotebookDAO.class, DAOGenerico.class));
	}
	@Test
	public void testeHerancaOrdemServicoDAO() {
		Assertions.assertTrue(herdaDe(OrdemServicoDAO.class, DAOGenerico.class));
	}
	@Test
	public void testeHerancaFechamentoOrdemServicoDAO() {
		Assertions.assertTrue(herdaDe(FechamentoOrdemServicoDAO.class, DAOGenerico.class));
	}	
}