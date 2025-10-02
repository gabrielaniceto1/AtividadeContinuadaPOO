package br.edu.cs.poo.ac.ordem.testes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static br.edu.cs.poo.ac.utils.StringUtils.*;

public class TesteStringUtils {
	@Test
	public void testeStringVazia() {
		Assertions.assertTrue(estaVazia(null));
		Assertions.assertTrue(estaVazia("  "));
		Assertions.assertFalse(estaVazia("ED"));
	}
	@Test 
	public void testTamanhoExcedido() {
		Assertions.assertFalse(tamanhoExcedido("SSS", -1));
		Assertions.assertFalse(tamanhoExcedido("", -1));
		Assertions.assertFalse(tamanhoExcedido(null, -1));
		Assertions.assertFalse(tamanhoExcedido(null, 0));
		Assertions.assertTrue(tamanhoExcedido(null, 1));
		Assertions.assertFalse(tamanhoExcedido("  ", 2));
		Assertions.assertFalse(tamanhoExcedido("ABCDEFG", 7));
		Assertions.assertTrue(tamanhoExcedido("SDFERTXXSA", 8));
	}
	@Test
	public void testeEmailValido() {
		Assertions.assertTrue(emailValido("acb@cs.com.us"));
		Assertions.assertFalse(emailValido("acb#cs.com.us"));
		Assertions.assertFalse(emailValido("XMPO ASS"));
		Assertions.assertFalse(emailValido(" "));
		Assertions.assertFalse(emailValido(null));

	}
	@Test
	public void testeTelefoneValido() {
		Assertions.assertTrue(telefoneValido("(85)99990000"));
		Assertions.assertTrue(telefoneValido("(81)899900001"));
		Assertions.assertFalse(telefoneValido("(85)999900"));
		Assertions.assertFalse(telefoneValido("(85)99990000000"));
		Assertions.assertFalse(telefoneValido("82899900001"));
		Assertions.assertFalse(telefoneValido("7199990000"));
		Assertions.assertFalse(telefoneValido("     "));
		Assertions.assertFalse(telefoneValido(null));
	}
	@Test 
	public void testTamanhoMenor() {
		Assertions.assertFalse(tamanhoMenor("SSS", -1));
		Assertions.assertFalse(tamanhoMenor("", -1));
		Assertions.assertFalse(tamanhoMenor(null, -1));
		Assertions.assertTrue(tamanhoMenor("", 2));
		Assertions.assertTrue(tamanhoMenor(null, 1));
		Assertions.assertFalse(tamanhoMenor("EEEEE", 4));
		Assertions.assertFalse(tamanhoMenor("EEE", 3));
		Assertions.assertTrue(tamanhoMenor("EEEEEE", 8));
	}
}