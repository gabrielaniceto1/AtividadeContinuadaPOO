package br.edu.cs.poo.ac.ordem.testes;
import org.junit.jupiter.api.Test;

import br.edu.cs.poo.ac.utils.ErroValidacaoCPFCNPJ;
import br.edu.cs.poo.ac.utils.ResultadoValidacaoCPFCNPJ;
import br.edu.cs.poo.ac.utils.ValidadorCPFCNPJ;

import static org.junit.jupiter.api.Assertions.*;

public class TesteValidadorCPFCNPJ {

    // CPF válido: 52998224725
    @Test
    public void testeCPFValido() {
        ResultadoValidacaoCPFCNPJ resultado = ValidadorCPFCNPJ.validarCPFCNPJ("52998224725");
        assertTrue(resultado.isCPF());
        assertFalse(resultado.isCNPJ());
        assertNull(resultado.getErroValidacao());
    }
    @Test
    public void testeCPFInvalido() {
    	testeCPFComDVInvalido();
    	testeCPFComTamanhoInvalido();
    	testeCPFNuloOuEmBranco();
    }
    
    private void testeCPFComDVInvalido() {
        ResultadoValidacaoCPFCNPJ resultado = ValidadorCPFCNPJ.validarCPFCNPJ("52998224724");
        assertEquals(ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_DV_INVALIDO, resultado.getErroValidacao());
    }
    
    private void testeCPFComTamanhoInvalido() {
        ResultadoValidacaoCPFCNPJ resultado = ValidadorCPFCNPJ.validarCPFCNPJ("123456789");
        assertEquals(ErroValidacaoCPFCNPJ.CPF_CNPJ_NAO_E_CPF_NEM_CNPJ, resultado.getErroValidacao());
    }

    private void testeCPFNuloOuEmBranco() {
        ResultadoValidacaoCPFCNPJ resultado = ValidadorCPFCNPJ.validarCPFCNPJ("   ");
        assertEquals(ErroValidacaoCPFCNPJ.CPF_CNPJ_NAO_E_CPF_NEM_CNPJ, resultado.getErroValidacao());
    }

    // CNPJ válido: 11444777000161
    @Test
    public void testeCNPJValido() {
        ResultadoValidacaoCPFCNPJ resultado = ValidadorCPFCNPJ.validarCPFCNPJ("11444777000161");
        assertFalse(resultado.isCPF());
        assertTrue(resultado.isCNPJ());
        assertNull(resultado.getErroValidacao());
    }
    @Test
    public void testeCNPJInvalido() {
    	testeCNPJComDVInvalido();
    	testeCNPJComTamanhoInvalido();
    	testeCNPJNuloOuEmBranco();
    	testeEntradaQueNaoEhCPFNemCNPJ();
    }

    private void testeCNPJComDVInvalido() {
        ResultadoValidacaoCPFCNPJ resultado = ValidadorCPFCNPJ.validarCPFCNPJ("11444777000162");
        assertEquals(ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_DV_INVALIDO, resultado.getErroValidacao());
    }

    
    private void testeCNPJComTamanhoInvalido() {
        ResultadoValidacaoCPFCNPJ resultado = ValidadorCPFCNPJ.validarCPFCNPJ("123456789012");
        assertEquals(ErroValidacaoCPFCNPJ.CPF_CNPJ_NAO_E_CPF_NEM_CNPJ, resultado.getErroValidacao());
    }

    private void testeCNPJNuloOuEmBranco() {
        ResultadoValidacaoCPFCNPJ resultado = ValidadorCPFCNPJ.validarCPFCNPJ(null);
        assertEquals(ErroValidacaoCPFCNPJ.CPF_CNPJ_NAO_E_CPF_NEM_CNPJ, resultado.getErroValidacao());
    }

    private void testeEntradaQueNaoEhCPFNemCNPJ() {
        ResultadoValidacaoCPFCNPJ resultado = ValidadorCPFCNPJ.validarCPFCNPJ("123");
        assertEquals(ErroValidacaoCPFCNPJ.CPF_CNPJ_NAO_E_CPF_NEM_CNPJ, resultado.getErroValidacao());
    }
}