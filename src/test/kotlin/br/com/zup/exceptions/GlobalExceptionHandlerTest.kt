package br.com.zup.exceptions

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.hateoas.JsonError
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GlobalExceptionHandlerTest {

    val request = HttpRequest.GET<Any>("/")

    @Test
    fun `deve lancar 404 para statusException not found`() {

        val mensagem = "Não foi encontrado com os dados informado"
        val notFoundException = StatusRuntimeException(Status.NOT_FOUND.withDescription(mensagem))

        val response = GlobalExceptionHandler().handle(request, notFoundException)

        with(response) {
            Assertions.assertEquals(HttpStatus.NOT_FOUND, status)
            Assertions.assertNotNull(body())
            Assertions.assertEquals(mensagem, (body() as JsonError).message)
        }
    }

}