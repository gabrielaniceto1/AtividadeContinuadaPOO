package br.edu.cs.poo.ac.ordem.entidades;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

import br.edu.cs.poo.ac.utils.Registro;

@Getter @Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public abstract class Equipamento implements Registro{
	private String serial;
	private String descricao;
	private boolean ehNovo;
	private double valorEstimado;
	
	public abstract String getIdTipo();
}
