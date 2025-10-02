package br.edu.cs.poo.ac.ordem.mediators;

public class DadosEquipamento {
	private String serial;
	private String descricao;
	private boolean ehNovo;
	private double valorEstimado;
	public DadosEquipamento(String serial, String descricao, 
		boolean ehNovo, double valorEstimado) {
		this.serial = serial;
		this.descricao = descricao;
		this.ehNovo = ehNovo;
		this.valorEstimado = valorEstimado;
	}
	String getSerial() {
		return serial;
	}
	String getDescricao() {
		return descricao;
	}
	boolean getEhNovo() {
		return ehNovo;
	}
	double getValorEstimado() {
		return valorEstimado;
	}
}