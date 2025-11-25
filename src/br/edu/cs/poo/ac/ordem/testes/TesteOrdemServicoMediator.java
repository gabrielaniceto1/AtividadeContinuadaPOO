package br.edu.cs.poo.ac.ordem.testes;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.excecoes.ExcecaoNegocio;
import br.edu.cs.poo.ac.ordem.daos.DAORegistro;
import br.edu.cs.poo.ac.ordem.entidades.Cliente;
import br.edu.cs.poo.ac.ordem.entidades.Desktop;
import br.edu.cs.poo.ac.ordem.entidades.Equipamento;
import br.edu.cs.poo.ac.ordem.entidades.FechamentoOrdemServico;
import br.edu.cs.poo.ac.ordem.entidades.Notebook;
import br.edu.cs.poo.ac.ordem.entidades.OrdemServico;
import br.edu.cs.poo.ac.ordem.entidades.PrecoBase;
import br.edu.cs.poo.ac.ordem.entidades.StatusOrdem;
import br.edu.cs.poo.ac.ordem.mediators.DadosOrdemServico;
import br.edu.cs.poo.ac.ordem.mediators.OrdemServicoMediator;
import br.edu.cs.poo.ac.ordem.mediators.ResultadoMediator;
import br.edu.cs.poo.ac.ordem.testes.FileUtils;
import br.edu.cs.poo.ac.ordem.testes.TesteAbstrato;
import br.edu.cs.poo.ac.utils.ListaString;

public class TesteOrdemServicoMediator extends TesteAbstrato {

