package br.com.zup.exclui

import br.com.zup.ExcluiChavePixRequest
import br.com.zup.KeyManagerExcluiGrpcServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.PathVariable
import io.micronaut.validation.Validated
import java.util.*
import javax.inject.Inject
import javax.validation.Valid
import javax.validation.constraints.NotNull

@Validated
@Controller("/deleta/pix/{pixId}")
class ExcluiChaveController(
    @Inject private val excluiChaveClient:
    KeyManagerExcluiGrpcServiceGrpc.KeyManagerExcluiGrpcServiceBlockingStub
) {

    @Delete("/cliente/{clienteId}")
    fun deleta(@Valid @PathVariable @NotNull pixId: Long, @PathVariable @NotNull clienteId: UUID): HttpResponse<Any> {

        val excluiChaveRequest = ExcluiChavePixRequest.newBuilder()
            .setPixId(pixId.toString())
            .setClienteId(clienteId.toString())
            .build()

        excluiChaveClient.exclui(excluiChaveRequest)

        return HttpResponse.ok()
    }

}