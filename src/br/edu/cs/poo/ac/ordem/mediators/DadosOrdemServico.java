package br.edu.cs.poo.ac.ordem.mediators;

public class DadosOrdemServico {
	private String cpfCnpjCliente;
	private int codigoPrecoBase;
	private String idEquipamento;
	private String vendedor;
	public DadosOrdemServico(String cpfCnpjCliente, int codigoPrecoBase, String idEquipamento,
			String vendedor) {
		this.cpfCnpjCliente = cpfCnpjCliente;
		this.codigoPrecoBase = codigoPrecoBase;
		this.idEquipamento = idEquipamento;
		this.vendedor = vendedor;
	}
	public String getCpfCnpjCliente() {
		return cpfCnpjCliente;
	}
	public int getCodigoPrecoBase() {
		return codigoPrecoBase;
	}
	public String getIdEquipamento() {
		return idEquipamento;
	}
	public String getVendedor() {
		return vendedor;
	}	
}