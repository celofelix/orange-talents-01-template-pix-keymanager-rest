package br.com.zup.cadastra

import br.com.zup.CadastraChavePixRequest
import br.com.zup.cadastra.validacoes.ValidaChave
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@ValidaChave
@Introspected
data class CadastraChaveRequest(
    @field:NotNull val tipoDeChave: TipoChaveRequest?,
    @field:Size(max = 77) val chave: String,
    @field:NotNull val tipoDeConta: TipoContaRequest?
) {

    fun toGrpc(clienteId: UUID): CadastraChavePixRequest {
        return CadastraChavePixRequest.newBuilder()
            .setClienteId(clienteId.toString())
            .setTipoDeChave(tipoDeChave?.grpcEnum)
            .setChave(chave)
            .setTipoDeConta(tipoDeConta?.grpcEnum)
            .build()
    }
}
