package br.edu.cs.poo.ac.ordem.mediators;

import br.edu.cs.poo.ac.utils.ListaString;

public class ResultadoMediator {
	private boolean validado; 
	private boolean operacaoRealizada; 
	private ListaString mensagensErro;
	public ResultadoMediator(boolean validado, boolean operacaoRealizada, ListaString mensagensErro) {
		this.validado = validado;
		this.operacaoRealizada = operacaoRealizada;
		this.mensagensErro = mensagensErro;
	}
	public boolean isValidado() {
		return validado;
	}
	public boolean isOperacaoRealizada() {
		return operacaoRealizada;
	}
	public ListaString getMensagensErro() {
		return mensagensErro;
	}
}