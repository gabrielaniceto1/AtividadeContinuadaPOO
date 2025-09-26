package br.edu.cs.poo.ac.utils;

public class ResultadoValidacaoCPFCNPJ {
	private boolean isCPF;
	private boolean isCNPJ;
	private ErroValidacaoCPFCNPJ erroValidacao;
	public ResultadoValidacaoCPFCNPJ(boolean isCPF, boolean isCNPJ, ErroValidacaoCPFCNPJ erroValidacao) {
		this.isCPF = isCPF;
		this.isCNPJ = isCNPJ;
		this.erroValidacao = erroValidacao;
	}
	public boolean isCPF() {
		return isCPF;
	}
	public boolean isCNPJ() {
		return isCNPJ;
	}
	public ErroValidacaoCPFCNPJ getErroValidacao() {
		return erroValidacao;
	}
}