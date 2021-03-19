package br.com.zup.busca

import br.com.zup.BuscaChavePorIDPixRequest
import br.com.zup.DetalhesChavePixResponse
import br.com.zup.KeyManagerBuscaChavePorIDGrpcServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.validation.Validated
import java.util.*
import javax.inject.Inject

@Validated
@Controller("/pix/{pixId}/cliente/{clienteId}")
class BuscaChavePorIDController(
    @Inject val buscaClint:
    KeyManagerBuscaChavePorIDGrpcServiceGrpc.KeyManagerBuscaChavePorIDGrpcServiceBlockingStub
) {

    @Get
    fun buscaChavePorID(
        @PathVariable clienteId: UUID,
        @PathVariable pixId: Long
    ): HttpResponse<Any>? {

        val buscaRequest = BuscaChavePorIDPixRequest.newBuilder()
            .setClienteId(clienteId.toString())
            .setPixId(pixId.toString())
            .build()

        val response = buscaClint.buscaChavePorID(buscaRequest)

        return HttpResponse.ok(DetalhesChaveResponse(response))
    }
}