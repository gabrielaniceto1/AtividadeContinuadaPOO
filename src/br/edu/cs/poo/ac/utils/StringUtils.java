package br.edu.cs.poo.ac.utils;

public class StringUtils {
	public static boolean estaVazia(String str) {
		if(str == null) {
			return true;
		}
		for(int i = 0; i < str.length(); i++) {
			char atual = str.charAt(i);
			if(atual != ' ') {
				return false;
			}
		}
		return true;
	}
	public static boolean tamanhoExcedido(String str, int tamanho) {
		if(tamanho < 0) return false;
		if(str == null) {
			return tamanho > 0 ? true : false;
		}
		if(str.length() > tamanho){
			return true;
		}
		
		return false;
	}
	public static boolean emailValido(String email) {
		if(email == null) return false;
		if(email.indexOf('@') < 0 || email.indexOf('.') < 0) {
			return false;
		}
		return true;
	}
	public static boolean telefoneValido(String tel) {
		if(tel == null) return false;
		if(tel.indexOf('(') < 0 || tel.indexOf(')') < 0) {
			return false;
		}
		String numeroPuro = tel.substring(tel.indexOf(')') + 1, tel.length());
		if(numeroPuro.length() > 9 || numeroPuro.length() < 8) {
			return false;
		}
		return true;
	}
}