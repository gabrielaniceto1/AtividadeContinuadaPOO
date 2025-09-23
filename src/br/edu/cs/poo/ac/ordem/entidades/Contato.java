package br.edu.cs.poo.ac.ordem.entidades;

import java.io.Serializable;

public class Contato implements Serializable{
	private String email;
	private String calular;
	private boolean ehZap;
	
	public Contato(String email, String calular, boolean ehZap) {
		super();
		this.email = email;
		this.calular = calular;
		this.ehZap = ehZap;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCalular() {
		return calular;
	}

	public void setCalular(String calular) {
		this.calular = calular;
	}

	public boolean isEhZap() {
		return ehZap;
	}

	public void setEhZap(boolean ehZap) {
		this.ehZap = ehZap;
	}
	
}
