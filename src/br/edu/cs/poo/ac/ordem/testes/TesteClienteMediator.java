package br.edu.cs.poo.ac.ordem.testes;

import java.time.LocalDate;
import java.util.function.Function;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.edu.cs.poo.ac.ordem.entidades.Cliente;
import br.edu.cs.poo.ac.ordem.entidades.Contato;
import br.edu.cs.poo.ac.ordem.mediators.ClienteMediator;
import br.edu.cs.poo.ac.ordem.mediators.ResultadoMediator;
import br.edu.cs.poo.ac.utils.ListaString;

public class TesteClienteMediator extends TesteAbstrato {
	private static final String CPF_CNPJ_INEXISTENTE = "CPF/CNPJ inexistente";
	private static final String CNPJ_VALIDO = "26055259000123";
	private static final String OUTRO_CPF_VALIDO = "61232801046";
	private static final String STR_VAZIA = "";
	private static final String CPF_VALIDO = "07237135023";
	private static final String CELULAR_NAO_INFORMADO_E_INDICADOR_DE_ZAP_ATIVO = "Celular não informado e indicador de zap ativo";
	private static final String CELULAR_E_E_MAIL_NAO_FORAM_INFORMADOS = "Celular e e-mail não foram informados";
	private static final String CPF_CNPJ_NAO_INFORMADO = "CPF/CNPJ não informado";
	private static final String CPF_OU_CNPJ_COM_DIGITO_VERIFICADOR_INVALIDO = "CPF ou CNPJ com dígito verificador inválido";
	private static final String NAO_E_CPF_NEM_CNJP = "Não é CPF nem CNJP";
	private static final String DATA_DO_CADASTRO_NAO_INFORMADA = "Data do cadastro não informada";
	private static final String CONTATO_NAO_INFORMADO = "Contato não informado";
	private static final String NOME_NAO_INFORMADO = "Nome não informado";	
	private ClienteMediator mediator = ClienteMediator.getInstancia();
	public TesteClienteMediator() {
		super(Cliente.class);
	}
	
