package br.edu.cs.poo.ac.ordem.entidades;

import lombok.Getter;

@Getter
public enum StatusOrdem {
	ABERTA(1, "Aberta"),
	CANCELADA(2, "Cancelada"),
	FECHADA(3, "Fechada");
	
	private int codigo;
	private String descricao;
	
	private StatusOrdem(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
}
