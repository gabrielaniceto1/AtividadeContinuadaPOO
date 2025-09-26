package br.edu.cs.poo.ac.utils;

public class ValidadorCPFCNPJ {
	
public static ResultadoValidacaoCPFCNPJ validarCPFCNPJ(String cpfCnpj) {
	boolean cpf = ValidadorCPFCNPJ.isCPF(cpfCnpj);
	boolean cnpj = ValidadorCPFCNPJ.isCNPJ(cpfCnpj);
	ErroValidacaoCPFCNPJ erro = null;
	if(cpf) {
		erro = ValidadorCPFCNPJ.validarCPF(cpfCnpj);
	}
	else if (cnpj){
		erro = ValidadorCPFCNPJ.validarCNPJ(cpfCnpj);
	}
	else {
		erro = ErroValidacaoCPFCNPJ.CPF_CNPJ_NAO_E_CPF_NEM_CNPJ;
	}
	ResultadoValidacaoCPFCNPJ novo = new ResultadoValidacaoCPFCNPJ(cpf, cnpj, erro);
	return novo;
}

public static boolean isCPF(String valor) {
	if(valor == null) return false;
	String numeros = valor.replaceAll("\\D", "");
	if(numeros.length() == 11) return true;
	else return false;
}

public static boolean isCNPJ(String valor) {
	if(valor == null) return false;
	String numeros = valor.replaceAll("\\D", "");
	if(numeros.length() == 14) return true;
	else return false;
}

public static ErroValidacaoCPFCNPJ validarCPF(String cpf) {
	if(StringUtils.estaVazia(cpf)) return ErroValidacaoCPFCNPJ.CPF_CNPJ_NULO_OU_BRANCO;
	boolean temLetras = false;
	for(int i = 0; i < cpf.length(); i++) {
		char ch = cpf.charAt(i);
		if(Character.isLetter(ch)) {
			temLetras = true;
		}
	}
	if(temLetras) {
		return ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_CARACTERES_INVALIDOS;
	}
	if(StringUtils.tamanhoExcedido(cpf.replaceAll("\\D", ""), 11) == true ||
			cpf.replaceAll("\\D","").length() < 11) {
		return ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_TAMANHO_INVALIDO;
	}

	if(ValidadorCPFCNPJ.isDigitoVerificadorValidoCPF(cpf) == false) {
		return ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_DV_INVALIDO;
	}
	
	return null;
}

public static ErroValidacaoCPFCNPJ validarCNPJ(String cnpj) {
	if(StringUtils.estaVazia(cnpj)) return ErroValidacaoCPFCNPJ.CPF_CNPJ_NULO_OU_BRANCO;
	boolean temLetras = false;
	for(int i = 0; i < cnpj.length(); i++) {
		char ch = cnpj.charAt(i);
		if(Character.isLetter(ch)) {
			temLetras = true;
		}
	}
	if(temLetras) {
		return ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_CARACTERES_INVALIDOS;
	}
	if(StringUtils.tamanhoExcedido(cnpj.replaceAll("\\D",""), 14) == true ||
			cnpj.replaceAll("\\D","").length() < 14) {
		return ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_TAMANHO_INVALIDO;
	}
	if(ValidadorCPFCNPJ.isDigitoVerificadorValidoCNPJ(cnpj) == false) {
		return ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_DV_INVALIDO;
	}
	
	return null;
}

private static boolean isDigitoVerificadorValidoCPF(String cpf) {
	String numeros = cpf.replaceAll("\\D", "");
	int soma = 0;
    for (int i = 0, peso = 10; i < 9; i++, peso--) {
        soma += (numeros.charAt(i) - '0') * peso;
    }
    int r = soma % 11;
    int d1 = (r < 2) ? 0 : 11 - r;

    soma = 0;
    for (int i = 0, peso = 11; i < 9; i++, peso--) {
        soma += (numeros.charAt(i) - '0') * peso;
    }
    soma += d1 * 2;
    r = soma % 11;
    int d2 = (r < 2) ? 0 : 11 - r;

    return d1 == (numeros.charAt(9) - '0') && d2 == (numeros.charAt(10) - '0');
}

private static boolean isDigitoVerificadorValidoCNPJ(String cnpj) {
	String numeros = cnpj.replaceAll("\\D", "");
	int[] w1 = {5,4,3,2,9,8,7,6,5,4,3,2};
    int[] w2 = {6,5,4,3,2,9,8,7,6,5,4,3,2};

    int soma = 0;
    for (int i = 0; i < 12; i++) {
        soma += (numeros.charAt(i) - '0') * w1[i];
    }
    int r = soma % 11;
    int d1 = (r < 2) ? 0 : 11 - r;

    soma = 0;
    for (int i = 0; i < 12; i++) {
        soma += (numeros.charAt(i) - '0') * w2[i];
    }
    soma += d1 * w2[12]; // peso 2
    r = soma % 11;
    int d2 = (r < 2) ? 0 : 11 - r;

    return d1 == (numeros.charAt(12) - '0') && d2 == (numeros.charAt(13) - '0');
}

}