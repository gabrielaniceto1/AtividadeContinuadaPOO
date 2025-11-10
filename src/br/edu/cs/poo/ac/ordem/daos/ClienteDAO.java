package br.edu.cs.poo.ac.ordem.daos;
import java.io.Serializable;
import br.edu.cs.poo.ac.ordem.entidades.Cliente;
import br.edu.cs.poo.ac.utils.Registro;

//O identificador único, por objeto, de Cliente é o cpfCnpj.  
public class ClienteDAO extends DAOGenerico{
	@Override
    public Class<?> getClasseEntidade() {
        return Cliente.class;
    }

    public Cliente buscar(String id) {
        return (Cliente) super.buscar(id);
    }

    public boolean incluir(Cliente entidade) {
        return super.incluir(entidade);
    }

    public boolean alterar(Cliente entidade) {
        return super.alterar(entidade);
    }

    public boolean excluir(String id) {
        return super.excluir(id);
    }

    public Cliente[] buscarTodos() {
        Registro[] rs = super.buscarTodos();
        Cliente[] out = new Cliente[rs.length];
        for (int i = 0; i < rs.length; i++) out[i] = (Cliente) rs[i];
        return out;
    }
}