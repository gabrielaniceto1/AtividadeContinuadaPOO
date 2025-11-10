package br.edu.cs.poo.ac.ordem.daos;
import java.io.Serializable;

import br.edu.cs.poo.ac.ordem.entidades.FechamentoOrdemServico;
import br.edu.cs.poo.ac.utils.Registro;

//O identificador único, por objeto, de FechamentoOrdemServico 
//é o número da ordem de serviço.   
public class FechamentoOrdemServicoDAO extends DAOGenerico{
	@Override
    public Class<?> getClasseEntidade() {
        return FechamentoOrdemServico.class;
    }

    public FechamentoOrdemServico buscar(String id) {
        return (FechamentoOrdemServico) super.buscar(id);
    }

    public boolean incluir(FechamentoOrdemServico entidade) {
        return super.incluir(entidade);
    }

    public boolean alterar(FechamentoOrdemServico entidade) {
        return super.alterar(entidade);
    }

    public boolean excluir(String id) {
        return super.excluir(id);
    }

    public FechamentoOrdemServico[] buscarTodos() {
        Registro[] rs = super.buscarTodos();
        FechamentoOrdemServico[] out = new FechamentoOrdemServico[rs.length];
        for (int i = 0; i < rs.length; i++) out[i] = (FechamentoOrdemServico) rs[i];
        return out;
    }
}