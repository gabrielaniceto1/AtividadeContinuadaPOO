package br.edu.cs.poo.ac.ordem.entidades;

public enum PrecoBase {
	MANUTENCAO_NORMAL(TipoOrdem.MANUTENCAO, Dificuldade.NORMAL, 50.00),
	MANUTENCAO_DIFICIL(TipoOrdem.MANUTENCAO, Dificuldade.DIFICIL, 70.00),
	CONFIGURACAO_NORMAL(TipoOrdem.CONFIGURACAO, Dificuldade.NORMAL, 30.00),
	CONFIGURACAO_DIFICIL(TipoOrdem.CONFIGURACAO, Dificuldade.DIFICIL, 45.00),
	UPGRADE_NORMAL(TipoOrdem.UPGRADE, Dificuldade.NORMAL, 95.00),
	UPGRADE_DIFICIL(TipoOrdem.UPGRADE, Dificuldade.DIFICIL, 110.00);
	
	private final TipoOrdem tipoOrdem;
	private final Dificuldade dificuldade;
	private final double preco;
	
	private PrecoBase(TipoOrdem tipoOrdem, Dificuldade dificuldade, double preco) {
		this.tipoOrdem = tipoOrdem;
		this.dificuldade = dificuldade;
		this.preco = preco;
	}

	public TipoOrdem getTipoOrdem() {
		return tipoOrdem;
	}

	public Dificuldade getDificuldade() {
		return dificuldade;
	}

	public double getPreco() {
		return preco;
	}
	
	public static PrecoBase getPrecoBase(TipoOrdem tipoOrdem, Dificuldade dificuldade) {
		PrecoBase arr[] = PrecoBase.values();
		for(int i = 0; i < arr.length; i++) {
			PrecoBase atual = arr[i];
			if(atual.getTipoOrdem() == tipoOrdem && atual.getDificuldade() == dificuldade) {
				return atual;
			}
		}
		return null;
	}
}
