package br.edu.cs.poo.ac.ordem.mediators;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.ordem.daos.DesktopDAO;
import br.edu.cs.poo.ac.ordem.daos.NotebookDAO;
import br.edu.cs.poo.ac.ordem.entidades.Desktop;
import br.edu.cs.poo.ac.ordem.entidades.Notebook;
import br.edu.cs.poo.ac.utils.ListaString;
import br.edu.cs.poo.ac.ordem.mediators.ResultadoMediator;
import br.edu.cs.poo.ac.utils.StringUtils;

public class EquipamentoMediator {
	private static final EquipamentoMediator INSTANCIA = new EquipamentoMediator();
	private final NotebookDAO notebookDAO = new NotebookDAO();
	private final DesktopDAO desktopDAO = new DesktopDAO();
	
	private EquipamentoMediator() {}
	
	public static EquipamentoMediator getInstancia() {
		return INSTANCIA;
	}
	
	public ResultadoMediator incluirDesktop(Desktop desk) {
		ResultadoMediator resultado = validarDesktop(desk);
		if(resultado.isValidado() == true) {
			if(buscarDesktop(desk.concat()) == null) {
				desktopDAO.incluir(desk);
				return new ResultadoMediator(true, true, resultado.getMensagensErro());
			}
			else {
				ListaString erro = new ListaString();
				erro.adicionar("Serial do desktop já existente");
				return new ResultadoMediator(true,false, erro);
			}
		}
		return new ResultadoMediator(false,false,resultado.getMensagensErro());
	}
	public ResultadoMediator alterarDesktop(Desktop desk) {
		ResultadoMediator resultado = validarDesktop(desk);
		if(resultado.isValidado() == true) {
			if(buscarDesktop(desk.concat()) != null) {
				desktopDAO.alterar(desk);
				return new ResultadoMediator(true, true, resultado.getMensagensErro());
			}
			else {
				ListaString erro = new ListaString();
				erro.adicionar("Serial do desktop não existente");
				return new ResultadoMediator(true,false, erro);
			}
		}
		return new ResultadoMediator(false,false,resultado.getMensagensErro());
	}
	public ResultadoMediator incluirNotebook(Notebook note) {
		ResultadoMediator resultado = validarNotebook(note);
		if(resultado.isValidado() == true) {
			if(buscarNotebook(note.concat()) == null) {
				notebookDAO.incluir(note);
				return new ResultadoMediator(true, true, resultado.getMensagensErro());
			}
			else {
			ListaString erro = new ListaString();
			erro.adicionar("Serial do notebook já existente");
			return new ResultadoMediator(true,false, erro);
			}
		}
		return new ResultadoMediator(false,false,resultado.getMensagensErro());
	}
	public ResultadoMediator alterarNotebook(Notebook note) {
		ResultadoMediator resultado = validarNotebook(note);
		if(resultado.isValidado() == true) {
			if(buscarNotebook(note.concat()) != null) {
				notebookDAO.alterar(note);
				return new ResultadoMediator(true, true, resultado.getMensagensErro());
			}
			else {
				ListaString erro = new ListaString();
				erro.adicionar("Serial do notebook não existente");
				return new ResultadoMediator(true,false, erro);
			}
		}
		return new ResultadoMediator(false,false,resultado.getMensagensErro());
	}
	public ResultadoMediator excluirNotebook(String idTipoSerial) {
		ListaString erro = new ListaString();
		if(StringUtils.estaVazia(idTipoSerial)) { 
			erro.adicionar("Id do tipo + serial do notebook não informado");
			return new ResultadoMediator(false,false,erro);
		}
		
		if(buscarNotebook(idTipoSerial) != null) {
			notebookDAO.excluir(idTipoSerial);
			return new ResultadoMediator(true,true,erro);
		}
		else {
			erro.adicionar("Serial do notebook não existente");
			return new ResultadoMediator(true,false,erro);
		}
	}
	public ResultadoMediator excluirDesktop(String idTipoSerial) {
		ListaString erro = new ListaString();
		if(StringUtils.estaVazia(idTipoSerial)) { 
			erro.adicionar("Id do tipo + serial do desktop não informado");
			return new ResultadoMediator(false,false,erro);
		}
		
		if(buscarDesktop(idTipoSerial) != null) {
			desktopDAO.excluir(idTipoSerial);
			return new ResultadoMediator(true,true,erro);
		}
		else {
			erro.adicionar("Serial do desktop não existente");
			return new ResultadoMediator(true,false,erro);
		}
	}
	public Notebook buscarNotebook(String idTipoSerial) {
		return notebookDAO.buscar(idTipoSerial);
	}
	public Desktop buscarDesktop(String idTipoSerial) {
		return desktopDAO.buscar(idTipoSerial);
	}
	public ResultadoMediator validarDesktop(Desktop desk) {
		ListaString erros = new ListaString();
		//ver se não é null
		if(desk == null) {
			erros.adicionar("Desktop não informado");
			return new ResultadoMediator(false,false,erros);
		}
		
		boolean validado = true;
		boolean operacao = false;
		
		String serial = desk.getSerial();
		String id = desk.getIdTipo();
		String idSerial = desk.concat();
		String descricao = desk.getDescricao();
		boolean ehNovo = desk.isEhNovo();
		double valorEstimado = desk.getValorEstimado();
		
		DadosEquipamento equip = new DadosEquipamento(serial, descricao, ehNovo, valorEstimado);
		ResultadoMediator resultadoEquip = validar(equip);
		//passando os dados de equipamento para desktop
		if(resultadoEquip.isValidado() == false) {
			
			validado = false;
			
			ListaString listaEncadeadaErros = resultadoEquip.getMensagensErro();
			String[] errosEquip = listaEncadeadaErros.listar();
			for(int i = 0; i < errosEquip.length;i++) {
				erros.adicionar(errosEquip[i]);
			}
		}
//		if(StringUtils.estaVazia(serial)) {
//			erros.adicionar("Serial do desktop não existente");
//			validado = false;
//		}
//		if(StringUtils.estaVazia(idSerial)) {
//			erros.adicionar("Id do tipo + serial do desktop não informado");
//			validado = false;
//		}
		return new ResultadoMediator(validado, operacao, erros);
	}
	public ResultadoMediator validarNotebook(Notebook note) {
		
		ListaString erros = new ListaString();
		//ver se nn é null
		if(note == null) {
			erros.adicionar("Notebook não informado");
			return new ResultadoMediator(false,false,erros);
		}
		
		boolean validado = true;
		boolean operacao = false;
		
		String serial = note.getSerial();
		String id = note.getIdTipo();
		String idSerial = note.concat();
		String descricao = note.getDescricao();
		boolean ehNovo = note.isEhNovo();
		double valorEstimado = note.getValorEstimado();
		
		DadosEquipamento equip = new DadosEquipamento(serial, descricao, ehNovo, valorEstimado);
		ResultadoMediator resultadoEquip = validar(equip);
		//passando os dados de equipamento para notebook
		if(resultadoEquip.isValidado() == false) {
			
			validado = false;
			
			ListaString listaEncadeadaErros = resultadoEquip.getMensagensErro();
			String[] errosEquip = listaEncadeadaErros.listar();
			for(int i = 0; i < errosEquip.length;i++) {
				erros.adicionar(errosEquip[i]);
			}
		}
//		if(StringUtils.estaVazia(serial)) {
//			erros.adicionar("Serial do notebook não existente");
//			validado = false;
//		}
//		if(StringUtils.estaVazia(idSerial)) {
//			erros.adicionar("Id do tipo + serial do notebook não informado");
//			validado = false;
//		}
		return new ResultadoMediator(validado, operacao, erros);
	}
	public ResultadoMediator validar(DadosEquipamento equip) {
		
		ListaString erros = new ListaString();
		//ver se nn é null
		if(equip == null) {
			erros.adicionar("Dados básicos do equipamento não informados");
			return new ResultadoMediator(false, false, erros);
		}
		
		boolean validado = true;
		boolean operacao = false;
		
		String serial = equip.getSerial();
		String descricao = equip.getDescricao();
		boolean ehNovo = equip.getEhNovo();
		double valorEstimado = equip.getValorEstimado();

		//descricao
		if(StringUtils.estaVazia(descricao)) {
			erros.adicionar("Descrição não informada");
			validado = false;
		}
		else {
			if(StringUtils.tamanhoExcedido(descricao, 150)) {
			erros.adicionar("Descrição tem mais de 150 caracteres");
			validado = false;
			}
			else if(StringUtils.tamanhoMenor(descricao, 10)) {
			erros.adicionar("Descrição tem menos de 10 caracteres");
			validado = false;
			}
		}	
		///serial
		if(StringUtils.estaVazia(serial)) {
			erros.adicionar("Serial não informado");
			validado = false;
		}
		//valor estimado
		if(valorEstimado <= 0) {
			erros.adicionar("Valor estimado menor ou igual a zero");
			validado = false;
		}
		return new ResultadoMediator(validado, operacao, erros);
	}
}