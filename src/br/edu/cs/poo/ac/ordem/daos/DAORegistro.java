package br.edu.cs.poo.ac.ordem.daos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.excecoes.ExcecaoObjetoJaExistente;
import br.edu.cs.poo.ac.excecoes.ExcecaoObjetoNaoExistente;
import br.edu.cs.poo.ac.utils.Registro;

public class DAORegistro<T extends Registro> {

    private final CadastroObjetos cadastro;
    private final String nomeClasse;
    public DAORegistro(Class<T> classeEntidade) {
        this.cadastro = new CadastroObjetos(classeEntidade);
        this.nomeClasse = classeEntidade.getSimpleName();
    }

    private String getNomeClasse() {
        return nomeClasse;
    }
    
    public T buscar(String id) {
        return (T) cadastro.buscar(id);
    }

    public void incluir(T entidade) throws ExcecaoObjetoJaExistente {
        if (entidade == null) {
            return;
        }
        String id = entidade.getId();
        if (buscar(id) != null) {
            throw new ExcecaoObjetoJaExistente(getNomeClasse() + " já existente");
        }
        cadastro.incluir(entidade, id);
    }

    public void alterar(T entidade) throws ExcecaoObjetoNaoExistente {
        if (entidade == null) {
            return;
        }
        String id = entidade.getId();
        if (buscar(id) == null) {
            throw new ExcecaoObjetoNaoExistente(getNomeClasse() + " não existente");
        }
        cadastro.alterar(entidade, id);
    }

    public void excluir(String id) throws ExcecaoObjetoNaoExistente {
        if (buscar(id) == null) {
            throw new ExcecaoObjetoNaoExistente(getNomeClasse() + " não existente");
        }
        cadastro.excluir(id);
    }

    public List<T> buscarTodos() {
        Serializable[] ret = cadastro.buscarTodos();
        List<T> lista = new ArrayList<>();

        if (ret != null) {
            for (Serializable s : ret) {
                lista.add((T) s);
            }
        }
        return lista;
    }
}
