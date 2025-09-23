package br.edu.cs.poo.ac.ordem.entidades;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString

public class Notebook extends Equipamento{
	private boolean carregaDadosSensiveis;
	
	public Notebook(String serial, String descricao, boolean ehNovo, double valorEstimado,
			boolean carregaDadosSensiveis) {
		super(serial, descricao, ehNovo, valorEstimado);
		this.carregaDadosSensiveis = carregaDadosSensiveis;
	}
	public String getIdTipo() {
		return "NO";
	}

	public String concat() {
		String concat = getIdTipo() + getSerial();
		return concat;
	}

}
