package br.com.zup.busca

import br.com.zup.ListaChavesResponse
import io.micronaut.core.annotation.Introspected
import io.micronaut.validation.Validated
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Validated
@Introspected
class ListaDeChavesResponse(chavesPix: ListaChavesResponse.ChavesPix) {

    @field:NotBlank
    val id = chavesPix.pixId

    @field:NotBlank
    val tipoDeChave = chavesPix.tipoChave

    @field:NotBlank
    val chave = chavesPix.chave

    @field:NotBlank
    val tipoDeConta = chavesPix.tipoConta

    @field:NotNull
    val criadaEm = chavesPix.criadaEm.let {
        LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneOffset.UTC)
    }
}
