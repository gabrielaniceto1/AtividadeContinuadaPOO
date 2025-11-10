package br.edu.cs.poo.ac.ordem.daos;
import java.io.Serializable;

import br.edu.cs.poo.ac.ordem.entidades.OrdemServico;
import br.edu.cs.poo.ac.utils.Registro;

//O identificador único, por objeto, de OrdemServico é o número.   
public class OrdemServicoDAO extends DAOGenerico{
	@Override
    public Class<?> getClasseEntidade() {
        return OrdemServico.class;
    }

    public OrdemServico buscar(String id) {
        return (OrdemServico) super.buscar(id);
    }

    public boolean incluir(OrdemServico entidade) {
        return super.incluir(entidade);
    }

    public boolean alterar(OrdemServico entidade) {
        return super.alterar(entidade);
    }

    public boolean excluir(String id) {
        return super.excluir(id);
    }

    public OrdemServico[] buscarTodos() {
        Registro[] rs = super.buscarTodos();
        OrdemServico[] out = new OrdemServico[rs.length];
        for (int i = 0; i < rs.length; i++) out[i] = (OrdemServico) rs[i];
        return out;
    }
}