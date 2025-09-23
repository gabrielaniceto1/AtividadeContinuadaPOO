package br.edu.cs.poo.ac.ordem.entidades;

import java.time.LocalDateTime;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor

public class OrdemServico implements Serializable{
	private Cliente cliente;
	private PrecoBase precoBase;
	private Notebook notebook;
	private Desktop desktop;
	private LocalDateTime dataHoraAbertura = LocalDateTime.now();
	private int prazoEmDias;
	private double valor;
	
	public LocalDate getDataEstimadaEntrega() {
		LocalDate dataHora = dataHoraAbertura.toLocalDate();
		return dataHora.plusDays(prazoEmDias);
	}
	public String getNumero() {
		
		String id = null;
		
		if(notebook != null) {
			id = notebook.getIdTipo();
		}
		else {
			id = desktop.getIdTipo();
		}
		String numeros = cliente.getCpfCnpj().replace(".", "").replace("-", "");
		
		String data = dataHoraAbertura.toLocalDate().toString();
		
		int horaInt = dataHoraAbertura.toLocalTime().getHour();
		String hora = String.valueOf(horaInt);
		
		int minutoInt = dataHoraAbertura.toLocalTime().getMinute();
		String minuto = String.valueOf(minutoInt);
		
		String dataHoraDesejada = data + hora + minuto;
		if(numeros.length() > 11) {
			return id + dataHoraDesejada.replaceAll("\\D","") + cliente.getCpfCnpj();
		}
		else {
			return id + dataHoraDesejada.replaceAll("\\D","") + "000" + cliente.getCpfCnpj();
		}
	}
}
