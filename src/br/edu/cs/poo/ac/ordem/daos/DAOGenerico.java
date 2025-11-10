package br.edu.cs.poo.ac.ordem.daos;

import java.io.Serializable;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.utils.Registro;

public abstract class DAOGenerico {
    protected CadastroObjetos cadastroObjetos;

    public DAOGenerico() {
        cadastroObjetos = new CadastroObjetos(getClasseEntidade());
    }

    public abstract Class<?> getClasseEntidade();

    public Registro buscar(String id) {
        return (Registro) cadastroObjetos.buscar(id);
    }

    public boolean incluir(Registro entidade) {
        if (entidade == null) return false;
        String id = entidade.getId();
        if (buscar(id) == null) {
            cadastroObjetos.incluir(entidade, id);
            return true;
        }
        return false;
    }

    public boolean alterar(Registro entidade) {
        if (entidade == null) return false;
        String id = entidade.getId();
        if (buscar(id) != null) {
            cadastroObjetos.alterar(entidade, id);
            return true;
        }
        return false;
    }

    public boolean excluir(String id) {
        if (buscar(id) != null) {
            cadastroObjetos.excluir(id);
            return true;
        }
        return false;
    }

    public Registro[] buscarTodos() {
        Serializable[] ret = cadastroObjetos.buscarTodos();
        if (ret == null || ret.length == 0) return new Registro[0];

        Registro[] out = new Registro[ret.length];
        for (int i = 0; i < ret.length; i++) {
            out[i] = (Registro) ret[i];
        }
        return out;
    }
}
