package br.edu.cs.poo.ac.ordem.daos;

import java.io.Serializable;
import br.edu.cs.poo.ac.ordem.entidades.Desktop;


//O identificador único, por objeto, de Desktop é a concatenação do retorno 
//do método getTipo com o atributo serial.   
public class DesktopDAO extends DAOGenerico{
	public DesktopDAO() {
		super(Desktop.class);
	}
	public Desktop buscar(String concat) {
		return (Desktop)cadastroObjetos.buscar(concat);		
	}
	public boolean incluir(Desktop desktop) {
		if (buscar(desktop.concat()) == null) {
			cadastroObjetos.incluir(desktop, desktop.concat());
			return true;
		} else {
			return false;
		}
	}
	public boolean alterar(Desktop desktop) {
		if (buscar(desktop.concat()) != null) {
			cadastroObjetos.alterar(desktop, desktop.concat());
			return true;
		} else {
			return false;
		}
	}
	public boolean excluir(String concat) {
		if (buscar(concat) != null) {
			cadastroObjetos.excluir(concat);
			return true;
		} else {
			return false;
		}
	}	
	public Desktop[] buscarTodos() {
		Serializable[] ret = cadastroObjetos.buscarTodos();
		Desktop[] retorno;
		if (ret != null && ret.length > 0) {
			retorno = new Desktop[ret.length];
			for (int i=0; i<ret.length; i++) {
				retorno[i] = (Desktop)ret[i];
			}
		} else {
			retorno = new Desktop[0]; 
		}
		return retorno;
	}
}