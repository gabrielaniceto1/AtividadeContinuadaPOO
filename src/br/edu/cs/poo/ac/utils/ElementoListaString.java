package br.edu.cs.poo.ac.utils;

class ElementoListaString {
	private String conteudo;
	private ElementoListaString proximo;
	ElementoListaString(String conteudo, ElementoListaString proximo) {
		this.conteudo = conteudo;
		this.proximo = proximo;
	}
	String getConteudo() {
		return conteudo;
	}
	void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	ElementoListaString getProximo() {
		return proximo;
	}
	void setProximo(ElementoListaString proximo) {
		this.proximo = proximo;
	}
}