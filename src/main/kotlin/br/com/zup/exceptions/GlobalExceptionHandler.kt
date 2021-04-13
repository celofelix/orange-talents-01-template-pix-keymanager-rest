package br.com.zup.exceptions

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.hateoas.JsonError
import io.micronaut.http.server.exceptions.ExceptionHandler
import javax.inject.Singleton

@Singleton
class GlobalExceptionHandler : ExceptionHandler<StatusRuntimeException, HttpResponse<Any>> {

    override fun handle(
        request: HttpRequest<*>,
        exception: StatusRuntimeException
    ): HttpResponse<Any> {

        val status = exception.status.code
        val description = exception.status.description

        val (httpStatus, message) = when (status) {
            Status.NOT_FOUND.code -> HttpStatus.NOT_FOUND to description
            Status.INVALID_ARGUMENT.code -> HttpStatus.BAD_REQUEST to "Dados preenchidos inválidos"
            Status.ALREADY_EXISTS.code -> HttpStatus.UNPROCESSABLE_ENTITY to description
            else -> HttpStatus.INTERNAL_SERVER_ERROR to "Erro ao cadastrar a chave"
        }

        return HttpResponse
            .status<JsonError>(httpStatus)
            .body(JsonError(message))
    }
}