	private static final String ORDEM_JA_FOI_FECHADA = "Ordem já foi fechada";
	private static final String ORDEM_JA_FOI_CANCELADA = "Ordem já foi cancelada";
	private static final String NUMERO_DE_ORDEM_NAO_ENCONTRADO = "Número de ordem não encontrado";
	private static final String NUMERO_DE_ORDEM_NAO_INFORMADO = "Número de ordem não informado";
	private static final String RELATORIO_FINAL_NAO_INFORMADO = "Relatório final não informado";
	private static final String NUMERO_DE_ORDEM_DEVE_SER_INFORMADO = "Número de ordem deve ser informado";
	private static final String MOTIVO_DEVE_SER_INFORMADO = "Motivo deve ser informado";
	private static final String CPF01 = "23456789011";
	private static final String CNPJ01 = "23456789011234";
	private static final String PONTO = ".";
	private static final String PREF_DESKTOP = "DE";
	private static final String PREF_NOTE = "NO";
	private static final String SERIAL01 = "004";
	private static final Desktop DESK = new Desktop(SERIAL01, "HP 01", false, 3000, false);
	private static final Notebook NOTE = new Notebook(SERIAL01, "Apple", false, 10000, false); 
	private CadastroObjetos cadClie = new CadastroObjetos(Cliente.class);
	private CadastroObjetos cadNote = new CadastroObjetos(Notebook.class);
	private CadastroObjetos cadDesk = new CadastroObjetos(Desktop.class);
	private OrdemServicoMediator mediator = OrdemServicoMediator.getInstancia();
	public TesteOrdemServicoMediator() {
		super(OrdemServico.class);
	}
	private void wrapTesteValidarInclusao01() {
		ResultadoMediator res = null;
		try {
			mediator.incluir(null);
			Assertions.fail();
		} catch (ExcecaoNegocio e) {
			res = e.getRes();
		}
		assertionsResultadoMediatorNaoValidado(res);
		ListaString mensagens = res.getMensagensErro();
		Assertions.assertEquals(1, mensagens.tamanho());
		Assertions.assertEquals("Dados básicos da ordem de serviço não informados", mensagens.buscar(0));
		DadosOrdemServico dados = new DadosOrdemServico(null, 0, null, null);
		try {
			mediator.incluir(dados);
			Assertions.fail();
		} catch (ExcecaoNegocio e) {
			res = e.getRes();
		}
		assertionsResultadoMediatorNaoValidado(res);		
		assertionsResultadoTudoNuloBranco(res);
		dados = new DadosOrdemServico("       ", 10, "  ", "");
		try {
			mediator.incluir(dados);
			Assertions.fail();
		} catch (ExcecaoNegocio e) {
			res = e.getRes();
		}
		assertionsResultadoMediatorNaoValidado(res);		
		assertionsResultadoTudoNuloBranco(res);
	}
	private void wrapTesteValidarInclusao02() {
		ResultadoMediator res = null;
		String cpf = "10987654321";
		String serial = "002";
		Cliente cli = new Cliente(cpf, "CARLOS", null, LocalDate.now());
		Desktop de = new Desktop(serial, "DELL 01", false, 3000, false);
		cadClie.incluir(cli, cpf);
		cadDesk.incluir(de, PREF_DESKTOP + serial);
		DadosOrdemServico dados = new DadosOrdemServico("12345678901", 2, "DE001", "AYLA");
		try {
			mediator.incluir(dados);
			Assertions.fail();
		} catch (ExcecaoNegocio e) {
			res = e.getRes();
		}
		assertionsResultadoMediatorNaoValidado(res);
		ListaString mensagens = res.getMensagensErro();
		Assertions.assertEquals(2, mensagens.tamanho());
		Assertions.assertEquals("CPF/CNPJ do cliente não encontrado", mensagens.buscar(0));
		Assertions.assertEquals("Id do equipamento não encontrado", mensagens.buscar(1));		
	}
	private void wrapTesteValidarInclusao03() {
		ResultadoMediator res = null;
		String cpf = "10987654322";
		String serial = "003";
		Cliente cli = new Cliente(cpf, "CARLOS", null, LocalDate.now());
		Desktop de = new Desktop(serial, "DELL 01", false, 3000, false);
		cadClie.incluir(cli, cpf);
		cadDesk.incluir(de, PREF_DESKTOP + serial);
		DadosOrdemServico dados = new DadosOrdemServico(cpf, 3, PREF_DESKTOP + serial, "CARLOS");
		try {
			mediator.incluir(dados);
			Assertions.fail();
		} catch (ExcecaoNegocio e) {
			res = e.getRes();
		}
		assertionsResultadoMediatorNaoValidado(res);
		ListaString mensagens = res.getMensagensErro();
		Assertions.assertEquals(1, mensagens.tamanho());
		Assertions.assertEquals("Prazo e valor não podem ser avaliados pois o preço base é incompatível com cliente pessoa física", mensagens.buscar(0));
	}	
	private void wrapTesteValidarInclusaoValorPrazo(String cpfCnpj, PrecoBase pb, double valor, 
			int prazo, Equipamento equip, String pref) {
		String vendedor = "LAURO";		
		Cliente cli = new Cliente(cpfCnpj, "MARIA", null, LocalDate.now());		
		OrdemServico ordemRef = new OrdemServico(cli, pb, equip, LocalDateTime.now(), prazo, valor);
		ordemRef.setVendedor(vendedor);
		ordemRef.setStatus(StatusOrdem.ABERTA);
		String numero = gerarNumeroOrdem(pref, cpfCnpj, LocalDateTime.now());		
		cadClie.incluir(cli, cpfCnpj);		
		DadosOrdemServico dados = new DadosOrdemServico(cpfCnpj, pb.getCodigo(), pref + SERIAL01, vendedor);
		try {
			mediator.incluir(dados);
		} catch (ExcecaoNegocio e) {
			Assertions.fail();
		}
		OrdemServico ordem = (OrdemServico)cadastro.buscar(numero);
		Assertions.assertNotNull(ordem);
		Assertions.assertNotNull(ordem.getDataHoraAbertura());
		Assertions.assertEquals(1, obterQuantidadeRegistros());		
		// Passando a régua em um valor trivial
		// Não esqueçam de setar a data e hora atual na inclusão da ordem 
		ordemRef.setDataHoraAbertura(ordem.getDataHoraAbertura());
		boolean comp = ComparadoraObjetosSerial.compareObjectsSerial(ordemRef, ordem);
		Assertions.assertTrue(comp);		
	}	
	private void assertionsResultadoTudoNuloBranco(ResultadoMediator res) {
		ListaString mensagens = res.getMensagensErro();
		Assertions.assertEquals(4, mensagens.tamanho());
		Assertions.assertEquals("Código do preço base inválido", mensagens.buscar(0));
		Assertions.assertEquals("Vendedor não informado", mensagens.buscar(1));
		Assertions.assertEquals("CPF/CNPJ do cliente não informado", mensagens.buscar(2));		
		Assertions.assertEquals("Id do equipamento não informado", mensagens.buscar(3));
	}
	@Test
	public void teste00() {
		Class<?> classe = OrdemServicoMediator.class;
		boolean encontrado = false;
        for (Field campo : classe.getDeclaredFields()) {
            if (campo.getName().equals("daoOrdem") && campo.getType().equals(DAORegistro.class)) {
                encontrado = true;
                break;
            }
        }
        Assertions.assertTrue(encontrado);
	}
	@Test
	public void teste01() {		
		wrapTesteValidarInclusao01();
	}
	@Test
	public void teste02() {
		wrapTesteValidarInclusao02();
	}
	@Test
	public void teste03() {
		wrapTesteValidarInclusao03();
	}
	@Test
	public void teste04() {
		cadDesk.incluir(DESK, PREF_DESKTOP + SERIAL01);
		wrapTesteValidarInclusaoValorPrazo(CPF01, PrecoBase.MANUTENCAO_NORMAL, 
				180.00, 6, DESK, PREF_DESKTOP);
	}
	@Test
	public void teste05() {
		cadDesk.incluir(DESK, PREF_DESKTOP + SERIAL01);
		wrapTesteValidarInclusaoValorPrazo(CPF01, PrecoBase.MANUTENCAO_EMERGENCIAL, 
				280.00, 3, DESK, PREF_DESKTOP);
	}
	@Test
	public void teste07() {
		cadDesk.incluir(DESK, PREF_DESKTOP + SERIAL01);
		wrapTesteValidarInclusaoValorPrazo(CPF01, PrecoBase.LIMPEZA, 
				210.00, 6, DESK, PREF_DESKTOP);
	}
	@Test
	public void teste08() {
		cadDesk.incluir(DESK, PREF_DESKTOP + SERIAL01);
		wrapTesteValidarInclusaoValorPrazo(CNPJ01, PrecoBase.LIMPEZA, 
				250.00, 6, DESK, PREF_DESKTOP);
	}
	@Test
	public void teste09() {
		cadDesk.incluir(DESK, PREF_DESKTOP + SERIAL01);
		wrapTesteValidarInclusaoValorPrazo(CNPJ01, PrecoBase.REVISAO, 
				270.00, 6, DESK, PREF_DESKTOP);
	}
	@Test
	public void teste10() {
		cadDesk.incluir(DESK, PREF_DESKTOP + SERIAL01);
		wrapTesteValidarInclusaoValorPrazo(CNPJ01, PrecoBase.MANUTENCAO_NORMAL, 
				240.00, 6, DESK, PREF_DESKTOP);
	}
	@Test
	public void teste11() {
		cadDesk.incluir(DESK, PREF_DESKTOP + SERIAL01);
		wrapTesteValidarInclusaoValorPrazo(CNPJ01, PrecoBase.MANUTENCAO_EMERGENCIAL, 
				340.00, 3, DESK, PREF_DESKTOP);
	}
	@Test
	public void teste13() {
		cadNote.incluir(NOTE, PREF_NOTE + SERIAL01);
		wrapTesteValidarInclusaoValorPrazo(CNPJ01, PrecoBase.MANUTENCAO_EMERGENCIAL, 
				340.00, 4, NOTE, PREF_NOTE);
	}
	@Test
	public void teste14() {
		cadNote.incluir(NOTE, PREF_NOTE + SERIAL01);
		wrapTesteValidarInclusaoValorPrazo(CPF01, PrecoBase.MANUTENCAO_EMERGENCIAL, 
				280.00, 4, NOTE, PREF_NOTE);
	}
	@Test
	public void teste15() {
		cadDesk.incluir(DESK, PREF_DESKTOP + SERIAL01);
		wrapTesteValidarInclusaoValorPrazo(CPF01, PrecoBase.MANUTENCAO_EMERGENCIAL, 
				280.00, 3, DESK, PREF_DESKTOP);
	}
	@Test
	public void teste16() {
		ResultadoMediator res = null;
		try {
			mediator.cancelar(null, null, null);
			Assertions.fail();
		} catch (ExcecaoNegocio e) {
			res = e.getRes();
		}
		assertionsResultadoMediatorNaoValidado(res);
		ListaString mensagens = res.getMensagensErro();
		Assertions.assertEquals(3, mensagens.tamanho());
		Assertions.assertEquals(MOTIVO_DEVE_SER_INFORMADO, mensagens.buscar(0));
		Assertions.assertEquals("Data/hora cancelamento deve ser informada", mensagens.buscar(1));
		Assertions.assertEquals(NUMERO_DE_ORDEM_DEVE_SER_INFORMADO, mensagens.buscar(2));
		try {
			mediator.cancelar("                                ", " ", LocalDateTime.now().plusDays(1));
			Assertions.fail();
		} catch (ExcecaoNegocio e) {
			res = e.getRes();
		}
		assertionsResultadoMediatorNaoValidado(res);
		mensagens = res.getMensagensErro();
		Assertions.assertEquals(3, mensagens.tamanho());
		Assertions.assertEquals(MOTIVO_DEVE_SER_INFORMADO, mensagens.buscar(0));
		Assertions.assertEquals("Data/hora cancelamento deve ser menor do que a data hora atual", mensagens.buscar(1));		
		Assertions.assertEquals(NUMERO_DE_ORDEM_DEVE_SER_INFORMADO, mensagens.buscar(2));
	}
	@Test
	public void teste17() {
		ResultadoMediator res = null;
		try {
			mediator.cancelar("DE20251010112212345678901", "Erro de digitação", LocalDateTime.now());
			Assertions.fail();
		} catch (ExcecaoNegocio e) {
			res = e.getRes();
		}
		assertionsResultadoMediatorNaoValidado(res);
		Assertions.assertNotNull(res);
		ListaString mensagens = res.getMensagensErro();
		Assertions.assertEquals(1, mensagens.tamanho());
		Assertions.assertEquals(NUMERO_DE_ORDEM_NAO_ENCONTRADO, mensagens.buscar(0));
	}	
	@Test
	public void teste18() {
		ResultadoMediator res = null;
		LocalDateTime hj = LocalDateTime.now();
		Cliente cli = new Cliente(CPF01, "PAULO", null, hj.toLocalDate());	
		OrdemServico ordemRef = new OrdemServico(cli, PrecoBase.MANUTENCAO_NORMAL, 
				NOTE, hj, 6, 200.0); 
		String numero = gerarNumeroOrdem(PREF_NOTE, CPF01, hj);
		ordemRef.setVendedor("JOSELITO");
		ordemRef.setStatus(StatusOrdem.CANCELADA);
		cadastro.incluir(ordemRef, numero);
		try {
			mediator.cancelar(numero, "Já cancelada?", LocalDateTime.now());
			Assertions.fail();
		} catch (ExcecaoNegocio e) {
			res = e.getRes();
		}
		Assertions.assertNotNull(res);
		ListaString mensagens = res.getMensagensErro();
		Assertions.assertEquals(1, mensagens.tamanho());
		Assertions.assertEquals(ORDEM_JA_FOI_CANCELADA, mensagens.buscar(0));
		OrdemServico ordem = (OrdemServico)cadastro.buscar(numero);
		Assertions.assertNotNull(ordem);		
		Assertions.assertEquals(1, obterQuantidadeRegistros());		
		boolean comp = ComparadoraObjetosSerial.compareObjectsSerial(ordemRef, ordem);
		Assertions.assertTrue(comp);						
	}
	@Test
	public void teste19() {
		ResultadoMediator res = null;
		LocalDateTime hj = LocalDateTime.now();
		Cliente cli = new Cliente(CPF01, "KAMILA", null, hj.toLocalDate());	
		OrdemServico ordemRef = new OrdemServico(cli, PrecoBase.MANUTENCAO_NORMAL, 
				NOTE, hj, 6, 200.0); 
		String numero = gerarNumeroOrdem(PREF_NOTE, CPF01, hj);
		ordemRef.setVendedor("LEONA");
		ordemRef.setStatus(StatusOrdem.FECHADA);
		cadastro.incluir(ordemRef, numero);
		try {
			mediator.cancelar(numero, "Já fechada?", LocalDateTime.now());
			Assertions.fail();
		} catch (ExcecaoNegocio e) {
			res = e.getRes();
		}
		Assertions.assertNotNull(res);
		ListaString mensagens = res.getMensagensErro();
		Assertions.assertEquals(1, mensagens.tamanho());
		Assertions.assertEquals(ORDEM_JA_FOI_FECHADA, mensagens.buscar(0));
		OrdemServico ordem = (OrdemServico)cadastro.buscar(numero);
		Assertions.assertNotNull(ordem);		
		Assertions.assertEquals(1, obterQuantidadeRegistros());		
		boolean comp = ComparadoraObjetosSerial.compareObjectsSerial(ordemRef, ordem);
		Assertions.assertTrue(comp);						
	}	
	@Test
	public void teste20() {
		ResultadoMediator res = null;
		LocalDateTime hj = LocalDateTime.now().minusDays(3);
		Cliente cli = new Cliente(CPF01, "KAMALA", null, hj.toLocalDate());	
		OrdemServico ordemRef = new OrdemServico(cli, PrecoBase.MANUTENCAO_NORMAL, 
				NOTE, hj, 6, 200.0); 
		String numero = gerarNumeroOrdem(PREF_NOTE, CPF01, hj);
		ordemRef.setVendedor("BARBARA");
		ordemRef.setStatus(StatusOrdem.ABERTA);
		cadastro.incluir(ordemRef, numero);
		try {
			mediator.cancelar(numero, "Passou mais de dois dias?", LocalDateTime.now());
			Assertions.fail();
		} catch (ExcecaoNegocio e) {
			res = e.getRes();
		}
		Assertions.assertNotNull(res);
		ListaString mensagens = res.getMensagensErro();
		Assertions.assertEquals(1, mensagens.tamanho());
		Assertions.assertEquals("Ordem aberta há mais de dois dias não pode ser cancelada", mensagens.buscar(0));
		OrdemServico ordem = (OrdemServico)cadastro.buscar(numero);
		Assertions.assertNotNull(ordem);		
		Assertions.assertEquals(1, obterQuantidadeRegistros());		
		boolean comp = ComparadoraObjetosSerial.compareObjectsSerial(ordemRef, ordem);
		Assertions.assertTrue(comp);						
	}	
	@Test
	public void teste21() {
		LocalDateTime hj = LocalDateTime.now();
		Cliente cli = new Cliente(CPF01, "KAMILA", null, hj.toLocalDate());	
		OrdemServico ordemRef = new OrdemServico(cli, PrecoBase.MANUTENCAO_NORMAL, 
				NOTE, hj, 6, 200.0); 
		String numero = gerarNumeroOrdem(PREF_NOTE, CPF01, hj);
		String motivo = "Cancelada";
		ordemRef.setVendedor("LEONA");
		ordemRef.setStatus(StatusOrdem.ABERTA);
		cadastro.incluir(ordemRef, numero);
		try {
			mediator.cancelar(numero, motivo, hj);
		} catch (ExcecaoNegocio e) {
			Assertions.fail();
		}
		Assertions.assertEquals(1, obterQuantidadeRegistros());
		OrdemServico ordem = (OrdemServico)cadastro.buscar(numero);
		Assertions.assertNotNull(ordem);
		Assertions.assertNotNull(ordem.getDataHoraCancelamento());		
		Assertions.assertEquals(hj, ordem.getDataHoraCancelamento()); 
		Assertions.assertEquals(motivo, ordem.getMotivoCancelamento());
		Assertions.assertEquals(StatusOrdem.CANCELADA, ordem.getStatus());
		ordem.setStatus(StatusOrdem.ABERTA);
		ordem.setMotivoCancelamento(null);
		ordem.setDataHoraCancelamento(null);
		boolean comp = ComparadoraObjetosSerial.compareObjectsSerial(ordemRef, ordem);
		Assertions.assertTrue(comp);								
	}
	
