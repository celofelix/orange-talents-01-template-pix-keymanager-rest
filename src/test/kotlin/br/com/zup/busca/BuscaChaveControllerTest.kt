package br.com.zup.busca

import br.com.zup.*
import br.com.zup.grpc.KeyManagerGrpcFactoryClient
import com.google.protobuf.Timestamp
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
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@MicronautTest
internal class BuscaChaveControllerTest {

    @field:Inject
    lateinit var buscaPorPixIDEClienteIDGrpc: KeyManagerBuscaChavePorIDGrpcServiceGrpc.KeyManagerBuscaChavePorIDGrpcServiceBlockingStub

    @field:Inject
    lateinit var buscaPorChavePixGrpc: KeyManagerBuscaChaveGrpcServiceGrpc.KeyManagerBuscaChaveGrpcServiceBlockingStub

    @field:Inject
    lateinit var listaChavesPixGrpc: KeyManagerListaChavesGrpcServiceGrpc.KeyManagerListaChavesGrpcServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var clientHttp: HttpClient

    companion object {
        val CLIENTE_ID = UUID.randomUUID().toString()
        val PIX_ID = Random.nextLong(10).toString()
    }

    @Test
    fun `deve buscar uma chave pix pelo ID do cliente e ID da chave pix`() {

        val chavePixResponse = chavePixResponse()

        Mockito.`when`(buscaPorPixIDEClienteIDGrpc.buscaChavePorID(Mockito.any()))
            .thenReturn(chavePixResponse)

        val request = HttpRequest.GET<Any>("/pix/${chavePixResponse.pixId}/cliente/${chavePixResponse.clienteId}")

        val response = clientHttp.toBlocking().exchange(request, Any::class.java)

        with(response) {
            Assertions.assertEquals(HttpStatus.OK, status)
            Assertions.assertNotNull(body())
        }
    }

    @Test
    fun `deve buscar uma chave pix pela chave`() {

        val chavePixResponse = chavePixResponse()

        Mockito.`when`(buscaPorChavePixGrpc.buscaPorChave(Mockito.any()))
            .thenReturn(chavePixResponse)

        val request = HttpRequest.GET<Any>("/pix/${chavePixResponse.chave}")

        val response = clientHttp.toBlocking().exchange(request, Any::class.java)

        with(response) {
            Assertions.assertEquals(HttpStatus.OK, status)
            Assertions.assertNotNull(body())
        }
    }

    @Test
    fun `deve listar todas as chaves pix por ID do cliente`() {

        val listaPixRespose = listaPixRespose()

        Mockito.`when`(listaChavesPixGrpc.listaChaves(Mockito.any()))
            .thenReturn(listaPixRespose)

        val request = HttpRequest.GET<Any>("pix/cliente/${listaPixRespose.clienteId}")

        val response = clientHttp.toBlocking().exchange(request, List::class.java)

        with(response) {
            Assertions.assertEquals(HttpStatus.OK, status)
            Assertions.assertNotNull(body())
            Assertions.assertEquals(listaPixRespose.chavesCount, body().size)
        }
    }

    @Factory
    @Replaces(factory = KeyManagerGrpcFactoryClient::class)
    class BuscaPorPixIDEClientIDStub {
        @Singleton
        fun buscaPorPixIDStub() =
            Mockito.mock(KeyManagerBuscaChavePorIDGrpcServiceGrpc.KeyManagerBuscaChavePorIDGrpcServiceBlockingStub::class.java)

        @Singleton
        fun buscaPorchaveStub() =
            Mockito.mock(KeyManagerBuscaChaveGrpcServiceGrpc.KeyManagerBuscaChaveGrpcServiceBlockingStub::class.java)

        @Singleton
        fun listaChavesStub() =
            Mockito.mock(KeyManagerListaChavesGrpcServiceGrpc.KeyManagerListaChavesGrpcServiceBlockingStub::class.java)
    }

    fun chavePixResponse(): DetalhesChavePixResponse {
        val criadaEm = LocalDateTime.now().atZone(ZoneId.of("UTC")).toInstant()
        return DetalhesChavePixResponse.newBuilder()
            .setPixId(PIX_ID)
            .setClienteId(CLIENTE_ID)
            .setTipoDeChave(TipoDeChave.EMAIL.name)
            .setChave("marcelo@gmail.com")
            .setConta(
                Conta.newBuilder()
                    .setTitular("Marcelo Felix")
                    .setCpf("111.111.111-11")
                    .setInstituicao("Itau")
                    .setAgencia("0001")
                    .setNumeroConta("13739018")
                    .setTipo(TipoDeConta.CONTA_CORRENTE.name)
            )
            .setCriadaEm(
                Timestamp.newBuilder()
                    .setSeconds(criadaEm.epochSecond)
                    .setNanos(criadaEm.nano)
                    .build()
            )
            .build()
    }

    fun listaPixRespose(): ListaChavesResponse {

        val criadaEm = LocalDateTime.now().atZone(ZoneId.of("UTC")).toInstant()

        val chaveEmail = ListaChavesResponse.ChavesPix.newBuilder()
            .setPixId(PIX_ID)
            .setTipoChave(TipoDeChave.EMAIL.name)
            .setChave("marcelo@gmail.com")
            .setTipoConta(TipoDeConta.CONTA_CORRENTE.name)
            .setCriadaEm(Timestamp.newBuilder()
                .setSeconds(criadaEm.epochSecond)
                .setNanos(criadaEm.nano))
            .build()

        val chaveAleatoria = ListaChavesResponse.ChavesPix.newBuilder()
            .setPixId(PIX_ID)
            .setTipoChave(TipoDeChave.ALEATORIA.name)
            .setChave(UUID.randomUUID().toString())
            .setTipoConta(TipoDeConta.CONTA_CORRENTE.name)
            .setCriadaEm(Timestamp.newBuilder()
                .setSeconds(criadaEm.epochSecond)
                .setNanos(criadaEm.nano))
            .build()

        return ListaChavesResponse.newBuilder()
            .setClienteId(CLIENTE_ID)
            .addAllChaves(listOf(chaveEmail, chaveAleatoria))
            .build()
    }
}