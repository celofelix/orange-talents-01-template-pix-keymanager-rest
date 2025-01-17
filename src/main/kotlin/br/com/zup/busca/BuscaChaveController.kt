package br.com.zup.busca

import br.com.zup.*
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.validation.Validated
import java.util.*
import javax.inject.Inject

@Validated
@Controller("/pix")
class BuscaChaveController(
    @Inject val buscaClient: KeyManagerBuscaChavePorIDGrpcServiceGrpc.KeyManagerBuscaChavePorIDGrpcServiceBlockingStub,
    @Inject val buscaPorChaveClient: KeyManagerBuscaChaveGrpcServiceGrpc.KeyManagerBuscaChaveGrpcServiceBlockingStub,
    @Inject val listaChavesClient: KeyManagerListaChavesGrpcServiceGrpc.KeyManagerListaChavesGrpcServiceBlockingStub
) {

    @Get(value = "/{pixId}/cliente/{clienteId}")
    fun buscaChavePorID(
        @PathVariable clienteId: UUID,
        @PathVariable pixId: Long
    ): HttpResponse<Any>? {

        val buscaRequest = BuscaChavePorIDPixRequest.newBuilder()
            .setClienteId(clienteId.toString())
            .setPixId(pixId.toString())
            .build()

        val response = buscaClient.buscaChavePorID(buscaRequest)

        return HttpResponse.ok(DetalhesChaveResponse(response))
    }

    @Get(value = "/{chave}")
    fun buscaPorChave(@PathVariable chave: String): HttpResponse<Any>? {

        val buscaChaveRequest = BuscaPorChavePixRequest.newBuilder()
            .setChave(chave)
            .build()

        val chaveResponse = buscaPorChaveClient.buscaPorChave(buscaChaveRequest)

        return HttpResponse.ok(DetalhesChaveResponse(chaveResponse))
    }

    @Get(value = "/cliente/{clienteId}")
    fun listaChavesPorCliente(@PathVariable clienteId: UUID): HttpResponse<Any> {

        val listaRequest = ListaChavesPixRequest.newBuilder()
            .setClienteId(clienteId.toString())
            .build()

        val chavesResponse = listaChavesClient.listaChaves(listaRequest)

        val listaChavesResponse = chavesResponse.chavesList.map { ListaDeChavesResponse(it) }

        return HttpResponse.ok(listaChavesResponse)
    }
}