package br.edu.cs.poo.ac.ordem.mediators;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.edu.cs.poo.ac.excecoes.ExcecaoNegocio;
import br.edu.cs.poo.ac.ordem.daos.DAORegistro;
import br.edu.cs.poo.ac.ordem.daos.OrdemServicoDAO;
import br.edu.cs.poo.ac.ordem.entidades.Desktop;
import br.edu.cs.poo.ac.ordem.entidades.Equipamento;
import br.edu.cs.poo.ac.ordem.entidades.FechamentoOrdemServico;
import br.edu.cs.poo.ac.ordem.entidades.Notebook;
import br.edu.cs.poo.ac.ordem.entidades.OrdemServico;
import br.edu.cs.poo.ac.ordem.entidades.PrecoBase;
import br.edu.cs.poo.ac.ordem.entidades.StatusOrdem;
import br.edu.cs.poo.ac.utils.ListaString;
import br.edu.cs.poo.ac.utils.StringUtils;

public class OrdemServicoMediator {
	//declara as constantes
	private static final OrdemServicoMediator INSTANCIA = new OrdemServicoMediator();
	private final OrdemServicoDAO ordemServicoDAO = new OrdemServicoDAO();
	private final DAORegistro<OrdemServico> daoOrdem = new DAORegistro<OrdemServico>(OrdemServico.class);
	private final ClienteMediator clienteMediator = ClienteMediator.getInstancia();
	private final EquipamentoMediator equipamentoMediator = EquipamentoMediator.getInstancia();
	private OrdemServicoMediator() {}
	
	public static OrdemServicoMediator getInstancia() {
		return INSTANCIA;
	}
	
