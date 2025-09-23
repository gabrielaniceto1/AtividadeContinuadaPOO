package br.edu.cs.poo.ac.ordem.daos;
import java.io.Serializable;

import br.edu.cs.poo.ac.ordem.entidades.Notebook;

//O identificador único, por objeto, de Notebook é a concatenação do retorno 
//do método getTipo com o atributo serial.   
public class NotebookDAO extends DAOGenerico{
	public NotebookDAO() {
		super(Notebook.class);
	}
	public Notebook buscar(String concat) {
		return (Notebook)cadastroObjetos.buscar(concat);		
	}
	public boolean incluir(Notebook notebook) {
		if (buscar(notebook.concat()) == null) {
			cadastroObjetos.incluir(notebook, notebook.concat());
			return true;
		} else {
			return false;
		}
	}
	public boolean alterar(Notebook notebook) {
		if (buscar(notebook.concat()) != null) {
			cadastroObjetos.alterar(notebook, notebook.concat());
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
	public Notebook[] buscarTodos() {
		Serializable[] ret = cadastroObjetos.buscarTodos();
		Notebook[] retorno;
		if (ret != null && ret.length > 0) {
			retorno = new Notebook[ret.length];
			for (int i=0; i<ret.length; i++) {
				retorno[i] = (Notebook)ret[i];
			}
		} else {
			retorno = new Notebook[0]; 
		}
		return retorno;
	}
}