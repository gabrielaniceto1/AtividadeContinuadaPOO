package br.edu.cs.poo.ac.ordem.testes;

import java.util.function.Function;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.ordem.entidades.Cliente;
import br.edu.cs.poo.ac.ordem.entidades.Contato;
import br.edu.cs.poo.ac.ordem.entidades.Desktop;
import br.edu.cs.poo.ac.ordem.entidades.Notebook;
import br.edu.cs.poo.ac.ordem.mediators.ClienteMediator;
import br.edu.cs.poo.ac.ordem.mediators.DadosEquipamento;
import br.edu.cs.poo.ac.ordem.mediators.EquipamentoMediator;
import br.edu.cs.poo.ac.ordem.mediators.ResultadoMediator;
import br.edu.cs.poo.ac.utils.ListaString;

public class TesteEquipamentoMediator extends TesteAbstrato {
	private static final String SERIAL_DO_NOTEBOOK_NAO_EXISTENTE = "Serial do notebook não existente";
	private static final String SERIAL_DO_DESKTOP_NAO_EXISTENTE = "Serial do desktop não existente";
	private static final String ID_DO_TIPO_SERIAL_DO_NOTEBOOK_NAO_INFORMADO = "Id do tipo + serial do notebook não informado";
	private static final String ID_DO_TIPO_SERIAL_DO_DESKTOP_NAO_INFORMADO = "Id do tipo + serial do desktop não informado";
	private static final String VALOR_ESTIMADO_MENOR_OU_IGUAL_A_ZERO = "Valor estimado menor ou igual a zero";
	private static final String SERIAL_NAO_INFORMADO = "Serial não informado";
	private static final String DESCRICAO_NAO_INFORMADA = "Descrição não informada";
	private static final String DESCRICAO_TEM_MENOS_DE_10_CARACTERES = "Descrição tem menos de 10 caracteres";
	private static final String DESCRICAO_TEM_MAIS_DE_150_CARACTERES = "Descrição tem mais de 150 caracteres";
	private CadastroObjetos cadastroDesktop = new CadastroObjetos(Desktop.class);
	private EquipamentoMediator mediator = EquipamentoMediator.getInstancia();
	public TesteEquipamentoMediator() {
		super(Notebook.class);
	}
	@BeforeEach
	public void limparRegistrosDesktopNotebook() {
		FileUtils.limparDiretorio("." + SEP + Desktop.class.getSimpleName());
		FileUtils.limparDiretorio("." + SEP + Notebook.class.getSimpleName());
	}
	@Test
	public void testeValidarEquipamento01() {
		wrapTesteValidarEquipamento01(mediator::validar);
	}
	@Test
	public void testeValidarEquipamento02() {
		wrapTesteValidarEquipamento02(mediator::validar);
	}
	@Test
	public void testeValidarEquipamento03() {
		wrapTesteValidarEquipamento03(mediator::validar);
	}
	@Test
	public void testeValidarDesktop01() {
		wrapTesteValidarDesktop01(mediator::validarDesktop);
	}
	@Test
	public void testeValidarDesktop02() {
		wrapTesteValidarDesktop02(mediator::validarDesktop);
	}
	@Test
	public void testeValidarDesktop03() {
		wrapTesteValidarDesktop03(mediator::validarDesktop);
	}
	@Test
	public void testeValidarNotebook01() {
		wrapTesteValidarNotebook01(mediator::validarNotebook);
	}
	@Test
	public void testeValidarNotebook02() {
		wrapTesteValidarNotebook02(mediator::validarNotebook);
	}
	@Test
	public void testeValidarNotebook03() {
		wrapTesteValidarNotebook03(mediator::validarNotebook);
	}
	@Test
	public void testeIncluirNotebookSucesso() {
		Notebook note = new Notebook("NO666", "THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG", false, 3.0, false);
		String id = note.getIdTipo() + note.getSerial();
		ResultadoMediator res = mediator.incluirNotebook(note);
		Assertions.assertNotNull(res);
		Assertions.assertTrue(res.isValidado());
		Assertions.assertTrue(res.isOperacaoRealizada());
		Assertions.assertNotNull(res.getMensagensErro());
		Assertions.assertEquals(0, res.getMensagensErro().tamanho());
		Assertions.assertEquals(1, obterQuantidadeRegistrosPorTipo(Notebook.class));
		Notebook noteBuscado = (Notebook)cadastro.buscar(id);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(note, noteBuscado));
	}
	@Test
	public void testeIncluirNotebookNaoSucesso() {
		wrapTesteValidarNotebook01(mediator::incluirNotebook);
		wrapTesteValidarNotebook02(mediator::incluirNotebook);
		wrapTesteValidarNotebook03(mediator::incluirNotebook);
		Notebook note = new Notebook("NO999", "NON OMINE QUOD LICET HONESTUM EST", false, 88.0, false);
		String id = note.getIdTipo() + note.getSerial();
		cadastro.incluir(note, id);
		ResultadoMediator res = mediator.incluirNotebook(note);
		Assertions.assertNotNull(res);
		Assertions.assertTrue(res.isValidado());
		Assertions.assertFalse(res.isOperacaoRealizada());
		Assertions.assertNotNull(res.getMensagensErro());
		Assertions.assertEquals(1, res.getMensagensErro().tamanho());
		Assertions.assertEquals("Serial do notebook já existente", res.getMensagensErro().buscar(0));
		Assertions.assertEquals(1, obterQuantidadeRegistrosPorTipo(Notebook.class));
	}
	@Test
	public void testeIncluirDesktopSucesso() {
		Desktop des = new Desktop("DE333", "I LIVE ON THE SECOND FLOOR", false, 3.0, false);
		String id = des.getIdTipo() + des.getSerial();
		ResultadoMediator res = mediator.incluirDesktop(des);
		Assertions.assertNotNull(res);
		Assertions.assertTrue(res.isValidado());
		Assertions.assertTrue(res.isOperacaoRealizada());
		Assertions.assertNotNull(res.getMensagensErro());
		Assertions.assertEquals(0, res.getMensagensErro().tamanho());
		Assertions.assertEquals(1, obterQuantidadeRegistrosPorTipo(Desktop.class));
		Desktop desBuscado = (Desktop)cadastroDesktop.buscar(id);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(des, desBuscado));
	}
	@Test
	public void testeIncluirDesktopNaoSucesso() {
		wrapTesteValidarDesktop01(mediator::incluirDesktop);
		wrapTesteValidarDesktop02(mediator::incluirDesktop);
		wrapTesteValidarDesktop03(mediator::incluirDesktop);
		Desktop des = new Desktop("DEXXXa1", "DON T LET THE SUN GO DOWN ON ME", false, 11.0, false);
		String id = des.getIdTipo() + des.getSerial();
		cadastroDesktop.incluir(des, id);
		ResultadoMediator res = mediator.incluirDesktop(des);
		Assertions.assertNotNull(res);
		Assertions.assertTrue(res.isValidado());
		Assertions.assertFalse(res.isOperacaoRealizada());
		Assertions.assertNotNull(res.getMensagensErro());
		Assertions.assertEquals(1, res.getMensagensErro().tamanho());
		Assertions.assertEquals("Serial do desktop já existente", res.getMensagensErro().buscar(0));
		Assertions.assertEquals(1, obterQuantidadeRegistrosPorTipo(Desktop.class));
	}


	@Test
	public void testeAlterarNotebookSucesso() {
		String serial = "NO666";
		Notebook note = new Notebook(serial, "THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG", false, 3.0, false);
		String id = note.getIdTipo() + serial;
		cadastro.incluir(note, id);
		Notebook noteAlt = new Notebook(serial, "THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG 111", true, 999.0, true);
		ResultadoMediator res = mediator.alterarNotebook(noteAlt);
		Assertions.assertNotNull(res);
		Assertions.assertTrue(res.isValidado());
		Assertions.assertTrue(res.isOperacaoRealizada());
		Assertions.assertNotNull(res.getMensagensErro());
		Assertions.assertEquals(0, res.getMensagensErro().tamanho());
		Assertions.assertEquals(1, obterQuantidadeRegistrosPorTipo(Notebook.class));
		Notebook noteBuscado = (Notebook)cadastro.buscar(id);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(noteAlt, noteBuscado));
	}
	@Test
	public void testeAlterarNotebookNaoSucesso() {
		wrapTesteValidarNotebook01(mediator::incluirNotebook);
		wrapTesteValidarNotebook02(mediator::incluirNotebook);
		wrapTesteValidarNotebook03(mediator::incluirNotebook);
		String serial = "NO666";
		Notebook note = new Notebook(serial, "THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG", false, 3.0, false);
		String id = note.getIdTipo() + serial;
		cadastro.incluir(note, id);
		Notebook noteAlt = new Notebook("N667", "THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG", false, 3.0, false);
		ResultadoMediator res = mediator.alterarNotebook(noteAlt);
		Assertions.assertNotNull(res);
		Assertions.assertTrue(res.isValidado());
		Assertions.assertFalse(res.isOperacaoRealizada());
		Assertions.assertNotNull(res.getMensagensErro());
		Assertions.assertEquals(1, res.getMensagensErro().tamanho());
		Assertions.assertEquals(SERIAL_DO_NOTEBOOK_NAO_EXISTENTE, res.getMensagensErro().buscar(0));
		Assertions.assertEquals(1, obterQuantidadeRegistrosPorTipo(Notebook.class));
	}
	@Test
	public void testeAlterarDesktopSucesso() {
		String serial = "DE111";
		Desktop des = new Desktop(serial, "I LIVE ON THE SECOND FLOOR", false, 3.0, false);
		String id = des.getIdTipo() + serial;
		cadastroDesktop.incluir(des, id);
		Desktop desAlt = new Desktop(serial, "THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG 66543", true, 98098.0, true);
		ResultadoMediator res = mediator.alterarDesktop(desAlt);
		Assertions.assertNotNull(res);
		Assertions.assertTrue(res.isValidado());
		Assertions.assertTrue(res.isOperacaoRealizada());
		Assertions.assertNotNull(res.getMensagensErro());
		Assertions.assertEquals(0, res.getMensagensErro().tamanho());
		Assertions.assertEquals(1, obterQuantidadeRegistrosPorTipo(Desktop.class));
		Desktop desBuscado = (Desktop)cadastroDesktop.buscar(id);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(desAlt, desBuscado));
	}
	@Test
	public void testeAlterarDesktopNaoSucesso() {
		wrapTesteValidarDesktop01(mediator::incluirDesktop);
		wrapTesteValidarDesktop02(mediator::incluirDesktop);
		wrapTesteValidarDesktop03(mediator::incluirDesktop);
		Desktop des = new Desktop("DEXXXa1", "DON T LET THE SUN GO DOWN ON ME", false, 11.0, false);
		String id = des.getIdTipo() + des.getSerial();
		cadastroDesktop.incluir(des, id);
		Desktop desAlt = new Desktop("DEXXX22", "DON T LET THE SUN GO DOWN ON ME", false, 11.0, false);
		ResultadoMediator res = mediator.alterarDesktop(desAlt);
		Assertions.assertNotNull(res);
		Assertions.assertTrue(res.isValidado());
		Assertions.assertFalse(res.isOperacaoRealizada());
		Assertions.assertNotNull(res.getMensagensErro());
		Assertions.assertEquals(1, res.getMensagensErro().tamanho());
		Assertions.assertEquals("Serial do desktop não existente", res.getMensagensErro().buscar(0));
		Assertions.assertEquals(1, obterQuantidadeRegistrosPorTipo(Desktop.class));
	}


	@Test
	public void testeExcluirNotebookNaoSucesso() {
		Notebook note = new Notebook("NXXX112", "THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG", false, 3.0, false);
		String id = note.getIdTipo() + note.getSerial();
		cadastro.incluir(note, id);
		ResultadoMediator res = mediator.excluirNotebook(null);
		assertionsExclusaoIdSerialVazio(res, Notebook.class);
		Assertions.assertEquals(ID_DO_TIPO_SERIAL_DO_NOTEBOOK_NAO_INFORMADO, res.getMensagensErro().buscar(0));
		res = mediator.excluirNotebook(STR_VAZIA);
		assertionsExclusaoIdSerialVazio(res, Notebook.class);
		Assertions.assertEquals(ID_DO_TIPO_SERIAL_DO_NOTEBOOK_NAO_INFORMADO, res.getMensagensErro().buscar(0));
		res = mediator.excluirNotebook("NO0000");
		Assertions.assertNotNull(res);
		Assertions.assertTrue(res.isValidado());
		Assertions.assertFalse(res.isOperacaoRealizada());
		Assertions.assertNotNull(res.getMensagensErro());
		Assertions.assertEquals(1, res.getMensagensErro().tamanho());
		Assertions.assertEquals(1, obterQuantidadeRegistros());
		Assertions.assertEquals(SERIAL_DO_NOTEBOOK_NAO_EXISTENTE, res.getMensagensErro().buscar(0));
	}

	@Test
	public void testeExcluirNotebookSucesso() {
		String serial = "NXXX112";
		Notebook note = new Notebook(serial, "THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG", false, 3.0, false);
		String id = note.getIdTipo() + note.getSerial();
		cadastro.incluir(note, id);
		ResultadoMediator res = mediator.excluirNotebook(id);
		Assertions.assertNotNull(res);
		Assertions.assertTrue(res.isValidado());
		Assertions.assertTrue(res.isOperacaoRealizada());
		Assertions.assertNotNull(res.getMensagensErro());
		Assertions.assertEquals(0, res.getMensagensErro().tamanho());
		Assertions.assertEquals(0, obterQuantidadeRegistros());
	}

	@Test
	public void testeExcluirDesktopNaoSucesso() {
		Desktop des = new Desktop("NXXX112", "THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG", false, 3.0, false);
		String id = des.getIdTipo() + des.getSerial();
		cadastroDesktop.incluir(des, id);
		ResultadoMediator res = mediator.excluirDesktop(null);
		assertionsExclusaoIdSerialVazio(res, Desktop.class);
		Assertions.assertEquals(ID_DO_TIPO_SERIAL_DO_DESKTOP_NAO_INFORMADO, res.getMensagensErro().buscar(0));
		res = mediator.excluirDesktop(STR_VAZIA);
		assertionsExclusaoIdSerialVazio(res, Desktop.class);
		Assertions.assertEquals(ID_DO_TIPO_SERIAL_DO_DESKTOP_NAO_INFORMADO, res.getMensagensErro().buscar(0));
		res = mediator.excluirDesktop("NO0000");
		Assertions.assertNotNull(res);
		Assertions.assertTrue(res.isValidado());
		Assertions.assertFalse(res.isOperacaoRealizada());
		Assertions.assertNotNull(res.getMensagensErro());
		Assertions.assertEquals(1, res.getMensagensErro().tamanho());
		Assertions.assertEquals(1, obterQuantidadeRegistrosPorTipo(Desktop.class));
		Assertions.assertEquals(SERIAL_DO_DESKTOP_NAO_EXISTENTE, res.getMensagensErro().buscar(0));
	}

	@Test
	public void testeExcluirDesktopSucesso() {
		String serial = "NXXX112";
		Desktop des = new Desktop(serial, "THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG", false, 3.0, false);
		String id = des.getIdTipo() + des.getSerial();
		cadastroDesktop.incluir(des, id);
		ResultadoMediator res = mediator.excluirDesktop(id);
		Assertions.assertNotNull(res);
		Assertions.assertTrue(res.isValidado());
		Assertions.assertTrue(res.isOperacaoRealizada());
		Assertions.assertNotNull(res.getMensagensErro());
		Assertions.assertEquals(0, res.getMensagensErro().tamanho());
		Assertions.assertEquals(0, obterQuantidadeRegistros());
	}

	@Test
	public void testeBuscarDesktopInexistente() {
		String serial = "NXXX112";

		Desktop note = new Desktop(serial, "THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG", false, 3.0, false);
		String id = note.getIdTipo() + note.getSerial();
		cadastroDesktop.incluir(note, id);
		Desktop desBus = mediator.buscarDesktop("NO000000");
		Assertions.assertNull(desBus);
		Assertions.assertEquals(1, obterQuantidadeRegistrosPorTipo(Desktop.class));
		desBus = mediator.buscarDesktop(null);
		Assertions.assertNull(desBus);
		Assertions.assertEquals(1, obterQuantidadeRegistrosPorTipo(Desktop.class));
		desBus = mediator.buscarDesktop(STR_VAZIA);
		Assertions.assertNull(desBus);
		Assertions.assertEquals(1, obterQuantidadeRegistrosPorTipo(Desktop.class));
	}

	@Test
	public void testeBuscarNotebookInexistente() {
		String serial = "NXXX112";

		Notebook note = new Notebook(serial, "THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG", false, 3.0, false);
		String id = note.getIdTipo() + note.getSerial();
		cadastro.incluir(note, id);
		Notebook noteBus = mediator.buscarNotebook("NO000000");
		Assertions.assertNull(noteBus);
		Assertions.assertEquals(1, obterQuantidadeRegistros());
		noteBus = mediator.buscarNotebook(null);
		Assertions.assertNull(noteBus);
		Assertions.assertEquals(1, obterQuantidadeRegistros());
		noteBus = mediator.buscarNotebook(STR_VAZIA);
		Assertions.assertNull(noteBus);
		Assertions.assertEquals(1, obterQuantidadeRegistros());
	}
	@Test
	public void testeBuscarDesktopSucesso() {
		String id = "NO11111";
		Desktop des = new Desktop(id, "THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG", false, 3.0, false);
		cadastroDesktop.incluir(des, id);
		Desktop desBuscado = mediator.buscarDesktop(id);
		Assertions.assertNotNull(desBuscado);
		Assertions.assertEquals(1, obterQuantidadeRegistrosPorTipo(Desktop.class));
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(desBuscado, des));
	}
	private void assertionsResultadoTudoNuloBranco(ResultadoMediator res) {
		ListaString mensagens = res.getMensagensErro();
		Assertions.assertEquals(2, mensagens.tamanho());
		Assertions.assertEquals(DESCRICAO_NAO_INFORMADA, mensagens.buscar(0));
		Assertions.assertEquals(SERIAL_NAO_INFORMADO, mensagens.buscar(1));
	}
	private void wrapTesteValidarEquipamento01(Function<DadosEquipamento, ResultadoMediator> conversor) {
		ResultadoMediator res = mediator.validar(null);
		assertionsResultadoMediatorNaoValidado(res);
		ListaString mensagens = res.getMensagensErro();
		Assertions.assertEquals(1, mensagens.tamanho());
		Assertions.assertEquals("Dados básicos do equipamento não informados", mensagens.buscar(0));
		DadosEquipamento dadosEquip = new DadosEquipamento(null, null, false, 1.0);
		res = conversor.apply(dadosEquip);
		assertionsResultadoMediatorNaoValidado(res);
		assertionsResultadoTudoNuloBranco(res);
		dadosEquip = new DadosEquipamento("                      ", " ", false, 2.0);
		res = conversor.apply(dadosEquip);
		assertionsResultadoMediatorNaoValidado(res);
		assertionsResultadoTudoNuloBranco(res);
	}
	private void wrapTesteValidarEquipamento02(Function<DadosEquipamento, ResultadoMediator> conversor) {
		DadosEquipamento dadosEquip = new DadosEquipamento("DE12345", "A".repeat(152), false, 3.0);
		ResultadoMediator res =  conversor.apply(dadosEquip);
		assertionsResultadoMediatorNaoValidado(res);
		ListaString mensagens = res.getMensagensErro();
		Assertions.assertEquals(1, mensagens.tamanho());
		Assertions.assertEquals(DESCRICAO_TEM_MAIS_DE_150_CARACTERES, mensagens.buscar(0));
		dadosEquip = new DadosEquipamento("NO12345", "ABCDEF", false, 4.0);
		res =  conversor.apply(dadosEquip);
		assertionsResultadoMediatorNaoValidado(res);
		mensagens = res.getMensagensErro();
		Assertions.assertEquals(1, mensagens.tamanho());
		Assertions.assertEquals(DESCRICAO_TEM_MENOS_DE_10_CARACTERES, mensagens.buscar(0));
	}
	private void wrapTesteValidarEquipamento03(Function<DadosEquipamento, ResultadoMediator> conversor) {
		DadosEquipamento dadosEquip = new DadosEquipamento(STR_VAZIA, null, true, 0.0);
		ResultadoMediator res =  conversor.apply(dadosEquip);
		assertionsResultadoMediatorNaoValidado(res);
		ListaString mensagens = res.getMensagensErro();
		Assertions.assertEquals(3, mensagens.tamanho());
		Assertions.assertEquals(DESCRICAO_NAO_INFORMADA, mensagens.buscar(0));
		Assertions.assertEquals(SERIAL_NAO_INFORMADO, mensagens.buscar(1));
		Assertions.assertEquals(VALOR_ESTIMADO_MENOR_OU_IGUAL_A_ZERO, mensagens.buscar(2));
		dadosEquip = new DadosEquipamento(STR_VAZIA, "ABCDEFGHIJKL", false, -2.0);
		res =  conversor.apply(dadosEquip);
		mensagens = res.getMensagensErro();
		Assertions.assertEquals(2, mensagens.tamanho());
		Assertions.assertEquals(SERIAL_NAO_INFORMADO, mensagens.buscar(0));
		Assertions.assertEquals(VALOR_ESTIMADO_MENOR_OU_IGUAL_A_ZERO, mensagens.buscar(1));
	}

	private void wrapTesteValidarDesktop01(Function<Desktop, ResultadoMediator> conversor) {
		ResultadoMediator res = mediator.validarDesktop(null);
		assertionsResultadoMediatorNaoValidado(res);
		ListaString mensagens = res.getMensagensErro();
		Assertions.assertEquals(1, mensagens.tamanho());
		Assertions.assertEquals("Desktop não informado", mensagens.buscar(0));
		Desktop des = new Desktop(null, null, false, 1.0, true);
		res = conversor.apply(des);
		assertionsResultadoMediatorNaoValidado(res);
		assertionsResultadoTudoNuloBranco(res);
		des = new Desktop("                        ", "  ", false, 2.0, false);
		res = conversor.apply(des);
		assertionsResultadoMediatorNaoValidado(res);
		assertionsResultadoTudoNuloBranco(res);
	}
	private void wrapTesteValidarDesktop02(Function<Desktop, ResultadoMediator> conversor) {
		Desktop des = new Desktop("DE12345", "A".repeat(152), false, 3.0, false);
		ResultadoMediator res =  conversor.apply(des);
		assertionsResultadoMediatorNaoValidado(res);
		ListaString mensagens = res.getMensagensErro();
		Assertions.assertEquals(1, mensagens.tamanho());
		Assertions.assertEquals(DESCRICAO_TEM_MAIS_DE_150_CARACTERES, mensagens.buscar(0));
		des = new Desktop("NO12345", "ABCDEF", false, 4.0, true);
		res =  conversor.apply(des);
		assertionsResultadoMediatorNaoValidado(res);
		mensagens = res.getMensagensErro();
		Assertions.assertEquals(1, mensagens.tamanho());
		Assertions.assertEquals(DESCRICAO_TEM_MENOS_DE_10_CARACTERES, mensagens.buscar(0));
	}
	private void wrapTesteValidarDesktop03(Function<Desktop, ResultadoMediator> conversor) {
		Desktop des = new Desktop(STR_VAZIA, null, true, 0.0, true);
		ResultadoMediator res =  conversor.apply(des);
		assertionsResultadoMediatorNaoValidado(res);
		ListaString mensagens = res.getMensagensErro();
		Assertions.assertEquals(3, mensagens.tamanho());
		Assertions.assertEquals(DESCRICAO_NAO_INFORMADA, mensagens.buscar(0));
		Assertions.assertEquals(SERIAL_NAO_INFORMADO, mensagens.buscar(1));
		Assertions.assertEquals(VALOR_ESTIMADO_MENOR_OU_IGUAL_A_ZERO, mensagens.buscar(2));
		des = new Desktop(STR_VAZIA, "ABCDEFGHIJKL", false, -2.0, true);
		res =  conversor.apply(des);
		mensagens = res.getMensagensErro();
		Assertions.assertEquals(2, mensagens.tamanho());
		Assertions.assertEquals(SERIAL_NAO_INFORMADO, mensagens.buscar(0));
		Assertions.assertEquals(VALOR_ESTIMADO_MENOR_OU_IGUAL_A_ZERO, mensagens.buscar(1));
	}

	private void wrapTesteValidarNotebook01(Function<Notebook, ResultadoMediator> conversor) {
		ResultadoMediator res = mediator.validarNotebook(null);
		assertionsResultadoMediatorNaoValidado(res);
		ListaString mensagens = res.getMensagensErro();
		Assertions.assertEquals(1, mensagens.tamanho());
		Assertions.assertEquals("Notebook não informado", mensagens.buscar(0));
		Notebook note = new Notebook(null, null, false, 1.0, true);
		res = conversor.apply(note);
		assertionsResultadoMediatorNaoValidado(res);
		assertionsResultadoTudoNuloBranco(res);
		note = new Notebook("                        ", "  ", false, 2.0, false);
		res = conversor.apply(note);
		assertionsResultadoMediatorNaoValidado(res);
		assertionsResultadoTudoNuloBranco(res);
	}
	private void wrapTesteValidarNotebook02(Function<Notebook, ResultadoMediator> conversor) {
		Notebook note = new Notebook("DE12345", "A".repeat(152), false, 3.0, false);
		ResultadoMediator res =  conversor.apply(note);
		assertionsResultadoMediatorNaoValidado(res);
		ListaString mensagens = res.getMensagensErro();
		Assertions.assertEquals(1, mensagens.tamanho());
		Assertions.assertEquals(DESCRICAO_TEM_MAIS_DE_150_CARACTERES, mensagens.buscar(0));
		note = new Notebook("NO12345", "ABCDEF", false, 4.0, true);
		res =  conversor.apply(note);
		assertionsResultadoMediatorNaoValidado(res);
		mensagens = res.getMensagensErro();
		Assertions.assertEquals(1, mensagens.tamanho());
		Assertions.assertEquals(DESCRICAO_TEM_MENOS_DE_10_CARACTERES, mensagens.buscar(0));
	}
	private void wrapTesteValidarNotebook03(Function<Notebook, ResultadoMediator> conversor) {
		Notebook note = new Notebook(STR_VAZIA, null, true, 0.0, true);
		ResultadoMediator res =  conversor.apply(note);
		assertionsResultadoMediatorNaoValidado(res);
		ListaString mensagens = res.getMensagensErro();
		Assertions.assertEquals(3, mensagens.tamanho());
		Assertions.assertEquals(DESCRICAO_NAO_INFORMADA, mensagens.buscar(0));
		Assertions.assertEquals(SERIAL_NAO_INFORMADO, mensagens.buscar(1));
		Assertions.assertEquals(VALOR_ESTIMADO_MENOR_OU_IGUAL_A_ZERO, mensagens.buscar(2));
		note = new Notebook(STR_VAZIA, "ABCDEFGHIJKL", false, -2.0, true);
		res =  conversor.apply(note);
		mensagens = res.getMensagensErro();
		Assertions.assertEquals(2, mensagens.tamanho());
		Assertions.assertEquals(SERIAL_NAO_INFORMADO, mensagens.buscar(0));
		Assertions.assertEquals(VALOR_ESTIMADO_MENOR_OU_IGUAL_A_ZERO, mensagens.buscar(1));
	}
	private void assertionsExclusaoIdSerialVazio(ResultadoMediator res, Class tipo) {
		Assertions.assertNotNull(res);
		Assertions.assertFalse(res.isValidado());
		Assertions.assertFalse(res.isOperacaoRealizada());
		Assertions.assertNotNull(res.getMensagensErro());
		Assertions.assertEquals(1, res.getMensagensErro().tamanho());
		Assertions.assertEquals(1, obterQuantidadeRegistrosPorTipo(tipo));
	}
	@Test
	public void testeSingleton() {
		Assertions.assertTrue(temUmSoConstrutorPrivado(EquipamentoMediator.class));
	}

}