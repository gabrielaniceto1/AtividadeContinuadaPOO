package br.edu.cs.poo.ac.excecoes;

import br.edu.cs.poo.ac.ordem.mediators.ResultadoMediator;
import br.edu.cs.poo.ac.utils.ListaString;

public class ExcecaoNegocio extends Exception{
	private static final long serialVersionUID = 1L;
	
	private ResultadoMediator resultadoMediator;
	
	public ExcecaoNegocio(ResultadoMediator resultadoMediator) {
		super();
		this.resultadoMediator = resultadoMediator;
	}
	
	public ResultadoMediator getRes() {
		return this.resultadoMediator;
	}
	
	public ListaString getMensagensErro() {
		return this.resultadoMediator.getMensagensErro();
	}

}
