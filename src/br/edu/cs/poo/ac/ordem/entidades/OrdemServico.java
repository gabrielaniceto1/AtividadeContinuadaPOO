package br.edu.cs.poo.ac.ordem.entidades;

import java.time.LocalDateTime;

import br.edu.cs.poo.ac.utils.Registro;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter @Setter

public class OrdemServico implements Registro{
	
	private Cliente cliente;
	
	private PrecoBase precoBase;
	
	private Equipamento equipamento;
	
//	private Notebook notebook;
//	private Desktop desktop;
	
	private LocalDateTime dataHoraAbertura = LocalDateTime.now();
	
	private int prazoEmDias;
	
	private double valor;
	
	private FechamentoOrdemServico dadosFechamento;
	private LocalDateTime dataHoraCancelamento;
	private String motivoCancelamento;
	private StatusOrdem status;
	private String vendedor;
	
	public OrdemServico(Cliente cliente, PrecoBase precoBase, Equipamento equipamento, LocalDateTime dataHoraAbertura, int prazoEmDias, double valor) {
		this.cliente = cliente;
		this.precoBase = precoBase;
		this.equipamento = equipamento;
		this.dataHoraAbertura = dataHoraAbertura;
		this.prazoEmDias = prazoEmDias;
		this.valor = valor;
	}
	public LocalDate getDataEstimadaEntrega() {
		LocalDate dataHora = dataHoraAbertura.toLocalDate();
		return dataHora.plusDays(prazoEmDias);
	}
	public String getNumero() {
		
		String id = equipamento.getIdTipo();
		
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
	@Override
	public String getId() {
		return getNumero();
	}
}
