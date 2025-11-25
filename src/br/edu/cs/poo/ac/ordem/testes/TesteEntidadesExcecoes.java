package br.edu.cs.poo.ac.ordem.testes;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.edu.cs.poo.ac.excecoes.ExcecaoNegocio;
import br.edu.cs.poo.ac.excecoes.ExcecaoObjetoJaExistente;
import br.edu.cs.poo.ac.excecoes.ExcecaoObjetoNaoExistente;
import br.edu.cs.poo.ac.ordem.entidades.Cliente;
import br.edu.cs.poo.ac.ordem.entidades.Contato;
import br.edu.cs.poo.ac.ordem.entidades.Desktop;
import br.edu.cs.poo.ac.ordem.entidades.FechamentoOrdemServico;
import br.edu.cs.poo.ac.ordem.entidades.Notebook;
import br.edu.cs.poo.ac.ordem.entidades.OrdemServico;
import br.edu.cs.poo.ac.ordem.entidades.PrecoBase;
import br.edu.cs.poo.ac.ordem.entidades.StatusOrdem;
import br.edu.cs.poo.ac.ordem.mediators.ResultadoMediator;
import br.edu.cs.poo.ac.utils.ListaString;

/*
 * O tipo StatusOrdem é um enum.
 * Os tipos StatusOrdem e PrecoBase não devem ter métodos set para os seus atributos. 
 */
