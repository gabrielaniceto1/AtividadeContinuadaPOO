package br.edu.cs.poo.ac.ordem.testes;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.edu.cs.poo.ac.ordem.entidades.Desktop;
import br.edu.cs.poo.ac.ordem.entidades.Equipamento;
import br.edu.cs.poo.ac.ordem.entidades.Notebook;

public class TesteEquipamento {
	private static final Notebook NOTE = new Notebook("123456", "Notebook 1", true, 
			4500.0, false);
	private static final Desktop DESK = new Desktop("34567890", "Desktop 1", false, 
			3000.0, false);

	@Test
	public void teste01() {
		try {
			Method metodo = Equipamento.class.getDeclaredMethod("getIdTipo");
			Assertions.assertTrue(Modifier.isAbstract(metodo.getModifiers()));
		} catch (Exception e) {
			Assertions.fail(e);
		}		
	} 
	@Test
	public void teste02() {
		Assertions.assertEquals("NO", NOTE.getIdTipo());
		Assertions.assertEquals("DE", DESK.getIdTipo());
	} 
}