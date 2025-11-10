package br.edu.cs.poo.ac.ordem.testes;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import br.edu.cs.poo.ac.ordem.daos.DAOGenerico;
import br.edu.cs.poo.ac.ordem.testes.TesteAbstrato;
import br.edu.cs.poo.ac.utils.Registro;

public class TesteDAOGenerico extends TesteAbstrato {
	public TesteDAOGenerico() {
		super(Entidade.class);
	}
	private DAOGenerico dao = new DAOGenerico() {			
		@Override
		public Class<?> getClasseEntidade() {
			return Entidade.class;
		}
	};

	@Test
	public void teste01() {
		try {
			Method metodo = DAOGenerico.class.getDeclaredMethod("getClasseEntidade");
			Assertions.assertTrue(Modifier.isAbstract(metodo.getModifiers()));
		} catch (Exception e) {
			Assertions.fail(e);
		}		
	} 
	@Test
	public void teste02() {
		Entidade ent = new Entidade(1, "ENT1");
		Assertions.assertTrue(dao.incluir(ent));
		Assertions.assertEquals(1, obterQuantidadeRegistros());
		Entidade entBuscado = (Entidade)cadastro.buscar(ent.getId());
		Assertions.assertNotNull(entBuscado);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(ent, entBuscado));
	}
	@Test
	public void teste03() {
		Entidade ent = new Entidade(2, "ENT2");
		cadastro.incluir(ent, ent.getId());
		Assertions.assertFalse(dao.incluir(ent));
		Assertions.assertEquals(1, obterQuantidadeRegistros());
		Entidade entBuscado = (Entidade)cadastro.buscar(ent.getId());
		Assertions.assertNotNull(entBuscado);
	}
	@Test
	public void teste04() {
		Entidade ent = new Entidade(3, "ENT3");
		Assertions.assertFalse(dao.alterar(ent));
		Assertions.assertEquals(0, obterQuantidadeRegistros());
		Entidade entBuscado = (Entidade)cadastro.buscar(ent.getId());
		Assertions.assertNull(entBuscado);		
	}
	@Test
	public void teste05() {
		Entidade ent = new Entidade(4, "ENT4");
		cadastro.incluir(ent, ent.getId());
		ent = new Entidade(4, "ENT4ALT");
		Assertions.assertTrue(dao.alterar(ent));
		Assertions.assertEquals(1, obterQuantidadeRegistros());
		Entidade entBuscado = (Entidade)cadastro.buscar(ent.getId());
		Assertions.assertNotNull(entBuscado);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(ent, entBuscado));
	}	
	@Test
	public void teste06() {
		Entidade ent = new Entidade(5, "ENT5");
		Entidade ent1 = new Entidade(6, "ENT6");
		cadastro.incluir(ent1, ent1.getId());
		Assertions.assertFalse(dao.excluir(ent.getId()));
		Assertions.assertEquals(1, obterQuantidadeRegistros());
		Entidade entBuscado = (Entidade)cadastro.buscar(ent1.getId());
		Assertions.assertNotNull(entBuscado);		
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(ent1, entBuscado));
	}
	@Test
	public void teste07() {
		Entidade ent = new Entidade(7, "ENT7");
		Entidade ent1 = new Entidade(8, "ENT8");
		cadastro.incluir(ent, ent.getId());
		cadastro.incluir(ent1, ent1.getId());
		Assertions.assertTrue(dao.excluir(ent.getId()));
		Assertions.assertEquals(1, obterQuantidadeRegistros());
		Entidade entBuscado = (Entidade)cadastro.buscar(ent.getId());
		Assertions.assertNull(entBuscado);		
		Entidade entBuscado1 = (Entidade)cadastro.buscar(ent1.getId());
		Assertions.assertNotNull(entBuscado1);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(ent1, entBuscado1));		
	}		
	@Test
	public void teste08() {
		Entidade ent = new Entidade(9, "ENT9");
		Entidade ent1 = new Entidade(10, "ENT10");
		cadastro.incluir(ent1, ent1.getId());
		Assertions.assertNull(dao.buscar(ent.getId()));
		Assertions.assertEquals(1, obterQuantidadeRegistros());
		Entidade entBuscado = (Entidade)cadastro.buscar(ent1.getId());
		Assertions.assertNotNull(entBuscado);		
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(ent1, entBuscado));
	}
	@Test
	public void teste09() {
		Entidade ent = new Entidade(11, "ENT11");
		Entidade ent1 = new Entidade(12, "ENT12");
		cadastro.incluir(ent, ent.getId());
		cadastro.incluir(ent1, ent1.getId());
		Entidade ret = (Entidade)dao.buscar(ent.getId());
		Assertions.assertNotNull(ret);
		Assertions.assertEquals(2, obterQuantidadeRegistros());
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(ent, ret));				
	}		
	@Test
	public void teste10() {
		Registro[] regs = dao.buscarTodos();
		Assertions.assertNotNull(regs);
		Assertions.assertEquals(0, obterQuantidadeRegistros());
		Assertions.assertEquals(0, regs.length);		
		Entidade ent1 = new Entidade(13, "ENT13");
		Entidade ent2 = new Entidade(14, "ENT14");
		Entidade ent3 = new Entidade(15, "ENT15");
		cadastro.incluir(ent1, ent1.getId());
		cadastro.incluir(ent2, ent2.getId());
		cadastro.incluir(ent3, ent3.getId());
		regs = dao.buscarTodos();
		Assertions.assertNotNull(regs);
		Assertions.assertEquals(3, obterQuantidadeRegistros());
		Assertions.assertEquals(3, regs.length);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(ent1, regs[0]));
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(ent2, regs[1]));
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(ent3, regs[2]));		
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
