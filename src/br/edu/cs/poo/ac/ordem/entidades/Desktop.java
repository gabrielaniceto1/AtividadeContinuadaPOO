package br.edu.cs.poo.ac.ordem.entidades;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter

public class Desktop extends Equipamento{
	private boolean ehServidor;
	
	public Desktop(String serial, String descricao, boolean ehNovo, double valorEstimado, boolean ehServidor) {
		super(serial, descricao, ehNovo, valorEstimado);
		this.ehServidor = ehServidor;
	}
	public String getIdTipo() {
		return "DE";
	}
	public String concat() {
		return getIdTipo() + getSerial();
	}
}
