package br.com.zup.cadastra

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class TipoChaveRequestTest {

    @Test
    fun `deve validar chave do tipo cpf quando for um cpf valido`() {
        val tipoChave = TipoChaveRequest.CPF

        with(tipoChave) {
            Assertions.assertTrue(valida("11111111111"))
        }
    }

    @Test
    fun `nao deve validar chave do tipo cpf vazio ou null`() {
        val tipoChave = TipoChaveRequest.CPF

        with(tipoChave) {
            Assertions.assertFalse(valida(null))
            Assertions.assertFalse(valida(""))
        }
    }

    @Test
    fun `deve validar chave tipo email quando for endereco valido`() {

        val tipoChave = TipoChaveRequest.EMAIL

        with(tipoChave) {
            Assertions.assertTrue(valida("marcelo@gmail.com.br"))
            Assertions.assertTrue(valida("marcelo@gmail.com"))
        }
    }

    @Test
    fun `nao deve validar chave do tipo email quando nao for endereco valido`() {

        val tipoChave = TipoChaveRequest.EMAIL

        with(tipoChave) {
            Assertions.assertFalse(valida("marcelo.com"))
            Assertions.assertFalse(valida("marcelo.com.br"))
            Assertions.assertFalse(valida("marcelo@.com.br"))
            Assertions.assertFalse(valida("marcelo@gmail.com."))
        }
    }

    @Test
    fun `nao deve validar chave do tipo email quando for vazio ou null`() {
        val tipoChave = TipoChaveRequest.EMAIL

        with(tipoChave) {
            Assertions.assertFalse(valida(null))
            Assertions.assertFalse(valida(""))
        }
    }

    @Test
    fun `deve validar chave do tipo celular quando for numero valido`() {

        val tipoChave = TipoChaveRequest.CELULAR

        with(tipoChave) {
            Assertions.assertTrue(valida("+5511994846400"))
        }
    }

    @Test
    fun `nao deve validar chave do tipo celular quando numero nao for valido`() {
        val tipoChave = TipoChaveRequest.CELULAR

        with(tipoChave) {
            Assertions.assertFalse(valida("+5511aa4846400"))
            Assertions.assertFalse(valida("5511994846400"))
            Assertions.assertFalse(valida("11994846400"))
            Assertions.assertFalse(valida("994846400"))
        }
    }

    @Test
    fun `nao deve validar chave do tipo celular quando for vazio ou null`() {
        val tipoChave = TipoChaveRequest.CELULAR

        with(tipoChave) {
            Assertions.assertFalse(valida(null))
            Assertions.assertFalse(valida(""))
        }
    }

    @Test
    fun `deve validar chave do tipo aleatoria quando for vazia ou null`() {
        val tipoChave = TipoChaveRequest.ALEATORIA

        with(tipoChave) {
            Assertions.assertTrue(valida(null))
            Assertions.assertTrue(valida(""))
        }
    }

    @Test
    fun `nao deve validar chave do tipo aleatoria se posuir valor`() {
        val tipoChave = TipoChaveRequest.ALEATORIA

        with(tipoChave){
            Assertions.assertFalse(valida("chave aleatoria nao deve ser preenchida"))
        }
    }

}