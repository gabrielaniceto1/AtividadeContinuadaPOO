package br.edu.cs.poo.ac.ordem.entidades;

public enum TipoOrdem {
	
	MANUTENCAO(1,"Manutenção"),
	CONFIGURACAO(2, "Configuração"),
	UPGRADE(3, "Upgrade");
	
	private final int codigo;
	private final String nome;
	
	private TipoOrdem(int codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}
	public int getCodigo() {
		return this.codigo;
	}
	public String getNome() {
		return this.nome;
	}
	public static TipoOrdem getTipoOrdem(int codigo) {
		TipoOrdem[] arr = TipoOrdem.values();
		for (int i = 0; i < arr.length; i++) {
		    TipoOrdem atual = arr[i];
		    if(atual.getCodigo() == codigo) {
		    	return atual;
		    }
		}
		return null;
	}
}
