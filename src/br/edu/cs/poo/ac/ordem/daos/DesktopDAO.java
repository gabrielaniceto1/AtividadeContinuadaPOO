package br.edu.cs.poo.ac.ordem.daos;

import java.io.Serializable;

import br.edu.cs.poo.ac.ordem.entidades.Desktop;
import br.edu.cs.poo.ac.utils.Registro;


//O identificador único, por objeto, de Desktop é a concatenação do retorno 
//do método getTipo com o atributo serial.   
public class DesktopDAO extends DAOGenerico{
	@Override
    public Class<?> getClasseEntidade() {
        return Desktop.class;
    }

    public Desktop buscar(String id) {
        return (Desktop) super.buscar(id);
    }

    public boolean incluir(Desktop entidade) {
        return super.incluir(entidade);
    }

    public boolean alterar(Desktop entidade) {
        return super.alterar(entidade);
    }

    public boolean excluir(String id) {
        return super.excluir(id);
    }

    public Desktop[] buscarTodos() {
        Registro[] rs = super.buscarTodos();
        Desktop[] out = new Desktop[rs.length];
        for (int i = 0; i < rs.length; i++) out[i] = (Desktop) rs[i];
        return out;
    }
}