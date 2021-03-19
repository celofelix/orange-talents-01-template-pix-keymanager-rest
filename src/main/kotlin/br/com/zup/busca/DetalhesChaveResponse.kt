package br.com.zup.busca

import br.com.zup.DetalhesChavePixResponse
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class DetalhesChaveResponse(response: DetalhesChavePixResponse) {

    val pixId = response.pixId
    val clienteId = response.clienteId
    val tipoDeChave = response.tipoDeChave
    val chave = response.chave
    val conta = ContaResponse(
        response.conta.titular,
        response.conta.cpf,
        response.conta.instituicao,
        response.conta.agencia,
        response.conta.numeroConta,
        response.conta.tipo
    )
    val criadaEm = response.criadaEm.let {
        LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneOffset.UTC)
    }

}
