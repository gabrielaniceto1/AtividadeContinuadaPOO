package br.edu.cs.poo.ac.ordem.entidades;

import java.io.Serializable;
import java.time.LocalDate;

import br.edu.cs.poo.ac.utils.Registro;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;

@Data
@AllArgsConstructor

public class FechamentoOrdemServico implements Registro{
	private String numeroOrdemServico;
	private LocalDate dataFechamento;
	private boolean pago;
	private String relatorioFinal;
	@Override
	public String getId() {
		return this.numeroOrdemServico;
	}
}
