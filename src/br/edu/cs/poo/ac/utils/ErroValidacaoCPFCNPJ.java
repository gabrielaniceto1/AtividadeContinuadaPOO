package br.edu.cs.poo.ac.utils;

public enum ErroValidacaoCPFCNPJ {
	CPF_CNPJ_NULO_OU_BRANCO("CPF ou CNPJ está nulo ou em branco"),	
	CPF_CNPJ_COM_TAMANHO_INVALIDO("CPF ou CNPJ com tamanho inválido"),
	CPF_CNPJ_COM_DV_INVALIDO("CPF ou CNPJ com dígito verificador inválido"),
	CPF_CNPJ_COM_CARACTERES_INVALIDOS("CPF ou CNPJ com caracteres inválidos"),
	CPF_CNPJ_NAO_E_CPF_NEM_CNPJ("Não é CPF nem CNJP");

    private final String mensagem;

    private ErroValidacaoCPFCNPJ(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}