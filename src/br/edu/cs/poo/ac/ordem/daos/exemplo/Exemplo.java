package br.edu.cs.poo.ac.ordem.daos.exemplo;

import java.io.Serializable;

public class Exemplo implements Serializable {
		private String codigo;
		private String nome;
		public Exemplo(String codigo, String nome) {
			super();
			this.codigo = codigo;
			this.nome = nome;
		}
		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}		
		public String getCodigo() {
			return codigo;
		}		
}