package br.edu.cs.poo.ac.ordem.testes;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.ordem.mediators.ResultadoMediator;

public class TesteAbstrato {
	protected static final String SEP = File.separator;
	protected static final String STR_VAZIA = "";
	protected final CadastroObjetos cadastro;
	private final Class classeCadastro;
	private final String diretorio;
	
	public TesteAbstrato(Class classeCadastro) {
		this.classeCadastro = classeCadastro;
		this.cadastro = new CadastroObjetos(this.classeCadastro);
		this.diretorio = "." + SEP + this.classeCadastro.getSimpleName(); 
	}
	
	@BeforeEach
	public void limparRegistros() {
		FileUtils.limparDiretorio(diretorio);
	}
	
	protected int obterQuantidadeRegistros() {
		return obterQuantidadeRegistrosPorTipo(classeCadastro);
	} 
	protected int obterQuantidadeRegistrosPorTipo(Class classe) {
		File dir = new File("." + SEP + classe.getSimpleName());
		String[] arqs = dir.list();
		int qtd = 0;
		if (arqs != null) {
			qtd = arqs.length;
		}
		return qtd;
	} 
	protected void assertionsResultadoMediatorNaoValidado(ResultadoMediator res) {
		Assertions.assertNotNull(res);
		Assertions.assertFalse(res.isValidado());
		Assertions.assertFalse(res.isOperacaoRealizada());
		Assertions.assertNotNull(res.getMensagensErro());
		Assertions.assertTrue(res.getMensagensErro().tamanho() > 0);
	}
	protected void assertionsResultadoMediatorValidado(ResultadoMediator res) {
		Assertions.assertNotNull(res);
		Assertions.assertTrue(res.isValidado());
		Assertions.assertFalse(res.isOperacaoRealizada());
		Assertions.assertNotNull(res.getMensagensErro());
		Assertions.assertEquals(0, res.getMensagensErro().tamanho());	
	}
	
	protected boolean temUmSoConstrutorPrivado(Class classe) {
        Constructor<?>[] construtores = classe.getDeclaredConstructors();
        if (construtores == null || construtores.length != 1) {
        	return false;
        } else {
        	return Modifier.isPrivate(construtores[0].getModifiers());
        }
	}
	protected boolean herdaDe(Class<?> classeFilha, Class<?> classeMae) {
	    Class<?> atual = classeFilha.getSuperclass();
	    while (atual != null) {
	        if (atual.equals(classeMae)) {
	            return true;
	        }
	        atual = atual.getSuperclass();
	    }
	    return false;
	}

}