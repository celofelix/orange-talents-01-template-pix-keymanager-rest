package br.com.zup.cadastra

import br.com.zup.KeyManagerCadastraGrpcServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.NotNull

@Validated
@Controller("/cadastra/pix")
class CadastraChaveController(
    private val cadastraPixClient:
    KeyManagerCadastraGrpcServiceGrpc.KeyManagerCadastraGrpcServiceBlockingStub
) {

    @Post("/cliente/{clienteId}")
    fun cadastra(
        @PathVariable @NotNull clienteId: UUID,
        @Valid @Body request: CadastraChaveRequest
    ): HttpResponse<Any>? {

        val chaveGrpc = request.toGrpc(clienteId)
        val response = cadastraPixClient.registra(chaveGrpc)

        val uri = UriBuilder.of("/cadastra/pix/cliente/${clienteId}/{pixId}")
            .expand(mutableMapOf("pixId" to response.pixId))

        return HttpResponse.created(uri)
    }
}