package br.edu.cs.poo.ac.ordem.daos;
import java.io.Serializable;

import br.edu.cs.poo.ac.ordem.entidades.Notebook;
import br.edu.cs.poo.ac.utils.Registro;

//O identificador único, por objeto, de Notebook é a concatenação do retorno 
//do método getTipo com o atributo serial.   
public class NotebookDAO extends DAOGenerico{
	@Override
    public Class<?> getClasseEntidade() {
        return Notebook.class;
    }

    public Notebook buscar(String id) {
        return (Notebook) super.buscar(id);
    }

    public boolean incluir(Notebook entidade) {
        return super.incluir(entidade);
    }

    public boolean alterar(Notebook entidade) {
        return super.alterar(entidade);
    }

    public boolean excluir(String id) {
        return super.excluir(id);
    }

    public Notebook[] buscarTodos() {
        Registro[] rs = super.buscarTodos();
        Notebook[] out = new Notebook[rs.length];
        for (int i = 0; i < rs.length; i++) out[i] = (Notebook) rs[i];
        return out;
    }
}