	@Test
	public void teste22() {
		ResultadoMediator res = null;
		try {
			mediator.fechar(null);
			Assertions.fail();
		} catch (ExcecaoNegocio e) {
			res = e.getRes();
		}
		assertionsResultadoMediatorNaoValidado(res);
		ListaString mensagens = res.getMensagensErro();
		Assertions.assertEquals(1, mensagens.tamanho());
		Assertions.assertEquals("Dados do fechamento de ordem não informados", mensagens.buscar(0));
		FechamentoOrdemServico fecho = new FechamentoOrdemServico(null, null, false, null);
		try {
			mediator.fechar(fecho);
			Assertions.fail();
		} catch (ExcecaoNegocio e) {
			res = e.getRes();
		}			
		mensagens = res.getMensagensErro();
		Assertions.assertEquals(3, mensagens.tamanho());
		Assertions.assertEquals(RELATORIO_FINAL_NAO_INFORMADO, mensagens.buscar(0));
		Assertions.assertEquals("Data de fechamento não informada", mensagens.buscar(1));
		Assertions.assertEquals(NUMERO_DE_ORDEM_NAO_INFORMADO, mensagens.buscar(2));
		fecho = new FechamentoOrdemServico("   ", LocalDate.now().plusDays(1), false, "           ");
		try {
			mediator.fechar(fecho);
			Assertions.fail();
		} catch (ExcecaoNegocio e) {
			res = e.getRes();
		}	
		mensagens = res.getMensagensErro();
		Assertions.assertEquals(3, mensagens.tamanho());
		Assertions.assertEquals(RELATORIO_FINAL_NAO_INFORMADO, mensagens.buscar(0));
		Assertions.assertEquals("Data de fechamento maior que a data atual", mensagens.buscar(1));
		Assertions.assertEquals(NUMERO_DE_ORDEM_NAO_INFORMADO, mensagens.buscar(2));		
	}
	@Test
	public void teste23() {
		ResultadoMediator res = null;
		FechamentoOrdemServico fecho = new FechamentoOrdemServico("DE20251210112212345678901", LocalDate.now(), false, "Nada resolvido");
		try {
			mediator.fechar(fecho);
			Assertions.fail();
		} catch (ExcecaoNegocio e) {
			res = e.getRes();
		}			
		assertionsResultadoMediatorNaoValidado(res);
		Assertions.assertNotNull(res);
		ListaString mensagens = res.getMensagensErro();
		Assertions.assertEquals(1, mensagens.tamanho());
		Assertions.assertEquals(NUMERO_DE_ORDEM_NAO_ENCONTRADO, mensagens.buscar(0));
	}	
	@Test
	public void teste24() {
		ResultadoMediator res = null;
		LocalDateTime hj = LocalDateTime.now();
		Cliente cli = new Cliente(CPF01, "CALUNGA", null, hj.toLocalDate());	
		OrdemServico ordemRef = new OrdemServico(cli, PrecoBase.MANUTENCAO_NORMAL, 
				NOTE, hj, 6, 200.0); 
		String numero = gerarNumeroOrdem(PREF_NOTE, CPF01, hj);
		ordemRef.setVendedor("MARCO");
		ordemRef.setStatus(StatusOrdem.CANCELADA);		
		cadastro.incluir(ordemRef, numero);
		FechamentoOrdemServico fecho = new FechamentoOrdemServico(numero, LocalDate.now(), false, "Cancelada");
		try {
			mediator.fechar(fecho);
			Assertions.fail();
		} catch (ExcecaoNegocio e) {
			res = e.getRes();
		}			
		Assertions.assertNotNull(res);
		ListaString mensagens = res.getMensagensErro();
		Assertions.assertEquals(1, mensagens.tamanho());
		Assertions.assertEquals(ORDEM_JA_FOI_CANCELADA, mensagens.buscar(0));
		OrdemServico ordem = (OrdemServico)cadastro.buscar(numero);
		Assertions.assertNotNull(ordem);		
		Assertions.assertEquals(1, obterQuantidadeRegistros());		
		boolean comp = ComparadoraObjetosSerial.compareObjectsSerial(ordemRef, ordem);
		Assertions.assertTrue(comp);						
	}
	@Test
	public void teste25() {
		ResultadoMediator res = null;
		LocalDateTime hj = LocalDateTime.now();
		Cliente cli = new Cliente(CPF01, "LUNGA", null, hj.toLocalDate());	
		OrdemServico ordemRef = new OrdemServico(cli, PrecoBase.MANUTENCAO_NORMAL, 
				NOTE, hj, 6, 200.0); 
		String numero = gerarNumeroOrdem(PREF_NOTE, CPF01, hj);
		ordemRef.setVendedor("JOSA");
		ordemRef.setStatus(StatusOrdem.FECHADA);		
		cadastro.incluir(ordemRef, numero);
		FechamentoOrdemServico fecho = new FechamentoOrdemServico(numero, LocalDate.now(), true, "Fechada");
		try {
			mediator.fechar(fecho);
			Assertions.fail();
		} catch (ExcecaoNegocio e) {
			res = e.getRes();
		}
		Assertions.assertNotNull(res);
		ListaString mensagens = res.getMensagensErro();
		Assertions.assertEquals(1, mensagens.tamanho());
		Assertions.assertEquals(ORDEM_JA_FOI_FECHADA, mensagens.buscar(0));
		OrdemServico ordem = (OrdemServico)cadastro.buscar(numero);
		Assertions.assertNotNull(ordem);		
		Assertions.assertEquals(1, obterQuantidadeRegistros());		
		boolean comp = ComparadoraObjetosSerial.compareObjectsSerial(ordemRef, ordem);
		Assertions.assertTrue(comp);						
	}
	@Test
	public void teste26() { 
		LocalDateTime hj = LocalDateTime.now();
		Cliente cli = new Cliente(CPF01, "BABI", null, hj.toLocalDate());	
		OrdemServico ordemRef = new OrdemServico(cli, PrecoBase.MANUTENCAO_NORMAL, 
				NOTE, hj, 6, 200.0); 
		String numero = gerarNumeroOrdem(PREF_NOTE, CPF01, hj);
		ordemRef.setVendedor("CLAUDIO");
		ordemRef.setStatus(StatusOrdem.ABERTA);		
		cadastro.incluir(ordemRef, numero);
		FechamentoOrdemServico fecho = new FechamentoOrdemServico(numero, LocalDate.now(), false, "Fechando");
		try {
			mediator.fechar(fecho);			
		} catch (ExcecaoNegocio e) {
			Assertions.fail();
		}
		ordemRef.setStatus(StatusOrdem.FECHADA);
		ordemRef.setDadosFechamento(fecho);		
		OrdemServico ordem = (OrdemServico)cadastro.buscar(numero);
		Assertions.assertNotNull(ordem);		
		Assertions.assertEquals(1, obterQuantidadeRegistros());		
		boolean comp = ComparadoraObjetosSerial.compareObjectsSerial(ordemRef, ordem);
		Assertions.assertTrue(comp);						
	}
	@Test
	public void teste27() {
		OrdemServico ordem = mediator.buscar("1234455");
		Assertions.assertNull(ordem);
	}
	@Test
	public void teste28() {
		LocalDateTime hj = LocalDateTime.now();
		Cliente cli = new Cliente(CPF01, "DANZI", null, hj.toLocalDate());
		OrdemServico ordemRef = new OrdemServico(cli, PrecoBase.MANUTENCAO_NORMAL, 
				NOTE, hj, 6, 200.0); 
		String numero = gerarNumeroOrdem(PREF_NOTE, CPF01, hj);
		ordemRef.setVendedor("CLAUDIA");
		ordemRef.setStatus(StatusOrdem.ABERTA);		
		cadastro.incluir(ordemRef, numero);
		OrdemServico ordem = mediator.buscar(numero);
		Assertions.assertNotNull(ordem);
		boolean comp = ComparadoraObjetosSerial.compareObjectsSerial(ordemRef, ordem);
		Assertions.assertTrue(comp);								
	}
	@BeforeEach
	@Override
	public void limparRegistros() {
		super.limparRegistros();
		FileUtils.limparDiretorio(PONTO + SEP + Cliente.class.getSimpleName());
		FileUtils.limparDiretorio(PONTO + SEP + Desktop.class.getSimpleName());
		FileUtils.limparDiretorio(PONTO + SEP + Notebook.class.getSimpleName());
		
	}
	private String gerarNumeroOrdem(String tipoEq, String cpfCnpj, LocalDateTime dhAbertura) {
		String parteCpfCnpj = "";
		if (cpfCnpj.length() == 11) {
			parteCpfCnpj = "000";
		}
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String dhaString = dhAbertura.format(formatter);
		return tipoEq + dhaString + parteCpfCnpj + cpfCnpj;    				
	}
}