package br.edu.cs.poo.ac.ordem.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;
import java.io.Serializable;

@Data

public class FechamentoOrdemServico implements Serializable{
	private String numeroOrdemServico;
	private LocalDate dataFechamento;
	private boolean pago;
	private String relatorioFinal;
}
