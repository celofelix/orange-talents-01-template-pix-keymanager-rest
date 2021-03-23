package br.com.zup.busca

import io.micronaut.core.annotation.Introspected
import io.micronaut.validation.Validated
import javax.validation.constraints.NotBlank

@Validated
@Introspected
class ContaResponse(
    @field:NotBlank val titular: String?,
    @field:NotBlank val cpf: String?,
    @field:NotBlank val instituicao: String?,
    @field:NotBlank val agencia: String?,
    @field:NotBlank val numeroConta: String?,
    @field:NotBlank val tipo: String?
) {

}
