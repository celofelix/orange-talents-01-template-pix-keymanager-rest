package br.com.zup.cadastra

import br.com.zup.CadastraChavePixResponse
import br.com.zup.KeyManagerCadastraGrpcServiceGrpc
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
internal class CadastraChaveControllerTest {

    @field:Inject
    lateinit var registraGrpc: KeyManagerCadastraGrpcServiceGrpc.KeyManagerCadastraGrpcServiceBlockingStub

    @field:Inject
    @field:Client(value = "/")
    lateinit var clientHttp: HttpClient

    @Test
    fun `deve cadastrar uma chave pix request`() {

        val clientID = UUID.randomUUID().toString()
        val pixID = Random.nextLong(10).toString()

        /* Criando objeto que será a resposta no mock do serviço externo do grpc */
        val chaveResponse = CadastraChavePixResponse.newBuilder()
            .setClienteId(clientID)
            .setPixId(pixID)
            .build()

        /* Simulando o corportamento do client grpc para chamada externa
        O retorno será o objeto chaveResponse criado anteriormente */
        Mockito.`when`(registraGrpc.registra(Mockito.any())).thenReturn(chaveResponse)

        /* Criando o objeto que será enviado no corpo da requisição */
        val chaveRequest = CadastraChaveRequest(
            TipoChaveRequest.EMAIL,
            chave = "marcelo@gmail.com",
            tipoDeConta = TipoContaRequest.CONTA_CORRENTE
        )

        /* Montando o método http da requisição com a uir do recurso e parametros que vão no corpo da requisição */
        val request = HttpRequest.POST("/cadastra/pix/cliente/${clientID}", chaveRequest)

        /*Executando a requisição para o endpoint rest */
        val response = clientHttp.toBlocking().exchange(request, CadastraChaveRequest::class.java)

        /* Validando a resposta do endpoint */
        with(response) {
            Assertions.assertEquals(HttpStatus.CREATED, status)
            Assertions.assertTrue(headers.contains("Location"))
            Assertions.assertTrue(header("Location")!!.contains(pixID))
        }
    }


    @Factory
    /* A anotação @Replaces é usada para o micronaut substituir a classe alvo no contexto de testes
    Dessa forma a classe que fabrica os clients gRPC não são afetadas no contexto de teste
    A classe é a KeyManagerGrpcFactoryClient ela é a responsável por gerar os clients gRPC
    Dessa forma é fabricado um Mock para simular o comporamento do client gRPC */
    @Replaces(factory = KeyManagerGrpcFactoryClient::class)
    internal class GrpcStubFactory {
        @Singleton
        fun stub() = Mockito
            .mock(KeyManagerCadastraGrpcServiceGrpc.KeyManagerCadastraGrpcServiceBlockingStub::class.java)
    }

}


