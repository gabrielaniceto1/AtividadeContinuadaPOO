package br.edu.cs.poo.ac.ordem.testes;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.edu.cs.poo.ac.excecoes.ExcecaoObjetoJaExistente;
import br.edu.cs.poo.ac.excecoes.ExcecaoObjetoNaoExistente;
import br.edu.cs.poo.ac.ordem.daos.DAORegistro;
import br.edu.cs.poo.ac.ordem.testes.TesteAbstrato;
import br.edu.cs.poo.ac.utils.Registro;

public class TesteDAORegistro extends TesteAbstrato {
	public TesteDAORegistro() {
		super(Entidade.class);
	}
	private DAORegistro<Entidade> dao = new DAORegistro<Entidade>(Entidade.class);
	
	@Test
	public void teste01() {
		Entidade ent = new Entidade(1, "ED");
		cadastro.incluir(ent, "" + ent.getId());
		try {
			dao.incluir(ent);
			Assertions.fail();
		} catch (ExcecaoObjetoJaExistente e) {
			Assertions.assertEquals("Entidade já existente", e.getMessage());
		}
		Entidade entBus = (Entidade)cadastro.buscar("1");
		Assertions.assertEquals(1, obterQuantidadeRegistros());
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(ent, entBus));		
	}
	@Test
	public void teste02() {
		Entidade ent = new Entidade(2, "MORT");
		try {
			dao.incluir(ent);			
		} catch (ExcecaoObjetoJaExistente e) {
			Assertions.fail();			
		}
		Assertions.assertEquals(1, obterQuantidadeRegistros());
		Entidade entBus = (Entidade)cadastro.buscar("2");
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(ent, entBus));
	}
	@Test
	public void teste03() {
		Entidade ent = new Entidade(3, "KANT");		
		try {
			dao.alterar(ent);
			Assertions.fail();
		} catch (ExcecaoObjetoNaoExistente e) {
			Assertions.assertEquals("Entidade não existente", e.getMessage());
		}
		Assertions.assertEquals(0, obterQuantidadeRegistros());		
	}
	@Test
	public void teste04() {
		Entidade ent = new Entidade(4, "BART");
		cadastro.incluir(ent, "" + ent.getId());
		ent.nome = "KENT";
		try {
			dao.alterar(ent);			
		} catch (ExcecaoObjetoNaoExistente e) {
			Assertions.fail();			
		}
		Assertions.assertEquals(1, obterQuantidadeRegistros());
		Entidade entBus = (Entidade)cadastro.buscar("4");
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(ent, entBus));
	}
	@Test
	public void teste05() {	
		try {
			dao.excluir("5");
			Assertions.fail();
		} catch (ExcecaoObjetoNaoExistente e) {
			Assertions.assertEquals("Entidade não existente", e.getMessage());
		}
		Assertions.assertEquals(0, obterQuantidadeRegistros());		
	}
	@Test
	public void teste06() {
		Entidade ent = new Entidade(6, "SWEET LORETTA");
		cadastro.incluir(ent, "" + ent.getId());
		try {
			dao.excluir("6");			
		} catch (ExcecaoObjetoNaoExistente e) {
			Assertions.fail();			
		}
		Assertions.assertEquals(0, obterQuantidadeRegistros());
		Entidade entBus = (Entidade)cadastro.buscar("6");
		Assertions.assertNull(entBus);
	}
	@Test
	public void teste07() {
		Entidade ent = dao.buscar("7");
		Assertions.assertNull(ent);
		Assertions.assertEquals(0, obterQuantidadeRegistros());		
	}
	@Test
	public void teste08() {
		Entidade ent = new Entidade(8, "JO JO");
		cadastro.incluir(ent, "" + ent.getId());
		Entidade entBus = dao.buscar("8");
		Assertions.assertNotNull(entBus);
		Assertions.assertEquals(1, obterQuantidadeRegistros());
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(ent, entBus));
	}
	@Test
	public void teste09() {
		List<Entidade> ents = dao.buscarTodos();		
		Assertions.assertEquals(0, obterQuantidadeRegistros());		
		Assertions.assertNotNull(ents);
		Assertions.assertTrue(ents.isEmpty());
	}
	@Test
	public void teste10() {
		Entidade ent1 = new Entidade(9, "DESMOND");
		Entidade ent2 = new Entidade(10, "MOLLY");
		Entidade ent3 = new Entidade(11, "MICHELLE");
		cadastro.incluir(ent1, "" + ent1.getId());
		cadastro.incluir(ent2, "" + ent2.getId());
		cadastro.incluir(ent3, "" + ent3.getId());
		List<Entidade> ents = dao.buscarTodos();		
		Assertions.assertEquals(3, obterQuantidadeRegistros());		
		Assertions.assertNotNull(ents);
		Assertions.assertFalse(ents.isEmpty());				
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(ent2, ents.get(0)));
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(ent3, ents.get(1)));
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(ent1, ents.get(2)));		
	}
	public static class Entidade implements Registro {
		private static final long serialVersionUID = 1L;
		private int id;
		private String nome;
		Entidade(int id, String nome) {
			this.id = id;
			this.nome = nome;
		}
		@Override
		public String getId() {		
			return "" + id;
		}		
		public String getNome() {
			return nome;
		}
	}
}