	public ResultadoMediator incluir(DadosOrdemServico dados) throws ExcecaoNegocio{
		//seta os parametros do RM
		ListaString erros = new ListaString();
		boolean valido = true;
		boolean realizado = false;
		//verifica se passou alguma coisa, se não ja da logo o erro
		if(dados == null) {
			valido = false;
			erros.adicionar("Dados básicos da ordem de serviço não informados");
			ResultadoMediator result = new ResultadoMediator(valido, realizado, erros);
			throw new ExcecaoNegocio(result);
		}
		
		//faz a primeira bateria de testes para validar os parametros dos dados
		if(dados.getCodigoPrecoBase() < 1 || dados.getCodigoPrecoBase() > 4) {
			valido = false;
			erros.adicionar("Código do preço base inválido");
		}
		if(StringUtils.estaVazia(dados.getVendedor())) {
			valido = false;
			erros.adicionar("Vendedor não informado");
		}
		if(StringUtils.estaVazia(dados.getCpfCnpjCliente())) {
			valido = false;
			erros.adicionar("CPF/CNPJ do cliente não informado");
		}
		if(StringUtils.estaVazia(dados.getIdEquipamento())) {
			valido = false;
			erros.adicionar("Id do equipamento não informado");
		}
		
		//aqui se não ficou valido, retorna a exceçao, se não, vai para a proxima bateria de testes
		if(valido == false) throw new ExcecaoNegocio(new ResultadoMediator(valido, realizado, erros));
		
		//testes que verificam o cliente e equipamento passado
		if(clienteMediator.buscar(dados.getCpfCnpjCliente()) == null) {
			valido = false;
			erros.adicionar("CPF/CNPJ do cliente não encontrado");
		}
		if(equipamentoMediator.buscarDesktop(dados.getIdEquipamento()) == null && equipamentoMediator.buscarNotebook(dados.getIdEquipamento()) == null) {
			valido = false;
			erros.adicionar("Id do equipamento não encontrado");
		}
		
		//verifica um caso especifico
		if(dados.getCpfCnpjCliente().replaceAll("\\D", "").length() == 11 && dados.getCodigoPrecoBase() == 3) {
			valido = false;
			erros.adicionar("Prazo e valor não podem ser avaliados pois o preço base é incompatível com cliente pessoa física");
		}
		//se não passou na segunda bateria, lança a exceção
		if(valido == false) throw new ExcecaoNegocio(new ResultadoMediator(valido, realizado, erros));
		
		//nas proximas funções seto e calculo o valor e o prazo
		int prazo;
		double valor;
		
		Notebook note = null;
		Desktop desk = null;
		if(dados.getIdEquipamento().startsWith("NO")) {
			note = equipamentoMediator.buscarNotebook(dados.getIdEquipamento());
		}
		else {
			desk = equipamentoMediator.buscarDesktop(dados.getIdEquipamento());
		}
		
		if(dados.getIdEquipamento().startsWith("NO") && dados.getCpfCnpjCliente().replaceAll("\\D", "").length() > 11 && PrecoBase.getPrecoBase(dados.getCodigoPrecoBase()) == PrecoBase.MANUTENCAO_EMERGENCIAL) {
			valor = 340.00;
			prazo = 4;
		}
		else if(dados.getIdEquipamento().startsWith("NO") && dados.getCpfCnpjCliente().replaceAll("\\D", "").length() == 11 && PrecoBase.getPrecoBase(dados.getCodigoPrecoBase()) == PrecoBase.MANUTENCAO_EMERGENCIAL) {
			valor = 280.00;
			prazo = 4;
		}
		else if(dados.getCpfCnpjCliente().replaceAll("\\D", "").length() > 11) {
			if(PrecoBase.getPrecoBase(dados.getCodigoPrecoBase()) == PrecoBase.LIMPEZA) {
				valor = 250.00;
				prazo = 6;
			}
			else if(PrecoBase.getPrecoBase(dados.getCodigoPrecoBase()) == PrecoBase.REVISAO) {
				valor = 270.00;
				prazo = 6;
			}
			else if(PrecoBase.getPrecoBase(dados.getCodigoPrecoBase()) == PrecoBase.MANUTENCAO_NORMAL) {
				valor = 240.00; 
				prazo = 6;
			}
			else {
				valor = 340.00;
				prazo = 3;
			}
		}
		else{
			if(PrecoBase.getPrecoBase(dados.getCodigoPrecoBase()) == PrecoBase.MANUTENCAO_NORMAL) {
				valor = 180.00;
				prazo = 6;
			}
			else if(PrecoBase.getPrecoBase(dados.getCodigoPrecoBase()) == PrecoBase.MANUTENCAO_EMERGENCIAL) {
				valor = 280.00;
				prazo = 3;
			}
			else {
				valor = 210.00;
				prazo = 6;
			}
		}
		
		//aqui preencho o ordemServiço que vai ser incluido
		OrdemServico ordemServico;
		if(note != null) {
			ordemServico = new OrdemServico(clienteMediator.buscar(dados.getCpfCnpjCliente()),PrecoBase.getPrecoBase(dados.getCodigoPrecoBase()), note ,LocalDateTime.now(),prazo,valor);
		}
		else {
			ordemServico = new OrdemServico(clienteMediator.buscar(dados.getCpfCnpjCliente()),PrecoBase.getPrecoBase(dados.getCodigoPrecoBase()), desk ,LocalDateTime.now(),prazo,valor);
		}
		
		ordemServico.setStatus(StatusOrdem.ABERTA);
		ordemServico.setVendedor(dados.getVendedor());
		
		//retorno
		ordemServicoDAO.incluir(ordemServico);
		realizado = true;
		return new ResultadoMediator(valido, realizado, erros);
	}
	public ResultadoMediator fechar(FechamentoOrdemServico fecho) throws ExcecaoNegocio{
		boolean valido = true;
		boolean realizado = false;
		ListaString erros = new ListaString();
		
		if(fecho == null) {
			valido = false;
			erros.adicionar("Dados do fechamento de ordem não informados");
			throw new ExcecaoNegocio(new ResultadoMediator(valido, realizado, erros));
		}
		if(fecho.getRelatorioFinal() == null) {
			valido = false;
			erros.adicionar("Relatório final não informado");
		}
		if(fecho.getDataFechamento() == null) {
			valido = false;
			erros.adicionar("Data de fechamento não informada");
		}
		
		if(fecho.getNumeroOrdemServico() == null) {
			valido = false;
			erros.adicionar("Número de ordem não informado");
		}
		if(valido == false) throw new ExcecaoNegocio(new ResultadoMediator(valido, realizado, erros));
		
		if(StringUtils.estaVazia(fecho.getRelatorioFinal())) {
			valido = false;
			erros.adicionar("Relatório final não informado");
		}
		
		if(fecho.getDataFechamento().isAfter(LocalDate.now())) {
			valido = false;
			erros.adicionar("Data de fechamento maior que a data atual");
			}
		if(StringUtils.estaVazia(fecho.getNumeroOrdemServico())) {
			valido = false;
			erros.adicionar("Número de ordem não informado");
		}
		
		if(valido == false) throw new ExcecaoNegocio(new ResultadoMediator(valido, realizado, erros));
		
		if(buscar(fecho.getNumeroOrdemServico()) == null) {
			valido = false;
			erros.adicionar("Número de ordem não encontrado");
			throw new ExcecaoNegocio(new ResultadoMediator(valido, realizado, erros));
		}
		else if(buscar(fecho.getNumeroOrdemServico()).getStatus() == StatusOrdem.CANCELADA) {
			valido = false;
			erros.adicionar("Ordem já foi cancelada");
			throw new ExcecaoNegocio(new ResultadoMediator(valido, realizado, erros));
		}
		else if(buscar(fecho.getNumeroOrdemServico()).getStatus() == StatusOrdem.FECHADA) {
			valido = false;
			erros.adicionar("Ordem já foi fechada");
			throw new ExcecaoNegocio(new ResultadoMediator(valido, realizado, erros));
		}
		
		OrdemServico ordem = buscar(fecho.getNumeroOrdemServico());
		ordem.setStatus(StatusOrdem.FECHADA);
		ordem.setDadosFechamento(fecho);
		ordemServicoDAO.alterar(ordem);
		realizado = true;
		return new ResultadoMediator(valido, realizado, erros);
	}
	public ResultadoMediator cancelar(String numeroOrdem, String motivo , LocalDateTime dataHoraCancelamento) throws ExcecaoNegocio{
		ListaString erros = new ListaString();
		boolean valido = true;
		boolean realizado = false;
		if(motivo == null) {
			valido = false;
			erros.adicionar("Motivo deve ser informado");
		}
		if(dataHoraCancelamento == null) {
			valido = false;
			erros.adicionar("Data/hora cancelamento deve ser informada");
		}
		if(numeroOrdem == null) {
			valido = false;
			erros.adicionar("Número de ordem deve ser informado");
		}
		if(valido == false) throw new ExcecaoNegocio(new ResultadoMediator(valido, realizado, erros));
		
		if(StringUtils.estaVazia(motivo)) {
			valido = false;
			erros.adicionar("Motivo deve ser informado");
		}
		if(dataHoraCancelamento.isAfter(LocalDateTime.now())) {
			valido = false;
			erros.adicionar("Data/hora cancelamento deve ser menor do que a data hora atual");
		}
		if(StringUtils.estaVazia(numeroOrdem)) {
			valido = false;
			erros.adicionar("Número de ordem deve ser informado");
		}
		if(valido == false) throw new ExcecaoNegocio(new ResultadoMediator(valido, realizado, erros));
		
		OrdemServico ordem = ordemServicoDAO.buscar(numeroOrdem);
		if(ordem == null) {
			valido = false;
			erros.adicionar("Número de ordem não encontrado");
			throw new ExcecaoNegocio(new ResultadoMediator(valido, realizado, erros));
		}
		else if(ordem.getStatus() == StatusOrdem.CANCELADA) {
			valido = false;
			erros.adicionar("Ordem já foi cancelada");
			throw new ExcecaoNegocio(new ResultadoMediator(valido, realizado, erros));
		}
		else if(ordem.getStatus() == StatusOrdem.FECHADA) {
			valido = false;
			erros.adicionar("Ordem já foi fechada");
			throw new ExcecaoNegocio(new ResultadoMediator(valido, realizado, erros));
		}
		else if(LocalDateTime.now().minusDays(2).isAfter(ordem.getDataHoraAbertura())) {
			valido = false;
			erros.adicionar("Ordem aberta há mais de dois dias não pode ser cancelada");
			throw new ExcecaoNegocio(new ResultadoMediator(valido, realizado, erros));
		}

	    ordem.setDataHoraCancelamento(dataHoraCancelamento);
	    ordem.setMotivoCancelamento(motivo);
	    ordem.setStatus(StatusOrdem.CANCELADA);

	    ordemServicoDAO.alterar(ordem);

	    realizado = true;
		
		return new ResultadoMediator(valido, realizado, erros);
	}
	public OrdemServico buscar(String numero) {
		return ordemServicoDAO.buscar(numero);
	}
}