	private void assertionsResultadoMediatorNaoValidado(ResultadoMediator res) {
		Assertions.assertNotNull(res);
		Assertions.assertFalse(res.isValidado());
		Assertions.assertFalse(res.isOperacaoRealizada());
		Assertions.assertNotNull(res.getMensagensErro());
		Assertions.assertTrue(res.getMensagensErro().tamanho() > 0);
	}
	private void assertionsResultadoMediatorValidado(ResultadoMediator res) {
		Assertions.assertNotNull(res);
		Assertions.assertTrue(res.isValidado());
		Assertions.assertFalse(res.isOperacaoRealizada());
		Assertions.assertNotNull(res.getMensagensErro());
		Assertions.assertEquals(0, res.getMensagensErro().tamanho());	
	}
	private void assertionsResultadoTudoNuloBranco(ResultadoMediator res) {
		ListaString mensagens = res.getMensagensErro();
		Assertions.assertEquals(4, mensagens.tamanho());
		Assertions.assertEquals(CPF_CNPJ_NAO_INFORMADO, mensagens.buscar(0));
		Assertions.assertEquals(NOME_NAO_INFORMADO, mensagens.buscar(1));
		Assertions.assertEquals(CONTATO_NAO_INFORMADO, mensagens.buscar(2));
		Assertions.assertEquals(DATA_DO_CADASTRO_NAO_INFORMADA, mensagens.buscar(3));
	}
	private void assertionsNulosNomeContatoData(ResultadoMediator res) {
		ListaString mensagens = res.getMensagensErro();
		Assertions.assertEquals(NOME_NAO_INFORMADO, mensagens.buscar(1));		
		Assertions.assertEquals(CONTATO_NAO_INFORMADO, mensagens.buscar(2));
		Assertions.assertEquals(DATA_DO_CADASTRO_NAO_INFORMADA, mensagens.buscar(3));		
	}
	private void assertionsCPFCNPJ(Function<Cliente, ResultadoMediator> conversor, Cliente cliente, String mensagem) {
		ResultadoMediator res = conversor.apply(cliente);
		assertionsResultadoMediatorNaoValidado(res);
		ListaString mensagens = res.getMensagensErro();
		Assertions.assertEquals(4, mensagens.tamanho());
		Assertions.assertEquals(mensagem, mensagens.buscar(0));
		assertionsNulosNomeContatoData(res);
	}
	private void assertionsContato(Function<Cliente, ResultadoMediator> conversor, Cliente cliente, String mensagem) {
		ResultadoMediator res = conversor.apply(cliente);
		assertionsResultadoMediatorNaoValidado(res);
		ListaString mensagens = res.getMensagensErro();
		Assertions.assertEquals(4, mensagens.tamanho());
		Assertions.assertEquals(CPF_CNPJ_NAO_INFORMADO, mensagens.buscar(0));
		Assertions.assertEquals(NOME_NAO_INFORMADO, mensagens.buscar(1));		
		Assertions.assertEquals(DATA_DO_CADASTRO_NAO_INFORMADA, mensagens.buscar(2));
		Assertions.assertEquals(mensagem, mensagens.buscar(3));				
	}
	@Test
	public void testeValidarCliente01() {
		wrapTesteValidarCliente01(mediator::validar);
	}
	@Test
	public void testeValidarCliente02() {
		wrapTesteValidarCliente02(mediator::validar);
	}
	@Test
	public void testeValidarCliente03() {
		wrapTesteValidarCliente03(mediator::validar);
	}
	@Test
	public void testeValidarCliente04() {
		wrapTesteValidarCliente04(mediator::validar);
	}
	@Test
	public void testeValidarCliente05() {
		wrapTesteValidarCliente05(mediator::validar);
	}
	@Test
	public void testeValidarCliente06() {
		Cliente cliente = new Cliente(CPF_VALIDO, "CARLOS", new Contato("egca@ddd.com.br", "(87)999880099", false), 
				LocalDate.now().minusDays(2));
		ResultadoMediator res = mediator.validar(cliente);
		assertionsResultadoMediatorValidado(res);
		cliente = new Cliente(CNPJ_VALIDO, "ACME SA", new Contato(null, "(11)987880091", true), 
				LocalDate.now().minusDays(10));
		res = mediator.validar(cliente);
		assertionsResultadoMediatorValidado(res);
		cliente = new Cliente(CPF_VALIDO, "JOSILDO SA", new Contato("js@eee.com.au", STR_VAZIA, false), LocalDate.now().minusDays(10));
		res = mediator.validar(cliente);
		assertionsResultadoMediatorValidado(res);	 	
	}
	@Test
	public void testeIncluirClienteNaoSucesso() {
		wrapTesteValidarCliente01(mediator::incluir);
		wrapTesteValidarCliente02(mediator::incluir);
		wrapTesteValidarCliente03(mediator::incluir);
		wrapTesteValidarCliente04(mediator::incluir);
		wrapTesteValidarCliente05(mediator::incluir);
		Cliente cliente = new Cliente(CPF_VALIDO, "MARIA", new Contato("mama@eee.com.jp", "(71)989860311", false), 
				LocalDate.now().minusDays(1));
		cadastro.incluir(cliente, CPF_VALIDO);
		ResultadoMediator res = mediator.incluir(cliente);
		Assertions.assertNotNull(res);
		Assertions.assertTrue(res.isValidado());
		Assertions.assertFalse(res.isOperacaoRealizada());
		Assertions.assertNotNull(res.getMensagensErro());
		Assertions.assertEquals(1, res.getMensagensErro().tamanho());
		Assertions.assertEquals("CPF/CNPJ já existente", res.getMensagensErro().buscar(0));
		Assertions.assertEquals(1, obterQuantidadeRegistros());		
	}
	@Test
	public void testeIncluirClienteSucesso() {
		Cliente cliente = new Cliente(CPF_VALIDO, "JOSA", new Contato("josa@vvv.com.fr", "(21)959660341", true), 
				LocalDate.now().minusDays(30));
		ResultadoMediator res = mediator.incluir(cliente);
		Assertions.assertNotNull(res);
		Assertions.assertTrue(res.isValidado());
		Assertions.assertTrue(res.isOperacaoRealizada());
		Assertions.assertNotNull(res.getMensagensErro());
		Assertions.assertEquals(0, res.getMensagensErro().tamanho());		
		Assertions.assertEquals(1, obterQuantidadeRegistros());
		Cliente clienteBuscado = (Cliente)cadastro.buscar(CPF_VALIDO);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(cliente, clienteBuscado));
	}
	@Test
	public void testeAlterarClienteNaoSucesso() {
		wrapTesteValidarCliente01(mediator::alterar);
		wrapTesteValidarCliente02(mediator::alterar);
		wrapTesteValidarCliente03(mediator::alterar);
		wrapTesteValidarCliente04(mediator::alterar);
		wrapTesteValidarCliente05(mediator::alterar);		
		Cliente cliente = new Cliente(CNPJ_VALIDO, "BETAO", new Contato("bet@bee.com.cn", "(41)979894321", false), 
				LocalDate.now().minusDays(1));		
		ResultadoMediator res = mediator.alterar(cliente);
		Assertions.assertNotNull(res);
		Assertions.assertTrue(res.isValidado());
		Assertions.assertFalse(res.isOperacaoRealizada());
		Assertions.assertNotNull(res.getMensagensErro());
		Assertions.assertEquals(1, res.getMensagensErro().tamanho());
		Assertions.assertEquals(CPF_CNPJ_INEXISTENTE, res.getMensagensErro().buscar(0));
		Assertions.assertEquals(0, obterQuantidadeRegistros());		
	}
	@Test
	public void testeAlterarClienteSucesso() {
		Cliente cliente = new Cliente(CNPJ_VALIDO, "JOPA LTDA", new Contato("jopa@vvv.com.fr", "(21)959660341", true), 
				LocalDate.now().minusDays(30));
		cadastro.incluir(cliente, CNPJ_VALIDO);
		Cliente clienteAlt = new Cliente(CNPJ_VALIDO, "JOPA11 LTDA", new Contato("jopa11@vvv.com.fr", "(31)959660341", false), 
				LocalDate.now().minusDays(22));
		ResultadoMediator res = mediator.alterar(clienteAlt);
		Assertions.assertNotNull(res);
		Assertions.assertTrue(res.isValidado());
		Assertions.assertTrue(res.isOperacaoRealizada());
		Assertions.assertNotNull(res.getMensagensErro());
		Assertions.assertEquals(0, res.getMensagensErro().tamanho());		
		Assertions.assertEquals(1, obterQuantidadeRegistros());
		Cliente clienteBuscado = (Cliente)cadastro.buscar(CNPJ_VALIDO);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(clienteAlt, clienteBuscado));
	}
	@Test
	public void testeBuscarClienteInexistente() {
		Cliente cliente = new Cliente(CPF_VALIDO, "LEO", new Contato("leo@nnn.com.ar", "(11)877943212", true), 
				LocalDate.now().minusDays(23));
		cadastro.incluir(cliente, CPF_VALIDO);
		Cliente cliBuscado = mediator.buscar(OUTRO_CPF_VALIDO);
		Assertions.assertNull(cliBuscado);
		Assertions.assertEquals(1, obterQuantidadeRegistros());
		cliBuscado = mediator.buscar(null);
		Assertions.assertNull(cliBuscado);
		Assertions.assertEquals(1, obterQuantidadeRegistros());
		cliBuscado = mediator.buscar(STR_VAZIA);
		Assertions.assertNull(cliBuscado);
		Assertions.assertEquals(1, obterQuantidadeRegistros());
	}
	@Test
	public void testeBuscarClienteSucesso() {
		Cliente cliente = new Cliente(CNPJ_VALIDO, "BAO", new Contato("bao@aaa.com.ur", "(61)988224433", true), 
				LocalDate.now().minusDays(3));
		cadastro.incluir(cliente, CNPJ_VALIDO);
		Cliente cliBuscado = mediator.buscar(CNPJ_VALIDO);
		Assertions.assertNotNull(cliBuscado);
		Assertions.assertEquals(1, obterQuantidadeRegistros());
		Cliente cliCadastro = (Cliente)cadastro.buscar(CNPJ_VALIDO);
		Assertions.assertTrue(ComparadoraObjetosSerial.compareObjectsSerial(cliCadastro, cliBuscado));		
	}
	@Test
	public void testeExcluirClienteNaoSucesso() {
		Cliente cliente = new Cliente(CPF_VALIDO, "BOOM", new Contato("boom@yyyy.com.br", "(91)999999776", true), 
				LocalDate.now().minusDays(7));
		cadastro.incluir(cliente, CPF_VALIDO);
		ResultadoMediator res = mediator.excluir(null);
		assertionsExclusaoCpfCnpjVazio(res);
		Assertions.assertEquals(CPF_CNPJ_NAO_INFORMADO, res.getMensagensErro().buscar(0));
		res = mediator.excluir(STR_VAZIA);
		assertionsExclusaoCpfCnpjVazio(res);
		Assertions.assertEquals(CPF_CNPJ_NAO_INFORMADO, res.getMensagensErro().buscar(0));
		res = mediator.excluir(OUTRO_CPF_VALIDO);
		Assertions.assertNotNull(res);
		Assertions.assertTrue(res.isValidado());
		Assertions.assertFalse(res.isOperacaoRealizada());
		Assertions.assertNotNull(res.getMensagensErro());
		Assertions.assertEquals(1, res.getMensagensErro().tamanho());		
		Assertions.assertEquals(1, obterQuantidadeRegistros());
		Assertions.assertEquals(CPF_CNPJ_INEXISTENTE, res.getMensagensErro().buscar(0));
	}
	@Test
	public void testeExcluirClienteSucesso() {
		Cliente cliente = new Cliente(CPF_VALIDO, "ZOROBABEL", new Contato("zoro@zzz.com.br", "(61)999999666", false), 
				LocalDate.now().minusDays(13));
		cadastro.incluir(cliente, CPF_VALIDO);
		ResultadoMediator res = mediator.excluir(CPF_VALIDO);
		Assertions.assertNotNull(res);
		Assertions.assertTrue(res.isValidado());
		Assertions.assertTrue(res.isOperacaoRealizada());
		Assertions.assertNotNull(res.getMensagensErro());
		Assertions.assertEquals(0, res.getMensagensErro().tamanho());		
		Assertions.assertEquals(0, obterQuantidadeRegistros());		
	}

	private void assertionsExclusaoCpfCnpjVazio(ResultadoMediator res) {
		Assertions.assertNotNull(res);
		Assertions.assertFalse(res.isValidado());
		Assertions.assertFalse(res.isOperacaoRealizada());
		Assertions.assertNotNull(res.getMensagensErro());
		Assertions.assertEquals(1, res.getMensagensErro().tamanho());		
		Assertions.assertEquals(1, obterQuantidadeRegistros());
	}

	private void wrapTesteValidarCliente01(Function<Cliente, ResultadoMediator> conversor) {
		ResultadoMediator res = mediator.validar(null);
		assertionsResultadoMediatorNaoValidado(res);
		ListaString mensagens = res.getMensagensErro();
		Assertions.assertEquals(1, mensagens.tamanho());
		Assertions.assertEquals("Cliente não informado", mensagens.buscar(0));
		Cliente cliente = new Cliente(null, null, null, null);
		res = conversor.apply(cliente);
		assertionsResultadoMediatorNaoValidado(res);		
		assertionsResultadoTudoNuloBranco(res);
		cliente = new Cliente("                      ", " ", null, null);
		res = conversor.apply(cliente);
		assertionsResultadoMediatorNaoValidado(res);		
		assertionsResultadoTudoNuloBranco(res);
	}
	private void wrapTesteValidarCliente02(Function<Cliente, ResultadoMediator> conversor) {
		Cliente cliente = new Cliente("123456", null, null, null);
		assertionsCPFCNPJ(conversor, cliente, NAO_E_CPF_NEM_CNJP);
		cliente = new Cliente("123456789012345", null, null, null);
		assertionsCPFCNPJ(conversor, cliente, NAO_E_CPF_NEM_CNJP);
		cliente = new Cliente("123A56B8901", null, null, null);
		assertionsCPFCNPJ(conversor, cliente, NAO_E_CPF_NEM_CNJP);		
		cliente = new Cliente("123A56B8901234", null, null, null);
		assertionsCPFCNPJ(conversor, cliente, NAO_E_CPF_NEM_CNJP);
		cliente = new Cliente("12345678901", null, null, null);
		assertionsCPFCNPJ(conversor, cliente, CPF_OU_CNPJ_COM_DIGITO_VERIFICADOR_INVALIDO);		
		cliente = new Cliente("12345678901234", null, null, null);
		assertionsCPFCNPJ(conversor, cliente, CPF_OU_CNPJ_COM_DIGITO_VERIFICADOR_INVALIDO);		
	}
	private void wrapTesteValidarCliente03(Function<Cliente, ResultadoMediator> conversor) {
		Cliente cliente = new Cliente(null, "A".repeat(51), null, null);
		ResultadoMediator res = conversor.apply(cliente);
		assertionsResultadoMediatorNaoValidado(res);
		ListaString mensagens = res.getMensagensErro();
		Assertions.assertEquals(4, mensagens.tamanho());
		Assertions.assertEquals(CPF_CNPJ_NAO_INFORMADO, mensagens.buscar(0));
		Assertions.assertEquals("Nome tem mais de 50 caracteres", mensagens.buscar(1));		
		Assertions.assertEquals(CONTATO_NAO_INFORMADO, mensagens.buscar(2));
		Assertions.assertEquals(DATA_DO_CADASTRO_NAO_INFORMADA, mensagens.buscar(3));				
	}
	private void wrapTesteValidarCliente04(Function<Cliente, ResultadoMediator> conversor) {
		Cliente cliente = new Cliente(null, null, null, LocalDate.now().plusDays(2));
		ResultadoMediator res = conversor.apply(cliente);
		assertionsResultadoMediatorNaoValidado(res);
		ListaString mensagens = res.getMensagensErro();
		Assertions.assertEquals(4, mensagens.tamanho());
		Assertions.assertEquals(CPF_CNPJ_NAO_INFORMADO, mensagens.buscar(0));
		Assertions.assertEquals(NOME_NAO_INFORMADO, mensagens.buscar(1));		
		Assertions.assertEquals(CONTATO_NAO_INFORMADO, mensagens.buscar(2));
		Assertions.assertEquals("Data do cadastro não pode ser posterior à data atual", mensagens.buscar(3));				
	}
	private void wrapTesteValidarCliente05(Function<Cliente, ResultadoMediator> conversor) {
		Cliente cliente = new Cliente(null, null, new Contato(null, null, false), null);
		assertionsContato(conversor, cliente, CELULAR_E_E_MAIL_NAO_FORAM_INFORMADOS);
		cliente = new Cliente(null, null, new Contato("        ", STR_VAZIA, false), null);
		assertionsContato(conversor, cliente, CELULAR_E_E_MAIL_NAO_FORAM_INFORMADOS);
		cliente = new Cliente(null, null, new Contato("abc@eee.com.br", "81991888888", false), null);
		assertionsContato(conversor, cliente, "Celular está em um formato inválido");
		cliente = new Cliente(null, null, new Contato("abc eee.com.br", "(81)991888888", false), null);
		assertionsContato(conversor, cliente, "E-mail está em um formato inválido");
		cliente = new Cliente(null, null, new Contato("egg@gemi.gov", null, true), null);
		assertionsContato(conversor, cliente, CELULAR_NAO_INFORMADO_E_INDICADOR_DE_ZAP_ATIVO);
		cliente = new Cliente(null, null, new Contato("xxx@yyy.edu.us", "   ", true), null);
		assertionsContato(conversor, cliente, CELULAR_NAO_INFORMADO_E_INDICADOR_DE_ZAP_ATIVO);						
	}
}