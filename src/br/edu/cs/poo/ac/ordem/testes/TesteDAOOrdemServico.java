package br.edu.cs.poo.ac.ordem.testes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.edu.cs.poo.ac.excecoes.ExcecaoObjetoJaExistente;
import br.edu.cs.poo.ac.excecoes.ExcecaoObjetoNaoExistente;
import br.edu.cs.poo.ac.ordem.daos.DAORegistro;
import br.edu.cs.poo.ac.ordem.entidades.Cliente;
import br.edu.cs.poo.ac.ordem.entidades.Desktop;
import br.edu.cs.poo.ac.ordem.entidades.OrdemServico;
import br.edu.cs.poo.ac.ordem.entidades.PrecoBase;
import br.edu.cs.poo.ac.ordem.entidades.StatusOrdem;
import br.edu.cs.poo.ac.ordem.testes.TesteAbstrato;

public class TesteDAOOrdemServico extends TesteAbstrato {
	private static final Cliente CLI = new Cliente("12345678901234", "ACME CO", null, LocalDate.now());
	private static final Desktop DESK = new Desktop("1234", "Desktop 1", false, 10000.00, true);
	private static final OrdemServico ORDEM = new OrdemServico(CLI, PrecoBase.REVISAO, DESK,
			LocalDateTime.of(2025, 11, 17, 23, 28), 4, 300.00);
	private static final String NUMERO = "DE20251117232812345678901234";
	private DAORegistro<OrdemServico> dao = new DAORegistro<OrdemServico>(OrdemServico.class);
	public TesteDAOOrdemServico() {
		super(OrdemServico.class);
	}	
	
	@Test
	public void teste01() {
		cadastro.incluir(ORDEM, NUMERO);
		try {
			dao.incluir(ORDEM);
			Assertions.fail();
		} catch (ExcecaoObjetoJaExistente e) {
			Assertions.assertEquals("OrdemServico já existente", e.getMessage());
		}
		OrdemServico ordemBus = (OrdemServico)cadastro.buscar(NUMERO);
		Assertions.assertEquals(1, obterQuantidadeRegistros());
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(ORDEM, ordemBus));		
	}
	@Test
	public void teste02() {
		try {
			dao.incluir(ORDEM);			
		} catch (ExcecaoObjetoJaExistente e) {
			Assertions.fail();
		}
		OrdemServico ordemBus = (OrdemServico)cadastro.buscar(NUMERO);
		Assertions.assertEquals(1, obterQuantidadeRegistros());
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(ORDEM, ordemBus));		
	}
	@Test
	public void teste03() {		
		try {
			dao.alterar(ORDEM);
			Assertions.fail();
		} catch (ExcecaoObjetoNaoExistente e) {
			Assertions.assertEquals("OrdemServico não existente", e.getMessage());
		}
		Assertions.assertEquals(0, obterQuantidadeRegistros());
		OrdemServico ordemBus = (OrdemServico)cadastro.buscar(NUMERO);
		Assertions.assertNull(ordemBus);				
	}
	@Test
	public void teste04() {
		cadastro.incluir(ORDEM, NUMERO);
		ORDEM.setStatus(StatusOrdem.CANCELADA);
		ORDEM.setMotivoCancelamento("XXXXXXXX");
		ORDEM.setDataHoraCancelamento(LocalDateTime.now());
		try {
			dao.alterar(ORDEM);			
		} catch (ExcecaoObjetoNaoExistente e) {
			Assertions.fail();
		}
		OrdemServico ordemBus = (OrdemServico)cadastro.buscar(NUMERO);
		Assertions.assertEquals(1, obterQuantidadeRegistros());
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(ORDEM, ordemBus));		
	}
	@Test
	public void teste05() {		
		try {
			dao.excluir(NUMERO);
			Assertions.fail();
		} catch (ExcecaoObjetoNaoExistente e) {
			Assertions.assertEquals("OrdemServico não existente", e.getMessage());
		}
		Assertions.assertEquals(0, obterQuantidadeRegistros());
		OrdemServico ordemBus = (OrdemServico)cadastro.buscar(NUMERO);
		Assertions.assertNull(ordemBus);				
	}
	@Test
	public void teste06() {
		cadastro.incluir(ORDEM, NUMERO);
		try {
			dao.excluir(NUMERO);			
		} catch (ExcecaoObjetoNaoExistente e) {
			Assertions.fail();
		}
		OrdemServico ordemBus = (OrdemServico)cadastro.buscar(NUMERO);
		Assertions.assertNull(ordemBus);
		Assertions.assertEquals(0, obterQuantidadeRegistros());		
	}
	@Test
	public void teste07() {
		OrdemServico ent = dao.buscar("NO00012345678901202511112233");
		Assertions.assertNull(ent);
		Assertions.assertEquals(0, obterQuantidadeRegistros());		
	}
	@Test
	public void teste08() {		
		cadastro.incluir(ORDEM, NUMERO);
		OrdemServico entBus = dao.buscar(NUMERO);
		Assertions.assertNotNull(entBus);
		Assertions.assertEquals(1, obterQuantidadeRegistros());
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(ORDEM, entBus));
	}
	@Test
	public void teste09() {
		List<OrdemServico> ents = dao.buscarTodos();		
		Assertions.assertEquals(0, obterQuantidadeRegistros());		
		Assertions.assertNotNull(ents);
		Assertions.assertTrue(ents.isEmpty());
	}
	@Test
	public void teste10() {
		OrdemServico ent1 = ORDEM;
		OrdemServico ent2 = new OrdemServico(CLI, PrecoBase.MANUTENCAO_EMERGENCIAL, DESK,
				LocalDateTime.of(2025, 12, 12, 11, 00), 3, 380.00);
		cadastro.incluir(ent1, "" + ent1.getId());
		cadastro.incluir(ent2, "" + ent2.getId());
		List<OrdemServico> ents = dao.buscarTodos();		
		Assertions.assertEquals(2, obterQuantidadeRegistros());		
		Assertions.assertNotNull(ents);
		Assertions.assertFalse(ents.isEmpty());				
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(ent1, ents.get(0)));
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(ent2, ents.get(1)));		
	}
}