package br.edu.cs.poo.ac.ordem.testes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import br.edu.cs.poo.ac.ordem.daos.exemplo.ExemploDAO;
import br.edu.cs.poo.ac.ordem.daos.exemplo.Exemplo;

@Disabled("Classe suprimida temporariamente")
public class TesteExemploDAO extends TesteAbstrato {
//	private static final String NOVO_NOME = "Novo nome";
//	private static final String OUTRO_CODIGO = "002";
//	private static final String CODIGO = "001";
//	private static final String NOME = "Exemplo 1";	
//	private ExemploDAO dao = new ExemploDAO();
	public TesteExemploDAO() {
		super(Exemplo.class); 
	}
//	
//	@Test
//	public void testeBuscaSucesso() {
//		cadastro.incluir(new Exemplo(CODIGO, NOME), CODIGO);
//		Exemplo seg = dao.buscar(CODIGO);
//		Assertions.assertNotNull(seg);
//	}
//	@Test
//	public void testeBuscaIdUnicoNaoExistente() {
//		cadastro.incluir(new Exemplo(CODIGO, NOME), CODIGO);
//		Exemplo seg = dao.buscar(OUTRO_CODIGO);
//		Assertions.assertNull(seg);
//	}
//	@Test
//	public void testeExclusaoSucesso() {
//		cadastro.incluir(new Exemplo(CODIGO, NOME), CODIGO);
//		boolean ret = dao.excluir(CODIGO);
//		Exemplo seg = (Exemplo)cadastro.buscar(CODIGO);
//		Assertions.assertNull(seg);
//		Assertions.assertTrue(ret);
//	}
//	@Test
//	public void testeExclusaoIdUnicoNaoExistente() {
//		cadastro.incluir(new Exemplo(CODIGO, NOME), CODIGO);
//		boolean ret = dao.excluir(OUTRO_CODIGO);
//		Exemplo seg = (Exemplo)cadastro.buscar(CODIGO);
//		Assertions.assertNotNull(seg);		
//		Assertions.assertFalse(ret);
//	}
//	@Test
//	public void testeInclusaoSucesso() {
//		Exemplo segInc = new Exemplo(CODIGO, NOME);
//		boolean ret = dao.incluir(segInc);		
//		Assertions.assertTrue(ret);
//		Exemplo seg = (Exemplo)cadastro.buscar(CODIGO);
//		Assertions.assertNotNull(seg);
//		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(segInc, seg));	
//		Assertions.assertEquals(1, obterQuantidadeRegistros());
//	}
//	
//	@Test
//	public void testeInclusaoIdUnicoJaExistente() {
//		Exemplo seg = new Exemplo(CODIGO, NOME);
//		cadastro.incluir(seg, CODIGO);
//		boolean ret = dao.incluir(seg);
//		Assertions.assertFalse(ret);
//		Assertions.assertEquals(1, obterQuantidadeRegistros());
//	}
//	@Test
//	public void testeAlteracaoIdUnicoNaoExistente() {
//		Exemplo segInc = new Exemplo(OUTRO_CODIGO, NOME);
//		cadastro.incluir(segInc, OUTRO_CODIGO);
//		boolean ret = dao.alterar(new Exemplo(CODIGO, NOME));		
//		Assertions.assertFalse(ret);
//		Exemplo seg = (Exemplo)cadastro.buscar(CODIGO);
//		Exemplo seg1 = (Exemplo)cadastro.buscar(OUTRO_CODIGO);
//		Assertions.assertNull(seg);
//		Assertions.assertNotNull(seg1);
//		Assertions.assertEquals(1, obterQuantidadeRegistros());
//		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(segInc, seg1));
//	}
//	
//	@Test
//	public void testeAlteracaoSucesso() {
//		Exemplo segInc = new Exemplo(CODIGO, NOME);
//		cadastro.incluir(segInc, CODIGO);
//		Exemplo segNovo = new Exemplo(CODIGO, NOVO_NOME);
//		boolean ret = dao.alterar(segNovo);
//		Assertions.assertTrue(ret);
//		Assertions.assertEquals(1, obterQuantidadeRegistros());
//		Exemplo segAlt = (Exemplo)cadastro.buscar(CODIGO);
//		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(segAlt, segNovo));
//	}
//	@Test
//	public void testeBuscarTodosVazio() {
//		Exemplo[] ret = dao.buscarTodos();
//		Assertions.assertNotNull(ret);
//		Assertions.assertEquals(0, obterQuantidadeRegistros());
//		Assertions.assertEquals(0, ret.length);
//	}
//	@Test
//	public void testeBuscarTodosSucesso() {
//		String codigoAux = "003";
//		Exemplo seg1 = new Exemplo(CODIGO, NOME);
//		Exemplo seg2 = new Exemplo(OUTRO_CODIGO, NOVO_NOME);
//		Exemplo seg3 = new Exemplo(codigoAux, "Exemplo 3");
//		cadastro.incluir(seg1, CODIGO);
//		cadastro.incluir(seg2, OUTRO_CODIGO);
//		cadastro.incluir(seg3, codigoAux);
//		Exemplo[] ret = dao.buscarTodos();
//		Assertions.assertNotNull(ret);
//		Assertions.assertEquals(3, obterQuantidadeRegistros());
//		Assertions.assertEquals(3, ret.length);
//	}	
}