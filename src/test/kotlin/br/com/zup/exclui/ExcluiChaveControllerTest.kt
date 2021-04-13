package br.com.zup.exclui

import br.com.zup.ExcluiChavePixResponse
import br.com.zup.KeyManagerExcluiGrpcServiceGrpc
import br.com.zup.grpc.KeyManagerGrpcFactoryClient
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@MicronautTest
internal class ExcluiChaveControllerTest {

    @field:Inject
    lateinit var excluirGrpc: KeyManagerExcluiGrpcServiceGrpc.KeyManagerExcluiGrpcServiceBlockingStub

    @field:Inject
    @field:Client(value = "/")
    lateinit var clientHttp: HttpClient

    @Test
    fun `deve excluir chave pix existente`() {

        val clienteID = UUID.randomUUID().toString()
        val pixID = Random.nextLong(10).toString()

        println(pixID)

        val responsePix = ExcluiChavePixResponse.newBuilder()
            .setClienteId(clienteID)
            .setPixId(pixID)
            .build()

        Mockito.`when`(excluirGrpc.exclui(Mockito.any())).thenReturn(responsePix)

        val request = HttpRequest.DELETE<Any>("/deleta/pix/${pixID}/cliente/${clienteID}")
        val response = clientHttp.toBlocking().exchange(request, Any::class.java)

        with(response) {
            Assertions.assertEquals(HttpStatus.OK, status)
        }
    }

    @Factory
    @Replaces(factory = KeyManagerGrpcFactoryClient::class)
    internal class GrpcStubFactory {
        @Singleton
        fun stub() = Mockito
            .mock(KeyManagerExcluiGrpcServiceGrpc.KeyManagerExcluiGrpcServiceBlockingStub::class.java)
    }
}