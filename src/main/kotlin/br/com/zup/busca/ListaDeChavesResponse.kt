package br.com.zup.busca

import br.com.zup.ListaChavesResponse
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class ListaDeChavesResponse(chavesPix: ListaChavesResponse.ChavesPix) {

    val id = chavesPix.pixId
    val tipoDeChave = chavesPix.tipoChave
    val chave = chavesPix.chave
    val tipoDeConta = chavesPix.tipoConta
    val criadaEm = chavesPix.criadaEm.let {
        LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneOffset.UTC)
    }
}
