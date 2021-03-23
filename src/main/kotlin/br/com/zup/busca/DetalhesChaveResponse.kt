package br.com.zup.busca

import br.com.zup.DetalhesChavePixResponse
import io.micronaut.core.annotation.Introspected
import io.micronaut.validation.Validated
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Validated
@Introspected
class DetalhesChaveResponse(response: DetalhesChavePixResponse) {

    @field:NotBlank
    val pixId = response.pixId

    @field:NotBlank
    val clienteId = response.clienteId

    @field:NotBlank
    val tipoDeChave = response.pixId

    @field:NotBlank
    val chave = response.chave.toString()

    @field:NotNull
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
