package br.edu.cs.poo.ac.ordem.entidades;
import java.time.LocalDate;
import java.time.Period;
import java.io.Serializable;

public class Cliente implements Serializable{
	private String cpfCnpj;
	private String nome;
	private Contato contato;
	private LocalDate dataCadastro = LocalDate.now();
	
	public Cliente(String cpfCnpj, String nome, Contato contato, LocalDate dataCadastro) {
		this.cpfCnpj = cpfCnpj;
		this.nome = nome;
		this.contato = contato;
		this.dataCadastro = dataCadastro;
	}
	public String getCpfCnpj() {
		return this.cpfCnpj;
	}
	public LocalDate getDataCadastro() {
		return this.dataCadastro;
	}
	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Contato getContato() {
		return this.contato;
	}
	public void setContato(Contato contato) {
		this.contato = contato;
	}
	public int getIdadeCadastro() {
		LocalDate hoje = LocalDate.now();
        if (dataCadastro == null) return 0;
        //Period.between(hoje, dataCadastro).getYears();
        int anos = hoje.getYear() - dataCadastro.getYear();
        return anos;
	}
}