public class TesteEntidadesExcecoes {
	private static final Desktop DESK = new Desktop("1234", "Desktop 1", false, 10000.00, true);
	private static final Notebook NOTE1 = new Notebook("4321", "Notebook 2", true, 3000.00, false); 
	private static final Contato CONTATO = new Contato("abc@def.com", "8199988771", true);
	private static final Cliente CLI = new Cliente("12345678901234", "ACME CO", CONTATO, LocalDate.now());
	private static final Cliente CLI1 = new Cliente("12345678901", "JERERECA", CONTATO, LocalDate.now());
	@Test
	public void teste01() {
		LocalDateTime now = LocalDateTime.now();
		LocalDate now1 = LocalDate.now();
		LocalDateTime now2 = LocalDateTime.now();
		String motivo = "MOTIVO 1";
		String relFechamento = "REL 010101";
		String vendedor = "JOHN";
		FechamentoOrdemServico fechamento = new FechamentoOrdemServico("DE20251111111112345678901234", now1, true, relFechamento);
		OrdemServico ordem = new OrdemServico(CLI, PrecoBase.LIMPEZA, DESK, now, 4, 200.0);
		ordem.setDadosFechamento(fechamento);
		ordem.setDataHoraCancelamento(now2);
		ordem.setMotivoCancelamento(motivo);
		ordem.setStatus(StatusOrdem.ABERTA);
		ordem.setVendedor(vendedor);
		Assertions.assertEquals(CLI, ordem.getCliente());
		Assertions.assertEquals(PrecoBase.LIMPEZA, ordem.getPrecoBase());
		Assertions.assertEquals(DESK, ordem.getEquipamento());
		Assertions.assertEquals(now, ordem.getDataHoraAbertura());
		Assertions.assertEquals(4, ordem.getPrazoEmDias());
		Assertions.assertEquals(200.0, ordem.getValor());
		Assertions.assertEquals(fechamento, ordem.getDadosFechamento());
		Assertions.assertEquals(now2, ordem.getDataHoraCancelamento());
		Assertions.assertEquals(motivo, ordem.getMotivoCancelamento());
		Assertions.assertEquals(StatusOrdem.ABERTA, ordem.getStatus());
		Assertions.assertEquals(vendedor, ordem.getVendedor());	
		ordem.setCliente(CLI1);
		ordem.setPrecoBase(PrecoBase.REVISAO);
		ordem.setEquipamento(NOTE1);
		LocalDateTime now3 = LocalDateTime.now();
		ordem.setDataHoraAbertura(now3);
		ordem.setPrazoEmDias(6);
		ordem.setValor(400.00);
		Assertions.assertEquals(CLI1, ordem.getCliente());
		Assertions.assertEquals(PrecoBase.REVISAO, ordem.getPrecoBase());
		Assertions.assertEquals(NOTE1, ordem.getEquipamento());
		Assertions.assertEquals(now3, ordem.getDataHoraAbertura());
		Assertions.assertEquals(6, ordem.getPrazoEmDias());
		Assertions.assertEquals(400.00, ordem.getValor());
	}
	@Test
	public void teste02() {
		Constructor<?>[] construtores1 = OrdemServico.class.getDeclaredConstructors();
		Assertions.assertNotNull(construtores1);
		Assertions.assertEquals(1, construtores1.length);
		Constructor<?>[] construtores2 = FechamentoOrdemServico.class.getDeclaredConstructors();
		Assertions.assertNotNull(construtores2);
		Assertions.assertEquals(1, construtores2.length);
		Constructor<?>[] construtores3 = ExcecaoNegocio.class.getDeclaredConstructors();
		Assertions.assertNotNull(construtores3);
		Assertions.assertEquals(1, construtores3.length);
		Constructor<?>[] construtores4 = ExcecaoObjetoJaExistente.class.getDeclaredConstructors();
		Assertions.assertNotNull(construtores4);
		Assertions.assertEquals(1, construtores4.length);
		Constructor<?>[] construtores5 = ExcecaoObjetoNaoExistente.class.getDeclaredConstructors();
		Assertions.assertNotNull(construtores5);
		Assertions.assertEquals(1, construtores5.length);
		Constructor<?>[] construtores6 = PrecoBase.class.getDeclaredConstructors();
		Assertions.assertNotNull(construtores6);
		Assertions.assertEquals(1, construtores6.length);
		Assertions.assertTrue(Modifier.isPrivate(construtores6[0].getModifiers()));
		Constructor<?>[] construtores7 = StatusOrdem.class.getDeclaredConstructors();
		Assertions.assertNotNull(construtores7);
		Assertions.assertEquals(1, construtores7.length);
		Assertions.assertTrue(Modifier.isPrivate(construtores7[0].getModifiers()));		
	}
	@Test
	public void teste03() {
		String numero = "DE20251111111112345678901234";
		LocalDate now1 = LocalDate.now();
		String relatorio = "bla bla bla";
		LocalDate now2 = LocalDate.now().plusDays(1);
		String relatorio2 = "bla1 bla1 bla1";		
		FechamentoOrdemServico fecho = new FechamentoOrdemServico(numero, now1, false, relatorio);
		Assertions.assertEquals(numero, fecho.getNumeroOrdemServico());
		Assertions.assertEquals(now1, fecho.getDataFechamento());
		Assertions.assertEquals(false, fecho.isPago());
		Assertions.assertEquals(relatorio, fecho.getRelatorioFinal());
		fecho.setDataFechamento(now2);
		fecho.setPago(true);
		fecho.setRelatorioFinal(relatorio2);
		Assertions.assertEquals(numero, fecho.getNumeroOrdemServico());
		Assertions.assertEquals(now2, fecho.getDataFechamento());
		Assertions.assertEquals(true, fecho.isPago());
		Assertions.assertEquals(relatorio2, fecho.getRelatorioFinal());		
	}
	@Test
	public void teste04() {
		String msg = "SSS";
		ResultadoMediator res = new ResultadoMediator(true, true, new ListaString());
		ExcecaoNegocio en = new ExcecaoNegocio(res);		
		Assertions.assertTrue(en.getRes() == res);
		ExcecaoObjetoJaExistente eoje = new ExcecaoObjetoJaExistente(msg);
		Assertions.assertEquals(msg, eoje.getMessage());
		ExcecaoObjetoNaoExistente eone = new ExcecaoObjetoNaoExistente(msg);
		Assertions.assertEquals(msg, eone.getMessage());		
	}
	@Test
	public void teste05() {
		Assertions.assertEquals(4, PrecoBase.values().length);
		Assertions.assertEquals(PrecoBase.MANUTENCAO_NORMAL.getCodigo(),1);
		Assertions.assertEquals(PrecoBase.MANUTENCAO_EMERGENCIAL.getCodigo(),2);
		Assertions.assertEquals(PrecoBase.REVISAO.getCodigo(),3);
		Assertions.assertEquals(PrecoBase.LIMPEZA.getCodigo(),4);
		Assertions.assertEquals(PrecoBase.MANUTENCAO_NORMAL.getDescricao(),"Manutenção normal");
		Assertions.assertEquals(PrecoBase.MANUTENCAO_EMERGENCIAL.getDescricao(),"Manutenção emergencial");
		Assertions.assertEquals(PrecoBase.REVISAO.getDescricao(),"Revisão");
		Assertions.assertEquals(PrecoBase.LIMPEZA.getDescricao(),"Limpeza");
		Assertions.assertTrue(PrecoBase.getPrecoBase(0) == null);
		Assertions.assertTrue(PrecoBase.getPrecoBase(1) == PrecoBase.MANUTENCAO_NORMAL);
		Assertions.assertTrue(PrecoBase.getPrecoBase(2) == PrecoBase.MANUTENCAO_EMERGENCIAL);
		Assertions.assertTrue(PrecoBase.getPrecoBase(3) == PrecoBase.REVISAO);
		Assertions.assertTrue(PrecoBase.getPrecoBase(4) == PrecoBase.LIMPEZA);
		Assertions.assertTrue(PrecoBase.getPrecoBase(5) == null);
	}
	
	@Test
	public void teste06() {
		Assertions.assertEquals(3, StatusOrdem.values().length);
		Assertions.assertEquals(StatusOrdem.ABERTA.getCodigo(),1);
		Assertions.assertEquals(StatusOrdem.CANCELADA.getCodigo(),2);
		Assertions.assertEquals(StatusOrdem.FECHADA.getCodigo(),3);
		Assertions.assertEquals(StatusOrdem.ABERTA.getDescricao(),"Aberta");
		Assertions.assertEquals(StatusOrdem.CANCELADA.getDescricao(),"Cancelada");
		Assertions.assertEquals(StatusOrdem.FECHADA.getDescricao(),"Fechada");
	}
}