package br.edu.cs.poo.ac.ordem.testes;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;

public class TesteAbstrato {
	private static final String SEP = File.separator;
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
		File dir = new File(diretorio);
		String[] arqs = dir.list();
		int qtd = 0;
		if (arqs != null) {
			qtd = arqs.length;
		}
		return qtd;
	}
}