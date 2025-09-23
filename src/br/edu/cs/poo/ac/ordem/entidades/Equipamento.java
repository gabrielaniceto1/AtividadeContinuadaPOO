package br.edu.cs.poo.ac.ordem.entidades;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Equipamento implements Serializable{
	private String serial;
	private String descricao;
	private boolean ehNovo;
	private double valorEstimado;
	
}
