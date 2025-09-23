package br.edu.cs.poo.ac.ordem.testes;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.edu.cs.poo.ac.ordem.entidades.Cliente;
import br.edu.cs.poo.ac.ordem.entidades.Contato;
import br.edu.cs.poo.ac.ordem.entidades.Desktop;
import br.edu.cs.poo.ac.ordem.entidades.Notebook;
import br.edu.cs.poo.ac.ordem.entidades.OrdemServico;
import br.edu.cs.poo.ac.ordem.entidades.PrecoBase;

public class TesteEntidades {	

	private static final LocalDate DATA1 = LocalDate.parse("2000-01-01");
	private static final LocalDateTime DATA_HORA1 = LocalDateTime.parse("2020-02-01T10:20:30");	
	private static final LocalDate DATA2 = LocalDate.parse("2020-01-01");
	private static final Cliente CLI1 = new Cliente("12345678900", "Cliente 1", 
			new Contato("xxx@yyy.com.br", "81999999999", false), DATA1);
	private static final Cliente CLI2 = new Cliente("23456789011234", "Cliente 2", 
			new Contato("zzz@yyy.com.br", "81899999999", false), DATA2);
	private static final Notebook NOTE = new Notebook("123456", "Notebook 1", true, 
			4500.0, false);
	private static final Desktop DESK = new Desktop("34567890", "Desktop 1", false, 
			3000.0, false);

	@Test
	public void testeIdadeCadastroCliente() {
		int idade1 = CLI1.getIdadeCadastro();
		int idade2 = CLI2.getIdadeCadastro();
		Assertions.assertEquals(25, idade1);
		Assertions.assertEquals(5, idade2);
	}
	@Test
	public void testeNumeroOrdemServico() {
		OrdemServico ordem = new OrdemServico(CLI1, PrecoBase.MANUTENCAO_NORMAL, 
				NOTE, null, DATA_HORA1, 10, 300.0);
		String numero = ordem.getNumero();
		Assertions.assertEquals("NO20200201102000012345678900", numero);
		ordem = new OrdemServico(CLI2, PrecoBase.MANUTENCAO_NORMAL, 
				NOTE, null, DATA_HORA1, 12, 500.0);
		numero = ordem.getNumero();
		Assertions.assertEquals("NO20200201102023456789011234", numero);
		ordem = new OrdemServico(CLI1, PrecoBase.MANUTENCAO_NORMAL, 
				null, DESK, DATA_HORA1, 13, 700.0);
		numero = ordem.getNumero();
		Assertions.assertEquals("DE20200201102000012345678900", numero);
		ordem = new OrdemServico(CLI2, PrecoBase.MANUTENCAO_NORMAL, 
				null, DESK, DATA_HORA1, 14, 900.0);
		numero = ordem.getNumero();
		Assertions.assertEquals("DE20200201102023456789011234", numero);
	}
	@Test
	public void testeDataEstimadaEntregaOrdemServico() {
		OrdemServico ordem = new OrdemServico(CLI1, PrecoBase.MANUTENCAO_NORMAL, 
				NOTE, null, DATA_HORA1, 5, 100.0);
		LocalDate dataEstimada = ordem.getDataEstimadaEntrega();
		dataEstimada.equals(LocalDate.parse("2000-02-06"));
	}
}