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
    fun `deve lancar 404 para StatusRuntimeException not found`() {

        val mensagem = "Não foi encontrado com os dados informado"
        val notFoundException = StatusRuntimeException(Status.NOT_FOUND.withDescription(mensagem))

        val response = GlobalExceptionHandler().handle(request, notFoundException)

        with(response) {
            Assertions.assertEquals(HttpStatus.NOT_FOUND, status)
            Assertions.assertNotNull(body())
            Assertions.assertEquals(mensagem, (body() as JsonError).message)
        }
    }

    @Test
    fun `deve lancar 400 para StatusRuntimeException Invalid Argument`() {

        val mensagem = "Dados preenchidos inválidos"
        val invalidArgument = StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription(mensagem))

        val response = GlobalExceptionHandler().handle(request, invalidArgument)

        with(response) {
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, status)
            Assertions.assertNotNull(body())
            Assertions.assertEquals(mensagem, (body() as JsonError).message)
        }
    }

    @Test
    fun `deve lancar 422 para StatusRuntimeException Already Exists`() {

        val mensagem = "Dados informados já existem"
        val alreadyExists = StatusRuntimeException(Status.ALREADY_EXISTS.withDescription(mensagem))

        val response = GlobalExceptionHandler().handle(request, alreadyExists)

        with(response) {
            Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status)
            Assertions.assertNotNull(body())
            Assertions.assertEquals(mensagem, (body() as JsonError).message)
        }
    }

}